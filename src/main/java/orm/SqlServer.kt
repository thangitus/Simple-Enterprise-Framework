package orm

import java.sql.*


class SqlServer(val user: String, val password: String, val baseUrl: String) {
    companion object {
        const val className = "com.mysql.cj.jdbc.Driver"
    }

    fun connectToServer(): List<String> {
        val databases: MutableList<String> = ArrayList()

        try {
            Class.forName(className)
            val connection: Connection = DriverManager.getConnection(baseUrl, user, password)
            val metadata: DatabaseMetaData = connection.metaData
            val resultSet: ResultSet = metadata.catalogs
            while (resultSet.next()) {
                val dbName = resultSet.getString("TABLE_CAT")
                databases.add(dbName)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return databases
    }

    fun connectToDatabase(schema: String): SqlDatabase {
        return SqlDatabase(this, schema)
    }

}