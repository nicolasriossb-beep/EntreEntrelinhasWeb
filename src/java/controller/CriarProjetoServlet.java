/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ProjetoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import model.Projeto;
import model.Usuario;

@WebServlet("/CriarProjetoServlet")
public class CriarProjetoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String nome = request.getParameter("nome");
            String descricao = request.getParameter("descricao");
            String tipo = request.getParameter("tipo");

            HttpSession sessao = request.getSession();

            Usuario usuario =
                    (Usuario) sessao.getAttribute("usuario");

            if (usuario == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/pages/logins.html"
                );

                return;
            }

            Projeto projeto = new Projeto();

            projeto.setNome(nome);
            projeto.setDescricao(descricao);
            projeto.setTipo(tipo);
            projeto.setDataCriacao(LocalDateTime.now());
            projeto.setDataUltimaEdicao(null);
            projeto.setUsuarioId(usuario.getId());

            ProjetoDAO dao = new ProjetoDAO();

            dao.criar(projeto);

            response.sendRedirect(
                    request.getContextPath()
                    + "/DashboardServlet"
            );

        } catch (Exception e) {

            throw new ServletException(e);

        }
    }
}