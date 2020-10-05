package com.hoony.hoonymusicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.hoony.hoonymusicplayer.databinding.ActivityMainBinding
import com.hoony.hoonymusicplayer.fragments.AlbumFragment
import com.hoony.hoonymusicplayer.fragments.FavoriteFragment
import com.hoony.hoonymusicplayer.fragments.HomeFragment

enum class FragmentType(val position: Int) {
    HOME(0),
    FAVORITE(1),
    ALBUM(2)
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    private val fragmentList: List<Fragment> = listOf(
        HomeFragment(),
        FavoriteFragment(),
        AlbumFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setListener()
        setObserver()
    }

    private fun setListener() {
        binding.apply {
            bottomNav.setOnNavigationItemSelectedListener {
                viewModel.changeFragmentType(getFragmentTypeById(it.itemId))

                return@setOnNavigationItemSelectedListener true
            }
        }
    }

    private fun getFragmentTypeById(id: Int) =
        when (id) {
            R.id.home -> FragmentType.HOME
            R.id.favorite -> FragmentType.FAVORITE
            R.id.album -> FragmentType.ALBUM
            else -> FragmentType.HOME
        }

    private fun setObserver() {
        viewModel.apply {
            fragmentTypeLiveData.observe(
                this@MainActivity,
                Observer {
                    showFragment(fragmentList[it.position])
                }
            )
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.frame.id, fragment).commit()
    }
}