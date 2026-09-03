package controller;

import java.io.IOException;
import java.time.LocalTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Usuario;

@WebServlet("/BibliotecaServlet")
public class BibliotecaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);
        if (sessao == null || sessao.getAttribute("usuario") == null) {
            response.sendRedirect("pages/logins.html");
            return;
        }

        Usuario usuario = (Usuario) sessao.getAttribute("usuario");
        request.setAttribute("usuario", usuario);

        request.getRequestDispatcher("/pages/biblioteca.jsp")
                .forward(request, response);
    }

}
