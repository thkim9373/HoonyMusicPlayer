package com.hoony.musicplayer

import android.media.browse.MediaBrowser
import android.os.Bundle
import android.service.media.MediaBrowserService

class MusicService : MediaBrowserService() {
    override fun onLoadChildren(p0: String, p1: Result<MutableList<MediaBrowser.MediaItem>>) {
        TODO("Not yet implemented")
    }

    override fun onGetRoot(p0: String, p1: Int, p2: Bundle?): BrowserRoot? {
        TODO("Not yet implemented")
    }

}