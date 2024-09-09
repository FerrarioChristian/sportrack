package it.unimib.icasiduso.sportrack;

import android.app.Application;
import android.content.res.Resources;

import com.google.firebase.database.FirebaseDatabase;

public class App extends Application {
    private static App INSTANCE;
    private static Resources res;

    public static App getInstance() {
        return INSTANCE;
    }

    public static Resources getRes() {
        return res;
    }

    public static void setRes(Resources res) {
        App.res = res;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        res = getResources();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
