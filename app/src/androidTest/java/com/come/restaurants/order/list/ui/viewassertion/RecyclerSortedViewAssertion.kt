package com.come.restaurants.order.list.ui.viewassertion

import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.PerformException
import android.support.test.espresso.ViewAssertion
import android.support.test.espresso.core.deps.guava.collect.Ordering
import android.support.test.espresso.util.HumanReadables
import android.support.v7.widget.RecyclerView
import android.view.View
import org.hamcrest.StringDescription
import org.junit.Assert.assertTrue
import java.util.*

class RecyclerSortedViewAssertion<T: Comparable<T>>(val withAdapter: WithAdapter<T>) : ViewAssertion {
    companion object {
        fun <T: Comparable<T>> isSorted(adapter: WithAdapter<T>) =
                RecyclerSortedViewAssertion<T>(adapter)
    }

    private var sortedList: List<T> = ArrayList()

    init {
        checkNotNull(withAdapter)
    }

    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        val description: StringDescription = StringDescription()
        val recyclerView: RecyclerView = view as RecyclerView
        sortedList = withAdapter.itemsToSort(recyclerView)

        checkIsNotEmpty(view, description)

        description.appendText("The list $sortedList is not sorted")
        assertTrue(description.toString(), Ordering.natural<T>().isOrdered(sortedList))
    }

    fun checkIsNotEmpty(view: View?, description: StringDescription) {
        if(sortedList.isEmpty()) {
            description.appendText("The list must be not null")
            throw (PerformException.Builder())
                    .withActionDescription(description.toString())
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(IllegalStateException("The list is empty"))
                    .build()
        }
    }

    interface WithAdapter<T> {
        fun itemsToSort(recyclerView: RecyclerView) : List<T>
    }
}