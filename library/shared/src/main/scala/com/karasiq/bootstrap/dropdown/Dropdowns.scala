package com.karasiq.bootstrap.dropdown

import com.karasiq.bootstrap.context.RenderingContext

trait Dropdowns { self: RenderingContext ⇒
  import scalaTags.all._

  type Dropdown <: AbstractDropdown

  trait AbstractDropdown extends BootstrapHtmlComponent {
    def dropdownId: String
    def title: Modifier
    def items: Seq[Modifier]

    def dropdown: Tag
    def dropup: Tag

    def renderTag(md: ModifierT*): TagT = this.dropdown(md:_*)
  }

  trait DropdownFactory {
    def apply(title: Modifier, items: Modifier*): Dropdown
    def link(targetId: String, md: Modifier*): Tag

    def dropup(title: Modifier, items: Modifier*): Tag = apply(title, items:_*).dropup
    def item(md: Modifier*): Tag = this.link("javascript:void(0);", md:_*)
  }
}
