package com.android.pestotask

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class TestObserver<T> : Observer<T> {
    private val latch = CountDownLatch(1)
    private val observedValues = mutableListOf<T>()

    override fun onChanged(value: T) {
        observedValues.add(value)
        latch.countDown()
    }

    fun awaitValue(): TestObserver<T> {
        latch.await(2, TimeUnit.SECONDS)
        return this
    }

    fun observedValues(): List<T> {
        return observedValues.toList()
    }
}

fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
    observeForever(it)
}