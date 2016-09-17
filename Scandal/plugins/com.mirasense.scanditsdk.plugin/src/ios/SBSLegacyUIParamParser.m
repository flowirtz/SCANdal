//  Copyright 2016 Scandit AG
//
//  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
// in compliance with the License. You may obtain a copy of the License at
//
//  http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software distributed under the
//  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
//  express or implied. See the License for the specific language governing permissions and
//  limitations under the License.
#import "SBSLegacyUIParamParser.h"

#import "SBSPhonegapParamParser.h"
#import "SBSUIParamParser.h"


@implementation SBSLegacyUIParamParser

+ (NSString *)paramTorchButtonPositionAndSize { return [@"torchButtonPositionAndSize" lowercaseString]; }
+ (NSString *)paramCameraSwitchButtonPositionAndSize { return [@"cameraSwitchButtonPositionAndSize" lowercaseString]; }
+ (NSString *)paramViewfinderSize { return [@"viewfinderSize" lowercaseString]; }
+ (NSString *)paramCameraSwitchVisibilityTablet { return [@"tablet" lowercaseString]; }
+ (NSString *)paramCameraSwitchVisibilityAlways { return [@"always" lowercaseString]; }
+ (NSString *)paramGuiStyleLaser { return [@"laser" lowercaseString]; }


