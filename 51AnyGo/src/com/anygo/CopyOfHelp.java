package com.anygo;

import com.anygo.R;

import android.app.Activity;
import android.os.Bundle;

public class CopyOfHelp extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        setTitle(R.string.meun_help);
    }
    
}
