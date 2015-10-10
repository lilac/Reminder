package co.rewen.android.common.bind

import android.view.View
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.{AdapterView, Spinner}
import co.rewen.android.common.Mutable

/**
 * Copyright Junjun Deng 2015.
 */
object BSpinner {
  def bindInt(spinner: Spinner, state: Mutable[Int]) = {
    spinner.setSelection(state.get())
    spinner.setOnItemSelectedListener(new OnItemSelectedListener {
      override def onNothingSelected(parent: AdapterView[_]): Unit = {
      }

      override def onItemSelected(parent: AdapterView[_], view: View, position: Int, id: Long): Unit = {
        state.set(position)
      }
    })
  }
}
