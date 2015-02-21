package com.csappat.pre.biometrickeyboardid.logic;

import java.util.ArrayList;

/**
 * Created by User on 2015.02.22..
 */
public class PasswordGestureCollector {
    private ArrayList<KeyPressCollector> gestures = new ArrayList<KeyPressCollector>();
    private GestureStatistics stat = null;


    //max 4 sec
    public void isItYou(KeyPressCollector current) {
        /* Check the valadity of the given password with gesture detector
        (using Gesture statistics and current KeyPressCollecters's gestures
         */

    }

    public void add(KeyPressCollector c) {
        gestures.add(c);
    }

    //max 30 sec
    public void finishTraining() {
        /*
        calculate the GestureStatistics object
         */
    }

    public void startTraining() {
        gestures = new ArrayList<KeyPressCollector>();
        GestureStatistics stat = null;
    }
}
