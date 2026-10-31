<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Conflito"%>
<%@page import="util.HtmlUtil"%>

<%
    String ctx = request.getContextPath();
    int projetoId = (Integer) request.getAttribute("projetoId");
    List<Conflito> conflitos =
            (List<Conflito>) request.getAttribute("conflitos");
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Conflitos</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="<%=ctx%>/css/conflitos.css">
    </head>
    <body>
        <header>
            <h1>⚔️ Conflitos</h1>
            <a href="<%=ctx%>/pages/projetos.html?id=<%=projetoId%>"
               id="voltarProjeto">← Voltar ao Projeto</a>
        </header>

        <main>
            <section class="cabecalho">
                <h2>Conflitos da História</h2>
                <p>Organize guerras, rivalidades, dilemas e qualquer obstáculo importante.</p>
            </section>

            <section class="lista">

                <% if (conflitos != null && !conflitos.isEmpty()) { %>

                    <% for (Conflito conflito : conflitos) { %>

                        <a class="card"
                           href="<%=ctx%>/VisualizarConflitoServlet?projetoId=<%=projetoId%>&conflitoId=<%=conflito.getId()%>">

                            <h3>⚔️ <%=HtmlUtil.escapar(conflito.getNome())%></h3>

                            <p>Escala: <%=conflito.getEscala() == null || conflito.getEscala().isBlank()
                                    ? "Não definida"
                                    : HtmlUtil.escapar(conflito.getEscala())%></p>

                        </a>

                    <% } %>

                <% } else { %>

                    <p class="vazio">Nenhum conflito cadastrado ainda. Que tal criar o primeiro?</p>

                <% } %>

                <a class="novo"
                   href="<%=ctx%>/EditarConflitoServlet?projetoId=<%=projetoId%>">
                    +
                </a>

            </section>
        </main>
    </body>
</html>