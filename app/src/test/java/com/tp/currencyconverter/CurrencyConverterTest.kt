package com.tp.currencyconverter

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Locale

class CurrencyConverterTest {

    @Test
    fun testConversionUsdToEur() {
        // 120 USD -> EUR
        val resultat = CurrencyConverterCalculator.convertir(120.0, "USD", "EUR")
        val formatted = String.format(Locale.US, "%.2f", resultat)
        assertEquals("102.56", formatted)
    }

    @Test
    fun testConversionEurToTnd() {
        // 100 EUR -> TND
        val resultat = CurrencyConverterCalculator.convertir(100.0, "EUR", "TND")
        val formatted = String.format(Locale.US, "%.2f", resultat)
        assertEquals("340.00", formatted)
    }

    @Test
    fun testConversionGbpToUsd() {
        // 50 GBP -> USD
        val resultat = CurrencyConverterCalculator.convertir(50.0, "GBP", "USD")
        val formatted = String.format(Locale.US, "%.2f", resultat)
        assertEquals("67.24", formatted)
    }

    @Test
    fun testConversionEurToEur() {
        // 10 EUR -> EUR
        val resultat = CurrencyConverterCalculator.convertir(10.0, "EUR", "EUR")
        val formatted = String.format(Locale.US, "%.2f", resultat)
        assertEquals("10.00", formatted)
    }
}
