package com.mroto.jjd311_p5_BackgroundManager;

import android.util.Log;

/**
 * Created by A5Alumno on 28/11/2016.
 */

public class Sleeper {

    private static final String TAG=Sleeper.class.getSimpleName();

    private static final int SLEEP_STEPS=10;

    public void sleepForAWhile(int numSeconds){
        numSeconds=numSeconds*1000;
        long endTime = System.currentTimeMillis() + (numSeconds);
        while(System.currentTimeMillis() < endTime){
            synchronized (this){
                try{
                    long leftTime=(endTime - System.currentTimeMillis())/1000;
                    Log.e(Sleeper.TAG, "sleepForAWhile: "+leftTime);
                    this.wait(numSeconds/Sleeper.SLEEP_STEPS);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
        Log.e(Sleeper.TAG, "sleepForAWhile: Wake Up!");
    }
}
