package orm.column

class ColumnBuilder {
    private val column = Column()

    fun setClassName(className: String): ColumnBuilder {
        column.className = className
        return this
    }

    fun setColumnName(columnName: String): ColumnBuilder {
        column.columnName = columnName
        return this
    }

    fun setAutoIncrement(autoIncrement: Boolean): ColumnBuilder {
        column.isAutoIncrement = autoIncrement
        return this
    }

    fun setNullable(isNullable: Boolean): ColumnBuilder {
        column.isNullable = isNullable
        return this
    }

    fun setIsPrimaryKey(isPrimaryKey: Boolean): ColumnBuilder {
        column.isPrimaryKey = isPrimaryKey
        return this
    }

    fun setFieldName(fieldName: String): ColumnBuilder {
        column.fieldName = fieldName
        return this
    }

    fun build(): Column {
        return column
    }
}