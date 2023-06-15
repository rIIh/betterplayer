package com.jhomlala.better_player

import android.app.Activity
import android.app.PendingIntent
import android.app.RemoteAction
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi

/**
 * A [BroadcastReceiver] for handling action items on the picture-in-picture mode.
 */
class PIPActionsReceiver(private val plugin: BetterPlayerPlugin) : BroadcastReceiver() {
    // Called when an item is clicked.
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || intent.action != ACTION_STOPWATCH_CONTROL) return

        val textureId: Long = intent.getLongExtra(EXTRA_TEXTURE_ID, -1)
        if (textureId < 0) return

        val player = plugin.getPlayer(textureId) ?: return
        when (intent.getIntExtra(EXTRA_CONTROL_TYPE, 0)) {
            CONTROL_TYPE_START_OR_PAUSE -> if (player.isPlaying.value) player.pause() else player.play()
            CONTROL_TYPE_FORWARD -> player.seekTo((player.position + 15000).toInt())
            CONTROL_TYPE_BACKWARD -> player.seekTo((player.position - 15000).toInt())
        }
    }

    companion object {
        /** Intent action for stopwatch controls from Picture-in-Picture mode.  */
        private const val ACTION_STOPWATCH_CONTROL = "stopwatch_control"

        /** Intent extra for stopwatch controls from Picture-in-Picture mode.  */
        private const val EXTRA_CONTROL_TYPE = "control_type"
        private const val EXTRA_TEXTURE_ID = "texture_id"
        internal const val CONTROL_TYPE_START_OR_PAUSE = 1
        internal const val CONTROL_TYPE_BACKWARD = 2
        internal const val CONTROL_TYPE_FORWARD = 3

        internal const val REQUEST_START_OR_PAUSE = 101
        internal const val REQUEST_BACKWARD = 102
        internal const val REQUEST_FORWARD = 103

        internal val INTENT_FILTER = IntentFilter(ACTION_STOPWATCH_CONTROL)

        @RequiresApi(Build.VERSION_CODES.O)
        fun createRemoteAction(
            activity: Activity, @DrawableRes iconResId: Int,
            title: String,
            requestCode: Int,
            controlType: Int,
            textureId: Long
        ): RemoteAction {
            activity.let {
                return RemoteAction(
                    Icon.createWithResource(it, iconResId),
                    title,
                    title,
                    PendingIntent.getBroadcast(
                        it,
                        requestCode,
                        Intent(ACTION_STOPWATCH_CONTROL)
                            .putExtra(EXTRA_CONTROL_TYPE, controlType)
                            .putExtra(EXTRA_TEXTURE_ID, textureId),
                        PendingIntent.FLAG_IMMUTABLE
                    )
                )
            }
        }
    }
}