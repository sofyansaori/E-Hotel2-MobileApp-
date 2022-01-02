package com.example.mobilehotelgroup2.pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mobilehotelgroup2.models.LoginModelResponse;

public class PrefHelper {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public PrefHelper(Context context) {
        pref = context.getSharedPreferences("prefHotel", Context.MODE_PRIVATE);
    }

    public void saveUser(LoginModelResponse userData) {
        editor = pref.edit();
        editor.putString("userId", userData.getIdUser());
        editor.putString("username", userData.getUsername());
        editor.commit();
    }

    public void removeUser() {
        editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    public LoginModelResponse getUser() {
        return new LoginModelResponse(
                pref.getString("userId", null),
                pref.getString("username", null),
                ""
        );
    }

}
