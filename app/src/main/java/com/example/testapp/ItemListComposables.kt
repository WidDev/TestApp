package com.example.testapp.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.testapp.interfaces.IIdentifiable


@Composable
fun <T:IIdentifiable> ItemList(items:MutableList<T>,
                               content:@Composable (T)->Unit,
                               modifier: Modifier = Modifier,
                               onDelete:(T)->Unit = {},
                               onEdit:(T)->Unit = {})
{

    LazyColumn (modifier = Modifier.fillMaxHeight())
    {
        items(items, {it.id}) { item ->

            Item(item, items, content, onDelete, onEdit)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T:IIdentifiable> Item(item: T, items:MutableList<T>, content:@Composable (T)->Unit, onDelete:(T) -> Unit, onEdit:(T) -> Unit)
{
    val context = LocalContext.current
    var show by remember { mutableStateOf(true) }
    val currentItem by rememberUpdatedState(item)
    var state = rememberDismissState(confirmValueChange = {
        if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
            onDelete(item)
            true
        } else false
    }, positionalThreshold = { 150f } )
    SwipeToDismiss(state = state,
        background = { DismissBackground(dismissState = state) },
        dismissContent = { ItemContent(item = item, content, onEdit) },
        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart))
}

@Composable
fun <T> ItemContent(item: T, content:@Composable (T)->Unit, onEdit:(T)->Unit)
{
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .pointerInput(Unit){
                detectTapGestures(
                    onDoubleTap = {
                        onEdit(item)
                    })
            })
    {
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

        DismissDirection.StartToEnd -> Color(0xFFBB2C2C)
        DismissDirection.EndToStart -> Color(0xFF1DE93F)
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
