package com.depromeet.whatnow.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.depromeet.whatnow.ui.R

abstract class BaseActivity : AppCompatActivity() {

    protected inline val TAG get() = this::class.java.simpleName
    protected open val activityTransition = ActivityTransition.Push

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityTransition.overridePendingTransition(this)
    }

    protected open fun handleException(throwable: Throwable) {
        Log.e(TAG, throwable.stackTraceToString())
        finish()
    }

    override fun finish() {
        super.finish()
        activityTransition.overridePendingPopTransition(this)
    }
}