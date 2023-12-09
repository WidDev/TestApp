package com.example.testapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.testapp.dal.entities.TeamMember
import com.example.testapp.shared.ColorPicker
import com.example.testapp.shared.FloatingButton
import com.example.testapp.shared.ItemList
import com.example.testapp.shared.LabelledSegment
import com.example.testapp.viewmodels.TeamMembersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun TeamMembersView(navHostController: NavHostController, teamMembersViewModel: TeamMembersViewModel) {

    var showCreate by remember { mutableStateOf(false) }
    val allItems by teamMembersViewModel.allItems.observeAsState(mutableListOf())
    var selectedItem by remember { mutableStateOf<TeamMember?>(TeamMember()) }


    Column {

        CreateTeamMemberDialog(visible = showCreate,
            onDismiss = {showCreate = false },
            onOK = { item -> teamMembersViewModel.upsert(item); showCreate = false; selectedItem = null},
            item = selectedItem)

        var list = allItems.sortedByDescending { item -> item.id }.toMutableList()
        ItemList(items = list,
                content = { RenderTeamMember(item = it) },
                onDelete = {it -> teamMembersViewModel.delete(it)},
                onEdit = { it -> selectedItem = it; showCreate = true})
    }

    FloatingButton(text="Add") {
        showCreate = true
    }

}


@Composable
fun RenderTeamMember(item: TeamMember) {

    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), horizontalArrangement = Arrangement.SpaceBetween)
    {
        Column {
            Text(
                text = "${item.name}",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Column {
            var col = Color(item.color)
            Icon(imageVector = Icons.Filled.Face, contentDescription = "", tint = col)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTeamMemberDialog(visible:Boolean = false,
                 onDismiss: () -> Unit = {},
                 onOK:(item: TeamMember) -> Unit = {},
                 item: TeamMember? = TeamMember(name = "")
)
{

    if(visible)
    {
        Dialog(onDismissRequest = onDismiss,
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
            )
        )
        {

            var updatedItem by remember { mutableStateOf(item ?: TeamMember(name = "")) }


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
                        TextField(value = updatedItem.name, onValueChange = { updatedItem = updatedItem.copy(name = it) }, modifier = Modifier.fillMaxWidth())
                    }

                    LabelledSegment(label = "Color") {
                        Row(modifier = Modifier.width(40.dp))
                        {
                            ColorPicker(selected = updatedItem.color, onSelect = {
                                updatedItem = updatedItem.copy(color = it)
                            })
                        }

                    }

                    Button(onClick = {onOK(updatedItem)},
                        shape = MaterialTheme.shapes.small)
                    {
                        Text("Add")
                    }

                }

            }

        }

    }

}






