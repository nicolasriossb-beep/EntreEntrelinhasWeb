/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import model.Usuario;
import util.Conexao;

/**
 *
 * @author Ryzen7RTX3050
 */
public class UsuarioDAO {
     public void cadastrar(Usuario usuario) throws SQLException {

        String sql = """
            INSERT INTO usuarios
            (USU_STR_NOMEUSUARIO,
             USU_STR_NOMECOMPLETO,
             USU_STR_EMAIL,
             USU_STR_SENHA,
             USU_DTA_CADASTRO)
            VALUES (?, ?, ?, ?, ?)
            """;

        Connection conn = Conexao.conectar();

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, usuario.getNomeUsuario());
        stmt.setString(2, usuario.getNomeCompleto());
        stmt.setString(3, usuario.getEmail());
        stmt.setString(4, usuario.getSenha());

        stmt.setObject(5, usuario.getDataCadastro());

        stmt.execute();

        stmt.close();
        conn.close();
    }
    
     public Usuario autenticar(String email, String senha) throws Exception {

    Connection conn = Conexao.conectar();

    String sql = """
                 SELECT *
                 FROM usuarios
                 WHERE USU_STR_EMAIL = ?
                 AND USU_STR_SENHA = ?
                 """;

    PreparedStatement ps = conn.prepareStatement(sql);

    ps.setString(1, email);
    ps.setString(2, senha);

    ResultSet rs = ps.executeQuery();

    Usuario usuario = null;

    if (rs.next()) {

        usuario = new Usuario();

        usuario.setId(rs.getInt("USU_INT_ID"));
        usuario.setNomeUsuario(rs.getString("USU_STR_NOMEUSUARIO"));
        usuario.setNomeCompleto(rs.getString("USU_STR_NOMECOMPLETO"));
        usuario.setEmail(rs.getString("USU_STR_EMAIL"));
        usuario.setSenha(rs.getString("USU_STR_SENHA"));
        usuario.setDataCadastro(
                rs.getTimestamp("USU_DTA_CADASTRO").toLocalDateTime()
        );

    }

    rs.close();
    ps.close();
    conn.close();

    return usuario;

}
     
}
