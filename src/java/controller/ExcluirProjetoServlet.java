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

@WebServlet("/ExcluirProjetoServlet")
public class ExcluirProjetoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

           
            HttpSession sessao = request.getSession(false);

            if (sessao == null
                    || sessao.getAttribute("usuario") == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/pages/logins.html"
                );

                return;
            }

            
            Usuario usuario =
                    (Usuario) sessao.getAttribute("usuario");

            
            String idParametro = request.getParameter("id");

            if (idParametro == null
                    || idParametro.isBlank()) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/BibliotecaServlet"
                );

                return;
            }

            int projetoId = Integer.parseInt(idParametro);

            
            ProjetoDAO dao = new ProjetoDAO();

            Projeto projeto = dao.buscarPorId(
                    projetoId,
                    usuario.getId()
            );

            if (projeto == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/BibliotecaServlet"
                );

                return;
            }

            
            dao.excluir(
                    projetoId,
                    usuario.getId()
            );

            
            response.sendRedirect(
                    request.getContextPath()
                    + "/BibliotecaServlet"
            );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/BibliotecaServlet"
            );

        } catch (Exception e) {

            throw new ServletException(e);
        }
    }
}