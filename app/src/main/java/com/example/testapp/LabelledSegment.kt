package com.example.testapp.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun LabelledSegment(label:String, modifier: Modifier = Modifier, content:@Composable() ()->Unit)
{
    Column(modifier = modifier)
    {
        Text(label, fontWeight = FontWeight.Bold)
        content()
    }

}