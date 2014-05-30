package com.ruan65.notfinalcountdown.app;


import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CountDown extends ActionBarActivity implements View.OnClickListener {

    private CountyDownyTimer cdTimer;
    private boolean started = false;
    private Button startBtn;
    public TextView tv;
    private long startTime = 20_000;
    private final long interval = 2_000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count_down);

        startBtn = (Button) findViewById(R.id.button);
        startBtn.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.timer);


        tv.setText(tv.getText() + String.valueOf(startTime / 1000));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.count_down, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startTime = 60_000;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {


        if (!started) {

            cdTimer = new MyCDTimer(startTime, interval);

            new Thread("background") {
                @Override
                public void run() {
                    Looper.prepare();  // )))
                    cdTimer.start();
                    Looper.loop();
                }
            }.start();

            started = true;
            startBtn.setText("STOP");
        } else {
            cdTimer.cancel();
            started = false;
            startBtn.setText("RESTART");
        }

    }

    public class MyCDTimer extends CountyDownyTimer {


        public MyCDTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void onTick(long l) {
            tv.setText("" + l / 1000);
        }

        @Override
        public void onFinish() {
            tv.setText("Time's up!");
        }
    }
}
