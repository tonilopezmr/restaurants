package com.come.restaurants.restaurant.login.ui

import android.graphics.Color
import android.graphics.PorterDuff
import android.text.InputType
import android.view.Gravity
import android.view.View
import com.come.restaurants.R
import com.come.restaurants.open.restaurant.ui.LoginRestaurantActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.design.textInputLayout

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
          editText {
            id = R.id.nameEditText
            hint = "Name"
          }
        }.lparams {
          width = matchParent
          height = wrapContent
          bottomMargin = 16
        }


        textInputLayout {
          editText {
            id = R.id.textPassword
            inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
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