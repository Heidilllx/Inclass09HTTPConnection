package com.example.inclass09httpconnection.parsers;

import com.example.inclass09httpconnection.utils.LoginPO;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginSignupJsonParser {
    public static LoginPO parseLogin(String in) throws JSONException {
        JSONObject root = new JSONObject(in);
        LoginPO logInDetails = new LoginPO();
        if (root.has("auth"))
            logInDetails.setAuth(root.getBoolean("auth"));
        if (root.has("token"))
            logInDetails.setToken(root.getString("token"));
        return logInDetails;
    }
}
