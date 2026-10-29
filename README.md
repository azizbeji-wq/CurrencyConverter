# 💱 Currency Converter - Application Android (Kotlin)

Application Android de conversion de devises développée en **Kotlin** dans le cadre du TP. L'application permet de convertir en temps réel des montants entre plusieurs monnaies basées sur des taux fixes avec l'Euro (EUR) comme devise pivot.

---

## 📱 Aperçu de l'Interface

L'application est constituée d'un écran unique divisé en deux zones distinctes :
1. **Zone d'affichage (Haut - ~45% de l'écran, fond turquoise `#2BB8E8`)** :
   - Titre centré *"Currency Converter"*
   - Menu déroulant (`Spinner`) pour la devise source (par défaut **USD**)
   - Zone de texte pour le montant saisi (par défaut `"0"`)
   - Bouton d'inversion des devises (`btnInverser`)
   - Menu déroulant (`Spinner`) pour la devise cible (par défaut **EUR**)
   - Zone de texte pour le résultat formaté (par défaut `"0.00"`)
   - Bouton *"Convertir"*
2. **Clavier numérique sur-mesure (Bas, fond clair)** :
   - `TableLayout` 4×3 contenant les chiffres `0` à `9`, le point décimal `.` et la touche d'effacement `C` (`#F0727A`).

---

## ✨ Fonctionnalités & Règles de Gestion

- 🔢 **Clavier numérique personnalisé** : La saisie s'effectue exclusivement via le clavier intégré.
- 🛑 **Contraintes de saisie** :
  - Limite maximale de **10 caractères**.
  - Un **seul point décimal** autorisé par montant.
  - Remplacement automatique du `0` initial lors de la première frappe.
- 🔄 **Inversion rapide** : Clic sur le bouton d'inversion pour échanger les devises source et cible.
- ⚡ **Conversion automatique (Bonus)** : Mise à jour instantanée du résultat lors de la frappe ou du changement de devise dans les listes déroulantes.
- ⚠️ **Gestion des erreurs** : Affichage d'un `Toast` *"Veuillez saisir un montant"* si le montant saisi est égal à 0 lors de l'appui sur *"Convertir"*.
- 📐 **Écran verrouillé en Portrait** : Conformément aux spécifications du TP.

---

## 🧮 Logique de Conversion

Les taux de change fixes sont exprimés pour **1 EUR** :
- **EUR** : 1.0
- **USD** : 1.17
- **TND** : 3.40
- **GBP** : 0.87
- **JPY** : 172.50
- **CAD** : 1.62

Formula de conversion via l'Euro comme devise pivot :
$$\text{Montant en EUR} = \frac{\text{Montant}}{\text{Taux}(\text{Source})}$$
$$\text{Résultat} = \text{Montant en EUR} \times \text{Taux}(\text{Cible})$$

---

## 🧪 Tests Unitaires Automatisés

Les cas de test demandés ont été implémentés et validés dans `CurrencyConverterTest.kt` :

| Montant | Devise Source | Devise Cible | Résultat Attendu |
| :---: | :---: | :---: | :---: |
| **120** | USD | EUR | **102.56** |
| **100** | EUR | TND | **340.00** |
| **50** | GBP | USD | **67.24** |
| **10** | EUR | EUR | **10.00** |

Pour exécuter les tests unitaires :
```bash
./gradlew test
```

---

## 🛠️ Stack Technique & Architecture

- **Langage** : Kotlin
- **Min SDK** : API 28 (Android 9.0)
- **Compile SDK** : API 35 / 37
- **Architecture & Binding** : View Binding (`ActivityMainBinding`)
- **UI Components** : `ConstraintLayout`, `TableLayout`, `TableRow`, `Spinner`, `ArrayAdapter`
- **Tests** : JUnit 4

---

## 📂 Structure du Projet

```
app/src/
├── main/
│   ├── java/com/tp/currencyconverter/
│   │   ├── MainActivity.kt                # Contrôleur principal
│   │   └── CurrencyConverterCalculator.kt # Logique pure de calcul de conversion
│   └── res/
│       ├── layout/
│       │   ├── activity_main.xml          # Layout principal (ConstraintLayout + TableLayout)
│       │   └── spinner_item.xml           # Layout personnalisé pour les Spinners
│       ├── drawable/
│       │   └── ic_swap.xml                # Icône vectorielle d'inversion
│       └── values/
│           ├── colors.xml                 # Couleurs personnalisées de la charte graphique
│           └── strings.xml                # Libellés et array de devises
└── test/java/com/tp/currencyconverter/
    └── CurrencyConverterTest.kt           # Tests unitaires automatisés
```
