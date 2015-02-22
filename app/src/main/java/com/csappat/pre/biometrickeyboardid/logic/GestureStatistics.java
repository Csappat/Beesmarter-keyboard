package com.csappat.pre.biometrickeyboardid.logic;

import java.util.HashMap;

/**
 * Created by User on 2015.02.22..
 */
public class GestureStatistics {
    public double averageSpeed = 0; //idő a jelszó begépelésére

    public double averageTypoCount = 0; //backspaceek száma

    public double averageTouchAccuracy = 0; //mennyire pontosan nyomta meg a felhasználó az egyes gombokat
                                        // - átlagos eltérés a középponttól
                                        double averageTouchAccuracyX = 0; // X irányban
                                        double averageTouchAccuracyY = 0; //Y irányban
    public double deviationTouchAccuracy = 0; // közzépponttól való eltérés szórása
    public double deviationTouchAccuracyX = 0; // x irányban eltérés szórása
    public double deviationTouchAccuracyY = 0; // y irányban eltérés szórása

    public double deviationTimeBetweenReleaseAndPress = 0;
    public double deviationTimeOfButtonPress = 0;

}
