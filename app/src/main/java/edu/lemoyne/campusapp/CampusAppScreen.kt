package edu.lemoyne.campusapp

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
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
    sliderPosition: Float,
    counterState: Int,
    onIncrementCounter: () -> Unit,
    onProgressChange: (Float) -> Unit,
    onAddNames: (String) -> Unit,
    onSeeAll: () -> Unit,
    onAbout: () -> Unit,
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
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // --- Class 6 - Step 4 | real styling ---
        Text(
            text = "Novel Progress", fontSize = 32.sp, fontWeight = FontWeight.Bold
        )

        Text(
            text = "Chapters written:",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Lab 7 Task 4: a live character counter ---
        Text(
            text = "Total Character Limit: ${newCharacter.length} / $MAX_NAME_LENGTH",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(4.dp))

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
        // Class 8 : Step 4 | error message display
        errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp
            )
        }

        Text(
            text = "${names.size} " + if (names.size == 1) "character" else "characters",
            fontWeight = FontWeight.Bold
        )


        Spacer(modifier = Modifier.height(4.dp))

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


        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Overall Progress:", fontSize = 18.sp)
        Slider(
            value = sliderPosition,
            onValueChange = onProgressChange,
            valueRange = 0f..100f,
            steps = 99
        )
        // display the slider position
        Text(
            text = "${sliderPosition.toInt()}%",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = "Plot Points",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        //Revised counter demo to have a reliable state
        CounterDemo(count = counterState, onIncrement = onIncrementCounter)

        Text(
            text = "Themes",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Horizontal pager using remember for single-session page state
        // in progress, resuming at a later time
//        val plotlines = remember { mutableStateListOf<String>() }
//        val pagerState = rememberPagerState(pageCount = { plotlines.size + 1 })
//
//        HorizontalPager(
//            state = pagerState,
//            modifier = Modifier.fillMaxWidth()
//        ) { onValueChange ->
//            OutlinedTextField(
//                verifyTheme(input = input)
//                value = plotlines.getOrNull(pageIndex)?: "H",
//                onValueChange = { input ->
//                    val verified = verifyTheme(input)
//                    if (pageIndex < plotlines.size) {
//                        plotlines[pageIndex] = verified?: input
//                    }
//                    if (verified?.isNotEmpty() ?: false) {
//                        plotlines.add(verified)
//                    }
//                },
//                label = { Text("Theme / Plotline ${pageIndex + 1}") },
//                singleLine = true,
//                modifier = Modifier.fillMaxWidth()
//            )
//        }

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "${names.size} " + if (names.size == 1) "total character" else "total characters",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onSeeAll) {
            Text(text = "See All Names")
        }
        Button(onClick = onAbout) {
            Text(text = "About")
        }

        // Pushes footer to the bottom of the screen
        Spacer(modifier = Modifier.weight(1f))

        // --- Lab 6 - Task 2: footer ---
        Text(
            text = "Last updated: October",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


// Class 9 : Step 3 | the second screen
@Composable
fun ListScreen(
    names: List<String>,
    onBack: () -> Unit,
    onRemove: (String) -> Unit,
    onRemoveAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Class 9 : Step 6 | the phones back button goes home too
    BackHandler(onBack = onBack)

    var search by remember { mutableStateOf("") }
    val filteredNames = names.filter { name ->
        name.contains(search.trim(), ignoreCase = true)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        TextButton(onClick = onBack) { Text(text = "Back") }
        Text(
            text = "All names",
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Search bar for "See All Names" Screen
        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            label = { Text("Search Names") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Lab 9 : Step 1 | count on the list screen
        Text(
            text = "total: ${names.size}" + if (search.isNotBlank()) " (${filteredNames.size} matching)" else "",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(18.dp))
        // Lab 10 : Task 2 | remove all via the owner
        if (names.isNotEmpty()) {
            TextButton(onClick = onRemoveAll) {
                Text(
                    text = "Remove All",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        Spacer(modifier = Modifier.height(18.dp))
        // Class 10 : Step 5 | empty case
        if (names.isEmpty()) {
            Text(
                text = "No Names Exist Yet.",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            // Lab 10 : Task 1 | Obvious home button on List Screen
            Button(onClick = onBack) {
                Text(text = "Home")
            }
        } else if (filteredNames.isEmpty()) {
            Text(
                text = "No names match $search",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            // Class 10 : Step 2 | Lazy Columns
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(filteredNames) { name ->
                    Callbacks(
                        name = name,
                        onRemove = { onRemove(name) }
                    )
                }
            }
        }
    }
}

// Lab 9 : Task 2 | about screen
@Composable
fun AboutScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    BackHandler(onBack = onBack)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        TextButton(onClick = onBack) {
            Text("Back")
        }
        Text(
            text = "About",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Simple spreadsheet to show for the amount of characters in my novel.")
        Text(text = "Built for CSC 441 by 'To Be Determined'.")
        Text(text = "Future Plans -> \nMultiple Sections\nComplexity in the code")
        Text(text = "Fields need to be truncated and more focused to one aspect of writing")
    }
}


// Class 9 : Step 2 | one owner for the data
@Composable
fun CampusAppScreen(modifier: Modifier = Modifier) {
    // Class 10 : Step 1 | list too long for screen
//    val names = remember {
//        mutableStateListOf("")
//    }
    val names = remember {
        mutableStateListOf<String>()
    }
    // Class 9 : Step 4 | which screen is showing it's just state
    var currentScreen by rememberSaveable { mutableStateOf("home") }
    var sliderPosition by rememberSaveable { mutableFloatStateOf(0f) }
    var counterState by rememberSaveable { mutableStateOf(0) }

    when (currentScreen) {
        "home" ->
            HomeScreen(
                names = names,
                onAddNames = { names.add(it) },
                sliderPosition = sliderPosition,
                counterState = counterState,
                onIncrementCounter = { counterState++ },
                onProgressChange = { sliderPosition = it },
                onSeeAll = { currentScreen = "list" },
                onAbout = { currentScreen = "about" },
                modifier = modifier
            )

        "list" ->
            ListScreen(
                names = names,
                onBack = { currentScreen = "home" },
                onRemove = { names.remove(it) },
                onRemoveAll = { names.clear() },
                modifier = modifier
            )

        "about" ->
            AboutScreen(
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
            onRemove = {},
            onRemoveAll = {},
            onBack = {}
        )
    }

}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AboutScreenPreview() {
    CampusAppTheme {
        AboutScreen(
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
                sliderPosition = 50f,
                counterState = 0,
                onIncrementCounter = {},
                onProgressChange = {},
                onAddNames = {},
                onAbout = {},
                onSeeAll = {}
            )
        }
    }
}


// Functional Backend Section


// ---Class 7 Step 1: a counter that remembers ---
@Composable
fun CounterDemo(
    count: Int,
    onIncrement: () -> Unit
) {
    Button(onClick = onIncrement) {
        Text("$count")
    }
}


// Class 10 : Step 3 | One row, its own composable
@Composable
fun Callbacks(
    name: String,
    onRemove: () -> Unit
) {
    // Class 10 : Step 4 | remove button
    TextButton(onClick = onRemove) {
        Text("Remove Name")
    }
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(
                horizontal = 24.dp, vertical = 12.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
        }
    }
}


//allows characters aA through zZ and digits, corrected to have it check all chars after allowed special
//checks for "oh something is wrong" then goes to say error -> hopefully faster than always checking for specific error
val normChars = Regex("^[A-Z][a-z]*([ '-][A-Z][a-z]*)*$")

fun validateCharacterName(input: String, existing: List<String>): String? {
    val name = input.trim()
    return when {
        name.isEmpty() -> null
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


fun verifyTheme(input: String): String? {
    val name = input.trim()
    return when {
        name.isEmpty() -> null
        name.length > MAX_NAME_LENGTH -> "Limit to $MAX_NAME_LENGTH!"
        else -> null
    }
}