package com.come.restaurants.restaurant.login.ui

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.Gravity
import android.view.View
import com.come.restaurants.R
import com.come.restaurants.open.restaurant.ui.LoginRestaurantActivity
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.button
import org.jetbrains.anko.design.textInputLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.padding
import org.jetbrains.anko.textColor
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.wrapContent

class LoginRestaurantUI : AnkoComponent<LoginRestaurantActivity> {
  override fun createView(ui: AnkoContext<LoginRestaurantActivity>): View {
    return with(ui) {
      verticalLayout {
        padding = 64
        lparams {
          width = matchParent
          height = matchParent
          gravity = Gravity.CENTER
        }

        textInputLayout {
          textInputLayout {
            id = R.id.nameEditText
            hint = "Name"
          }
        }.lparams {
          width = matchParent
          height = wrapContent
          bottomMargin = 16
        }


        textInputLayout {
          textInputLayout {
            id = R.id.textPassword
            hint = "Password"
          }
        }.lparams {
          width = matchParent
          height = wrapContent
          bottomMargin = 64
        }

        button("Sign In") {
          id = R.id.openButton
        }.lparams {
          height = 150
          width = matchParent
        }

        button("Google Sign In") {
          id = R.id.openGoogleButton
          textColor = Color.WHITE
          background.setColorFilter(resources.getColor(R.color.google_login_button),
              PorterDuff.Mode.MULTIPLY)
          val image = resources.getDrawable(R.drawable.google_logo_white_24dp)
          setCompoundDrawablesWithIntrinsicBounds(
              null,
              null,
              image,
              null)
        }.lparams {
          width = matchParent
          height = 150
        }

      }
    }
  }
}