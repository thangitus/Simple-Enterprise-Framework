package orm

import com.squareup.javapoet.*
import generator.Generatable
import org.apache.commons.io.FileUtils
import java.io.File
import java.sql.Connection
import javax.lang.model.element.Modifier
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence

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
        for (table in tableList)
            table.generate(directory)

        generateBaseDao(directory)
        generateEntityManagerProvider(directory)

    }

    private fun generateBaseDao(directory: File) {
        val baseDao = File("src\\main\\resources\\BaseDao.java")
        val dstFile = File(directory.absolutePath + "\\dao\\BaseDao.java")
        FileUtils.copyFile(baseDao, dstFile)
    }

    private fun generateEntityManagerProvider(directory: File) {
        val persistenceFieldBuilder = FieldSpec.builder(ClassName.get(String::class.java), "PERSISTENCE_UNIT_NAME")
        persistenceFieldBuilder.addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
        persistenceFieldBuilder.initializer("\$S", "persistence")

        val instanceFieldBuilder = FieldSpec.builder(ClassName.get(EntityManagerFactory::class.java), "INSTANCE")
        instanceFieldBuilder.addModifiers(Modifier.PRIVATE, Modifier.STATIC)

        val constructor = MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PRIVATE).build()

        val getInstance = MethodSpec.methodBuilder("createEntityManager")
            .returns(EntityManager::class.java)
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .beginControlFlow("if (INSTANCE == null)")
            .addStatement("INSTANCE = \$T.createEntityManagerFactory(PERSISTENCE_UNIT_NAME)", Persistence::class.java)
            .endControlFlow()
            .addStatement("return INSTANCE.createEntityManager()")
            .build()


        val typeSpecBuilder = TypeSpec.classBuilder("EntityManagerProvider")
        typeSpecBuilder.addModifiers(Modifier.PUBLIC)
        typeSpecBuilder.addField(persistenceFieldBuilder.build())
        typeSpecBuilder.addField(instanceFieldBuilder.build())
        typeSpecBuilder.addMethod(constructor)
        typeSpecBuilder.addMethod(getInstance)

        JavaFile.builder("dao", typeSpecBuilder.build())
            .build().writeTo(directory)
    }

}

