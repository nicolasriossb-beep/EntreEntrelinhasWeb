/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

import model.Usuario;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String email = request.getParameter("email");
            String senha = request.getParameter("senha");

            UsuarioDAO dao = new UsuarioDAO();

            Usuario usuario = dao.autenticar(email, senha);

            if (usuario != null) {

                HttpSession sessao = request.getSession();

                sessao.setAttribute("usuario", usuario);

                response.sendRedirect(
                        request.getContextPath()
                        + "/DashboardServlet");

            } else {

                response.sendRedirect(
                        request.getContextPath()
                        + "/paginas/logins.html");

            }

        } catch (Exception e) {

            throw new ServletException(e);

        }

    }

}
