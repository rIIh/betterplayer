package com.jhomlala.better_player

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

/**
 * A [BroadcastReceiver] for handling system buttons to notify flutter end about pip start.
 */
class HomeButtonReceiver(private val plugin: BetterPlayerPlugin) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || intent.action != Intent.ACTION_CLOSE_SYSTEM_DIALOGS) return
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S || !BetterPlayerPlugin.USE_AUTO_PIP_MODE) return
        if (plugin.isInPictureInPictureMode) return

        val reason = intent.getStringExtra("reason")

        Log.v(TAG, "System button intent received: $reason")

        when (reason) {
            "homekey" -> plugin.getFirstExistingPlayer { it.isPlaying.value && plugin.pipPrimary == it }
                ?.onPictureInPictureStatusChanged(true)
        }
    }

    companion object {
        private const val TAG = "HomeButtonReceiver";
    }
}