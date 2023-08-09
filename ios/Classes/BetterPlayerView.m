// Copyright 2017 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

#import "BetterPlayer.h"
#import "BetterPlayerView.h"
#import <better_player/better_player-Swift.h>

// BetterPlayerView.m
@implementation BetterPlayerView
bool _observerAdded = false;

- (void)observe {
    _observerAdded = true;
    [self.playerLayer addObserver:self forKeyPath:@"readyForDisplay" options:NSKeyValueObservingOptionInitial | NSKeyValueObservingOptionNew context:NULL];
}

- (AVPlayer *)player {
    return self.playerLayer.player;
}

- (void)setPlayer:(AVPlayer *)player {
    self.playerLayer.player = player;
}

// Override UIView method
+ (Class)layerClass {
    return [AVPlayerLayer class];
}

- (AVPlayerLayer *)playerLayer {
    return (AVPlayerLayer *)self.layer;
}

- (void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary<NSKeyValueChangeKey,id> *)change context:(void *)context {
    if ([keyPath isEqualToString:@"readyForDisplay"]) {
        [BetterPlayerLogger log:[NSString stringWithFormat:@"player view layer is ready for display - %o", self.playerLayer.isReadyForDisplay]];
        if (self.playerLayer.isReadyForDisplay) {
            [BetterPlayerLogger log:[NSString stringWithFormat:@"player view layer frame - %@", NSStringFromCGRect(self.playerLayer.frame)]];
            [self.superview layoutSubviews];
        }
    }
}

-(void)removeFromSuperview {
    if (_observerAdded) {
        [self.playerLayer removeObserver:self forKeyPath:@"readyForDisplay"];
        _observerAdded = false;
    }
    
    [super removeFromSuperview];
}
@end
