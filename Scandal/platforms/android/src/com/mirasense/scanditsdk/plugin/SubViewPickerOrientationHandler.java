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

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.OrientationEventListener;

import com.scandit.base.system.SbSystemUtils;

import org.apache.cordova.CordovaPlugin;

/**
 * Handler to check the orientation of the phone and adjust the margins based on it. Used by the
 * subview picker controller only, since the full-screen picker doesn't support margins.
 */
final class SubViewPickerOrientationHandler extends Handler {

    final static int CHECK_ORIENTATION = 1;
    final static int SET_PICKER = 2;
    private int mLastRotation = 0;
    private boolean mRunning = false;

    CordovaPlugin mPlugin;
    BarcodePickerWithSearchBar mPicker;
    Point mScreenDimensions = new Point(0, 0);

    public SubViewPickerOrientationHandler(Looper mainLooper, CordovaPlugin plugin,
                                           BarcodePickerWithSearchBar picker) {
        super(mainLooper);
        mPlugin = plugin;
        mPicker = picker;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        switch (msg.what) {
            case CHECK_ORIENTATION:
                if (mRunning) {
                    checkOrientation();
                }
                break;
            case SET_PICKER:
                mPicker = (BarcodePickerWithSearchBar)msg.obj;
        }
    }

    void setScreenDimensions(Point d) {
        mScreenDimensions.set(d.x, d.y);
    }

    void setPicker(BarcodePickerWithSearchBar picker) {
        this.sendMessage(this.obtainMessage(SET_PICKER, picker));
    }

    public void start() {
        if (!mRunning) {
            mRunning = true;
            mLastRotation = SbSystemUtils.getDisplayRotation(mPlugin.cordova.getActivity());
            this.sendEmptyMessageDelayed(SubViewPickerOrientationHandler.CHECK_ORIENTATION, 20);
        }
    }

    public void stop() {
        if (mRunning) {
            mRunning = false;
        }
    }

    private void checkOrientation() {
        Context context = mPlugin.cordova.getActivity();
        if (context == null || mPicker == null || mScreenDimensions.equals(0, 0)) {
            this.sendEmptyMessageDelayed(SubViewPickerOrientationHandler.CHECK_ORIENTATION, 20);
            return;
        }

        int displayRotation = SbSystemUtils.getDisplayRotation(context);
        if (displayRotation == mLastRotation) {
            this.sendEmptyMessageDelayed(SubViewPickerOrientationHandler.CHECK_ORIENTATION, 20);
            return;
        }

        mPlugin.cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                Bundle constraints = new Bundle();
                addConstraintToBundle(constraints, PhonegapParamParser.paramMarginLeft,
                        BarcodePickerWithSearchBar.portraitConstraints.getLeftMargin());
                addConstraintToBundle(constraints, PhonegapParamParser.paramMarginTop,
                        BarcodePickerWithSearchBar.portraitConstraints.getTopMargin());
                addConstraintToBundle(constraints, PhonegapParamParser.paramMarginRight,
                        BarcodePickerWithSearchBar.portraitConstraints.getRightMargin());
                addConstraintToBundle(constraints, PhonegapParamParser.paramMarginBottom,
                        BarcodePickerWithSearchBar.portraitConstraints.getBottomMargin());
                addConstraintToBundle(constraints, PhonegapParamParser.paramWidth,
                        BarcodePickerWithSearchBar.portraitConstraints.getWidth());
                addConstraintToBundle(constraints, PhonegapParamParser.paramHeight,
                        BarcodePickerWithSearchBar.portraitConstraints.getHeight());
                bundle.putBundle(PhonegapParamParser.paramPortraitConstraints, constraints);

                constraints = new Bundle();
                addConstraintToBundle(constraints, PhonegapParamParser.paramMarginLeft,
                        BarcodePickerWithSearchBar.landscapeConstraints.getLeftMargin());
                addConstraintToBundle(constraints, PhonegapParamParser.paramMarginTop,
                        BarcodePickerWithSearchBar.landscapeConstraints.getTopMargin());
                addConstraintToBundle(constraints, PhonegapParamParser.paramMarginRight,
                        BarcodePickerWithSearchBar.landscapeConstraints.getRightMargin());
                addConstraintToBundle(constraints, PhonegapParamParser.paramMarginBottom,
                        BarcodePickerWithSearchBar.landscapeConstraints.getBottomMargin());
                addConstraintToBundle(constraints, PhonegapParamParser.paramWidth,
                        BarcodePickerWithSearchBar.landscapeConstraints.getWidth());
                addConstraintToBundle(constraints, PhonegapParamParser.paramHeight,
                        BarcodePickerWithSearchBar.landscapeConstraints.getHeight());
                bundle.putBundle(PhonegapParamParser.paramLandscapeConstraints, constraints);

                PhonegapParamParser.updateLayout(mPlugin.cordova.getActivity(),
                                                 mPicker, bundle, mScreenDimensions);
            }
        });
        mLastRotation = displayRotation;
        this.sendEmptyMessageDelayed(SubViewPickerOrientationHandler.CHECK_ORIENTATION, 20);
    }

    private void addConstraintToBundle(Bundle bundle, String constraintKey,
                                       Integer constraintValue) {
        if (constraintValue != null) {
            bundle.putInt(constraintKey, constraintValue);
        }
    }
}
