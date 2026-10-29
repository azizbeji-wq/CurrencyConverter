package com.tp.currencyconverter

/**
 * Calculateur de conversion de devises utilisant l'Euro (EUR) comme devise pivot.
 */
object CurrencyConverterCalculator {

    // Taux de change fixes exprimés pour 1 EUR
    val taux = mapOf(
        "EUR" to 1.0,
        "USD" to 1.17,
        "TND" to 3.40,
        "GBP" to 0.87,
        "JPY" to 172.50,
        "CAD" to 1.62,
    )

    /**
     * Convertit un montant d'une devise source vers une devise cible.
     */
    fun convertir(montant: Double, source: String, cible: String): Double {
        if (source == cible) return montant

        val tauxSource = taux[source] ?: 1.0
        val tauxCible = taux[cible] ?: 1.0

        val montantEur = montant / tauxSource
        return montantEur * tauxCible
    }
}
