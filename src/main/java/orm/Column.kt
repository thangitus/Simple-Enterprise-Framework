package orm

import com.mysql.cj.jdbc.result.ResultSetMetaData
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import org.apache.commons.lang3.text.WordUtils
import org.jetbrains.annotations.NotNull
import javax.lang.model.element.Modifier
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


class Column(resultSetMetaData: ResultSetMetaData, column: Int) {
    var className: String = resultSetMetaData.getColumnClassName(column)
    var columnName: String = resultSetMetaData.getColumnName(column)
    var isAutoIncrement: Boolean = resultSetMetaData.isAutoIncrement(column)
    var isNullable: Boolean = resultSetMetaData.isNullable(column) != 0
    var isPrimaryKey: Boolean = resultSetMetaData.fields[column - 1].isPrimaryKey
    var fieldName: String

    init {
        fieldName = Character.toLowerCase(columnName[0]) + columnName.substring(1)
        fieldName = fieldName.replace("_", "")
        fieldName = fieldName.replace(" ", "")
    }

    private fun getSimpleClassName(): String {
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

        if (isAutoIncrement)
            fieldSpecBuilder.addAnnotation(AnnotationSpec.builder(NotNull::class.java).build())

        if (!isNullable)
            fieldSpecBuilder.addAnnotation(AnnotationSpec.builder(GeneratedValue::class.java)
                    .addMember("strategy", "\$T.\$L", GenerationType::class.java, GenerationType.AUTO.name).build())

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
}