package com.guilhermemagro.myplayground.extensions

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

fun Modifier.visibleIf(boolean: Boolean): Modifier {
    return this.then(alpha(if (boolean) 1F else 0F))
}
