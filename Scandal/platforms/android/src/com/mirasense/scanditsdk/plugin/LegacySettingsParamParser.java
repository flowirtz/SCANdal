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
import android.util.Log;

import com.mirasense.scanditsdk.ScanditSDKScanSettings;
import com.scandit.barcodepicker.ScanSettings;
import com.scandit.recognition.Barcode;
import com.scandit.recognition.SymbologySettings;

public class LegacySettingsParamParser {
    public static final String paramPreferFrontCamera = "preferFrontCamera".toLowerCase();
    public static final String param1DScanning = "1DScanning".toLowerCase();
    public static final String param2DScanning = "2DScanning".toLowerCase();
    public static final String paramEan13AndUpc12 = "ean13AndUpc12".toLowerCase();
    public static final String paramEan8 = "ean8".toLowerCase();
    public static final String paramUpce = "upce".toLowerCase();
    public static final String paramCode39 = "code39".toLowerCase();
    public static final String paramCode93 = "code93".toLowerCase();
    public static final String paramCode128 = "code128".toLowerCase();
    public static final String paramItf = "itf".toLowerCase();
    public static final String paramGS1Databar = "gs1Databar".toLowerCase();
    public static final String paramGS1DatabarExpanded = "gs1DatabarExpanded".toLowerCase();
    public static final String paramCodabar = "codabar".toLowerCase();
    public static final String paramCode11 = "code11".toLowerCase();
    public static final String paramQR = "qr".toLowerCase();
    public static final String paramDatamatrix = "datamatrix".toLowerCase();
    public static final String paramPdf417 = "pdf417".toLowerCase();
    public static final String paramAztec = "aztec".toLowerCase();
    public static final String paramMaxiCode = "maxiCode".toLowerCase();
    public static final String paramMsiPlessey = "msiPlessey".toLowerCase();
    public static final String paramMsiPlesseyChecksumType = "msiPlesseyChecksumType".toLowerCase();
    public static final String paramMsiPlesseyChecksumTypeNone = "none".toLowerCase();
    public static final String paramMsiPlesseyChecksumTypeMod11 = "mod11".toLowerCase();
    public static final String paramMsiPlesseyChecksumTypeMod1010 = "mod1010".toLowerCase();
    public static final String paramMsiPlesseyChecksumTypeMod1110 = "mod1110".toLowerCase();
    public static final String paramDataMatrixInverseRecognition = "dataMatrixInverseRecognition".toLowerCase();
    public static final String paramQRInverseRecognition = "qrInverseRecognition".toLowerCase();
    public static final String paramInverseRecognition = "inverseRecognition".toLowerCase();
    public static final String paramMicroDataMatrix = "microDataMatrix".toLowerCase();
    public static final String paramForce2D = "force2d".toLowerCase();
    public static final String paramCodeDuplicateFilter = "codeDuplicateFilter".toLowerCase();
    public static final String paramScanningHotSpot = "scanningHotSpot".toLowerCase();
    public static final String paramScanningHotSpotHeight = "scanningHotSpotHeight".toLowerCase();
    
