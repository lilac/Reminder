package co.rewen.android

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.{LinearLayoutManager, RecyclerView, Toolbar}

class MainActivity extends AppCompatActivity {
  var mList: Option[RecyclerView] = None
  var mToolbar: Option[Toolbar] = None
  var mAdd: Option[FloatingActionButton] = None

  protected override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)

    val list = findViewById(R.id.list).asInstanceOf[RecyclerView]
    val toolbar = findViewById(R.id.toolbar).asInstanceOf[Toolbar]
    val add = findViewById(R.id.add).asInstanceOf[FloatingActionButton]
    mList = Some(list)
    mToolbar = Some(toolbar)
    mAdd = Some(add)

    val table = new Database(this).Reminders
    val cursor = table.all
    if (cursor.getCount == 0) {
      for (i <- 1 to 10) {
        val reminder = Util.randomReminder()
        table.insert(reminder)
      }
    }
    toolbar.setTitle(R.string.app_name)
    setSupportActionBar(toolbar)

    val adapter = new ReminderCursorAdapter(this, cursor)
    list.setAdapter(adapter)
    list.setLayoutManager(new LinearLayoutManager(this))
  }
}
