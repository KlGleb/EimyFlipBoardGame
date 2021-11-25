package at.gleb.composetest.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.gleb.composetest.Interactor
import kotlin.random.Random

class MainViewModel : ViewModel() {
    val testTextLiveData = MutableLiveData(Color.Red)

    val colsCount = Interactor.FIELD_WIDTH
    val rowsCount = Interactor.FIELD_HEIGHT

    val fieldState: LiveData<Array<IntArray>>
        get() = _fieldState

    private val _fieldState: MutableLiveData<Array<IntArray>> = MutableLiveData()
    private val interactor = Interactor(_fieldState.value)


    init {
        _fieldState.postValue(interactor.getField())
    }

    fun clickOn(x: Int, y: Int) {
        interactor.togglePoint(x, y)
        _fieldState.postValue(interactor.getField())
        testTextLiveData.postValue(randomColor())
    }

    private fun randomColor(): androidx.compose.ui.graphics.Color {
        return Color(
            red = Random.nextFloat(),
            green = Random.nextFloat(),
            blue = Random.nextFloat()
        )
    }
}