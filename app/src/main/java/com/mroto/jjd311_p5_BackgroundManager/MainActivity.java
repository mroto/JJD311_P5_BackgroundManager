package com.mroto.jjd311_p5_BackgroundManager;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mroto.jjd311_p5_BackgroundManager.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int SLEEP_TASK=10;
    private static final String TAG=MainActivity.class.getSimpleName();



    public ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_start_async_task).setOnClickListener(this);
        findViewById(R.id.btn_start_thread).setOnClickListener(this);
        this.progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_start_async_task){
            runAsyncTask();
        }else if(view.getId()==R.id.btn_start_thread){
            runThread();
        }
    }

    //THREAD ///////////////////////////////////////////////////////////////////////////////////////
    private void runThread(){
        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(MainActivity.TAG, "Starting Thread...");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Sleeping...", Toast.LENGTH_SHORT).show();
                    }
                });
                new Sleeper().sleepForAWhile(MainActivity.SLEEP_TASK);
            }
        });

        myThread.start();
        Log.e(MainActivity.TAG, "Thread Started");
    }


    //ASYNC TASK////////////////////////////////////////////////////////////////////////////////////
    private void runAsyncTask(){
        this.progressBar.setProgress(0);
        MyAsyncTask asyncTask = new MyAsyncTask(this);
        asyncTask.execute(MainActivity.SLEEP_TASK);
    }

    public class MyAsyncTask extends AsyncTask<Integer, Integer, String> {

        private final String TAG=MyAsyncTask.class.getSimpleName();
        private static final int UPDATE_STEPS=10;

        private Context myContext;

        public MyAsyncTask(Context anyContext){
            this.myContext=anyContext;
        }

        @Override
        protected String doInBackground(Integer... integers) {
            int sleepTime=integers[0]/MyAsyncTask.UPDATE_STEPS;
            Sleeper mySleeper = new Sleeper();

            for(int i=0; i<MyAsyncTask.UPDATE_STEPS; i++) {
                mySleeper.sleepForAWhile(sleepTime);
                publishProgress((i+1)*(100/MyAsyncTask.UPDATE_STEPS));
            }

            return "Async Task finished";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(this.myContext, s, Toast.LENGTH_SHORT).show();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
