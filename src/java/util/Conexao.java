/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author Ryzen7RTX3050
 */

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {

    private static final String URL =
    "jdbc:postgresql://localhost:5432/EntreEntrelinhasWeb";

    public static Connection conectar() throws SQLException {

        Properties propriedades = new Properties();

        try (InputStream arquivo = Conexao.class
                .getClassLoader()
                .getResourceAsStream("util/config.properties")) {

            if (arquivo == null) {
                throw new SQLException(
                    "Arquivo config.properties não encontrado."
                );
            }

            propriedades.load(arquivo);

        } catch (IOException e) {
            throw new SQLException(
                "Erro ao ler o arquivo de configuração.", e
            );
        }

        String usuario = propriedades.getProperty("usuario");
        String senha = propriedades.getProperty("senha");

        try {

           Class.forName(
    "org.postgresql.Driver"
                        );

            return DriverManager.getConnection(
                URL,
                usuario,
                senha
            );

        } catch (ClassNotFoundException e) {

            throw new SQLException(
                "Driver JDBC não encontrado.", e
            );
        }
    }
}