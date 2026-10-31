
package controller;

import dao.LocalDAO;
import dao.ProjetoDAO;
import java.io.IOException;
import model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ExcluirLocalServlet")
public class ExcluirLocalServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
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

            LocalDAO dao = new LocalDAO();

            if (dao.buscarPorId(localId, projetoId) == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            dao.excluir(localId, projetoId);

            response.sendRedirect(request.getContextPath()
                    + "/LocalServlet?id=" + projetoId);

        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST);
        } catch (java.sql.SQLException e) {
            throw new ServletException(e);
        }
    }
}
