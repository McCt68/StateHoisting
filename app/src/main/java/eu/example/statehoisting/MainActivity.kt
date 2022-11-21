package eu.example.statehoisting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.example.statehoisting.ui.theme.StateHoistingTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			StateHoistingTheme {
				// A surface container using the 'background' color from the theme
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colors.background
				) {
					Column(
						modifier = Modifier.fillMaxSize(),
						verticalArrangement = Arrangement.Center,
						horizontalAlignment = Alignment.CenterHorizontally
					) {

						// I have the state defined outside the calling function
						// This will not survive screen rotation
						var count by remember { mutableStateOf(0) }

						// Passing the state with arguments, and using a lambda to change it
						// We can move the lambda parameter outside the function, because its the last parameter
						MyButton(currentCount = count) {
							count = it + 1 }
						Spacer(modifier = Modifier.height(8.dp))

						// I have the state defined outside the calling function
						// This will survive screen rotation
						var countSaved by rememberSaveable { mutableStateOf(0) }

						// Passing the state with parameters, and using a lambda to change it
						// We can move the lambda parameter outside the function, because its the last parameter
						MyButton(currentCount = countSaved){countSaved = it + 1}

						Spacer(modifier = Modifier.height(8.dp))
						
						// Doing the same but getting the state from viewModel
						// And using an event in the viewModel to change the state
						// Getting a viewModel instance - remember to add dependency's in gradle
						val viewModel = viewModel<SharedViewModel>()
						var countStateFromViewModel = viewModel.countFromViewModelState

						MyButton(currentCount = countStateFromViewModel){
							viewModel.increaseCount()
						}
					}
				}
			}
		}
	}
}

// A stateless composable function - It gets the state via parameters when we call the function
// And then send event's to manipulate the state the calling function
@Composable
fun MyButton(
	currentCount: Int,
	updateCount: (Int) -> Unit
) {
	Button(
		// Event we send to calling function
		onClick = { updateCount(currentCount) },
		contentPadding = PaddingValues(16.dp),
		border = BorderStroke(10.dp, Color.Black),
		colors = ButtonDefaults.textButtonColors(
			backgroundColor = Color.LightGray,
			contentColorFor(backgroundColor = Color.White)
		)
	) {
		Text(
			text = "Count is ${currentCount}",
			style = MaterialTheme.typography.h3,
			modifier = Modifier.padding(5.dp)
		)

	}
}