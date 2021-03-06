package com.karasiq.bootstrap.tooltip

import org.scalajs.jquery

import com.karasiq.bootstrap.components.BootstrapComponents
import com.karasiq.bootstrap.context.JSRenderingContext

trait JSTooltips { self: JSRenderingContext with BootstrapComponents with Tooltips ⇒
  import scalaTags.all._

  type Tooltip = JSTooltip
  object Tooltip extends TooltipFactory {
    def apply(content: Frag, placement: TooltipPlacement = TooltipPlacement.auto): AbstractTooltip = {
      new JSTooltip(TooltipOptions(html = true, title = content, placement = placement))
    }
  }

  class JSTooltip(val options: TooltipOptions) extends AbstractTooltip {
    def toggle: Modifier = new Modifier {
      def applyTo(t: Element): Unit = {
        val jsOptions = scalajs.js.Object().asInstanceOf[JSTooltipOptions]
        def set(value: String, f: String ⇒ Unit) = if (value.nonEmpty) f(value)
        jsOptions.animation = options.animation
        jsOptions.html = options.html
        jsOptions.placement = options.placement.toString
        jsOptions.title = options.title.render
        set(options.container, jsOptions.container = _)
        set(options.delay, jsOptions.delay = _)
        set(options.selector, jsOptions.selector = _)
        set(options.template, jsOptions.template = _)
        set(options.trigger, jsOptions.trigger = _)
        set(options.viewport, jsOptions.viewport = _)
        jquery.jQuery(t).tooltip(jsOptions)
      }
    }

    override def render(md: Modifier*): Modifier = {
      toggle +: md
    }
  }
}
