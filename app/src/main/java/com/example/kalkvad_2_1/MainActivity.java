package com.example.kalkvad_2_1;

/*import static com.example.kalkvad_2_1.CvetIChrift.viborShriftaDelen;*/

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    /*Задаём переменные*/
    TextView tv1, tv2, tv3, tv4; // плавное появление экрана
    int koifPrivet;
    Animation anim;
    String operator = "";
    public static byte viborPoliaDlyaZadaniaRazmera;
    Boolean znakKoren = false;
    String number = String.valueOf(0);
    String numberSProb = String.valueOf(0);
    String oldNumberSProb = String.valueOf(0);
    String newNumberSProb = String.valueOf(0);
    String oldNumber = String.valueOf(0);
    String newNumber = String.valueOf(0);
    /* Boolean flagMem = false;  // обозначает, что был вывод из памяти*/
    double result = 0.0;
    String memori;
    String formatOtveta = String.valueOf(0);
    double memori1 = 0.0;
    double memori2 = 0.0;
    Boolean isNew = true;
    Button buAC;
    public static EditText vvodChisla; // поле для ввода числа
    public static TextView otvet; // поле для вывода результата
    TextView poleMemori; // поле для вывода значения ячейки памяти
    public static TextView vichislen; // поле для вывода процесса набора
    Boolean zapretVtoroiTochki = false;
    double obrabativDlyaRezChislo;
    String scobci;
    public static int cvet;  // цвет вывода слов
    public static Boolean aktivaciyaKnopok = true;

    // Переменные удаления пробелов
    String udalProbVvod;
    String udalProbOldNamb;
    String udalProbNamber;
    String udalProbFormatOtveta;
    String udalProbOtvet;

    // звуковые переменные
    SeekBar volumeControl;
    AudioManager audioManager;
    MediaPlayer mediaPlayer1;
    MediaPlayer mediaPlayer2;
    MediaPlayer mediaPlayer3;
    MediaPlayer mediaPlayer4;
    MediaPlayer mediaPlayer5;
    MediaPlayer mediaPlayerAC;
    private LocalTime curTime;

    /* Аннотация Java. Он сообщает компилятору, что следующий метод
    переопределяет метод своего суперкласса*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Задаём звуковые сигналы*/

        mediaPlayer1 = MediaPlayer.create(this, R.raw.elektron1);
        mediaPlayer2 = MediaPlayer.create(this, R.raw.del);
        mediaPlayer3 = MediaPlayer.create(this, R.raw.znak);
        mediaPlayerAC = MediaPlayer.create(this, R.raw.ac);
        mediaPlayer4 = MediaPlayer.create(this, R.raw.ravno2);
        mediaPlayer5 = MediaPlayer.create(this, R.raw.proc);

        vvodChisla = findViewById(R.id.editText);
        otvet = findViewById(R.id.otvet);
        poleMemori = findViewById(R.id.znak);
        vichislen = findViewById(R.id.vichislen);
        buAC = findViewById(R.id.buAC);

        /*Регулировка громкости*/

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curValue = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumeControl = findViewById(R.id.volumeControl);
        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(curValue);
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        Button z = findViewById(R.id.buAC);   // ПРОЗРАЧНОСТЬ КНОПКИ
        z.setAlpha(1f);
        z.animate().alpha(0.6f).setDuration(2000);

        CvetIRazmer.cvetNadpisei();
        otvet.setTextColor(cvet);

        anim = AnimationUtils.loadAnimation(this, R.anim.anim_pole1); // Плавное появление экрана
        tv1 = (TextView) findViewById(R.id.znak);
        tv1.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(this, R.anim.anim_pole2); // Плавное появление экрана
        tv2 = (TextView) findViewById(R.id.vichislen);
        tv2.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(this, R.anim.anim_pole3); // Плавное появление экрана
        tv3 = (TextView) findViewById(R.id.otvet);
        tv3.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(this, R.anim.anim_pole4); // Плавное появление экрана
        tv4 = (TextView) findViewById(R.id.editText);
        tv4.startAnimation(anim);

        Button b2 = (Button) findViewById(R.id.buSbrosMem);
        b2.setEnabled(false);
        Button b20 = (Button) findViewById(R.id.buVivMem);
        b20.setEnabled(false);

        String timeStamp = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime()); // Задается экран приветствия
        byte q = Byte.parseByte(timeStamp);
        byte a = (byte) (Math.random() * 3); // Случайное число от 0 до 2

        if (a == 0) {
            if (q >= 5 && q < 11) {
                koifPrivet = 1;
                otvet.setText("ДОБРОЕ  УТРО");
            }
            if (q >= 11 && q < 18) {
                koifPrivet = 2;
                otvet.setText("ДОБРЫЙ  ДЕНЬ");
            }
            if (q >= 18 && q < 23) {
                koifPrivet = 4;
                otvet.setText("ДОБРЫЙ ВЕЧЕР");
            }
            if (q >= 23 || q < 5) {
                koifPrivet = 2;
                otvet.setText("ДОБРОЙ  НОЧИ");
            }
            viborShriftaPrivet();
            return;
        }

        if (a == 1) {

            if (q >= 5 && q < 11) {
                otvet.setText("УТРЕННИЙ ПРИВЕТ");
                koifPrivet = 12;
            }
            if (q >= 11 && q < 18) {
                otvet.setText("ДНЕВНОЙ ПРИВЕТ");
                koifPrivet = 11;
            }
            if (q >= 18 && q < 23) {
                otvet.setText("ВЕЧЕРНИЙ ПРИВЕТ");
                koifPrivet = 10;
            }
            if (q >= 23 || q < 5) {
                otvet.setText("НОЧНОЙ ПРИВЕТ");
                koifPrivet = 6;
            }
            viborShriftaPrivet();
            return;
        }

        if (a == 2) {

            if (q >= 5 & q < 11) {
                koifPrivet = 5;
                otvet.setText("бодрого утра");
                koifPrivet = 8;
            }
            if (q >= 11 & q < 18) {
                koifPrivet = 3;
                otvet.setText("хорошего дня");
                koifPrivet = 5;
            }
            if (q >= 18 & q < 23) {
                koifPrivet = 6;
                otvet.setText("приятного вечера");
                koifPrivet = 13;
            }
            if (q >= 23 || q < 4) {
                koifPrivet = 4;
                otvet.setText("спокойной ночи");
                koifPrivet = 2;
            }
            viborShriftaPrivet();
                  }
    }

    public void sbrosPrivet() {
        String s10 = otvet.getText().toString();
        if (s10.contains("ДОБ") || s10.contains("ПРИ") || s10.contains("при") || s10.contains("хор")
                || s10.contains("спо") || s10.contains("тих")) {
            otvet.setTextColor(Color.parseColor("#30FBDF"));  // Цвет поля ответа
            otvet.setText("0");
            Typeface typefaceLato = getResources().getFont(R.font.abrilfatface_regular);
            otvet.setTypeface(typefaceLato);
            viborPoliaDlyaZadaniaRazmera = 4;
            CvetIRazmer.razmerPolei();
        }
    }

    /* Метод удаления пробелов */
    public void udalProbelov() {
        udalProbVvod = vvodChisla.getText().toString();
        udalProbVvod = udalProbVvod.replaceAll("\\s+", "");

        udalProbOldNamb = oldNumber.replaceAll("\\s+", "");
        udalProbNamber = number.replaceAll("\\s+", "");
        udalProbOtvet = otvet.getText().toString();
        udalProbOtvet = udalProbOtvet.replaceAll("\\s+", "");
        udalProbFormatOtveta = formatOtveta.replaceAll("\\s+", "");
    }

    /* Метод ввода цифр */
    public void clickNumber(View view) {
        sbrosPrivet();
        Button bb = (Button) findViewById(R.id.buAC);
        bb.setEnabled(true);

        String s = vichislen.getText().toString();
        if (s.contains("=")) {
            vichislen.setText("");
            otvet.setTextColor(Color.parseColor("#30FBDF"));  // Цвет поля ответа
            otvet.setText("0");
        }

        mediaPlayer3.start();
        udalProbelov();
        vvodChisla.setTextColor(Color.parseColor("#3954F8"));  // Синий цвет CV22

        if (isNew)                          // убирает ноль при наборе цифр
            vvodChisla.setText("");
        isNew = false;

        switch (view.getId()) {

            case R.id.bu1:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "1";
                break;
            case R.id.bu2:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "2";
                break;
            case R.id.bu3:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "3";
                break;
            case R.id.bu4:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "4";
                break;
            case R.id.bu5:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "5";
                break;
            case R.id.bu6:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "6";
                break;
            case R.id.bu7:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "7";
                break;
            case
                    R.id.bu8:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "8";
                break;
            case R.id.bu9:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = number.substring(1);
                }
                number = number + "9";
                break;

            case R.id.bu0:
                if (zeroIsFirst(number) && number.length() == 1) {
                    number = "0";
                } else {
                    number = number + "0";
                    vvodChisla.setText(number + "");
                    String s1 = number;
                    if (s1.contains(".")) {
                        return;
                    }
                }
                break;

            case R.id.buZap:
                if (zapretVtoroiTochki == false) {  // Не даёт поставить больше одной точки
                    zapretVtoroiTochki = true;
                } else {
                    return;
                }

                Button a = findViewById(R.id.buZap);   // ПРОЗРАЧНОСТЬ КНОПКИ
                a.setAlpha(0.1f);
                a.animate().alpha(1f).setDuration(5000);

                if (zeroIsFirst(number)) {   // Ставит 0 перед точкой
                    number = "0.";
                } else {
                    number = number + ".";
                }
                break;

            case R.id.buPlusMinus:  /* Не даёт поставить больше одного минуса*/
                Button a1 = findViewById(R.id.buPlusMinus);   // ПРОЗРАЧНОСТЬ КНОПКИ
                a1.setAlpha(0.3f);
                a1.animate().alpha(1f).setDuration(5000);

                if (numberIsZero(number)) {
                    number = "0";
                } else {
                    if (minusIsPres(number)) {
                        number = number.substring(1);
                    } else {
                        number = "-" + number;
                    }
                }
                break;
        }
        viborPoliaDlyaZadaniaRazmera = 3;
        CvetIRazmer.razmerPolei();

        double s3 = Double.parseDouble(number);
        udalProbelov();
        if (number.contains(".0") || number.contains(".1") || number.contains(".2") || number.contains(".3")
                || number.contains(".4") || number.contains(".5") || number.contains(".6")
                || number.contains(".7") || number.contains(".8") || number.contains(".9")) {
            obrabativDlyaRezChislo = Double.parseDouble(number);
            formatObrabotkiRezEdinui();
            vvodChisla.setText(formatOtveta);
            udalProbelov();
            numberSProb = vvodChisla.getText().toString();
            return;
        }

        if (number.length() > 12) {
            obrabativDlyaRezChislo = Double.parseDouble(number);
            formatObrabotkiRezEdinui();
            vvodChisla.setText(formatOtveta);
            udalProbelov();
            numberSProb = vvodChisla.getText().toString();
            return;
        }

        if (number.contains(".")) {
            obrabativDlyaRezChislo = Double.parseDouble(number);
            formatObrabotkiRezEdinui();
            if (s3 == 0 || number.contains("0.") || number.contains("1.") || number.contains("2.") || number.contains("3.") ||
                    number.contains("4.") || number.contains("5.") || number.contains("6.") || number.contains("7.")
                    || number.contains("8.") || number.contains("9.")) {
                vvodChisla.setText(formatOtveta + ".");
            } else {
                vvodChisla.setText(formatOtveta + "");
                numberSProb = vvodChisla.getText().toString();
            }
        } else {
            obrabativDlyaRezChislo = Double.parseDouble(number);
            formatObrabotkiRezEdinui();
            vvodChisla.setText(formatOtveta);
            udalProbelov();
            numberSProb = vvodChisla.getText().toString();
        }
        znakKoren = false;

        String s2 = vichislen.getText().toString();
        if (s2.contains("0)") || s2.contains("1)") || s2.contains("2)") || s2.contains("3)") || s2.contains("4)")
                || s2.contains("5)") || s2.contains("6)") || s2.contains("7)") || s2.contains("8)")
                || s2.contains("9)") || s2.contains("))")) {
            obrabativDlyaRezChislo = result;
            formatObrabotkiRezEdinui();
            vichislen.setText(formatOtveta + " " + operator);
            otvet.setTextColor(Color.parseColor("#30FBDF"));  // Цвет поля ответа
        }
    }

    /* Метод ввода операций вычисления */
    public void operation(View view) throws InterruptedException {
        sbrosPrivet();
        viborPoliaDlyaZadaniaRazmera = 1;
        CvetIRazmer.razmerPolei();
        vichislen.setTextColor(Color.parseColor("#874311"));  // Цвет текста вычисления
        /*  flagMem = false;*/
        vvodChisla.setTextColor(Color.parseColor("#3954F8"));  // Синий цвет CV22
        mediaPlayer1.start();
        udalProbelov();
        zapretVtoroiTochki = false;
        isNew = true;

        String s = vichislen.getText().toString();
        String ss = vvodChisla.getText().toString();

        if ((s.contains("-") || s.contains("+") || s.contains("*") || s.contains("÷"))
                && ((ss.contains("1") || ss.contains("2") || ss.contains("3") ||
                ss.contains("4") || ss.contains("5") || ss.contains("6") || ss.contains("7")
                || ss.contains("8") || ss.contains("9")))) {
            String s9 = number;
            udalProbelov();
            double s8 = Double.parseDouble(udalProbOtvet);
            clickRavno(null);
            number = String.valueOf(result);
            String starOperator = operator;

            switch (view.getId()) {

                case R.id.buMinus:

                    operator = "-";
                    View buMinus = findViewById(R.id.buMinus);
                    buMinus.animate().rotationXBy(180).setDuration(300);
                    break;
                case R.id.buPlus:
                    operator = "+";
                    View buPlus = findViewById(R.id.buPlus);
                    buPlus.animate().rotationXBy(-180).setDuration(300);
                    break;
                case R.id.buMultiply:
                    operator = "*";
                    View buMultiply = findViewById(R.id.buMultiply);
                    buMultiply.animate().rotationYBy(180).setDuration(300);
                    break;
                case R.id.buDivide:
                    operator = "÷";
                    View buDivide = findViewById(R.id.buDivide);
                    buDivide.animate().rotationYBy(-180).setDuration(300);
                    break;
            }
            obrabativDlyaRezChislo = Double.parseDouble(s9);
            formatObrabotkiRezEdinui();
            scobci = formatOtveta;
            scobci();

            if (s8 == 0) {
                vichislen.setText("(" + s + " " + scobci + ") " + operator);
                CvetIRazmer.razmerPolyaVichislen();
            } else {
                obrabativDlyaRezChislo = s8;
                formatObrabotkiRezEdinui();

                vichislen.setText("(" + formatOtveta + " " + starOperator + " " + scobci + ") " + operator);
                CvetIRazmer.razmerPolyaVichislen();
            }
            number = String.valueOf(0);
            vvodChisla.setTextColor(Color.parseColor("#30FF02"));  // светло зелен
            vvodChisla.setText("0 ");
            return;
        }
        switch (view.getId()) {

            case R.id.buMinus:
                operator = "-";
                View buMinus = findViewById(R.id.buMinus);
                buMinus.animate().rotationXBy(-180).setDuration(300);
                break;
            case R.id.buPlus:
                operator = "+";
                View buPlus = findViewById(R.id.buPlus);
                buPlus.animate().rotationXBy(180).setDuration(300);
                break;
            case R.id.buMultiply:
                operator = "*";
                View buMultiply = findViewById(R.id.buMultiply);
                buMultiply.animate().rotationYBy(180).setDuration(300);
                break;
            case R.id.buDivide:
                operator = "÷";
                View buDivide = findViewById(R.id.buDivide);
                buDivide.animate().rotationYBy(-180).setDuration(300);
                break;
        }
        if ((s.contains("0 -") || s.contains("1 -") || s.contains("2 -") || s.contains("3 -") ||
                s.contains("4 -") || s.contains("5 -") || s.contains("6 -") || s.contains("7 -") ||
                s.contains("8 -") || s.contains("9 -") || s.contains("+") || s.contains("*") ||
                s.contains("÷")) && (ss.contains("0")) != (s.contains("="))) {
            String s1 = s.substring(0, s.length() - 1) + operator;
            vichislen.setText(s1);
            return;
        }

        oldNumber = number;
        oldNumberSProb = numberSProb;

        double a2 = Double.parseDouble(udalProbOtvet);
        double a3 = Double.parseDouble(oldNumber);
        if (a3 == 0) {
            obrabativDlyaRezChislo = a2;
        } else {
            obrabativDlyaRezChislo = a3;
        }
        formatObrabotkiRezEdinui();
        vichislen.setText(formatOtveta + " " + operator);
        otvet.setTextColor(Color.parseColor("#30FBDF"));  // Цвет поля ответа

        number = String.valueOf(0);
        vvodChisla.setTextColor(Color.parseColor("#30FF02"));  // светло зелен
        vvodChisla.setText("0 ");
    }

    /* Метод проводящий вычисления */
    public void clickRavno(View view) throws InterruptedException {
        Button b1 = (Button) findViewById(R.id.buAC);
        b1.setEnabled(true);
        Button z = findViewById(R.id.buAC);   // ПРОЗРАЧНОСТЬ КНОПКИ АС
        z.setAlpha(0.6f);
        z.animate().alpha(1.0f).setDuration(2000);

        /*    flagMem = false;*/
        viborPoliaDlyaZadaniaRazmera = 1;
        CvetIRazmer.razmerPolei();

        udalProbelov();
        newNumber = number;
        newNumberSProb = numberSProb;

        mediaPlayer2.start();
        zapretVtoroiTochki = false;

        double a1 = Double.parseDouble(oldNumber);
        if (a1 == 0) {
            oldNumber = udalProbOtvet;
        }
        switch (operator) {

            case "-":
                result = Double.parseDouble(oldNumber) - Double.
                        parseDouble(newNumber);
                break;
            case "+":
                result = Double.parseDouble(oldNumber) + Double.
                        parseDouble(newNumber);
                break;
            case "*":
                result = Double.parseDouble(oldNumber) * Double.parseDouble(newNumber);
                break;
            case "÷":

                if (Double.parseDouble(newNumber) == 0) {

                    int maxLength = 30;
                    otvet.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                    CvetIRazmer.cvetNadpisei();
                    otvet.setTextColor(cvet);

                    viborShriftaDelen();
                    otvet.setText("НЕЛЬЗЯ  ДЕЛИТЬ  НА  «0»");

                    aktivaciyaKnopok = false;
                    blokiAktivaciyaKnopok();
                    /*Button bb = (Button) findViewById(R.id.buAC);
                    bb.setEnabled(true);*/
                    scobci = newNumberSProb;
                    scobci();
                    if (result == 0) {
                        vichislen.setText(oldNumberSProb + " " + operator + " " + scobci + " ="); // n
                        CvetIRazmer.razmerPolyaVichislen();
                    } else {
                        obrabativDlyaRezChislo = result;
                        formatObrabotkiRezEdinui();
                        vichislen.setText(formatOtveta + " " + operator + " " + scobci + " =");  // n
                        CvetIRazmer.razmerPolyaVichislen();
                    }
                    /*Button bb = (Button) findViewById(R.id.buAC);
                    bb.setEnabled(true);*/
                    return;
                } else {
                    result = Double.parseDouble(oldNumber) / Double.parseDouble(newNumber);
                    break;
                }
        }
        if (result > 9e300 || result < -9e300) {
            bolchChisloVOtvet();
            scobci = newNumberSProb;
            scobci();
            vichislen.setText(oldNumber + " " + operator + " " + scobci); // n
            CvetIRazmer.razmerPolyaVichislen();
            return;
        } else {

            VivodRezVOtvet(); /*Вывод результата вычислений*/

            View buRavn = findViewById(R.id.buRavn);              // ПОВОРОТ КНОПКИ
            buRavn.animate().rotationYBy(180).setDuration(2000);
            Button a3 = findViewById(R.id.buRavn);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a3.setAlpha(0.2f);
            a3.animate().alpha(1f).setDuration(2000);

            number = String.valueOf(0);
            oldNumber = String.valueOf(0);
            newNumber = String.valueOf(0);
            vvodChisla.setTextColor(Color.parseColor("#00E3C4"));  // цвет поля ввода
            vvodChisla.setText("0");
        }
    }

    /* Метод задает формат вывода ответа на экран */
    public void VivodRezVOtvet() throws InterruptedException {
        obrabativDlyaRezChislo = result;
        formatObrabotkiRezEdinui();

        if (znakKoren == false) {

            double a1 = Double.parseDouble(udalProbOtvet);

            if (a1 != 0) {
                DecimalFormat formatter1 = new DecimalFormat();
                DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
                symbols.setGroupingSeparator(' '); //явно задаем символ разделителя тысяч
                formatter1.setDecimalFormatSymbols(symbols);
                double i = Double.parseDouble(oldNumber);
                /*String s = formatter1.format(i).toString();*/
                udalProbelov();
                obrabativDlyaRezChislo = Double.parseDouble(udalProbOldNamb);
                formatObrabotkiRezEdinui();
                scobci = formatOtveta;
                scobci();
                String b1 = scobci;
                scobci = numberSProb;
                scobci();
                vichislen.setText(b1 + " " + operator + " " + scobci + " =");
                CvetIRazmer.razmerPolyaVichislen();
            } else {
                scobci = newNumberSProb;
                scobci();
                vichislen.setText(oldNumberSProb + " " + operator + " " + scobci + " =");
                CvetIRazmer.razmerPolyaVichislen();
            }
        }
        znakKoren = false;
        viborPoliaDlyaZadaniaRazmera = 2;
        CvetIRazmer.razmerPolei();
        otvet.setTextColor(Color.parseColor("#F80A75"));   // Красный
        obrabativDlyaRezChislo = result;
        formatObrabotkiRezEdinui();
        otvet.setText(formatOtveta + "");
    }

    public void formatObrabotkiRezEdinui() {

        BigDecimal a1 = BigDecimal.valueOf(obrabativDlyaRezChislo);
        double a2 = a1.doubleValue();
        double a8 = Math.abs(a2);
        String s = vvodChisla.getText().toString();

        if ((number.contains("-") & number.contains(".") & a8 >= 1e7) || (number.contains(".") & a8 >= 1e8)
                || a8 >= 1e9 || (a8 > 0 & a8 <= 1e-9) || (a8 < 1 & s.length() > 8)) {
            DecimalFormat s1 = new DecimalFormat("#.#####E00");
            formatOtveta = (s1.format(obrabativDlyaRezChislo));
            formatOtveta = formatOtveta.replace(',', '.');
            formatOtveta = formatOtveta.replace("E", " e ");
            return;
        } else {

            if (Math.abs(obrabativDlyaRezChislo) >= 1000) {
                DecimalFormat s2 = new DecimalFormat("#.#########");
                String a5 = s2.format(a2);
                formatOtveta = a5.replace(',', '.');
                DecimalFormat s3 = new DecimalFormat();
                DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
                symbols.setGroupingSeparator(' '); //явно задаем символ разделителя тысяч
                s3.setDecimalFormatSymbols(symbols);
                double a3 = Double.parseDouble(formatOtveta);
                String a4 = s3.format(a3);
                formatOtveta = a4.replace(',', '.');
            } else {
                DecimalFormat s4 = new DecimalFormat("#.#########");
                String a7 = s4.format(a2);
                formatOtveta = a7.replace(',', '.');
            }
        }
    }

    // Скобки перед отрицат числов в вычислении
    public void scobci() {
        String a2 = scobci.replaceAll("\\s+", "");
        double a1 = Double.parseDouble(a2);
        if (a1 < 0) {
            scobci = ("( " + scobci + " )");
        }
    }

    // Возведение в квадрат  Х 2
    public void clickVozvedVKvadrat(View view) throws InterruptedException {
        sbrosPrivet();
        udalProbelov();
        double aa = Double.parseDouble(udalProbOtvet);
        if (aa == 0) {
            Button b1 = (Button) findViewById(R.id.buAC);
            b1.setEnabled(true);
            Button z = findViewById(R.id.buAC);   // ПРОЗРАЧНОСТЬ КНОПКИ АС
            z.setAlpha(0.6f);
            z.animate().alpha(1f).setDuration(1000);
        }

        mediaPlayer5.start();
        udalProbelov();
        double n1 = Double.parseDouble(udalProbVvod);

        if (n1 > 9e149 || n1 < -9e149) {
            bolchChisloVOtvet();
            obrabativDlyaRezChislo = n1;
            vichislen.setText("( " + formatOtveta + " )" + " ² =");
            return;
        }
        if (result > 9e149 || result < -9e149) {
            bolchChisloVOtvet();
            obrabativDlyaRezChislo = result;
            vichislen.setText("( " + formatOtveta + " )" + " ² =");
            return;
        }

        switch (view.getId()) {

            case R.id.buVozvedVKvadrat:

                String s = vichislen.getText().toString();
                double a2 = result;

                if (s.contains("=") != (result > 9e149 || result < -9e149 || n1 > 9e149 || n1 < -9e149)) {
                    result = result * result;
                    vvodChisla.setTextColor(Color.parseColor("#00E3C4"));  // цвет поля ввода
                    obrabativDlyaRezChislo = a2;
                    formatObrabotkiRezEdinui();
                    scobci = formatOtveta;
                    scobci();
                    vichislen.setText(scobci + " ²" + " =");
                    obrabativDlyaRezChislo = result;
                    formatObrabotkiRezEdinui();
                    otvet.setText(formatOtveta);
                    return;
                }

                if (s.contains("+") | s.contains("-") | s.contains("*") | s.contains("÷")) {
                    obrabativDlyaRezChislo = n1;
                    formatObrabotkiRezEdinui();
                    String s3 = formatOtveta;
                    number = String.valueOf(n1 * n1);
                    vvodChisla.setTextColor(Color.parseColor("#3954F8"));  // Синий цвет CV22
                    obrabativDlyaRezChislo = Double.parseDouble(number);
                    formatObrabotkiRezEdinui();
                    String s4 = vichislen.getText().toString();
                    clickRavno(null);
                    vichislen.setText(s4 + " " + "(" + s3 + " ²" + ")" + " =");
                    CvetIRazmer.razmerPolyaVichislen();
                    return;
                } else {
                    if (n1 != 0) {
                        result = (n1 * n1);
                        RezDelenNaX();
                        scobci = numberSProb;
                        scobci();
                        vichislen.setText(scobci + " ² =");
                    } else {
                        result = Double.valueOf(result * result);
                        String n2 = otvet.getText().toString();
                        RezDelenNaX();
                        scobci = n2;
                        scobci();
                        vichislen.setText(scobci + " ² =");
                    }
                    vvodChisla.setText("0");
                }
        }
        Button helloWorld = findViewById(R.id.buVozvedVKvadrat);   // ПРОЗРАЧНОСТЬ КНОПКИ
        helloWorld.setAlpha(0.5f);
        helloWorld.animate().alpha(1f).setDuration(1500);

        number = String.valueOf(0);
        vvodChisla.setTextColor(Color.parseColor("#00E3C4"));  // цвет поля ввода
    }

    // Деление  1 / Х
    public void clickDelenNaX(View view) throws InterruptedException {
        sbrosPrivet();
        udalProbelov();
        double a2 = Double.parseDouble(udalProbOtvet);
        if (a2 == 0) {
            Button b1 = (Button) findViewById(R.id.buAC);
            b1.setEnabled(true);
            Button z = findViewById(R.id.buAC);   // ПРОЗРАЧНОСТЬ КНОПКИ АС
            z.setAlpha(0.6f);
            z.animate().alpha(1f).setDuration(1000);
        }

        mediaPlayer5.start();

        double a1 = Double.parseDouble(udalProbVvod);
        String s = vichislen.getText().toString();

        if (a1 != 0 & (s.contains("+") | s.contains("-") | s.contains("*") | s.contains("÷"))) {
            String s3 = vvodChisla.getText().toString();
            number = String.valueOf(1 / a1);
            vvodChisla.setTextColor(Color.parseColor("#3954F8"));  // Синий цвет CV22
            obrabativDlyaRezChislo = Double.parseDouble(number);
            formatObrabotkiRezEdinui();
            String s4 = vichislen.getText().toString();
            clickRavno(null);
            vichislen.setText(s4 + " " + "(" + "1 ÷" + " " + s3 + ")" + " =");
            CvetIRazmer.razmerPolyaVichislen();
            return;
        } else {
            if (a1 == 0 && a2 == 0) {
                /*otvet.setTextSize(31);  */        // ЗАДАЁТ РАЗМЕР ТЕКСТА
                int maxLength = 30;             // Разрешённая длина строки
                otvet.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                CvetIRazmer.cvetNadpisei();
                otvet.setTextColor(cvet);
                viborShriftaDelen();
                otvet.setText("НЕЛЬЗЯ  ДЕЛИТЬ  НА  «0»");
                aktivaciyaKnopok = false;
                blokiAktivaciyaKnopok();
                vichislen.setText("1 / " + "0");
                return;
            } else {
                switch (view.getId()) {
                    case R.id.buDelX:

                        if (a1 != 0) {
                            result = 1 / a1;
                            RezDelenNaX();
                            scobci = numberSProb;
                            scobci();
                            vichislen.setText("1 ÷ " + scobci + " =");
                        } else {
                            result = Double.valueOf(1 / (a2));
                            scobci = formatOtveta;
                            scobci();
                            vichislen.setText("1 ÷ " + scobci + " =");
                            RezDelenNaX();
                        }
                        vvodChisla.setText("0");
                }
            }
            Button helloWorld = findViewById(R.id.buDelX);   // ПРОЗРАЧНОСТЬ КНОПКИ
            helloWorld.setAlpha(0.5f);
            helloWorld.animate().alpha(1f).setDuration(1500);

            number = String.valueOf(0);
            vvodChisla.setTextColor(Color.parseColor("#00E3C4"));   // цвет поля ввода
        }
    }

    /* Метод обработки  %  */
    public void clickProcent(View view) {
        sbrosPrivet();
        udalProbelov();
        double s2 = Double.parseDouble(udalProbOtvet);
        if (s2 == 0) {
            Button b1 = (Button) findViewById(R.id.buAC);
            b1.setEnabled(true);
            Button z = findViewById(R.id.buAC);   // ПРОЗРАЧНОСТЬ КНОПКИ АС
            z.setAlpha(0.6f);
            z.animate().alpha(1f).setDuration(1000);
        }

        zapretVtoroiTochki = false;
        mediaPlayer5.start();

        double s0 = Double.parseDouble(oldNumber);
        if (result != 0 && s0 == 0) {
            oldNumber = String.valueOf(result);
        }
        udalProbelov();
        double s1 = Double.parseDouble(udalProbVvod);
        if (s1 == 0 & s2 == 0) {
            vichislen.setText("0");
            return;
        }

        if (operator == "") {
            double temp = Double.parseDouble(udalProbVvod) / 100;
            String number1 = temp + "";
            otvet.setTextColor(Color.parseColor("#F80A75"));   // Красный
            otvet.setText(number1);
            vichislen.setText(numberSProb + " % от 1" + " =");
        } else {
            String newNumber = udalProbVvod;
            switch (operator) {
                case "+":
                    result = Double.parseDouble(udalProbOldNamb) + Double.
                            parseDouble(udalProbOldNamb) *
                            Double.parseDouble(newNumber) / 100;
                    obrabativDlyaRezChislo = Double.parseDouble(oldNumber);
                    formatObrabotkiRezEdinui();
                    scobci = formatOtveta;
                    scobci();
                    vichislen.setText(scobci + " + " + numberSProb + " % " + " =");
                    CvetIRazmer.razmerPolyaVichislen();
                    number = String.valueOf(0);
                    break;

                case "-":
                    result = Double.parseDouble(udalProbOldNamb) - Double.
                            parseDouble(udalProbOldNamb) *
                            Double.parseDouble(newNumber) / 100;
                    obrabativDlyaRezChislo = Double.parseDouble(oldNumber);
                    formatObrabotkiRezEdinui();
                    scobci = formatOtveta;
                    scobci();
                    vichislen.setText(oldNumber + " - " + numberSProb + " % " + " =");
                    CvetIRazmer.razmerPolyaVichislen();
                    number = String.valueOf(0);
                    break;

                case "*":
                    result = Double.parseDouble(udalProbOldNamb) * Double.
                            parseDouble(newNumber) / 100;
                    obrabativDlyaRezChislo = Double.parseDouble(oldNumber);
                    formatObrabotkiRezEdinui();
                    scobci = formatOtveta;
                    scobci();
                    String a1 = scobci;
                    obrabativDlyaRezChislo = Double.parseDouble(number);
                    formatObrabotkiRezEdinui();
                    scobci = formatOtveta;
                    scobci();
                    String a2 = scobci;
                    vichislen.setText(a2 + " % от " + a1 + " =");
                    CvetIRazmer.razmerPolyaVichislen();
                    number = String.valueOf(0);
                    break;

                case "÷":
                    result = Double.parseDouble(udalProbOldNamb) / Double.
                            parseDouble(newNumber) * 100;

                    obrabativDlyaRezChislo = Double.parseDouble(oldNumber);
                    formatObrabotkiRezEdinui();
                    scobci = formatOtveta;
                    scobci();
                    String a3 = scobci;
                    obrabativDlyaRezChislo = Double.parseDouble(number);
                    formatObrabotkiRezEdinui();
                    scobci = formatOtveta;
                    scobci();
                    String a4 = scobci;
                    vichislen.setText(a3 + " ÷ (на " + a4 + " % от " + a3 + ") =");
                    CvetIRazmer.razmerPolyaVichislen();

                    number = String.valueOf(0);
                    break;
            }
            obrabativDlyaRezChislo = result;
            formatObrabotkiRezEdinui();
            otvet.setTextColor(Color.parseColor("#F80A75"));  // Красный
            otvet.setText(formatOtveta + "");
            operator = "";
        }
        vvodChisla.setTextColor(Color.parseColor("#00E3C4"));  // цвет поля ввода
        vvodChisla.setText("0");
        oldNumber = udalProbOtvet;

        View buA = findViewById(R.id.buPercent);
        buA.animate().rotationYBy(360).setDuration(100);
        oldNumber = String.valueOf(0);
    }

    /* Начинает вызывать Квадратный корень */
    public void clickKoren(View view) throws InterruptedException {
        sbrosPrivet();
        mediaPlayer5.start();

        udalProbelov();
        double aa = Double.parseDouble(udalProbOtvet);
        if (aa == 0) {
            Button b1 = (Button) findViewById(R.id.buAC);
            b1.setEnabled(true);
            Button z = findViewById(R.id.buAC);   // ПРОЗРАЧНОСТЬ КНОПКИ АС
            z.setAlpha(0.6f);
            z.animate().alpha(1f).setDuration(1000);
        }

        otvet.setTextColor(Color.parseColor("#F80A75"));    // Красный
        udalProbelov();

        switch (view.getId()) {
            case R.id.buKoren:
                znakKoren = true; // не стирать

                String s = vichislen.getText().toString();
                double s1 = Double.parseDouble(udalProbVvod);
                double s8 = result;

                if (s1 < 0 || (s8 < 0 && s1 == 0)) {
                    /* otvet.setTextSize(24);    */      // ЗАДАЁТ РАЗМЕР ТЕКСТА
                    int maxLength = 50;             // ЗАДАЁТ ДЛИННУ ТЕКСТА
                    otvet.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                    CvetIRazmer.cvetNadpisei();
                    otvet.setTextColor(cvet);
                    viborShriftaKoren();
                    otvet.setText("НЕЛЬЗЯ  ИЗВЛЕЧЬ  КОРЕНЬ  ИЗ ОТРИЦАТЕЛЬНОГО  ЧИСЛА");
                    vvodChisla.setText("0");
                    aktivaciyaKnopok = false;
                    blokiAktivaciyaKnopok();
                    if (s1 != 0) {
                        vichislen.setText("√" + " " + "(" + numberSProb + ")");
                    } else {
                        vichislen.setText("√" + " " + "(" + formatOtveta + ")");
                    }
                    return;
                }

                View buA = findViewById(R.id.buKoren);
                buA.animate().rotationYBy(360).setDuration(100);

                if (s8 != 0 && (s.contains("="))) {
                    String s5 = String.valueOf(result);
                    result = (Math.sqrt(result));
                    obrabativDlyaRezChislo = Double.parseDouble(s5);
                    formatObrabotkiRezEdinui();
                    vichislen.setText("√ " + formatOtveta + " " + " =");
                    obrabativDlyaRezChislo = result;
                    formatObrabotkiRezEdinui();
                    otvet.setText(formatOtveta);
                    return;
                }

                if (s1 == 0 && (s.contains("+") | s.contains("-") | s.contains("*") | s.contains("÷"))) {
                    String s3 = vvodChisla.getText().toString();
                    number = String.valueOf(Math.sqrt(Double.parseDouble(number)));

                    if (result == 0) {
                        obrabativDlyaRezChislo = Double.parseDouble(oldNumber);
                    } else {
                        obrabativDlyaRezChislo = result;
                    }
                    formatObrabotkiRezEdinui();
                    String s4 = formatOtveta;
                    clickRavno(null);
                    vichislen.setText(s4 + " " + operator + " " + "(" + "√" + " " + s3 + ")" + " =");
                    CvetIRazmer.razmerPolyaVichislen();
                    return;
                }

                if (s1 != 0 && (s.contains("+") | s.contains("-") | s.contains("*") | s.contains("÷"))) {
                    String s3 = vvodChisla.getText().toString();
                    number = String.valueOf(Math.sqrt(Double.parseDouble(number)));

                    double z1 = Double.parseDouble(oldNumber);

                    if (z1 != 0) {
                        obrabativDlyaRezChislo = Double.parseDouble(oldNumber);
                    } else {
                        obrabativDlyaRezChislo = result;
                    }
                    formatObrabotkiRezEdinui();
                    String s4 = formatOtveta;
                    clickRavno(null);
                    vichislen.setText(s4 + " " + operator + " " + "(" + "√" + " " + s3 + ")" + " =");
                    CvetIRazmer.razmerPolyaVichislen();
                } else {
                    if (s1 != 0) {
                        obrabativDlyaRezChislo = Double.parseDouble(number);  // вычисляет из ввода
                        formatObrabotkiRezEdinui();
                        vichislen.setText("√" + " " + formatOtveta + " =");
                        result = (Math.sqrt(Double.parseDouble(number)));
                        obrabativDlyaRezChislo = result;
                        formatObrabotkiRezEdinui();
                        otvet.setText(formatOtveta);
                        vvodChisla.setTextColor(Color.parseColor("#00E3C4")); // цвет поля ввода
                        vvodChisla.setText("0");
                        number = "0";
                    } else {
                        result = Math.sqrt(result);                  // вычисляет из ответа
                        obrabativDlyaRezChislo = result;
                        formatObrabotkiRezEdinui();
                        vichislen.setText("√" + " " + otvet.getText().toString() + " =");
                        otvet.setText(formatOtveta);
                    }
                }
        }
           }

    /*Метод задает формат вывода ответа на экран */
    public void RezDelenNaX() {
        obrabativDlyaRezChislo = result;
        formatObrabotkiRezEdinui();
        udalProbelov();
        otvet.setTextColor(Color.parseColor("#F80A75"));   // Красный
        otvet.setText(formatOtveta);
    }

    /*Метод обработки работы с памятью */
    public void memori(View view) {
        mediaPlayer5.start();
        udalProbelov();

        switch (view.getId()) {
            case R.id.buZapMem:

                String x1 = otvet.getText().toString();
                if (x1.contains("У") | x1.contains("Д") | x1.contains("В") | x1.contains("Н")|x1.contains("у") | x1.contains("д") | x1.contains("в") | x1.contains("н")) {
                    return;
                }

                Button b10 = (Button) findViewById(R.id.buVivMem);
                b10.setEnabled(true);
                Button b11 = (Button) findViewById(R.id.buSbrosMem);
                b11.setEnabled(true);

                View buZapMem = findViewById(R.id.buZapMem);        // ПРОВОРОТ КНОПКИ
                buZapMem.animate().rotationXBy(360).setDuration(3000);

                double a1 = Double.parseDouble(udalProbVvod);
                double a2 = Double.parseDouble(udalProbOtvet);

                if (a1 != 0) {
                    memori = String.valueOf(a1);
                    oldNumber = number;
                } else {
                    if (a2 != 0) {
                        memori = String.valueOf(a2);
                    } else return;
                }

                obrabativDlyaRezChislo = Double.valueOf(memori);
                formatObrabotkiRezEdinui();
                poleMemori.setText("  MEM = " + formatOtveta);
                memori1 = Double.valueOf(memori);
                poleMemori.setTextColor(Color.parseColor("#FF42FC"));
                number = String.valueOf(0);
                break;

            case R.id.buPlusMem:

                String x2 = otvet.getText().toString();
                if (x2.contains("У") | x2.contains("Д") | x2.contains("В") | x2.contains("Н")|x2.contains("у") | x2.contains("д") | x2.contains("в") | x2.contains("н")) {
                    return;
                }

                Button b12 = (Button) findViewById(R.id.buVivMem);
                b12.setEnabled(true);
                Button b13 = (Button) findViewById(R.id.buSbrosMem);
                b13.setEnabled(true);

                View buPlusMem = findViewById(R.id.buPlusMem);          // ПРОВОРОТ КНОПКИ
                buPlusMem.animate().rotationXBy(360).setDuration(2000);

                double a3 = Double.parseDouble(udalProbVvod);
                double a4 = Double.parseDouble(udalProbOtvet);

                if (a3 != 0) {
                    memori2 = memori1 + a3;
                } else {
                    if (a4 != 0) {
                        memori2 = memori1 + a4;
                    } else return;
                }
                memori1 = memori2;
                obrabativDlyaRezChislo = Double.valueOf(memori2);
                formatObrabotkiRezEdinui();
                poleMemori.setText("  MEM = " + formatOtveta);
                memori = String.valueOf(memori2);
                poleMemori.setTextColor(Color.parseColor("#A600FF"));
                number = String.valueOf(0);
                break;

            case R.id.buMinusMem:

                String x3 = otvet.getText().toString();
                if (x3.contains("У") | x3.contains("Д") | x3.contains("В") | x3.contains("Н")|x3.contains("у") | x3.contains("д") | x3.contains("в") | x3.contains("н")) {
                    return;
                }

                Button b14 = (Button) findViewById(R.id.buVivMem);
                b14.setEnabled(true);
                Button b15 = (Button) findViewById(R.id.buSbrosMem);
                b15.setEnabled(true);

                View buMinusMem = findViewById(R.id.buMinusMem);    // ПРОВОРОТ КНОПКИ
                buMinusMem.animate().rotationXBy(360).setDuration(1000);

                double a5 = Double.parseDouble(udalProbVvod);
                double a6 = Double.parseDouble(udalProbOtvet);

                if (a5 != 0) {
                    memori2 = memori1 - a5;
                } else {
                    if (a6 != 0) {
                        memori2 = memori1 - a6;
                    } else return;
                }
                memori1 = memori2;
                obrabativDlyaRezChislo = Double.valueOf(memori2);
                formatObrabotkiRezEdinui();
                poleMemori.setText("  MEM = " + formatOtveta);
                memori = String.valueOf(memori2);
                poleMemori.setTextColor(Color.parseColor("#FF6D00"));
                number = String.valueOf(0);
                break;

            case R.id.buVivMem:

                String s3 = poleMemori.getText().toString();
                if (s3.contains("M")) {
                    Button b1 = (Button) findViewById(R.id.buVivMem);
                    b1.setEnabled(true);
                } else {
                    Button b2 = (Button) findViewById(R.id.buVivMem);
                    b2.setEnabled(false);
                }

                /*      flagMem = true;*/
                result = Double.parseDouble(memori);
                number = memori;
                obrabativDlyaRezChislo = Double.parseDouble(number);
                formatObrabotkiRezEdinui();
                numberSProb = formatOtveta;

                double a = Double.valueOf(memori);
                obrabativDlyaRezChislo = a;
                formatObrabotkiRezEdinui();
                viborPoliaDlyaZadaniaRazmera = 3;
                CvetIRazmer.razmerPolei();
                vvodChisla.setTextColor(Color.parseColor("#3954F8"));    // Синий цвет CV22
                vvodChisla.setText(formatOtveta + "");

                String s = vichislen.getText().toString();

                if (s.contains("=")) {
                    otvet.setTextColor(Color.parseColor("#30FBDF"));  // Цвет поля ответа
                    vichislen.setText("");
                    otvet.setText("0");

                }

                String s2 = vichislen.getText().toString();
                if (s2.contains("0)") || s2.contains("1)") || s2.contains("2)") || s2.contains("3)") || s2.contains("4)")
                        || s2.contains("5)") || s2.contains("6)") || s2.contains("7)") || s2.contains("8)")
                        || s2.contains("9)") || s2.contains("))")) {
                    udalProbelov();
                    obrabativDlyaRezChislo = Double.parseDouble(udalProbOtvet);
                    formatObrabotkiRezEdinui();
                    vichislen.setText(formatOtveta + " " + operator);
                    otvet.setTextColor(Color.parseColor("#30FBDF"));  // Цвет поля ответа
                }

                number = String.valueOf(0);

                View buVivMem = findViewById(R.id.buVivMem);       // ПРОВОРОТ КНОПКИ
                buVivMem.animate().rotationYBy(360).setDuration(3000);
                break;

            case R.id.buSbrosMem:
                Button helloWorld = findViewById(R.id.buSbrosMem);   // ПРОЗРАЧНОСТЬ КНОПКИ
                helloWorld.setAlpha(0f);
                helloWorld.animate().alpha(1f).setDuration(1500);

                memori = String.valueOf(0);
                poleMemori.setText("");
                memori1 = 0.0;
                Button b2 = (Button) findViewById(R.id.buSbrosMem);
                b2.setEnabled(false);
                Button b20 = (Button) findViewById(R.id.buVivMem);
                b20.setEnabled(false);
                break;
        }
    }

    /* Метод обработки сброса AC */
    public void acClick(View view) throws InterruptedException {
        udalProbelov();
        String s1 = otvet.getText().toString();
        double s2 = Double.parseDouble(udalProbVvod);
        String s3 = vichislen.getText().toString();

        if (s1.contains("0") && s2 == 0 /*!= s1.contains("НЕЛЬЗЯ") */!= s3.contains("=")) {
            Button b2 = (Button) findViewById(R.id.buAC);
            b2.setEnabled(false);
            return;
        }

        Button z = findViewById(R.id.buAC);   // ПРОЗРАЧНОСТЬ КНОПКИ АС
        z.setAlpha(1f);
        z.animate().alpha(0.6f).setDuration(1000);

        viborPoliaDlyaZadaniaRazmera = 4;
        CvetIRazmer.razmerPolei();
            Typeface typefaceLato = getResources().getFont(R.font.abrilfatface_regular);
        otvet.setTypeface(typefaceLato);
        otvet.setTextColor(Color.parseColor("#30FBDF"));  // Цвет поля ответа
        otvet.setText("0");
        zapretVtoroiTochki = false;
        mediaPlayerAC.start();
        vvodChisla.setTextColor(Color.parseColor("#30FF02"));  // светло зелен
        vvodChisla.setText("0 ");
        vichislen.setText("");
        result = 0.0;
        numberSProb = String.valueOf(0);
        newNumberSProb = String.valueOf(0);
        udalProbOtvet = String.valueOf(0);
        oldNumberSProb = String.valueOf(0);
        number = String.valueOf(0);
        newNumber = String.valueOf(0);
        oldNumber = String.valueOf(0);
        isNew = true;
        znakKoren = false;

        int maxLength = 25;
        if (aktivaciyaKnopok == false) {
            aktivaciyaKnopok = true;
            blokiAktivaciyaKnopok();
        }
        otvet.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        udalProbelov();

        View buAC = findViewById(R.id.buAC);                   // ПОВОРОТ КНОПКИ
        buAC.animate().rotationXBy(360).setDuration(2000);
    }

    /* Метод удаления последнего набранного символа DEL */
    public void delitClick(View view) {
        mediaPlayer4.start();
        String s = vvodChisla.getText().toString();
        udalProbelov();

        double s1 = Double.parseDouble(udalProbVvod);

        if (s.contains("e")) {
            double s2 = s1 / 10;
            obrabativDlyaRezChislo = s2;
            formatObrabotkiRezEdinui();
            vvodChisla.setText(formatOtveta);
            udalProbelov();
            number = udalProbFormatOtveta;
            return;
        }
        if (s.length() == 1) {
            viborPoliaDlyaZadaniaRazmera = 3;
            CvetIRazmer.razmerPolei();
            vvodChisla.setTextColor(Color.parseColor("#30FF02"));  // светло зелен
            vvodChisla.setText("0");
            number = String.valueOf(0);
            return;
        }

        s = s.substring(0, s.length() - 1);
        int length = s.length();

        if (length == 0) {
            vvodChisla.setText("0");
            return;
        } else {
            vvodChisla.setText(s);
            udalProbelov();
            obrabativDlyaRezChislo = Double.parseDouble(udalProbVvod);
            formatObrabotkiRezEdinui();
            vvodChisla.setText(formatOtveta);
        }

        s = vvodChisla.getText().toString();
        if (s.contains(".")) {
            zapretVtoroiTochki = true;
        } else {
            zapretVtoroiTochki = false;
        }
        if (s == null || s.length() == 0) {
            vvodChisla.setText("0");
            isNew = true;
        }
        udalProbelov();
        number = udalProbFormatOtveta;
        obrabativDlyaRezChislo = Double.parseDouble(number);
        formatObrabotkiRezEdinui();
        numberSProb = formatOtveta;

        Button helloWorld = findViewById(R.id.buDel);   // ПРОЗРАЧНОСТЬ КНОПКИ
        helloWorld.setAlpha(0.15f);
        helloWorld.animate().alpha(1f).setDuration(2500);
    }

    /* Метод проверки большого числа в ответе */
    public void bolchChisloVOtvet() throws InterruptedException {
        /* otvet.setTextSize(25);*/
        byte maxLength = 50;             // ЗАДАЁТ ДЛИННУ ТЕКСТА
        otvet.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        CvetIRazmer.cvetNadpisei();
        otvet.setTextColor(cvet);
        viborShriftaBolchCifri();
        otvet.setText("Я   НЕ   МОГУ   СЧИТАТЬ ТАКИЕ БОЛЬШИЕ  ЧИСЛА");
        aktivaciyaKnopok = false;
        blokiAktivaciyaKnopok();
        vvodChisla.setTextColor(Color.parseColor("#00E3C4"));  // цвет поля ввода
        vvodChisla.setText("0 ");
    }

    /* Метод проверки количества минусов */
    public boolean minusIsPres(String number) {
        if (number.charAt(0) == '-') {
            return true;
        } else {
            return false;
        }
    }

    /* Метод проверки если стоит 0, то ещё 0 ставить нельзя */
    private boolean zeroIsFirst(String number) {
        if (number.equals("")) {
            return true;
        }
        if (number.charAt(0) == '0') {
            return true;
        } else {
            return false;
        }
    }

    /* Метод проверки если стоит 0, то минус ставить нельзя */
    private boolean numberIsZero(String number) {
        if (number.equals("0") || number.equals("")) {
            vvodChisla.setTextColor(Color.parseColor("#30FF02"));  // светло зелен
            return true;
        } else {
            return false;
        }
    }

    public void viborShriftaPrivet() {
        byte a = (byte) (Math.random() * 5); // Случайное число от 0 до 4

        if (a == 0) {
            otvet.setTextSize(40 - koifPrivet);
            Typeface a1 = getResources().getFont(R.font.pacifico_regular);
            otvet.setTypeface(a1);
        }
        if (a == 1) {
            otvet.setTextSize(33 - koifPrivet);
            Typeface a1 = getResources().getFont(R.font.komi);
            otvet.setTypeface(a1);
        }
        if (a == 2) {
            otvet.setTextSize(48 - koifPrivet);
            Typeface a1 = getResources().getFont(R.font.qwe);
            otvet.setTypeface(a1);
        }
        if (a == 3) {
            otvet.setTextSize(58 - koifPrivet);
            Typeface a1 = getResources().getFont(R.font.vanowitsch);
            otvet.setTypeface(a1);
        }
        if (a == 4) {
            otvet.setTextSize(43 - koifPrivet);
            Typeface a1 = getResources().getFont(R.font.ocker);
            otvet.setTypeface(a1);
        }
    }

    public void viborShriftaBolchCifri() {
        byte a = (byte) (Math.random() * 5); // Случайное число от 0 до 4

        if (a == 0) {
            otvet.setTextSize(18);
            Typeface a1 = getResources().getFont(R.font.pacifico_regular);
            otvet.setTypeface(a1);
        }
        if (a == 1) {
            otvet.setTextSize(19);
            Typeface a1 = getResources().getFont(R.font.komi);
            otvet.setTypeface(a1);
        }
        if (a == 2) {
            otvet.setTextSize(29);
            Typeface a1 = getResources().getFont(R.font.qwe);
            otvet.setTypeface(a1);
        }
        if (a == 3) {
            otvet.setTextSize(31);
            Typeface a1 = getResources().getFont(R.font.vanowitsch);
            otvet.setTypeface(a1);
        }
        if (a == 4) {
            otvet.setTextSize(25);
            Typeface a1 = getResources().getFont(R.font.ocker);
            otvet.setTypeface(a1);
        }
    }

    public void viborShriftaDelen() {
        byte a = (byte) (Math.random() * 5); // Случайное число от 0 до 4

        if (a == 0) {
            otvet.setTextSize(24);
            Typeface a1 = getResources().getFont(R.font.pacifico_regular);
            otvet.setTypeface(a1);
        }
        if (a == 1) {
            otvet.setTextSize(20);
            Typeface a1 = getResources().getFont(R.font.komi);
            otvet.setTypeface(a1);
        }
        if (a == 2) {
            otvet.setTextSize(27);
            Typeface a1 = getResources().getFont(R.font.qwe);
            otvet.setTypeface(a1);
        }
        if (a == 3) {
            otvet.setTextSize(35);
            Typeface a1 = getResources().getFont(R.font.vanowitsch);
            otvet.setTypeface(a1);
        }
        if (a == 4) {
            otvet.setTextSize(25);
            Typeface a1 = getResources().getFont(R.font.ocker);
            otvet.setTypeface(a1);
        }
    }

    public void viborShriftaKoren() {
        byte a = (byte) (Math.random() * 5); // Случайное число от 0 до 9

        if (a == 0) {
            otvet.setTextSize(18);
            Typeface a1 = getResources().getFont(R.font.pacifico_regular);
            otvet.setTypeface(a1);
        }
        if (a == 1) {
            otvet.setTextSize(16);
            Typeface a1 = getResources().getFont(R.font.komi);
            otvet.setTypeface(a1);
        }
        if (a == 2) {
            otvet.setTextSize(22);
            Typeface a1 = getResources().getFont(R.font.qwe);
            otvet.setTypeface(a1);
        }
        if (a == 3) {
            otvet.setTextSize(31);
            Typeface a1 = getResources().getFont(R.font.vanowitsch);
            otvet.setTypeface(a1);
        }
        if (a == 4) {
            otvet.setTextSize(22);
            Typeface a1 = getResources().getFont(R.font.ocker);
            otvet.setTypeface(a1);
        }
        vvodChisla.setText("");
    }

    public void blokiAktivaciyaKnopok() throws InterruptedException {

        if (aktivaciyaKnopok == false) {
            Button bb = (Button) findViewById(R.id.buAC);
            bb.setEnabled(true);
            Button z = findViewById(R.id.buAC);   // ПРОЗРАЧНОСТЬ КНОПКИ
            z.setAlpha(0.6f);
            z.animate().alpha(1.0f).setDuration(1000);
            vvodChisla.setTextColor(Color.parseColor("#00E3C4"));  // цвет поля ввода

            buAC.setTextColor(Color.parseColor("#63FF00")); // Цвет текста кнопки AC
            buAC.setBackgroundColor(Color.parseColor("#FF00B7"));   // Цвет поля кнопки AC

            Button b1 = (Button) findViewById(R.id.bu1);
            b1.setEnabled(false);
            Button a1 = findViewById(R.id.bu1);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a1.setAlpha(1f);
            a1.animate().alpha(0.13f).setDuration(2000);
            Button b2 = (Button) findViewById(R.id.bu2);
            b2.setEnabled(false);
            Button a2 = findViewById(R.id.bu2);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a2.setAlpha(1f);
            a2.animate().alpha(0.13f).setDuration(2000);
            Button b3 = (Button) findViewById(R.id.bu3);
            b3.setEnabled(false);
            Button a3 = findViewById(R.id.bu3);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a3.setAlpha(1f);
            a3.animate().alpha(0.13f).setDuration(2000);

            Button b4 = (Button) findViewById(R.id.bu4);
            b4.setEnabled(false);
            Button a4 = findViewById(R.id.bu4);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a4.setAlpha(1f);
            a4.animate().alpha(0.18f).setDuration(1500);
            Button b5 = (Button) findViewById(R.id.bu5);
            b5.setEnabled(false);
            Button a5 = findViewById(R.id.bu5);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a5.setAlpha(1f);
            a5.animate().alpha(0.18f).setDuration(1500);
            Button b6 = (Button) findViewById(R.id.bu6);
            b6.setEnabled(false);
            Button a6 = findViewById(R.id.bu6);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a6.setAlpha(1f);
            a6.animate().alpha(0.18f).setDuration(1500);

            Button b7 = (Button) findViewById(R.id.bu7);
            b7.setEnabled(false);
            Button a7 = findViewById(R.id.bu7);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a7.setAlpha(1f);
            a7.animate().alpha(0.23f).setDuration(1000);
            Button b8 = (Button) findViewById(R.id.bu8);
            b8.setEnabled(false);
            Button a8 = findViewById(R.id.bu8);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a8.setAlpha(1f);
            a8.animate().alpha(0.23f).setDuration(1000);
            Button b9 = (Button) findViewById(R.id.bu9);
            b9.setEnabled(false);
            Button a9 = findViewById(R.id.bu9);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a9.setAlpha(1f);
            a9.animate().alpha(0.23f).setDuration(1000);

            Button b0 = (Button) findViewById(R.id.bu0);
            b0.setEnabled(false);
            Button a0 = findViewById(R.id.bu0);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a0.setAlpha(1f);
            a0.animate().alpha(0.08f).setDuration(2500);

            Button b11 = (Button) findViewById(R.id.buVozvedVKvadrat);
            b11.setEnabled(false);
            Button a11 = findViewById(R.id.buVozvedVKvadrat);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a11.setAlpha(0.001f);
            a11.animate().alpha(0.2f).setDuration(2500);
            Button b12 = (Button) findViewById(R.id.buDelX);
            b12.setEnabled(false);
            Button a12 = findViewById(R.id.buDelX);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a12.setAlpha(0.001f);
            a12.animate().alpha(0.2f).setDuration(2500);

            Button b13 = (Button) findViewById(R.id.buPlusMinus);
            b13.setEnabled(false);
            Button a13 = findViewById(R.id.buPlusMinus);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a13.setAlpha(0.001f);
            a13.animate().alpha(0.2f).setDuration(2500);
            Button b14 = (Button) findViewById(R.id.buPercent);
            b14.setEnabled(false);
            Button a14 = findViewById(R.id.buPercent);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a14.setAlpha(0.001f);
            a14.animate().alpha(0.2f).setDuration(2500);
            Button b15 = (Button) findViewById(R.id.buKoren);
            b15.setEnabled(false);
            Button a15 = findViewById(R.id.buKoren);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a15.setAlpha(0.001f);
            a15.animate().alpha(0.2f).setDuration(2500);

            Button b16 = (Button) findViewById(R.id.buDel);
            b16.setEnabled(false);
            View a16 = findViewById(R.id.buDel);              // ПОВОРОТ КНОПКИ ВЛЕВО
            a16.animate().rotationYBy(90).setDuration(1000);

            Button b17 = (Button) findViewById(R.id.buMultiply);
            b17.setEnabled(false);
            View a17 = findViewById(R.id.buMultiply);                   // ПОВОРОТ КНОПКИ ВНИЗ
            a17.animate().rotationXBy(90).setDuration(500);
            Button b18 = (Button) findViewById(R.id.buDivide);
            b18.setEnabled(false);
            View a18 = findViewById(R.id.buDivide);                   // ПОВОРОТ КНОПКИ ВНИЗ
            a18.animate().rotationXBy(90).setDuration(1000);
            Button b19 = (Button) findViewById(R.id.buPlus);
            b19.setEnabled(false);
            View a19 = findViewById(R.id.buPlus);                   // ПОВОРОТ КНОПКИ ВНИЗ
            a19.animate().rotationXBy(90).setDuration(1500);
            Button b20 = (Button) findViewById(R.id.buMinus);
            b20.setEnabled(false);
            View a20 = findViewById(R.id.buMinus);                   // ПОВОРОТ КНОПКИ ВНИЗ
            a20.animate().rotationXBy(90).setDuration(2000);

            Button b21 = (Button) findViewById(R.id.buRavn);
            b21.setEnabled(false);
            Button a21 = findViewById(R.id.buRavn);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a21.setAlpha(1f);
            a21.animate().alpha(0.001f).setDuration(6000);
            Button b22 = (Button) findViewById(R.id.buZap);
            b22.setEnabled(false);
            Button a22 = findViewById(R.id.buZap);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a22.setAlpha(1f);
            a22.animate().alpha(0.001f).setDuration(10000);

            Button b23 = (Button) findViewById(R.id.buSbrosMem);
            b23.setEnabled(false);
            Button a23 = findViewById(R.id.buSbrosMem);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a23.setAlpha(1f);
            a23.animate().alpha(0.2f).setDuration(1000);
            Button b24 = (Button) findViewById(R.id.buVivMem);
            b24.setEnabled(false);
            Button a24 = findViewById(R.id.buVivMem);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a24.setAlpha(1f);
            a24.animate().alpha(0.2f).setDuration(2000);
            Button b25 = (Button) findViewById(R.id.buMinusMem);
            b25.setEnabled(false);
            Button a25 = findViewById(R.id.buMinusMem);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a25.setAlpha(1f);
            a25.animate().alpha(0.2f).setDuration(3000);
            Button b26 = (Button) findViewById(R.id.buPlusMem);
            b26.setEnabled(false);
            Button a26 = findViewById(R.id.buPlusMem);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a26.setAlpha(1f);
            a26.animate().alpha(0.2f).setDuration(4000);
            Button b27 = (Button) findViewById(R.id.buZapMem);
            b27.setEnabled(false);
            Button a27 = findViewById(R.id.buZapMem);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a27.setAlpha(1f);
            a27.animate().alpha(0.2f).setDuration(5000);

        } else {
            Button z = findViewById(R.id.buAC);   // ПРОЗРАЧНОСТЬ КНОПКИ
            z.setAlpha(1f);
            z.animate().alpha(0.6f).setDuration(1000);
            vvodChisla.setTextColor(Color.parseColor("#30FF02"));  // светло зелен

            buAC.setTextColor(Color.parseColor("#FF003C")); // Цвет текста кнопки AC
            buAC.setBackgroundColor(Color.parseColor("#FBF23B"));   // Цвет поля кнопки AC

            Button b1 = (Button) findViewById(R.id.bu1);
            b1.setEnabled(true);
            Button a1 = findViewById(R.id.bu1);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a1.setAlpha(0.13f);
            a1.animate().alpha(1f).setDuration(500);
            Button b2 = (Button) findViewById(R.id.bu2);
            b2.setEnabled(true);
            Button a2 = findViewById(R.id.bu2);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a2.setAlpha(0.13f);
            a2.animate().alpha(1f).setDuration(500);
            Button b3 = (Button) findViewById(R.id.bu3);
            b3.setEnabled(true);
            Button a3 = findViewById(R.id.bu3);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a3.setAlpha(0.13f);
            a3.animate().alpha(1f).setDuration(500);

            Button b4 = (Button) findViewById(R.id.bu4);
            b4.setEnabled(true);
            Button a4 = findViewById(R.id.bu4);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a4.setAlpha(0.18f);
            a4.animate().alpha(1f).setDuration(700);
            Button b5 = (Button) findViewById(R.id.bu5);
            b5.setEnabled(true);
            Button a5 = findViewById(R.id.bu5);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a5.setAlpha(0.18f);
            a5.animate().alpha(1f).setDuration(700);
            Button b6 = (Button) findViewById(R.id.bu6);
            b6.setEnabled(true);
            Button a6 = findViewById(R.id.bu6);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a6.setAlpha(0.18f);
            a6.animate().alpha(1f).setDuration(700);

            Button b7 = (Button) findViewById(R.id.bu7);
            b7.setEnabled(true);
            Button a7 = findViewById(R.id.bu7);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a7.setAlpha(0.23f);
            a7.animate().alpha(1f).setDuration(900);
            Button b8 = (Button) findViewById(R.id.bu8);
            b8.setEnabled(true);
            Button a8 = findViewById(R.id.bu8);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a8.setAlpha(0.23f);
            a8.animate().alpha(1f).setDuration(900);
            Button b9 = (Button) findViewById(R.id.bu9);
            b9.setEnabled(true);
            Button a9 = findViewById(R.id.bu9);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a9.setAlpha(0.23f);
            a9.animate().alpha(1f).setDuration(900);
            Button b0 = (Button) findViewById(R.id.bu0);
            b0.setEnabled(true);
            Button a0 = findViewById(R.id.bu0);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a0.setAlpha(0.23f);
            a0.animate().alpha(1f).setDuration(300);

            Button b11 = (Button) findViewById(R.id.buVozvedVKvadrat);
            b11.setEnabled(true);
            Button a11 = findViewById(R.id.buVozvedVKvadrat);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a11.setAlpha(0.2f);
            a11.animate().alpha(1f).setDuration(1500);
            Button b12 = (Button) findViewById(R.id.buDelX);
            b12.setEnabled(true);
            Button a12 = findViewById(R.id.buDelX);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a12.setAlpha(0.2f);
            a12.animate().alpha(1f).setDuration(1500);

            Button b13 = (Button) findViewById(R.id.buPlusMinus);
            b13.setEnabled(true);
            Button a13 = findViewById(R.id.buPlusMinus);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a13.setAlpha(0.2f);
            a13.animate().alpha(1f).setDuration(1200);
            Button b14 = (Button) findViewById(R.id.buPercent);
            b14.setEnabled(true);
            Button a14 = findViewById(R.id.buPercent);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a14.setAlpha(0.2f);
            a14.animate().alpha(1f).setDuration(1200);
            Button b15 = (Button) findViewById(R.id.buKoren);
            b15.setEnabled(true);
            Button a15 = findViewById(R.id.buKoren);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a15.setAlpha(0.2f);
            a15.animate().alpha(1f).setDuration(1200);

            Button b16 = (Button) findViewById(R.id.buDel);
            b16.setEnabled(true);
            View a16 = findViewById(R.id.buDel);              // ПОВОРОТ КНОПКИ ВЛЕВО
            a16.animate().rotationYBy(-90).setDuration(500);
            Button b17 = (Button) findViewById(R.id.buMultiply);
            b17.setEnabled(true);
            View a17 = findViewById(R.id.buMultiply);                   // ПОВОРОТ КНОПКИ ВНИЗ
            a17.animate().rotationXBy(-90).setDuration(2000);
            Button b18 = (Button) findViewById(R.id.buDivide);
            b18.setEnabled(true);
            View a18 = findViewById(R.id.buDivide);                   // ПОВОРОТ КНОПКИ ВНИЗ
            a18.animate().rotationXBy(-90).setDuration(1500);
            Button b19 = (Button) findViewById(R.id.buPlus);
            b19.setEnabled(true);
            View a19 = findViewById(R.id.buPlus);                   // ПОВОРОТ КНОПКИ ВНИЗ
            a19.animate().rotationXBy(-90).setDuration(1000);
            Button b20 = (Button) findViewById(R.id.buMinus);
            b20.setEnabled(true);
            View a20 = findViewById(R.id.buMinus);                   // ПОВОРОТ КНОПКИ ВНИЗ
            a20.animate().rotationXBy(-90).setDuration(500);
            Button b21 = (Button) findViewById(R.id.buRavn);
            b21.setEnabled(true);
            Button a21 = findViewById(R.id.buRavn);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a21.setAlpha(0.001f);
            a21.animate().alpha(1f).setDuration(300);
            Button b22 = (Button) findViewById(R.id.buZap);
            b22.setEnabled(true);
            Button a22 = findViewById(R.id.buZap);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a22.setAlpha(0.001f);
            a22.animate().alpha(1f).setDuration(300);

            Button b23 = (Button) findViewById(R.id.buSbrosMem);
            b23.setEnabled(true);
            Button a23 = findViewById(R.id.buSbrosMem);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a23.setAlpha(0.2f);
            a23.animate().alpha(1f).setDuration(2000);
            Button b24 = (Button) findViewById(R.id.buVivMem);
            b24.setEnabled(true);
            Button a24 = findViewById(R.id.buVivMem);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a24.setAlpha(0.2f);
            a24.animate().alpha(1f).setDuration(1000);
            Button b25 = (Button) findViewById(R.id.buMinusMem);
            b25.setEnabled(true);
            Button a25 = findViewById(R.id.buMinusMem);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a25.setAlpha(0.2f);
            a25.animate().alpha(1f).setDuration(500);
            Button b26 = (Button) findViewById(R.id.buPlusMem);
            b26.setEnabled(true);
            Button a26 = findViewById(R.id.buPlusMem);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a26.setAlpha(0.2f);
            a26.animate().alpha(1f).setDuration(1000);
            Button b27 = (Button) findViewById(R.id.buZapMem);
            b27.setEnabled(true);
            Button a27 = findViewById(R.id.buZapMem);   // ПРОЗРАЧНОСТЬ КНОПКИ
            a27.setAlpha(0.2f);
            a27.animate().alpha(1f).setDuration(2000);
        }
    }
}

