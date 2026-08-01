/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.UsuarioDAO;
import java.time.LocalDateTime;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Usuario;

/**
 *
 * @author Ryzen7RTX3050
 */
@WebServlet(name = "CadastroServlet", urlPatterns = {"/CadastroServlet"})
public class CadastroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Usuario usuario = new Usuario();

            usuario.setNomeUsuario(request.getParameter("nomeUsuario"));
            usuario.setNomeCompleto(request.getParameter("nomeCompleto"));
            usuario.setEmail(request.getParameter("email"));
            usuario.setSenha(request.getParameter("senha"));

            usuario.setDataCadastro(LocalDateTime.now());

            UsuarioDAO dao = new UsuarioDAO();

            dao.cadastrar(usuario);

            response.sendRedirect("pages/logins.html");

        } catch (Exception e) {

            throw new ServletException(e);

        }

    }
   

 

}
