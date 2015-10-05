package co.rewen.android.common

import android.text.Editable
import android.widget.TextView

/**
 * Copyright Junjun Deng 2015.
 */
object BindUtils {

  def bindTextView(textView: TextView, string: Mutable[String]) = {
    textView.setText(string.get())
    textView.addTextChangedListener(new TextWatcher {
      override def afterTextChanged(s: Editable): Unit = {
        string.set(s.toString)
      }
    })
  }
}
