package orm

import com.mysql.cj.jdbc.result.ResultSetMetaData
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import org.apache.commons.lang3.text.WordUtils
import javax.lang.model.element.Modifier


class Column(resultSetMetaData: ResultSetMetaData, column: Int) {
    var className: String = resultSetMetaData.getColumnClassName(column)
    var columnName: String = resultSetMetaData.getColumnName(column)
    var isAutoIncrement: Boolean = resultSetMetaData.isAutoIncrement(column)
    var isNullable: Boolean = resultSetMetaData.isNullable(column) != 0
    var isCurrency: Boolean = resultSetMetaData.isCurrency(column)
    var isPrimaryKey: Boolean = resultSetMetaData.fields[column - 1].isPrimaryKey


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
        var fieldName: String = WordUtils.capitalizeFully(columnName.toLowerCase(), '_')
                .replace("_", "")
        fieldName = Character.toLowerCase(fieldName[0]) + fieldName.substring(1)
        val fieldSpecBuilder = FieldSpec.builder(typeName, fieldName, Modifier.PRIVATE)
        return fieldSpecBuilder.build()
    }

    fun createGetterMethod(): MethodSpec {
        val typeName = ClassName.get(getPackageName(), getSimpleClassName())
        val fieldName: String = WordUtils.capitalize(columnName.toLowerCase(), '_')
                .replace("_", "")
        val methodName = "get" + Character.toUpperCase(fieldName[0]) +
                         fieldName.substring(1)
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(typeName)
                .addStatement("return fieldName")
                .build()
    }

    fun createSetterMethod(): MethodSpec {
        val typeName = ClassName.get(getPackageName(), getSimpleClassName())
        val fieldName: String = WordUtils.capitalize(columnName.toLowerCase(), '_')
                .replace("_", "")
        val methodName = "set" + Character.toUpperCase(fieldName[0]) +
                         fieldName.substring(1)
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(typeName, fieldName)
                .addStatement("this.\$N = \$N", fieldName, fieldName)
                .build()

    }
}