package com.come.restaurants.order.list.ui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.come.restaurants.R
import com.come.restaurants.order.list.ui.adapter.OrderListAdapter
import com.come.restaurants.order.list.ui.matchers.RecyclerViewItemsCountMatcher
import com.come.restaurants.order.list.ui.viewassertion.RecyclerSortedViewAssertion
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
@LargeTest
class OrderListActivityTest {

    @Rule
    fun orderListActivityTestRule(): ActivityTestRule<OrderListActivity> =
            ActivityTestRule<OrderListActivity>(OrderListActivity::class.java)

    @Test
    fun show_all_characters_size_in_list_view() {
        onView(withId(R.id.ordersRecyclerView))
                .check(matches(RecyclerViewItemsCountMatcher.withItemCounts(2)))
    }

    @Test
    fun recycler_view_should_be_sorted_by_timestamp() {
        val withAdapter = object : RecyclerSortedViewAssertion.WithAdapter<Long> {
            override fun itemsToSort(recyclerView: RecyclerView): List<Long> {
                val adapter = recyclerView.adapter as OrderListAdapter
                val result = ArrayList<Long>()
                var i = 0
                while (i < adapter.itemCount) {
                    result.add(adapter.getItems()[i].timestamp)
                    i++
                }
                return result
            }
        }

        onView(withId(R.id.ordersRecyclerView))
                .check(RecyclerSortedViewAssertion.isSorted(withAdapter))
    }
}