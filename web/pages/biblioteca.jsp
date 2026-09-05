<%-- 
    Document   : biblioteca
    Created on : 4 de set. de 2026, 21:20:31
    Author     : Ryzen7RTX3050
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="model.Projeto"%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Minha Biblioteca</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/biblioteca.css">

</head>

<body>

    <header>

        <h1>📚 Minha Biblioteca</h1>

        <a href="${pageContext.request.contextPath}/DashboardServlet">
            ← Dashboard
        </a>

    </header>

    <main>

        <div class="novoProjeto">

            <a href="${pageContext.request.contextPath}/pages/novoProjeto.jsp">
                ＋ Novo Projeto
            </a>

        </div>


        <div class="projetos">

            <%
                List<Projeto> projetos =
                    (List<Projeto>) request.getAttribute("projetos");

                if (projetos != null && !projetos.isEmpty()) {

                    for (Projeto projeto : projetos) {
            %>

                <div class="card">

                    <h2>
                        <%= projeto.getNome() %>
                    </h2>

                    <p>
                        <%= projeto.getTipo() != null
                            ? projeto.getTipo()
                            : "Projeto sem tipo" %>
                    </p>

                    <a href="#">
                        Abrir Projeto
                    </a>

                </div>

            <%
                    }

                } else {
            %>

                <div class="semProjetos">

                    <h2>📖 Sua biblioteca está vazia</h2>

                    <p>
                        Crie seu primeiro projeto e comece
                        a escrever sua história.
                    </p>

                </div>

            <%
                }
            %>

        </div>

    </main>

</body>
</html>