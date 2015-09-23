package co.rewen.android.common

/**
 * Copyright Junjun Deng 2015.
 */
class State[T](var v: T) extends Mutable[T] {

  override def get(): T = v

  override def set(v: T): Unit = {
    this.v = v
  }
}
