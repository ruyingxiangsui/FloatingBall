package com.chenyee.stephenlau.floatingball.floatingBall;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Keep;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.chenyee.stephenlau.floatingball.App;
import com.chenyee.stephenlau.floatingball.R;
import com.chenyee.stephenlau.floatingball.floatingBall.gesture.FloatingBallGestureListener;
import com.chenyee.stephenlau.floatingball.floatingBall.gesture.OnGestureEventListener;
import com.chenyee.stephenlau.floatingball.util.FunctionInterfaceUtils;
import com.chenyee.stephenlau.floatingball.util.InputMethodDetector;
import com.chenyee.stephenlau.floatingball.util.SharedPrefsUtils;
import com.chenyee.stephenlau.floatingball.util.SingleDataManager;

import static com.chenyee.stephenlau.floatingball.App.gScreenHeight;
import static com.chenyee.stephenlau.floatingball.App.gScreenWidth;
import static com.chenyee.stephenlau.floatingball.util.StaticStringUtil.*;

/**
 * Created by stephenlau on 2017/12/5.
 */
@Keep
public class FloatingBallView extends View implements OnGestureEventListener {

  private static final String TAG = FloatingBallView.class.getSimpleName();

  private FloatingBallGestureListener floatingBallGestureListener;

  private FloatingBallPaint floatingBallPaint;

  private FloatingBallDrawer floatingBallDrawer;

  private FloatingBallAnimator floatingBallAnimator;

  public int getIdCode() {
    return idCode;
  }

  private int idCode;

  //function list
  public FunctionListener singleTapFunctionListener;
  private FunctionListener downFunctionListener;
  private FunctionListener upFunctionListener;
  private FunctionListener leftFunctionListener;
  private FunctionListener rightFunctionListener;
  public FunctionListener doubleTapFunctionListener;

  //标志位
  private boolean useBackground = false;

  public boolean isUseDoubleClick = false;


  private GestureDetector gestureDetector;

  private WindowManager windowManager;

  private Bitmap mBitmapRead;
  private Bitmap mBitmapScaled;

  private int userSetOpacity = 125;

  private int opacityMode;


  private WindowManager.LayoutParams ballViewLayoutParams;

  private int lastLayoutParamsY;

  private int layoutParamsY;//用于使用反射

  private int keyboardTopY;

  private int moveUpDistance;

  public LayoutParams getBallViewLayoutParams() {
    return ballViewLayoutParams;
  }

  public void setBallViewLayoutParams(LayoutParams ballViewLayoutParams) {
    this.ballViewLayoutParams = ballViewLayoutParams;
  }

  public int getLayoutParamsY() {
    return ballViewLayoutParams.y;
  }

  public void setLayoutParamsY(int y) {
    ballViewLayoutParams.y = y;
    windowManager.updateViewLayout(FloatingBallView.this, ballViewLayoutParams);
  }

  public void setUseBackground(boolean useBackground) {
    if (useBackground) {
      refreshBitmapRead();
      createBitmapCropFromBitmapRead();
    } else {
      recycleBitmap();
    }

    this.useBackground = useBackground;
  }

  public void recycleBitmap() {
    if (mBitmapRead != null && !mBitmapRead.isRecycled()) {
      mBitmapRead.recycle();
    }
    if (!useBackground && mBitmapScaled != null && !mBitmapScaled.isRecycled()) {
      mBitmapScaled.recycle();
    }
  }

  public void setDoubleClickEventType(int doubleClickEventType) {
    doubleTapFunctionListener = FunctionInterfaceUtils.getListener(doubleClickEventType);

    isUseDoubleClick = doubleTapFunctionListener != FunctionInterfaceUtils.getListener(NONE);

    if (isUseDoubleClick) {
      gestureDetector.setOnDoubleTapListener(floatingBallGestureListener);
    } else {
      gestureDetector.setOnDoubleTapListener(null);
    }
  }

  /**
   * 设置透明度的模式，设置完后需要立刻刷新起效。
   */
  public void setOpacityMode(int mOpacityMode) {
    this.opacityMode = mOpacityMode;
    refreshOpacityMode();
  }

  private void refreshOpacityMode() {
    if (opacityMode == OPACITY_NONE) {
      floatingBallPaint.setPaintAlpha(userSetOpacity);
    }

    if (opacityMode == OPACITY_REDUCE) {
      floatingBallAnimator.setUpReduceAnimator(userSetOpacity);
      floatingBallAnimator.startReduceOpacityAnimator();
    }

    if (opacityMode == OPACITY_BREATHING) {
      floatingBallAnimator.setUpBreathingAnimator(userSetOpacity);
      floatingBallAnimator.startBreathingAnimator();
    } else {
      floatingBallAnimator.cancelBreathingAnimator();
    }
  }

  public int getOpacityMode() {
    return opacityMode;
  }


  public FloatingBallAnimator getFloatingBallAnimator() {
    return floatingBallAnimator;
  }

  public void setOpacity(int opacity) {
    floatingBallPaint.setPaintAlpha(opacity);

    userSetOpacity = opacity;

    refreshOpacityMode();
  }

