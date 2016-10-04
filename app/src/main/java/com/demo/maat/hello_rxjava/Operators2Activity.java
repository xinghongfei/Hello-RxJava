/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package com.demo.maat.hello_rxjava;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.demo.maat.hello_rxjava.common.activities.SampleActivityBase;
import com.demo.maat.hello_rxjava.common.logger.Log;
import com.demo.maat.hello_rxjava.common.logger.LogFragment;
import com.demo.maat.hello_rxjava.common.logger.LogWrapper;
import com.demo.maat.hello_rxjava.common.logger.MessageOnlyLogFilter;


public class Operators2Activity extends SampleActivityBase {

    public static final String TAG = "Operators1Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operator2_activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Operators2Fragment fragment = new Operators2Fragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }




    @Override
    public void initializeLogging() {
        LogWrapper logWrapper = new LogWrapper();
        Log.setLogNode(logWrapper);

        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
        LogFragment logFragment = (LogFragment) getSupportFragmentManager()
                .findFragmentById(R.id.log_fragment);
        msgFilter.setNext(logFragment.getLogView());

        Log.i(TAG, "Ready");
    }
}
