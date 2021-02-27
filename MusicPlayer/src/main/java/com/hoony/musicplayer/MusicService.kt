package com.hoony.musicplayer

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat

class MusicService(
    context: Context,
    serviceComponent: ComponentName,
    callback: ConnectionCallback,
    rootHints: Bundle
) : MediaBrowserCompat(
    context, serviceComponent, callback, rootHints
) {

}