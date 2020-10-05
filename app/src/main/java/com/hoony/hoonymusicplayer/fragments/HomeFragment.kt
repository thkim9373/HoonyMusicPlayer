package com.hoony.hoonymusicplayer.fragments

import androidx.fragment.app.viewModels
import com.hoony.hoonymusicplayer.MainViewModel
import com.hoony.hoonymusicplayer.R
import com.hoony.hoonymusicplayer.databinding.FragHomeBinding

class HomeFragment : BaseFragment<FragHomeBinding, MainViewModel>() {

    private val viewModel by viewModels<MainViewModel>()

    override fun getLayoutId(): Int = R.layout.frag_home
}