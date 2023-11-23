package com.example.calculadora_jaume_nadal;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView pantalla;
    Integer primerNumero = null;
    Integer segundoNumero = null;
    private String operacionPendente = null;
    private boolean operandoEnEspera = false;

    private String numeroAnterior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pantalla = findViewById(R.id.tvResultado);

        // Inicialitzar los numeros
        int[] numerosButtons = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
        for (int numero : numerosButtons) {
            findViewById(numero).setOnClickListener(this::NumeroPulsado);
        }

        // Inicialitzacion de los operandos
        int[] operacionsButtons = {R.id.btnSumar, R.id.btnRestar, R.id.btnMultiplicar, R.id.btnDividir};
        for (int Boton : operacionsButtons) {
            findViewById(Boton).setOnClickListener(this::BotonOperacion);
        }

        // Inicialitzacion de borrar y del resultado
        findViewById(R.id.btnC).setOnClickListener(v -> BorrarTodo());
        findViewById(R.id.btnResultado).setOnClickListener(v -> pulsarResultado());
    }

    // Metodo para los numeros pulsados
    public void NumeroPulsado(View view) {
        if (operandoEnEspera) {
            pantalla.setText("");
            operandoEnEspera = false;
        }
        String textoBoton = ((Button) view).getText().toString();

        pantalla.append(textoBoton);
        numeroAnterior = textoBoton;
    }

    // Metodo para los botones pulsados
    public void BotonOperacion(View view) {
        if (!operandoEnEspera && pantalla.getText().length() != 0) {
            primerNumero = Integer.parseInt(pantalla.getText().toString());
            operacionPendente = ((Button) view).getText().toString();
            operandoEnEspera = true;
            String boton = ((Button) view).getText().toString();
            pantalla.append(boton);
        }
    }

    // Metodo para el igual donde con un switch segun el signo se hace la operacion
    public void pulsarResultado() {
        if (operacionPendente == null || primerNumero == null || pantalla.getText().length() == 0) {
            return;
        }
        segundoNumero = Integer.parseInt(pantalla.getText().toString());
        int resultado;
        switch (operacionPendente) {
            case "+":
                resultado = primerNumero + segundoNumero;
                break;
            case "−":
                if (primerNumero >= segundoNumero) {
                    resultado = primerNumero - segundoNumero;
                } else {
                    pantalla.setText("Error, no es pot fer una resta negativa");
                    return;
                }
                break;
            case "X":
                resultado = primerNumero * segundoNumero;
                break;
            case "/":
                if (segundoNumero == 0) {
                    pantalla.setText("Error, no es pot dividir entre 0");
                    return;
                } else {
                    resultado = primerNumero / segundoNumero;
                }
                break;
            default:
                resultado = 0;
        }
        pantalla.setText(String.valueOf(resultado));
        primerNumero = resultado;
        operacionPendente = null;
    }

    // Metodos para borrar
    public void BorrarTodo() {
        pantalla.setText("");
        primerNumero = null;
        segundoNumero = null;
        operacionPendente = null;
        operandoEnEspera = false;
    }
}