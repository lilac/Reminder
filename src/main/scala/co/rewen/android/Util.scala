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
}
