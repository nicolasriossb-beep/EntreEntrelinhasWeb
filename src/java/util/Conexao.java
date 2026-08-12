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

        String host = propriedades.getProperty("host");
        String port = propriedades.getProperty("port");
        String database = propriedades.getProperty("database");
        String usuario = propriedades.getProperty("usuario");
        String senha = propriedades.getProperty("senha");

        String url = "jdbc:postgresql://" + host + ":" + port + "/"
                + database + "?sslmode=require&channelBinding=require";

        try {

            Class.forName("org.postgresql.Driver");

            return DriverManager.getConnection(
                url,
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