package com.yummit.ownerapp.global

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.NonNull
import androidx.core.widget.NestedScrollView
import android.view.MotionEvent
import androidx.annotation.Nullable


class LockableNestedScrollView : NestedScrollView {
    // by default is scrollable
    private var scrollable = true

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr:Int) : super(context, attributeSet, defStyleAttr)

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return scrollable && super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return scrollable && super.onInterceptTouchEvent(ev)
    }

    fun setScrollingEnabled(enabled: Boolean) {
        scrollable = enabled
    }
}