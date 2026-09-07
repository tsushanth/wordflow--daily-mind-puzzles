package com.factory.wordflowdailymindpuzzles.ui.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.factory.wordflowdailymindpuzzles.R

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onUpgradeClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val statusMessage by viewModel.statusMessage.collectAsState()
    val context = LocalContext.current
    val haptics = LocalHapticFeedback.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(statusMessage) {
        statusMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.dismissStatusMessage()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) { data -> Snackbar(snackbarData = data) } }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            Text("Settings", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        enabled = !state.entitlement.isPremium,
                        onClickLabel = "Upgrade to Premium",
                        onClick = {
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            onUpgradeClick()
                        }
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Icon(Icons.Filled.WorkspacePremium, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Column {
                            Text(
                                if (state.entitlement.isPremium) "Premium Member" else "Upgrade to Premium",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                if (state.entitlement.isPremium) {
                                    if (state.entitlement.isLifetime) "Lifetime access" else "Active subscription"
                                } else {
                                    "Unlock every puzzle category, full history & more"
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    if (!state.entitlement.isPremium) {
                        Icon(Icons.Filled.ChevronRight, contentDescription = null)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Text("Hint credits: ${state.hintCredits}", style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.height(24.dp))
            TextButton(onClick = {
                haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                viewModel.onRestoreClicked()
            }) {
                Text("Restore Purchases")
            }

            Spacer(Modifier.height(8.dp))
            TextButton(onClick = {
                openUrl(context, context.getString(R.string.terms_of_service_url), viewModel::onLinkOpenFailed)
            }) {
                Text("Terms of Service")
            }
            TextButton(onClick = {
                openUrl(context, context.getString(R.string.privacy_policy_url), viewModel::onLinkOpenFailed)
            }) {
                Text("Privacy Policy")
            }
        }
    }
}

private fun openUrl(context: android.content.Context, url: String, onFailure: () -> Unit) {
    runCatching {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }.onFailure { onFailure() }
}
