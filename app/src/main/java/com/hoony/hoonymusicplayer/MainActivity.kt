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
import com.hoony.hoonymusicplayer.fragments.MoreFragment
import java.util.*

enum class FragmentType(val position: Int) {
    HOME(0),
    FAVORITE(1),
    ALBUM(2)
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    private val fragmentStackList: List<Stack<Fragment>> = listOf(
        Stack<Fragment>().apply {
            add(HomeFragment())
        },
        Stack<Fragment>().apply {
            add(FavoriteFragment())
        },
        Stack<Fragment>().apply {
            add(AlbumFragment())
        },
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
                    showFragment(fragmentStackList[it.position].peek())
                }
            )
            createFragmentLiveData.observe(
                this@MainActivity,
                Observer {
                    val fragmentStack = fragmentStackList[getCurrentFragmentStackPosition()]
                    val fragment = MoreFragment(fragmentStack.size)
                    fragmentStack.push(fragment)
                    showFragment(fragmentStack.peek())
                }
            )
        }
    }

    override fun onBackPressed() {
        if (isLastFragment()) {
            super.onBackPressed()
        } else {
            val fragmentStack = fragmentStackList[getCurrentFragmentStackPosition()]
            fragmentStack.pop()
            showFragment(fragmentStack.peek())
        }
    }

    private fun isLastFragment(): Boolean {
        val fragmentPosition = getCurrentFragmentStackPosition()
        return fragmentStackList[fragmentPosition].size == 1
    }

    private fun getCurrentFragmentStackPosition(): Int =
        viewModel.fragmentTypeLiveData.value?.position ?: -1

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.frame.id, fragment).commit()
    }
}