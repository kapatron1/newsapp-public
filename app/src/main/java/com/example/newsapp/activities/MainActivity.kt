package com.example.newsapp.activities

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.newsapp.R

class MainActivity : AppCompatActivity() {

    private var cancellationSignal: CancellationSignal? = null
    private var context: Context? = null

    // create an authenticationCallback
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() = @RequiresApi(Build.VERSION_CODES.P)
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)

                when(errorCode) {
                    BiometricPrompt.BIOMETRIC_ERROR_LOCKOUT -> notifyUser("Too many attempts. Please try again later")
                    BiometricPrompt.BIOMETRIC_ERROR_LOCKOUT_PERMANENT -> notifyUser("Too many attempts. Please try again later")
                    BiometricPrompt.BIOMETRIC_ERROR_USER_CANCELED -> notifyUser("User Cancelled the operation")
                    BiometricPrompt.BIOMETRIC_ERROR_TIMEOUT -> notifyUser("Authentication Timeout")
                    BiometricPrompt.BIOMETRIC_ERROR_UNABLE_TO_PROCESS -> notifyUser("Unable to process the authentication")
                    BiometricPrompt.BIOMETRIC_ERROR_HW_NOT_PRESENT,
                    BiometricPrompt.BIOMETRIC_ERROR_HW_UNAVAILABLE,
                    BiometricPrompt.BIOMETRIC_ERROR_NO_BIOMETRICS,
                    BiometricPrompt.BIOMETRIC_ERROR_NO_DEVICE_CREDENTIAL,
                    BiometricPrompt.BIOMETRIC_ERROR_VENDOR -> startArticlesListActivity()
                    else -> notifyUser("Authentication Error : $errString")
                }
            }

            // If the fingerprint is recognized by the app then it will call
            // onAuthenticationSucceeded and show a toast that Authentication has Succeed
            // Here you can also start a new activity after that
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                notifyUser("Authentication Succeeded")
                context?.startActivity(Intent(context, ArticlesListActivity::class.java))
                // or start a new Activity
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this
        if(checkBiometricSupport()){
                // fingerprint authentication
                val biometricPrompt = BiometricPrompt.Builder(this)
                    .setTitle("Touch to authenticate")
                    .setNegativeButton("Cancel", this.mainExecutor) { dialog, which ->
                        notifyUser("Authentication Cancelled")
                    }.build()

                // start the authenticationCallback in mainExecutor
                biometricPrompt.authenticate(getCancellationSignal(), mainExecutor, authenticationCallback)
        } else {
            startArticlesListActivity()
        }
    }

    // it will be called when authentication is cancelled by the user
    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("Authentication was Cancelled by the user")
        }
        return cancellationSignal as CancellationSignal
    }

    // it checks whether the app the app has fingerprint permission
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkBiometricSupport(): Boolean {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (!keyguardManager.isDeviceSecure) {
            return false
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            return false
        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true
    }

    private fun notifyUser(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun startArticlesListActivity() {
        context?.startActivity(Intent(context, ArticlesListActivity::class.java))
    }
}
