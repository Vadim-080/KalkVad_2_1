package com.example.kalkvad_2_1;

import android.graphics.Color;

import static com.example.kalkvad_2_1.MainActivity.cvet;
import static com.example.kalkvad_2_1.MainActivity.otvet;
import static com.example.kalkvad_2_1.MainActivity.viborPoliaDlyaZadaniaRazmera;
import static com.example.kalkvad_2_1.MainActivity.vichislen;
import static com.example.kalkvad_2_1.MainActivity.vvodChisla;

public class CvetIRazmer {

    public static void cvetNadpisei() {
        byte a2 = (byte) (Math.random() * 11); // Случайное число от 0 до 9
        if (a2 == 1) {
            cvet = Color.parseColor("#059C00"); // Зеленый
        }
        if (a2 == 2) {
            cvet = Color.parseColor("#0037FF");  // Тёмно синий
        }
        if (a2 == 3) {
            cvet = Color.parseColor("#AA00FF");  // Фиолетовый
        }
        if (a2 == 4) {
            cvet = Color.parseColor("#6200EA");   // Тёмно фиолетовый
        }
        if (a2 == 5) {
            cvet = Color.parseColor("#2962FF"); //Светло синий
        }
        if (a2 == 6) {
            cvet = Color.parseColor("#987700");  // Тёмно желтый
        }
        if (a2 == 7) {
            cvet = Color.parseColor("#FF6D00");  // Оранжевый
        }
        if (a2 == 8) {
            cvet = Color.parseColor("#DD2C00");  // морковный
        }
        if (a2 == 9) {
            cvet = Color.parseColor("#D50000");  // Красный
        }
        if (a2 == 10) {
            cvet = Color.parseColor("#EF0B72");  // Алый
        }
        if (a2 == 0) {
            cvet = Color.parseColor("#001173"); // Очень тёмно синий
        }
    }
}