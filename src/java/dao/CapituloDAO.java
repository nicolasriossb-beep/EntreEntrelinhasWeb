/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.Capitulo;
import util.Conexao;

public class CapituloDAO {

    public void criar(Capitulo c) throws SQLException {
        String sql = """
            INSERT INTO capitulos
            (cap_int_numero, cap_str_titulo, cap_str_texto,
             cap_str_json, cap_str_html,
             cap_int_quantpalavras, cap_dta_ultimaedicao,
             pro_int_id, cap_bool_publico)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, c.getNumero());
            stmt.setString(2, c.getTitulo());
            stmt.setString(3, c.getTexto());
            stmt.setString(4, c.getJson());
            stmt.setString(5, c.getHtml());
            stmt.setInt(6, contarPalavras(c.getTexto()));
            definirData(stmt, 7, c.getDataUltimaEdicao());
            stmt.setInt(8, c.getProjetoId());
            stmt.setBoolean(9, c.isPublico());

            stmt.executeUpdate();
        }
    }

    public List<Capitulo> listarPorProjeto(int projetoId)
            throws SQLException {
        String sql = """
            SELECT *
            FROM capitulos
            WHERE pro_int_id = ?
            ORDER BY cap_int_numero ASC
            """;

        List<Capitulo> lista = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projetoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }

        return lista;
    }

    public Capitulo buscarPorId(int capituloId, int projetoId)
            throws SQLException {
        String sql = """
            SELECT *
            FROM capitulos
            WHERE cap_int_id = ? AND pro_int_id = ?
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, capituloId);
            stmt.setInt(2, projetoId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }

        return null;
    }

    public void atualizar(Capitulo c) throws SQLException {
        String sql = """
            UPDATE capitulos
            SET cap_int_numero = ?,
                cap_str_titulo = ?,
                cap_str_texto = ?,
                cap_str_json = ?,
                cap_str_html = ?,
                cap_int_quantpalavras = ?,
                cap_dta_ultimaedicao = ?,
                cap_bool_publico = ?
            WHERE cap_int_id = ? AND pro_int_id = ?
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, c.getNumero());
            stmt.setString(2, c.getTitulo());
            stmt.setString(3, c.getTexto());
            stmt.setString(4, c.getJson());
            stmt.setString(5, c.getHtml());
            stmt.setInt(6, contarPalavras(c.getTexto()));
            definirData(stmt, 7, c.getDataUltimaEdicao());
            stmt.setBoolean(8, c.isPublico());
            stmt.setInt(9, c.getId());
            stmt.setInt(10, c.getProjetoId());

            stmt.executeUpdate();
        }
    }

    public void excluir(int capituloId, int projetoId)
            throws SQLException {
        String sql = """
            DELETE FROM capitulos
            WHERE cap_int_id = ? AND pro_int_id = ?
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, capituloId);
            stmt.setInt(2, projetoId);
            stmt.executeUpdate();
        }
    }

    private void definirData(PreparedStatement stmt, int indice,
            java.time.LocalDateTime data) throws SQLException {
        if (data == null) {
            stmt.setNull(indice, Types.TIMESTAMP);
        } else {
            stmt.setTimestamp(indice, Timestamp.valueOf(data));
        }
    }

    private Capitulo mapear(ResultSet rs) throws SQLException {
        Capitulo c = new Capitulo();

        c.setId(rs.getInt("cap_int_id"));
        c.setNumero(rs.getInt("cap_int_numero"));
        c.setTitulo(rs.getString("cap_str_titulo"));
        c.setTexto(rs.getString("cap_str_texto"));
        c.setJson(rs.getString("cap_str_json"));
        c.setHtml(rs.getString("cap_str_html"));

        int palavras = rs.getInt("cap_int_quantpalavras");
        c.setQuantidadePalavras(
            rs.wasNull() ? null : palavras
        );

        Timestamp data = rs.getTimestamp("cap_dta_ultimaedicao");
        if (data != null) {
            c.setDataUltimaEdicao(data.toLocalDateTime());
        }

        c.setProjetoId(rs.getInt("pro_int_id"));
        c.setPublico(rs.getBoolean("cap_bool_publico"));

        return c;
    }

    private int contarPalavras(String texto) {
        if (texto == null || texto.isBlank()) {
            return 0;
        }

        return texto.trim().split("\\s+").length;
    }
}
