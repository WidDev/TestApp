
package com.example.testapp


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testapp.models.Team
import com.example.testapp.models.TeamMember
import com.example.testapp.models.ToDo
import com.example.testapp.ui.theme.TestAppTheme
import com.example.testapp.viewmodels.ItemListViewModel
import com.example.testapp.viewmodels.MainActivityViewModel
import com.example.testapp.views.ActionsView
import com.example.testapp.views.ApplicationHeaderWithDrawer
import com.example.testapp.views.TeamMembersView
import com.example.testapp.views.TeamView
import java.util.UUID

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(viewModel: MainActivityViewModel = viewModel(),
        todoListViewModel:ItemListViewModel<ToDo> = ItemListViewModel<ToDo>(),
        teamsListViewModel:ItemListViewModel<Team> = ItemListViewModel<Team>(),
        teamMembersListViewModel:ItemListViewModel<TeamMember> = ItemListViewModel<TeamMember>())
{
    val context = LocalContext.current
    val navController = rememberNavController()

    Column(modifier = Modifier.fillMaxSize()) {





        ApplicationHeaderWithDrawer(navHostController = navController, viewModel.navigationItems )
        {
            NavHost(navController = navController, startDestination = "todo"){
                composable("todo"){
                    ActionsView(navController, todoListViewModel, teamMembersListViewModel)
                }
                composable("teams"){
                    TeamView(navController, teamsListViewModel)
                }
                composable("people"){
                    TeamMembersView(navController, teamMembersListViewModel)
                }
            }
        }


    }

}

@Composable
public fun DrawerItem(icon: ImageVector, text:String)
{
    Row(modifier = Modifier.height(30.dp))
    {
    }
        Icon(imageVector = icon, contentDescription = "")
        Text(text)
    }



@Preview(showBackground = true)
@Composable
fun AppPreview()
{
    TestAppTheme {
        App(MainActivityViewModel(),
        ItemListViewModel<ToDo>(),
        ItemListViewModel<Team>(),
        ItemListViewModel<TeamMember>())
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
                        actions.add(ToDo(id = UUID.randomUUID(), todo))
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





