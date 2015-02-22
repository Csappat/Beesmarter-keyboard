package com.csappat.pre.biometrickeyboardid;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.csappat.pre.biometrickeyboardid.logic.KeyPressCollector;
import com.csappat.pre.biometrickeyboardid.logic.PasswordGestureCollector;
import com.csappat.pre.biometrickeyboardid.views.SensorKey;
import com.csappat.pre.biometrickeyboardid.xml.PatternModel;

import java.util.ArrayList;


public class PasswordActivity extends ActionBarActivity {

    private ArrayList<SensorKey> keys;

    boolean isTraining = false;
    private int trainingCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        keys = new ArrayList<SensorKey>();
        SensorKey _0 = (SensorKey) findViewById(R.id._0);
        _0.setKey("0");
        keys.add(_0);
        SensorKey _1 = (SensorKey) findViewById(R.id._1);
        _1.setKey("1");
        keys.add(_1);
        SensorKey _2 = (SensorKey) findViewById(R.id._2);
        _2.setKey("2");
        keys.add(_2);
        SensorKey _3 = (SensorKey) findViewById(R.id._3);
        _3.setKey("3");
        keys.add(_3);
        SensorKey _4 = (SensorKey) findViewById(R.id._4);
        _4.setKey("4");
        keys.add(_4);
        SensorKey _5 = (SensorKey) findViewById(R.id._5);
        _5.setKey("5");
        keys.add(_5);
        SensorKey _6 = (SensorKey) findViewById(R.id._6);
        _6.setKey("6");
        keys.add(_6);
        SensorKey _7 = (SensorKey) findViewById(R.id._7);
        _7.setKey("7");
        keys.add(_7);
        SensorKey _8 = (SensorKey) findViewById(R.id._8);
        _8.setKey("8");
        keys.add(_8);
        SensorKey _9 = (SensorKey) findViewById(R.id._9);
        _9.setKey("9");
        keys.add(_9);
        SensorKey q = (SensorKey) findViewById(R.id.q);
        q.setKey("q");
        keys.add(q);
        SensorKey w = (SensorKey) findViewById(R.id.w);
        w.setKey("w");
        keys.add(w);
        SensorKey e = (SensorKey) findViewById(R.id.e);
        e.setKey("e");
        keys.add(e);
        SensorKey r = (SensorKey) findViewById(R.id.r);
        r.setKey("r");
        keys.add(r);
        SensorKey t = (SensorKey) findViewById(R.id.t);
        t.setKey("t");
        keys.add(t);
        SensorKey z = (SensorKey) findViewById(R.id.z);
        z.setKey("z");
        keys.add(z);
        SensorKey u = (SensorKey) findViewById(R.id.u);
        u.setKey("u");
        keys.add(u);
        SensorKey i = (SensorKey) findViewById(R.id.i);
        i.setKey("i");
        keys.add(i);
        SensorKey o = (SensorKey) findViewById(R.id.o);
        o.setKey("o");
        keys.add(o);
        SensorKey p = (SensorKey) findViewById(R.id.p);
        p.setKey("p");
        keys.add(p);
        SensorKey backspace = (SensorKey) findViewById(R.id.backspace);
        backspace.setKey("←");
        keys.add(backspace);
        SensorKey a = (SensorKey) findViewById(R.id.a);
        a.setKey("a");
        keys.add(a);
        SensorKey s = (SensorKey) findViewById(R.id.s);
        s.setKey("s");
        keys.add(s);
        SensorKey d = (SensorKey) findViewById(R.id.d);
        d.setKey("d");
        keys.add(d);
        SensorKey f = (SensorKey) findViewById(R.id.f);
        f.setKey("f");
        keys.add(f);
        SensorKey g = (SensorKey) findViewById(R.id.g);
        g.setKey("g");
        keys.add(g);
        SensorKey h = (SensorKey) findViewById(R.id.h);
        h.setKey("h");
        keys.add(h);
        SensorKey j = (SensorKey) findViewById(R.id.j);
        j.setKey("j");
        keys.add(j);
        SensorKey k = (SensorKey) findViewById(R.id.k);
        k.setKey("k");
        keys.add(k);
        SensorKey l = (SensorKey) findViewById(R.id.l);
        l.setKey("l");
        keys.add(l);
        SensorKey enter = (SensorKey) findViewById(R.id.enter);
        enter.setKey("↵");
        keys.add(enter);
        SensorKey shift = (SensorKey) findViewById(R.id.shift);
        shift.setKey("⇧");
        keys.add(shift);
        SensorKey y = (SensorKey) findViewById(R.id.y);
        y.setKey("y");
        keys.add(y);
        SensorKey x = (SensorKey) findViewById(R.id.x);
        x.setKey("x");
        keys.add(x);
        SensorKey c = (SensorKey) findViewById(R.id.c);
        c.setKey("c");
        keys.add(c);
        SensorKey v = (SensorKey) findViewById(R.id.v);
        v.setKey("v");
        keys.add(v);
        SensorKey b = (SensorKey) findViewById(R.id.b);
        b.setKey("b");
        keys.add(b);
        SensorKey n = (SensorKey) findViewById(R.id.n);
        n.setKey("n");
        keys.add(n);
        SensorKey m = (SensorKey) findViewById(R.id.m);
        m.setKey("m");
        keys.add(m);
        SensorKey comma = (SensorKey) findViewById(R.id.comma);
        comma.setKey(",");
        keys.add(comma);
        SensorKey dot = (SensorKey) findViewById(R.id.dot);
        dot.setKey(".");
        keys.add(dot);
        SensorKey rshift = (SensorKey) findViewById(R.id.rshift);
        rshift.setKey("⇧");
        keys.add(rshift);
        SensorKey space = (SensorKey) findViewById(R.id.space);
        space.setKey(" ");
        keys.add(space);

