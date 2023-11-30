package com.example.testapp.views

import android.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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

    Column {

        CreateTeamDialog(visible = showCreate,
            onDismiss = {showCreate = false },
            onOK = { name:String -> teamMembersViewModel.insert(TeamMember(name = name, color = Color.BLUE)); showCreate = false})

        var list = allItems.sortedByDescending { item -> item.id }.toMutableList()
        ItemList(items = list, content = { RenderTeamMember(item = it) }, onDelete = {it -> teamMembersViewModel.delete(it)})
    }

    FloatingButton(text="Add") {
        showCreate = true
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTeamMemberDialog(visible:Boolean = false,
                     onDismiss: () -> Unit = {},
                     onOK:(str:String) -> Unit = {})
{
    var name by remember { mutableStateOf("") }
    var sel: Int by remember {mutableStateOf(Color.BLUE)}

    if(visible)
    {
        Dialog(onDismissRequest = onDismiss,
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
            )
        )
        {
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

                    Text("Selected ${sel}")

                    LabelledSegment(label = "Name", modifier = Modifier.fillMaxWidth())
                    {
                        TextField(value = name, onValueChange = { name = it }, modifier = Modifier.fillMaxWidth())
                    }

                    LabelledSegment(label = "Color") {
                        Row(modifier = Modifier.width(40.dp))
                        {
                            ColorPicker(selected = sel, onSelect = {
                                sel = it
                            })
                        }

                    }

                    Button(onClick = {onOK(name)},
                        shape = MaterialTheme.shapes.small)
                    {
                        Text("Add")
                    }

                }

            }

        }

    }

}






