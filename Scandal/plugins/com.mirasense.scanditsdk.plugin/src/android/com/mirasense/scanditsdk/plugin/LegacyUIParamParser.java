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

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;

import com.scandit.barcodepicker.BarcodePicker;
import com.scandit.barcodepicker.ScanOverlay;

/**
 * Created by mo on 14/12/15.
 */
public class LegacyUIParamParser {

    public static final String paramViewfinder = "viewfinder".toLowerCase();
    public static final String paramViewfinderSize = "viewfinderSize".toLowerCase();
    public static final String paramCameraSwitchVisibilityTablet = "tablet".toLowerCase();
    public static final String paramCameraSwitchVisibilityAlways = "always".toLowerCase();
    public static final String paramGuiStyleLaser = "laser".toLowerCase();


    public static void updatePickerUI(Activity activity, BarcodePicker picker, Bundle bundle) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();

        if (bundle.containsKey(UIParamParser.paramBeep)) {
            picker.getOverlayView().setBeepEnabled(bundle.getBoolean(UIParamParser.paramBeep));
        }
        if (bundle.containsKey(UIParamParser.paramVibrate)) {
            picker.getOverlayView().setVibrateEnabled(bundle.getBoolean(UIParamParser.paramVibrate));
        }
        if (bundle.containsKey(UIParamParser.paramTorch)) {
            picker.getOverlayView().setTorchEnabled(bundle.getBoolean(UIParamParser.paramTorch));
        }
        if (UIParamParser.bundleContainsStringKey(bundle, UIParamParser.paramTorchButtonPositionAndSize)) {
            String hotspot = bundle.getString(UIParamParser.paramTorchButtonPositionAndSize);
            String[] split = hotspot.split("[/]");
            if (split.length == 4) {
                try {
                    Float x = Float.valueOf(split[0]);
                    Float y = Float.valueOf(split[1]);
                    int width = Integer.valueOf(split[2]);
                    int height = Integer.valueOf(split[3]);
                    picker.getOverlayView().setTorchButtonMarginsAndSize(
                            (int) (x * screenWidth), (int) (y * screenHeight), width, height);
                } catch (NumberFormatException e) {}
            }
        }
        if (UIParamParser.bundleContainsStringKey(bundle, UIParamParser.paramTorchButtonMarginsAndSize)) {
            String hotspot = bundle.getString(UIParamParser.paramTorchButtonMarginsAndSize);
            String[] split = hotspot.split("[/]");
            if (split.length == 4) {
                try {
                    int x = Integer.valueOf(split[0]);
                    int y = Integer.valueOf(split[1]);
                    int width = Integer.valueOf(split[2]);
                    int height = Integer.valueOf(split[3]);
                    picker.getOverlayView().setTorchButtonMarginsAndSize(x, y, width, height);
                } catch (NumberFormatException e) {}
            }
        }

        if (UIParamParser.bundleContainsStringKey(bundle, UIParamParser.paramCameraSwitchVisibility)) {
            String visibility = bundle.getString(UIParamParser.paramCameraSwitchVisibility);
            int actualVisibility = ScanOverlay.CAMERA_SWITCH_NEVER;
            if (visibility.equals(paramCameraSwitchVisibilityTablet)) {
                actualVisibility = ScanOverlay.CAMERA_SWITCH_ON_TABLET;
            } else if (visibility.equals(paramCameraSwitchVisibilityAlways)) {
                actualVisibility = ScanOverlay.CAMERA_SWITCH_ALWAYS;
            }
            picker.getOverlayView().setCameraSwitchVisibility(actualVisibility);
        }
        if (UIParamParser.bundleContainsStringKey(bundle, UIParamParser.paramCameraSwitchButtonPositionAndSize)) {
            String hotspot = bundle.getString(UIParamParser.paramCameraSwitchButtonPositionAndSize);
            String[] split = hotspot.split("[/]");
            if (split.length == 4) {
                try {
                    Float x = Float.valueOf(split[0]);
                    Float y = Float.valueOf(split[1]);
                    int width = Integer.valueOf(split[2]);
                    int height = Integer.valueOf(split[3]);
                    picker.getOverlayView().setCameraSwitchButtonMarginsAndSize(
                            (int) (x * screenWidth), (int) (y * screenHeight), width, height);
                } catch (NumberFormatException e) {}
            }
        }
        if (UIParamParser.bundleContainsStringKey(bundle, UIParamParser.paramCameraSwitchButtonMarginsAndSize)) {
            String hotspot = bundle.getString(UIParamParser.paramCameraSwitchButtonMarginsAndSize);
            String[] split = hotspot.split("[/]");
            if (split.length == 4) {
                try {
                    int x = Integer.valueOf(split[0]);
                    int y = Integer.valueOf(split[1]);
                    int width = Integer.valueOf(split[2]);
                    int height = Integer.valueOf(split[3]);
                    picker.getOverlayView().setCameraSwitchButtonMarginsAndSize(x, y, width, height);
                } catch (NumberFormatException e) {}
            }
        }

