package com.factory.wordflowdailymindpuzzles.data.billing

import com.android.billingclient.api.BillingClient.ProductType

object BillingConstants {
    private const val PACKAGE = "com.factory.wordflowdailymindpuzzles"

    const val SUB_WEEKLY = "$PACKAGE.subscription.weekly"
    const val SUB_MONTHLY = "$PACKAGE.subscription.monthly"
    const val SUB_YEARLY = "$PACKAGE.subscription.yearly"
    const val IAP_LIFETIME = "$PACKAGE.subscription.lifetime"
    const val IAP_HINT_PACK = "$PACKAGE.small_iap"

    /** Recurring products, queried and sold as [ProductType.SUBS]. */
    val SUBSCRIPTION_PRODUCT_IDS = listOf(SUB_WEEKLY, SUB_MONTHLY, SUB_YEARLY)

    /** One-time products, queried and sold as [ProductType.INAPP]. */
    val INAPP_PRODUCT_IDS = listOf(IAP_LIFETIME, IAP_HINT_PACK)

    /** Any of these being actively owned grants full premium access. */
    val PREMIUM_ENTITLEMENT_PRODUCT_IDS = SUBSCRIPTION_PRODUCT_IDS + IAP_LIFETIME

    /** Purchasing this consumable tops up hint credits instead of granting premium. */
    const val HINT_PACK_CREDITS = 5
}
