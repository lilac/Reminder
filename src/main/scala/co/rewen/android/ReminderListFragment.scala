package co.rewen.android

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.{LinearLayoutManager, RecyclerView}
import android.view.View.OnClickListener
import android.view.{LayoutInflater, View, ViewGroup}
import co.rewen.android.ReminderListFragment.Observer

/**
 * Copyright Junjun Deng 2015.
 */
class ReminderListFragment extends Fragment {
  var mList: Option[RecyclerView] = None
  var mAdd: Option[FloatingActionButton] = None

  var observer: Observer = null
  var database: Database = null

  override def onAttach(activity: Activity): Unit = {
    super.onAttach(activity)
    observer = activity.asInstanceOf[Observer]
  }

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    database = new Database(getActivity)
  }

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    val view = inflater.inflate(R.layout.reminder_list, container, false)
    val list = view.findViewById(R.id.list).asInstanceOf[RecyclerView]
    val add = view.findViewById(R.id.add).asInstanceOf[FloatingActionButton]
    mList = Some(list)
    mAdd = Some(add)

    val table = database.Reminders
    val cursor = table.all()

    val adapter = new ReminderCursorAdapter(getActivity, cursor, observer.onItemSelected)
    list.setAdapter(adapter)
    list.setLayoutManager(new LinearLayoutManager(getActivity))

    add.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        observer.onAddItem()
      }
    })
    view
  }

  override def onDestroy(): Unit = {
    database.close()
    super.onDestroy()
  }
}

object ReminderListFragment {

  trait Observer {
    def onItemSelected(item: Reminder)

    def onAddItem()
  }

}