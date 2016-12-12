package com.come.restaurants.order.detail.ui

import android.view.Gravity
import android.view.View
import com.come.restaurants.R
import com.come.restaurants.order.detail.OrderDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class OrderDetailUI : AnkoComponent<OrderDetailActivity> {
  override fun createView(ui: AnkoContext<OrderDetailActivity>): View {
    return with(ui) {
      verticalLayout {

        cardView {
          gravity = Gravity.CENTER
          lparams {
            width = matchParent
            height = matchParent
            setMargins(24, 24, 24, 24)
          }

          verticalLayout {
            padding = 16
            gravity = Gravity.CENTER

            textView {
              id = R.id.orderNumberTextView
              gravity = Gravity.CENTER
              setTextAppearance(R.style.TextAppearance_AppCompat_Headline)
            }

            view {
              backgroundColor = R.color.stroke
            }.lparams {
              width = matchParent
              height = 1
              topMargin = 16
            }

            textView {
              textSize = 18f
              id = R.id.orderTextView
              gravity = Gravity.CENTER
            }.lparams {
              setMargins(16, 240, 16, 200)
            }

            view {
              backgroundColor = R.color.stroke
            }.lparams {
              width = matchParent
              height = 1
              topMargin = 16
            }

            linearLayout {
              padding = 16
              textView {
                setTextAppearance(R.style.TextAppearance_AppCompat_Headline)
                text = "Total:"
              }

              textView {
                setTextAppearance(R.style.TextAppearance_AppCompat_Headline)
                id = R.id.totalPriceTextView
                gravity = Gravity.END
              }.lparams(width = matchParent, height = wrapContent)
            }.lparams(width = matchParent, height = wrapContent)

            button("Print") {
              id = R.id.printButton
            }.lparams {
              width = wrapContent
              height = wrapContent
              gravity = Gravity.CENTER
            }

          }

        }
      }

    }
  }
}
