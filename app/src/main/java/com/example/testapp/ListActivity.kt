package com.example.testapp

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.testapp.ui.theme.TestAppTheme

class ListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        setContent {
            TestAppTheme {
                MainView();
            }
        }
    }

    @Composable
    public fun MainView()
    {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.Red) {
            Text(text = "HELLO WORLD", modifier = Modifier.fillMaxWidth())
        }
    }

    @Preview
    @Composable
    public fun MainViewPreview()
    {
        MainView();
    }


}