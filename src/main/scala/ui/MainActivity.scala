package edu.luc.etl.cs313.scala.simpledraw
package ui

import android.app.Activity
import android.os.Bundle
import android.util.Log

/**
 * The main Android activity, which provides the required lifecycle methods.
 * By mixing in the reactive view behaviors, this class serves as the Adapter
 * in the Model-View-Adapter pattern. It connects the Android GUI view with the
 * reactive model.
 */
class MainActivity extends Activity with TypedActivity {

  private def TAG = "simpledraw-android-activity"

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    Log.i(TAG, "onCreate")
    // load main GUI layout
    setContentView(R.layout.main)
  }

  override def onStart(): Unit = {
    super.onStart()
    Log.i(TAG, "onStart")
  }
}
