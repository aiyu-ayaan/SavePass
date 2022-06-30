package com.ab.savepass.util

import android.app.Activity
import android.app.KeyguardManager
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import javax.inject.Inject


class FingerPrintHelperClass @Inject constructor(
    val activity: Activity
) {

    private var action: (() -> Unit)? = null
    private var cancellationSignal: CancellationSignal? = null

    fun setAction(action: (() -> Unit)?) {
        this.action = action
    }

    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(activity, "Authentication error $errString", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(activity, "Authentication Success!", Toast.LENGTH_SHORT)
                        .show()
                    action?.invoke()
                }
            }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            Toast.makeText(activity, "Authentication was cancelled by user!", Toast.LENGTH_SHORT)
                .show()
        }
        return cancellationSignal as CancellationSignal
    }


    @Suppress("DEPRECATION")
    fun openBiometricPrompt() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val biometricManager =
                activity.getSystemService(BiometricManager::class.java)
            if (biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS) {
                val biometricPrompt = BiometricPrompt.Builder(activity)
                    .setTitle("Verify your identity")
                    .setDescription("Use the fingerprint to unlock")
                    .setNegativeButton(
                        "Cancel",
                        activity.mainExecutor
                    ) { _, _ ->
                        Toast.makeText(
                            activity,
                            "Authentication Cancelled!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    .build()
                biometricPrompt.authenticate(
                    getCancellationSignal(),
                    activity.mainExecutor,
                    authenticationCallback
                )
            } else {
                Toast.makeText(
                    activity,
                    "Biometric authentication not supported",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        } else {
            Toast.makeText(activity, "Biometric authentication not supported", Toast.LENGTH_SHORT)
                .show()
        }
    }


    fun isBiometricSupport(): Boolean {
        val keyguardManager =
            activity.getSystemService(android.app.KeyguardManager::class.java) as KeyguardManager
        if (!keyguardManager.isKeyguardSecure) {
            Toast.makeText(
                activity,
                "Fingerprint authenticator has not been enabled is setting",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        if (ActivityCompat.checkSelfPermission(
                activity,
                android.Manifest.permission.USE_BIOMETRIC
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(
                activity,
                "Fingerprint authenticator permission not enabled",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        return if (activity.packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true
    }
}