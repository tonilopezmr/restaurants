package com.come.restaurants.restaurant.login.ui

import android.text.InputType
import android.view.Gravity
import android.view.View
import com.come.restaurants.R
import com.come.restaurants.open.restaurant.ui.LoginRestaurantActivity
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.button
import org.jetbrains.anko.design.textInputLayout
import org.jetbrains.anko.editText
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.padding
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

      }
    }
  }
}