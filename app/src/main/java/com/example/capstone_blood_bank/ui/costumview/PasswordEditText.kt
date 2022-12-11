package com.example.capstone_blood_bank.ui.costumview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.view.updatePadding
import com.example.capstone_blood_bank.R

class PasswordEditText : AppCompatEditText {
    private lateinit var icPassword: Drawable
    private var enabledBackground: Drawable? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        transformationMethod = PasswordTransformationMethod.getInstance()
        background = enabledBackground
    }

    private fun init() {
        icPassword =
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_lock_24) as Drawable
        inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.shape_edit_text)
        compoundDrawablePadding = 12

        updatePadding(12, 0, 12, 0)
        setHint(R.string.password)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setAutofillHints(AUTOFILL_HINT_PASSWORD)
        }
        setDrawable(icPassword)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (!s.isNullOrEmpty() && s.length < 6)
                    error = context.getString(R.string.password_error)
            }
        })
    }

    private fun setDrawable(
        start: Drawable? = null,
        top: Drawable? = null,
        end: Drawable? = null,
        bottom: Drawable? = null,
    ) {
        setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom)
    }
}