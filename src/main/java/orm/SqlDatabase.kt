package orm

import generator.Generatable
import org.apache.commons.io.FileUtils
import java.io.File
import java.sql.Connection

class SqlDatabase(connection: Connection) : Generatable {
    private val tableList: MutableList<Table>

    init {
        tableList = ArrayList()
        val metadata = connection.metaData
        val resultSet = metadata.getTables(connection.catalog, null, "%", null)
        while (resultSet.next()) {
            val tableName = resultSet.getString("TABLE_NAME")
            val table = Table(tableName, connection)
            tableList.add(table)
        }
    }


    override fun generate(directory: File) {
        //create directory for db first
        for (table in tableList) table.generate(directory)

        val baseDao = File("src\\main\\resources\\BaseDao.java")
        val dstFile = File(directory.absolutePath + "\\dao\\BaseDao.java")
        FileUtils.copyFile(baseDao, dstFile)
    }
}

