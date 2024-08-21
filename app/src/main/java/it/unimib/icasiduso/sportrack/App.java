package it.unimib.icasiduso.sportrack;

import android.app.Application;
import android.content.res.Resources;

public class App extends Application {
    private static App INSTANCE;
    private static Resources res;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        res = getResources();
    }

    public static App getInstance() {
        return INSTANCE;
    }

    public static Resources getRes() {
        return res;
    }
}
