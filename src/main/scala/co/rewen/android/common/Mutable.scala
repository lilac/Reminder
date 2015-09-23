package co.rewen.android.common

/**
 * Copyright Junjun Deng 2015.
 */
trait Mutable[T] {

  def get(): T

  def set(v: T)
}
