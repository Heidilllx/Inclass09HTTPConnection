package com.example.inclass09httpconnection.parsers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.inclass09httpconnection.utils.EmailPO;


import java.util.ArrayList;

public class EmailParser {
    /**
     * @param in
     * @return
     * @throws
     */
    public static ArrayList<EmailPO> parseEmails(String in) {
        ArrayList<EmailPO> emails = new ArrayList();

        JSONObject root = JSONObject.parseObject(in);
        JSONArray rootJsonArray = root.getJSONArray("messages");

        for (int idx = 0; idx < rootJsonArray.size(); idx++) {
            JSONObject emailJson = rootJsonArray.getJSONObject(idx);

            EmailPO emailPO = JSONObject.parseObject(emailJson.toString(), EmailPO.class);

            emails.add(emailPO);
        }

        return emails;
    }
}
