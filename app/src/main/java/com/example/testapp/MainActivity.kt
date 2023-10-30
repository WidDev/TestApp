
package com.example.testapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testapp.ui.theme.TestAppTheme

class MainActivity : ComponentActivity() {


    public var actions = mutableStateListOf<ToDo>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column()
                    {
                        App(actions)
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(actions:MutableList<ToDo>)
{
    Column{
        TopAppBar(
            title = { Text("Simple TopAppBar") },
            navigationIcon = {
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(Icons.Filled.Menu, contentDescription = null)
                }
            },
            actions = {
                // RowScope here, so these icons will be placed horizontally
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "Localized description")
                }
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(Icons.Filled.Add, contentDescription = "Localized description")
                }
            })
        AddButton(actions = actions)
        ToDoList(actions = actions)
    }
}

@Preview(showBackground = true)
@Composable
fun AppToolbarPreview()
{
    TestAppTheme {
        var list = mutableListOf<ToDo>()
        list.add(ToDo(1, "Order groceries"))
        list.add(ToDo(2, "Call Charlie"))
        App(list)
    }
}


@Composable
fun AddButton(actions:MutableList<ToDo>)
{
    var nextId by remember { mutableIntStateOf(1) }
    Surface(modifier = Modifier) {
        Button(onClick = {
            actions.add(ToDo(id = nextId++, "New Action"))
        }) {
            Text(text = "Add Action")
        }
    }
}

@Composable
fun ToDoList(actions:MutableList<ToDo>, modifier: Modifier = Modifier) {
    LazyColumn (modifier = Modifier.fillMaxHeight()){
        items(actions, {it.id}) { action -> ToDoItem(action, actions)
        }
    }
}


@Composable
fun Todo(todo:ToDo, actions: MutableList<ToDo>)
{
    /*val dismissState = rememberSwipeToDismissBoxState(
        confirmStateChange = {
            //viewModel.removeRecord(currentItem)
            true
        }
    )


    SwipeToDismissBox( state = rememberSwipeToDismissBoxState(),
                        onDismissed = { actions.remove(todo)},
                      contentScrimColor = Color.Red,
                      backgroundScrimColor = Color.Red,
                      modifier = Modifier
                          .fillMaxWidth()
                          .height(40.dp)) {
        Text(todo.txt, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
            .wrapContentHeight(Alignment.CenterVertically));

    }*/

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoItem(action:ToDo, actions:MutableList<ToDo>)
{
    val context = LocalContext.current
    var show by remember { mutableStateOf(true) }
    val currentItem by rememberUpdatedState(action)
    var state = rememberDismissState(confirmValueChange = {
            if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                actions.remove(action)
                println("Items"+actions.count())
                true
            } else false
        }, positionalThreshold = { 150.dp.toPx() } )
    SwipeToDismiss(state = state,
                   background = {DismissBackground(dismissState = state)},
                   dismissContent = {ToDoContent(action = action)},
                   directions = setOf(DismissDirection.StartToEnd))
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState:DismissState)
{
    val color = when (dismissState.dismissDirection) {
        DismissDirection.StartToEnd -> Color(0xFFFF1744)
        DismissDirection.EndToStart -> Color(0xFF1DE9B6)
        null -> Color.Transparent
    }
    val direction = dismissState.dismissDirection

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (direction == DismissDirection.StartToEnd) Icon(
            Icons.Default.Delete,
            modifier = Modifier.fillMaxHeight().background((Color.White)),
            contentDescription = "delete"
        )
        Spacer(modifier = Modifier)
        if (direction == DismissDirection.EndToStart) Icon(
            // make sure add baseline_archive_24 resource to drawable folder
            Icons.Default.Favorite,
            contentDescription = "Archive"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoBackground(state:DismissState)
{
    val color by animateColorAsState(
        when (state.targetValue) {
            DismissValue.Default -> Color.Transparent
            DismissValue.DismissedToEnd -> Color.Red
            DismissValue.DismissedToStart -> Color.Transparent
        }
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .background(color = color)
    ){
        Icon(Icons.Filled.Delete, contentDescription = "Localized description")
    }
}



@Composable
fun ToDoBackground()
{
    Surface(color = Color.Red, modifier = Modifier.fillMaxSize())
    {
        Icon(Icons.Filled.Delete, contentDescription = "Localized description")
    }
}

@Composable
fun ToDoContent(action:ToDo)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "TODO",
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = "${action.txt} ${action.id}")
        }
    }
    /*Surface(color = Color.Blue, modifier = Modifier.fillMaxWidth().height(30.dp)){
        Text(action.txt, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
            .wrapContentHeight(Alignment.CenterVertically));
    }*/
}