        SensorKey.setViewGroupAndEditedView(keys, (TextView)findViewById(R.id.editable) );
    }

    private boolean trial = false;

    public void startTraining(View v) {
        Button trainingButton = (Button)v;
        if (trainingCount > 2 && trial == false) {
            trainingCount = 0;
            trial = true;
            trainingButton.setText("Send");
            return;
        }
        if (trial == true)  {
            KeyPressCollector colForTrial = new KeyPressCollector(false);
            for (PatternModel p : KeyPressCollector.getDefaultCollector().getModels()) {
                colForTrial.keyPressed(p);
            }
            Log.d("IsItYou: ",  "" + PasswordGestureCollector.getGesturesFromKeyboardTraining().isItYou(colForTrial));
            Toast.makeText(this, "Is it you? "
                    + PasswordGestureCollector.getGesturesFromKeyboardTraining().isItYou(colForTrial), Toast.LENGTH_LONG).show();
            trainingButton.setText("Start training");
            trial = false;

            return;
        }
        if (!isTraining) {
            isTraining = true;
            trainingButton.setText("Next pattern");
            PasswordGestureCollector.resetKeyBoardTrainingGestures();
            trainingCount = 0;
            SensorKey.millisDown = 0;
            SensorKey.lastKeyUp = 0;
        } else {
            KeyPressCollector col = new KeyPressCollector(false);
            for (PatternModel p : KeyPressCollector.getDefaultCollector().getModels()) {
                col.keyPressed(p);
            }
            KeyPressCollector.resetDefaultCollector();
            PasswordGestureCollector.addGestureFromKeyboard(col);
            trainingCount++;
            if (trainingCount == 2) {
                trainingButton.setText("Finish training");
            }
            if (trainingCount > 2) {
                isTraining = false;
                Log.d("PasswordActivity", "Gestures " + PasswordGestureCollector.getGesturesFromKeyboardTraining().toString());
                PasswordGestureCollector.getGesturesFromKeyboardTraining().finishTraining();
                trainingButton.setText("Start trial");
                return;
            }

        }
    }

}
