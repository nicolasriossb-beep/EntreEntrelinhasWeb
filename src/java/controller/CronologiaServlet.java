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
import java.util.List;
import model.EventoCronologia;
import model.Usuario;

@WebServlet("/CronologiaServlet")
public class CronologiaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession sessao = request.getSession(false);

        Usuario usuario = sessao == null
                ? null : (Usuario) sessao.getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect(
                    request.getContextPath() + "/pages/login.html");
            return;
        }

        int projetoId;

        try {
            projetoId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendRedirect(
                    request.getContextPath() + "/pages/projetos.html");
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

        List<EventoCronologia> eventos =
                eventoDAO.listarPorProjeto(projetoId);

        request.setAttribute("projetoId", projetoId);
        request.setAttribute("eventos", eventos);

        request.getRequestDispatcher("/pages/cronologia.jsp")
                .forward(request, response);
    }
}