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

import android.os.Bundle;

import com.scandit.barcodepicker.ScanSession;
import com.scandit.recognition.Barcode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ResultRelay {
    
    private static Callback mCallback;
    
    public static void setCallback(Callback callback) {
        mCallback = callback;
    }
    
    public static int relayResult(Bundle bundle) {
        if (mCallback != null) {
            return mCallback.onRelayedResult(bundle);
        }
        return 0;
    }
    
    public static JSONObject jsonForSession(ScanSession session) {
        JSONObject json = new JSONObject();
        try {
            json.put("newlyRecognizedCodes", jsonForCodes(session.getNewlyRecognizedCodes()));
            json.put("newlyLocalizedCodes", jsonForCodes(session.getNewlyLocalizedCodes()));
            json.put("allRecognizedCodes", jsonForCodes(session.getAllRecognizedCodes()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static JSONArray jsonForCodes(List<Barcode> codes) {
        JSONArray array = new JSONArray();

        for (Barcode code : codes) {
            JSONObject object = new JSONObject();
            try {
                object.put("symbology", code.getSymbologyName());
                object.put("gs1DataCarrier", code.isGs1DataCarrier());
                object.put("recognized", code.isRecognized());
                object.put("data", code.getData());
                object.put("compositeFlag", code.getCompositeFlag());
                object.put("uniqueId", code.getHandle());
                if (code.isRecognized()) {
                    JSONArray bytes = new JSONArray();
                    byte[] rawData = code.getRawData();
                    for (byte theByte : rawData) {
                        bytes.put((int)theByte);
                    }
                    object.put("rawData", bytes);
                }
                array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return array;
    }
    
    public interface Callback {
        int onRelayedResult(Bundle bundle);
    }
}
