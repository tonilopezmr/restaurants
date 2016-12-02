package com.come.restaurants.printer.pairing

import com.come.restaurants.base.MVP
import com.come.restaurants.printer.domain.model.Printer


class BtPairingPresenter : MVP.Presenter<BtPairingPresenter.View> {

    lateinit var view : View

    interface View : MVP.View {
        fun show(printers : List<Printer>)
        fun turnOnBtMessage()
    }

    override fun init() {
        this.view.initUi()
        requestPrinters()
    }

    override fun setView(view: MVP.View) {
        this.view = view as View
    }

    fun requestPrinters() {
        /*
        * if bluetooth == ON:
        *   getPrinters
        * else:
        *   showBtOnMessage()
        *
        * */
    }
}