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
import java.util.ArrayList;
import java.util.List;
import model.Usuario;

@WebServlet("/ReordenarCronologiaServlet")
public class ReordenarCronologiaServlet extends HttpServlet {

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

        try {
            projetoId = Integer.parseInt(
                    request.getParameter("projetoId"));

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

        String ordem = request.getParameter("ordem");

        if (ordem == null || ordem.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        List<Integer> idsOrdenados = new ArrayList<>();

        try {
            for (String id : ordem.split(",")) {
                idsOrdenados.add(Integer.parseInt(id));
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        EventoCronologiaDAO eventoDAO = new EventoCronologiaDAO();

        if (eventoDAO.reorganizar(projetoId, idsOrdenados)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Não foi possível reorganizar os eventos.");
        }
    }
}