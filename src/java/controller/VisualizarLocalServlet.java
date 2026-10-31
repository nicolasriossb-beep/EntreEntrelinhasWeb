
package controller;

import dao.LocalDAO;
import dao.ProjetoDAO;
import java.io.IOException;
import model.Local;
import model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/VisualizarLocalServlet")
public class VisualizarLocalServlet extends HttpServlet {

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

            int localId = Integer.parseInt(
                    request.getParameter("localId"));

            if (new ProjetoDAO().buscarPorId(
                    projetoId, usuario.getId()) == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Local local = new LocalDAO().buscarPorId(
                    localId, projetoId);

            if (local == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            request.setAttribute("projetoId", projetoId);
            request.setAttribute("local", local);

            request.getRequestDispatcher(
                    "/pages/visualizarLocal.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST);
        } catch (java.sql.SQLException e) {
            throw new ServletException(e);
        }
    }
}
