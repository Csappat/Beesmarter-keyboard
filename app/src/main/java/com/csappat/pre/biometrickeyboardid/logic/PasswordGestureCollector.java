package com.csappat.pre.biometrickeyboardid.logic;

import com.csappat.pre.biometrickeyboardid.xml.PatternModel;
import com.csappat.pre.biometrickeyboardid.xml.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2015.02.22..
 */
public class PasswordGestureCollector {
    private ArrayList<KeyPressCollector> gestures = new ArrayList<KeyPressCollector>();
    private GestureStatistics stat = null;


    //max 4 sec
    public boolean isItYou(KeyPressCollector current) {
        PasswordGestureCollector col = new PasswordGestureCollector();
        col.add(current);
        col.finishTraining();
        boolean result = true;
        if (gestures != null) {
            if (Math.abs((col.stat.deviationTouchAccuracyX - stat.deviationTouchAccuracyX) / stat.deviationTouchAccuracyX) > 0.3 ) {
                return false;
            }
            if (Math.abs((col.stat.deviationTouchAccuracyY - stat.deviationTouchAccuracyY) / stat.deviationTouchAccuracyY) > 0.3 ) {
                return false;
            }
            if (Math.abs(col.stat.averageTypoCount - col.stat.averageTypoCount) > 1) {
                return false;
            }
            if (Math.abs((col.stat.averageSpeed - stat.averageSpeed) / stat.averageSpeed) > 0.3 ) {
                return false;
            }
            if (Math.abs((col.stat.deviationTimeBetweenReleaseAndPress - stat.deviationTimeBetweenReleaseAndPress) / stat.deviationTimeBetweenReleaseAndPress) > 0.3) {
                return false;
            }
            if (Math.abs((col.stat.deviationTimeOfButtonPress - stat.deviationTimeOfButtonPress) / stat.deviationTimeOfButtonPress) > 0.3) {
                return false;
            }
        } else {
            return false;
        }
        return result;
    }

    public void add(KeyPressCollector c) {
        gestures.add(c);
    }

    //max 30 sec
    public void finishTraining() {
        stat = new GestureStatistics();

        int avgSpeed=0;
        ArrayList<Double> onButton = new ArrayList<Double>();
        ArrayList<Double> betweenGestures = new ArrayList<Double>();
        ArrayList<Double> accuracyX = new ArrayList<Double>();
        ArrayList<Double> accuracyY = new ArrayList<Double>();
        for (KeyPressCollector k : gestures) {
            for (PatternModel p : k.getModels()) {
                avgSpeed += p.time;

                if (p.type == Type.KeyRelease) {
                    onButton.add(new Double(p.time));
                } else {
                    betweenGestures.add(new Double(p.time));
                }

                accuracyX.add(new Double(p.relposx));
                accuracyY.add(new Double(p.relposy));
                if (p.charCode.equals(XMLConstants.BACKSPACE)) {
                    stat.averageTypoCount++;
                }

            }
        }

        stat.averageSpeed = avgSpeed / gestures.size();

        stat.deviationTouchAccuracyX = calculateDeviationSquare(accuracyX);
        stat.deviationTouchAccuracyX = calculateDeviationSquare(accuracyY);
        stat.deviationTimeBetweenReleaseAndPress = calculateDeviationSquare(betweenGestures);
        stat.deviationTimeOfButtonPress = calculateDeviationSquare(onButton);
    }

    public static double calculateDeviationSquare(List<Double> nums) {
        double sum = 0;
        for (double d : nums) {
                sum += d;
        }
        double avg = sum/nums.size();
        double value = 0;
        for (double d : nums) {
            value += (avg - d) * (avg - d);
        }

        return value;

    }
    public void startTraining() {
        gestures = new ArrayList<KeyPressCollector>();
        GestureStatistics stat = null;
    }
}
