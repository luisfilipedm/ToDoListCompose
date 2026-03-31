package br.edu.satc.todolistcompose.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import br.edu.satc.todolistcompose.data.TaskData

@Composable
fun TaskCard(taskData: TaskData) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = taskData.title)
            Text(text = taskData.description)
        }
    }
}