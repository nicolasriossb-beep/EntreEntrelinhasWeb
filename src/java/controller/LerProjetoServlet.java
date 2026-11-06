/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ProjetoDAO;
import dao.CapituloDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Projeto;
import model.Usuario;

@WebServlet("/LerProjetoServlet")
public class LerProjetoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            HttpSession sessao =
                    request.getSession(false);

            if (sessao == null
                    || sessao.getAttribute("usuario") == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/pages/logins.html"
                );

                return;
            }

            Usuario usuario =
                    (Usuario) sessao.getAttribute("usuario");

            String idParametro =
                    request.getParameter("id");

            if (idParametro == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/BibliotecaServlet"
                );

                return;
            }

            int projetoId =
                    Integer.parseInt(idParametro);

            ProjetoDAO dao = new ProjetoDAO();

            Projeto projeto =
                    dao.buscarPorId(projetoId);

            if (projeto == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/BibliotecaServlet"
                );

                return;
            }

            request.setAttribute("projeto", projeto);
            request.setAttribute("capitulos",
                    new CapituloDAO().listarCapitulosPublicosPorProjeto(projetoId));

            request.getRequestDispatcher(
                    "/pages/leitura-projeto.jsp").forward(request, response);
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
