package com.bubbble.data.global.parsing

object ParsingRegex {
    const val JS_OBJECT_REGEX =
        "(\\{(?:(?>[^{}\"'\\/]+)|(?>\"(?:(?>[^\\\\\"]+)|\\\\.)*\")|(?>'(?:(?>[^\\\\']+)|\\\\.)*')|(?>\\/\\/.*\\n)|(?>\\/\\*.*?\\*\\/))*\\})"
    const val JS_ARRAY_REGEX = "\\[($JS_OBJECT_REGEX(,?))*\\]"
    const val JS_OBJECT_KEY_REGEX = "([a-z_]*)(\\:.*,?\\n)"
}