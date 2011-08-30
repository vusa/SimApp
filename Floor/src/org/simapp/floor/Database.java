package org.simapp.floor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.util.Hashtable;

import java.util.Vector;

/**
 *
 * @author vusa
 */
public class Database {

    private static Database DATABASE = null;
    private Connection connection;

    private Database() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
        connection = DriverManager.getConnection(getDbUrl());
    }
    
    public static Database getInstance() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        if(DATABASE==null){
            DATABASE = new Database();
        }
        return DATABASE;
    }

    public void query(String sql) throws SQLException {
        System.out.println(sql);
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        stmt.close();
    }

    public Vector<Hashtable> select(String sql) throws SQLException {
        System.out.println(sql);
        Vector<Hashtable> vector = new Vector<Hashtable>();
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = results.getMetaData();

        int numberCols = rsmd.getColumnCount();

        while (results.next()) {
            System.out.print(".");
            Hashtable hash = new Hashtable();
            for (int i = 1; i <= numberCols; i++) {
                hash.put(rsmd.getColumnLabel(i), results.getObject(i));
            }
            vector.add(hash);
        }

        stmt.close();
        return vector;
    }

    public void close() throws SQLException {
        DriverManager.getConnection(getDbUrl() + ";shutdown=true");
        connection.close();
    }

    private String getDbUrl() {
        return "jdbc:derby:laamu;create=true;";
    }
}