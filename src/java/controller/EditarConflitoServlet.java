package controller;

import dao.ConflitoDAO;
import dao.ProjetoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import model.Conflito;
import model.Usuario;

@WebServlet("/EditarConflitoServlet")
public class EditarConflitoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Usuario usuario = session == null ? null
                : (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
            return;
        }

        try {
            int projetoId = Integer.parseInt(request.getParameter("projetoId"));

            ProjetoDAO projetoDAO = new ProjetoDAO();

            if (projetoDAO.buscarPorId(projetoId, usuario.getId()) == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            String idParametro = request.getParameter("conflitoId");
            Conflito conflito = new Conflito();

            conflito.setProjetoId(projetoId);

            if (idParametro != null && !idParametro.isBlank()) {
                int conflitoId = Integer.parseInt(idParametro);

                conflito = new ConflitoDAO()
                        .buscarPorId(conflitoId, projetoId);

                if (conflito == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
            }

            request.setAttribute("projetoId", projetoId);
            request.setAttribute("conflito", conflito);

            request.getRequestDispatcher("/pages/conflito.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}