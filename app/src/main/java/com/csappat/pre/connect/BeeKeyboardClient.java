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
    private boolean ready = false;

    private String password, trainer, probe;

    public BeeKeyboardClient() {
        Log.d("BeeKeyboardClient", "Konstruktor meghívva, csatlakozás, handshake.");
        Log.d("BeeKeyboardClient", "Adatok: " + IP_ADDRESS + ":" + IP_PORT + "@" + TEAM_ID);
        sendMessage("BSP 1.0 CLIENT HELLO");
        sendMessage(TEAM_ID);
        if (msgResponse == TEAM_ID + " ID ACK - WAITING FOR REQUEST") {
            ready = true;
        }
    }

    public String GetPassword() {
        if (!ready) return null;
        sendMessage("RQSTDATA");
        password = msgResponse;
        return password;
    }

    public String getTraining() {
        if (!ready) return null;
        sendMessage("RQSTTRAIN");
        trainer = msgResponse;
        return trainer;
    }

    public String getTest() {
        if (!ready) return null;
        sendMessage("RQSTTEST");
        probe = msgResponse;
        return probe;
    }

    public String setResult(boolean auth) {
        if (!ready) return null;
        sendMessage((auth) ? "ACCEPT" : "REJECT");
        if (msgResponse.startsWith("GOODBYE")) {
            ready = false;
            return null;
        } else return msgResponse;
    }

    private void sendMessage(String toSend) {
        //TODO: küldő javítás, tesztelés, beillesztés
    }
}
