package co.rewen.android

import java.text.DateFormat

import android.content.Context
import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.{ImageView, TextView}
import co.rewen.android.Database.ReminderTable
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator

/**
 * Copyright Junjun Deng 2015.
 */
class ReminderCursorAdapter(context: Context, cursor: Cursor)
  extends CursorRecyclerViewAdapter[ReminderCursorAdapter.ViewHolder](context, cursor) {
  type VH = ReminderCursorAdapter.ViewHolder

  override def onBindViewHolder(viewHolder: VH, cursor: Cursor): Unit = {
    val reminder = ReminderTable.fromCursor(cursor)
    viewHolder.setValue(reminder)
  }

  override def onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = {
    val context = parent.getContext
    val inflater = LayoutInflater.from(context)
    val root = inflater.inflate(R.layout.reminder, parent, false)
    new ReminderCursorAdapter.ViewHolder(root)
  }
}

object ReminderCursorAdapter {

  class ViewHolder(view: View) extends RecyclerView.ViewHolder(view) {
    val context = view.getContext

    val mTitle: TextView = view.findViewById(R.id.title).asInstanceOf[TextView]
    val mDateTime: TextView = view.findViewById(R.id.date_time).asInstanceOf[TextView]
    val mInfo: TextView = view.findViewById(R.id.repeat_info).asInstanceOf[TextView]
    val mThumbnail: ImageView = view.findViewById(R.id.thumbnail).asInstanceOf[ImageView]
    val mStatus: ImageView = view.findViewById(R.id.status).asInstanceOf[ImageView]

    def setValue(value: Reminder): Unit = {
      setTitle(value.title)
      val dateTime = DateFormat.getDateTimeInstance.format(value.time)
      mDateTime.setText(dateTime)
      val icon: Int = value.status match {
        case Reminder.OFF =>
          R.drawable.ic_notifications_off_white_24dp
        case _ =>
          R.drawable.ic_notifications_active_white_24dp
      }
      val interval: String = context.getString(Util.translateInterval(value.interval.t))
      val text: String = context.getString(R.string.repeat_info, value.interval.count.asInstanceOf[Integer], interval,
        value.repeat.asInstanceOf[Integer])
      mInfo.setText(text)
      mStatus.setImageResource(icon)

    }

    def setTitle(title: String): Unit = {
      mTitle.setText(title)
      val letter = title.substring(0, 1)
      val color: Int = ColorGenerator.DEFAULT.getRandomColor

      val thumbnail = TextDrawable.builder.buildRound(letter, color)
      mThumbnail.setImageDrawable(thumbnail)
    }
  }
}