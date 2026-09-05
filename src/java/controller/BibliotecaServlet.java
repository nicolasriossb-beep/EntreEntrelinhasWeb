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
import java.util.List;
import model.Projeto;
import model.Usuario;

@WebServlet("/BibliotecaServlet")
public class BibliotecaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

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

            ProjetoDAO dao = new ProjetoDAO();

            List<Projeto> projetos =
                    dao.listarPorUsuario(usuario.getId());

            request.setAttribute("projetos", projetos);

            request.getRequestDispatcher(
                    "/pages/biblioteca.jsp"
            ).forward(request, response);

        } catch (Exception e) {

            throw new ServletException(e);

        }
    }
}