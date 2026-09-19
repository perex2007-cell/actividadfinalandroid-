package com.sena.taskmanager.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sena.taskmanager.domain.repository.AuthRepository
import com.sena.taskmanager.ui.screen.login.LoginScreen
import com.sena.taskmanager.ui.screen.login.LoginViewModel
import com.sena.taskmanager.ui.screen.register.RegisterScreen
import com.sena.taskmanager.ui.screen.register.RegisterViewModel
import com.sena.taskmanager.ui.screen.drafts.DraftListScreen
import com.sena.taskmanager.ui.screen.drafts.DraftListViewModel
import com.sena.taskmanager.ui.screen.tasklist.TaskListScreen
import com.sena.taskmanager.ui.screen.tasklist.TaskListViewModel
import com.sena.taskmanager.ui.screen.taskform.TaskFormScreen
import com.sena.taskmanager.ui.screen.taskform.TaskFormViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    authRepository: AuthRepository,
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel,
    taskListViewModel: TaskListViewModel,
    taskFormViewModel: TaskFormViewModel,
    draftListViewModel: DraftListViewModel
) {
    val startDestination = if (authRepository.isUserLoggedIn()) {
        Screen.TaskList.route
    } else {
        Screen.Login.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = loginViewModel,
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginSuccess = {
                    navController.navigate(Screen.TaskList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = registerViewModel,
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onRegisterSuccess = {
                    navController.navigate(Screen.TaskList.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.TaskList.route) {
            if (!authRepository.isUserLoggedIn()) {
                LaunchedEffect(Unit) {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            } else {
                TaskListScreen(
                    viewModel = taskListViewModel,
                    onNavigateToForm = { taskId ->
                        navController.navigate(Screen.TaskForm.createRoute(taskId))
                    },
                    onNavigateToDrafts = {
                        navController.navigate(Screen.DraftList.route)
                    },
                    onLogout = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable(
            route = Screen.TaskForm.route,
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            if (!authRepository.isUserLoggedIn()) {
                LaunchedEffect(Unit) {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            } else {
                val taskId = backStackEntry.arguments?.getString("taskId")
                TaskFormScreen(
                    viewModel = taskFormViewModel,
                    taskId = taskId,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(Screen.DraftList.route) {
            if (!authRepository.isUserLoggedIn()) {
                LaunchedEffect(Unit) {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            } else {
                DraftListScreen(
                    viewModel = draftListViewModel,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
