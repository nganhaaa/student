package com.example.bai1

/**
 * A wrapper for data that is exposed via a LiveData that represents an event.
 */
open class StateEvent<out T>(private val payload: T) {

    private var handled = false

    /**
     * Returns the payload and prevents its use again.
     */
    fun fetchPayload(): T? {
        return if (handled) {
            null
        } else {
            handled = true
            payload
        }
    }

    /**
     * Returns the payload, even if it's already been handled.
     */
    fun checkPayload(): T = payload
}
