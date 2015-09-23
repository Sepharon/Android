package com.iha.group14.h4_group_14;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Weather_Data extends Service {
    public Weather_Data() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
