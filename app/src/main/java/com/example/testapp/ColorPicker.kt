package com.example.testapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPicker(selected:Color, onSelect:(col:Color) -> Unit)
{

    var expanded by remember { mutableStateOf(false) }
    var items = listOf(Color.Gray, Color.Red, Color.Magenta, Color.Green, Color.Blue, Color.Yellow)

    Column{
    Row(modifier = Modifier.fillMaxWidth().padding(5.dp).clickable { expanded = true }) {
        Box(modifier = Modifier.fillMaxWidth().background(selected).height(30.dp))
    }
    DropdownMenu(expanded = expanded,
                 onDismissRequest = { expanded = false },
                 modifier = Modifier.fillMaxWidth()) {


        items.forEach({
            /*DropdownMenuItem(text = {Text(it.toString())},
                                     onClick = { onSelect(it); expanded = false },
                                     modifier = Modifier.fillMaxWidth().height(20.dp))*/
            Row(
                modifier = Modifier.fillMaxWidth().padding(5.dp)
                    .clickable { expanded = false; onSelect(it) }) {
                Box(modifier = Modifier.fillMaxWidth().background(it).height(30.dp))
            }
        })


    }}


}