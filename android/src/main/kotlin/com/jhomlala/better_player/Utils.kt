package com.jhomlala.better_player

import android.graphics.Rect

inline fun <T : Any, R> ifLet(vararg elements: T?, closure: (List<T>) -> R): R? {
    if (elements.all { it != null }) {
        return closure(elements.filterNotNull())
    }

    return null
}

operator fun Rect.times(multiplier: Float): Rect {
    return Rect(
        (left * multiplier).toInt(),
        (top * multiplier).toInt(),
        (right * multiplier).toInt(),
        (bottom * multiplier).toInt(),
    )
}