        if (UIParamParser.bundleContainsStringKey(bundle, UIParamParser.paramViewfinderDimension)
                || UIParamParser.bundleContainsStringKey(bundle, paramViewfinderSize)) {
            String hotspot = "";
            if (bundle.containsKey(UIParamParser.paramViewfinderDimension)) {
                hotspot = bundle.getString(UIParamParser.paramViewfinderDimension);
            } else if (bundle.containsKey(paramViewfinderSize)) {
                hotspot = bundle.getString(paramViewfinderSize);
            }
            String[] split = hotspot.split("[/]");
            if (split.length == 2) {
                try {
                    Float width = Float.valueOf(split[0]);
                    Float height = Float.valueOf(split[1]);
                    picker.getOverlayView().setViewfinderDimension(width, height, width, height);
                } catch (NumberFormatException e) {}
            } else if (split.length == 4) {
                try {
                    Float width = Float.valueOf(split[0]);
                    Float height = Float.valueOf(split[1]);
                    Float landscapeWidth = Float.valueOf(split[2]);
                    Float landscapeHeight = Float.valueOf(split[3]);
                    picker.getOverlayView().setViewfinderDimension(
                            width, height, landscapeWidth, landscapeHeight);
                } catch (NumberFormatException e) {}
            }
        }

        if (UIParamParser.bundleContainsStringKey(bundle, UIParamParser.paramViewfinderColor)) {
            String color = bundle.getString(UIParamParser.paramViewfinderColor);
            if (color.length() == 6) {
                try {
                    String red = color.substring(0, 2);
                    String green = color.substring(2, 4);
                    String blue = color.substring(4, 6);
                    float r = ((float) Integer.parseInt(red, 16)) / 256.0f;
                    float g = ((float) Integer.parseInt(green, 16)) / 256.0f;
                    float b = ((float) Integer.parseInt(blue, 16)) / 256.0f;
                    picker.getOverlayView().setViewfinderColor(r, g, b);
                } catch (NumberFormatException e) {}
            }
        }
        if (UIParamParser.bundleContainsStringKey(bundle, UIParamParser.paramViewfinderDecodedColor)) {
            String color = bundle.getString(UIParamParser.paramViewfinderDecodedColor);
            if (color.length() == 6) {
                try {
                    String red = color.substring(0, 2);
                    String green = color.substring(2, 4);
                    String blue = color.substring(4, 6);
                    float r = ((float) Integer.parseInt(red, 16)) / 256.0f;
                    float g = ((float) Integer.parseInt(green, 16)) / 256.0f;
                    float b = ((float) Integer.parseInt(blue, 16)) / 256.0f;
                    picker.getOverlayView().setViewfinderDecodedColor(r, g, b);
                } catch (NumberFormatException e) {}
            }
        }
        if (bundle.containsKey(paramViewfinder)) {
            picker.getOverlayView().drawViewfinder(bundle.getBoolean(paramViewfinder));
        }

        if (UIParamParser.bundleContainsStringKey(bundle, UIParamParser.paramGuiStyle)) {
            String guiStyle = bundle.getString(UIParamParser.paramGuiStyle);
            if (guiStyle.equals(paramGuiStyleLaser)) {
                picker.getOverlayView().setGuiStyle(ScanOverlay.GUI_STYLE_LASER);
            } else {
                picker.getOverlayView().setGuiStyle(ScanOverlay.GUI_STYLE_DEFAULT);
            }
        }
    }
}
