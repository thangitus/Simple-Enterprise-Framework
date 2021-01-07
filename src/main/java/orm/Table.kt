package orm

import generator.Generatable
import java.io.File
import java.sql.Connection
import java.sql.ResultSet
import java.io.Serializable
import com.mysql.cj.jdbc.result.ResultSetMetaData
import com.squareup.javapoet.*
import org.apache.commons.lang3.text.WordUtils
import java.sql.Statement
import javax.lang.model.element.Modifier
import javax.persistence.*
import javax.persistence.Table

class Table(val tableName: String, connection: Connection) : Generatable {
    val className: String
    val columnList: MutableList<Column>

    init {
        className = WordUtils.capitalize(tableName, '_', ' ')
            .replace("_", "")
            .replace("", "")

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
        generateEntity(directory)
        this.generateDao(directory);
    }

    private fun generateDao(directory: File) {
        val typeSpecBuilder = TypeSpec.classBuilder(className + "Dao")

        typeSpecBuilder.superclass(
            ParameterizedTypeName.get(
                ClassName.get("dao", "BaseDao"),
                ClassName.get("entity", className),
                TypeVariableName.get(getSimpleClassNamePrimaryKey())
            )
        )

        val returnType = ParameterizedTypeName.get(
            ClassName.get(Class::class.java),
            TypeVariableName.get(className)
        )
        val getClazzMethod = MethodSpec.methodBuilder("getClazz")
            .addAnnotation(Override::class.java)
            .addModifiers(Modifier.PROTECTED)
            .returns(returnType)
            .addStatement("return \$L.class", className)
            .build()

        typeSpecBuilder.addMethod(getClazzMethod)
        JavaFile.builder("dao", typeSpecBuilder.build())
            .build().writeTo(directory)
    }


    private fun generateEntity(directory: File) {
        val fieldSpecs: MutableList<FieldSpec> = ArrayList()
        val methodSpecs: MutableList<MethodSpec> = ArrayList()
        columnList.forEach {
            fieldSpecs.add(it.toFieldSpec())
            methodSpecs.add(it.createGetterMethod())
            methodSpecs.add(it.createSetterMethod())
        }

        val typeSpecBuilder = TypeSpec.classBuilder(className)
            .addModifiers(Modifier.PUBLIC).addSuperinterface(Serializable::class.java)
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

    private fun getSimpleClassNamePrimaryKey(): String {
        columnList.forEach {
            if (it.isPrimaryKey)
                return it.getSimpleClassName()
        }
        return ""
    }
}