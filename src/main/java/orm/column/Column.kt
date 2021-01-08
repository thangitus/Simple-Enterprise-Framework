package orm.column

import com.mysql.cj.jdbc.result.ResultSetMetaData
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import org.jetbrains.annotations.NotNull
import java.lang.StringBuilder
import javax.lang.model.element.Modifier
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


class Column() {
    lateinit var className: String
    lateinit var columnName: String
    var isAutoIncrement: Boolean = false
    var isNullable: Boolean = false
    var isPrimaryKey: Boolean = false
    lateinit var fieldName: String

    constructor(resultSetMetaData: ResultSetMetaData, column: Int) : this() {
        className = resultSetMetaData.getColumnClassName(column)
        columnName = resultSetMetaData.getColumnName(column)
        isAutoIncrement = resultSetMetaData.isAutoIncrement(column)
        isNullable = resultSetMetaData.isNullable(column) != 0
        isPrimaryKey = resultSetMetaData.fields[column - 1].isPrimaryKey
        fieldName = Character.toLowerCase(columnName[0]) + columnName.substring(1)
        fieldName = fieldName.replace("_", "")
        fieldName = fieldName.replace(" ", "")
    }

    fun getSimpleClassName(): String {
        val tokens = className.split(".").toList()
        return tokens.last()
    }

    private fun getPackageName(): String {
        val tokens = className.split(".").toList()
        var packageName = ""
        for (i in 0..tokens.size - 2) {
            if (i != 0)
                packageName += "."
            packageName += tokens[i]
        }
        return packageName
    }

    fun toFieldSpec(): FieldSpec {
        val typeName = ClassName.get(getPackageName(), getSimpleClassName())
        val fieldSpecBuilder = FieldSpec.builder(typeName, fieldName, Modifier.PRIVATE)
        if (isPrimaryKey)
            fieldSpecBuilder.addAnnotation(Id::class.java)

        val annotationColumnName = AnnotationSpec.builder(Column::class.java)
            .addMember("name", "\$S", columnName)
            .build()

        if (!isNullable)
            fieldSpecBuilder.addAnnotation(AnnotationSpec.builder(NotNull::class.java).build())

        if (isAutoIncrement)
            fieldSpecBuilder.addAnnotation(
                AnnotationSpec.builder(GeneratedValue::class.java)
                    .addMember("strategy", "\$T.\$L", GenerationType::class.java, GenerationType.AUTO.name).build()
            )

        fieldSpecBuilder.addAnnotation(annotationColumnName)

        return fieldSpecBuilder.build()
    }

    fun createGetterMethod(): MethodSpec {
        val typeName = ClassName.get(getPackageName(), getSimpleClassName())
        val methodName = "get" + Character.toUpperCase(fieldName[0]) +
                fieldName.substring(1)
        return MethodSpec.methodBuilder(methodName)
            .addModifiers(Modifier.PUBLIC)
            .returns(typeName)
            .addStatement("return $fieldName")
            .build()
    }

    fun createSetterMethod(): MethodSpec {
        val typeName = ClassName.get(getPackageName(), getSimpleClassName())
        val methodName = "set" + Character.toUpperCase(fieldName[0]) +
                fieldName.substring(1)
        return MethodSpec.methodBuilder(methodName)
            .addModifiers(Modifier.PUBLIC)
            .addParameter(typeName, fieldName)
            .addStatement("this.\$N = \$N", fieldName, fieldName)
            .build()
    }

    fun toSql(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(columnName)
        stringBuilder.append(" ${TypeMapping.javaTypeToSqlType(className)}")
        if (isAutoIncrement)
            stringBuilder.append(" AUTO_INCREMENT")
        if (isPrimaryKey)
            stringBuilder.append(" PRIMARY KEY")
        if (!isNullable)
            stringBuilder.append(" NOT NULL")
        return stringBuilder.toString()
    }
}

class TypeMapping {
    companion object {
        fun javaTypeToSqlType(javaType: String): String {
            return when (javaType) {
                "String" -> "VARCHAR(50)"
                "Integer" -> "INT"
                "Boolean" -> "BIT"
                "Float" -> "FLOAT"
                "Double" -> "DOUBLE"
                "byte[]" -> "BINARY"
                else -> ""
            }
        }
    }
}