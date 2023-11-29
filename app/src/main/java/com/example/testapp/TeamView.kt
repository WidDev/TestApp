package com.example.testapp.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.example.testapp.dal.entities.Team
import com.example.testapp.viewmodels.TeamsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun TeamView(navHostController:NavHostController, teamsViewModel: TeamsViewModel) {

    var showCreate by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }

    Column {
        /*QuickAddRow(list = teamListViewModel.items, addItem = teamListViewModel::addItem)*/
        Button(onClick = {
            showCreate = true
        })
        {
            Text("Press")
        }
        /*ItemList(items = teamListViewModel.items, content = { RenderTeam(item = it)})*/

    }

}


@Composable
fun RenderTeam(item: Team)
{
    Text(
        text = "Team Member",
        style = MaterialTheme.typography.titleMedium
    )
    Text(text = "${item.name} ${item.id}")
}





