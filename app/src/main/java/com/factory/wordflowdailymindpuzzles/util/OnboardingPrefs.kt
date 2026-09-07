package com.factory.wordflowdailymindpuzzles.util

import android.content.Context
import androidx.core.content.edit

/** Tracks whether the user has already been through the one-time onboarding flow. */
object OnboardingPrefs {
    private const val PREFS_NAME = "onboarding_prefs"
    private const val KEY_COMPLETED = "completed"

    fun hasCompletedOnboarding(context: Context): Boolean =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_COMPLETED, false)

    fun markOnboardingCompleted(context: Context) {
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit { putBoolean(KEY_COMPLETED, true) }
    }
}
