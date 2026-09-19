package com.sena.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sena.taskmanager.domain.repository.AuthRepository
import com.sena.taskmanager.ui.screen.login.LoginViewModel
import com.sena.taskmanager.ui.screen.register.RegisterViewModel
import com.sena.taskmanager.ui.screen.drafts.DraftListViewModel
import com.sena.taskmanager.ui.navigation.AppNavigation
import com.sena.taskmanager.ui.screen.taskform.TaskFormViewModel
import com.sena.taskmanager.ui.screen.tasklist.TaskListViewModel
import com.sena.taskmanager.ui.theme.ActividadfinalandroidTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    private val loginViewModel: LoginViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()
    private val taskListViewModel: TaskListViewModel by viewModels()
    private val taskFormViewModel: TaskFormViewModel by viewModels()
    private val draftListViewModel: DraftListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ActividadfinalandroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        val navController = rememberNavController()
                        AppNavigation(
                            navController = navController,
                            authRepository = authRepository,
                            loginViewModel = loginViewModel,
                            registerViewModel = registerViewModel,
                            taskListViewModel = taskListViewModel,
                            taskFormViewModel = taskFormViewModel,
                            draftListViewModel = draftListViewModel
                        )
                    }
                }
            }
        }
    }
}
