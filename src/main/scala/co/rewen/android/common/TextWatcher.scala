package co.rewen.android.common

import android.text.Editable

/**
 * Copyright Junjun Deng 2015.
 */
class TextWatcher extends android.text.TextWatcher {
  override def beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int): Unit = {}

  override def onTextChanged(s: CharSequence, start: Int, before: Int, count: Int): Unit = {}

  override def afterTextChanged(s: Editable): Unit = {}
}
