/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.time.LocalTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Usuario;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession(false);

        if (sessao == null || sessao.getAttribute("usuario") == null) {

            response.sendRedirect("pages/logins.html");
            return;

        }
        Usuario usuario
                = (Usuario) sessao.getAttribute("usuario");

        LocalTime agora = LocalTime.now();

        String saudacao;

        if (agora.getHour() < 12) {

            saudacao = "Bom dia";

        } else if (agora.getHour() < 18) {

            saudacao = "Boa tarde";

        } else {

            saudacao = "Boa noite";

        }
        request.setAttribute("saudacao", saudacao);

        request.setAttribute("usuario", usuario);

        request.getRequestDispatcher("/pages/dashboard.jsp")
                .forward(request, response);
    }

}
