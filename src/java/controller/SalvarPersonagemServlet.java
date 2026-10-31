/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.PersonagemDAO;
import dao.ProjetoDAO;
import java.io.IOException;
import model.Personagem;
import model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SalvarPersonagemServlet")
public class SalvarPersonagemServlet extends HttpServlet {

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
            request.setCharacterEncoding("UTF-8");

            int projetoId = Integer.parseInt(
                    request.getParameter("projetoId"));

            if (new ProjetoDAO().buscarPorId(
                    projetoId, usuario.getId()) == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            String nome = request.getParameter("nome");
            String idade = request.getParameter("idade");
            String aparencia = request.getParameter("aparencia");
            String personalidade =
                    request.getParameter("personalidade");
            String historia = request.getParameter("historia");

            if (nome == null || nome.isBlank()) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "O nome do personagem é obrigatório.");
                return;
            }

            Personagem personagem = new Personagem();

            personagem.setNome(nome.trim());
            personagem.setIdade(idade);
            personagem.setAparencia(aparencia);
            personagem.setPersonalidade(personalidade);
            personagem.setHistoria(historia);
            personagem.setProjetoId(projetoId);

            PersonagemDAO dao = new PersonagemDAO();

            String idParametro =
                    request.getParameter("personagemId");

            if (idParametro == null || idParametro.isBlank()) {

                dao.criar(personagem);

            } else {

                int personagemId =
                        Integer.parseInt(idParametro);

                if (dao.buscarPorId(
                        personagemId, projetoId) == null) {
                    response.sendError(
                            HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                personagem.setId(personagemId);
                dao.atualizar(personagem);
            }

            response.sendRedirect(request.getContextPath()
                    + "/PersonagemServlet?id=" + projetoId);

        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST);
        } catch (java.sql.SQLException e) {
            throw new ServletException(e);
        }
    }
}
