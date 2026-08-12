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

            if (arquivo != null) {
                propriedades.load(arquivo);
            }

        } catch (IOException e) {
            throw new SQLException(
                "Erro ao ler o arquivo de configuração.", e
            );
        }

       

        String host = obterConfiguracao(
                "DB_HOST",
                propriedades.getProperty("host")
        );

        String port = obterConfiguracao(
                "DB_PORT",
                propriedades.getProperty("port")
        );

        String database = obterConfiguracao(
                "DB_DATABASE",
                propriedades.getProperty("database")
        );

        String usuario = obterConfiguracao(
                "DB_USER",
                propriedades.getProperty("usuario")
        );

        String senha = obterConfiguracao(
                "DB_PASSWORD",
                propriedades.getProperty("senha")
        );

        if (host == null || port == null || database == null
                || usuario == null || senha == null) {

            throw new SQLException(
                "Configuração do banco de dados incompleta."
            );
        }

        String url = "jdbc:postgresql://" + host + ":" + port + "/"
                + database
                + "?sslmode=require&channelBinding=require";

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

    private static String obterConfiguracao(
            String nomeVariavel,
            String valorLocal) {

        String valorAmbiente = System.getenv(nomeVariavel);

        if (valorAmbiente != null && !valorAmbiente.isBlank()) {
            return valorAmbiente;
        }

        return valorLocal;
    }
}