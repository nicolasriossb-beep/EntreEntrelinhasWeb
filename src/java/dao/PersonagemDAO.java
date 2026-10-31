/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Personagem;
import util.Conexao;

public class PersonagemDAO {

   
    public void criar(Personagem p) throws SQLException {
        String sql = """
            INSERT INTO personagens
            (per_str_nome, per_str_idade, per_str_aparencia,
             per_str_personalidade, per_str_historia, pro_int_id)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getIdade());
            stmt.setString(3, p.getAparencia());
            stmt.setString(4, p.getPersonalidade());
            stmt.setString(5, p.getHistoria());
            stmt.setInt(6, p.getProjetoId());

            stmt.executeUpdate();
        }
    }

    
    public List<Personagem> listarPorProjeto(int projetoId)
            throws SQLException {

        String sql = """
            SELECT *
            FROM personagens
            WHERE pro_int_id = ?
            ORDER BY per_str_nome ASC
            """;

        List<Personagem> lista = new ArrayList<>();

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

    
    public Personagem buscarPorId(int personagemId, int projetoId)
            throws SQLException {

        String sql = """
            SELECT *
            FROM personagens
            WHERE per_int_id = ? AND pro_int_id = ?
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, personagemId);
            stmt.setInt(2, projetoId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }

        return null;
    }

    
    public void atualizar(Personagem p) throws SQLException {
        String sql = """
            UPDATE personagens
            SET per_str_nome = ?,
                per_str_idade = ?,
                per_str_aparencia = ?,
                per_str_personalidade = ?,
                per_str_historia = ?
            WHERE per_int_id = ? AND pro_int_id = ?
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getIdade());
            stmt.setString(3, p.getAparencia());
            stmt.setString(4, p.getPersonalidade());
            stmt.setString(5, p.getHistoria());
            stmt.setInt(6, p.getId());
            stmt.setInt(7, p.getProjetoId());

            stmt.executeUpdate();
        }
    }

    // EXCLUIR PERSONAGEM
    public void excluir(int personagemId, int projetoId)
            throws SQLException {

        String sql = """
            DELETE FROM personagens
            WHERE per_int_id = ? AND pro_int_id = ?
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, personagemId);
            stmt.setInt(2, projetoId);

            stmt.executeUpdate();
        }
    }

   
    private Personagem mapear(ResultSet rs) throws SQLException {
        Personagem p = new Personagem();

        p.setId(rs.getInt("per_int_id"));
        p.setNome(rs.getString("per_str_nome"));
        p.setIdade(rs.getString("per_str_idade"));
        p.setAparencia(rs.getString("per_str_aparencia"));
        p.setPersonalidade(rs.getString("per_str_personalidade"));
        p.setHistoria(rs.getString("per_str_historia"));
        p.setProjetoId(rs.getInt("pro_int_id"));

        return p;
    }
}
