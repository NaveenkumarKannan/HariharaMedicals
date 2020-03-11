package com.example.harihara_medicals.utils;

import com.google.firebase.auth.FirebaseAuth;

public class FireManager {
    //is this user is logged in
    public static boolean isLoggedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    //get this user's uid
    public static String getUid() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        return null;
    }

    //get this user's phone number
    public static String getPhoneNumber() {
        //if (FirebaseAuth.getInstance().getCurrentUser() != null)
            return FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        //return null;
    }
}
