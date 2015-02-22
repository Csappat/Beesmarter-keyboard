package com.csappat.pre.biometrickeyboardid.xml;

/**
 * Created by User on 2015.02.21..
 */
public class PatternModel {
    public final Type type; //
    public final int time; //millisec
    public final String charCode; //charachter code
    public final int relposx; // %
    public final int relposy; // %

    public PatternModel(Type type, int time, String charCode, int relposx, int relposy) {
        this.type = type;
        this.time = time;
        this.charCode = charCode;
        this.relposx = relposx;
        this.relposy = relposy;
    }

    @Override
    public String toString() {
        return type.toString() + " " + time + " " + charCode + " " + relposx + " " + relposy;
    }
}
