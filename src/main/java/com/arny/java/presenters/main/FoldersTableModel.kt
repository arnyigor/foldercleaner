package com.arny.java.presenters.main

import com.arny.java.data.models.CleanFolder

import javax.swing.table.AbstractTableModel
import java.util.ArrayList

internal class FoldersTableModel(var folders: ArrayList<CleanFolder>) : AbstractTableModel() {

    private val columnNames = arrayOf("Path", "Size")

    override fun getColumnName(column: Int): String {
        return columnNames[column]
    }

    override fun getRowCount(): Int {
        return folders.size
    }

    override fun getColumnCount(): Int {
        return columnNames.size
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any? {
        when (columnIndex) {
            0 -> return folders[rowIndex].path
            1 -> return folders[rowIndex].getFormatedSize()
        }
        return ""
    }

    fun getSelected(rows: IntArray): ArrayList<CleanFolder> {
        val selectedAsteroids = ArrayList<CleanFolder>()
        for (i in rows.indices) {
            selectedAsteroids.add(folders[i])
        }
        return selectedAsteroids
    }
}