package com.example.test3;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.LinkedList;

public class ReadDataHandler extends Handler {

    private String json;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }


}
