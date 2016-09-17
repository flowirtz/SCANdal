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
#import <Foundation/Foundation.h>

#import <ScanditBarcodeScanner/ScanditBarcodeScanner.h>


/**
 * UI parameter parser for the new API in 4.11 and above. The difference to the legacy parameter
 * parser is that it moves away from strings as much as possible providing a cleaner api.
 */
@interface SBSUIParamParser : NSObject

+ (NSString *)paramBeep;
+ (NSString *)paramVibrate;
+ (NSString *)paramTorch;
+ (NSString *)paramTorchButtonMarginsAndSize;
+ (NSString *)paramCameraSwitchVisibility;
+ (NSString *)paramCameraSwitchButtonMarginsAndSize;
+ (NSString *)paramToolBarButtonCaption;

+ (NSString *)paramViewfinderColor;
+ (NSString *)paramViewfinderDecodedColor;
+ (NSString *)paramZoom;

+ (NSString *)paramGuiStyle;

+ (void)updatePickerUI:(SBSBarcodePicker *)picker fromOptions:(NSDictionary *)options;

+ (BOOL)array:(NSArray *)array onlyContainObjectsOfClass:(Class)aClass;

+ (NSNumber *)getSizeOrNull:(NSObject *)obj relativeTo:(int)max;
+ (float)getSize: (NSObject *)obj relativeTo:(int)max;

@end
