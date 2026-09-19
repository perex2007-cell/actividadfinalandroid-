package com.sena.taskmanager.ui.screen.taskform

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFormScreen(
    viewModel: TaskFormViewModel,
    taskId: String?,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    LaunchedEffect(taskId) {
        viewModel.loadTask(taskId)
    }

    LaunchedEffect(state.existingTask) {
        state.existingTask?.let { task ->
            title = task.title
            description = task.description
        }
    }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onNavigateBack()
            viewModel.resetState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (taskId == null) "Create Task" else "Edit Task") },
                navigationIcon = {
                    TextButton(onClick = { onNavigateBack() }) {
                        Text("Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )

                    Button(
                        onClick = { viewModel.saveTask(title, description, taskId) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Save Task")
                    }

                    if (taskId == null) {
                        OutlinedButton(
                            onClick = { viewModel.saveAsDraft(title, description) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Save as Local Draft")
                        }
                    }

                    state.error?.let { err ->
                        Text(text = err, color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}
