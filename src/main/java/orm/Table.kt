package orm

import generator.Generatable
import java.io.File
import java.sql.Connection
import java.sql.ResultSet
import com.mysql.cj.jdbc.result.ResultSetMetaData
import com.squareup.javapoet.*
import org.apache.commons.lang3.text.WordUtils
import java.sql.Statement
import javax.lang.model.element.Modifier
import javax.persistence.*
import javax.persistence.Table

class Table(private val tableName: String, connection: Connection) : Generatable {
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
        generateEntities(directory)
        generatePackageDao(directory);
    }

    private fun generatePackageDao(directory: File) {
        generateDao(directory)
        generateEntityManagerProvider(directory)

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

    private fun generateDao(directory: File) {

    }

    private fun generateEntities(directory: File) {
        val fieldSpecs: MutableList<FieldSpec> = ArrayList()
        val methodSpecs: MutableList<MethodSpec> = ArrayList()
        columnList.forEach {
            fieldSpecs.add(it.toFieldSpec())
            methodSpecs.add(it.createGetterMethod())
            methodSpecs.add(it.createSetterMethod())
        }
        val className = WordUtils.capitalize(tableName, '_', ' ')
            .replace("_", "")
            .replace("", "")
        val typeSpecBuilder = TypeSpec.classBuilder(className)
        typeSpecBuilder.addAnnotation(Entity::class.java)
        val tableAnnotation = AnnotationSpec.builder(Table::class.java)
            .addMember("name", "\$S", tableName)
            .build()

        typeSpecBuilder.addAnnotation(tableAnnotation)
        typeSpecBuilder.addFields(fieldSpecs)
        typeSpecBuilder.addMethods(methodSpecs)
        JavaFile.builder("entity", typeSpecBuilder.build())
            .build().writeTo(directory)
    }
}