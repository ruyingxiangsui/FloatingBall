package com.chenyee.stephenlau.floatingball.floatingBall;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Keep;

@Keep
public class FloatingBallPaint {

  private Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  private Paint ballPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  private Paint ballEmptyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


  public FloatingBallPaint() {
    backgroundPaint.setColor(Color.GRAY);
    backgroundPaint.setAlpha(80);

    ballPaint.setColor(Color.WHITE);
    ballPaint.setAlpha(150);

    PorterDuff.Mode mode = PorterDuff.Mode.CLEAR;
    ballEmptyPaint.setXfermode(new PorterDuffXfermode(mode));
  }

  public void setPaintAlpha(int opacity) {
    backgroundPaint.setAlpha(opacity);
    ballPaint.setAlpha(opacity);
  }

  public int getOpacity() {
    return backgroundPaint.getAlpha();
  }

  public Paint getBackgroundPaint() {
    return backgroundPaint;
  }

  public Paint getBallPaint() {
    return ballPaint;
  }

  public Paint getBallEmptyPaint() {
    return ballEmptyPaint;
  }
}
