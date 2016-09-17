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
#import "SBSLegacySettingsParamParser.h"

@implementation SBSLegacySettingsParamParser

+ (NSString *)paramPreferFrontCamera { return [@"preferFrontCamera" lowercaseString]; }

+ (NSString *)param1DScanning { return [@"1DScanning" lowercaseString]; }
+ (NSString *)param2DScanning { return [@"2DScanning" lowercaseString]; }
+ (NSString *)paramEan13AndUpc12 { return [@"ean13AndUpc12" lowercaseString]; }
+ (NSString *)paramEan8 { return [@"ean8" lowercaseString]; }
+ (NSString *)paramUpce { return [@"upce" lowercaseString]; }
+ (NSString *)paramCode39 { return [@"code39" lowercaseString]; }
+ (NSString *)paramCode93 { return [@"code93" lowercaseString]; }
+ (NSString *)paramCode128 { return [@"code128" lowercaseString]; }
+ (NSString *)paramItf { return [@"itf" lowercaseString]; }
+ (NSString *)paramGS1Databar { return [@"gs1Databar" lowercaseString]; }
+ (NSString *)paramGS1DatabarExpanded { return [@"gs1DatabarExpanded" lowercaseString]; }
+ (NSString *)paramCodabar { return [@"codabar" lowercaseString]; }
+ (NSString *)paramQR { return [@"qr" lowercaseString]; }
+ (NSString *)paramDatamatrix { return [@"datamatrix" lowercaseString]; }
+ (NSString *)paramPdf417 { return [@"pdf417" lowercaseString]; }
+ (NSString *)paramAztec { return [@"aztec" lowercaseString]; }
+ (NSString *)paramMsiPlessey { return [@"msiPlessey" lowercaseString]; }
+ (NSString *)paramCode11 { return [@"code11" lowercaseString]; }
+ (NSString *)paramMaxiCode { return [@"maxicode" lowercaseString]; }

+ (NSString *)paramMsiPlesseyChecksumType { return [@"msiPlesseyChecksumType" lowercaseString]; }
+ (NSString *)paramMsiPlesseyChecksumTypeNone { return [@"none" lowercaseString]; }
+ (NSString *)paramMsiPlesseyChecksumTypeMod11 { return [@"mod11" lowercaseString]; }
+ (NSString *)paramMsiPlesseyChecksumTypeMod1010 { return [@"mod1010" lowercaseString]; }
+ (NSString *)paramMsiPlesseyChecksumTypeMod1110 { return [@"mod1110" lowercaseString]; }

+ (NSString *)paramInverseRecognition { return [@"inverseRecognition" lowercaseString]; }
+ (NSString *)paramDataMatrixInverseRecognition { return [@"dataMatrixInverseRecognition" lowercaseString]; }
+ (NSString *)paramQRInverseRecognition { return [@"qrInverseRecognition" lowercaseString]; }
+ (NSString *)paramMicroDataMatrix { return [@"microDataMatrix" lowercaseString]; }
+ (NSString *)paramForce2D { return [@"force2d" lowercaseString]; }
+ (NSString *)paramCodeDuplicateFilter { return [@"codeDuplicateFilter" lowercaseString]; }
+ (NSString *)paramScanningHotSpot { return [@"scanningHotSpot" lowercaseString]; }
+ (NSString *)paramScanningHotSpotHeight { return [@"scanningHotSpotHeight" lowercaseString]; }

+ (NSString *)paramDeviceName { return [@"deviceName" lowercaseString]; }


