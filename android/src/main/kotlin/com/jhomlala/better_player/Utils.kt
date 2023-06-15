package com.jhomlala.better_player

inline fun <T: Any, R> ifLet(vararg elements: T?, closure: (List<T>) -> R): R? {
    if (elements.all { it != null }) {
        return closure(elements.filterNotNull())
    }

    return null
}