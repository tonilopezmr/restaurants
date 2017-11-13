package com.come.restaurants.base

interface MVP {

  interface View {
    fun initUi()
  }

  interface Presenter<T : View> {
    fun init()
    fun setView(view: View)
  }

}

