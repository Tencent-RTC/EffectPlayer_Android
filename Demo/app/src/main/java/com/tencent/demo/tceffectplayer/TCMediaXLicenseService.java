package com.tencent.demo.tceffectplayer;

import android.util.Log;

import com.tencent.tcmediax.api.ILicenseCallback;
import com.tencent.tcmediax.api.TCMediaXBase;

public class TCMediaXLicenseService {
    private static final String TAG = "TCMediaXLicenseService";

    private TCMediaXLicenseService() {
    }

    private static class Holder {
        private static final TCMediaXLicenseService instance = new TCMediaXLicenseService();
    }
    public static TCMediaXLicenseService getInstance() {
        return Holder.instance;
    }

    private static final String sLicenseUrl = $your-license-url$;
    private static final String sLicenseKey = $your-license-key$;

    private final ILicenseCallback mLicenseCallback = new ILicenseCallback() {
        @Override
        public void onResult(int errCode, String msg) {
            Log.d(TAG, "TCMediaX license result: errCode: " + errCode + ", msg: " + msg);
        }
    };

    /**
     * 1. Change the applicationId in build.gradle to the corresponding value
     * 2. Change sLicenseUrl and sLicenseKey to the corresponding values
     * 3. Sync the project and run to experience the effect
     */
    public void init() {
        TCMediaXBase.getInstance().setLogEnable(DemoApplication.getAppContext(), true);
        TCMediaXBase.getInstance().setLicense(DemoApplication.getAppContext(), sLicenseUrl, sLicenseKey, mLicenseCallback);
    }
}
