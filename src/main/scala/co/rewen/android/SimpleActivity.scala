package co.rewen.android

import android.app.Activity
import android.os.Bundle

class SimpleActivity extends Activity {
  protected override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_simple)
  }
}
