package com.example.testapp.shared

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.testapp.interfaces.IIdentifiable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T>IdBasedPicklist(items:List<T>,
                       selected:T? = null,
                       getLabel:(T)->String = {t->t.id.toString()},
                       onSelect:(T)->Unit) where T:IIdentifiable
{
    var expanded by remember { mutableStateOf(false) }

    Text("EXPANDED:" + expanded.toString())

    ExposedDropdownMenuBox(expanded = expanded,
                            onExpandedChange = { newValue ->
                                expanded = newValue
                            }) {
        TextField(
            value = "",
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            placeholder = {
                Text(text = "Please select your gender")
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            items.forEach({

                DropdownMenuItem(text = {Text("HELLO")}, onClick = { onSelect(it) })
            })
        }
        
    }
    
}