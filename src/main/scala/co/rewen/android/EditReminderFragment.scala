package co.rewen.android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view._
import android.widget.{ArrayAdapter, CompoundButton, EditText, Spinner}
import co.rewen.android.common.State
import co.rewen.android.common.bind.{BCompoundButton, BSpinner, BTextView}

/**
 * Copyright Junjun Deng 2015.
 */
class EditReminderFragment extends Fragment {

  import EditReminderFragment._

  var model: Reminder = Reminder()
  var state: ViewState = null

  private var database: Database = null
  private var saveChange = true

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
    BTextView.bindString(title, state.title)

    val notificationSwitch = view.findViewById(R.id.notification_switch).asInstanceOf[CompoundButton]
    BCompoundButton.bindInt(notificationSwitch, Reminder.ON, Reminder.OFF, state.status)

    val repeat = view.findViewById(R.id.repeat).asInstanceOf[EditText]
    BTextView.bindInt(repeat, state.repeat)

    val interval = view.findViewById(R.id.interval).asInstanceOf[EditText]
    BTextView.bindInt(interval, state.interval)

    // Initialize the internal type spinner
    val intervalType = view.findViewById(R.id.interval_type).asInstanceOf[Spinner]
    // Create an ArrayAdapter using the string array and a default spinner layout
    val adapter = ArrayAdapter.createFromResource(getActivity,
      R.array.interval_types, R.layout.capitalize_textview)
    // Specify the layout to use when the list of choices appears
    adapter.setDropDownViewResource(R.layout.capitalize_textview)
    // Apply the adapter to the spinner
    intervalType.setAdapter(adapter)
    BSpinner.bindInt(intervalType, state.intervalType)

    view
  }

  override def onOptionsItemSelected(item: MenuItem): Boolean = {
    item.getItemId match {
      case R.id.discard =>
        database.Reminders.delete(model.id)
        saveChange = false
        finish()
      case _ =>
        super.onOptionsItemSelected(item)
    }
  }

  def finish() = {
    getActivity.onBackPressed()
    true
  }

  override def onPause(): Unit = {
    if (saveChange) {
      val reminder = mergeDiff(model, state)
      if (reminder.id < 0) {
        database.Reminders.insert(reminder)
      } else {
        database.Reminders.update(reminder)
      }
    }
    super.onPause()
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
    val status = new State[Int](v.status)

    val repeat = new State[Int](v.repeat)
    val interval = new State[Int](v.interval.count)
    val intervalType = new State[Int](v.interval.t.ordinal())
  }

  def mergeDiff(model: Reminder, state: ViewState): Reminder = {
    val intervalType = state.intervalType.get()
    model.copy(
      title = state.title.get(),
      status = state.status.get(),
      repeat = state.repeat.get(),
      interval = new Interval(state.interval.get(), IntervalType.values()(intervalType))
    )
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