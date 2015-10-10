package co.rewen.android.common.bind

import android.widget.CompoundButton
import co.rewen.android.common.State

/**
 * Copyright Junjun Deng 2015.
 */
object BCompoundButton {
  def bindInt(button: CompoundButton, on: Int, off: Int, state: State[Int]): Unit = {
    val value = state.get()
    val status = value == on
    button.setChecked(status)
    button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener {
      override def onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean): Unit = {
        state.set(if (isChecked) on else off)
      }
    })
  }
}
