package com.demo.pins.utils.parser

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class BooleanFromInt

class BooleanJsonAdapter {
    @ToJson fun toJson(@BooleanFromInt boolean: Boolean): String {
        return if (boolean) "1" else "0"
    }

    @FromJson @BooleanFromInt fun fromJson(boolean: Int): Boolean {
        return boolean == 0
    }
}