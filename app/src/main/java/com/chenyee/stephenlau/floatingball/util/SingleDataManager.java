package com.chenyee.stephenlau.floatingball.util;

import static com.chenyee.stephenlau.floatingball.App.gScreenHeight;
import static com.chenyee.stephenlau.floatingball.App.gScreenWidth;
import static com.chenyee.stephenlau.floatingball.util.StaticStringUtil.*;

import android.content.SharedPreferences;

public class SingleDataManager {

  private SingleDataManager() {

  }

  public static void registerOnDataChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
    SharedPrefsUtils.getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
  }

  public static void unregisterOnDataChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
    SharedPrefsUtils.getSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener);
  }


  public static boolean isAddedBallInSetting() {
    return SharedPrefsUtils.getBooleanPreference(PREF_IS_ADDED_BALL_IN_SETTING, false);
  }

  public static void setIsAddedBallInSetting(boolean value) {
    SharedPrefsUtils.setBooleanPreference(PREF_IS_ADDED_BALL_IN_SETTING, value);
  }

  public static boolean isBallHideBecauseRotate() {
    return SharedPrefsUtils.getBooleanPreference(PREF_IS_BALL_HIDE_BECAUSE_ROTATE, false);
  }

  public static void setIsBallHideBecauseRotate(boolean value) {
    SharedPrefsUtils.setBooleanPreference(PREF_IS_BALL_HIDE_BECAUSE_ROTATE, value);
  }

  public static boolean isRotateHideSetting() {
    return SharedPrefsUtils.getBooleanPreference(PREF_IS_ROTATE_HIDE_SETTING, true);
  }

  public static void setIsRotateHideSetting(boolean value) {
    SharedPrefsUtils.setBooleanPreference(PREF_IS_ROTATE_HIDE_SETTING, value);
  }

  public static int opacity() {
    return SharedPrefsUtils.getIntegerPreference(PREF_OPACITY, 125);
  }

  public static void setOpacity(int value) {
    SharedPrefsUtils.setIntegerPreference(PREF_OPACITY, value);
  }

  public static int size() {
    return SharedPrefsUtils.getIntegerPreference(PREF_SIZE, 25);
  }

  public static void setSize(int value) {
    SharedPrefsUtils.setIntegerPreference(PREF_SIZE, value);
  }

  public static boolean isUseBackground() {
    return SharedPrefsUtils.getBooleanPreference(PREF_USE_BACKGROUND, false);
  }

  public static void setIsUseBackground(boolean value) {
    SharedPrefsUtils.setBooleanPreference(PREF_USE_BACKGROUND, value);
  }

  public static boolean isUseGrayBackground() {
    return SharedPrefsUtils.getBooleanPreference(PREF_USE_GRAY_BACKGROUND, true);
  }

  public static void setIsUseGrayBackground(boolean value) {
    SharedPrefsUtils.setBooleanPreference(PREF_USE_GRAY_BACKGROUND, value);
  }

  public static int singleTapEvent() {
    return SharedPrefsUtils.getIntegerPreference(PREF_SINGLE_TAP_EVENT, BACK);
  }

  public static void setsSingleTapEvent(int value) {
    SharedPrefsUtils.setIntegerPreference(PREF_SINGLE_TAP_EVENT, value);
  }

  public static int doubleClickEvent() {
    return SharedPrefsUtils.getIntegerPreference(PREF_DOUBLE_CLICK_EVENT, NONE);
  }

  public static void setDoubleClickEvent(int value) {
    SharedPrefsUtils.setIntegerPreference(PREF_DOUBLE_CLICK_EVENT, value);
  }

  public static int leftSlideEvent() {
    return SharedPrefsUtils.getIntegerPreference(PREF_LEFT_SLIDE_EVENT, RECENT_APPS);
  }

  public static void setLeftSlideEvent(int value) {
    SharedPrefsUtils.setIntegerPreference(PREF_LEFT_SLIDE_EVENT, value);
  }

  public static int rightSlideEvent() {
    return SharedPrefsUtils.getIntegerPreference(PREF_RIGHT_SLIDE_EVENT, RECENT_APPS);
  }

  public static void setRightSlideEvent(int value) {
    SharedPrefsUtils.setIntegerPreference(PREF_RIGHT_SLIDE_EVENT, value);
  }

  public static int upSlideEvent() {
    return SharedPrefsUtils.getIntegerPreference(PREF_UP_SLIDE_EVENT, HOME);
  }

  public static void setUpSlideEvent(int value) {
    SharedPrefsUtils.setIntegerPreference(PREF_UP_SLIDE_EVENT, value);
  }

  public static int downSlideEvent() {
    return SharedPrefsUtils.getIntegerPreference(PREF_DOWN_SLIDE_EVENT, NOTIFICATION);
  }

  public static void setDownSlideEvent(int value) {
    SharedPrefsUtils.setIntegerPreference(PREF_DOWN_SLIDE_EVENT, value);
  }

  public static int moveUpDistance() {
    return SharedPrefsUtils.getIntegerPreference(PREF_MOVE_UP_DISTANCE, 8);
  }

  public static void setMoveUpDistance(int value) {
    SharedPrefsUtils.setIntegerPreference(PREF_MOVE_UP_DISTANCE, value);
  }

  public static boolean isVibrate() {
    return SharedPrefsUtils.getBooleanPreference(PREF_IS_VIBRATE, true);
  }

  public static void setIsVibrate(boolean value) {
    SharedPrefsUtils.setBooleanPreference(PREF_IS_VIBRATE, value);
  }

  public static boolean isAvoidKeyboard() {
    return SharedPrefsUtils.getBooleanPreference(PREF_IS_AVOID_KEYBOARD, true);
  }

  public static boolean setIsAvoidKeyboard(boolean value) {
    return SharedPrefsUtils.setBooleanPreference(PREF_IS_AVOID_KEYBOARD, value);
  }

  //Position
  //landscape
  public static int floatingBallLandscapeX(int idCode) {
    return SharedPrefsUtils.getIntegerPreference(PREF_PARAM_X_LANDSCAPE + idCode, gScreenWidth / 2);
  }

  public static void setFloatingBallLandscapeX(int x, int idCode) {
    SharedPrefsUtils.setIntegerPreference(PREF_PARAM_X_LANDSCAPE + idCode, x);
  }

  public static int floatingBallLandscapeY(int idCode) {
    return SharedPrefsUtils.getIntegerPreference(PREF_PARAM_Y_LANDSCAPE + idCode, gScreenHeight / 2);
  }

  public static void setFloatingBallLandscapeY(int y, int idCode) {
    SharedPrefsUtils.setIntegerPreference(PREF_PARAM_Y_LANDSCAPE + idCode, y);
  }

  //portrait
  public static int floatingBallPortraitX(int idCode) {
    return SharedPrefsUtils.getIntegerPreference(PREF_PARAM_X_PORTRAIT + idCode, gScreenWidth / 2);
  }

  public static void setFloatingBallPortraitX(int x, int idCode) {

    SharedPrefsUtils.setIntegerPreference(PREF_PARAM_X_PORTRAIT + idCode, x);
  }

  public static int floatingBallPortraitY(int idCode) {
    return SharedPrefsUtils.getIntegerPreference(PREF_PARAM_Y_PORTRAIT + idCode, gScreenHeight / 2);
  }

  public static void setFloatingBallPortraitY(int y, int idCode) {
    SharedPrefsUtils.setIntegerPreference(PREF_PARAM_Y_PORTRAIT + idCode, y);
  }

  public static int amount() {
    return SharedPrefsUtils.getIntegerPreference(PREF_AMOUNT ,1);
  }

  public static void setAmount(int amount) {
    SharedPrefsUtils.setIntegerPreference(PREF_AMOUNT ,amount);
  }
}
