package edu.lemoyne.campusapp

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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


// --- Class 8 - Step 2: one real book for trail names ---
const val MAX_NAME_LENGTH = 40
const val MIN_NAME_LENGTH = 1

// --- Class 6 | Step 1: My Own Screen ---
@Composable
fun HomeScreen(
    names: List<String>,
    onAddNames: (String) -> Unit,
    onSeeAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    //Class 7: Step 3 | what's typed lives in state
    var newCharacter by remember { mutableStateOf("") }
    // Class 8: Step 3 | check before continuing
    var errorMessage by remember { mutableStateOf<String?>(null) }

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
            .padding(24.dp)
    ) {
        // --- Class 6 - Step 4 | real styling ---
        Text(
            text = "Novel Progress", fontSize = 32.sp, fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Chapters written:",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = newCharacter,
            //Class 8 : Step 4 | the field return
            onValueChange = { input ->
                val truncated = input.take(MAX_NAME_LENGTH)
                newCharacter = truncated
                errorMessage = validateCharacterName(truncated, names)
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

        Spacer(modifier = Modifier.height(8.dp))

        // --- Class 7 Step 4: the button changes the state ---
        Button(
            onClick = {
                // Class 8: Step 3 | check before continuing
                onAddNames(newCharacter.trim())
                newCharacter = ""
                errorMessage = null
            },
            enabled = errorMessage == null && newCharacter.isNotBlank()
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

        // --- Lab 6 - Task 2: footer ---
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Last updated: October",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = "${names.size} " + if (names.size <= 1) "character" else "characters",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onSeeAll) {
            Text(text = "See All Names")
        }
    }
}


// Class 9 : Step 3 | the second screen
@Composable
fun ListScreen(
    names: List<String>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Class 9 : Step 6 | the phones back button goes home too
    BackHandler(onBack = onBack)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        TextButton(onClick = onBack) { Text(text = "Back") }
        Text(
            text = "All names",
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(18.dp))
        for (name in names) {
            Text(
                text = name,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
    }
}


// Class 9 : Step 2 | one owner for the data
@Composable
fun CampusAppScreen(modifier: Modifier = Modifier) {
    val names = remember {
        mutableStateListOf("Grant", "Lindesay")
    }
    // Class 9 : Step 4 | which screen is showing it's just state
    var currentScreen by rememberSaveable { mutableStateOf("home") }
    when (currentScreen) {
        "home" ->
            HomeScreen(
                names = names,
                onAddNames = { names.add(it) },
                onSeeAll = { currentScreen = "list" },
                modifier = modifier
            )

        "list" ->
            ListScreen(
                names = names,
                onBack = { currentScreen = "home" },
                modifier = modifier
            )
    }
}


// Preview Section

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ListScreenPreview() {
    CampusAppTheme {
        ListScreen(
            names = listOf("Grant", "Lindsey"),
            onBack = {}
        )
    }
}

// --- Class 6 - Step 2: preview ---
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CampusAppTheme {
        Surface {
            HomeScreen(
                names = listOf("Grant", "Jemima", "Linda"),
                onAddNames = {},
                onSeeAll = {}
            )
        }
    }
}




// Function Section



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


//allows characters aA through zZ and digits, corrected to have it check all chars after allowed special
//checks "oh something is wrong" then goes to say error -> hopefully faster on non-error
val normChars = Regex("^[A-Z][a-z]*([ '-][A-Z][a-z]*)*$")

fun validateCharacterName(input: String, existing: List<String>): String? {
    val name = input.trim()
    return when {
        name.isEmpty() -> "Enter living character"
        name.length > MAX_NAME_LENGTH -> "Limit to $MAX_NAME_LENGTH!"
        // Lab 8 : Task 1 | minimum length && checks
        name.length < MIN_NAME_LENGTH -> "Choose a name longer than: $MIN_NAME_LENGTH"

        // Lab 8 : Task 2 | proper naming functions
        !normChars.matches(name) -> {
            if (name.any { (!it.isLetter()) && (it != ' ') && (it != '\'') && (it != '-') }) {
                "Only regular characters allowed"
            } else {
                val words = name.split(' ', '\'', '-')
                val wrongCase = words.firstOrNull { word ->
                    word.isNotEmpty() && (!word.first().isUpperCase() || !word.drop(n = 1)
                        .all { it.isLowerCase() })
                }
                if (wrongCase != null) {
                    if (!wrongCase.first().isUpperCase()) {
                        "Check title case, first letter should be capitalized"
                    } else {
                        "Letters inside words must be lowercast"

                    }
                } else {
                    "Check Formatting"
                }
            }

        }

        existing.any { it.equals(name, ignoreCase = true) } -> "Name exists in list"
        else -> null
    }
}
