package com.arny.java.data.utils

import kotlinx.coroutines.*
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.security.MessageDigest
import kotlin.coroutines.CoroutineContext
import kotlin.experimental.and
import kotlin.experimental.or

fun getSQLType(fieldType: String): String {
    val res = when {
        fieldType.equals("int", true) -> "INTEGER"
        fieldType.equals("integer", true) -> "INTEGER"
        fieldType.equals("float", true) -> "REAL"
        fieldType.equals("double", true) -> "REAL"
        fieldType.equals("string", true) -> "TEXT"
        fieldType.equals("char", true) -> "TEXT"
        fieldType.equals("byte", true) -> "TEXT"
        else -> "TEXT"
    }
    return res;
}

fun cutText(text: String, size: Int): String {
    val trim = text.trim()
    var res = ""
    if (Utility.empty(trim)) {
        return ""
    }
    if (trim.length > size) {
        res = trim.substring(0, size)
    }
    return res
}

/**
 * Универсальная функция окончаний
 * @param [count] число
 * @param [zero_other] слово с окончанием значения  [count] либо ноль,либо все остальные варианты включая от 11 до 19 (слов)
 * @param [one] слово с окончанием значения  [count]=1 (слово)
 * @param [two_four] слово с окончанием значения  [count]=2,3,4 (слова)
 */
fun getTermination(count: Int, zero_other: String, one: String, two_four: String, concat: Boolean = true): String {
    if (count % 100 in 11..19) {
        return if (concat) count.toString() + " " + zero_other else " $zero_other"
    }
    return when (count % 10) {
        1 -> if (concat) count.toString() + " " + one else one
        2, 3, 4 -> if (concat) count.toString() + " " + two_four else two_four
        else -> if (concat) count.toString() + " " + zero_other else zero_other
    }
}

/**
 * Extended function to check empty
 */
fun Any?.empty(): Boolean {
    return when {
        this == null -> true
        this is String && this == "null" -> true
        this is String -> this.isBlank()
        this is Iterable<*> -> this.asIterable().none()
        else -> false
    }
}

/**
 * Extended function to check empty and return default value
 */
fun <T> Any?.ifEmpty(default: T): T? {
    if (this.empty()) {
        return default
    }
    return this as? T
}

fun validPin(content: String, pin: Int): Boolean {
    return !content.empty() && content.parseInt() == pin
}

fun <T> filterTasks(tasks: List<T>, predicate: (T) -> Boolean): List<T> {
    return tasks.filter(predicate)
}

fun <T> find(list: List<T>, predicate: (T) -> Boolean): T? {
    return list.find(predicate)
}

fun <T, K> groupBy(list: List<T>, keySelector: (T) -> K): Map<K, List<T>> {
    return list.groupBy(keySelector)
}

fun <T> Array<T>.findPosition(item: T): Int {
    return this.indexOf(item)
}

fun <T> List<T>.findPosition(predicate: (T) -> Boolean): Int {
    val find = this.find(predicate)
    return this.indexOf(find)
}

fun <T> List<T>.findPosition(item: T): Int {
    return this.indexOf(item)
}

fun <T> HashMap<String, Any?>?.getOrNUll(key: String): T? {
    return this?.get(key) as? T
}

fun String?.parseLong(): Long? {
    return when {
        this == null -> null
        this.isBlank() -> null
        else -> {
            try {
                this.toLong()
            } catch (e: Exception) {
                null
            }
        }
    }
}

fun String?.parseDouble(): Double? {
    return when {
        this == null -> null
        this.isBlank() -> null
        else -> {
            try {
                this.toDouble()
            } catch (e: Exception) {
                null
            }
        }
    }
}

fun String?.parseInt(): Int? {
    return when {
        this == null -> null
        this.isBlank() -> null
        else -> {
            try {
                this.toInt()
            } catch (e: Exception) {
                null
            }
        }
    }
}

fun <T> launchAsync(block: suspend () -> T, onComplete: (T) -> Unit = {}, onError: (Throwable) -> Unit = {}, dispatcher: CoroutineDispatcher = Dispatchers.IO, context: CoroutineContext = Dispatchers.Main + SupervisorJob(), onCanceled: () -> Unit = {}): Job {
    val scope = CoroutineScope(context)
    return scope.launch {
        try {
            val result = withContext(dispatcher) { block.invoke() }
            onComplete.invoke(result)
        } catch (e: CancellationException) {
            println("canceled by user")
            onCanceled()
        } catch (e: Exception) {
            onError(e)
        }
    }
}

suspend fun <T> background(block: suspend () -> T): Deferred<T> {
    return GlobalScope.async(Dispatchers.IO) { block.invoke() }
}

suspend fun <T> launch(block: suspend () -> T, context: CoroutineContext = Dispatchers.Main): Job {
    return GlobalScope.launch(context) { block.invoke() }
}

fun getFileMD5(filename: String): String {
    val buffer = 8192
    val buf = ByteArray(buffer)
    var length: Int
    try {
        val fis = FileInputStream(filename)
        val bis = BufferedInputStream(fis)
        val md = MessageDigest.getInstance("MD5")
        while (true) {
            val read = bis.read(buf)
            if (read != -1) {
                length = read
                md.update(buf, 0, length)
            } else {
                break
            }
        }
        bis.close()
        val array = md.digest()
        val sb = StringBuilder()
        for (anArray in array) {
            sb.append(Integer.toHexString((anArray and 0xFF.toByte() or 0x100.toByte()).toInt()), 1, 3)
        }
        return sb.toString()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return "md5bad"
}





