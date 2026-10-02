package edu.lemoyne.campusapp

import android.content.res.Configuration
import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.lemoyne.campusapp.ui.theme.CampusAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampusAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(innerPadding))

//                    Class 5 | Step 6: My Greeting
//                    Greeting(
//                      name = "Daltyn",
//                      modifier = Modifier.padding(innerPadding),
//                  )
                }
            }
        }
    }
}

// --- Class 6 | Step 1: My Own Screen ---
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    // --- Lab 6 - Task 3:a picture of my own ---
    Image(
        painter = painterResource(id = R.drawable.busybee),
        contentDescription = "A busy bee",
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp) //sized up to be noticeable
    )


    // --- Class 6 - Step 3 | a column so things stack ---
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 24.dp, end = 48.dp)
    ) {
        // --- Class 6 - Step 4 | real styling ---
        Text(
            text = "Novel Progress", fontSize = 32.sp, fontWeight = FontWeight.Bold
        )

        Spacer(modifier = modifier.height(8.dp))

        Text(
            text = "Chapters written:",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))
        // --- Class 7 Step 3: the text field ---
        var newCharacter by remember{ mutableStateOf("")}

        OutlinedTextField(
            value = newCharacter,
            onValueChange = { newCharacter = it },
            label = { Text("Character Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Overall Progress:", fontSize = 18.sp)
        Text(text = "Plotline", fontSize = 18.sp)
        Text(text = "Points", fontSize = 18.sp)
        CounterDemo()
        Text(text = "Characters", fontSize = 18.sp)

        // --- Lab 6 - Task 2: footer ---
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Last updated: October",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        // --- Class 7 Step 2 : draw whatever is in the list ---
        val names = remember {
            mutableStateListOf("Grant", "Manada", "Lindesay")
        }

        Text(
            text = "${names.size} characters",
            fontWeight = FontWeight.Bold
        )
        for(name in names){ Text(text = name, fontSize = 18.sp) }

        // --- Class 7 Step 4: the button changes the state ---
        Button(onClick = {
            names.add(newCharacter)
            newCharacter = ""
        }) {
            Text("Add Character")
        }
    }
    Spacer(modifier = Modifier.height(24.dp))

}

// --- Class 6 - Step 2: preview ---
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    CampusAppTheme {
        Surface {
            HomeScreen()
        }

    }
}


//Class 5 | Step 6: My Greeting
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier){
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
// }


// ---Class 7 Step 1: a counter that remembers ---
@Composable
fun CounterDemo() {
    var count by remember { mutableStateOf(0)}
    Button(onClick = {
        count ++
        println("count is now $count")
    }) {
        Text("Tapped $count times")
    }
}


