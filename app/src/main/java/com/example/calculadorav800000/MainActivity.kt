package com.example.calculadorav800000

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.math.*


class MainActivity : AppCompatActivity() {
    private var signo : Boolean = false
    private var operador : String = ""
    private var numero1 : Double = 0.0
    private var numero2 : Double = 0.0
    private var resultado : Double = 0.0
    private var swRes : Boolean = false
    private var hex : Boolean=false
    private var dec : Boolean=true
    private var bin : Boolean=false





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    private fun comprobarlacoma(){
        var pantallazo = pantalla.text.indexOf(".")
        if (pantallazo == -1) {
            pantalla.setText("${pantalla.text}.")
        }
    }

    private fun masMenos(){
        if (pantalla.text.toString().trim() == "") {
            pantalla.setText(0)
            signo = if (!signo) {
                pantalla.setText("2-${pantalla.text}")
                true
            } else {
                pantalla.setText(pantalla.text.toString().replace("-",""))
                false
            }
        }else{
            signo = if (!signo) {
                pantalla.setText("-${pantalla.text}")
                true
            } else {
                pantalla.setText(pantalla.text.toString().replace("-",""))
                false
            }
        }
    }

    private fun porcentaje(){
        var display = pantalla.text.toString().toDouble()
        display /= 100
        pantalla.setText(String.format("%.2f", display))
    }

    private fun dividir(){
        falsoBotones()
        operador = "÷"
        numero1 = pantalla.text.toString().toDouble()
        pantalla.setText("0.00")
    }

    private fun multiplicar(){
        falsoBotones()
        operador = "*"
        numero1 = pantalla.text.toString().toDouble()
        pantalla.setText("0.00")
    }

    private fun restar(){
        falsoBotones()
        operador = "-"
        numero1 = pantalla.text.toString().toDouble()
        pantalla.setText("0.00")
    }

    private fun sumar(){
        falsoBotones()
        operador = "+"
        numero1 = pantalla.text.toString().toDouble()
        pantalla.setText("0.00")
    }

    private fun igual(op : String){
        try {
            numero2 = pantalla.text.toString().toDouble()
            pantalla.text = null
            when (op) {
                "+" -> {
                    resultado = numero1 + numero2
                    barradeanterior.setText("$numero1 + $numero2 = $resultado")
                }
                "-" -> {
                    resultado = numero1 - numero2
                    barradeanterior.setText("$numero1 - $numero2 = $resultado")
                }
                "*" -> {
                    resultado = numero1 * numero2
                    barradeanterior.setText("$numero1 x $numero2 = $resultado")
                }
                "÷" -> {
                    resultado = numero1 / numero2
                    barradeanterior.setText("$numero1 / $numero2 = $resultado")
                }
            }
            if(hex){
                resultado = decimalHexadecimal(resultado.toInt()).toDouble()
                pantalla.setText(resultado.toInt().toString())
            }
            if(dec){
                pantalla.setText(resultado.toString())
            }
            if(bin){
                resultado = decimalBinario(resultado.toInt()).toDouble()
                pantalla.setText(resultado.toInt().toString())
            }

            swRes = false
            sumar.isEnabled = true
            restar.isEnabled = true
            division.isEnabled = true
            multplicacion.isEnabled = true
        }catch (e: NumberFormatException){

        }
    }

    private fun botonborrar(){
        pantalla.setText("0.00")
        barradeanterior.setText("")
        signo = false
        numero1 = 0.0
        numero2 = 0.0
        resultado = 0.0
        operador = ""
        sumar.isEnabled = true
        restar.isEnabled = true
        division.isEnabled = true
        multplicacion.isEnabled = true
    }

    private fun falsoBotones(){
        sumar.isEnabled = false
        restar.isEnabled = false
        division.isEnabled = false
        multplicacion.isEnabled = false
    }

    fun miraquebotonestapulsado(view: View){
        when (findViewById<Button>(view.id)){
            binario -> {
                decimal.isChecked = false
                hexadecimal.isChecked = false
                dec = false
                hex = false
                bin = true
                botonborrar()
                lacoma.isEnabled = false

            }
            decimal ->{
                hexadecimal.isChecked = false
                binario.isChecked = false
                hex = false
                bin = false
                dec = true
                botonborrar()
                lacoma.isEnabled = true

            }
            hexadecimal ->{
                binario.isChecked = false
                decimal.isChecked = false
                hex = true
                bin = false
                dec = false
                botonborrar()
                lacoma.isEnabled = false
            }
        }
    }

    private fun decimalBinario(numero : Int) : Int{
        var binario = 0
        var bin : Double
        var digito : Int
        var exp = 0.0
        var numero = numero
        while(numero != 0){
            digito = numero % 2
            bin = binario + digito * 10.0.pow(exp)
            binario = bin.toInt()
            exp++
            numero /= 2
        }
        return binario
    }

    private fun decimalHexadecimal(decimal : Int): String{
        var decimal= decimal
        var resto : Int
        var hexa = ""
        while(decimal>0) {
            resto=(decimal%16)
            hexa = if(resto>9)
                letras(resto)+hexa
            else
                resto.toString() + hexa
            decimal /= 16
        }
        return hexa
    }

