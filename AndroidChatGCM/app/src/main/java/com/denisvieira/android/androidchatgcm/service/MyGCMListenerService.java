package com.denisvieira.android.androidchatgcm.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.denisvieira.android.androidchatgcm.R;
import com.denisvieira.android.androidchatgcm.activity.SecondActivity;
import com.denisvieira.android.androidchatgcm.domain.PushMessage;
import com.denisvieira.android.androidchatgcm.extra.Util;
import com.denisvieira.android.androidchatgcm.receiver.NotificationReceiver;
import com.google.android.gms.gcm.GcmListenerService;

import de.greenrobot.event.EventBus;

/**
 * Created by denisvieira on 02/05/16.
 */
public class MyGCMListenerService extends GcmListenerService {
    public  static final String LOG = "LOG";

    @Override
    public void onMessageReceived(String from, Bundle data){
//        super.onMessageReceived(from,data);


        if(!Util.isMyApplicationTaskOnTop(this)){
            setNotificationApp(data);
        }

        EventBus.getDefault().post(new PushMessage(data.getString("title"), data.getString("message")));
    }

    public void setNotificationApp(final Bundle data){
        final int id = 6565;

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setTicker(data.getString("body"))
                .setSmallIcon(R.drawable.ic_new_message)
                .setContentTitle(data.getString("Title"))
                .setContentText(data.getString("body"))
                .setAutoCancel(true);

//        TaskStackBuilder stack = TaskStackBuilder.create(this);
//        stack.addParentStack(SecondActivity.class);

        Intent it = new Intent(this, SecondActivity.class);
        Bundle b = new Bundle();
        b.putString("data","test");
        it.putExtras(b);
        it.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
//        stack.addNextIntent(it);

        PendingIntent pi = PendingIntent.getActivity(this,0,it,PendingIntent.FLAG_UPDATE_CURRENT);
//        PendingIntent pi = stack.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pi);

        // BIG CONTENT
//            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
//            bigText.bigText(data.getString("body"));
//            builder.setStyle(bigText);

//            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
//            String[] messages = new String[6];
//            builder.setNumber( messages.length );
//            inboxStyle.setSummaryText("denisvieira05@gmail.com");
//
//            for(int i = 0; i < messages.length;i++){
//                messages[i] = "Test "+(new Random()).nextInt(9999);
//                inboxStyle.addLine( messages[i] );
//            }

//            builder.setStyle(inboxStyle);

        //ACTION BUTTON
            RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.custom_notification);
            //YES
            Intent intent = new Intent( this, NotificationReceiver.class);
            intent.setAction( NotificationReceiver.YES_NOTIFICATION_BUTTON);
            Bundle bu = new Bundle();
            bu.putInt("data",1);
            PendingIntent pb = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.bt_yes,pb);
//            builder.addAction( R.drawable.ic_yes,"Yes", pb );

            //MAYBE
            intent = new Intent( this, NotificationReceiver.class);
            intent.setAction( NotificationReceiver.MAYBE_NOTIFICATION_BUTTON);
            bu = new Bundle();
            bu.putInt("data",2);
            pb = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.bt_maybe,pb);
//            builder.addAction( R.drawable.ic_maybe,"Maybe", pb );

            //NO
            intent = new Intent( this, NotificationReceiver.class);
            intent.setAction( NotificationReceiver.NO_NOTIFICATION_BUTTON);
            bu = new Bundle();
            bu.putInt("data",3);
            pb = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.bt_no,pb);
//            builder.addAction( R.drawable.ic_no,"No", pb );

        builder.setContent( remoteViews );

        final NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(id,builder.build());

        new Thread(){
            @Override
            public void run() {
                super.run();
                Bitmap img1 = null, img2 = null;
                Bitmap bitmapPic, bitmapPhoto;

//                try{
//                    img1 = Picasso.with(getBaseContext()).load( data.getString( "large_icon" ) ).get();
//                    img2 = Picasso.with(getBaseContext()).load( data.getString( "big_picture" ) ).get();
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//
//                builder.setLargeIcon( img1 );
//                builder.setStyle( new NotificationCompat.BigPictureStyle().bigPicture(img2));

                // PROGRESS
                    // DETERMINATE
//                    for( int i = 0 ; i < 5;i++){
//                        builder.setProgress(100,i*20,false);
//                        builder.setContentTitle("Download");
//                        builder.setContentText("Download Music");
//                        nm.notify(id, builder.build() );
//
//                        SystemClock.sleep(1000);
//                    }

                    // INDETERMINATE
//                    for( int i = 0 ; i < 5;i++){
//                        builder.setProgress(0,0,true);
//                        builder.setContentTitle("Download");
//                        builder.setContentText("Download Music");
//                        nm.notify(id, builder.build() );
//
//                        SystemClock.sleep(1000);
//                    }
//
//                builder.setProgress(0,0,false);
//                builder.setContentTitle("Download");
//                builder.setContentText("Finalizado");
//                nm.notify(id, builder.build() );

            }
        }.start();

        builder.setPriority( NotificationCompat.PRIORITY_HIGH );
        builder.setVisibility( NotificationCompat.VISIBILITY_PUBLIC );


        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});


        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);

    }
}


