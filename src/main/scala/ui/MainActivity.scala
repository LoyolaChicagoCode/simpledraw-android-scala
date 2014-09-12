package edu.luc.etl.cs313.scala.hello
package ui

import java.io.IOException

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.media.{AudioManager, MediaPlayer, RingtoneManager}
import android.media.MediaPlayer.OnCompletionListener
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.Toast

/**
 * The main Android activity, which provides the required lifecycle methods.
 * By mixing in the reactive view behaviors, this class serves as the Adapter
 * in the Model-View-Adapter pattern. It connects the Android GUI view with the
 * reactive model.
 */
class MainActivity extends Activity with TypedActivity {

  private def TAG = "hello-android-activity"

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    Log.i(TAG, "onCreate")
    // load main GUI layout
    setContentView(R.layout.main)
    // programmatically define a long-click listener
    // (the regular onClick listener is defined declaratively in the main layout)
    findView(TR.main_textView).setOnLongClickListener(new OnLongClickListener {
      override def onLongClick(view: View): Boolean = {
        Log.i(TAG, "onLongClick")
        showToast()
        true
      }
    })
  }

  override def onStart(): Unit = {
    super.onStart()
    Log.i(TAG, "onStart")
  }

  def onClick(view: View): Unit = {
    Log.i(TAG, "onClick")
    playDefaultNotification()
  }

  /** Plays the default notification sound. */
  protected def playDefaultNotification(): Unit = {
    val defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val mediaPlayer = new MediaPlayer

    try {
      mediaPlayer.setDataSource(getApplicationContext, defaultRingtoneUri)
      mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION)
      mediaPlayer.prepare()
      mediaPlayer.setOnCompletionListener(new OnCompletionListener {
        override def onCompletion(mp: MediaPlayer): Unit = { mp.release() }
      })
      mediaPlayer.start()
    } catch {
      case ex: IOException =>  throw new RuntimeException(ex)
    }
  }

  /** Shows a toast notification. */
  protected def showToast(): Unit = {
    // show toast (small nonmodal notification popup)
    Toast.makeText(getApplicationContext, R.string.text_toast, Toast.LENGTH_SHORT).show()
  }
}
