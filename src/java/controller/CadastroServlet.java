/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Usuario;
import util.Config;
import util.Resend;
import java.net.http.HttpResponse;

/**
 *
 * @author Ryzen7RTX3050
 */
@WebServlet(name = "CadastroServlet", urlPatterns = {"/CadastroServlet"})
public class CadastroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            HttpSession sessao = request.getSession();

            String codigoConfirmacao = gerarCodigoConfirmacao();
            sessao.setAttribute("codigoConfirmacao", codigoConfirmacao);

            Usuario usuarioTemp = new Usuario();
            usuarioTemp.setNomeUsuario(request.getParameter("nomeUsuario"));
            usuarioTemp.setNomeCompleto(request.getParameter("nomeCompleto"));
            usuarioTemp.setEmail(request.getParameter("email"));
            usuarioTemp.setSenha(request.getParameter("senha"));
            sessao.setAttribute("usuarioTemp", usuarioTemp);

            String email = usuarioTemp.getEmail();
            String nome = usuarioTemp.getNomeCompleto();
            String corpoEmail = """
                {
                    "to": "%s",
                    "template": {
                        "id": "confirmacao-cadastro",
                        "variables": {
                            "nome": "%s",
                            "codigo": "%s"
                        }
                    }
                }
                """.formatted(email, nome, codigoConfirmacao);
            HttpResponse<String> httpRequest = Resend.enviarEmail(corpoEmail);
            if (httpRequest.statusCode() == 200) {
                response.sendRedirect("pages/cadastro-confirmar.html");
            } else {
                sessao.invalidate();
                response.sendRedirect("pages/cadastro-confirmar-erro.html");
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    public static String gerarCodigoConfirmacao() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int digito = (int) (Math.random() * 10); // 0 a 9
            sb.append(digito);
        }

        return sb.toString();
    }

}
