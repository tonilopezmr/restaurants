package com.come.restaurants.order.list.ui.adapter

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.come.restaurants.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.padding
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.wrapContent

class OrderItemUI : AnkoComponent<ViewGroup> {
  override fun createView(ui: AnkoContext<ViewGroup>): View {
    return with(ui) {
      linearLayout {
        lparams {
          width = matchParent
          height = wrapContent
        }

        cardView {
          lparams {
            width = matchParent
            height = wrapContent
            topMargin = 16
          }

          verticalLayout {
            padding = 16
            lparams {
              width = matchParent
              height = wrapContent
            }

            linearLayout {
              orientation = LinearLayout.HORIZONTAL

              textView {
                id = R.id.orderNumberText
                setTextAppearance(this.context, R.style.TextAppearance_AppCompat_Headline)
              }.lparams(width = wrapContent, height = matchParent)
              textView {
                id = R.id.orderHourText
                setTextAppearance(this.context, R.style.TextAppearance_AppCompat_Headline)
                gravity = Gravity.END
              }.lparams(width = matchParent, height = matchParent)

            }

            linearLayout {
              orientation = LinearLayout.HORIZONTAL

              textView {
                id = R.id.orderPlatesText
              }.lparams(width = wrapContent, height = matchParent)
              textView {
                id = R.id.totalPriceText
                gravity = Gravity.END
              }.lparams(width = matchParent, height = matchParent)

            }
          }
        }
      }
    }
  }
}