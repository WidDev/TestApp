package com.example.testapp.views

import android.graphics.Color
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.testapp.dal.entities.TeamMember
import com.example.testapp.dal.entities.Todo
import com.example.testapp.dal.entities.TodoAndOwner
import com.example.testapp.shared.FloatingButton
import com.example.testapp.shared.IdBasedPicklist
import com.example.testapp.shared.ItemContent
import com.example.testapp.shared.ItemList
import com.example.testapp.shared.LabelledSegment
import com.example.testapp.viewmodels.TeamMembersViewModel
import com.example.testapp.viewmodels.TodosViewModel
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable
import androidx.compose.ui.graphics.Color as JColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun TodosView(
    navHostController: NavHostController?,
    todosViewModel: TodosViewModel,
    teamMembersViewModel: TeamMembersViewModel
) {
    var showCreate by remember { mutableStateOf(false) }
    var showDrag by remember { mutableStateOf(false) }
    val allTodos by todosViewModel.allTodos.observeAsState(mutableListOf())

    var selectedItem by remember { mutableStateOf<Todo?>(Todo(txt = "")) }


    Column(modifier = Modifier.fillMaxWidth()) {

        CreateDialog(
            visible = showCreate,
            onDismiss = { showCreate = false },
            onOK = {
                if (it != null) todosViewModel.upsertTodo(it); showCreate = false; selectedItem =
                null
            },
            teamMembersList = teamMembersViewModel,
            item = selectedItem
        )
        var list = allTodos.sortedByDescending { todo -> todo.todo.id }.map({ it -> it.todo })
            .toMutableList()
        if(showDrag)
        {
            DraggableList(todosViewModel, onEdit = { it -> selectedItem = it; showCreate = true })
        }
        else
        {
            ItemList(items = list,
                content = { RenderToDo(item = it, items = allTodos) },
                onDelete = {it -> todosViewModel.deleteTodo(it)},
                onEdit = { it -> selectedItem = it; showCreate = true }
            )
        }



    }


    FloatingButton(text = "Add") {
        showCreate = true
    }
    FloatingButton(text = "Switch", modifier = Modifier.offset(x = -80.dp)) {
        showDrag = !showDrag
    }
    /*FloatingButton(text = "Move Up", modifier = Modifier.offset(x = -160.dp)) {
        todosViewModel.reorderItems(0,1)
    }*/

}

@Composable
fun DraggableList(todosViewModel: TodosViewModel, onEdit:(Todo)->Unit) {

    val allTodos by todosViewModel.allTodos.observeAsState(mutableListOf())
    val data = remember { mutableStateOf(List(100) { Todo(id = it, txt = "$it") }) }
    val state = rememberReorderableLazyListState(onMove = { from, to ->
        todosViewModel.reorderItems(from.index, to.index)
    })
    LazyColumn(
        state = state.listState,
        modifier = Modifier
            .reorderable(state)
            .detectReorderAfterLongPress(state)
            .fillMaxHeight()
    ) {
        items(allTodos, { it.todo.id }) { item ->
            ReorderableItem(
                state, key = item.todo.id
            ) { isDragging ->
                val elevation = animateDpAsState(if (isDragging) 24.dp else 0.dp)

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .shadow(elevation.value)
                ) {
                    ItemContent(item = item.todo,
                        content = { RenderToDo(item = item.todo, items = allTodos) },
                        onEdit = { onEdit(item.todo) })

                }
            }
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun ActionsViewPreview()
{
TodosView(navHostController = null, listViewModel = ItemListViewModel<Todo>(), teamMembersViewModel = ItemListViewModel<TeamMember>())
}*/

@Composable
fun RenderToDo(item: Todo, items: List<TodoAndOwner>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val todoAndOwner: TodoAndOwner? = items.find({ t -> t.todo.id == item.id })


        Column {
            Text(
                text = "${item.txt}",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Column {
            if (todoAndOwner?.owner != null) {
                var col = JColor(todoAndOwner.owner?.color ?: Color.DKGRAY)
                Row {
                    Text(text = "${todoAndOwner.owner?.name}")
                    Icon(
                        imageVector = Icons.Filled.Face,
                        contentDescription = "",
                        tint = col,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )
                }

            }

        }

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDialog(
    visible: Boolean = false,
    onDismiss: () -> Unit = {},
    onOK: (todo: Todo?) -> Unit = {},
    teamMembersList: TeamMembersViewModel,
    item: Todo? = Todo(txt = "")
) {

    if (visible) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true
            )
        )
        {

            var updatedItem by remember { mutableStateOf(item ?: Todo(txt = "")) }

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(50.dp)
                )
                {

                    LabelledSegment(label = "Name", modifier = Modifier.fillMaxWidth())
                    {
                        TextField(
                            value = updatedItem.txt,
                            onValueChange = { updatedItem = updatedItem.copy(txt = it) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }


                    val teamMembers: List<TeamMember> =
                        teamMembersList.allItems.observeAsState().value ?: listOf()
                    val sel = teamMembers.find { o: TeamMember -> o.id == updatedItem.owner }

                    LabelledSegment(label = "Team Member") {
                        IdBasedPicklist<TeamMember>(selected = sel,
                            items = teamMembers,
                            getLabel = { it -> it?.name ?: "" },
                            onSelect = { it -> updatedItem = updatedItem.copy(owner = it.id) })
                    }

                    /*LabelledSegment(label = "Color") {
                        Row(modifier = Modifier.width(40.dp))
                        {
                            ColorPicker(selected = updatedTodo.co, onSelect = {
                                sel = it
                                ColorPicker(selected = color, onSelect = {
                                    color = it
                                })
                            }

                        }
                    }*/

                    Row {
                        Button(
                            onClick = { onOK(updatedItem) },
                            shape = MaterialTheme.shapes.small
                        )
                        {
                            Text("Ok")
                        }
                        Button(
                            onClick = { onOK(null) },
                            shape = MaterialTheme.shapes.small
                        )
                        {
                            Text("Cancel")
                        }
                    }


                }

            }

        }

    }

}