+ (SBSScanSettings *)settingsForOptions:(NSDictionary *)options {
    SBSScanSettings *settings = [SBSScanSettings pre47DefaultSettings];
    
    SBSCameraFacingDirection facing = SBSCameraFacingDirectionBack;
    NSObject *preferFrontCamera = [options objectForKey:[self paramPreferFrontCamera]];
    if (preferFrontCamera && [preferFrontCamera isKindOfClass:[NSNumber class]]) {
        if ([((NSNumber *)preferFrontCamera) boolValue]) {
            facing = SBSCameraFacingDirectionFront;
        }
    }
    
    NSObject *deviceName = [options objectForKey:[self paramDeviceName]];
    if (deviceName && [deviceName isKindOfClass:[NSString class]]) {
        [settings setDeviceName:(NSString *)deviceName];
    }
    
    NSObject *scanning1D = [options objectForKey:[self param1DScanning]];
    if (scanning1D && [scanning1D isKindOfClass:[NSNumber class]]) {
        NSLog(@"The parameter '1DScanning' is deprecated. Please enable symbologies individually instead");
        [settings enableSymbologies:[NSSet setWithObjects:
                                     [NSNumber numberWithInt:SBSSymbologyEAN13],
                                     [NSNumber numberWithInt:SBSSymbologyUPC12],
                                     [NSNumber numberWithInt:SBSSymbologyEAN8],
                                     [NSNumber numberWithInt:SBSSymbologyCode128],
                                     [NSNumber numberWithInt:SBSSymbologyCode39],
                                     [NSNumber numberWithInt:SBSSymbologyCode93],
                                     [NSNumber numberWithInt:SBSSymbologyITF],
                                     [NSNumber numberWithInt:SBSSymbologyMSIPlessey],
                                     [NSNumber numberWithInt:SBSSymbologyUPCE],
                                     [NSNumber numberWithInt:SBSSymbologyCodabar],
                                     [NSNumber numberWithInt:SBSSymbologyGS1Databar],
                                     [NSNumber numberWithInt:SBSSymbologyGS1DatabarExpanded], nil]];
    }
    NSObject *scanning2D = [options objectForKey:[self param2DScanning]];
    if (scanning2D && [scanning2D isKindOfClass:[NSNumber class]]) {
        NSLog(@"The parameter '2DScanning' is deprecated. Please enable symbologies individually instead");
        [settings enableSymbologies:[NSSet setWithObjects:
                                     [NSNumber numberWithInt:SBSSymbologyAztec],
                                     [NSNumber numberWithInt:SBSSymbologyDatamatrix],
                                     [NSNumber numberWithInt:SBSSymbologyPDF417],
                                     [NSNumber numberWithInt:SBSSymbologyQR], nil]];
    }
    
    NSObject *ean13AndUpc12 = [options objectForKey:[self paramEan13AndUpc12]];
    if (ean13AndUpc12 && [ean13AndUpc12 isKindOfClass:[NSNumber class]]) {
        [settings setSymbology:SBSSymbologyEAN13 enabled:[((NSNumber *)ean13AndUpc12) boolValue]];
        [settings setSymbology:SBSSymbologyUPC12 enabled:[((NSNumber *)ean13AndUpc12) boolValue]];
    }
    NSObject *ean8 = [options objectForKey:[self paramEan8]];
    if (ean8 && [ean8 isKindOfClass:[NSNumber class]]) {
        [settings setSymbology:SBSSymbologyEAN8 enabled:[((NSNumber *)ean8) boolValue]];
    }
    NSObject *upce = [options objectForKey:[self paramUpce]];
    if (upce && [upce isKindOfClass:[NSNumber class]]) {
        [settings setSymbology:SBSSymbologyUPCE enabled:[((NSNumber *)upce) boolValue]];
    }
    NSObject *code39 = [options objectForKey:[self paramCode39]];
    if (code39 && [code39 isKindOfClass:[NSNumber class]]) {
        [settings setSymbology:SBSSymbologyCode39 enabled:[((NSNumber *)code39) boolValue]];
    }
    NSObject *code93 = [options objectForKey:[self paramCode93]];
    if (code93 && [code93 isKindOfClass:[NSNumber class]]) {
        [settings setSymbology:SBSSymbologyCode93 enabled:[((NSNumber *)code93) boolValue]];
    }
    NSObject *code128 = [options objectForKey:[self paramCode128]];
    if (code128 && [code128 isKindOfClass:[NSNumber class]]) {
        [settings setSymbology:SBSSymbologyCode128 enabled:[((NSNumber *)code128) boolValue]];
    }
    NSObject *itf = [options objectForKey:[self paramItf]];
    if (itf && [itf isKindOfClass:[NSNumber class]]) {
        [settings setSymbology:SBSSymbologyITF enabled:[((NSNumber *)itf) boolValue]];
    }
    NSObject *gs1DataBar = [options objectForKey:[self paramGS1Databar]];
    if (gs1DataBar && [gs1DataBar isKindOfClass:[NSNumber class]]) {
        [settings setSymbology:SBSSymbologyGS1Databar enabled:[((NSNumber *)gs1DataBar) boolValue]];
    }
    NSObject *gs1DataBarExpanded = [options objectForKey:[self paramGS1DatabarExpanded]];
    if (gs1DataBarExpanded && [gs1DataBarExpanded isKindOfClass:[NSNumber class]]) {
        [settings setSymbology:SBSSymbologyGS1DatabarExpanded enabled:[((NSNumber *)gs1DataBarExpanded) boolValue]];
    }
    NSObject *codabar = [options objectForKey:[self paramCodabar]];
    if (codabar && [codabar isKindOfClass:[NSNumber class]]) {
        [settings setSymbology:SBSSymbologyCodabar enabled:[((NSNumber *)codabar) boolValue]];
    }
    NSObject *code11 = [options objectForKey:[self paramCode11]];
    if (code11 && [code11 isKindOfClass:[NSNumber class]]) {
        [settings setSymbology:SBSSymbologyCode11 enabled:[((NSNumber *)code11) boolValue]];
    }
    NSObject *qr = [options objectForKey:[self paramQR]];
    if (qr && [qr isKindOfClass:[NSNumber class]]) {
        [settings setSymbology:SBSSymbologyQR enabled:[((NSNumber *)qr) boolValue]];
    }
    NSObject *maxiCode = [options objectForKey:[self paramMaxiCode]];
    if (maxiCode && [maxiCode isKindOfClass:[NSNumber class]]) {
        [settings setSymbology:SBSSymbologyMaxiCode enabled:[((NSNumber *)maxiCode) boolValue]];
    }
    NSObject *dataMatrix = [options objectForKey:[self paramDatamatrix]];
    if (dataMatrix && [dataMatrix isKindOfClass:[NSNumber class]]) {
        [settings setSymbology:SBSSymbologyDatamatrix enabled:[((NSNumber *)dataMatrix) boolValue]];
    }
    NSObject *pdf417 = [options objectForKey:[self paramPdf417]];
    if (pdf417 && [pdf417 isKindOfClass:[NSNumber class]]) {
        [settings setSymbology:SBSSymbologyPDF417 enabled:[((NSNumber *)pdf417) boolValue]];
    }
    NSObject *aztec = [options objectForKey:[self paramAztec]];
    if (aztec && [aztec isKindOfClass:[NSNumber class]]) {
        [settings setSymbology:SBSSymbologyAztec enabled:[((NSNumber *)aztec) boolValue]];
    }
    NSObject *msiPlessey = [options objectForKey:[self paramMsiPlessey]];
    if (msiPlessey && [msiPlessey isKindOfClass:[NSNumber class]]) {
        [settings setSymbology:SBSSymbologyMSIPlessey enabled:[((NSNumber *)msiPlessey) boolValue]];
    }
    
    NSObject *msiPlesseyChecksum = [options objectForKey:[self paramMsiPlesseyChecksumType]];
    if (msiPlesseyChecksum && [msiPlesseyChecksum isKindOfClass:[NSString class]]) {
        NSString *msiPlesseyChecksumString = (NSString *)msiPlesseyChecksum;
        NSMutableSet *msiChecksums = [NSMutableSet set];
        if ([msiPlesseyChecksumString isEqualToString:[self paramMsiPlesseyChecksumTypeNone]]) {
            [msiChecksums addObject:[NSNumber numberWithInt:SBSChecksumNone]];
        } else if ([msiPlesseyChecksumString isEqualToString:[self paramMsiPlesseyChecksumTypeMod11]]) {
            [msiChecksums addObject:[NSNumber numberWithInt:SBSChecksumMod11]];
        } else if ([msiPlesseyChecksumString isEqualToString:[self paramMsiPlesseyChecksumTypeMod1010]]) {
            [msiChecksums addObject:[NSNumber numberWithInt:SBSChecksumMod1010]];
        } else if ([msiPlesseyChecksumString isEqualToString:[self paramMsiPlesseyChecksumTypeMod1110]]) {
            [msiChecksums addObject:[NSNumber numberWithInt:SBSChecksumMod1110]];
        } else {
            [msiChecksums addObject:[NSNumber numberWithInt:SBSChecksumMod10]];
        }
        [settings settingsForSymbology:SBSSymbologyMSIPlessey].checksums = msiChecksums;
    }
    
    
    NSObject *inverseRecognition = [options objectForKey:[self paramInverseRecognition]];
    if (inverseRecognition && [inverseRecognition isKindOfClass:[NSNumber class]]) {
        SBSSymbologySettings *dataMatrixSettings = [settings settingsForSymbology:SBSSymbologyDatamatrix];
        dataMatrixSettings.colorInvertedEnabled = [((NSNumber *)inverseRecognition) boolValue];
        SBSSymbologySettings *qrSettings = [settings settingsForSymbology:SBSSymbologyQR];
        qrSettings.colorInvertedEnabled = [((NSNumber *)inverseRecognition) boolValue];
    }
    
    NSObject *dataMatrixinverseRecognition = [options objectForKey:[self paramDataMatrixInverseRecognition]];
    if (dataMatrixinverseRecognition && [dataMatrixinverseRecognition isKindOfClass:[NSNumber class]]) {
        SBSSymbologySettings *dataMatrixSettings = [settings settingsForSymbology:SBSSymbologyDatamatrix];
        dataMatrixSettings.colorInvertedEnabled = [((NSNumber *)dataMatrixinverseRecognition) boolValue];
    }
    
    NSObject *qrInverseRecognition = [options objectForKey:[self paramQRInverseRecognition]];
    if (qrInverseRecognition && [qrInverseRecognition isKindOfClass:[NSNumber class]]) {
        SBSSymbologySettings *qrSettings = [settings settingsForSymbology:SBSSymbologyQR];
        qrSettings.colorInvertedEnabled = [((NSNumber *)qrInverseRecognition) boolValue];
    }
    NSObject *microDataMatrix = [options objectForKey:[self paramMicroDataMatrix]];
    if (microDataMatrix && [microDataMatrix isKindOfClass:[NSNumber class]]) {
        SBSSymbologySettings *dataMatrixSettings = [settings settingsForSymbology:SBSSymbologyDatamatrix];
        [dataMatrixSettings setExtension:SBSSymbologySettingsExtensionTiny
                                 enabled:[((NSNumber *)microDataMatrix) boolValue]];
    }
    NSObject *force2d = [options objectForKey:[self paramForce2D]];
    if (force2d && [force2d isKindOfClass:[NSNumber class]]) {
        settings.force2dRecognition = [((NSNumber *)force2d) boolValue];
    }
    
    NSObject *codeDuplicateFilter = [options objectForKey:[self paramCodeDuplicateFilter]];
    if (codeDuplicateFilter && [codeDuplicateFilter isKindOfClass:[NSNumber class]]) {
        settings.codeDuplicateFilter = [((NSNumber *)codeDuplicateFilter) floatValue];
    }
    
    NSObject *scanningHotspot = [options objectForKey:[self paramScanningHotSpot]];
    if (scanningHotspot && [scanningHotspot isKindOfClass:[NSString class]]) {
        NSArray *split = [((NSString *) scanningHotspot) componentsSeparatedByString:@"/"];
        if ([split count] == 2) {
            float x = [[split objectAtIndex:0] floatValue];
            float y = [[split objectAtIndex:1] floatValue];
            settings.scanningHotSpot = CGPointMake(x, y);
        }
    }
    NSObject *scanningHotspotHeight = [options objectForKey:[self paramScanningHotSpotHeight]];
    if (scanningHotspotHeight && [scanningHotspotHeight isKindOfClass:[NSNumber class]]) {
        float height = [((NSNumber *)scanningHotspotHeight) floatValue];
        CGRect activeScanArea = CGRectMake(0.0f, settings.scanningHotSpot.y - height * 0.5f, 1.0f, height);
        [settings setActiveScanningArea:activeScanArea];
    }
    
    return settings;
}

@end
