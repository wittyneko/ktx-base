package com.wittyneko.base.extenstions

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.IdRes
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import com.wittyneko.base.R
import org.jetbrains.anko.dip
import org.jetbrains.anko.findOptional

val View.layoutInflater get() = LayoutInflater.from(this.context)

fun View.findTagView(@IdRes id: Int, block: (view: View?) -> Unit) {

    val view = findTagView<View>(id)
    block(view)
}

@Suppress("UNCHECKED_CAST")
fun <T : View> View.findTagView(@IdRes id: Int): T? {

    val tagId = R.id.tag_views
    val array = getTag(tagId) as? SparseArray<View> ?: run {
        val views = SparseArray<View>()
        setTag(tagId, views)
        views
    }

    return array.get(id) as? T ?: run {
        val view = findOptional<View>(id)
        array.put(id, view)
        view as? T
    }
}

inline val View.dividerHeight get() = context.dividerHeight

inline val View.dividerWidth get() = context.dividerWidth

val Context.dividerHeight
    get() = listDivider?.intrinsicHeight ?: dip(1)

val Context.dividerWidth get() = listDivider?.intrinsicWidth ?: dip(1)

val Context.listDivider
    get() = run {

        var listDivider: Drawable? = null
        val dividerAttrs = intArrayOf(android.R.attr.listDivider)
        obtainStyledAttributes(dividerAttrs).apply {
            @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
            listDivider = getDrawable(0)
        }.recycle()
        listDivider
    }