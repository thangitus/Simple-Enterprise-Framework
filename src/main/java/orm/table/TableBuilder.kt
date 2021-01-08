package orm.table

import orm.column.Column


class TableBuilder {
    val table = Table()

    fun setTableName(tableName: String): TableBuilder {
        table.tableName = tableName
        return this
    }

    fun setClassName(className: String): TableBuilder {
        table.className = className
        return this
    }

    fun addColumn(column: Column): TableBuilder {
        table.columnList.add(column)
        return this
    }

    fun build(): Table = table
}