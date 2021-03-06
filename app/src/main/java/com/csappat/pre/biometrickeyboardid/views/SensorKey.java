package com.csappat.pre.biometrickeyboardid.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.csappat.pre.biometrickeyboardid.R;
import com.csappat.pre.biometrickeyboardid.logic.KeyPressCollector;
import com.csappat.pre.biometrickeyboardid.xml.PatternModel;
import com.csappat.pre.biometrickeyboardid.xml.Type;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * TODO: document your custom view class.
 */
public class SensorKey extends View {

    public static long lastKeyUp = 0;
    public static long millisDown = 0;
    private static boolean isUpperCase = false;
    private static List<SensorKey> keys = new ArrayList<SensorKey>();
    private static TextView editedView;
    KeyPressCollector collector = KeyPressCollector.getDefaultCollector();
    private String key;
    private Drawable keyBackground;
    private Paint paint;
    private OnClickListener listener;
    private boolean active = false;

    public SensorKey(Context context) {
        super(context);
        setKeyBackground(getResources().getDrawable(R.drawable.key_background_inactive));
    }

    public SensorKey(Context context, AttributeSet attrs) {
        super(context, attrs);
        requestFocus();
        setKeyBackground(getResources().getDrawable(R.drawable.key_background_inactive));
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.white));
        this.setFocusable(true);
        this.setClickable(true);

    }

    public static void setViewGroupAndEditedView(ArrayList<SensorKey> keys, TextView editedView) {
        SensorKey.keys = keys;
        SensorKey.editedView = editedView;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Drawable getKeyBackground() {
        return keyBackground;
    }

    public void setKeyBackground(Drawable keyBackground) {
        this.keyBackground = keyBackground;
    }

    public void changeUpperOrLowerCase() {
        Log.d("SensorKey", "change Upper and Lower");
        for (SensorKey k : keys) {
            if (isUpperCase) k.setKey(k.key.toLowerCase());
            else k.setKey(k.key.toUpperCase());


            k.invalidate();
        }
        isUpperCase = !isUpperCase;
        invalidate();
    }

    private void changeState() {
        if (active) {
            setKeyBackground(getResources().getDrawable(R.drawable.key_background_inactive));
            paint.setColor(getResources().getColor(R.color.white));
            invalidate();
            active = !active;

        } else {
            setKeyBackground(getResources().getDrawable(R.drawable.key_background_active));
            paint.setColor(getResources().getColor(R.color.gray));
            invalidate();
            active = !active;

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Background
        keyBackground.setBounds(0, 0, this.getWidth(), this.getHeight());
        keyBackground.draw(canvas);

        //Key
        paint.setStrokeWidth(2);
        paint.setTextSize(this.getHeight() / 3);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
        try {
            canvas.drawText(key, xPos, yPos, paint);
        } catch (NullPointerException e) {
        }
        ;
    }

    //Click
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Type type;
        String charCode = key;
        long millisRelease = 0;


        switch (charCode) {
            case "←": {
                charCode = "BACKSPACE";
                break;
            }
            case "↵": {
                charCode = "ENTER";
                break;
            }
            case "⇧": {
                charCode = "SHIFT";
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    changeUpperOrLowerCase();
                }
                break;
            }
            case " ": {
                charCode = "SPACE";
                break;
            }

        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (listener != null) listener.onClick(this);
            changeState();
            type = Type.KeyDown;
            millisDown = Calendar.getInstance().getTimeInMillis();
            long millisBetweenTwoIteraction = 0;
            if (lastKeyUp != 0) {
                millisBetweenTwoIteraction = Calendar.getInstance().getTimeInMillis() - lastKeyUp;
            }

            PatternModel keyDownModel = new PatternModel(type, (int) (millisBetweenTwoIteraction), charCode,
                    (int) ((event.getX() / this.getWidth()) * 100), (int) ((event.getY() / this.getHeight() * 100)));
            collector.keyPressed(keyDownModel);
            Log.d("SesorKey", "Gesture: " + keyDownModel.toString());
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (listener != null) listener.onClick(this);
            lastKeyUp = Calendar.getInstance().getTimeInMillis();
            changeState();
            type = Type.KeyRelease;
            millisRelease = Calendar.getInstance().getTimeInMillis();
            int millisOnKey = 0;
            if (millisDown != 0) {
                millisOnKey = (int) (millisRelease - millisDown);
            }
            PatternModel reseaseModel = new PatternModel(type, millisOnKey, charCode,
                    (int) ((event.getX() / this.getWidth()) * 100), (int) ((event.getY() / this.getHeight() * 100)));
            collector.keyPressed(reseaseModel);
            Log.d("SesorKey", "Gesture: " + reseaseModel.toString());
        }
        editedView.setText(collector.getCurrentString());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP && (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER || event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            if (listener != null) listener.onClick(this);
        }
        return super.dispatchKeyEvent(event);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

}
