package com.mikerzdev.snakegame.presentation.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData


fun <T> LiveData<T>.bind(owner: LifecycleOwner, observer: (T?) -> Unit) {
    if (owner is Fragment) this.observe(owner.viewLifecycleOwner) { it?.run { observer(it) } }
    else this.observe(owner) { observer(it) }
}