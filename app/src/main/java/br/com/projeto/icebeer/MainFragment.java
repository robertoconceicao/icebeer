/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.projeto.icebeer;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Encapsulates fetching the forecast and displaying it as a {@link ListView} layout.
 */
public class MainFragment extends Fragment {

    Double temperatura;
    Timer timer;
    TimerTask timerTask;
    final Handler handler = new Handler();

    public MainFragment() {
        temperatura = 5.00;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        /*
        TextView tx = (TextView) getActivity().findViewById(R.id.txtTemperatura);
        tx.setText("000");
        tx.setAllCaps(true);
        tx.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 45);
        tx.setShadowLayer(8, 15, 15, Color.GRAY);
        */
        //Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/digital-7.ttf");
        //tx.setTypeface(tf);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mainfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            if(timer == null){
                startTimer();
            } else {
                stoptimertask();
            }
            return true;
        }

        if (id == R.id.action_test) {
            TestConexaoFragment tcf = new TestConexaoFragment();
            getFragmentManager().beginTransaction().add(R.id.container, tcf).commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        // TODO cria a tela
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void updateTemperatura() {
        if(--temperatura < -5){
            temperatura = 5.0;
        }
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/digital-7.ttf");

        TextView tx = (TextView) getActivity().findViewById(R.id.txtTemperatura);
        tx.setText(String.valueOf(temperatura));
        //tx.setAllCaps(true);
        //tx.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 45);
        //tx.setShadowLayer(5, 5, 5, Color.GRAY);
        tx.setTextSize(80);
        tx.setTypeface(tf);

        TextView sw = (TextView) getActivity().findViewById(R.id.shadowTemperatura);
        sw.setText("88.8");
        sw.setTextSize(80);

        sw.setTypeface(tf);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateTemperatura();
    }

    public void startTimer() {
        //set a new Timer
        timer = new Timer();
        //initialize the TimerTask's job
        initializeTimerTask();
        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 5000, 5000);
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        updateTemperatura();
                    }
                });
            }
        };
    }

}