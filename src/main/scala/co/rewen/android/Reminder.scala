package co.rewen.android

import java.util.Date

case class Interval(count: Int = 0, t: IntervalType = IntervalType.Day)

case class Reminder(id: Long = -1, title: String = "", time: Date = new Date(), repeat: Int = 1,
                    interval: Interval = Interval(), status: Int = Reminder.OFF)

object Reminder {
  val OFF = 0
  val ON = 1
}