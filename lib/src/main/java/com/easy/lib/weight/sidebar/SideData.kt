package com.easy.lib.weight.sidebar

import java.util.*

/**
 * @author too young
 * @date  2020/4/11 10:40
 */
data class SideData(
    val name: String,
    var firstLetter: String = "",
    var pinyin: String = "",
    val sideType: SideType = SideType.CONTENT
) :
    Comparable<SideData> {
    init {
        pinyin = Cn2Spell.getPinYin(name) // 根据姓名获取拼音
        firstLetter = pinyin.substring(0, 1).toUpperCase(Locale.getDefault()) // 获取拼音首字母并转成大写
        if (!firstLetter.matches(Regex("[A-Z]"))) { // 如果不在A-Z中则默认为“#”
            firstLetter = "#"
        }
    }


    override fun compareTo(other: SideData): Int {
        return if (firstLetter == "#" && other.firstLetter != "#") {
            1
        } else if (firstLetter != "#" && other.firstLetter == "#") {
            -1
        } else {
            pinyin.compareTo(other.pinyin, ignoreCase = true)
        }
    }

}