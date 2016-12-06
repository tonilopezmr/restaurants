package com.come.restaurants.order.list.ui

import android.view.View
import com.come.restaurants.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class OrderListUI : AnkoComponent<OrderListActivity> {
    override fun createView(ui: AnkoContext<OrderListActivity>): View {
        return with(ui) {
            verticalLayout {
                progressBar {
                    id = R.id.orderLoader
                    visibility = View.GONE
                }.lparams(height = wrapContent, width = wrapContent)

                recyclerView {
                    id = R.id.ordersRecyclerView
                }.lparams(height = matchParent, width = matchParent)
            }
        }
    }
}