//  Copyright 2016 Scandit AG
//
//  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
//  in compliance with the License. You may obtain a copy of the License at
//
//  http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software distributed under the
//  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
//  express or implied. See the License for the specific language governing permissions and
//  limitations under the License.
package com.mirasense.scanditsdk.plugin;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Created by mo on 10/12/15.
 */
public class ScanditWorker extends HandlerThread {

    private Handler mHandler;

    public ScanditWorker() {
        super("ScanditWorker");
    }

    @Override
    public void start() {
        super.start();
        mHandler = new Handler(getLooper());
    }

    public Handler getHandler() {
        return mHandler;
    }
}
