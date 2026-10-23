package controller;

import dao.UsuarioDAO;
import java.time.LocalDateTime;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Usuario;

@WebServlet(name = "ConfirmarCadastroServlet", urlPatterns = {"/ConfirmarCadastroServlet"})
public class ConfirmarCadastroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession sessao = request.getSession(false);

            if (sessao == null 
                || sessao.getAttribute("usuarioTemp") == null 
                || sessao.getAttribute("codigoConfirmacao") == null) {
                response.sendRedirect("index.html");
                return;
            }

            String codigoDigitado = request.getParameter("codigo");
            String codigoSalvo = (String) sessao.getAttribute("codigoConfirmacao");

            if (codigoSalvo != null && codigoSalvo.equals(codigoDigitado)) {
                Usuario usuario = (Usuario) sessao.getAttribute("usuarioTemp");
                usuario.setDataCadastro(LocalDateTime.now());

                UsuarioDAO dao = new UsuarioDAO();
                dao.cadastrar(usuario);

                sessao.removeAttribute("codigoConfirmacao");
                sessao.removeAttribute("usuarioTemp");

                sessao.setAttribute("usuario", usuario);
                response.sendRedirect(request.getContextPath() + "/DashboardServlet");
            } else {
                // Código incorreto
                sessao.invalidate();
                response.sendRedirect("pages/cadastro-confirmar-incorreto.html");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
