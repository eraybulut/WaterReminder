package com.eray.waterreminder

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkerNotification(context: Context,workerParameters: WorkerParameters):Worker(context,workerParameters) {

    override fun doWork(): Result {

        bildirimOlustur()
        return Result.success()
    }
    fun bildirimOlustur(){

        val builder:NotificationCompat.Builder
        val bildirimYoneticisi=applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent=Intent(applicationContext,MainActivity::class.java)
        val gidilecekIntent=PendingIntent.getActivity(applicationContext
            ,1,intent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)



        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){

            var channelId="channelId"
            var channelName="channelAd"
            var channelTanitim="channelTanitim"
            var channelPriority=NotificationManager.IMPORTANCE_HIGH

            var channel:NotificationChannel?=bildirimYoneticisi.getNotificationChannel(channelId)

            if (channel==null){
                channel= NotificationChannel(channelId,channelName,channelPriority)
                channel.description=channelTanitim
                bildirimYoneticisi.createNotificationChannel(channel)
            }
            builder=NotificationCompat.Builder(applicationContext,channelId)
            builder.setContentTitle("Su Hatırlatıcı")
                .setContentText("Su içme zamanı geldi ! Su içmeyi unutma ")
                .setContentIntent(gidilecekIntent)
                .setSmallIcon(R.drawable.plasticbottle)
                .setAutoCancel(true)
        }
        else{
            builder=NotificationCompat.Builder(applicationContext)

            builder.setContentTitle("Su Hatırlatıcı")
                .setContentText("Su içme zamanı geldi ! Su içmeyi unutma ")
                .setContentIntent(gidilecekIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.plasticbottle)
                .priority=Notification.PRIORITY_HIGH
        }
        bildirimYoneticisi.notify(1,builder.build())
    }
}