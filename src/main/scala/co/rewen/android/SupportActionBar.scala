package co.rewen.android

import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar

/**
 * Copyright Junjun Deng 2015.
 */
trait SupportActionBar {
  def getSupportActionBar: ActionBar

  def setSupportActionBar(bar: Toolbar)
}
