package com.suxa.myapplication

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import com.suxa.myapplication.databinding.ActivityMainBinding
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        buttonClickListener()
    }

    private fun buttonClickListener() {
        binding.btnSend.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        if (ContextCompat.checkSelfPermission(
                this,
                SEND_PERMISSION
            ) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    SEND_PERMISSION
                ))
            else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(SEND_PERMISSION),
                    MY_PERMISSION
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) run {
                    val smsManager = applicationContext.getSystemService(SmsManager::class.java)
                    smsManager.sendTextMessage(
                        binding.etPhone.text.toString(), null,
                        binding.etMessage.text.toString(), null, null
                    )
                    Toast.makeText(this, "SMS sent.",
                        Toast.LENGTH_LONG).show()
                }
                else {
                    Toast.makeText(this,
                        "SMS failed, please try again.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        private const val SEND_PERMISSION = android.Manifest.permission.SEND_SMS
        private const val MY_PERMISSION = 0
    }
}