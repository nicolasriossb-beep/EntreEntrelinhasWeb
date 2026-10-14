/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.UsuarioDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Usuario;

@WebServlet("/AtualizarAvatarServlet")
public class AtualizarAvatarServlet extends HttpServlet {

    private static final Set<String> AVATARES_PERMITIDOS = Set.of(
        "avatar01.jpeg",
        "avatar02.jpeg",
        "avatar03.jpeg",
        "avatar04.jpeg",
        "avatar05.jpeg",
        "avatar06.jpeg"
    );

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("usuario") == null) {
            response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED
            );
            return;
        }

        Usuario usuario =
            (Usuario) session.getAttribute("usuario");

        String avatar = request.getParameter("avatar");

        if (avatar == null
                || !AVATARES_PERMITIDOS.contains(avatar)) {
            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Avatar inválido."
            );
            return;
        }

        try {
            UsuarioDAO dao = new UsuarioDAO();

            dao.atualizarAvatar(usuario.getId(), avatar);

            // Atualiza também o usuário guardado na sessão.
            usuario.setAvatar(avatar);

            response.setStatus(
                HttpServletResponse.SC_NO_CONTENT
            );

        } catch (SQLException e) {
            throw new ServletException(
                "Não foi possível salvar o avatar.", e
            );
        }
    }
}