package com.csappat.pre.biometrickeyboardid.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.csappat.pre.biometrickeyboardid.R;
import com.csappat.pre.biometrickeyboardid.xml.PatternModel;
import com.csappat.pre.biometrickeyboardid.xml.Type;

import java.util.Calendar;

/**
 * TODO: document your custom view class.
 */
public class SensorKey extends View  {

    private String key;
    private Drawable keyBackground;
    private Paint paint;
    private OnClickListener listener;
    private boolean active = false;
    private static boolean isUpperCase = false;

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

    public SensorKey(Context context) {
        super(context);
        setKeyBackground(getResources().getDrawable(R.drawable.key_background_inactive));
    }

    public SensorKey(Context context, AttributeSet attrs) {
        super(context, attrs);
        requestFocus();
        setKeyBackground(getResources().getDrawable(R.drawable.key_background_inactive));
        paint= new Paint();
        paint.setColor(getResources().getColor(R.color.white));
        this.setFocusable(true);
        this.setClickable(true);

    }

    public void changeUpperOrLowerCase(){
        if(isUpperCase) key.toLowerCase();
        else key.toUpperCase();
        isUpperCase=!isUpperCase;
        invalidate();
    }

    private void changeState(){
        if (active){
            setKeyBackground(getResources().getDrawable(R.drawable.key_background_inactive));
            paint.setColor(getResources().getColor(R.color.white));
            invalidate();
            active=!active;

        }
        else{
            setKeyBackground(getResources().getDrawable(R.drawable.key_background_active));
            paint.setColor(getResources().getColor(R.color.gray));
            invalidate();
            active=!active;

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
        paint.setTextSize(this.getHeight()/3);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
        try {
            canvas.drawText(key, xPos, yPos, paint);
        }catch(NullPointerException e ){};
    }

    //Click

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Type type;
        String charCode=key;
        long millisDown=0;
        long millisRelease=0;


        switch (charCode){
            case "←" : {
                charCode= "BACKSPACE";
                break;
            }
            case "↵" : {
                charCode= "ENTER";
                break;
            }
            case "⇧" : {
                charCode= "SHIFT";
                changeUpperOrLowerCase();
                break;
            }
            case " " : {
                charCode= "SPACE";
                break;
            }

        }
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if(listener != null) listener.onClick(this);
            changeState();
            type= Type.KeyDown;
            millisDown=  Calendar.getInstance().getTimeInMillis();


        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(listener != null) listener.onClick(this);
            changeState();
            type= Type.KeyRelease;
            millisRelease=Calendar.getInstance().getTimeInMillis();
            PatternModel reseaseModel= new PatternModel(type,(int)(millisRelease-millisDown),charCode,
                    (int)((event.getX()/this.getWidth())*100),(int)((event.getY()/this.getHeight()*100)));
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_UP && (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER || event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            if(listener != null) listener.onClick(this);
        }
        return super.dispatchKeyEvent(event);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

}
