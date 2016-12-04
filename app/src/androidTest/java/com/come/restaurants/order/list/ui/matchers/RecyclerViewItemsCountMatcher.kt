package com.come.restaurants.order.list.ui.matchers

import android.support.v7.widget.RecyclerView
import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class RecyclerViewItemsCountMatcher(val expectedItemCount: Int) : TypeSafeMatcher<View>() {
    override fun describeTo(description: Description?) {
        description?.appendText("Recycler view does not contain $expectedItemCount items")
    }

    override fun matchesSafely(item: View?): Boolean {
        val recyclerView = item as RecyclerView
        return recyclerView.adapter.itemCount == expectedItemCount
    }

    companion object {
        fun withItemCounts(itemCount : Int) : Matcher<View> =
                RecyclerViewItemsCountMatcher(itemCount)
    }




}