package co.rewen.android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import co.rewen.android.common.{HasBackStack, SupportActionBar}

class MainActivity extends AppCompatActivity
with ReminderListFragment.Observer
with SupportActionBar
with HasBackStack {

  var mToolbar: Option[Toolbar] = None

  protected override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)

    val toolbar = findViewById(R.id.toolbar).asInstanceOf[Toolbar]

    mToolbar = Some(toolbar)

    toolbar.setTitle(R.string.app_name)
    setSupportActionBar(toolbar)
    val actionBar = getSupportActionBar
    actionBar.setDisplayShowHomeEnabled(true)

    val fragment = new ReminderListFragment()
    val fragmentManager = getSupportFragmentManager
    val mainFragment = fragmentManager.findFragmentById(R.id.main)
    if (mainFragment == null) {
      fragmentManager.beginTransaction()
        .add(R.id.main, fragment)
        .commit()
    }
    initBackStack()
  }

  override def onItemSelected(item: Reminder): Unit = {
    val fragment = EditReminderFragment.newInstance(Some(item.id))
    showFragment(fragment)
  }

  def showFragment(fragment: Fragment) = {
    getSupportFragmentManager.beginTransaction()
      .replace(R.id.main, fragment)
      .addToBackStack(null)
      .commit()
  }

  override def onOptionsItemSelected(item: MenuItem): Boolean = {
    item.getItemId match {
      case android.R.id.home =>
        onBackPressed()
        true
      case _ =>
        super.onOptionsItemSelected(item)
    }
  }

  override def onAddItem(): Unit = {
    val fragment = EditReminderFragment.newInstance(None)
    showFragment(fragment)
  }
}
