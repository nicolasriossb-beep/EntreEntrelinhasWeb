/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.EventoCronologia;
import util.Conexao;

public class EventoCronologiaDAO {

    public boolean criar(EventoCronologia evento) {

        String sqlOrdem = "SELECT COALESCE(MAX(eve_int_ordem), 0) + 1 "
                + "FROM eventos_cronologia WHERE pro_int_id = ?";

        String sql = "INSERT INTO eventos_cronologia "
                + "(eve_int_ordem, eve_str_titulo, eve_str_descricao, pro_int_id) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar()) {

            int ordem;

            try (PreparedStatement stmtOrdem = conexao.prepareStatement(sqlOrdem)) {
                stmtOrdem.setInt(1, evento.getProjetoId());

                try (ResultSet rs = stmtOrdem.executeQuery()) {
                    rs.next();
                    ordem = rs.getInt(1);
                }
            }

            try (PreparedStatement stmt = conexao.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setInt(1, ordem);
                stmt.setString(2, evento.getTitulo());
                stmt.setString(3, evento.getDescricao());
                stmt.setInt(4, evento.getProjetoId());

                int linhas = stmt.executeUpdate();

                if (linhas > 0) {
                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            evento.setId(rs.getInt(1));
                            evento.setOrdem(ordem);
                            return true;
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<EventoCronologia> listarPorProjeto(int projetoId) {

        List<EventoCronologia> eventos = new ArrayList<>();

        String sql = "SELECT * FROM eventos_cronologia "
                + "WHERE pro_int_id = ? "
                + "ORDER BY eve_int_ordem ASC";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, projetoId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    eventos.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eventos;
    }

    public EventoCronologia buscarPorId(int eventoId, int projetoId) {

        String sql = "SELECT * FROM eventos_cronologia "
                + "WHERE eve_int_id = ? AND pro_int_id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, eventoId);
            stmt.setInt(2, projetoId);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapear(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean atualizar(EventoCronologia evento) {

        String sql = "UPDATE eventos_cronologia "
                + "SET eve_str_titulo = ?, eve_str_descricao = ? "
                + "WHERE eve_int_id = ? AND pro_int_id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, evento.getTitulo());
            stmt.setString(2, evento.getDescricao());
            stmt.setInt(3, evento.getId());
            stmt.setInt(4, evento.getProjetoId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean excluir(int eventoId, int projetoId) {

        String sql = "DELETE FROM eventos_cronologia "
                + "WHERE eve_int_id = ? AND pro_int_id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, eventoId);
            stmt.setInt(2, projetoId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean reorganizar(int projetoId, List<Integer> idsOrdenados) {

        if (idsOrdenados == null) {
            return false;
        }

        Set<Integer> idsUnicos = new HashSet<>(idsOrdenados);

        if (idsUnicos.size() != idsOrdenados.size()) {
            return false;
        }

        String sqlContagem = "SELECT COUNT(*) FROM eventos_cronologia "
                + "WHERE pro_int_id = ?";

        String sqlAtualizar = "UPDATE eventos_cronologia "
                + "SET eve_int_ordem = ? "
                + "WHERE eve_int_id = ? AND pro_int_id = ?";

        Connection conexao = null;

        try {
            conexao = Conexao.conectar();
            conexao.setAutoCommit(false);

            int totalEventos;

            try (PreparedStatement stmt = conexao.prepareStatement(sqlContagem)) {
                stmt.setInt(1, projetoId);

                try (ResultSet rs = stmt.executeQuery()) {
                    rs.next();
                    totalEventos = rs.getInt(1);
                }
            }

            if (totalEventos != idsOrdenados.size()) {
                conexao.rollback();
                return false;
            }

            try (PreparedStatement stmt = conexao.prepareStatement(sqlAtualizar)) {

                for (int i = 0; i < idsOrdenados.size(); i++) {

                    stmt.setInt(1, i + 1);
                    stmt.setInt(2, idsOrdenados.get(i));
                    stmt.setInt(3, projetoId);

                    if (stmt.executeUpdate() != 1) {
                        conexao.rollback();
                        return false;
                    }
                }
            }

            conexao.commit();
            return true;

        } catch (SQLException e) {

            if (conexao != null) {
                try {
                    conexao.rollback();
                } catch (SQLException erroRollback) {
                    erroRollback.printStackTrace();
                }
            }

            e.printStackTrace();

        } finally {

            if (conexao != null) {
                try {
                    conexao.setAutoCommit(true);
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    private EventoCronologia mapear(ResultSet rs) throws SQLException {

        EventoCronologia evento = new EventoCronologia();

        evento.setId(rs.getInt("eve_int_id"));
        evento.setOrdem(rs.getInt("eve_int_ordem"));
        evento.setTitulo(rs.getString("eve_str_titulo"));
        evento.setDescricao(rs.getString("eve_str_descricao"));
        evento.setProjetoId(rs.getInt("pro_int_id"));

        return evento;
    }
}