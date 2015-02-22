package com.csappat.pre.biometrickeyboardid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.csappat.pre.biometrickeyboardid.connection.AsyncResponse;
import com.csappat.pre.biometrickeyboardid.connection.BeeKeyboardClient;

public class ServerTest extends Activity implements AsyncResponse {
    public ArrayAdapter<String> msgList;
    BeeKeyboardClient myClient;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servertest);
        list = (ListView) findViewById(R.id.listView);
        msgList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        list.setAdapter(msgList);
        myClient = new BeeKeyboardClient();
        myClient.conctTask.delegate = this;
        Log.d("ConnectionActivity", "Konstruktor vége");

    }

    public void clickTest(View view) {
        myClient.Start();
    }

    public void msgIn(String type, String message) {
        Log.d("msgIn", "Típus: " + type + " Üzenet: " + message);

        msgList.add("Típus: " + type + " Üzenet: " + message);

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