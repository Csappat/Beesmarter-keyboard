package com.csappat.pre.biometrickeyboardid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.csappat.pre.biometrickeyboardid.connection.AsyncResponse;
import com.csappat.pre.biometrickeyboardid.connection.BeeKeyboardClient;
import com.csappat.pre.biometrickeyboardid.logic.KeyPressCollector;
import com.csappat.pre.biometrickeyboardid.logic.PasswordGestureCollector;
import com.csappat.pre.biometrickeyboardid.xml.PatternModel;
import com.csappat.pre.biometrickeyboardid.xml.XMLParser;

import java.util.List;

public class ServerTest extends Activity implements AsyncResponse {
    public ArrayAdapter<String> msgList;
    BeeKeyboardClient myClient;
    List<PatternModel> pmlist;
    private ListView list;
    private XMLParser parser = new XMLParser();
    private KeyPressCollector col = new KeyPressCollector(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servertest);
        list = (ListView) findViewById(R.id.listView);
        msgList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        list.setAdapter(msgList);
        myClient = new BeeKeyboardClient(getIntent().getExtras().getString("ip"));
        myClient.conctTask.delegate = this;
        Log.d("ConnectionActivity", "Konstruktor vége");

    }

    public void clickTest(View view) {
        myClient.Start();
        PasswordGestureCollector.resetKeyBoardTrainingGestures();
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
                try {
                    pmlist = XMLParser.parseXML(message);
                } catch (Exception e) {
                }
                for (PatternModel p : pmlist) {
                    col.keyPressed(p);
                }
                KeyPressCollector.resetDefaultCollector();
                PasswordGestureCollector.addGestureFromKeyboard(col);
                break;
            case "TEST": //<pattern> tesztadat a message változóban
                try {
                    pmlist = XMLParser.parseXML(message);
                } catch (Exception e) {
                }
                KeyPressCollector colForTrial = new KeyPressCollector(false);
                for (PatternModel p : pmlist) {
                    colForTrial.keyPressed(p);
                }
                Log.d("IsItYou: ", "" + PasswordGestureCollector.getGesturesFromKeyboardTraining().isItYou(colForTrial));
                myClient.Auth(PasswordGestureCollector.getGesturesFromKeyboardTraining().isItYou(colForTrial));
                //PasswordGestureCollector.getGesturesFromKeyboardTraining().isItYou(col);
                break;
            case "BYE":
                myClient.Destroy();
            default:
                break;
        }
    }

}