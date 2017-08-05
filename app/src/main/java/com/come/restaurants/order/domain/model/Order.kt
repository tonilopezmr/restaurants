package com.come.restaurants.order.domain.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class Order(val id: String = "",
            val code: String = "",
            val timestamp: Long = 0,
            val comment: String = "",
            val orderLines: List<OrderLine> = emptyList()) : Parcelable{
  fun getPrice(): Double {
    return orderLines.fold(0.0) { price, orderLine ->
      price + orderLine.getPrice()
    }
  }

  companion object {
    @JvmField val CREATOR: Parcelable.Creator<Order> = object : Parcelable.Creator<Order> {
      override fun createFromParcel(source: Parcel): Order = Order(source)
      override fun newArray(size: Int): Array<Order?> = arrayOfNulls(size)
    }
  }

  constructor(source: Parcel) : this(source.readString(), source.readString(), source.readLong(), source.readString(), ArrayList<OrderLine>().apply{ source.readList(this, OrderLine::class.java.classLoader) })

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel?, flags: Int) {
    dest?.writeString(id)
    dest?.writeString(code)
    dest?.writeLong(timestamp)
    dest?.writeString(comment)
    dest?.writeList(orderLines)
  }
}
