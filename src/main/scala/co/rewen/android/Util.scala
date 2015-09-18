package co.rewen.android

import java.util.Date

import scala.util.Random

/**
 * Copyright Junjun Deng 2015.
 */
object Util {

  def randomReminder(): Reminder = {
    val id: Int = Random.nextInt()
    val title: String = Random.nextString(10)
    val date: Date = new Date(Random.nextLong())
    val repeat = Random.nextInt()
    val count = Random.nextInt()
    val t = IntervalType.Hour
    val interval = new Interval(count, t)
    val status = Reminder.ON
    Reminder(id, title, date, repeat, interval, status)
  }

  def translateInterval(t: IntervalType): Int = {
    t match {
      case IntervalType.Min =>
        R.string.minute
      case IntervalType.Hour =>
        R.string.hour
      case IntervalType.Day =>
        R.string.day
      case IntervalType.Week =>
        R.string.week
      case IntervalType.Month =>
        R.string.month
    }
  }
}
