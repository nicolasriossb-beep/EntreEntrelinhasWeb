/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.PersonagemDAO;
import dao.ProjetoDAO;
import java.io.IOException;
import model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ExcluirPersonagemServlet")
public class ExcluirPersonagemServlet extends HttpServlet {

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

            int personagemId = Integer.parseInt(
                    request.getParameter("personagemId"));

            if (new ProjetoDAO().buscarPorId(
                    projetoId, usuario.getId()) == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            PersonagemDAO dao = new PersonagemDAO();

            if (dao.buscarPorId(
                    personagemId, projetoId) == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            dao.excluir(personagemId, projetoId);

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
