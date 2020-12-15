package database;

import java.util.List;

import table.Table;

abstract class Database<T extends Table> {
    String schema, url;
    String user, password;
    List<T> tableList;
}
