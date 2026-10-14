/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CapituloDAO;
import dao.ProjetoDAO;
import java.io.IOException;
import model.Capitulo;
import model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EditarCapituloServlet")
public class EditarCapituloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Usuario usuario = (Usuario) request.getSession()
                .getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect(request.getContextPath()
                    + "/pages/login.jsp");
            return;
        }

        try {
            int projetoId = Integer.parseInt(
                    request.getParameter("projetoId"));

            if (new ProjetoDAO().buscarPorId(
                    projetoId, usuario.getId()) == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            String idParametro =
                    request.getParameter("capituloId");

            Capitulo capitulo;

            if (idParametro == null || idParametro.isBlank()) {
                capitulo = new Capitulo();
                capitulo.setProjetoId(projetoId);
                capitulo.setNumero(
                    new CapituloDAO()
                        .listarPorProjeto(projetoId).size() + 1);
                capitulo.setPublico(false);
            } else {
                int capituloId = Integer.parseInt(idParametro);

                capitulo = new CapituloDAO()
                        .buscarPorId(capituloId, projetoId);

                if (capitulo == null) {
                    response.sendError(
                            HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
            }

            request.setAttribute("capitulo", capitulo);
            request.setAttribute("projetoId", projetoId);

            request.getRequestDispatcher(
                    "/pages/editarCapitulo.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST);
        } catch (java.sql.SQLException e) {
            throw new ServletException(e);
        }
    }
}