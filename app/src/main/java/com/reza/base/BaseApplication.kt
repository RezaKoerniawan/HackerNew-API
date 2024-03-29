package com.reza.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.multidex.MultiDex
import com.reza.base.util.PrefManager

class BaseApplication : Application(), LifecycleOwner {

    internal val mLifecycleRegistry = LifecycleRegistry(this)

    init {
        mLifecycleRegistry.markState(Lifecycle.State.INITIALIZED)
    }

    override fun onCreate() {
        super.onCreate()
        PrefManager.init(this)
    }

    override fun getLifecycle(): LifecycleRegistry {
        return mLifecycleRegistry
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}