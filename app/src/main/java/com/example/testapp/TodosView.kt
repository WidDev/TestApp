package com.example.testapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.testapp.dal.entities.Todo
import com.example.testapp.dal.entities.TodoAndOwner
import com.example.testapp.shared.FloatingButton
import com.example.testapp.shared.ItemList
import com.example.testapp.shared.LabelledSegment
import com.example.testapp.viewmodels.TeamMembersViewModel
import com.example.testapp.viewmodels.TodosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun TodosView(navHostController: NavHostController?,
                     todosViewModel: TodosViewModel,
                     teamMembersViewModel:TeamMembersViewModel)
{
    var showCreate by remember { mutableStateOf(false) }
    val allTodos by todosViewModel.allTodos.observeAsState(mutableListOf())

    var selectedTodo by remember { mutableStateOf<Todo?>(Todo(txt = "")) }
    //var selectedTodo:Todo? by remember {mutableStateOf(null)}


    Column(modifier = Modifier.fillMaxWidth()) {

        CreateDialog(visible = showCreate,
                    onDismiss = {showCreate = false },
                    onOK = { todosViewModel.upsertTodo(it); showCreate = false; selectedTodo = null},
                    teamMembersList = teamMembersViewModel,
                    todo = selectedTodo)
        var list = allTodos.sortedByDescending { todo -> todo.todo.id }.map({ it -> it.todo }).toMutableList()
        ItemList(items = list,
            content = { RenderToDo(item = it, items = allTodos) },
            onDelete = {it -> todosViewModel.deleteTodo(it)},
            onEdit = { it -> selectedTodo = it; showCreate = true }
        )

    }


    FloatingButton(text="Add") {
        showCreate = true
    }
    /*FloatingButton(text="Clear", modifier = Modifier.padding(horizontal=70.dp)) {
        todosViewModel.deleteAll()
    }*/
}

/*@Preview(showBackground = true)
@Composable
fun ActionsViewPreview()
{
    TodosView(navHostController = null, listViewModel = ItemListViewModel<Todo>(), teamMembersViewModel = ItemListViewModel<TeamMember>())
}*/

@Composable
fun RenderToDo(item: Todo, items:List<TodoAndOwner>)
{
    val todoAndOwner:TodoAndOwner? = items.find( { t -> t.todo.id == item.id})
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "${item.txt} ${item.id}")
                if(todoAndOwner != null)
                {
                    Text(text = "Owned by:${todoAndOwner.owner?.name} ")
                }
                /*Icon(imageVector = Icons.Filled.Face, contentDescription = "", tint = item.owner.color)*/
           }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDialog(visible:Boolean = false,
                 onDismiss: () -> Unit = {},
                 onOK:(todo: Todo) -> Unit = {},
                 teamMembersList:TeamMembersViewModel,
                 todo:Todo? = Todo(txt = ""))
{

    if(visible)
    {
        Dialog(onDismissRequest = onDismiss,
               properties = DialogProperties(usePlatformDefaultWidth = false,dismissOnBackPress = true))
        {

            var updatedTodo by remember { mutableStateOf(todo ?: Todo(txt = "")) }

            Card(modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
                 shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                       verticalArrangement = Arrangement.spacedBy(50.dp))
                {

                    LabelledSegment(label = "Name", modifier = Modifier.fillMaxWidth())
                    {
                        TextField(value = updatedTodo.txt, onValueChange = { updatedTodo = updatedTodo.copy(txt = it) }, modifier = Modifier.fillMaxWidth())
                    }


                    Button(onClick = {onOK(updatedTodo)},
                        shape = MaterialTheme.shapes.small)
                    {
                        Text("Add")
                    }

                }

            }

        }

    }

}

