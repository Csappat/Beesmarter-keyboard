package com.csappat.pre.biometrickeyboardid.logic;

import com.csappat.pre.biometrickeyboardid.xml.PatternModel;
import com.csappat.pre.biometrickeyboardid.xml.Type;

import java.util.ArrayList;

/**
 * Created by Patrik on 2015.02.21..
 */
public class KeyPressCollector {
    ArrayList<PatternModel> models = new ArrayList<PatternModel>();
    private boolean isUpperCase = false;

    public KeyPressCollector(boolean isUpperCaseForInitial) {
        isUpperCase = isUpperCaseForInitial;
    }

    public void keyPressed(PatternModel m) {
        models.add(m);
    }

    public String getCurrentString() {
        boolean isUpperCase = this.isUpperCase;
        StringBuilder builder = new StringBuilder();

        loop:
        for (PatternModel m : models) {
            if (m.type != Type.KeyRelease) {
                continue;
            }
            switch(m.charCode) {
                case XMLConstants.BACKSPACE:
                    builder.deleteCharAt(builder.length() - 1);
                    continue loop;
                case XMLConstants.ENTER:
                    builder.append("\n");
                    continue loop;
                case XMLConstants.SHIFT:
                    isUpperCase = !isUpperCase;
                    continue loop;
                case XMLConstants.SPACE:
                    builder.append(" ");
                    continue loop;
            }
            if (m.charCode.length() == 1) {
                if (isUpperCase) {
                    builder.append(m.charCode.toUpperCase());
                } else {
                    builder.append(m.charCode.toLowerCase());
                }
            }

        }
        return models.toString();
    }

}
