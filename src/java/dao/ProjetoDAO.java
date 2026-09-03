package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

import model.*;
import util.Conexao;

public class ProjetoDAO {
    public static int inserir(Projeto projeto, Usuario usuario) throws SQLException {

        String sql = """
            INSERT INTO projetos
            (PRO_STR_NOME,
             PRO_STR_DESCRICAO,
             PRO_STR_TIPO,
             PRO_DTA_CRIACAO,
             USU_INT_ID)
            VALUES (?, ?, ?, ?, ?) RETURNING PRO_INT_ID;
            """;

        Connection conn = Conexao.conectar();

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, projeto.getNome());
        ps.setString(2, projeto.getDescricao());
        ps.setString(3, projeto.getTipo());
        ps.setObject(4, projeto.getDataCriacao());
        ps.setInt(5, usuario.getId());

        ResultSet rs = ps.executeQuery();
        int projetoId = 0;
        if (rs.next()) {
            projetoId = rs.getInt("PRO_INT_ID");
        }

        ps.close();
        conn.close();

        return projetoId;
    }

    public static List<Projeto> ler(Usuario usuario) throws Exception {

        Connection conn = Conexao.conectar();

        String sql = """
            SELECT
                projetos.PRO_INT_ID,
                projetos.PRO_STR_NOME,
                projetos.PRO_STR_DESCRICAO,
                projetos.PRO_STR_TIPO,
                projetos.PRO_DTA_CRIACAO,
                projetos.PRO_DTA_ULTIMAEDICAO
            FROM projetos INNER JOIN usuarios
            ON projetos.USU_INT_ID = usuarios.USU_INT_ID
            WHERE usuarios.USU_INT_ID = ?;
            """;

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, usuario.getId());

        ResultSet rs = ps.executeQuery();

        List<Projeto> projetos = new ArrayList<>();

        while (rs.next()) {

            Projeto projeto = new Projeto();
            projeto.setId(rs.getInt(1));
            projeto.setNome(rs.getString(2));
            projeto.setDescricao(rs.getString(3));
            projeto.setTipo(rs.getString(4));
            projeto.setDataCriacao(rs.getObject(5, LocalDateTime.class));
            projeto.setDataUltimaEdicao(rs.getObject(6, LocalDateTime.class));

            projetos.add(projeto);
        }

        rs.close();
        ps.close();
        conn.close();

        return projetos;
    }
}
