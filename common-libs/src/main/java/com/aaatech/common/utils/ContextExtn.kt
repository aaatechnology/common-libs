/*
 * Copyright (c) 2021 AAA Technology
 *
 * All rights reserved.
 *
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY
 * KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
 * PARTICULAR PURPOSE.
 */

package com.aaatech.common.utils

import android.content.Context
import android.graphics.drawable.Drawable

/**
 * @author Arun A
 */
fun Context.getDrawableByName(name: String): Drawable? {
    val resourceId = resources.getIdentifier(name, "drawable", packageName)
    return resources.getDrawable(resourceId)
}

/*@ColorInt
fun Context.primaryColor(): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
    return typedValue.data
}*/
