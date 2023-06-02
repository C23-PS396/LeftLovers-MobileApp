package com.example.LeftLoversApp.customView

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class PasswordCustomView : AppCompatEditText {
    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }
    override fun onTextChanged(char: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(char, start, lengthBefore, lengthAfter)
        if (char?.length ?: 0 < 8) {
            setError("Password minimal terdiri adalah 8 karakter")
        } else {
            error = null
        }
    }
}