package com.factory.wordflowdailymindpuzzles.data.billing

import android.util.Base64
import android.util.Log
import com.android.billingclient.api.Purchase
import java.security.KeyFactory
import java.security.PublicKey
import java.security.Signature
import java.security.spec.X509EncodedKeySpec

/**
 * Best-effort local signature check against the app's Play Console licensing key.
 *
 * This is a defense-in-depth client-side check only - it does not replace verifying
 * the purchase token server-side against the Play Developer API, which this project
 * does not currently have a backend for.
 */
object PurchaseVerifier {
    private const val TAG = "PurchaseVerifier"

    // TODO: paste the Base64-encoded license key from Play Console > Monetization setup.
    private const val BASE64_PUBLIC_KEY = ""

    fun isValid(purchase: Purchase): Boolean {
        if (purchase.signature.isBlank() || purchase.originalJson.isBlank()) return false
        if (BASE64_PUBLIC_KEY.isBlank()) {
            Log.w(TAG, "No licensing key configured; skipping local signature verification.")
            return true
        }
        return try {
            val publicKey = generatePublicKey(BASE64_PUBLIC_KEY)
            val signature = Signature.getInstance("SHA1withRSA")
            signature.initVerify(publicKey)
            signature.update(purchase.originalJson.toByteArray())
            signature.verify(Base64.decode(purchase.signature, Base64.DEFAULT))
        } catch (e: Exception) {
            Log.e(TAG, "Purchase signature verification failed", e)
            false
        }
    }

    private fun generatePublicKey(base64Key: String): PublicKey {
        val keyBytes = Base64.decode(base64Key, Base64.DEFAULT)
        val keySpec = X509EncodedKeySpec(keyBytes)
        return KeyFactory.getInstance("RSA").generatePublic(keySpec)
    }
}
