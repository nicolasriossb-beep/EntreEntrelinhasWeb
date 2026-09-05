/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Ryzen7RTX3050
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Projeto;
import util.Conexao;

public class ProjetoDAO {

    public void criar(Projeto projeto) throws SQLException {

        String sql = """
            INSERT INTO projetos
            (pro_str_nome,
             pro_str_descricao,
             pro_str_tipo,
             pro_dta_criacao,
             pro_dta_ultimaedicao,
             usu_int_id)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, projeto.getNome());
            stmt.setString(2, projeto.getDescricao());
            stmt.setString(3, projeto.getTipo());
            stmt.setObject(4, projeto.getDataCriacao());
            stmt.setObject(5, projeto.getDataUltimaEdicao());
            stmt.setInt(6, projeto.getUsuarioId());

            stmt.executeUpdate();
        }
    }

    public List<Projeto> listarPorUsuario(int usuarioId)
            throws SQLException {

        String sql = """
            SELECT *
            FROM projetos
            WHERE usu_int_id = ?
            ORDER BY pro_dta_criacao DESC
            """;

        List<Projeto> projetos = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Projeto projeto = new Projeto();

                projeto.setId(rs.getInt("pro_int_id"));
                projeto.setNome(rs.getString("pro_str_nome"));
                projeto.setDescricao(
                    rs.getString("pro_str_descricao")
                );
                projeto.setTipo(rs.getString("pro_str_tipo"));

                Timestamp criacao =
                    rs.getTimestamp("pro_dta_criacao");

                if (criacao != null) {
                    projeto.setDataCriacao(
                        criacao.toLocalDateTime()
                    );
                }

                Timestamp ultimaEdicao =
                    rs.getTimestamp("pro_dta_ultimaedicao");

                if (ultimaEdicao != null) {
                    projeto.setDataUltimaEdicao(
                        ultimaEdicao.toLocalDateTime()
                    );
                }

                projeto.setUsuarioId(
                    rs.getInt("usu_int_id")
                );

                projetos.add(projeto);
            }
        }

        return projetos;
    }

    public Projeto buscarPorId(int projetoId)
            throws SQLException {

        String sql = """
            SELECT *
            FROM projetos
            WHERE pro_int_id = ?
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projetoId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Projeto projeto = new Projeto();

                projeto.setId(rs.getInt("pro_int_id"));
                projeto.setNome(rs.getString("pro_str_nome"));
                projeto.setDescricao(
                    rs.getString("pro_str_descricao")
                );
                projeto.setTipo(rs.getString("pro_str_tipo"));

                Timestamp criacao =
                    rs.getTimestamp("pro_dta_criacao");

                if (criacao != null) {
                    projeto.setDataCriacao(
                        criacao.toLocalDateTime()
                    );
                }

                Timestamp ultimaEdicao =
                    rs.getTimestamp("pro_dta_ultimaedicao");

                if (ultimaEdicao != null) {
                    projeto.setDataUltimaEdicao(
                        ultimaEdicao.toLocalDateTime()
                    );
                }

                projeto.setUsuarioId(
                    rs.getInt("usu_int_id")
                );

                return projeto;
            }
        }

        return null;
    }

    public void atualizar(Projeto projeto)
            throws SQLException {

        String sql = """
            UPDATE projetos
            SET pro_str_nome = ?,
                pro_str_descricao = ?,
                pro_str_tipo = ?,
                pro_dta_ultimaedicao = ?
            WHERE pro_int_id = ?
            AND usu_int_id = ?
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, projeto.getNome());
            stmt.setString(2, projeto.getDescricao());
            stmt.setString(3, projeto.getTipo());
            stmt.setObject(4, projeto.getDataUltimaEdicao());
            stmt.setInt(5, projeto.getId());
            stmt.setInt(6, projeto.getUsuarioId());

            stmt.executeUpdate();
        }
    }

    public void excluir(int projetoId, int usuarioId)
            throws SQLException {

        String sql = """
            DELETE FROM projetos
            WHERE pro_int_id = ?
            AND usu_int_id = ?
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projetoId);
            stmt.setInt(2, usuarioId);

            stmt.executeUpdate();
        }
    }
}
