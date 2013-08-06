package com.anygo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/** *//**
* TODO
* 
* @author tianlu
* @version 1.0 Create At : 2010-2-18 下午01:58:39
*/
public class TestContentProvider extends ContentProvider {
    private SQLiteDatabase mDb;
    private DatabaseHelper mDbHelper = null;
    private static final String DATABASE_NAME = "51anygo.db";
    private static final String DATABASE_TABLE_NAME = "rssItems";
    private static final int DB_VERSION = 1;
    private static final int ALL_MESSAGES = 1;
    private static final int SPECIFIC_MESSAGE = 2;

    // Set up our URL matchers to help us determine what an
    // incoming URI parameter is.
    private static final UriMatcher URI_MATCHER;
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI("test", "item", ALL_MESSAGES);
        URI_MATCHER.addURI("test", "item/#", SPECIFIC_MESSAGE);
    }

    // Here's the public URI used to query for RSS items.
    public static final Uri CONTENT_URI = Uri
            .parse("content://test/item");

    // Here are our column name constants, used to query for field values.
    public static final String ID = "_id";
    public static final String NAME = "NAME";
    public static final String VALUE = "VALUE";
    public static final String DEFAULT_SORT_ORDER = ID + " DESC";

    private static class DatabaseHelper extends AbstractDatabaseHelper {

        @Override
        protected String[] createDBTables() {
            // TODO Auto-generated method stub
            String sql = "CREATE TABLE " + DATABASE_TABLE_NAME + "(" + ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT,"
                    + VALUE + " TEXT);";
            return new String[] { sql };
        }

        @Override
        protected String[] dropDBTables() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        protected String getDatabaseName() {
            // TODO Auto-generated method stub
            return DATABASE_NAME;
        }

        @Override
        protected int getDatabaseVersion() {
            // TODO Auto-generated method stub
            return DB_VERSION;
        }

        @Override
        protected String getTag() {
            // TODO Auto-generated method stub
            return TestContentProvider.class.getSimpleName();
        }

    }

    /** *//**
     * 
     */
    public TestContentProvider() {
        // TODO Auto-generated constructor stub
        
    }

    /**//*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#delete(android.net.Uri,
     * java.lang.String, java.lang.String[])
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // NOTE Argument checking code omitted. Check your parameters!
        int rowCount = mDb.delete(DATABASE_TABLE_NAME, selection, selectionArgs);

        // Notify any listeners and return the deleted row count.
        getContext().getContentResolver().notifyChange(uri, null);
        return rowCount;
    }

    /**//*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#getType(android.net.Uri)
     */
    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
        case ALL_MESSAGES:
            return "vnd.android.cursor.dir/rssitem"; // List of items.
        case SPECIFIC_MESSAGE:
            return "vnd.android.cursor.item/rssitem"; // Specific item.
        default:
            return null;
        }
    }

    /**//*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#insert(android.net.Uri,
     * android.content.ContentValues)
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // NOTE Argument checking code omitted. Check your parameters! Check that
        // your row addition request succeeded!

       long rowId = -1;
       rowId = mDb.insert(DATABASE_TABLE_NAME, NAME, values);
       Uri newUri = Uri.withAppendedPath(CONTENT_URI, ""+rowId);
       Log.i("TestContentProvider", "saved a record " + rowId + " " + newUri);
       // Notify any listeners and return the URI of the new row.
       getContext().getContentResolver().notifyChange(CONTENT_URI, null);
       return newUri;
    }

    /**//*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#onCreate()
     */
    @Override
    public boolean onCreate() {
        // TODO Auto-generated method stub
        try
        {
            mDbHelper = new DatabaseHelper();
            mDbHelper.open(getContext());
            mDb = mDbHelper.getMDb();
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    /**//*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#query(android.net.Uri,
     * java.lang.String[], java.lang.String, java.lang.String[],
     * java.lang.String)
     */
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        // We won't bother checking the validity of params here, but you should!

        // SQLiteQueryBuilder is the helper class that creates the
        // proper SQL syntax for us.
        SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();

        // Set the table we're querying.
        qBuilder.setTables(DATABASE_TABLE_NAME);

        // If the query ends in a specific record number, we're
        // being asked for a specific record, so set the
        // WHERE clause in our query.
        if((URI_MATCHER.match(uri)) == SPECIFIC_MESSAGE){
            qBuilder.appendWhere("_id=" + uri.getLastPathSegment());
            Log.i("TestContentProvider", "_id=" +  uri.getLastPathSegment());
        }


        // Make the query.
        Cursor c = qBuilder.query(mDb,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        Log.i("TestContentProvider", "get records");
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    /**//*
     * (non-Javadoc)
     * 
     * @see android.content.ContentProvider#update(android.net.Uri,
     * android.content.ContentValues, java.lang.String, java.lang.String[])
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        // NOTE Argument checking code omitted. Check your parameters!
        int updateCount = mDb.update(DATABASE_TABLE_NAME, values, selection, selectionArgs);

        // Notify any listeners and return the updated row count.
        getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;
    }

}

/*在客户端中可以使用如下方法进行调用： 
ContentValues values = new ContentValues(); 
values.put(TestContentProvider.NAME, "testname1"); 
values.put(TestContentProvider.VALUE, "testvalu1e"); 
Uri newAddUri = TestActivity.this.getContentResolver().insert(TestContentProvider.CONTENT_URI, values); 
Cursor c = TestActivity.this.managedQuery(newAddUri, new String[]{TestContentProvider.NAME}, null, null, null); 
Log.i("TestActivity", "" + c.getCount()); 
if(c.moveToNext()) 
{ 
    Log.i("TestActivity", c.getString(0)); 
}上面的代码是先进行插入，然后进行查询并打印。就是如此简单，所有的应用如果需要都可以对外方便的提供数据接口，同时其他应用也可以很方便的进行调用
*/