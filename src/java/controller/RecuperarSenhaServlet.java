package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.net.URI;
import java.net.http.*;

import dao.UsuarioDAO;
import model.Usuario;
import util.Config;

import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet("/RecuperarSenhaServlet")
public class RecuperarSenhaServlet extends HttpServlet {

    @Override
    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {

        try {

            String email = request.getParameter("email");

            Usuario usuario = UsuarioDAO.buscarUsuario(email);

            if (usuario != null) {
                HttpResponse<String> httpRequest = enviarEmail(email, usuario);
                if (httpRequest.statusCode() == 200) {
                    String pagina = "/pages/esqueci-minha-senha-sucesso.html";
                    response.sendRedirect(request.getContextPath() + pagina);
                } else {
                    String pagina = "/pages/esqueci-minha-senha-erro.html";
                    response.sendRedirect(request.getContextPath() + pagina);
                }
            } else {
                String pagina = "/pages/esqueci-minha-senha-invalido.html";
                response.sendRedirect(request.getContextPath() + pagina);
            }
        } catch (Exception e) {

            throw new ServletException(e);

        }

    }

    public static HttpResponse<String> enviarEmail(String to, Usuario usuario) throws Exception {
        String apiKey = Config.get("resend_api_key");
        String urlString = "https://api.resend.com/emails";

        // Corpo do e-mail em formato JSON
        String nome = usuario.getNomeCompleto();
        String senha = usuario.getSenha();
        String jsonInputString = """
            {
                "to": "%s",
                "template": {
                    "id": "esqueci-minha-senha",
                    "variables": {
                        "nome": "%s",
                        "senha": "%s"
                    }
                }
            }
            """.formatted(to, nome, senha);

        HttpResponse<String> out = null;
        try {
            // Cria o cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Monta a requisição POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json; utf-8")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonInputString))
                    .build();

            // Envia a requisição e aguarda a resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Exibe o resultado
            System.out.println("Código de Resposta HTTP: " + response.statusCode());
            System.out.println("Corpo da Resposta da API: " + response.body());

            out = response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

}
