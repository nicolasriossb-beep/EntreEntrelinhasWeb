/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author Ryzen7RTX3050
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    
    private static final String URL =
        "jdbc:sqlserver://THALES\\SQLEXPRESS;databaseName=EntreEntrelinhasWeb;encrypt=true;trustServerCertificate=true";

    private static final String USUARIO = "sa";

    private static final String SENHA = "";

    public static Connection conectar() throws SQLException {
        
        try {
            
           Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        return DriverManager.getConnection(URL, USUARIO, SENHA);
        
        } catch (ClassNotFoundException e) {
        throw new SQLException("Driver JDBC não encontrado.", e);
        }

    }
    
}
