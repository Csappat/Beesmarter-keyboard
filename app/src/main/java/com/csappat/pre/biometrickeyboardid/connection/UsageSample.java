package com.csappat.pre.biometrickeyboardid.connection;

import android.app.Activity;
import android.util.Log;

public class UsageSample extends Activity implements AsyncResponse {
    BeeKeyboardClient myClient;

    /*
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_connection);

            myClient = new BeeKeyboardClient();
            myClient.conctTask.delegate = this;
        }
        */
    public void msgIn(String type, String message) {
        Log.d("msgIn", "Típus: " + type + " Üzenet: " + message);
        //TODO: különböző visszatérések kezelése
        //Kapható típusok:
        //PASS, TRAIN, TEST

        switch (type) {
            case "PASS": //Jelszót kaptunk a message változóban
                break;
            case "TRAIN": //<train>ADAT</train> XML a message változóban
                break;
            case "TEST": //<pattern> tesztadat a message változóban
                break;
            default:
                break;
        }
    }
}