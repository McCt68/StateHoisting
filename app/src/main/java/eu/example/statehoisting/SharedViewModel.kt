package eu.example.statehoisting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

// Having the state in a viewModel is better than using rememberSaveAble
class SharedViewModel: ViewModel() {

	var countFromViewModelState by mutableStateOf(0)

	fun increaseCount(){
		countFromViewModelState ++
	}
}