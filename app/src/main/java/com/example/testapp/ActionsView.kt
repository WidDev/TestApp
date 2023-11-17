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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.testapp.ColorPicker
import com.example.testapp.LabelledSegment
import com.example.testapp.models.TeamMember
import com.example.testapp.models.ToDo
import com.example.testapp.shared.FloatingButton
import com.example.testapp.shared.IdBasedPicklist
import com.example.testapp.shared.ItemList
import com.example.testapp.viewmodels.ItemListViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun ActionsView(navHostController: NavHostController?,
                       listViewModel: ItemListViewModel<ToDo>,
                       teamMembersViewModel:ItemListViewModel<TeamMember>)
{
    var showCreate by remember { mutableStateOf(false) }
    val mod = Modifier

    var sel:Color by remember {mutableStateOf(Color.Red)}
    Row(modifier = Modifier.width(40.dp))
    {
        ColorPicker(selected = sel, onSelect = {
            sel = it
        })
    }



    Column(modifier = Modifier.fillMaxWidth()) {

        CreateDialog(visible = showCreate,
                    onDismiss = {showCreate = false },
                    onOK = { name:String -> listViewModel.addItem(ToDo(UUID.randomUUID(), txt = name)); showCreate = false},
                    teamMembersList = teamMembersViewModel)



        ItemList(items = listViewModel.items, content = { RenderToDo(item = it) })

    }


    FloatingButton {
        showCreate = true
    }
}

@Preview(showBackground = true)
@Composable
fun ActionsViewPreview()
{
    ActionsView(navHostController = null, listViewModel = ItemListViewModel<ToDo>(), teamMembersViewModel = ItemListViewModel<TeamMember>())
}

@Composable
fun RenderToDo(item: ToDo)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            if(item.owner != null)
            {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "${item.txt}")
                    Icon(imageVector = Icons.Filled.Face, contentDescription = "", tint = item.owner.color)
                }
            }


        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDialog(visible:Boolean = false,
                 onDismiss: () -> Unit = {},
                 onOK:(str:String) -> Unit = {},
                 teamMembersList:ItemListViewModel<TeamMember>)
{
    var name by remember { mutableStateOf("") }
    var teamMember: TeamMember? by remember {mutableStateOf(null)}

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



                    LabelledSegment(label = "Name", modifier = Modifier.fillMaxWidth())
                    {
                        TextField(value = name, onValueChange = { name = it }, modifier = Modifier.fillMaxWidth())
                    }

                    LabelledSegment(label = "Team Member") {
                        IdBasedPicklist(selected = teamMember,
                                        items = teamMembersList.items,
                                        getLabel = { it -> it?.name ?: ""},
                                        onSelect = { it -> teamMember = it})
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
