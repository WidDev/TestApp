package com.example.testapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.testapp.models.NavigationDrawerItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationHeader(navHostController: NavHostController, navListContent:List<Pair<String, String>>) {

    var showMenu by remember { mutableStateOf(false) }
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()

    navHostController.addOnDestinationChangedListener { controller, destination, arguments ->
        destination.hierarchy.forEach {
            // Highlight menu items here
        }
    }

    TopAppBar(
        title = { Text(navBackStackEntry?.destination?.route ?: "") },
        navigationIcon = {
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                navListContent.forEach{
                    it ->

                    DropdownMenuItem(text = {Text(it.first)}, onClick = { navHostController?.navigate(it.second); showMenu = false })
                }
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationHeaderWithDrawer(navHostController: NavHostController,
                                navListContent:List<NavigationDrawerItem>,
                                content:@Composable() ()-> Unit)
{
    var title by remember { mutableStateOf(navListContent[0].label) }
    var showMenu by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column( modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.SpaceBetween){

                    navListContent.forEach{
                            it ->

                        NavigationDrawerItem(icon = {Icon(it.icon, contentDescription = null, modifier = Modifier, tint = it.color)}, label = { Text(text = it.label)}, onClick = {  scope.launch {
                            drawerState.apply {
                                navHostController?.navigate(it.route);
                                close()
                            }}}, selected = false)
                    }
                }
            }
        },
    )
    {
        Column(modifier = Modifier.fillMaxSize())
        {
            TopAppBar(
                title = { Text(GetNameFor(navListContent, navBackStackEntry?.destination?.route)) },
                navigationIcon = {
                    IconButton(onClick = {  scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                    }) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
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

            content()
        }
    }


}

fun GetNameFor(navListContent:List<NavigationDrawerItem>, route: String?): String
{
    if(route == null) return "";
    var item = navListContent.find { it -> it.route == route }
    return item?.label ?: ""
}

