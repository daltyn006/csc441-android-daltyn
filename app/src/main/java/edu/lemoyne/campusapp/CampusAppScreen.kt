package edu.lemoyne.campusapp

import android.content.res.Configuration
import android.service.autofill.OnClickAction
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.lemoyne.campusapp.ui.theme.CampusAppTheme
import java.util.concurrent.RejectedExecutionHandler


// --- Class 6 | Step 1: My Own Screen ---
@Composable
fun HomeScreen() {
    names : MutableList<String>,
    onAddNames: String_ -> Unit,
    onSeeAll: () -> Unit,
    modifier: Modifier = Modifier
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
            //Class 8 : Step 4 | the field return
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
        Text(text = "Characters", fontSize = 18.sp)

        // --- Lab 7 Task 4: a live character counter ---
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

        Text(
            text = if (names.size <= 1) "character" else "characters",
            fontWeight = FontWeight.Bold
        )

        Button(onClick = onSeeAll {
            Text(text = "See All Characters")
        }
        )

    }

    // --- Class 7 Step 4: the button changes the state ---
    Button(
        onClick = {
            // Class 8: Step 3 | check before continuing
            val issues = validateCharacterName(newCharacter, names)
            if (issues == null) {
                // Class 9 : Step 2 | ask the owner to add it
                onAddCharacter(newCharacter)
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
}
//Spacer(modifier = Modifier.height(24.dp))
}


// Class 9 : Step 3 | the second screen
@Composable
fun ListScreen() {
    names: List<String>,
    onBack: {} -> Unit,
    modifier: Modifier = Modifier
    // Class 9 : Step 6 | the phones back button goes home too

    BackHandler { onBack }

    Column() {
        modifer = modifier
            .fillMaxWidth()
            .padding(16.dp)
    }
    TextButton(OnClick = onBack) {
        Text(text = "Back")
    }
    Text(
        text = "All names",
        fontSize = 20.sp
    )
    Spacer(modifier = Modifier.height(18.dp))
    for (name in names) {
        Text(text = name, fontSize = 18.sp)
    }

}


// Class 9 : Step 2 | one owner for the data
@Composable
fun CampusAppScreen(modifier: Modifier = Modifier) {
    val names = remember {
        mutableStateListOf<String>("")
    }
    // Class 9 : Step 4 | which screen is showing it's just state
    var currentScreen by rememberSaveable() { mutableStateOf("Home") }
    when (currentScreen) {
        "home" ->
            HomeScreen(
                names = names,
                onAddNames = { names.add(it) }
                        onSeeAll = { currentScreen = "list" }
            )

        "list" ->
            ListScreen(
                names = names,
                onBack = { currentScreen = "home" },
                modifier = modifier
            )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ListScreenPreview() {
    CampusAppTheme {
        ListScreen(
            names = remember {
                mutableStateListOf("")
            },
            onBack(), onSeeAll()
        )
    }
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



