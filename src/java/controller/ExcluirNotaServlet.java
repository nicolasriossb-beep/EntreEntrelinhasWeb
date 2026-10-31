package controller;

import dao.NotaDAO;
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

@WebServlet("/ExcluirNotaServlet")
public class ExcluirNotaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
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
            int notaId = Integer.parseInt(request.getParameter("notaId"));

            ProjetoDAO projetoDAO = new ProjetoDAO();

            if (projetoDAO.buscarPorId(projetoId, usuario.getId()) == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            NotaDAO dao = new NotaDAO();

            if (dao.buscarPorId(notaId, projetoId) == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            dao.excluir(notaId, projetoId);

            response.sendRedirect(request.getContextPath()
                    + "/NotaServlet?id=" + projetoId);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}