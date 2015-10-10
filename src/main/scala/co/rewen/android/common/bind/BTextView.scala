package co.rewen.android.common.bind

import android.text.Editable
import android.widget.TextView
import co.rewen.android.common.StringUtils.StringImprovements
import co.rewen.android.common.{Mutable, State, TextWatcher}

/**
 * Copyright Junjun Deng 2015.
 */
object BTextView {
  def bindInt(textView: TextView, state: State[Int]) = {
    textView.setText(state.get().toString)
    textView.addTextChangedListener(new TextWatcher {
      override def afterTextChanged(s: Editable): Unit = {
        val string = s.toString
        string.toIntOpt.foreach { v =>
          state.set(v)
        }
      }
    })
  }

  def bindString(textView: TextView, string: Mutable[String]) = {
    textView.setText(string.get())
    textView.addTextChangedListener(new TextWatcher {
      override def afterTextChanged(s: Editable): Unit = {
        string.set(s.toString)
      }
    })
  }
}
