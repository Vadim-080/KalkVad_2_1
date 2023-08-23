package com.example.kalkvad_2_1;

import android.graphics.Color;

import static com.example.kalkvad_2_1.MainActivity.cvet;
import static com.example.kalkvad_2_1.MainActivity.otvet;
import static com.example.kalkvad_2_1.MainActivity.viborPoliaDlyaZadaniaRazmera;
import static com.example.kalkvad_2_1.MainActivity.vichislen;
import static com.example.kalkvad_2_1.MainActivity.vvodChisla;

public class CvetIRazmer {

    public static void razmerPolei() {
        if (viborPoliaDlyaZadaniaRazmera == 1) {
            vichislen.setTextSize(22);
        }
        if (viborPoliaDlyaZadaniaRazmera == 2) {
            otvet.setTextSize(50);
        }
        if (viborPoliaDlyaZadaniaRazmera == 3) {
            vvodChisla.setTextSize(38);
        }
        if (viborPoliaDlyaZadaniaRazmera == 4) {
            vichislen.setTextSize(22);
            vvodChisla.setTextSize(38);
            otvet.setTextSize(50);
        }
    }

    public static void razmerPolyaVichislen() {
        String s = vichislen.getText().toString();

        if (s.length() > 69) {
            vichislen.setTextSize(12);
            return;
        }
        if (s.length() > 59) {
            vichislen.setTextSize(14);
            return;
        }
        if (s.length() > 55) {
            vichislen.setTextSize(15);
            return;
        }
        if (s.length() > 50) {
            vichislen.setTextSize(16);
            return;
        }
        if (s.length() > 48) {
            vichislen.setTextSize(17);
            return;
        }
        if (s.length() > 46) {
            vichislen.setTextSize(18);
            return;
        }
        if (s.length() > 43) {
            vichislen.setTextSize(19);
            return;
        }
        if (s.length() > 37) {
            vichislen.setTextSize(20);
            return;
        }
        if (s.length() > 35) {
            vichislen.setTextSize(22);
            return;
        }
        if (s.length() > 31) {
            vichislen.setTextSize(24);
            return;
        }
    }

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