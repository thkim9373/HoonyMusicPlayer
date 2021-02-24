package com.hoony.hoonymusicplayer

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.hoony.hoonymusicplayer.databinding.ActivityMainBinding
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

    private val fragmentStackList: List<Stack<Fragment>> by lazy {
        viewModel.fragmentStackList.let {
            listOf(
                Stack<Fragment>().apply {
                    it[FragmentType.HOME.position].forEach {
                        add(it.newInstance())
                    }
                },
                Stack<Fragment>().apply {
                    it[FragmentType.FAVORITE.position].forEach {
                        add(it.newInstance())
                    }
                },
                Stack<Fragment>().apply {
                    it[FragmentType.ALBUM.position].forEach {
                        add(it.newInstance())
                    }
                },
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setListener()
        setObserver()
    }

    private fun setListener() {
        binding.apply {
            bottomNav.setOnNavigationItemSelectedListener {
                viewModel.changeCurrentFragmentType(getFragmentTypeById(it.itemId))

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
        viewModel.currentFragmentTypeLiveData.observe(
            this@MainActivity,
            Observer {
                showFragment(fragmentStackList[it.position].peek())
            }
        )
        viewModel.addFragmentEvent.observe(
            this@MainActivity,
            Observer {
                it.getContentIfNotHandled().let { fragmentClass ->
                    if (fragmentClass != null) {
                        val fragmentStack =
                            fragmentStackList[viewModel.getCurrentFragmentStackPosition()]
                        Log.d("hoony", fragmentClass.name)
                        val fragment = MoreFragment.newInstance(fragmentStack.size)
                        fragmentStack.push(fragment)
                        showFragment(fragmentStack.peek())
                    }
                }
            }
        )
    }

    override fun onBackPressed() {
        if (viewModel.isLastFragment()) {
            super.onBackPressed()
        } else {
            val fragmentStack = fragmentStackList[viewModel.getCurrentFragmentStackPosition()]
            fragmentStack.pop()
            showFragment(fragmentStack.peek())
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.frame.id, fragment).commit()
    }
}