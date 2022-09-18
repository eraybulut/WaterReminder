package com.eray.waterreminder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.eray.waterreminder.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        var view=binding.root
        setContentView(view)

        var time=15


        binding.radioButton15Minute.setOnClickListener(){
            time=15
        }

        binding.radioButton30Minute.setOnClickListener(){
            time=30
        }
        binding.radioButton60Minute.setOnClickListener(){
            time=60
        }


        binding.buttonReminder.setOnClickListener(){

            var istek= PeriodicWorkRequestBuilder<WorkerNotification>(time.toLong(),TimeUnit.MINUTES)
                .build()
            Toast.makeText(this,"$time dakika hatırlatıcı kuruldu",Toast.LENGTH_SHORT).show()

            WorkManager.getInstance(this)
                .enqueue(istek)

            WorkManager.getInstance(this)
                .getWorkInfoByIdLiveData(istek.id).observe(this){

                val status=it.state.name
                Log.e("status",status)

            }
        }
    }
}