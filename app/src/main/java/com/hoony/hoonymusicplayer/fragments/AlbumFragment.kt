package com.hoony.hoonymusicplayer.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.hoony.hoonymusicplayer.MainViewModel
import com.hoony.hoonymusicplayer.R
import com.hoony.hoonymusicplayer.databinding.FragAlbumBinding

class AlbumFragment : BaseFragment<FragAlbumBinding, MainViewModel>() {

    private val viewModel by viewModels<MainViewModel>()

    override fun getLayoutId(): Int = R.layout.frag_album

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }

    private fun setListener() {
        getBinding().apply {
            title.setOnClickListener {
                viewModel.createFragment(true)
            }
        }
    }
}