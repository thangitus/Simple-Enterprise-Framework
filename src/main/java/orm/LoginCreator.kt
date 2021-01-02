package orm

import java.sql.Connection

class LoginCreator(private val connection: Connection) {
    companion object {
        val CREATE_TABLE_USER = """
        CREATE TABLE IF NOT EXISTS users (
            user_id INT AUTO_INCREMENT PRIMARY KEY,
            username VARCHAR(30) NOT NULL,
            password VARCHAR(30) NOT NULL
        )
    """.trimIndent()
    }

    public fun createTableUser() {
        val statement = connection.createStatement()
        statement.execute(CREATE_TABLE_USER)
    }
}