package org.jokar.gankio.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.tencent.bugly.crashreport.CrashReport;

import org.jokar.gankio.BuildConfig;
import org.jokar.gankio.di.component.network.DaggerNetComponent;
import org.jokar.gankio.di.component.network.NetComponent;
import org.jokar.gankio.di.module.network.NetModule;


/**
 * Created by JokAr on 16/9/9.
 */
public class GankioApplication extends Application {

    private static NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化bugly
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppVersion(String.valueOf(getVersionCode(getApplicationContext())));
        CrashReport.initCrashReport(getApplicationContext(), "eba929c175", BuildConfig.DEBUG, strategy);
        mNetComponent = DaggerNetComponent.builder()
                .netModule(new NetModule())
                .build();
    }

    public static NetComponent getNetComponent() {
        return mNetComponent;
    }

    /**
     * 获取versionCode
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {// 获取版本号(内部识别号)
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }
}
