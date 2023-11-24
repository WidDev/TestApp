
package com.example.testapp


import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testapp.models.Team
import com.example.testapp.models.TeamMember
import com.example.testapp.ui.theme.TestAppTheme
import com.example.testapp.viewmodels.ItemListViewModel
import com.example.testapp.viewmodels.MainActivityViewModel
import com.example.testapp.views.ApplicationHeaderWithDrawer
import com.example.testapp.views.TeamMembersView
import com.example.testapp.views.TeamView
import com.example.testapp.views.TodosView

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

                    val owner = LocalViewModelStoreOwner.current
                    owner?.let {
                        val todosViewModel: TodosViewModel = viewModel(
                            it,
                            "TodosViewModel",
                            TodosViewModelFactory(
                                LocalContext.current.applicationContext as Application)
                        )

                        App(todosViewModel)
                    }


                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(todoListViewModel:TodosViewModel,
        viewModel: MainActivityViewModel = viewModel(),
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
                    TodosView(navController, todoListViewModel, teamMembersListViewModel)
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



/*@Preview(showBackground = true)
@Composable
fun AppPreview()
{
    TestAppTheme {
        App(ItemListViewModel<Todo>(), MainActivityViewModel(),
        MainActivityViewModel<Team>(),
        ItemListViewModel<TeamMember>())
    }
}*/


class TodosViewModelFactory(val application: Application) : ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass:Class<T>) : T
    {
        return TodosViewModel(application) as T
    }
}


/*
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

*/




