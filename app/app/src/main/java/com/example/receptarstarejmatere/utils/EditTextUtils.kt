package com.example.receptarstarejmatere.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView

class EditTextUtils {

    companion object {
        fun showAnotherEditTextIfNotEmpty(
            editText: EditText,
            others: List<TextView>
        ) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 0 || s == "0") {
                        others.forEach { view ->
                            view.visibility = View.GONE
                        }
                    } else {
                        others.forEach { view ->
                            view.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }
    }
}