    private fun letras(n: Int): String {
        var letra = ""
        when (n) {
            10 -> letra = "A"
            11 -> letra = "B"
            12 -> letra = "C"
            13 -> letra = "D"
            14 -> letra = "E"
            15 -> letra = "F"
        }
        return letra
    }

    private fun hexDecimal(s: String): Int {
        var s = s
        val digits = "0123456789ABCDEF"
        s = s.toUpperCase(Locale.ROOT)
        var valor = 0
        for (element in s) {
            val c : Char = element
            val d : Int = digits.indexOf(c)
            valor = 16 * valor + d
        }
        return valor
    }

    private fun binDecimal(number: Int): Int {
        var decimal = 0
        var binario = number
        var exp = 0

        while (binario != 0) {
            val ultimo = binario % 10
            decimal += (ultimo * 2.0.pow(exp.toDouble())).toInt()
            exp++
            binario /= 10
        }
        return decimal
    }
    fun damelosID(view: View) {
        val num = findViewById<Button>(view.id)
        val bTexto = num.text.toString()
        when (num) {
            borra-> {
                botonborrar()
            }
            negativopostivo-> masMenos()
            botonporcentajes-> porcentaje()
            division-> {
                if(hex){
                    pantalla.setText(hexDecimal((pantalla.text.toString()))).toString()
                    println(pantalla.text.toString())
                    dividir()
                }
                if(dec){
                    dividir()
                }
                if(bin){
                    pantalla.setText((binDecimal(Integer.parseInt(pantalla.text.toString()))).toString())
                            dividir()
                }
            }
            multplicacion-> {
                if(hex){
                    pantalla.setText(hexDecimal(pantalla.text.toString())).toString()
                    println(pantalla.text.toString())
                    multiplicar()
                }
                if(dec){
                    multiplicar()
                }
                if(bin){
                    pantalla.setText(binDecimal(Integer.parseInt(pantalla.text.toString()))).toString()
                    println(pantalla.text.toString())
                    multiplicar()
                }
            }
            restar->{
                if(hex){
                    pantalla.setText(hexDecimal(pantalla.text.toString())).toString()
                    println(pantalla.text.toString())
                    restar()
                }
                if(dec){
                    restar()
                }
                if(bin){
                    pantalla.setText(binDecimal(Integer.parseInt(pantalla.text.toString()))).toString()
                    println(pantalla.text.toString())
                    restar()
                }
            }
            sumar->{
                if(hex){
                    pantalla.setText(hexDecimal(pantalla.text.toString())).toString()
                    println(pantalla.text.toString())
                    sumar()
                }
                if(dec){
                    sumar()
                }
                if(bin){
                    pantalla.setText(binDecimal(Integer.parseInt(pantalla.editableText.toString()))).toString()
                    println(pantalla.text.toString())
                    sumar()
                }
            }
            igual->{
                if(hex){
                    pantalla.setText(hexDecimal(pantalla.text.toString())).toString()
                    println(pantalla.text.toString())
                    igual(operador)
                }
                if(dec){
                    igual(operador)
                }
                if(bin){
                    pantalla.setText(binDecimal(Integer.parseInt(pantalla.text.toString()))).toString()
                    println(pantalla.text.toString())
                    igual(operador)
                }
            }

            lacoma-> comprobarlacoma()

            uno,dos,tres,cuatro,cinco,seis,siete,ocho,nueve,cero,letraA,letraB,letraC,letraD,letraE,letraF -> {
                if(!swRes){
                    swRes = true
                    pantalla.setText("0.00")
                    barradeanterior.setText("")

                    if (pantalla.editableText.toString() == "0.00" || pantalla.editableText.toString() == "-0.00") {
                        pantalla.setText(pantalla.text.toString().replace("0.00", ""))
                        pantalla.setText(pantalla.text.toString().replace("-0.00", "-"))
                    }
                    pantalla.setText("${pantalla.text}${bTexto}")
                }else {
                    if (pantalla.editableText.toString() == "0.00" || pantalla.editableText.toString() == "-0.00") {
                        pantalla.setText(pantalla.text.toString().replace("0.00", ""))
                        pantalla.setText(pantalla.text.toString().replace("-0.00", "-"))
                    }
                    pantalla.setText("${pantalla.text}${bTexto}")
                }
            }
        }
    }


    fun calculaseno(view: View) {
        var seno = findViewById<Button>(view.id)
        if (pantalla.text.toString().isNotEmpty()) {
            pantalla.setText((sin(pantalla.text.toString().toDouble())).toString())
            barradeanterior.setText("seno")
        }
    }

    fun calculatangente(view: View) {
            var tangente = findViewById<Button>(view.id)
        if (pantalla.text.toString().isNotEmpty()) {
            pantalla.setText((tan(pantalla.text.toString().toDouble())).toString())
            barradeanterior.setText("tangente ")

        }
    }

    fun calculaconseno(view: View) {
            var cos = findViewById<Button>(view.id)
            if (pantalla.text.toString().isNotEmpty()) {
                pantalla.setText(cos(pantalla.text.toString().toDouble()).toString())
                barradeanterior.setText("coseno")
            }
        }

}