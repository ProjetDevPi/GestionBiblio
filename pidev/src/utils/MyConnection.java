/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MyConnection {

    private static MyConnection instance;

    private String url = "jdbc:mysql://localhost:3306/pidev";
    private String login = "root";
    private String mdp = "";
    private Connection cnx;

    MyConnection() {
        try {
            cnx = DriverManager.getConnection(url, login, mdp);
            System.out.println("Connexion etablie!");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public static MyConnection getInstance(){
        if (instance == null) {
            instance = new MyConnection();
        }
        return instance ;
    }

    public Connection getCnx() {
        return cnx;
    }

    Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