+ (void)updatePickerUI:(SBSBarcodePicker *)picker fromOptions:(NSDictionary *)options {
    
    NSObject *viewfinderSize = [options objectForKey:[self paramViewfinderSize]];
    if (viewfinderSize && [viewfinderSize isKindOfClass:[NSString class]]) {
        NSArray *split = [((NSString *) viewfinderSize) componentsSeparatedByString:@"/"];
        if ([split count] == 4) {
            float width = [[split objectAtIndex:0] floatValue];
            float height = [[split objectAtIndex:1] floatValue];
            float landscapeWidth = [[split objectAtIndex:2] floatValue];
            float landscapeHeight = [[split objectAtIndex:3] floatValue];
            [picker.overlayController setViewfinderHeight:height
                                                    width:width
                                          landscapeHeight:landscapeHeight
                                           landscapeWidth:landscapeWidth];
        }
    }
    
    NSObject *beep = [options objectForKey:[SBSUIParamParser paramBeep]];
    if (beep && [beep isKindOfClass:[NSNumber class]]) {
        [picker.overlayController setBeepEnabled:[((NSNumber *)beep) boolValue]];
    }
    NSObject *vibrate = [options objectForKey:[SBSUIParamParser paramVibrate]];
    if (vibrate && [vibrate isKindOfClass:[NSNumber class]]) {
        [picker.overlayController setVibrateEnabled:[((NSNumber *)vibrate) boolValue]];
    }
    
    NSObject *torch = [options objectForKey:[SBSUIParamParser paramTorch]];
    if (torch && [torch isKindOfClass:[NSNumber class]]) {
        [picker.overlayController setTorchEnabled:[((NSNumber *)torch) boolValue]];
    }
    NSObject *torchButtonPositionAndSize = [options objectForKey:
                                            [self paramTorchButtonPositionAndSize]];
    if (torchButtonPositionAndSize) {
        CGRect buttonRect = [self rectFromParameter:torchButtonPositionAndSize];
        CGSize screenSize = [UIScreen mainScreen].bounds.size;
        [picker.overlayController setTorchButtonLeftMargin:buttonRect.origin.x * screenSize.width
                                                 topMargin:buttonRect.origin.y * screenSize.height
                                                     width:buttonRect.size.width
                                                    height:buttonRect.size.height];
    }
    NSObject *torchButtonMarginsAndSize = [options objectForKey:
                                           [SBSUIParamParser paramTorchButtonMarginsAndSize]];
    if (torchButtonMarginsAndSize) {
        CGRect buttonRect = [self rectFromParameter:torchButtonMarginsAndSize];
        [picker.overlayController setTorchButtonLeftMargin:buttonRect.origin.x
                                                 topMargin:buttonRect.origin.y
                                                     width:buttonRect.size.width
                                                    height:buttonRect.size.height];
    }
    NSObject *cameraSwitchVisibility = [options objectForKey:[SBSUIParamParser
                                                              paramCameraSwitchVisibility]];
    if (cameraSwitchVisibility && [cameraSwitchVisibility isKindOfClass:[NSString class]]) {
        NSString *cameraSwitchVisibilityString = (NSString *)cameraSwitchVisibility;
        
        if ([cameraSwitchVisibilityString isEqualToString:
             [self paramCameraSwitchVisibilityTablet]]) {
            [picker.overlayController setCameraSwitchVisibility:SBSCameraSwitchVisibilityOnTablet];
            
        } else if ([cameraSwitchVisibilityString isEqualToString:
                    [self paramCameraSwitchVisibilityAlways]]) {
            [picker.overlayController setCameraSwitchVisibility:SBSCameraSwitchVisibilityAlways];
            
        } else {
            [picker.overlayController setCameraSwitchVisibility:SBSCameraSwitchVisibilityNever];
        }
    }
    NSObject *cameraSwitchButtonPositionAndSize = [options objectForKey:
                                                   [self paramCameraSwitchButtonPositionAndSize]];
    if (cameraSwitchButtonPositionAndSize) {
        CGRect buttonRect = [self rectFromParameter:cameraSwitchButtonPositionAndSize];
        CGSize screenSize = [UIScreen mainScreen].bounds.size;
        [picker.overlayController setCameraSwitchButtonRightMargin:buttonRect.origin.x * screenSize.width
                                                         topMargin:buttonRect.origin.y * screenSize.height
                                                             width:buttonRect.size.width
                                                            height:buttonRect.size.height];
    }
    NSObject *cameraSwitchButtonMarginsAndSize = [options objectForKey:
                                                  [SBSUIParamParser paramCameraSwitchButtonMarginsAndSize]];
    if (cameraSwitchButtonMarginsAndSize) {
        CGRect buttonRect = [self rectFromParameter:cameraSwitchButtonMarginsAndSize];
        [picker.overlayController setCameraSwitchButtonRightMargin:buttonRect.origin.x
                                                         topMargin:buttonRect.origin.y
                                                             width:buttonRect.size.width
                                                            height:buttonRect.size.height];
    }
    
    NSObject *guiStyle = [options objectForKey:[SBSUIParamParser paramGuiStyle]];
    if (guiStyle && [guiStyle isKindOfClass:[NSString class]]) {
        NSString *guiStyleString = (NSString *)guiStyle;
        if ([guiStyleString isEqualToString:[self paramGuiStyleLaser]]) {
            picker.overlayController.guiStyle = SBSGuiStyleLaser;
        } else {
            picker.overlayController.guiStyle = SBSGuiStyleDefault;
        }
    }
    
    NSObject *color1 = [options objectForKey:[SBSUIParamParser paramViewfinderColor]];
    if (color1 && [color1 isKindOfClass:[NSString class]]) {
        NSString *color1String = (NSString *)color1;
        if ([color1String length] == 6) {
            unsigned int redInt;
            NSScanner *redScanner = [NSScanner scannerWithString:[color1String substringToIndex:2]];
            [redScanner scanHexInt:&redInt];
            float red = ((float) redInt) / 256.0;
            
            unsigned int greenInt;
            NSScanner *greenScanner = [NSScanner scannerWithString:[[color1String substringFromIndex:2] substringToIndex:2]];
            [greenScanner scanHexInt:&greenInt];
            float green = ((float) greenInt) / 256.0;
            
            unsigned int blueInt;
            NSScanner *blueScanner = [NSScanner scannerWithString:[color1String substringFromIndex:4]];
            [blueScanner scanHexInt:&blueInt];
            float blue = ((float) blueInt) / 256.0;
            
            [picker.overlayController setViewfinderColor:red green:green blue:blue];
        }
    }
    NSObject *color2 = [options objectForKey:[SBSUIParamParser paramViewfinderDecodedColor]];
    if (color2 && [color2 isKindOfClass:[NSString class]]) {
        NSString *color2String = (NSString *)color2;
        if ([color2String length] == 6) {
            unsigned int redInt;
            NSScanner *redScanner = [NSScanner scannerWithString:[color2String substringToIndex:2]];
            [redScanner scanHexInt:&redInt];
            float red = ((float) redInt) / 256.0;
            
            unsigned int greenInt;
            NSScanner *greenScanner = [NSScanner scannerWithString:[[color2String substringFromIndex:2] substringToIndex:2]];
            [greenScanner scanHexInt:&greenInt];
            float green = ((float) greenInt) / 256.0;
            
            unsigned int blueInt;
            NSScanner *blueScanner = [NSScanner scannerWithString:[color2String substringFromIndex:4]];
            [blueScanner scanHexInt:&blueInt];
            float blue = ((float) blueInt) / 256.0;
            
            [picker.overlayController setViewfinderDecodedColor:red green:green blue:blue];
        }
    }
    
    if (![options objectForKey:[SBSPhonegapParamParser paramPortraitMargins]]
            && ![options objectForKey:[SBSPhonegapParamParser paramLandscapeMargins]]
            && ![options objectForKey:[SBSPhonegapParamParser paramPortraitConstraints]]
            && ![options objectForKey:[SBSPhonegapParamParser paramLandscapeConstraints]]) {
        // Show the toolbar that contains a cancel button.
        [picker.overlayController showToolBar:YES];
    }
    
    NSObject *t8 = [options objectForKey:[SBSUIParamParser paramToolBarButtonCaption]];
    if (t8 && [t8 isKindOfClass:[NSString class]]) {
        [picker.overlayController setToolBarButtonCaption:((NSString *) t8)];
    }
}

+ (CGRect)rectFromParameter:(NSObject *)parameter {
    if (parameter && [parameter isKindOfClass:[NSString class]]) {
        NSArray *split = [((NSString *) parameter) componentsSeparatedByString:@"/"];
        if ([split count] == 4) {
            return CGRectMake([[split objectAtIndex:0] floatValue],
                              [[split objectAtIndex:1] floatValue],
                              [[split objectAtIndex:2] floatValue],
                              [[split objectAtIndex:3] floatValue]);
        }
    }
    return CGRectZero;
}

@end
