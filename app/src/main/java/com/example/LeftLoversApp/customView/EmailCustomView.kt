package com.example.LeftLoversApp.customView

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet

import androidx.appcompat.widget.AppCompatEditText
import java.util.regex.Pattern

class EmailCustomView : AppCompatEditText {
    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }
    private val emailRegex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$"

    init {
        onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                checkEmailCustomView()
            }
        }
    }
    private fun checkEmail(email: String): Boolean {
        val matcher = Pattern.compile(emailRegex).matcher(email)
        return matcher.matches()
    }

    private fun checkEmailCustomView() {
        val email = text.toString()
        if (!checkEmail(email)) {
            setError("Email yang anda masukkan tidak cocok")
            setTextColor(Color.BLACK)
        } else {
            setError(null)
            setTextColor(Color.BLACK)
        }
    }


}