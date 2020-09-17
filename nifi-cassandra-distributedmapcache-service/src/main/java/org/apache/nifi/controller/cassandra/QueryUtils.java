package org.apache.nifi.controller.cassandra;

public class QueryUtils {
    private QueryUtils() {}

    public static String createDeleteStatement(String keyField, String table) {
        return String.format("DELETE FROM %s WHERE %s = ?", table, keyField);
    }

    public static String createExistsQuery(String keyField, String table) {
        return String.format("SELECT COUNT(*) FROM %s WHERE %s = ?", table, keyField);
    }

    public static String createFetchQuery(String keyField, String valueField, String table) {
        return String.format("SELECT %s FROM %s WHERE %s = ?", valueField, table, keyField);
    }

    public static String createInsertStatement(String keyField, String valueField, String table, Integer ttl) {
        String retVal = String.format("INSERT INTO %s (%s, %s) VALUES(?, ?)", table, keyField, valueField);

        if (ttl != null) {
            retVal += String.format(" using ttl %d", ttl);
        }

        return retVal;
    }
}
