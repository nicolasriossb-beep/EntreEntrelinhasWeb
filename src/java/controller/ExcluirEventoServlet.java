package controller;

import dao.EventoCronologiaDAO;
import dao.ProjetoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import model.Usuario;

@WebServlet("/ExcluirEventoServlet")
public class ExcluirEventoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);

        Usuario usuario = sessao == null
                ? null : (Usuario) sessao.getAttribute("usuario");

        if (usuario == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        int projetoId;
        int eventoId;

        try {
            projetoId = Integer.parseInt(
                    request.getParameter("projetoId"));

            eventoId = Integer.parseInt(
                    request.getParameter("eventoId"));

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        ProjetoDAO projetoDAO = new ProjetoDAO();

        try {
            if (projetoDAO.buscarPorId(projetoId, usuario.getId()) == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        } catch (SQLException e) {
            throw new ServletException("Erro ao verificar o projeto.", e);
        }

        EventoCronologiaDAO eventoDAO = new EventoCronologiaDAO();

        if (eventoDAO.buscarPorId(eventoId, projetoId) == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (eventoDAO.excluir(eventoId, projetoId)) {
            response.sendRedirect(request.getContextPath()
                    + "/CronologiaServlet?id=" + projetoId);
        } else {
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}