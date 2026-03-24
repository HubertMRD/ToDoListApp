package com.example.todo_listapp

import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.CheckBox
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo_listapp.ui.theme.ToDo_ListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDo_ListAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ToDoScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ToDoScreen( modifier: Modifier = Modifier, toDoViewModel: ToDoViewModel = viewModel()) {
    var taskBody by remember { mutableStateOf("") }
    Column(
        modifier=modifier
        .fillMaxSize()
    ) {
        TextField(
            modifier = modifier.fillMaxWidth()
                .padding(6.dp),
            value = taskBody,
            onValueChange = {taskBody= it},
            label = {Text("Enter Task")},
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions (
                onDone = {
                    toDoViewModel.addTask(taskBody)
                    taskBody = ""
                }
            )
        )
        LazyColumn{
            items(
                items = toDoViewModel.taskList,
                key = { task -> task.id }
            ){ task ->
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        if(it == SwipeToDismissBoxValue.StartToEnd){
                            toDoViewModel.deleteTask(task)
                            true
                        }else
                            false
                    }
                )
                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = {SwipeBackground(dismissState)},
                    content = {TaskCard(task, toggleCompleted = toDoViewModel::toggleTaskCompleted)},
                    modifier = Modifier.padding(vertical = 1.dp).animateItem()
                )
            }
        }

    }

}


@Composable
fun SwipeBackground(dismissState: SwipeToDismissBoxState, modifier: Modifier = Modifier){
    val color = if( dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd)
            Color.Red
        else
            Color.Transparent
    Row(
        modifier.fillMaxSize().background(color)
    ) {
        if(dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd)
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete"
            )
    }
}


@Composable
fun TaskCard(task: Task, toggleCompleted:(Task)->Unit, modifier: Modifier = Modifier){
    Card(
      modifier = modifier.padding(8.dp).fillMaxWidth()
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = task.body,
                modifier = modifier.padding(12.dp)
            )
            Checkbox(
                checked = task.completed,
                onCheckedChange =  {
                    toggleCompleted(task)
                }
            )
        }
    }
}
