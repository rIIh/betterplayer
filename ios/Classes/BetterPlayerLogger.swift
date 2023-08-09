//
//  BetterPlayerLogger.swift
//  better_player
//
//  Created by Vadim Melnikov on 09.08.2023.
//

import Foundation

@objc public class BetterPlayerLogger: NSObject {
    @objc public static var isEnabled = true
    
    @objc public static func log(_ message: String) {
        if (!isEnabled) { return }
        
        NSLog("[BetterPlayer]: " + message);
    }
    
    @objc public static func log(_ message: String, force forceLog: Bool = false) {
        if (!isEnabled && !forceLog) { return }
        
        NSLog("[BetterPlayer]: " + message);
    }
}
