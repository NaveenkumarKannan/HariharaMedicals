package com.example.harihara_medicals.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.example.harihara_medicals.Model.User;

public class SharedPreferencesManager {

    //this will contains the app preferences
    private static SharedPreferences mSharedPref;

    synchronized public static void init(Context context) {
        if (mSharedPref == null)
            mSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    //save user username locally to show it his profile
    public static void saveMyUsername(String username) {
        mSharedPref.edit().putString("username", username).apply();
    }

    //save user photo path locally to show it his profile
    public static void saveMyPhoto(String path) {
        mSharedPref.edit().putString("user_image", path).apply();
    }

    //save user phone number locally to show it his profile
    public static void savePhoneNumber(String phoneNumber) {
        mSharedPref.edit().putString("phone_number", phoneNumber).apply();
    }


    public static String getUserName() {
        return mSharedPref.getString("username", "");
    }

    public static String getPhoneNumber() {
        return mSharedPref.getString("phone_number", "");
    }

    public static String getMyPhoto() {
        return mSharedPref.getString("user_image", "");
    }


    //check if user enabled vibration for notifications
    public static boolean isVibrateEnabled() {
        return mSharedPref.getBoolean("notifications_new_message_vibrate", false);
    }

    //get notification ringtone
    public static Uri getRingtone() {
        return Uri.parse(mSharedPref.getString("notifications_new_message_ringtone", "content://settings/system/notification_sound"));
    }

    //check if user enabled notifications
    public static boolean isNotificationEnabled() {
        return mSharedPref.getBoolean("notifications_new_message", true);
    }


    //check if user info is saved when launch the app for first time
    public static boolean isUserInfoSaved() {
        return mSharedPref.getBoolean("is_userInfo_saved", false);
    }

    public static void setUserInfoSaved(boolean bool) {
        mSharedPref.edit().putBoolean("is_userInfo_saved", bool).apply();
    }

    //save country code to use it late when formatting the numbers
    public static void saveCountryCode(String phoneNumber) {
        mSharedPref.edit().putString("ccode", phoneNumber).apply();
    }

    public static void saveMyThumbImg(String thumbImg) {
        mSharedPref.edit().putString("thumbImg", thumbImg).apply();
    }

    public static String getThumbImg() {
        return mSharedPref.getString("thumbImg", "");
    }

    public static String getCountryCode() {
        return mSharedPref.getString("ccode", "");
    }


    // set notification token as saved to prevent resending it to server
    public static void setTokenSaved(boolean bool) {
        mSharedPref.edit().putBoolean("token_sent", bool).apply();
    }

    public static boolean isTokenSaved() {
        return mSharedPref.getBoolean("token_sent", false);
    }


    public static User getCurrentUser() {
        User user = new User();
        user.setUid(FireManager.getUid());
        user.setThumbImg(SharedPreferencesManager.getThumbImg());
        user.setPhoto("");
        user.setPhone(SharedPreferencesManager.getPhoneNumber());
        user.setUserName(SharedPreferencesManager.getUserName());
        user.setUserLocalPhoto(SharedPreferencesManager.getMyPhoto());
        return user;
    }

    public static void setFetchUserGroupsSaved(boolean b) {
        mSharedPref.edit().putBoolean("fetch_user_groups_saved", b).apply();
    }

    public static boolean isFetchedUserGroups() {
        return mSharedPref.getBoolean("fetch_user_groups_saved", false);
    }

    public static void setAppVersionSaved(boolean b) {
        mSharedPref.edit().putBoolean("is_app_ver_saved", b).apply();
    }

    public static boolean isAppVersionSaved() {
        return mSharedPref.getBoolean("is_app_ver_saved", false);
    }

    //used to determine whether the UID,and Phone number were saved
    public static void setCurrentUserInfoSaved(boolean value) {
        mSharedPref.edit().putBoolean("currentUserInfoSaved", value).apply();
    }

    public static boolean isCurrentUserInfoSaved() {
        return mSharedPref.getBoolean("currentUserInfoSaved", false);
    }

    private static final int INVALID_SHOT_ID = -1;

    public static long shotId = INVALID_SHOT_ID;

    public static boolean hasShot() {
        return isSingleShot() && mSharedPref.getBoolean("hasShot" + shotId, false);
    }

    public static boolean isSingleShot() {
        return shotId != INVALID_SHOT_ID;
    }

    public static void storeShot() {
        if (isSingleShot()) {
            mSharedPref.edit().putBoolean("hasShot" + shotId, true).apply();
        }
    }

    public static void saveFirstTimeLogin() {
        mSharedPref.edit().putBoolean("IsLoggedIn", true).apply();
    }
    /**
     * Quick check for login
     * **/
    // Get Login State
    public static boolean isLoggedIn(){
        return mSharedPref.getBoolean("IsLoggedIn", false);
    }

    public static void logoutUser(){
        // Clearing all data from Shared Preferences
        mSharedPref.edit().clear().apply();
    }
}
