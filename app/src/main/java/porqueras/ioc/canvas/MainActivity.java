package porqueras.ioc.canvas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.time.LocalDateTime;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public int contador = 0;
    public long tiempoInicial=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new EjemploView(this));
        //setContentView(R.layout.activity_main);
        
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        AsyncTask<Void, Void, Void> t1 = new Task1().execute();

    }

    public class EjemploView extends View {
        Paint pincel;
        final int DIMENSION = 4;
        final int OFFSET = 24;

        public EjemploView(Context context) {
            super(context);
            pincel = new Paint();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        protected void onDraw(Canvas canvas) {

            pincel = new Paint();
            pincel.setStyle(Paint.Style.FILL_AND_STROKE);
            float y = OFFSET;
            float x = OFFSET;
            float maxX = canvas.getHeight();
            float maxY = canvas.getWidth();
            Random r1 = new Random();

            while (y < (192 * DIMENSION) + OFFSET) {
                while (x < (256 * DIMENSION) + OFFSET) {
                    pincel.setColor(Color.argb(255, r1.nextInt(255), r1.nextInt(255), r1.nextInt(255)));
                    canvas.drawRect(x, y, x + DIMENSION, y + DIMENSION, pincel);
                    x = x + DIMENSION;
                }
                x = OFFSET;
                y = y + DIMENSION;
            }
            pincel.setColor(Color.RED);
            pincel.setTextSize(50);
            canvas.drawText("Hora: " + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute(), 400, 1100, pincel);
            canvas.drawText("Contador=" + contador, 400, 1200, pincel);
            long tiempoFinal=System.currentTimeMillis()-tiempoInicial;
            canvas.drawText("Tiempo actualización pantalla=" + tiempoFinal+" ms", 100, 1300, pincel);
            tiempoInicial=System.currentTimeMillis();
            invalidate();
        }
    }

    public class Task1 extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            while (true) {
                contador++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}