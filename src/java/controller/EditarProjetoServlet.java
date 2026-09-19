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
import model.Projeto;
import model.Usuario;

@WebServlet("/EditarProjetoServlet")
public class EditarProjetoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // Verifica se existe uma sessão ativa
            HttpSession sessao = request.getSession(false);

            if (sessao == null
                    || sessao.getAttribute("usuario") == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/pages/logins.html"
                );

                return;
            }

            // Recupera o usuário logado
            Usuario usuario =
                    (Usuario) sessao.getAttribute("usuario");

            // Recupera o ID enviado pela URL
            String idParametro = request.getParameter("id");

            if (idParametro == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/BibliotecaServlet"
                );

                return;
            }

            int projetoId = Integer.parseInt(idParametro);

            // Busca o projeto pertencente ao usuário
            ProjetoDAO dao = new ProjetoDAO();

            Projeto projeto = dao.buscarPorId(
                    projetoId,
                    usuario.getId()
            );

            // Verifica se o projeto foi encontrado
            if (projeto == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/BibliotecaServlet"
                );

                return;
            }

            // Envia o projeto para a JSP
            request.setAttribute("projeto", projeto);

            request.getRequestDispatcher(
                    "/pages/novoProjeto.jsp"
            ).forward(request, response);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/BibliotecaServlet"
            );

        } catch (Exception e) {

            throw new ServletException(e);

        }
    }
}