@file:OptIn(ExperimentalMaterial3Api::class)

package br.edu.satc.todolistcompose.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import br.edu.satc.todolistcompose.data.AppDatabase
import br.edu.satc.todolistcompose.data.TaskData
import br.edu.satc.todolistcompose.ui.components.TaskCard
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {

    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)

    val tarefas = remember { mutableStateListOf<TaskData>() }
    val scope = rememberCoroutineScope()

    // Carrega tarefas ao abrir
    LaunchedEffect(Unit) {
        val lista = db.tarefaDao().getAll()
        tarefas.clear()
        tarefas.addAll(lista)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {

        LazyColumn {
            items(tarefas) { task ->
                TaskCard(taskData = task)
            }
        }

        NewTask(
            onSave = { title, desc ->
                scope.launch {
                    db.tarefaDao().inserir(
                        TaskData(
                            title = title,
                            description = desc,
                            complete = false
                        )
                    )

                    val lista = db.tarefaDao().getAll()
                    tarefas.clear()
                    tarefas.addAll(lista)
                }
            }
        )
    }
}

@Composable
fun NewTask(onSave: (String, String) -> Unit) {

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        ExtendedFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            text = { Text("Nova tarefa") },
            icon = { Icon(Icons.Filled.Add, contentDescription = "") },
            onClick = { showBottomSheet = true }
        )
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                OutlinedTextField(
                    value = taskTitle,
                    onValueChange = { taskTitle = it },
                    label = { Text("Título") }
                )

                OutlinedTextField(
                    value = taskDescription,
                    onValueChange = { taskDescription = it },
                    label = { Text("Descrição") }
                )

                Button(
                    onClick = {
                        onSave(taskTitle, taskDescription)

                        scope.launch { sheetState.hide() }
                        showBottomSheet = false

                        taskTitle = ""
                        taskDescription = ""
                    }
                ) {
                    Text("Salvar")
                }
            }
        }
    }
}