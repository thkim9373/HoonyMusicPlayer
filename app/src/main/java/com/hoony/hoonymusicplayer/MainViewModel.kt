package com.hoony.hoonymusicplayer

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hoony.hoonymusicplayer.fragments.AlbumFragment
import com.hoony.hoonymusicplayer.fragments.FavoriteFragment
import com.hoony.hoonymusicplayer.fragments.HomeFragment
import com.hoony.hoonymusicplayer.life_cycle.Event
import java.util.*

class MainViewModel(private val handle: SavedStateHandle) : ViewModel() {

    companion object {
        private const val HANDLE_KEY_FRAGMENT_STACK = "fragment_stack"
        private const val HANDLE_KEY_FRAGMENT_TYPE = "fragment_type"
    }

    var fragmentStackList: List<Stack<Class<out Fragment>>> = handle.get(
        HANDLE_KEY_FRAGMENT_STACK
    ) ?: listOf(
        Stack<Class<out Fragment>>().apply {
            add(HomeFragment::class.java)
        },
        Stack<Class<out Fragment>>().apply {
            add(FavoriteFragment::class.java)
        },
        Stack<Class<out Fragment>>().apply {
            add(AlbumFragment::class.java)
        },
    )
        set(value) {
            handle.set(HANDLE_KEY_FRAGMENT_STACK, value)
            field = value
        }

    private var fragmentType: FragmentType =
        handle.get<FragmentType>(HANDLE_KEY_FRAGMENT_TYPE) ?: FragmentType.HOME
        set(value) {
            handle.set(HANDLE_KEY_FRAGMENT_TYPE, value)
            field = value
        }

    private val _currentFragmentTypeLiveData = MutableLiveData<FragmentType>(fragmentType)
    val currentFragmentTypeLiveData: LiveData<FragmentType> = _currentFragmentTypeLiveData

    fun changeCurrentFragmentType(fragmentType: FragmentType) {
        this.fragmentType = fragmentType
        _currentFragmentTypeLiveData.value = fragmentType
    }

    fun getCurrentFragmentStackPosition(): Int =
        currentFragmentTypeLiveData.value?.position ?: -1

    fun isLastFragment(): Boolean =
        fragmentStackList[getCurrentFragmentStackPosition()].size == 1


    private val _addFragmentEvent = MutableLiveData<Event<Class<out Fragment>>>()
    val addFragmentEvent: LiveData<Event<Class<out Fragment>>>
        get() = _addFragmentEvent

    fun addFragment(fragmentClass: Class<out Fragment>) {
        fragmentStackList[getCurrentFragmentStackPosition()].add(fragmentClass)

        _addFragmentEvent.value = Event(fragmentClass)
    }
}