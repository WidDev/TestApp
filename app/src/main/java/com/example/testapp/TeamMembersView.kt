package com.example.testapp.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.testapp.dal.entities.TeamMember
import com.example.testapp.shared.ItemList
import com.example.testapp.shared.QuickAddRow
import com.example.testapp.viewmodels.ItemListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun TeamMembersView(navHostController: NavHostController, teamViewListViewModel: ItemListViewModel<TeamMember>) {

    Column {
        QuickAddRow(list = teamViewListViewModel.items, addItem = teamViewListViewModel::addItem)
        ItemList(items = teamViewListViewModel.items, content = { RenderTeamMember(item = it)})
    }

}


@Composable
fun RenderTeamMember(item: TeamMember)
{
    Text(
        text = "Team Member",
        style = MaterialTheme.typography.titleMedium
    )
    Text(text = "${item.name} ${item.id}")
}
