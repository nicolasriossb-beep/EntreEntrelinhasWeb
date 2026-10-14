/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CapituloDAO;
import dao.ProjetoDAO;
import java.io.IOException;
import java.time.LocalDateTime;
import model.Capitulo;
import model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AtualizarCapituloServlet")
public class AtualizarCapituloServlet extends HttpServlet {

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
            int capituloId = Integer.parseInt(
                    request.getParameter("capituloId"));

            if (new ProjetoDAO().buscarPorId(
                    projetoId, usuario.getId()) == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            CapituloDAO dao = new CapituloDAO();
            Capitulo capitulo =
                    dao.buscarPorId(capituloId, projetoId);

            if (capitulo == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            int numero = Integer.parseInt(
                    request.getParameter("numero"));
            String titulo = request.getParameter("titulo");

            if (numero < 1 || titulo == null
                    || titulo.isBlank()) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Informe um número válido e um título.");
                return;
            }

            capitulo.setNumero(numero);
            capitulo.setTitulo(titulo.trim());
            capitulo.setTexto(request.getParameter("texto"));
            capitulo.setDataUltimaEdicao(LocalDateTime.now());
            capitulo.setPublico(
                    "true".equals(request.getParameter("publico")));

            dao.atualizar(capitulo);

            response.sendRedirect(request.getContextPath()
                    + "/VisualizarCapituloServlet?projetoId="
                    + projetoId + "&capituloId=" + capituloId);

        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST);
        } catch (java.sql.SQLException e) {
            throw new ServletException(e);
        }
    }
}