
package com.example.testapp


import android.app.ListActivity
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                    TopNav(actions = actions)
                }
            }
        }
    }
}


@Composable
fun TopNav(actions:MutableList<ToDo>)
{
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "First"){
        composable("First"){
            App(actions, navController)
        }
        composable("Second"){
            MainView(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(actions:MutableList<ToDo>, navHostController: NavHostController?)
{
    val context = LocalContext.current
    var showMenu by remember { mutableStateOf(false) }



    Column{
        TopAppBar(
            title = { Text("Simple TopAppBar") },


            navigationIcon = {
                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(Icons.Filled.Menu, contentDescription = null)
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Red Page") },
                        onClick = { navHostController?.navigate("second") }
                    )
                    DropdownMenuItem(
                        text = { Text("Save") },
                        onClick = { Toast.makeText(context, "Save", Toast.LENGTH_SHORT).show(); showMenu = false }
                    )
                }
            },
            actions = {

                // RowScope here, so these icons will be placed horizontally
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(Icons.Filled.AccountCircle, contentDescription = "Localized description")
                }
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(Icons.Filled.Settings, contentDescription = "Localized description")
                }
            })





        AddButton(actions = actions)
        ToDoList(actions = actions)
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview()
{
    TestAppTheme {
        var list = mutableListOf<ToDo>()
        list.add(ToDo(1, "Order groceries"))
        list.add(ToDo(2, "Call Charlie"))
        App(list, null)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddButton(actions:MutableList<ToDo>)
{
    var nextId by remember { mutableStateOf(1) }
    var todo by remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxWidth())
    {

        TextField(shape = RoundedCornerShape(2.dp),
            trailingIcon = {
                Button(shape = MaterialTheme.shapes.small, onClick = {
                    if(todo != "")
                    {
                        actions.add(ToDo(id = nextId++, todo))
                        todo = ""
                    }

                }) {
                    Text(text = "Add Action")
                }
            },
            colors = TextFieldDefaults.colors(
            focusedIndicatorColor =  Color.LightGray, //hide the indicator
            unfocusedIndicatorColor = Color.Transparent),
            value = todo,
            onValueChange = { todo = it})
    }

}

@Composable
fun ToDoList(actions:MutableList<ToDo>, modifier: Modifier = Modifier) {
    LazyColumn (modifier = Modifier.fillMaxHeight()){
        items(actions, {it.id}) { action -> ToDoItem(action, actions)
        }
    }
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
                   directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart))
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
            .background(color),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (direction == DismissDirection.StartToEnd) Icon(
            Icons.Default.Delete,
            modifier = Modifier
                .fillMaxHeight()
                .background((Color.White)),
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
            .background(color = color)
    ){
        Icon(Icons.Filled.Delete, contentDescription = "Localized description")
    }
}


@Composable
fun ToDoContent(action:ToDo)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
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



@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun MainView(navHostController: NavHostController?)
{
    val context = LocalContext.current
    var showMenu by remember { mutableStateOf(false) }

    Column{
        TopAppBar(
            title = { Text("Simple TopAppBar") },


            navigationIcon = {
                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(Icons.Filled.Menu, contentDescription = null)
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Home") },
                        onClick = { navHostController?.navigate("first") }
                    )
                    DropdownMenuItem(
                        text = { Text("Save") },
                        onClick = { Toast.makeText(context, "Save", Toast.LENGTH_SHORT).show(); showMenu = false }
                    )
                }
            })

        Surface(modifier = Modifier.fillMaxSize(), color = Color.Red) {
            Text(text = "HELLO WORLD", modifier = Modifier.fillMaxWidth())
        }
    }

}





