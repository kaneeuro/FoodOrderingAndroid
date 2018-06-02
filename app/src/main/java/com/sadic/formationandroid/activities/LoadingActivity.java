package com.sadic.formationandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sadic.formationandroid.R;

import java.util.Random;

public class LoadingActivity extends Activity {
    Random random = new Random();
    double valeur=1.0;
    int progressStatus = 0;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deconnexion);
    }

    public void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    handler.post(new Runnable() {
                        public void run() {
                            if (valeur != 0.0) {
                                progressStatus += random.nextInt(3);
                            }
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (progressStatus >= 100) {
                    Intent i = new Intent(LoadingActivity.this, InvoicesActivity.class);
                    startActivity(i);
                    LoadingActivity.this.finish();

                }
            }
        }).start();
    }

    public void onStop() {
        super.onStop();
        onDestroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onDestroy();
    }
}

