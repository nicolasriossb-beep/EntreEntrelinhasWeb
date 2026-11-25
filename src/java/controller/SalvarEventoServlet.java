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
import model.EventoCronologia;
import model.Usuario;

@WebServlet("/SalvarEventoServlet")
public class SalvarEventoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession sessao = request.getSession(false);

        Usuario usuario = sessao == null
                ? null : (Usuario) sessao.getAttribute("usuario");

        if (usuario == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        int projetoId;
        int eventoId = 0;

        try {
            projetoId = Integer.parseInt(
                    request.getParameter("projetoId"));

            String id = request.getParameter("eventoId");

            if (id != null && !id.isBlank()) {
                eventoId = Integer.parseInt(id);
            }

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

        String titulo = request.getParameter("titulo");
        String descricao = request.getParameter("descricao");

        if (titulo == null || titulo.isBlank()
                || titulo.trim().length() > 100) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "O título é obrigatório e deve ter até 100 caracteres.");
            return;
        }

        EventoCronologiaDAO eventoDAO = new EventoCronologiaDAO();

        EventoCronologia evento = new EventoCronologia();

        evento.setTitulo(titulo.trim());
        evento.setDescricao(descricao);
        evento.setProjetoId(projetoId);

        boolean sucesso;

        if (eventoId > 0) {

            if (eventoDAO.buscarPorId(eventoId, projetoId) == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            evento.setId(eventoId);
            sucesso = eventoDAO.atualizar(evento);

        } else {
            sucesso = eventoDAO.criar(evento);
        }

        if (sucesso) {
            response.sendRedirect(request.getContextPath()
                    + "/CronologiaServlet?id=" + projetoId);
        } else {
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}