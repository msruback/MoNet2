package com.mattrubacky.monet2

import org.robolectric.internal.bytecode.RobolectricInternals.getClassLoader
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import kotlin.reflect.KClass

@Throws(IOException::class)
fun getCSV(path: String,test:KClass<*>): Array<String> {
    val fis = test.java.classLoader!!.getResourceAsStream(path)
    val `in` = InputStreamReader(fis)
    val br = BufferedReader(`in`)
    val stringBuilder = StringBuilder()

    for(line in br.lines()){
        stringBuilder.append(line)
    }
    return stringBuilder.toString().split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
}

@Throws(IOException::class)
fun getJSON(path:String,test:KClass<*>): String {
    val fis = test.java.classLoader!!.getResourceAsStream(path)
    val `in` = InputStreamReader(fis)
    val br = BufferedReader(`in`)
    val stringBuilder = StringBuilder()

    for(line in br.lines()){
        stringBuilder.append(line)
    }
    return stringBuilder.toString()
}