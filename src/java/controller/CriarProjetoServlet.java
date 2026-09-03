package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.*;
import dao.*;

@WebServlet("/CriarProjetoServlet")
public class CriarProjetoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession(false);

        if (sessao == null || sessao.getAttribute("usuario") == null) {
            response.sendRedirect("pages/logins.html");
            return;
        }

        Usuario usuario = (Usuario) sessao.getAttribute("usuario");

        try {
            Projeto p = new Projeto();
            p.setNome(request.getParameter("nome"));
            p.setDescricao(request.getParameter("descricao"));
            p.setTipo(request.getParameter("tipo"));
            p.setDataCriacao(LocalDateTime.now());
            try {
                int projetoId = ProjetoDAO.inserir(p, usuario);
                p.setId(projetoId);
                List<Projeto> projetos = usuario.getProjetos();
                projetos.add(p);
                System.out.println("Projeto inserido com sucesso: " + p);

                response.sendRedirect(
                        request.getContextPath()
                        + "/BibliotecaServlet");
            } catch (SQLException e) {
                System.out.println(e);
                throw new ServletException(e);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new ServletException(e);
        }

    }

}
