package com.factory.wordflowdailymindpuzzles.data.billing

sealed class BillingConnectionState {
    data object Connecting : BillingConnectionState()
    data object Connected : BillingConnectionState()
    data class Disconnected(val message: String) : BillingConnectionState()
}

sealed class PurchaseUiEvent {
    data class Success(val productId: String) : PurchaseUiEvent()
    data object Cancelled : PurchaseUiEvent()
    data object Pending : PurchaseUiEvent()
    data class Error(val message: String) : PurchaseUiEvent()
}

data class PremiumEntitlement(
    val isPremium: Boolean = false,
    val productId: String? = null,
    val isLifetime: Boolean = false
)

/** Subscription tiers surfaced on the paywall, with store-independent fallback prices. */
enum class PaywallTier(
    val productId: String,
    val label: String,
    val billingPeriodText: String,
    val defaultPriceText: String,
    val badge: String? = null
) {
    WEEKLY(
        productId = BillingConstants.SUB_WEEKLY,
        label = "Weekly",
        billingPeriodText = "per week",
        defaultPriceText = "$4.79"
    ),
    YEARLY(
        productId = BillingConstants.SUB_YEARLY,
        label = "Yearly",
        billingPeriodText = "per year",
        defaultPriceText = "$35.99",
        badge = "BEST VALUE"
    ),
    LIFETIME(
        productId = BillingConstants.IAP_LIFETIME,
        label = "Lifetime",
        billingPeriodText = "one-time",
        defaultPriceText = "$79.99",
        badge = "NO RENEWAL"
    )
}

/** A single, non-subscription top-up offer for players who don't want to subscribe. */
object HintPackOffer {
    const val productId = BillingConstants.IAP_HINT_PACK
    const val label = "5 Hint Pack"
    const val description = "A one-time top-up of 5 hints, usable in Daily Scramble."
    const val defaultPriceText = "$0.99"
}
