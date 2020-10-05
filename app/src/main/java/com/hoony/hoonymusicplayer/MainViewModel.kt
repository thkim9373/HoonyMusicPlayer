package com.hoony.hoonymusicplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class MainViewModel(private val handle: SavedStateHandle) : ViewModel() {

    companion object {
        private const val HANDLE_KEY_FRAGMENT_TYPE = "fragment_type"
    }

    private var fragmentType: FragmentType =
        handle.get<FragmentType>(HANDLE_KEY_FRAGMENT_TYPE) ?: FragmentType.HOME
        set(value) {
            handle.set(HANDLE_KEY_FRAGMENT_TYPE, value)
            field = value
        }

    private val _fragmentTypeLiveData = MutableLiveData<FragmentType>(fragmentType)
    val fragmentTypeLiveData: LiveData<FragmentType> = _fragmentTypeLiveData

    fun changeFragmentType(fragmentType: FragmentType) {
        this.fragmentType = fragmentType
        _fragmentTypeLiveData.value = fragmentType
    }
}