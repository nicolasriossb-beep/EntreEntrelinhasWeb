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

@WebServlet("/SalvarConflitoServlet")
public class SalvarConflitoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

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

            String nome = request.getParameter("nome");
            String escala = request.getParameter("escala");

            if (nome == null || nome.isBlank() || nome.length() > 100) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "O nome é obrigatório e deve ter até 100 caracteres.");
                return;
            }

            if (escala != null && escala.length() > 50) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Conflito conflito = new Conflito();

            conflito.setNome(nome.trim());
            conflito.setEscala(escala);
            conflito.setLados(request.getParameter("lados"));
            conflito.setMotivacao(request.getParameter("motivacao"));
            conflito.setDescricao(request.getParameter("descricao"));
            conflito.setProjetoId(projetoId);

            ConflitoDAO dao = new ConflitoDAO();

            String idParametro = request.getParameter("conflitoId");

            if (idParametro == null || idParametro.isBlank()) {
                dao.criar(conflito);
            } else {
                int conflitoId = Integer.parseInt(idParametro);

                Conflito existente = dao.buscarPorId(conflitoId, projetoId);

                if (existente == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                conflito.setId(conflitoId);
                dao.atualizar(conflito);
            }

            response.sendRedirect(request.getContextPath()
                    + "/ConflitoServlet?id=" + projetoId);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}