package com.denisvieira.android.androidchatgcm.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.denisvieira.android.androidchatgcm.conf.Configuration;
import com.denisvieira.android.androidchatgcm.domain.User;
import com.denisvieira.android.androidchatgcm.domain.WrapObjToNetwork;
import com.denisvieira.android.androidchatgcm.network.NetworkConnection;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by denisvieira on 02/05/16.
 */
public class RegistrationIntentService extends IntentService {
    public static final String LOG = "LOG";

    public RegistrationIntentService(){
        super(LOG);
    }

    @Override
    protected void onHandleIntent( Intent intent) {
//
//        int id = Integer.parseInt( Pref.retrievePrefKeyValue(getApplicationContext(),
//                Pref.PREF_KEY_ID,
//                "0") );
//
//        String nickname = Pref.retrievePrefKeyValue(getApplicationContext(),
//                Pref.PREF_KEY_NICKNAME);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean status = preferences.getBoolean("status",false);

        synchronized (LOG){
            InstanceID instanceID = InstanceID.getInstance( this );
            try {

                if( !status ){
                    String token = instanceID.getToken(Configuration.SENDER_ID,
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE,
                            null);
                    Log.i(LOG,"TOKEN: "+token);

//                    preferences.edit().putBoolean("status",token != null && token.trim().length() > 0).apply();

//                    sendRegistrationId(token, nickname);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
//
    private void sendRegistrationId( String token, String nickname ){
        User user = new User();
        user.setRegistrationId( token );
        user.setNickname( nickname );

        NetworkConnection
                .getInstance(this)
                .execute( new WrapObjToNetwork( user, User.METHOD_SAVE_USER ),
                        RegistrationIntentService.class.getName() );
    }

}
