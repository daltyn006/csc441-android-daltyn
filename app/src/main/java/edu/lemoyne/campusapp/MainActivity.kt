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
        var newCharacter by remember { mutableStateOf("") }

        // Class 8: Step 3 | check before continuing
        var errorMessage by remember { mutableStateOf<String?>(null) }

        OutlinedTextField(
            value = newCharacter,
            //Class 8 : Step 4 | the feild return
            onValueChange = {
                newCharacter = it.take(MAX_NAME_LENGTH)
                errorMessage = null
            },
            label = { Text("Character Name") },
            singleLine = true,
            isError = errorMessage != null,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Overall Progress:", fontSize = 18.sp)
        Text(text = "Plotline", fontSize = 18.sp)
        Text(text = "Plot Points", fontSize = 18.sp)
        CounterDemo()
        Text(text = "Characters", fontSize = 18.sp)

        // --- Lab 7 Task 4: a live characer counter ---
        Text(
            text = "${newCharacter.length} / $MAX_NAME_LENGTH",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        // --- Lab 6 - Task 2: footer ---
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Last updated: October",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        // --- Class 7 Step 2 : draw whatever is in the list ---
        val names = remember {
            mutableStateListOf("")
        }

        Text(
            text = if (names.size <= 1) "character" else "characters",
            fontWeight = FontWeight.Bold
        )
        for (name in names) {
            Text(text = name, fontSize = 18.sp)
        }

        // --- Class 7 Step 4: the button changes the state ---
        Button(
            onClick = {
                // Class 8: Step 3 | check before continuing
                val issues = validateCharacterName(newCharacter, names)
                if (issues == null) {
                    names.add(newCharacter.trim())
                    newCharacter = ""
                    errorMessage = null
                } else {
                    errorMessage = issues
                }
            },
            // Class 8 : Step 5 | informing the user
            enabled = newCharacter.isNotBlank()
        ) {
            Text("Add Character")
        }
            // Class 8 : Step 4 | error message display
        errorMessage?.let { message ->
            Text(
               text = message,
               color = MaterialTheme.colorScheme.error,
               fontSize = 14.sp
            )
        }

        Button(onClick = {
            if (names.isNotEmpty()) {
                names.removeAt(names.lastIndex)
            }
        }) { Text("Remove Character") }
        // --- Lab 3 Task 4: a live character counter ---
        Button(onClick = {
            names.clear()
        }) { Text("Clear Characters") }
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

// ---Class 7 Step 1: a counter that remembers ---
@Composable
fun CounterDemo() {
    var count by remember { mutableStateOf(0) }
    Button(onClick = {
        count++
        println("count is now $count")
    }) {
        Text("$count")
    }
}

// --- Class 8 - Step 2: one real book for trail names ---
const val MAX_NAME_LENGTH = 40
const val MIN_NAME_LENGTH = 1

//allows characters aA through zZ and digits
val normChars = Regex("^[a-zA-Z0-9 '-]+$")

fun validateCharacterName(input: String, existing: List<String>): String? {
    val name = input.trim()
    return when {
        name.isEmpty() -> "Enter living character"
        name.length > MAX_NAME_LENGTH -> "Limit to $MAX_NAME_LENGTH!"
        // Lab 8 : Task 1 | minimum length && checks
        name.length < MIN_NAME_LENGTH -> "Choose a name longer than: $MIN_NAME_LENGTH"
        // Lab 8 : Task 2 | proper naming functions
        !normChars.matches(name) -> "use non-special characters"
        name.first().isLowerCase() -> "Use Correct Title"
        existing.any { it.equals(name, ignoreCase = true) } -> "$name exists in the list"
        else -> null
    }
}