    public static final String paramZoom = "zoom".toLowerCase();
    public static final String paramDeviceName = "deviceName".toLowerCase();
    
    
    public static ScanSettings getSettings(Bundle options) {
        ScanditSDKScanSettings oldSettings = ScanditSDKScanSettings.getPre43DefaultSettings();
        ScanSettings settings = oldSettings.getScanSettings();
        
        int facing = ScanSettings.CAMERA_FACING_BACK;
        if (options.containsKey(paramPreferFrontCamera) && options.getBoolean(paramPreferFrontCamera)) {
            facing = ScanSettings.CAMERA_FACING_FRONT;
        }
        settings.setCameraFacingPreference(facing);
        
        if (bundleContainsStringKey(options, paramDeviceName)) {
            settings.setDeviceName(options.getString(paramDeviceName));
        }
        
        if (options.containsKey(param1DScanning) && options.getBoolean(param1DScanning)) {
            Log.e("ScanditSDK", "The parameter '1DScanning' is deprecated. Please enable symbologies individually instead");
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_EAN13, true);
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_UPCA, true);
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_UPCE, true);
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_EAN8, true);
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_CODE39, true);
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_CODE128, true);
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_CODE93, true);
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_INTERLEAVED_2_OF_5, true);
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_MSI_PLESSEY, true);
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_CODABAR, true);
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_GS1_DATABAR, true);
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_GS1_DATABAR_EXPANDED, true);
        }
        if (options.containsKey(param2DScanning) && options.getBoolean(param2DScanning)) {
            Log.e("ScanditSDK", "The parameter '2DScanning' is deprecated. Please enable symbologies individually instead");
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_AZTEC, true);
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_DATA_MATRIX, true);
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_QR, true);
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_PDF417, true);
        }
        
        if (options.containsKey(paramEan13AndUpc12)) {
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_EAN13, options.getBoolean(paramEan13AndUpc12));
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_UPCA, options.getBoolean(paramEan13AndUpc12));
        }
        if (options.containsKey(paramEan8)) {
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_EAN8, options.getBoolean(paramEan8));
        }
        if (options.containsKey(paramUpce)) {
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_UPCE, options.getBoolean(paramUpce));
        }
        if (options.containsKey(paramCode39)) {
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_CODE39, options.getBoolean(paramCode39));
        }
        if (options.containsKey(paramCode93)) {
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_CODE93, options.getBoolean(paramCode93));
        }
        if (options.containsKey(paramCode128)) {
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_CODE128, options.getBoolean(paramCode128));
        }
        if (options.containsKey(paramItf)) {
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_INTERLEAVED_2_OF_5,
                                         options.getBoolean(paramItf));
        }
        if (options.containsKey(paramGS1Databar)) {
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_GS1_DATABAR,
                                         options.getBoolean(paramGS1Databar));
        }
        if (options.containsKey(paramGS1DatabarExpanded)) {
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_GS1_DATABAR_EXPANDED,
                                         options.getBoolean(paramGS1DatabarExpanded));
        }
        if (options.containsKey(paramCodabar) && options.getBoolean(paramCodabar)) {
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_CODABAR,
                                         options.getBoolean(paramCodabar));
        }
        if (options.containsKey(paramQR)) {
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_QR, options.getBoolean(paramQR));
        }
        if (options.containsKey(paramDatamatrix)) {
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_DATA_MATRIX,
                                         options.getBoolean(paramDatamatrix));
        }
        if (options.containsKey(paramPdf417)) {
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_PDF417, options.getBoolean(paramPdf417));
        }
        if (options.containsKey(paramAztec)) {
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_AZTEC, options.getBoolean(paramAztec));
        }
        if (options.containsKey(paramMsiPlessey)) {
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_MSI_PLESSEY,
                                         options.getBoolean(paramMsiPlessey));
        }
        if (options.containsKey(paramCode11)) {
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_CODE11, options.getBoolean(paramCode11));
        }
        if (options.containsKey(paramMaxiCode)) {
            settings.setSymbologyEnabled(Barcode.SYMBOLOGY_MAXICODE,
                                         options.getBoolean(paramMaxiCode));
        }
        if (bundleContainsStringKey(options, paramMsiPlesseyChecksumType)) {
            String checksum = options.getString(paramMsiPlesseyChecksumType);
            int actualChecksum = SymbologySettings.CHECKSUM_MOD_10;
            if (checksum.equals(paramMsiPlesseyChecksumTypeNone)) {
                actualChecksum = SymbologySettings.CHECKSUM_NONE;
            } else if (checksum.equals(paramMsiPlesseyChecksumTypeMod11)) {
                actualChecksum = SymbologySettings.CHECKSUM_MOD_11;
            } else if (checksum.equals(paramMsiPlesseyChecksumTypeMod1010)) {
                actualChecksum = SymbologySettings.CHECKSUM_MOD_1010;
            } else if (checksum.equals(paramMsiPlesseyChecksumTypeMod1110)) {
                actualChecksum = SymbologySettings.CHECKSUM_MOD_1110;
            }
            SymbologySettings symbSettings = settings.getSymbologySettings(Barcode.SYMBOLOGY_MSI_PLESSEY);
            symbSettings.setChecksums(actualChecksum);
        }
        
        if (options.containsKey(paramInverseRecognition)) {
            SymbologySettings symbSettingsQr = settings.getSymbologySettings(Barcode.SYMBOLOGY_QR);
            SymbologySettings symbSettingsDm = settings.getSymbologySettings(Barcode.SYMBOLOGY_DATA_MATRIX);
            symbSettingsQr.setColorInvertedEnabled(options.getBoolean(paramInverseRecognition));
            symbSettingsDm.setColorInvertedEnabled(options.getBoolean(paramInverseRecognition));
        }
        
        if (options.containsKey(paramQRInverseRecognition)) {
            SymbologySettings symbSettingsQr = settings.getSymbologySettings(Barcode.SYMBOLOGY_QR);
            symbSettingsQr.setColorInvertedEnabled(options.getBoolean(paramQRInverseRecognition));
        }
        
        if (options.containsKey(paramDataMatrixInverseRecognition)) {
            SymbologySettings symbSettingsDm = settings.getSymbologySettings(Barcode.SYMBOLOGY_DATA_MATRIX);
            symbSettingsDm.setColorInvertedEnabled(options.getBoolean(paramDataMatrixInverseRecognition));
        }
        
        if (options.containsKey(paramMicroDataMatrix)) {
            settings.setMicroDataMatrixEnabled(options.getBoolean(paramMicroDataMatrix));
        }
        
        if (options.containsKey(paramForce2D)) {
            settings.setForce2dRecognitionEnabled(options.getBoolean(paramForce2D));
        }
        
        if (options.containsKey(paramCodeDuplicateFilter)) {
            settings.setCodeDuplicateFilter(options.getInt(paramCodeDuplicateFilter));
        }
        
        if (bundleContainsStringKey(options, paramScanningHotSpot)) {
            String hotspot = options.getString(paramScanningHotSpot);
            String[] split = hotspot.split("[/]");
            if (split.length == 2) {
                try {
                    Float x = Float.valueOf(split[0]);
                    Float y = Float.valueOf(split[1]);
                    settings.setScanningHotSpot(x, y);
                } catch (NumberFormatException e) {}
            }
        }
        
        if (options.containsKey(paramScanningHotSpotHeight)) {
            settings.setRestrictedAreaScanningEnabled(true);
            settings.setScanningHotSpotHeight(((Number)options.getSerializable(paramScanningHotSpotHeight)).floatValue());
        }
        
        if (options.containsKey(paramZoom)) {
            settings.setRelativeZoom(((Number)options.getSerializable(paramZoom)).floatValue());
        }
        return settings;
    }
    
    private static boolean bundleContainsStringKey(Bundle bundle, String key) {
        return (bundle.containsKey(key) && bundle.getString(key) != null);
    }
}
