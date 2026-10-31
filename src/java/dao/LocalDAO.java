
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Local;
import util.Conexao;

public class LocalDAO {

    public void criar(Local l) throws SQLException {
        String sql = """
            INSERT INTO locais
            (loc_str_nome, loc_str_tipo, loc_str_atmosfera,
             loc_str_acontecimentos, loc_str_descricao, pro_int_id)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, l.getNome());
            stmt.setString(2, l.getTipo());
            stmt.setString(3, l.getAtmosfera());
            stmt.setString(4, l.getAcontecimentos());
            stmt.setString(5, l.getDescricao());
            stmt.setInt(6, l.getProjetoId());

            stmt.executeUpdate();
        }
    }

    public List<Local> listarPorProjeto(int projetoId)
            throws SQLException {

        String sql = """
            SELECT *
            FROM locais
            WHERE pro_int_id = ?
            ORDER BY loc_str_nome ASC
            """;

        List<Local> lista = new ArrayList<>();

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

    public Local buscarPorId(int localId, int projetoId)
            throws SQLException {

        String sql = """
            SELECT *
            FROM locais
            WHERE loc_int_id = ? AND pro_int_id = ?
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, localId);
            stmt.setInt(2, projetoId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }

        return null;
    }

    public void atualizar(Local l) throws SQLException {
        String sql = """
            UPDATE locais
            SET loc_str_nome = ?,
                loc_str_tipo = ?,
                loc_str_atmosfera = ?,
                loc_str_acontecimentos = ?,
                loc_str_descricao = ?
            WHERE loc_int_id = ? AND pro_int_id = ?
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, l.getNome());
            stmt.setString(2, l.getTipo());
            stmt.setString(3, l.getAtmosfera());
            stmt.setString(4, l.getAcontecimentos());
            stmt.setString(5, l.getDescricao());
            stmt.setInt(6, l.getId());
            stmt.setInt(7, l.getProjetoId());

            stmt.executeUpdate();
        }
    }

    public void excluir(int localId, int projetoId)
            throws SQLException {

        String sql = """
            DELETE FROM locais
            WHERE loc_int_id = ? AND pro_int_id = ?
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, localId);
            stmt.setInt(2, projetoId);

            stmt.executeUpdate();
        }
    }

    private Local mapear(ResultSet rs) throws SQLException {
        Local l = new Local();

        l.setId(rs.getInt("loc_int_id"));
        l.setNome(rs.getString("loc_str_nome"));
        l.setTipo(rs.getString("loc_str_tipo"));
        l.setAtmosfera(rs.getString("loc_str_atmosfera"));
        l.setAcontecimentos(rs.getString("loc_str_acontecimentos"));
        l.setDescricao(rs.getString("loc_str_descricao"));
        l.setProjetoId(rs.getInt("pro_int_id"));

        return l;
    }
}

