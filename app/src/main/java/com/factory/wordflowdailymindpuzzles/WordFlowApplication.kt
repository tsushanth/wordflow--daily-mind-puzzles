package com.factory.wordflowdailymindpuzzles

import android.app.Application
import com.factory.wordflowdailymindpuzzles.data.AppDatabase
import com.factory.wordflowdailymindpuzzles.data.GameRepository
import com.factory.wordflowdailymindpuzzles.data.billing.BillingManager
import com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager

class WordFlowApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }
    val repository: GameRepository by lazy {
        GameRepository(database.dailyPuzzleDao(), database.wordSearchDao())
    }
    val premiumManager: PremiumManager by lazy { PremiumManager(this) }
    val billingManager: BillingManager by lazy { BillingManager(this, premiumManager) }
}