  public void setDownFunctionListener(int downFunctionListenerType) {
    this.downFunctionListener = FunctionInterfaceUtils.getListener(downFunctionListenerType);
  }

  public void setUpFunctionListener(int upFunctionListenerType) {
    this.upFunctionListener = FunctionInterfaceUtils.getListener(upFunctionListenerType);
  }

  public void setLeftFunctionListener(int leftFunctionListenerType) {
    this.leftFunctionListener = FunctionInterfaceUtils.getListener(leftFunctionListenerType);
  }

  public void setRightFunctionListener(int rightFunctionListenerType) {
    this.rightFunctionListener = FunctionInterfaceUtils.getListener(rightFunctionListenerType);
  }

  public void setSingleTapFunctionListener(int singleTapFunctionListenerType) {
    this.singleTapFunctionListener = FunctionInterfaceUtils.getListener(singleTapFunctionListenerType);
  }

  public FloatingBallDrawer getFloatingBallDrawer() {
    return floatingBallDrawer;
  }

  public void setFloatingBallDrawer(FloatingBallDrawer floatingBallDrawer) {
    this.floatingBallDrawer = floatingBallDrawer;
  }

  public FloatingBallPaint getFloatingBallPaint() {
    return floatingBallPaint;
  }

  public void setFloatingBallPaint(FloatingBallPaint floatingBallPaint) {
    this.floatingBallPaint = floatingBallPaint;
  }

  public int getMeasureLength() {
    if (floatingBallAnimator != null) {
      return floatingBallDrawer.measuredSideLength;
    } else {
      return 0;
    }
  }

  /**
   * 构造函数
   */
  public FloatingBallView(Context context, int idCode) {
    super(context);
    this.idCode = idCode;

    floatingBallPaint = new FloatingBallPaint();

    floatingBallDrawer = new FloatingBallDrawer(this, floatingBallPaint);

    floatingBallAnimator = new FloatingBallAnimator(this, floatingBallDrawer);

    floatingBallAnimator.performAddAnimator();

    changeFloatBallSizeWithRadius(25);

    windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

    floatingBallGestureListener = new FloatingBallGestureListener(this);
    floatingBallGestureListener.setOnGestureEventListener(this);

    gestureDetector = new GestureDetector(context, floatingBallGestureListener);

    refreshOpacityMode();
  }

  public void setLayoutPositionParamsAndSave(int x, int y) {
    if (windowManager != null) {
      ballViewLayoutParams.x = x;
      ballViewLayoutParams.y = y;

      windowManager.updateViewLayout(FloatingBallView.this, ballViewLayoutParams);

      //不是很耗性能，直接保存。当然也可以长按结束的时候再进行保存。
      saveLayoutParams();
    }
  }

