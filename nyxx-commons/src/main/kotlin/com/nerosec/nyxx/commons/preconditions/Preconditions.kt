package com.nerosec.nyxx.commons.preconditions

import com.nerosec.nyxx.commons.exceptions.ArgumentException
import com.nerosec.nyxx.commons.exceptions.StateException

object Preconditions {

    fun require(condition: Boolean, exception: Exception) {
        if (!condition) throw exception
    }

    fun requireArgument(condition: Boolean, message: (() -> String?)? = null) = require(condition, ArgumentException(message?.invoke()))

    fun requireState(condition: Boolean, message: (() -> String?)? = null) = require(condition, StateException(message?.invoke()))
}