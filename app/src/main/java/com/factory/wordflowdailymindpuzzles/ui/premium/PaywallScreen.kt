package com.factory.wordflowdailymindpuzzles.ui.premium

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.factory.wordflowdailymindpuzzles.R
import com.factory.wordflowdailymindpuzzles.data.billing.HintPackOffer
import com.factory.wordflowdailymindpuzzles.data.billing.PaywallTier

private val FEATURE_HIGHLIGHTS = listOf(
    "Unlock every Word Search category",
    "Full puzzle history & stats",
    "Unlimited hints in Daily Scramble",
    "Exclusive premium themes",
    "Ad-free, distraction-free play"
)

@Composable
fun PaywallScreen(
    viewModel: PaywallViewModel,
    onClose: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val activity = context as? Activity
    val haptics = LocalHapticFeedback.current

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onClose()
                }) {
                    Icon(Icons.Filled.Close, contentDescription = "Close")
                }
            }

            Text("WordFlow Premium", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(4.dp))
            Text(
                "Unlock the full puzzle experience",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(24.dp))

            if (state.isPremium) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.height(8.dp))
                        Text("You're a Premium member!", style = MaterialTheme.typography.titleMedium)
                    }
                }
                Spacer(Modifier.height(24.dp))
            }

            FEATURE_HIGHLIGHTS.forEach { feature ->
                FeatureRow(feature)
            }

            Spacer(Modifier.height(24.dp))

            if (state.isConnecting) {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.isOffline) {
                Text(
                    "You're offline. Connect to the internet to purchase or restore Premium.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.semantics { contentDescription = "Error: you're offline. Connect to the internet to purchase or restore Premium." }
                )
            } else if (!state.isPremium) {
                PaywallTier.entries.forEach { tier ->
                    Spacer(Modifier.height(12.dp))
                    TierButton(
                        tier = tier,
                        priceText = viewModel.priceFor(tier),
                        onClick = {
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            activity?.let { viewModel.onTierSelected(tier, it) }
                        }
                    )
                }

                Spacer(Modifier.height(20.dp))
                HintPackCard(
                    priceText = viewModel.hintPackPrice(),
                    onClick = {
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        activity?.let { viewModel.onHintPackSelected(it) }
                    }
                )
            }

            state.statusMessage?.let { message ->
                Spacer(Modifier.height(16.dp))
                Text(message, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(Modifier.height(24.dp))
            TextButton(
                onClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    viewModel.onRestoreClicked()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Restore Purchases")
            }

            Spacer(Modifier.height(8.dp))
            LegalLinksRow(onLinkOpenFailed = viewModel::onLinkOpenFailed)
        }
    }
}

@Composable
private fun FeatureRow(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun TierButton(tier: PaywallTier, priceText: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClickLabel = "Select ${tier.label}, $priceText ${tier.billingPeriodText}",
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(tier.label, style = MaterialTheme.typography.titleMedium)
                    tier.badge?.let { badge ->
                        Surface(
                            color = MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                badge,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                        }
                    }
                }
                Text(
                    tier.billingPeriodText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(priceText, style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
private fun HintPackCard(priceText: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClickLabel = "Buy ${HintPackOffer.label} for $priceText",
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(HintPackOffer.label, style = MaterialTheme.typography.titleSmall)
                Text(
                    HintPackOffer.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(priceText, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun LegalLinksRow(onLinkOpenFailed: () -> Unit) {
    val context = LocalContext.current
    val termsUrl = stringResource(R.string.terms_of_service_url)
    val privacyUrl = stringResource(R.string.privacy_policy_url)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        TextButton(onClick = { openUrl(context, termsUrl, onLinkOpenFailed) }) { Text("Terms of Service") }
        TextButton(onClick = { openUrl(context, privacyUrl, onLinkOpenFailed) }) { Text("Privacy Policy") }
    }
}

private fun openUrl(context: android.content.Context, url: String, onFailure: () -> Unit) {
    runCatching {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }.onFailure { onFailure() }
}
