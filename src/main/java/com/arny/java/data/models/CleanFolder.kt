package com.arny.java.data.models

import com.arny.java.data.utils.FileUtils

data class CleanFolder(val id: Long = 0, val path: String? = null, val size: Long = 0) {
    fun getFormatedSize(): String {
        return FileUtils.formatFileSize(size)
    }
}