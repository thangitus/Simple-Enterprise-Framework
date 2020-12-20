package orm

import java.sql.*


class SqlServer(private val user: String, private val password: String) {
    companion object {
        const val className = "com.mysql.cj.jdbc.Drive"
        const val baseUrl = "jdbc:mysql://localhost:3306"

    }

    fun connectToServer(): List<String> {
        val databases: MutableList<String> = ArrayList()

        try {
            Class.forName("com.mysql.cj.jdbc.Driver")
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

    fun connectToDatabase(schema: String): SqlDatabase? {
        try {
            val connection = DriverManager.getConnection("$baseUrl/$schema", user, password)
            return SqlDatabase(connection)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }

}