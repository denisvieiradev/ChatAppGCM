package com.denisvieira.android.androidchatgcm.network;

import com.denisvieira.android.androidchatgcm.domain.WrapObjToNetwork;

import org.json.JSONObject;

/**
 * Created by denisvieira on 02/05/16.
 */
public class Transaction {
    WrapObjToNetwork doBefore();

    void doAfter(JSONObject jsonObject);
}
