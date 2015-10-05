package co.rewen.android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view._
import android.widget.EditText
import co.rewen.android.common.State

/**
 * Copyright Junjun Deng 2015.
 */
class EditReminderFragment extends Fragment {

  import EditReminderFragment._

  var model: Reminder = Reminder()
  var state: ViewState = null

  private var database: Database = null

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)

    database = new Database(getActivity)
    val args = getArguments
    val id = args.getLong(ID)
    if (id != 0) {
      val reminder = database.Reminders.get(id)
      reminder.foreach(model = _)
    }
    state = new ViewState(model)

    setHasOptionsMenu(true)
  }

  override def onCreateOptionsMenu(menu: Menu, inflater: MenuInflater): Unit = {
    inflater.inflate(R.menu.reminder_edit, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    val view = inflater.inflate(R.layout.reminder_edit, container, false)
    val title = view.findViewById(R.id.title).asInstanceOf[EditText]
    BindUtils.bindTextView(title, state.title)
    view
  }

  override def onOptionsItemSelected(item: MenuItem): Boolean = {
    item.getItemId match {
      case R.id.discard =>
        database.Reminders.delete(model.id)
        getActivity.onBackPressed()
        true
      case _ =>
        super.onOptionsItemSelected(item)
    }
  }

  override def onDestroy(): Unit = {
    database.close()
    super.onDestroy()
  }
}

object EditReminderFragment {
  val ID = "id"

  class ViewState(v: Reminder) {
    val title = new State[String](v.title)

  }

  def newInstance(id: Option[Long]): EditReminderFragment = {
    val fragment = new EditReminderFragment
    val bundle = new Bundle()
    id.foreach(
      bundle.putLong(ID, _)
    )
    fragment.setArguments(bundle)
    fragment
  }
}