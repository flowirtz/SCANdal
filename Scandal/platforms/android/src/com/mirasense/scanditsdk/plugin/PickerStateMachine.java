
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

import com.scandit.barcodepicker.ScanSession;
import com.scandit.barcodepicker.ScanSettings;

import java.lang.ref.WeakReference;

/**
 * @brief Simple state machine for managing the different states of the barcode picker
 */
class PickerStateMachine {

    // must match values of JS wrapper
    public final static int STOPPED =  2;
    public final static int PAUSED =   1;
    public final static int ACTIVE =   3;

    public void applyScanSettings(ScanSettings scanSettings) {
        mPicker.applyScanSettings(scanSettings);
    }

    public int getState() {
        return mCurrentState;
    }

    interface Callback {
        /**
         * Invoked whenever the state of the barcode picker changes to a new state.
         * @param picker The picker
         * @param newState the new state of the picker
         */
        void pickerEnteredState(BarcodePickerWithSearchBar picker, int newState);
    }


    private final BarcodePickerWithSearchBar mPicker;
    private int mCurrentState = STOPPED;


    private WeakReference<Callback> mCallback;


    /**
     * Create a new picker state machine.
     * @param picker The picker for which to control the states. Must not be null.
     * @param callback The state change callback gets invoked whenever the state of the picker
     *                 changes. The callback is stored as a weak reference, so make sure to keep a
     *                 reference to the object around.
     */
    PickerStateMachine(BarcodePickerWithSearchBar picker, Callback callback) {
        mPicker = picker;
        mCallback = new WeakReference<Callback>(callback);
    }

    public int setState(int state) {
        if (mCurrentState == state) {
            return state;
        }
        switch (state) {
            case ACTIVE:
                transitionToActiveState(false);
                break;
            case PAUSED:
                transitionToPausedState();
                break;
            case STOPPED:
                transitionToStoppedState();
                break;

        }
        Callback cb = mCallback.get();
        if (cb != null) {
            cb.pickerEnteredState(mPicker, state);
        }
        mCurrentState = state;
        return state;
    }

    public void startScanning() {
        if (mCurrentState == ACTIVE) {
            return;
        }
        transitionToActiveState(true);
        Callback cb = mCallback.get();
        if (cb != null) {
            cb.pickerEnteredState(mPicker, ACTIVE);
        }
        mCurrentState = ACTIVE;
    }

    private void transitionToStoppedState() {
        if (mCurrentState == ACTIVE || mCurrentState == PAUSED) {
            mPicker.stopScanning();
        }
    }

    private void transitionToPausedState() {
        if (mCurrentState == ACTIVE) {
            mPicker.pauseScanning();
            return;
        }
        if (mCurrentState == STOPPED) {
            mPicker.startScanning(true);
        }
    }

    private void transitionToActiveState(boolean useStartInsteadOfResume) {
        if (mCurrentState == STOPPED) {
            mPicker.startScanning();
        }
        if (mCurrentState == PAUSED) {
            if (useStartInsteadOfResume) {
                mPicker.startScanning();
            } else {
                mPicker.resumeScanning();
            }

        }
    }

    public BarcodePickerWithSearchBar getPicker() {
        return mPicker;
    }

    /**
     * Implements the logic of handling picker state changes sent from the didScan callback.
     * @param nextState The next state.
     * @param session The implementation calls stopScanning/pauseScanning if the provided session
     *                is non-null.
     */
    public void switchToNextScanState(int nextState, ScanSession session) {
        if (nextState == STOPPED) {
            if (mCurrentState == ACTIVE || mCurrentState == PAUSED) {
                if (session != null) {
                    session.stopScanning();
                } else {
                    mPicker.stopScanning();
                }
            }
            mCurrentState = STOPPED;
            Callback cb = mCallback.get();
            if (cb != null) {
                cb.pickerEnteredState(mPicker, STOPPED);
            }
            return;
        }
        if (nextState == PAUSED) {
            if (mCurrentState == ACTIVE) {
                if (session != null) {
                    session.pauseScanning();
                } else {
                    mPicker.pauseScanning();
                }
            }
            mCurrentState = PAUSED;
            Callback cb = mCallback.get();
            if (cb != null) {
                cb.pickerEnteredState(mPicker, PAUSED);
            }
        }
    }
}
