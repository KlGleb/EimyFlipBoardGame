package at.gleb.composetest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.gleb.composetest.Interactor

class MainViewModel : ViewModel() {
    val fieldLiveData: LiveData<Array<IntArray>>
        get() = _fieldLiveData

    val areaLiveData: LiveData<Int>
        get() = _areaLiveData

    private val _fieldLiveData: MutableLiveData<Array<IntArray>> = MutableLiveData()
    private val _areaLiveData: MutableLiveData<Int> = MutableLiveData(0)
    private val interactor = Interactor(_fieldLiveData.value)

    init {
        _fieldLiveData.postValue(interactor.getField())
    }

    fun clickOn(x: Int, y: Int) {
        interactor.togglePoint(x, y)
        _fieldLiveData.postValue(interactor.getField())
        _areaLiveData.postValue(interactor.getArea())
    }
}