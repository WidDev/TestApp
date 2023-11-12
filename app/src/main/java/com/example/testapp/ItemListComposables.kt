package com.example.testapp.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.testapp.interfaces.IIdentifiable
import com.example.testapp.models.TeamMember
import java.util.UUID

@Composable
fun QuickAddRow(list:MutableList<TeamMember>, addItem:(item: TeamMember) -> TeamMember)
{
    var txt by remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxWidth())
    {

        TextField(shape = RoundedCornerShape(2.dp),
            trailingIcon = {
                Button(shape = MaterialTheme.shapes.small, onClick = {
                    if(txt != "")
                    {
                        var item = TeamMember(UUID.randomUUID(), name=txt, team = null)
                        addItem(item)
                        txt = ""
                    }

                }) {
                    Text(text = "Add Action")
                }
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor =  Color.LightGray, //hide the indicator
                unfocusedIndicatorColor = Color.Transparent),
            value = txt,
            onValueChange = { txt = it})
    }

}

@Composable
fun <T:IIdentifiable> ItemList(items:MutableList<T>,
                               content:@Composable (T)->Unit,
                               modifier: Modifier = Modifier)
{
    LazyColumn (modifier = Modifier.fillMaxHeight())
    {
        items(items, {it.id}) { item -> Item(item, items, content) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T:IIdentifiable> Item(item: T, items:MutableList<T>, content:@Composable (T)->Unit)
{
    val context = LocalContext.current
    var show by remember { mutableStateOf(true) }
    val currentItem by rememberUpdatedState(item)
    var state = rememberDismissState(confirmValueChange = {
        if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
            items.remove(item)
            true
        } else false
    }, positionalThreshold = { 150.dp.toPx() } )
    SwipeToDismiss(state = state,
        background = { DismissBackground(dismissState = state) },
        dismissContent = { ItemContent(item = item, content) },
        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart))
}

@Composable
fun <T> ItemContent(item: T, content:@Composable (T)->Unit)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            content(item)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: DismissState)
{
    val color = when (dismissState.dismissDirection) {

        DismissDirection.StartToEnd -> Color(0xFFFF1744)
        DismissDirection.EndToStart -> Color(0xFF1DE9B6)
        null -> Color.Transparent
    }
    val direction = dismissState.dismissDirection

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (direction == DismissDirection.StartToEnd)
            Icon(   Icons.Filled.Delete,
                    tint = Color.White,
                    modifier = Modifier.fillMaxHeight().padding(5.dp),
                    contentDescription = "delete"
                )
        Spacer(modifier = Modifier)
        if (direction == DismissDirection.EndToStart)
            Icon(   Icons.Filled.CheckCircle,
                    tint = Color.White,
                    modifier = Modifier.fillMaxHeight().padding(5.dp),
                    contentDescription = "Archive"
        )
    }
}

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemBackground(state: DismissState)
{
    val color by animateColorAsState(
        when (state.targetValue) {
            DismissValue.Default -> Color.Transparent
            DismissValue.DismissedToEnd -> Color.Red
            DismissValue.DismissedToStart -> Color.Transparent
        }
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = color)
    ){
        Icon(Icons.Filled.Delete, contentDescription = "Localized description")
    }
}*/
