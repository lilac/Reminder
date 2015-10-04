package co.rewen.android

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

class MainActivity extends AppCompatActivity with ReminderListFragment.OnItemSelected {
  var mToolbar: Option[Toolbar] = None

  protected override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)

    val toolbar = findViewById(R.id.toolbar).asInstanceOf[Toolbar]

    mToolbar = Some(toolbar)

    toolbar.setTitle(R.string.app_name)
    setSupportActionBar(toolbar)

    val fragment = new ReminderListFragment()
    val fragmentManager = getSupportFragmentManager
    val mainFragment = fragmentManager.findFragmentById(R.id.main)
    if (mainFragment == null) {
      fragmentManager.beginTransaction()
        .add(R.id.main, fragment)
        .commit()
    }
  }

  override def onItemSelected(item: Reminder): Unit = {
    val fragment = EditReminderFragment.newInstance(Some(item.id))
    getSupportFragmentManager.beginTransaction()
      .replace(R.id.main, fragment)
      .addToBackStack(null)
      .commit()
  }
}
