package com.example.dados

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var botonGenerar: Button
    private lateinit var imagenDados: ImageView
    private lateinit var textJugadas: TextView
    private lateinit var textNJugadas: TextView
    private var intentos = 0
    private val listaMutable: MutableList<Int> = mutableListOf()
    private var coincidencias = 0
    private lateinit var generala: TextView
    private val escalera1: List<Int> = listOf(1, 2, 3, 4, 5)
    private val escalera2: List<Int> = listOf(2, 3, 4, 5, 6)
    private val escalera3: List<Int> = listOf(3, 4, 5, 6, 1)
    private lateinit var listaText: TextView
    private var juegoTerminado = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        botonGenerar = findViewById(R.id.Boton)
        imagenDados = findViewById(R.id.imageView)
        imagenDados.setImageResource(R.drawable.empty_dice)
        textJugadas = findViewById(R.id.Jugadas)
        textNJugadas = findViewById(R.id.NJugadas)
        generala = findViewById(R.id.Generala)
        listaText = findViewById(R.id.Lista)
        listaText.text = listaMutable.toString()

        botonGenerar.setOnClickListener {
            if (juegoTerminado) {
                // Reiniciar el juego
                intentos = 0
                listaMutable.clear()
                juegoTerminado = false
                generala.text = ""

                botonGenerar.text = "Comenzar"
                listaText.text = listaMutable.toString()
            } else {
                // Jugar
                intentos++
                if (intentos in 1..5) {
                    botonGenerar.text = "Tirar"
                    textJugadas.text = "Jugadas"
                    textNJugadas.text = intentos.toString()
                    val numeroRandom = numeroAleatorio()
                    listaMutable.add(numeroRandom)
                    imagenDados.setImageResource(getDiceImageResource(numeroRandom))
                    listaText.text = listaMutable.toString()

                    if (intentos > 4) {
                        textJugadas.text = "Jugadas"
                        coincidencias = contarCoincidencias(listaMutable)

                        generala.text = when (coincidencias) {
                            5 -> "GENERALA"
                            4 -> "POKER"
                            3 -> if (isFull(listaMutable))  "FULL" else "NADA"
                            else -> "NADA"
                        }

                        if (isEscalera(listaMutable)) {
                            generala.text = "ESCALERA"
                        }

                        juegoTerminado = true
                        botonGenerar.text = "Reiniciar"
                    }
                } else {
                    botonGenerar.text = "Comenzar"
                    juegoTerminado = true
                }
            }
        }
    }

    private fun numeroAleatorio(): Int {
        return (1..6).random()
    }

    private fun getDiceImageResource(diceValue: Int): Int {
        return when (diceValue) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.empty_dice
        }
    }

    private fun contarCoincidencias(lista: List<Int>): Int {
        val counts = mutableMapOf<Int, Int>()

        for (dice in lista) {
            counts[dice] = counts.getOrDefault(dice, 0) + 1
        }

        return counts.values.maxOrNull() ?: 0
    }

    private fun isFull(lista: List<Int>): Boolean {
        for (i in lista.distinct()){
            if (lista.count{it==i} == 2) return true
        }
        return false
    }

    private fun isEscalera(lista: List<Int>): Boolean {
        val sortedDice = lista.sorted()
        return sortedDice == escalera1 || sortedDice == escalera2 || sortedDice == escalera3
    }
}


