package co.rewen.android

import java.util.Date

import android.content.{ContentValues, Context}
import android.database.sqlite.{SQLiteDatabase, SQLiteOpenHelper}
import android.provider.BaseColumns

/**
 * Copyright Junjun Deng 2015.
 */
class Database(context: Context) extends SQLiteOpenHelper(context, Database.NAME, null, Database.VERSION) {

  override def onCreate(db: SQLiteDatabase): Unit = {
    Database.ReminderTable.create(db)
  }

  override def onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int): Unit = {

  }
  
  def wrapReadAction[T](f: SQLiteDatabase => T): T = f(getReadableDatabase)
  
  def wrapWriteAction[T](f: SQLiteDatabase => T): T = f(getWritableDatabase)
  
  object ReminderTable {
    val get = wrapReadAction(Database.ReminderTable.get)
    val insert = wrapWriteAction(Database.ReminderTable.insert)
    val update = wrapWriteAction(Database.ReminderTable.update)
    val delete = wrapWriteAction(Database.ReminderTable.delete)
  }

}

object Database {
  val NAME = "app"
  val VERSION = 1
  val REMINDER = "reminder"

  object ReminderTable extends BaseColumns {
    val _ID = BaseColumns._ID
    val _COUNT = BaseColumns._COUNT
    val TITLE = "title"
    val TIME = "time"
    val REPEAT = "repeat"
    val INTERVAL = "interval"
    val INTERVAL_TYPE = "interval_type"
    val STATUS = "status"
    
    val columns = Array(_ID, TITLE, TIME, REPEAT, INTERVAL, INTERVAL_TYPE, STATUS)
    def create(db: SQLiteDatabase): Unit = {
      val dml =
        s"""
           |create table if not exists ${Database.REMINDER} (
           |${_ID} integer primary key,
           |$TITLE text not null,
           |$TIME integer not null,
           |$REPEAT integer not null,
           |$INTERVAL integer,
           |$INTERVAL_TYPE integer,
           |$STATUS integer not null
           |);
        """.stripMargin
      db.execSQL(dml)
    }
    
    def contentValues(reminder: Reminder): ContentValues = {
      val values: ContentValues = new ContentValues()
      values.put(TITLE, reminder.title)
      values.put(TIME, reminder.time.getTime)
      values.put(REPEAT, reminder.repeat)
      values.put(INTERVAL, reminder.interval.count)
      values.put(INTERVAL_TYPE, reminder.interval.t.ordinal())
      values.put(STATUS, reminder.status)
      values
    }

    def insert(db: SQLiteDatabase) (reminder: Reminder): Long = {
      val values = contentValues(reminder)
      db.insert(REMINDER, null, values)
    }

    def update(db: SQLiteDatabase) (reminder: Reminder): Int = {
      val values = contentValues(reminder)
      db.update(REMINDER, values, s"$_ID = ?", Array(reminder.id.toString))
    }
    
    def get(db: SQLiteDatabase) (id: Long): Option[Reminder] = {
      val cursor = db.query(REMINDER, columns, s"${_ID} = ?", Array(id.toString), null, null, null, null)
      if (cursor.moveToFirst()) {
        val intervalType = cursor.getInt(5)
        val interval = Interval(cursor.getInt(4), IntervalType.values()(intervalType))
        val reminder = Reminder(
          id = cursor.getLong(0),
          title = cursor.getString(1),
          time = new Date(cursor.getInt(2)),
          repeat = cursor.getInt(3),
          interval = interval,
          status = cursor.getInt(6)
        )
        Some(reminder)
      } else {
        None
      }
    }
    
    def delete(db: SQLiteDatabase) (id: Long): Int = {
      db.delete(REMINDER, s"${_ID} = ?", Array(id.toString))
    }
  }
}
