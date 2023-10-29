
package com.example.testapp

import android.os.Bundle
import android.view.Display
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.SwipeToReveal
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.SwipeToDismissBox
import androidx.wear.compose.material.rememberSwipeToDismissBoxState
import com.example.testapp.ui.theme.TestAppTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.math.roundToInt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp



import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

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
    Surface(modifier = Modifier) {
        Button(onClick = {
            actions.add(ToDo(1, "HELLO"))
        }) {
            Text(text = "Add Action")
        }
    }
}

@Composable
fun ToDoList(actions:MutableList<ToDo>, modifier: Modifier = Modifier) {
    LazyColumn (modifier = Modifier.height(200.dp)){
        /*for( action in actions)
        {
            *//*Row(modifier = Modifier.height(40.dp)) {
                Surface {
                    Text(
                        text = action.txt,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }*//*
        }*/
        items(actions) { action -> ToDoItem(action, actions)

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
    var state = rememberDismissState(confirmValueChange = -> { actions.remove(action)})
    SwipeToDismiss(state = state,
                   background = {ToDoBackground(action = action)},
                   dismissContent = {ToDoContent(action = action)},
                   directions = setOf(DismissDirection.StartToEnd, DismissDirection.StartToEnd))
}

@Composable
fun ToDoBackground(action:ToDo)
{
    Surface(color = Color.Red, modifier = Modifier.fillMaxSize())
    {

    }
}

@Composable
fun ToDoContent(action:ToDo)
{
    Surface(color = Color.Blue, modifier = Modifier.fillMaxWidth().height(30.dp)){
        Text(action.txt, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
            .wrapContentHeight(Alignment.CenterVertically));
    }
}





