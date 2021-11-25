package at.gleb.composetest

class Interactor(initField: Array<IntArray>? = null) {
    private var _field = Array(FIELD_WIDTH) { IntArray(FIELD_HEIGHT) }

    init {
        if (initField != null) {
            _field = initField
        } else {
            for (i in 0 until FIELD_WIDTH) {
                for (j in 0 until FIELD_HEIGHT) {
                    _field[i][j] = 0
                }
            }
        }
    }

    fun togglePoint(x: Int, y: Int) {
        val newArr = Array(FIELD_WIDTH) { IntArray(FIELD_HEIGHT) }
        for (i in _field.indices) {
            for (j in _field[i].indices) {
                newArr[i][j] = _field[i][j]
            }
        }

        when (newArr[x][y]) {
            0 -> newArr[x][y] = 1
            else -> newArr[x][y] = 0
        }

        _field = newArr
    }

    fun getField(): Array<IntArray> = _field

    companion object {
        const val FIELD_WIDTH = 3
        const val FIELD_HEIGHT = 3
    }
}


