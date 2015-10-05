package co.rewen.android.common

import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity

/**
 * Copyright Junjun Deng 2015.
 */
trait HasBackStack extends AppCompatActivity with FragmentManager.OnBackStackChangedListener {
  def initBackStack() = {
    onBackStackChanged()
    getSupportFragmentManager.addOnBackStackChangedListener(this)
  }

  override def onBackStackChanged(): Unit = {
    getSupportActionBar.setDisplayHomeAsUpEnabled(getSupportFragmentManager.getBackStackEntryCount > 0)
  }
}
