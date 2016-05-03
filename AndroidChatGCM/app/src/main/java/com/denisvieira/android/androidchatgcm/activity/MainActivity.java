package com.denisvieira.android.androidchatgcm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.denisvieira.android.androidchatgcm.R;
import com.denisvieira.android.androidchatgcm.domain.PushMessage;
import com.denisvieira.android.androidchatgcm.service.RegistrationIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    private static final String SERVER_API_KEY = "AIzaSyD7PdFI77mT9aXM6phYwKvBN4zMyLqzF8I";
    private static final String SENDER_ID = "";

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private TextView tvTitle;
    private TextView tvMessage;
    private Button btCallSActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvMessage = (TextView) findViewById(R.id.tv_message);

        if( checkPlayServices()){
            Intent it = new Intent(this, RegistrationIntentService.class);
            startService(it);
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("LOG", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    // LISTENER
    public void onEvent( final PushMessage pushMessage ){
        // garantir que ele não vai atualizar uma entidade de visualização fora da thread principal
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvTitle.setText( pushMessage.getTitle() );
                tvMessage.setText( pushMessage.getMessage() );
            }
        });
    }
}
