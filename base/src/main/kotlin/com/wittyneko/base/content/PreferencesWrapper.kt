
package com.wittyneko.base.content

import android.content.SharedPreferences

/**
 * SharedPreferences 包装类
 * Created by wittytutu on 17-3-29.
 * @author wittyneko
 * @since 2018/8/8
 */

abstract class PreferencesWrapper(preferences: SharedPreferences) : SharedPreferences by preferences

open class EditorWrapper(editor: SharedPreferences.Editor) : SharedPreferences.Editor by editor
