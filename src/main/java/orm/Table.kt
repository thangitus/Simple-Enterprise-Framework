package orm

import generator.Generatable
import java.io.File
import java.sql.Connection
import java.sql.ResultSet
import com.mysql.cj.jdbc.result.ResultSetMetaData
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import java.sql.Statement

class Table(tableName: String, connection: Connection) : Generatable {
    private val columnList: MutableList<Column>

    init {
        columnList = ArrayList()
        val statement: Statement = connection.createStatement()
        val results: ResultSet = statement.executeQuery("SELECT * FROM $tableName")

        val resultSetMetaData: ResultSetMetaData = results.metaData as ResultSetMetaData
        val columnCount: Int = resultSetMetaData.columnCount

        for (i in 1..columnCount) {
            val column = Column(resultSetMetaData, i)
            columnList.add(column)
        }
    }

    override fun generate(directory: File) {
        val fieldSpecs: MutableList<FieldSpec> = ArrayList()
        val methodSpecs: MutableList<MethodSpec> = ArrayList()
        columnList.forEach {
            fieldSpecs.add(it.toFieldSpec())
            methodSpecs.add(it.createGetterMethod())
            methodSpecs.add(it.createSetterMethod())
        }
    }
}