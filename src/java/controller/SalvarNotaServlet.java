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
import model.Nota;
import model.Usuario;

@WebServlet("/SalvarNotaServlet")
public class SalvarNotaServlet extends HttpServlet {

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

            String titulo = request.getParameter("titulo");
            String conteudo = request.getParameter("conteudo");

            if (titulo == null || titulo.isBlank() || titulo.length() > 100) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "O título é obrigatório e deve ter até 100 caracteres.");
                return;
            }

            Nota nota = new Nota();

            nota.setTitulo(titulo.trim());
            nota.setConteudo(conteudo);
            nota.setProjetoId(projetoId);

            NotaDAO dao = new NotaDAO();

            String idParametro = request.getParameter("notaId");

            if (idParametro == null || idParametro.isBlank()) {
                dao.criar(nota);
            } else {
                int notaId = Integer.parseInt(idParametro);

                Nota existente = dao.buscarPorId(notaId, projetoId);

                if (existente == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                nota.setId(notaId);
                dao.atualizar(nota);
            }

            response.sendRedirect(request.getContextPath()
                    + "/NotaServlet?id=" + projetoId);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}