package edu.lemoyne.campusapp

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun InterestsScreen(
    state: ShowcaseUiState,
    onBack: () -> Unit,
    onRefresh: () -> Unit,
    onLogin: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = onBack)
    LaunchedEffect(Unit) { onRefresh() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        TextButton(onClick = onBack) {
            Text(text = "Back")
        }
        TextButton(onClick = onRefresh) {
            Text(text = "Refresh Items")
        }
        TextButton(onClick = onLogin) {
            Text(text = "Login")
        }

        Text(
            text = "Showcase Items",
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is ShowcaseUiState.Loading -> {
                Text(
                    text = "Loading items...",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            is ShowcaseUiState.Error -> {
                Column {
                    Text(
                        text = "Error: ${state.message ?: "network error"}",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(onClick = onLogin) {
                        Text(text = "Go to Login")
                    }
                }
            }

            is ShowcaseUiState.Success -> {
                if (state.items.isEmpty()) {
                    Text("No items yet.")
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = state.items,
                            key = { it.id }
                        ) { item ->
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(item.title)
                                Text(item.category)
                                Text(item.description)
                            }
                        }
                    }
                }
            }
        }
    }
}