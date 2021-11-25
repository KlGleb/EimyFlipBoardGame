package at.gleb.composetest

import java.util.*

class Interactor(initField: Array<IntArray>? = null) {
    private var _field = Array(FIELD_SIZE) { IntArray(FIELD_SIZE) }
    private var _area = 0

    private val sol = Solution()

    init {
        if (initField != null) {
            _field = initField
        } else {
            for (i in 0 until FIELD_SIZE) {
                for (j in 0 until FIELD_SIZE) {
                    _field[i][j] = 0
                }
            }
        }
    }

    fun togglePoint(x: Int, y: Int) {
        if (x >= FIELD_SIZE || y >= FIELD_SIZE || x < 0 || y < 0) {
            return
        }

        val newArr = Array(FIELD_SIZE) { IntArray(FIELD_SIZE) }
        for (i in _field.indices) {
            for (j in _field[i].indices) {
                var a = _field[i][j]
                if (a == 2) a = 1
                newArr[i][j] = a

            }
        }

        when (newArr[x][y]) {
            0 -> newArr[x][y] = 1
            else -> newArr[x][y] = 0
        }

        _field = newArr

        val res = sol.maximalRectangle(newArr)
        _area = res.area

        if (res.area > 0) {
            for (i in res.x until res.x + res.w) {
                for (j in res.y until res.y + res.h) {
                    newArr[i][j] = 2;
                }
            }
        }
    }

    fun getField(): Array<IntArray> = _field
    fun getArea(): Int = _area

    companion object {
        const val FIELD_SIZE = 15
    }
}

/**
 * This is the O(MxN) solution from Leetcode (Dynamic Programming - Maximum Height at Each Point)
 * https://leetcode.com/problems/maximal-rectangle/solution/
 * with small additions that allows to find the coordinates
 */
private class Solution {
    fun maximalRectangle(matrix: Array<IntArray>): LeetcodeRes {
        val m = matrix.size
        val n: Int = matrix[0].size
        val left = IntArray(n) // initialize left as the leftmost boundary possible
        val right = IntArray(n)
        val height = IntArray(n)
        Arrays.fill(right, n) // initialize right as the rightmost boundary possible

        var x0 = -1;
        var y0 = -1;
        var w = -1;
        var leftY = -1;

        var maxarea = 0
        for (i in 0 until m) {
            var curLeft = 0
            var curRight = n
            // update height
            for (j in 0 until n) {
                if (matrix[i][j] == 1) height[j]++ else height[j] = 0
            }
            // update left
            for (j in 0 until n) {
                if (matrix[i][j] == 1) left[j] = Math.max(left[j], curLeft) else {
                    left[j] = 0
                    curLeft = j + 1
                }
            }
            // update right
            for (j in n - 1 downTo 0) {
                if (matrix[i][j] == 1) right[j] = Math.min(right[j], curRight) else {
                    right[j] = n
                    curRight = j
                }
            }
            // update area
            for (j in 0 until n) {

                val area = (right[j] - left[j]) * height[j]
                if (maxarea < area) {
                    w = height[j]
                    leftY = left[j]
                    x0 = i
                    y0 = j
                    maxarea = area
                }

            }
        }

        //We know the point. Now we can find the coordinates

        //top left square
        var topLeft = x0

        while (topLeft - 1 >= 0 && matrix[topLeft - 1][y0] == 1) {
            topLeft--
        }

        return LeetcodeRes(topLeft, leftY, w, maxarea / w, maxarea)
    }
}


private data class LeetcodeRes(val x: Int, val y: Int, val w: Int, val h: Int, val area: Int)

