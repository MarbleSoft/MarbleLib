package com.marble.lib.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.marble.lib.demo.ui.theme.PairViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PairViewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(this,"Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(from: Activity?, name: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Button(onClick = {
//            showActivity(from,WidgetExample::class.java)
//            val i =
            from?.startActivity(Intent(from,WidgetExample::class.java))
        }){
            Text(
                text = "PairView示例",
                modifier = modifier
            )
        }
        Button(onClick = {
//            showActivity(from,LabelLayoutExample.class)
            from?.startActivity(Intent(from,LabelLayoutExample::class.java))
        }){
            Text(
                text = "LabelView示例",
                modifier = modifier
            )
        }
    }
}

fun showActivity(from:Activity?,act:Class<Activity>){
    val i = Intent(from,act)
    from?.startActivity(i)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PairViewTheme {
        Greeting(null,"Android preview")
    }
}