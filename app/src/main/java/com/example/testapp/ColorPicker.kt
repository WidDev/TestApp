package com.example.testapp.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

fun Color.Companion.fromHex(color: String) = Color(android.graphics.Color.parseColor("#" + color))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPicker(selected:Color, onSelect:(col:Color) -> Unit)
{

    var expanded by remember { mutableStateOf(false) }
    var cols = listOf(  0xFF800000,
                        0xFF9A6324,
                        0xFF808000,
                        0xFF469990,
                        0xFF000075,
                        0xFF000000,
                        0xFFE6194B,
                        0xFFF58231,
                        0xFFFFE119,
                        0xFFBFEF34,
                        0xFF3CB44B,
                        0xFF42D4F4,
                        0xFF4363D8,
                        0xFF911EB4,
                        0xFFF032E6,
                        0xFFA9A9A9,
                        0xFFFABED4,
                        0xFFFFD8B1,
                        0xFFFFFAC8,
                        0xFFAAFFC3,
                        0xFFDCBEFF)
    var colCount = Math.sqrt(cols.count().toDouble()).toInt()
    var rowCount = cols.count()/colCount


    Column{
    Row(modifier = Modifier.fillMaxWidth().padding(5.dp).clickable { expanded = true }) {
        Box(modifier = Modifier.fillMaxWidth().background(selected).height(30.dp))
    }
    DropdownMenu(expanded = expanded,
                 onDismissRequest = { expanded = false }) {

        Column()
        {
            var index = 0
            while(index < cols.count())
            {
                Row()
                {
                    (0..colCount).forEach()
                    {
                        val color = if(index < cols.count()) Color(cols[index]) else Color.White
                        Box(modifier = Modifier.padding(2.dp).size(30.dp).background(color).clickable { onSelect(color); expanded = false })
                        index += 1
                    }

                }

            }


        }


    }


    }


}