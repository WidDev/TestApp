package com.example.testapp.shared

import androidx.compose.foundation.layout.fillMaxWidth
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
                       getLabel:(T?)->String? = {t->t?.id?.toString()?:""},
                       onSelect:(T)->Unit,
                       placeHolderText:String = "Select") where T:IIdentifiable
{
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded,
                           onExpandedChange = { newValue ->
                                expanded = newValue
                            },
                            modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = getLabel(selected) ?: "",
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            placeholder = {
                Text(text = placeHolderText)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach({
                DropdownMenuItem(text = {Text(getLabel(it)?:"")}, onClick = { onSelect(it); expanded = false }, modifier = Modifier.fillMaxWidth())
            })
        }

    }

}