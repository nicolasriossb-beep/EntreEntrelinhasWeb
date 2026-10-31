<%-- 
    Document   : personagens
    Created on : 29 de out. de 2026, 23:26:24
    Author     : Ryzen7RTX3050
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Personagem"%>
<%@page import="util.HtmlUtil"%>
<%
    Integer projetoId = (Integer) request.getAttribute("projetoId");
    List<Personagem> personagens =
        (List<Personagem>) request.getAttribute("personagens");

    if (projetoId == null || personagens == null) {
        response.sendError(500);
        return;
    }

    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Personagens | EntreEntrelinhas</title>

    <link rel="stylesheet" href="<%=ctx%>/css/personagens.css">
</head>

<body>

<header>
    <h1>👤 Personagens</h1>

    <a href="<%=ctx%>/AbrirProjetoServlet?id=<%=projetoId%>">
        ← Voltar ao Projeto
    </a>
</header>

<main>

    <section class="cabecalho">
        <h2>Todos os personagens do seu universo</h2>
        <p>Crie protagonistas, antagonistas e personagens secundários.</p>
    </section>

    <section class="lista">

        <% for (Personagem personagem : personagens) { %>

            <a class="card"
               href="<%=ctx%>/VisualizarPersonagemServlet?projetoId=<%=projetoId%>&personagemId=<%=personagem.getId()%>"
               style="text-decoration:none;">

                <h3>
                    <%=HtmlUtil.escapar(personagem.getNome())%>
                </h3>

                <p>
                    <% if (personagem.getPersonalidade() != null
                            && !personagem.getPersonalidade().isBlank()) { %>

                        <%=HtmlUtil.escapar(personagem.getPersonalidade())%>

                    <% } else if (personagem.getAparencia() != null
                            && !personagem.getAparencia().isBlank()) { %>

                        <%=HtmlUtil.escapar(personagem.getAparencia())%>

                    <% } else { %>

                        Personagem do seu universo

                    <% } %>
                </p>

            </a>

        <% } %>

        <a class="novo"
           href="<%=ctx%>/EditarPersonagemServlet?projetoId=<%=projetoId%>"
           style="text-decoration:none;">
            +
        </a>

    </section>

</main>

</body>
</html>

