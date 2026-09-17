package com.example.actividadfinalandroid.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object TaskList : Screen("task_list")
    object TaskForm : Screen("task_form?taskId={taskId}") {
        fun createRoute(taskId: String? = null) = if (taskId != null) "task_form?taskId=$taskId" else "task_form"
    }
    object DraftList : Screen("draft_list")
}
