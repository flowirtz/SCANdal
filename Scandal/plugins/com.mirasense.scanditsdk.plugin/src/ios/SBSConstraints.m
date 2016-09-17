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
#import "SBSConstraints.h"


/**
 * Constraints for the barcode picker consisting of margins, width and height. The getters
 * do not necessarily return the properties previously set but may return 0 for a margin if too
 * few margins were set or return null for width/height if too many margins were set.
 */
@implementation SBSConstraints

- (instancetype)init {
    if (self = [super init]) {
        
    }
    return self;
}

- (instancetype)initWithMargins:(CGRect)margins {
    if (self = [super init]) {
        self.leftMargin = [NSNumber numberWithInteger:margins.origin.x];
        self.topMargin = [NSNumber numberWithInteger:margins.origin.y];
        self.rightMargin = [NSNumber numberWithInteger:margins.size.width];
        self.bottomMargin = [NSNumber numberWithInteger:margins.size.height];
    }
    return self;
}

- (NSNumber *)leftMargin {
    if (!_leftMargin && (!_rightMargin || !_width)) return [NSNumber numberWithInteger:0];
    return _leftMargin;
}

- (NSNumber *)topMargin {
    if (!_topMargin && (!_bottomMargin || !_height)) return [NSNumber numberWithInteger:0];
    return _topMargin;
}

- (NSNumber *)rightMargin {
    if (!_rightMargin && !_width) return [NSNumber numberWithInteger:0];
    return _rightMargin;
}

- (NSNumber *)bottomMargin {
    if (!_bottomMargin && !_height) return [NSNumber numberWithInteger:0];
    return _bottomMargin;
}

- (NSNumber *)width {
    if (_leftMargin && _rightMargin) return nil;
    return _width;
}

- (NSNumber *)height {
    if (_topMargin && _bottomMargin) return nil;
    return _height;
}

@end
