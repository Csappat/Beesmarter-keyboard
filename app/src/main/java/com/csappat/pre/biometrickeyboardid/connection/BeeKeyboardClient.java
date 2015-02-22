package com.csappat.pre.biometrickeyboardid.connection;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Krisz on 2015.02.22..
 */
public class BeeKeyboardClient {
    private static final String TEAM_ID = "TEST1"; //TODO: változtatni az éles verzióhoz
    public connectTask conctTask = null;
    private Handler handler = new Handler();
    private String msgResponse = "";
    private TCPClient mTcpClient = null;
    private boolean ready = false;
    private String status = "";

    private ArrayList<String> arrayList;
    private String password, trainer, probe;

    public BeeKeyboardClient() {
        Log.d("BeeKeyboardClient", "Konstruktor meghívva, csatlakozás, handshake.");
        mTcpClient = null;
        // connect to the server
        conctTask = new connectTask();
        conctTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void ConnectionReady() {
        sendMessage(TEAM_ID);
        ready = true;
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
            this.Destroy();
            return null;
        } else return msgResponse;
    }

    private void sendMessage(String message) {
        if (mTcpClient != null) {
            Log.d("Bee", "mTcpClient not null, sendMessage" + message);
            mTcpClient.sendMessage(message);
        }
    }

    public void Destroy() {
        try {
            System.out.println("Destroy.");
            mTcpClient.sendMessage("BYE");
            mTcpClient.stopClient();
            conctTask.cancel(true);
            conctTask = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class connectTask extends AsyncTask<String, String, TCPClient> {
        public AsyncResponse delegate = null;

        @Override
        protected TCPClient doInBackground(String... message) {
            mTcpClient = new TCPClient(new TCPClient.OnMessageReceived() {
                @Override
                public void messageReceived(String message) {
                    try {
                        publishProgress(message);
                        if (message != null) {
                            System.out.println("FromSocket> " + message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            mTcpClient.run();
            if (mTcpClient != null) {
                mTcpClient.sendMessage("Init");
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String tmpMsg = values[0];
            if (tmpMsg.endsWith("WAITING FOR REQUEST")) tmpMsg = "WAITING";
            if (tmpMsg.startsWith("PASSWORD")) tmpMsg = "PASS";
            switch (tmpMsg) {
                case "BSP 1.0 SERVER HELLO":
                    sendMessage("BSP 1.0 CLIENT HELLO");
                    break;
                case "SEND YOUR ID":
                    sendMessage(TEAM_ID);
                    break;
                case "WAITING":
                    sendMessage("RQSTDATA");
                    break;
                case "PASS":
                    delegate.msgIn("PASS", values[0].substring(9));
                    status = "RQSTTRAIN";
                    sendMessage("RQSTTRAIN");
                    break;
                default:
                    if (status == "RQSTTRAIN") {
                        delegate.msgIn("TRAIN", values[0]);
                        status = "RQSTTEST";
                        break;
                    }
                    if (status == "RQSTTEST") {
                        if (values[0].startsWith("GOODBYE")) status = "";
                        else {
                            delegate.msgIn("TEST", values[0]);
                            break;
                        }
                    }

            }
        }
    }
}
