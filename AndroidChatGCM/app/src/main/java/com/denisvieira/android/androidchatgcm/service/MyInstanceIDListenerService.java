package com.denisvieira.android.androidchatgcm.service;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by denisvieira on 02/05/16.
 */

// Função dele é permitir que atualize o token que está sendo utilizado no device e app, listener de refresh para gerar um novo token, questão de segurança .
public class MyInstanceIDListenerService extends InstanceIDListenerService{
    private static final String TAG = "LOG";

    @Override
    public void onTokenRefresh() {
//        super.onTokenRefresh();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putBoolean("status",false).apply();

        Intent it = new Intent(this, RegistrationIntentService.class);
        startService(it);

    }
}
