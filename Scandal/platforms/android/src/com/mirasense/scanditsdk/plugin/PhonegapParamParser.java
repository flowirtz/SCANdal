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
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

/**
 * Created by mo on 14/12/15.
 */
public class PhonegapParamParser {

    public static final String paramSearchBar = "searchBar".toLowerCase();
    public static final String paramSearchBarPlaceholderText = "searchBarPlaceholderText".toLowerCase();
    public static final String paramMinSearchBarBarcodeLength = "minSearchBarBarcodeLength".toLowerCase();
    public static final String paramMaxSearchBarBarcodeLength = "maxSearchBarBarcodeLength".toLowerCase();

    public static final String paramPortraitMargins = "portraitMargins".toLowerCase();
    public static final String paramLandscapeMargins = "landscapeMargins".toLowerCase();
    public static final String paramPortraitConstraints = "portraitConstraints".toLowerCase();
    public static final String paramLandscapeConstraints = "landscapeConstraints".toLowerCase();
    public static final String paramAnimationDuration = "animationDuration".toLowerCase();

    public static final String paramMarginLeft = "leftMargin".toLowerCase();
    public static final String paramMarginTop = "topMargin".toLowerCase();
    public static final String paramMarginRight = "rightMargin".toLowerCase();
    public static final String paramMarginBottom = "bottomMargin".toLowerCase();
    public static final String paramWidth = "width".toLowerCase();
    public static final String paramHeight = "height".toLowerCase();
    
    public static final String paramContinuousMode = "continuousMode".toLowerCase();
    public static final String paramPaused = "paused".toLowerCase();


    public static void updatePicker(BarcodePickerWithSearchBar picker, Bundle bundle,
                                    BarcodePickerWithSearchBar.SearchBarListener listener) {
        
        if (picker == null || bundle == null) {
            return;
        }
        
        if (bundle.containsKey(paramSearchBar)) {
            picker.showSearchBar(bundle.getBoolean(paramSearchBar));
            picker.setOnSearchBarListener(listener);
        }

        if (bundle.containsKey(paramSearchBarPlaceholderText)) {
            picker.setSearchBarPlaceholderText(bundle.getString(paramSearchBarPlaceholderText));
        }

        if (bundle.containsKey(paramMinSearchBarBarcodeLength)) {

        }

        if (bundle.containsKey(paramMaxSearchBarBarcodeLength)) {

        }
    }

    public static void updateLayout(final Activity activity, final BarcodePickerWithSearchBar picker,
                                    Bundle bundle, Point screenDimensions) {
        if (picker == null || bundle == null) {
            return;
        }
        
        double animationDuration = 0;
        if (bundle.containsKey(paramAnimationDuration)) {
            Object duration = bundle.get(paramAnimationDuration);
            if (duration instanceof Number) {
                animationDuration = ((Number) duration).doubleValue();
            }
        }
        
        if (bundle.containsKey(paramPortraitMargins) ||
            bundle.containsKey(paramLandscapeMargins)) {
            adjustWithMargins(activity, picker, bundle, screenDimensions, animationDuration);
            return;
        }
        if (bundle.containsKey(paramPortraitConstraints) ||
            bundle.containsKey(paramLandscapeConstraints)) {
            adjustWithConstraints(activity, picker, bundle, screenDimensions, animationDuration);
        }
    }

    private static void adjustWithConstraints(Activity activity, BarcodePickerWithSearchBar picker,
                                              Bundle bundle, Point screenDimensions,
                                              double animationDuration) {
        BarcodePickerWithSearchBar.Constraints portraitConstraints = new BarcodePickerWithSearchBar.Constraints();
        BarcodePickerWithSearchBar.Constraints landscapeConstraints = new BarcodePickerWithSearchBar.Constraints();

        if (bundle.containsKey(paramPortraitConstraints)) {
            portraitConstraints = extractConstraints(bundle, paramPortraitConstraints,
                    screenDimensions.x, screenDimensions.y);
        }

        if (bundle.containsKey(paramLandscapeConstraints)) {
            landscapeConstraints = extractConstraints(bundle, paramLandscapeConstraints,
                    screenDimensions.y, screenDimensions.x);
        }

        picker.adjustSize(activity, portraitConstraints, landscapeConstraints, animationDuration);
    }

