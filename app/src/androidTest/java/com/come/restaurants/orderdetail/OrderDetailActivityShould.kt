package com.come.restaurants.orderdetail

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.come.restaurants.R
import com.come.restaurants.order.detail.OrderDetailActivity
import com.come.restaurants.order.list.ui.OrderListActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.assertion.ViewAssertions.matches
import org.junit.Before

@RunWith(AndroidJUnit4::class)
@LargeTest
class OrderDetailActivityShould {

  @Rule
  fun activity(): ActivityTestRule<OrderDetailActivity>
      = ActivityTestRule<OrderDetailActivity>(OrderDetailActivity::class.java, true, false)


  @Test
  fun show_correct_order_number() {
    val intent: Intent = Intent(InstrumentationRegistry.getContext(), OrderDetailActivity::class.java)
    intent.putExtra(OrderDetailActivity.NUMBER, "1")
    intent.putExtra(OrderDetailActivity.ID, "1")
    activity().launchActivity(intent)

    val resources = InstrumentationRegistry.getTargetContext().resources
    val number = resources.getString(R.string.number)

    onView(withId(R.id.orderNumberTextView)).check(matches(withText(number + " 1")))
  }

  @Test
  fun show_correct_order_detail() {
    val intent: Intent = Intent(InstrumentationRegistry.getContext(), OrderDetailActivity::class.java)
    intent.putExtra(OrderDetailActivity.NUMBER, "1")
    intent.putExtra(OrderDetailActivity.ID, "1")
    activity().launchActivity(intent)

    val order : String = "2x Tortilla \t2.6€\n1x Zumo \t0.95€\n"

    onView(withId(R.id.orderTextView)).check(matches(withText(order)))
  }

  @Test
  fun show_correct_total_price() {
    val intent: Intent = Intent(InstrumentationRegistry.getContext(), OrderDetailActivity::class.java)
    intent.putExtra(OrderDetailActivity.NUMBER, "1")
    intent.putExtra(OrderDetailActivity.ID, "1")
    activity().launchActivity(intent)

    onView(withId(R.id.totalPriceTextView)).check(matches(withText("3.55€")))
  }
}
