package com.hoony.hoonymusicplayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hoony.hoonymusicplayer.MainViewModel
import com.hoony.hoonymusicplayer.R
import com.hoony.hoonymusicplayer.databinding.FragTestBinding

class MoreFragment : Fragment() {

    companion object {
        private const val COUNT = "count"

        @JvmStatic
        fun newInstance(count: Int): MoreFragment {
            val args = Bundle()
            args.putInt(COUNT, count)

            val fragment = MoreFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: FragTestBinding
    private val viewModel by activityViewModels<MainViewModel>()

    private val num: Int by lazy {
        arguments?.getInt(COUNT) ?: -1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.frag_test,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = "${binding.title.text} - $num"
        setListener()
    }

    private fun setListener() {
        binding.apply {
            title.setOnClickListener {
                viewModel.addFragment(MoreFragment::class.java)
            }
        }
    }
}