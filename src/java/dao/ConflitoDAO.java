package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Conflito;
import util.Conexao;

public class ConflitoDAO {

    public void criar(Conflito conflito) throws SQLException {
        String sql = """
            INSERT INTO conflitos
            (con_str_nome, con_str_escala, con_str_lados,
             con_str_motivacao, con_str_descricao, pro_int_id)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, conflito.getNome());
            stmt.setString(2, conflito.getEscala());
            stmt.setString(3, conflito.getLados());
            stmt.setString(4, conflito.getMotivacao());
            stmt.setString(5, conflito.getDescricao());
            stmt.setInt(6, conflito.getProjetoId());

            stmt.executeUpdate();
        }
    }

    public List<Conflito> listarPorProjeto(int projetoId) throws SQLException {
        List<Conflito> conflitos = new ArrayList<>();

        String sql = """
            SELECT * FROM conflitos
            WHERE pro_int_id = ?
            ORDER BY con_int_id
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projetoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    conflitos.add(mapear(rs));
                }
            }
        }

        return conflitos;
    }

    public Conflito buscarPorId(int conflitoId, int projetoId)
            throws SQLException {

        String sql = """
            SELECT * FROM conflitos
            WHERE con_int_id = ? AND pro_int_id = ?
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, conflitoId);
            stmt.setInt(2, projetoId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }

        return null;
    }

    public void atualizar(Conflito conflito) throws SQLException {
        String sql = """
            UPDATE conflitos
            SET con_str_nome = ?,
                con_str_escala = ?,
                con_str_lados = ?,
                con_str_motivacao = ?,
                con_str_descricao = ?
            WHERE con_int_id = ? AND pro_int_id = ?
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, conflito.getNome());
            stmt.setString(2, conflito.getEscala());
            stmt.setString(3, conflito.getLados());
            stmt.setString(4, conflito.getMotivacao());
            stmt.setString(5, conflito.getDescricao());
            stmt.setInt(6, conflito.getId());
            stmt.setInt(7, conflito.getProjetoId());

            stmt.executeUpdate();
        }
    }

    public void excluir(int conflitoId, int projetoId) throws SQLException {
        String sql = """
            DELETE FROM conflitos
            WHERE con_int_id = ? AND pro_int_id = ?
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, conflitoId);
            stmt.setInt(2, projetoId);

            stmt.executeUpdate();
        }
    }

    private Conflito mapear(ResultSet rs) throws SQLException {
        Conflito conflito = new Conflito();

        conflito.setId(rs.getInt("con_int_id"));
        conflito.setNome(rs.getString("con_str_nome"));
        conflito.setEscala(rs.getString("con_str_escala"));
        conflito.setLados(rs.getString("con_str_lados"));
        conflito.setMotivacao(rs.getString("con_str_motivacao"));
        conflito.setDescricao(rs.getString("con_str_descricao"));
        conflito.setProjetoId(rs.getInt("pro_int_id"));

        return conflito;
    }
}
