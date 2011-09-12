package org.simapp.floor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author vusa
 */
public class Database {

    private static Database DATABASE = null;
    private Connection connection;

    private Database() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Class.forName("org.postgresql.Driver").newInstance();
        connection = DriverManager.getConnection(getDbUrl());
    }

    public static Database getInstance() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        if (DATABASE == null) {
            DATABASE = new Database();
        }
        return DATABASE;
    }

    public void execute(String sql) throws SQLException {
        System.out.println(sql);
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        stmt.close();
    }

    public ArrayList<HashMap<String, Object>> query(String sql) throws SQLException {
        System.out.println(sql);
        ArrayList<HashMap<String, Object>> vector = new ArrayList<HashMap<String, Object>>();
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = results.getMetaData();

        int numberCols = rsmd.getColumnCount();

        while (results.next()) {
            System.out.print(".");
            HashMap<String, Object> hash = new HashMap<String, Object>();
            for (int i = 1; i <= numberCols; i++) {
                hash.put(rsmd.getColumnLabel(i), results.getObject(i));
            }
            vector.add(hash);
        }

        stmt.close();
        return vector;
    }

    public void close() throws SQLException {
        DriverManager.getConnection(getDbUrl());
        connection.close();
    }

    private String getDbUrl() {
        return "jdbc:postgresql://localhost/simapp?user=postgres&password=postgres";
    }
}