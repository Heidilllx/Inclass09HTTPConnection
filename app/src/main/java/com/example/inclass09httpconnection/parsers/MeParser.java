package com.example.inclass09httpconnection.parsers;

import com.example.inclass09httpconnection.utils.MePO;

import org.json.JSONException;
import org.json.JSONObject;

public class MeParser {
    public static MePO parseMe(String in) throws JSONException {
        JSONObject root = new JSONObject(in);
        MePO meDetails = new MePO();
        if (root.has("name"))
            meDetails.setUser_name(root.getString("name"));
        return meDetails;
    }
}
