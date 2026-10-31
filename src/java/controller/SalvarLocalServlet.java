
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

@WebServlet("/SalvarLocalServlet")
public class SalvarLocalServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

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

            String nome = request.getParameter("nome");
            String tipo = request.getParameter("tipo");

            if (nome == null || nome.isBlank()
                    || tipo == null || tipo.length() > 50) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Local local = new Local();

            local.setNome(nome.trim());
            local.setTipo(tipo.trim());
            local.setAtmosfera(request.getParameter("atmosfera"));
            local.setAcontecimentos(
                    request.getParameter("acontecimentos"));
            local.setDescricao(request.getParameter("descricao"));
            local.setProjetoId(projetoId);

            String localIdParam = request.getParameter("localId");

            LocalDAO dao = new LocalDAO();

            if (localIdParam == null || localIdParam.isBlank()) {
                dao.criar(local);
            } else {
                int localId = Integer.parseInt(localIdParam);

                if (dao.buscarPorId(localId, projetoId) == null) {
                    response.sendError(
                            HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                local.setId(localId);
                dao.atualizar(local);
            }

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
