package com.csappat.pre.biometrickeyboardid.logic;

/**
 * Created by User on 2015.02.22..
 */
public class GestureStatistics {
    double averageSpeed = 0; //idő a jelszó begépelésére

    double averageTypoCount = 0; //backspaceek száma

    double averageTouchAccuracy = 0; //mennyire pontosan nyomta meg a felhasználó az egyes gombokat
                                        // - átlagos eltérés a középponttól
                                        double averageTouchAccuracyX = 0; // X irányban
                                        double averageTouchAccuracyY = 0; //Y irányban
    double deviationTouchAccuracy = 0; // közzépponttól való eltérés szórása
    double deviationTouchAccuracyX = 0; // x irányban eltérés szórása
    double deviationTouchAccuracyY = 0; // y irányban eltérés szórása

    double deviationTimeBetweenReleaseAndPress = 0;
    double deviationTimeOfButtonPress = 0;

    //karakterenként megnézni pl. R betűt mindig bal felső sarokban nyomja meg a P-t mindig középen
}
