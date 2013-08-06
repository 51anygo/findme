package com.anygo;

import com.anygo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewTrack extends Activity
{
    private static final String TAG = "NewTrack";
    private Button mButtonNew;
    private EditText mNewNameEditText;
    private EditText mDescEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_track);
        setTitle(R.string.menu_new);

        findViews();
        setListensers();
    }

    private void setListensers()
    {
        mButtonNew.setOnClickListener(newTrack);
    }

    private Button.OnClickListener newTrack = new Button.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            String name = mNewNameEditText.getText().toString();
            String desc = mDescEditText.getText().toString();

            // �������Ϊ��
            if (name.trim().equals(""))
            {
                Toast.makeText(NewTrack.this, R.string.new_name_null,
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                // ���ô洢�ӿ�,����Service
                Intent intent = new Intent(NewTrack.this, ShowTrack.class);
                startActivity(intent);
            }
        }

    };

    private void findViews()
    {
        mButtonNew = (Button) findViewById(R.id.new_submit);
        mNewNameEditText = (EditText) findViewById(R.id.new_name);
        mDescEditText = (EditText) findViewById(R.id.new_desc);
    }

}
