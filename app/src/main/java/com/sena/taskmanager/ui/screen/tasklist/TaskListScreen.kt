package com.sena.taskmanager.ui.screen.tasklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel,
    onNavigateToForm: (String?) -> Unit,
    onNavigateToDrafts: () -> Unit,
    onLogout: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadTasks()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Tasks") },
                actions = {
                    TextButton(onClick = { onNavigateToDrafts() }) {
                        Text("Drafts")
                    }
                    TextButton(onClick = { viewModel.logout(onLogout) }) {
                        Text("Logout")
                    }
                }
            )
        },
        floatingActionButton = {
            Button(onClick = { onNavigateToForm(null) }) {
                Text("+ New Task")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (state.isLoading && state.items.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.error != null && state.items.isEmpty()) {
                Text(
                    text = state.error ?: "An error occurred",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (state.items.isEmpty()) {
                Text(
                    text = "No tasks found. Click button to create one.",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.items, key = { it.id }) { task ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onNavigateToForm(task.id) }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Checkbox(
                                        checked = task.completed,
                                        onCheckedChange = { viewModel.toggleTaskCompletion(task) }
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = task.title,
                                            style = MaterialTheme.typography.titleMedium,
                                            textDecoration = if (task.completed) TextDecoration.LineThrough else null
                                        )
                                        if (task.description.isNotEmpty()) {
                                            Text(
                                                text = task.description,
                                                style = MaterialTheme.typography.bodyMedium,
                                                maxLines = 2
                                            )
                                        }
                                    }
                                }
                                TextButton(onClick = { viewModel.deleteTask(task.id) }) {
                                    Text("Delete", color = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