    private static void adjustWithMargins(Activity activity, BarcodePickerWithSearchBar picker, Bundle bundle, Point screenDimensions, double animationDuration) {
        Rect portraitMargins = null;
        Rect landscapeMargins = null;

        if (bundle.containsKey(paramPortraitMargins)) {
            portraitMargins = extractMarginsRect(bundle, paramPortraitMargins,
                    screenDimensions.x, screenDimensions.y);
        }

        if (bundle.containsKey(paramLandscapeMargins)) {
            portraitMargins = extractMarginsRect(bundle, paramLandscapeMargins,
                    screenDimensions.y, screenDimensions.x);
        }

        BarcodePickerWithSearchBar.Constraints portraitConstraints =
        new BarcodePickerWithSearchBar.Constraints(portraitMargins);
        BarcodePickerWithSearchBar.Constraints landscapeConstraints =
        new BarcodePickerWithSearchBar.Constraints(landscapeMargins);
        picker.adjustSize(activity, portraitConstraints, landscapeConstraints, animationDuration);
    }

    private static Rect extractMarginsRect(Bundle bundle, String key, int width, int height) {
        Rect result = null;
        if (bundle.getSerializable(key) != null
            && bundle.getSerializable(key) instanceof List) {
            List<Object> list = (List<Object>) bundle.getSerializable(key);
            if (list.size() == 4 &&
                (UIParamParser.checkClassOfListObjects(list, Integer.class) ||
                 UIParamParser.checkClassOfListObjects(list, String.class))) {
                    result = new Rect(
                                      UIParamParser.getSize(list.get(0), width),
                                      UIParamParser.getSize(list.get(1), height),
                                      UIParamParser.getSize(list.get(2), width),
                                      UIParamParser.getSize(list.get(3), height));
                }
        } else if (bundle.getString(key) != null) {
            String portraitMarginsString = bundle.getString(key);
            String[] split = portraitMarginsString.split("[/]");
            if (split.length == 4) {
                try {
                    result = new Rect(
                                      UIParamParser.getSize(split[0], width),
                                      UIParamParser.getSize(split[1], height),
                                      UIParamParser.getSize(split[2], width),
                                      UIParamParser.getSize(split[3], height));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.e("ScanditSDK", "Failed to parse '" + key + "' - wrong type.");
        }
        return result;
    }

    private static BarcodePickerWithSearchBar.Constraints extractConstraints(Bundle bundle, String key,
                                                                         int width, int height) {
        BarcodePickerWithSearchBar.Constraints result = new BarcodePickerWithSearchBar.Constraints();
        Bundle constraintsBundle = bundle.getBundle(key);
        if (constraintsBundle != null) {
            result.setLeftMargin(UIParamParser.getSize(constraintsBundle, paramMarginLeft, width));
            result.setTopMargin(UIParamParser.getSize(constraintsBundle, paramMarginTop, height));
            result.setRightMargin(UIParamParser.getSize(constraintsBundle, paramMarginRight, width));
            result.setBottomMargin(UIParamParser.getSize(constraintsBundle, paramMarginBottom, height));
            result.setWidth(UIParamParser.getSize(constraintsBundle, paramWidth, width));
            result.setHeight(UIParamParser.getSize(constraintsBundle, paramHeight, height));
        } else {
            Log.e("ScanditSDK", "Failed to parse '" + key + "' - wrong type.");
        }
        return result;
    }

    public static boolean shouldRunInContinuousMode(Bundle options) {
        return options != null &&
                options.containsKey(PhonegapParamParser.paramContinuousMode) &&
                options.getBoolean(PhonegapParamParser.paramContinuousMode)
                ;
    }

    public static boolean shouldStartInPausedState(Bundle options) {
        return options != null && options.containsKey(PhonegapParamParser.paramPaused) &&
                options.getBoolean(PhonegapParamParser.paramPaused);
    }
}
