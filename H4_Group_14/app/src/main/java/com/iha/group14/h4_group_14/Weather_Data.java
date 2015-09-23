/*
REFERENCE http://developer.android.com/guide/components/bound-services.html
 */

package com.iha.group14.h4_group_14;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class Weather_Data extends Service {

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        Weather_Data getService() {
            // Return this instance of LocalService so clients can call public methods
            return Weather_Data.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public Weather_Data() {

    }

}
