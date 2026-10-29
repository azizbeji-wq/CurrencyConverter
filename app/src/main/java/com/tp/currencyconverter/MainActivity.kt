package com.tp.currencyconverter

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tp.currencyconverter.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupSpinners()
        setupKeypad()
        setupActions()
    }

    /**
     * Initialise et remplit les deux Spinner avec la liste des devises.
     * Source par défaut: USD, Cible par défaut: EUR.
     */
    private fun setupSpinners() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.devises,
            R.layout.spinner_item,
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spDeviseSource.adapter = adapter
        binding.spDeviseCible.adapter = adapter

        // Sélection par défaut : USD pour la source, EUR pour la cible
        val devisesArray = resources.getStringArray(R.array.devises)
        val posUsd = devisesArray.indexOf("USD").takeIf { it >= 0 } ?: 0
        val posEur = devisesArray.indexOf("EUR").takeIf { it >= 0 } ?: 0

        binding.spDeviseSource.setSelection(posUsd)
        binding.spDeviseCible.setSelection(posEur)

        // Écouteur de changement de sélection pour la conversion automatique
        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                autoConvertir()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.spDeviseSource.onItemSelectedListener = listener
        binding.spDeviseCible.onItemSelectedListener = listener
    }

    /**
     * Configure les clics sur les touches du clavier numérique.
     */
    private fun setupKeypad() {
        binding.btn0.setOnClickListener { ajouterCaractere("0") }
        binding.btn1.setOnClickListener { ajouterCaractere("1") }
        binding.btn2.setOnClickListener { ajouterCaractere("2") }
        binding.btn3.setOnClickListener { ajouterCaractere("3") }
        binding.btn4.setOnClickListener { ajouterCaractere("4") }
        binding.btn5.setOnClickListener { ajouterCaractere("5") }
        binding.btn6.setOnClickListener { ajouterCaractere("6") }
        binding.btn7.setOnClickListener { ajouterCaractere("7") }
        binding.btn8.setOnClickListener { ajouterCaractere("8") }
        binding.btn9.setOnClickListener { ajouterCaractere("9") }
        binding.btnDot.setOnClickListener { ajouterCaractere(".") }

        // Touche C : remet le montant à "0" et réinitialise le résultat
        binding.btnC.setOnClickListener {
            binding.tvMontant.text = getString(R.string.default_amount)
            binding.tvResultat.text = getString(R.string.default_result)
        }
    }

    /**
     * Configure les actions sur les boutons principaux (Convertir, Inverser).
     */
    private fun setupActions() {
        binding.btnConvertir.setOnClickListener {
            lancerConversion(afficherToastSiZero = true)
        }

        binding.btnInverser.setOnClickListener {
            val posSource = binding.spDeviseSource.selectedItemPosition
            val posCible = binding.spDeviseCible.selectedItemPosition

            binding.spDeviseSource.setSelection(posCible)
            binding.spDeviseCible.setSelection(posSource)

            autoConvertir()
        }
    }

    /**
     * Ajoute un caractère au montant saisi en respectant les règles de gestion :
     * - Limite à 10 caractères.
     * - Un seul point décimal autorisé.
     * - Remplacement du 0 initial.
     */
    private fun ajouterCaractere(c: String) {
        val actuel = binding.tvMontant.text.toString()

        // Contrainte : 10 caractères max
        if (actuel.length >= 10) return

        val nouveauText = when {
            c == "." -> {
                if (actuel.contains(".")) return
                if (actuel.isEmpty() || (actuel == "0")) "0." else "$actuel."
            }
            actuel == "0" -> c
            else -> actuel + c
        }

        binding.tvMontant.text = nouveauText
        autoConvertir()
    }

    /**
     * Effectue la conversion si un montant est supérieur à 0 (sans Toast)
     */
    private fun autoConvertir() {
        lancerConversion(afficherToastSiZero = false)
    }

    /**
     * Lance la conversion et met à jour le résultat.
     */
    private fun lancerConversion(afficherToastSiZero: Boolean) {
        val texteMontant = binding.tvMontant.text.toString()
        val montant = texteMontant.toDoubleOrNull() ?: 0.0

        if (montant == 0.0) {
            if (afficherToastSiZero) {
                Toast.makeText(this, R.string.toast_enter_amount, Toast.LENGTH_SHORT).show()
            }
            binding.tvResultat.text = getString(R.string.default_result)
            return
        }

        val source = binding.spDeviseSource.selectedItem?.toString() ?: "USD"
        val cible = binding.spDeviseCible.selectedItem?.toString() ?: "EUR"

        val resultat = convertir(montant, source, cible)
        binding.tvResultat.text = String.format(Locale.US, "%.2f", resultat)
    }

    /**
     * Effectue un calcul de conversion via l'euro comme devise pivot.
     */
    fun convertir(montant: Double, source: String, cible: String): Double {
        return CurrencyConverterCalculator.convertir(montant, source, cible)
    }
}
