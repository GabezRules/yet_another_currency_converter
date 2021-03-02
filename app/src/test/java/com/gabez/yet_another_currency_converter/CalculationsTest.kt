package com.gabez.yet_another_currency_converter

import com.gabez.yet_another_currency_converter.domain.calculations.CalculationsHelper
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.pow
import kotlin.math.roundToInt

class CalculationsTest {
    @Test
    fun calculationsTest(){
        val amount = 5f
        val midFirst = 1.3945798435f
        val midSecond = 0.55f

        val result = CalculationsHelper.getResult(amount, midFirst, midSecond)
        assertEquals(result.roundTo(2), 1.97f)
    }

    private fun Float.roundTo(numFractionDigits: Int): Float {
        val factor = 10.0.pow(numFractionDigits.toDouble())
        return ((this * factor).roundToInt() / factor).toFloat()
    }
}