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
import java.time.LocalDateTime;

import model.Projeto;
import model.Usuario;

@WebServlet("/AtualizarProjetoServlet")
public class AtualizarProjetoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // 1. Verifica se existe uma sessão ativa
            HttpSession sessao = request.getSession(false);

            if (sessao == null
                    || sessao.getAttribute("usuario") == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/pages/logins.html"
                );

                return;
            }

            // 2. Recupera o usuário logado
            Usuario usuario =
                    (Usuario) sessao.getAttribute("usuario");

            // 3. Recupera os dados enviados pelo formulário
            String idParametro = request.getParameter("id");
            String nome = request.getParameter("nome");
            String descricao = request.getParameter("descricao");
            String tipo = request.getParameter("tipo");

            // Verifica se o ID foi enviado
            if (idParametro == null || idParametro.isBlank()) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/BibliotecaServlet"
                );

                return;
            }

            int projetoId = Integer.parseInt(idParametro);

            // 4. Busca o projeto pertencente ao usuário
            ProjetoDAO dao = new ProjetoDAO();

            Projeto projeto = dao.buscarPorId(
                    projetoId,
                    usuario.getId()
            );

            // Verifica se o projeto existe e pertence ao usuário
            if (projeto == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/BibliotecaServlet"
                );

                return;
            }

            // 5. Atualiza os dados do projeto
            projeto.setNome(nome);
            projeto.setDescricao(descricao);
            projeto.setTipo(tipo);
            projeto.setDataUltimaEdicao(LocalDateTime.now());

            // 6. Atualiza o projeto no banco de dados
            dao.atualizar(projeto);

            // 7. Redireciona para o projeto atualizado
            response.sendRedirect(
                    request.getContextPath()
                    + "/AbrirProjetoServlet?id=" + projetoId
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