  private void saveLayoutParams() {
    Configuration configuration = App.getApplication().getResources().getConfiguration(); //获取设置的配置信息
    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
      SingleDataManager.setFloatingBallLandscapeX(ballViewLayoutParams.x, idCode);
      SingleDataManager.setFloatingBallLandscapeY(ballViewLayoutParams.y, idCode);
    } else if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
      SingleDataManager.setFloatingBallPortraitX(ballViewLayoutParams.x, idCode);
      SingleDataManager.setFloatingBallPortraitY(ballViewLayoutParams.y, idCode);
    }
  }

  /**
   * 取数据更新
   */
  public void updateLayoutParamsWithOrientation() {
    if (windowManager != null) {

      Configuration configuration = App.getApplication().getResources().getConfiguration();
      int orientation = configuration.orientation;

      int x = 0, y = 0;
      if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        x = SingleDataManager.floatingBallLandscapeX(idCode);
        y = SingleDataManager.floatingBallLandscapeY(idCode);

      } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        x = SingleDataManager.floatingBallPortraitX(idCode);
        y = SingleDataManager.floatingBallPortraitY(idCode);
      }

      ballViewLayoutParams.x = x;
      ballViewLayoutParams.y = y;
      windowManager.updateViewLayout(FloatingBallView.this, ballViewLayoutParams);
    }
  }

  /**
   * 改变悬浮球大小，需要改变所有与Size相关的东西
   */
  public void changeFloatBallSizeWithRadius(int ballRadius) {

    floatingBallDrawer.calculateBackgroundRadiusAndMeasureSideLength(ballRadius);

    if (useBackground) {
      createBitmapCropFromBitmapRead();
    }

    floatingBallAnimator.setUpTouchAnimator(ballRadius);
  }

  /**
   * 更新bitmapRead的值
   */
  public void refreshBitmapRead() {
    //path为app内部目录
    String path = getContext().getFilesDir().toString();
    mBitmapRead = BitmapFactory.decodeFile(path + "/ballBackground.png");

    //读取不成功就取默认图片
    if (mBitmapRead == null) {
      Resources res = getResources();
      mBitmapRead = BitmapFactory.decodeResource(res, R.drawable.joe_big);
    }
  }


  public void createBitmapCropFromBitmapRead() {
    if (floatingBallDrawer.ballRadius <= 0) {
      return;
    }
    //bitmapRead可能已被回收
    if (mBitmapRead == null || mBitmapRead.isRecycled()) {
      refreshBitmapRead();
    }

    int edge = (int) floatingBallDrawer.ballRadius * 2;

    Bitmap scaledBitmap = Bitmap.createScaledBitmap(mBitmapRead, edge, edge, true);
    //进行裁切后的bitmapCrop
    mBitmapScaled = Bitmap.createBitmap(edge, edge, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(mBitmapScaled);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //x y r
    canvas
        .drawCircle(floatingBallDrawer.ballRadius, floatingBallDrawer.ballRadius, floatingBallDrawer.ballRadius, paint);
    paint.reset();
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    canvas.drawBitmap(scaledBitmap, 0, 0, paint);

    scaledBitmap.recycle();
  }


  public void removeBallWithAnimation() {
    floatingBallAnimator.performRemoveAnimatorWithEndAction(new Runnable() {
      @Override
      public void run() {
        removeBallWithoutAnimation();
      }
    });
  }

  private void removeBallWithoutAnimation() {
    if (windowManager != null) {
      windowManager.removeViewImmediate(this);
    }
  }

  private boolean isBallMoveUp = false;
  private boolean isKeyboardShow = false;

  /**
   * 移动到键盘顶部，移动前记录
   */
  public void moveToKeyboardTop() {
    isKeyboardShow = true;

    // 计算键盘顶部Y坐标
    keyboardTopY = gScreenHeight - InputMethodDetector.inputMethodWindowHeight;

    int ballBottomYPlusGap = getLayoutParamsY() + getMeasureLength() + moveUpDistance;

    if (ballBottomYPlusGap >= keyboardTopY) {//球在键盘下方
      lastLayoutParamsY = getLayoutParamsY();

      floatingBallAnimator.startParamsYAnimationTo(keyboardTopY - getMeasureLength() - moveUpDistance);
      isBallMoveUp = true;
    }
  }

  public void moveBackWhenKeyboardDisappear() {
    isKeyboardShow = false;

    if (isBallMoveUp) {
      isBallMoveUp = false;

      floatingBallAnimator.startParamsYAnimationTo(lastLayoutParamsY);
    }

  }


  /**
   * 绘制
   */
  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    floatingBallDrawer.drawBallWithThisModel(canvas);

    Paint ballPaint = floatingBallPaint.getBallPaint();

    if (useBackground) {
      canvas.drawBitmap(mBitmapScaled, -mBitmapScaled.getWidth() / 2 + floatingBallDrawer.ballCenterX,
          -mBitmapScaled.getHeight() / 2 + floatingBallDrawer.ballCenterY, ballPaint);
    }
  }

  /**
   * 布局，改变View的大小
   */
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    setMeasuredDimension(getMeasureLength(), getMeasureLength());
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    gestureDetector.onTouchEvent(event);

    floatingBallGestureListener.processEvent(event);

    return true;
  }


  public void updateModelData() {
    floatingBallDrawer.updateFieldBySingleDataManager();
    floatingBallGestureListener.updateFieldBySingleDataManager();
    moveUpDistance = SingleDataManager.moveUpDistance();
  }

  @Override
  public void onActionDown() {
    floatingBallAnimator.startOnTouchAnimator();

    if (opacityMode == OPACITY_REDUCE) {
      floatingBallPaint.setPaintAlpha(userSetOpacity);
    }

  }

  @Override
  public void onActionUp() {
    floatingBallAnimator.startUnTouchAnimator();

    if (opacityMode == OPACITY_REDUCE) {
      floatingBallAnimator.startReduceOpacityAnimator();
    }
  }

  public void onFunctionWithCurrentGestureState(int currentGestureState) {
    switch (currentGestureState) {
      case FloatingBallGestureListener.STATE_UP:
        if (upFunctionListener != null) {
          upFunctionListener.onFunction();
        }
        break;
      case FloatingBallGestureListener.STATE_DOWN:
        if (upFunctionListener != null) {
          downFunctionListener.onFunction();
        }
        break;
      case FloatingBallGestureListener.STATE_LEFT:
        if (leftFunctionListener != null) {
          leftFunctionListener.onFunction();
        }
        break;
      case FloatingBallGestureListener.STATE_RIGHT:
        if (rightFunctionListener != null) {
          rightFunctionListener.onFunction();
        }
        break;
      case FloatingBallGestureListener.STATE_NONE:
        break;
    }
  }

  @Override
  public void onScrollEnd() {
    //球移动动画
    floatingBallAnimator.moveFloatBallBack();
  }

  @Override
  public void onLongPressEnd() {
    if (isKeyboardShow) {

      int ballBottomYPlusGap = getLayoutParamsY() + getMeasureLength() + moveUpDistance;
      if (ballBottomYPlusGap >= keyboardTopY) { //球在键盘下方
        lastLayoutParamsY = getLayoutParamsY();
        floatingBallAnimator.startParamsYAnimationTo(keyboardTopY - getMeasureLength() - moveUpDistance);
      } else {
        isBallMoveUp = false;
      }
    }
  }
}
