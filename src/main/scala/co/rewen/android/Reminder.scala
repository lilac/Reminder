package co.rewen.android

import java.util.Date

case class Interval(count: Int, t: IntervalType)

case class Reminder(id: Long, title: String, time: Date, repeat: Int, interval: Interval, status: Int)

object Reminder {
  val OFF = 0
  val ON = 1
}