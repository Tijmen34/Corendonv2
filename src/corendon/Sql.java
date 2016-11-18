/*
 * Deze class zorgt ervoor dat er een verbinding wordt gemaakt met de database
 */
package corendon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static javafx.application.Application.launch;

/**
 *
 * @author Burak
 */
public class Sql {

    //connect met DB
    public static Connection DbConnector() {

        try {
            Connection conn = null;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/corendon", "root", "admin");
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);  
        }
        return null;
    }
    
    //launch de applicatie
    public static void main(String[] args) {
        launch(args);
    }
}
