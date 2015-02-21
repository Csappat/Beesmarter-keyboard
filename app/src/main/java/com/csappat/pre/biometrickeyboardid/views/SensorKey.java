package com.csappat.pre.biometrickeyboardid.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.csappat.pre.biometrickeyboardid.R;

/**
 * TODO: document your custom view class.
 */
public class SensorKey extends View implements View.OnClickListener {

    private String key;
    private Drawable keyBackground;

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
        setKeyBackground(getResources().getDrawable(R.drawable.key_background));
       // setKey("VIKI");
    }

    public SensorKey(Context context, AttributeSet attrs) {
        super(context, attrs);
        requestFocus();
        setKeyBackground(getResources().getDrawable(R.drawable.key_background));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Background
        keyBackground.setBounds(0, 0, this.getWidth(), this.getHeight());
        keyBackground.draw(canvas);

        //Key
        Paint paint= new Paint();
        paint.setColor(getResources().getColor(R.color.gray));
        paint.setStrokeWidth(4);
        paint.setTextSize(this.getHeight()-5);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
        canvas.drawText(key,xPos,yPos,paint);
    }

    @Override
    public void onClick(View v) {

    }
}
