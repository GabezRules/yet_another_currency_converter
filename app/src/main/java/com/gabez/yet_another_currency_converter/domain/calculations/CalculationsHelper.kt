package com.gabez.yet_another_currency_converter.domain.calculations

import kotlin.math.pow
import kotlin.math.roundToInt

class CalculationsHelper {
    companion object{
        fun getResult(amount: Float, midFirst: Float, midSecond: Float): Float{
            var first = midFirst.roundTo(3)
            var second = midSecond.roundTo(3)

            first *= 1000
            second *= 1000

            second /= first
            second *= amount

            return second.roundTo(2)
        }

        private fun Float.roundTo(numFractionDigits: Int): Float {
            val factor = 10.0.pow(numFractionDigits.toDouble())
            return ((this * factor).roundToInt() / factor).toFloat()
        }
    }
}