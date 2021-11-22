package com.Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*  This handles all the postgres Stuff
 *
 *
 */
public class PostgresHandler {

    // See if there exists a user with that user id
    public static String getName(String Input) {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            // Check if that user exists
            ResultSet rset = stmt.executeQuery(
                    "Select * from customer where IDCustomer =" + Input +" ;"
            );
            while (rset.next()) {
                return Input;
            }
            return "-1";
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }
}

