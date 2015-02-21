package com.csappat.pre.connect;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

/**
 * Created by Krisz on 2015.02.22..
 */
public class BeeKeyboardClient {
    private static String IP_ADDRESS = "192.168.0.105";
    private static int IP_PORT = 9999;
    private static String TEAM_ID = "TEST1"; //TODO: változtatni az éles verzióhoz
    private Socket client;
    private BufferedReader read;
    private BufferedWriter output;
    private Thread thread;
    private String msgResponse = "";

    private String password, trainer, probe;

    public BeeKeyboardClient() {
        Log.d("Csatlakozás", "Konstruktor meghívva.");
    }

    public String GetPassword() {
        //TODO: jelszólekérés
        return password;
    }

    public String getTraining() {
        //TODO: tanítóadatok lekérése
        return trainer;
    }

    public String getTest() {
        //TODO: próbaadat lekérése
        return probe;
    }

    public boolean setResult(boolean auth) {
        //TODO: szerver felé válasz küldése
        boolean flag = false; //Feladat elvégzés jelző
        return flag;
    }
}
