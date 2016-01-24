package com.karasiq.bootstrap.test.frontend

import com.karasiq.bootstrap.BootstrapComponent
import com.karasiq.bootstrap.BootstrapImplicits._
import com.karasiq.bootstrap.progressbar.{ProgressBar, ProgressBarStyle}
import rx._
import rx.ops._

import scala.concurrent.duration._
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scalatags.JsDom.all._

final class TestProgressBar(style: ProgressBarStyle, updateInterval: FiniteDuration) extends BootstrapComponent {
  override def render(md: Modifier*): Modifier = {
    val progressBarValue = Var(0)

    val progressBar = ProgressBar.withLabel(progressBarValue).renderTag(style, ProgressBarStyle.striped, ProgressBarStyle.animated, md).render

    implicit val scheduler = new DomScheduler
    val timer = Timer(updateInterval)
    Obs(timer) {
      if (progressBarValue.now < 100) {
        progressBarValue.update(progressBarValue.now + 1)
      } else {
        progressBar.parentNode.removeChild(progressBar)
        progressBarValue.killAll()
      }
    }

    progressBar
  }
}