package edu.luc.etl.cs313.scala.simpledraw
package ui

import android.util.AttributeSet
import android.view.{WindowManager, View}
import android.content.Context
import android.graphics.{Point, Canvas, Color, Paint}

/**
 * A custom widget for drawing some lines.
 */
class DrawWidget(context: Context) extends View(context) {

  // this class will not work at runtime
  // unless all required constructors are implemented

  def this(context: Context, attrs: AttributeSet, defStyle: Int) { this(context) }

  def this(context: Context, attrs: AttributeSet) { this(context) }

  private def TAG = "simpledraw-android-activity"

  private val SIZE = {
    // obtain screen size programmatically, see also
    // http://stackoverflow.com/questions/1016896/how-to-get-screen-dimensions
    val display = context.getSystemService(Context.WINDOW_SERVICE)
      .asInstanceOf[WindowManager].getDefaultDisplay()
    val size = new Point
    display.getSize(size)
    math.min(size.x, size.y)
  }

  override protected def onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int): Unit = {
    setMeasuredDimension(SIZE , SIZE)
  }

  private val paint = new Paint

  override protected def onDraw(canvas: Canvas): Unit = {
    canvas.drawColor(Color.WHITE)
    canvas.drawLine(SIZE / 3, 0, SIZE / 3, SIZE, paint)

    paint.setColor(Color.RED)
    paint.setStrokeWidth(SIZE / 10)
    canvas.drawLine(SIZE * 9 / 16, 0, SIZE * 9 / 16, SIZE, paint)

    /**
     * Produces a sequence of alpha values for gradual fading.
     * Conceptually speaking, this is a coalgebra (unfold).
     */
    def alphas(alpha: Int): Iterable[Int] =
      if (alpha <= 0) Iterable.empty else Iterable(alpha) ++ alphas(alpha >> 1)

    paint.setColor(Color.GREEN)
    paint.setStrokeWidth(SIZE / 20)

    for ((alpha, y) <- alphas(255).zip((SIZE * 3 / 10) to (SIZE * 3) by SIZE / 10)) {
      paint.setAlpha(alpha)
      canvas.drawLine(0, y, SIZE, y, paint)
    }
  }
}

