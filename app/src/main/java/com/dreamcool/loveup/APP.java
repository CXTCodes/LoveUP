package com.dreamcool.loveup;

import android.app.Application;
import com.tencent.bugly.crashreport.CrashReport;

public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "a450f5fa93", true);
    }
}
