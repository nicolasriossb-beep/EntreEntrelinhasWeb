/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ProjetoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Projeto;
import model.Usuario;

@WebServlet("/ProjetoServlet")
public class ProjetoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            HttpSession sessao =
                    request.getSession(false);

            if (sessao == null
                    || sessao.getAttribute("usuario") == null) {

                response.setStatus(
                        HttpServletResponse.SC_UNAUTHORIZED
                );

                return;
            }

            Usuario usuario =
                    (Usuario) sessao.getAttribute("usuario");

            String idParametro =
                    request.getParameter("id");

            if (idParametro == null) {

                response.setStatus(
                        HttpServletResponse.SC_BAD_REQUEST
                );

                return;
            }

            int projetoId =
                    Integer.parseInt(idParametro);

            ProjetoDAO dao = new ProjetoDAO();

            Projeto projeto =
                    dao.buscarPorId(
                            projetoId,
                            usuario.getId()
                    );

            if (projeto == null) {

                response.setStatus(
                        HttpServletResponse.SC_NOT_FOUND
                );

                return;
            }

            response.setContentType(
                    "application/json"
            );

            response.setCharacterEncoding("UTF-8");

            String json =
                    "{\"nome\":\""
                    + projeto.getNome().replace("\"", "\\\"")
                    + "\"}";

            response.getWriter().print(json);

        } catch (NumberFormatException e) {

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
            );

        } catch (Exception e) {

            throw new ServletException(e);

        }
    }
}