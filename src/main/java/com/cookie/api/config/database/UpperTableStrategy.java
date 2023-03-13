package com.cookie.api.config.database;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class UpperTableStrategy extends PhysicalNamingStrategyStandardImpl {
    private static final long serialVersionUID = 1L;
    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        //The table name all converted to uppercase
        String tableName = name.getText().toUpperCase();

        return name.toIdentifier(tableName);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        String str = name.getText();
        String regex = "([a-z]|[0-9])([A-Z]+)";

        String replacement = "$1_$2";
        str = str.replaceAll(regex, replacement).toUpperCase();

        return name.toIdentifier(str);
    }
}
