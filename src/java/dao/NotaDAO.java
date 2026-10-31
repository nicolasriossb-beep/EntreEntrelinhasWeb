
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Nota;
import util.Conexao;

public class NotaDAO {

    public void criar(Nota nota) throws SQLException {

        String sql = """
            INSERT INTO notas
            (not_str_titulo, not_str_conteudo,
             not_dta_ultimaedicao, pro_int_id)
            VALUES (?, ?, CURRENT_TIMESTAMP, ?)
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nota.getTitulo());
            stmt.setString(2, nota.getConteudo());
            stmt.setInt(3, nota.getProjetoId());

            stmt.executeUpdate();
        }
    }

    public List<Nota> listarPorProjeto(int projetoId)
            throws SQLException {

        List<Nota> notas = new ArrayList<>();

        String sql = """
            SELECT *
            FROM notas
            WHERE pro_int_id = ?
            ORDER BY not_dta_ultimaedicao DESC
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projetoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    notas.add(mapear(rs));
                }
            }
        }

        return notas;
    }

    public Nota buscarPorId(int notaId, int projetoId)
            throws SQLException {

        String sql = """
            SELECT *
            FROM notas
            WHERE not_int_id = ?
            AND pro_int_id = ?
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, notaId);
            stmt.setInt(2, projetoId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }

        return null;
    }

    public void atualizar(Nota nota) throws SQLException {

        String sql = """
            UPDATE notas
            SET not_str_titulo = ?,
                not_str_conteudo = ?,
                not_dta_ultimaedicao = CURRENT_TIMESTAMP
            WHERE not_int_id = ?
            AND pro_int_id = ?
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nota.getTitulo());
            stmt.setString(2, nota.getConteudo());
            stmt.setInt(3, nota.getId());
            stmt.setInt(4, nota.getProjetoId());

            stmt.executeUpdate();
        }
    }

    public void excluir(int notaId, int projetoId)
            throws SQLException {

        String sql = """
            DELETE FROM notas
            WHERE not_int_id = ?
            AND pro_int_id = ?
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, notaId);
            stmt.setInt(2, projetoId);

            stmt.executeUpdate();
        }
    }

    private Nota mapear(ResultSet rs) throws SQLException {

        Nota nota = new Nota();

        nota.setId(rs.getInt("not_int_id"));
        nota.setTitulo(rs.getString("not_str_titulo"));
        nota.setConteudo(rs.getString("not_str_conteudo"));

        Timestamp data = rs.getTimestamp("not_dta_ultimaedicao");

        if (data != null) {
            nota.setDataUltimaEdicao(data.toLocalDateTime());
        }

        nota.setProjetoId(rs.getInt("pro_int_id"));

        return nota;
    }
}
