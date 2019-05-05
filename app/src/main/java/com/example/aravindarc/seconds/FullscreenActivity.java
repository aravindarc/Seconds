package com.example.aravindarc.seconds;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FullscreenActivity extends AppCompatActivity {

    private View mContentView;

    DateDifferenceHelper.type currentType = DateDifferenceHelper.type.ALL;

    private AsyncTaskRunner myTask;

    private String BIRTHDATE = "1999/05/12 14:20:00";
    private DateDifferenceHelper dateDifferenceHelper = new DateDifferenceHelper(BIRTHDATE);
    private int delay = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        mContentView = findViewById(R.id.fullscreen_content);

        mContentView.setBackgroundColor(Color.WHITE);
        ((TextView)mContentView).setTextColor(Color.BLACK);
        myTask = new AsyncTaskRunner();
        myTask.execute(BIRTHDATE);

        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
        hide();
    }

    @Override
    public void onResume() {
        super.onResume();

        hide();
    }

    private void toggle() {

        switch (currentType) {

            case ALL:
                currentType = DateDifferenceHelper.type.MONTHS;
                break;
            case MONTHS:
                currentType = DateDifferenceHelper.type.DAYS;
                break;
            case DAYS:
                currentType = DateDifferenceHelper.type.HOURS;
                break;
            case HOURS:
                currentType = DateDifferenceHelper.type.MINUTES;
                break;
            case MINUTES:
                currentType = DateDifferenceHelper.type.SECONDS;
                break;
            case SECONDS:
                currentType = DateDifferenceHelper.type.LOVE;
                break;
            case LOVE:
                currentType = DateDifferenceHelper.type.ALL;
        }

        dateDifferenceHelper.setCurrentType(currentType);
    }

    @SuppressLint("InlinedApi")
    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            while (true) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                publishProgress(dateDifferenceHelper.getDifference());
            }
        }

        @Override
        protected void onProgressUpdate(String... text) {
            delay = 100;
            ((TextView)mContentView).setText(text[0]);
            mContentView.setBackgroundColor(Color.BLACK);
            ((TextView)mContentView).setTextColor(Color.WHITE);
        }
    }
}
