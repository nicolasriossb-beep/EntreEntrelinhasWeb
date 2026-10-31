<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Nota"%>
<%@page import="util.HtmlUtil"%>

<%
    String ctx = request.getContextPath();
    int projetoId = (Integer) request.getAttribute("projetoId");
    List<Nota> notas = (List<Nota>) request.getAttribute("notas");
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Geral</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="<%=ctx%>/css/geral.css">
    </head>

    <body>
        <header>
            <h1>📝 Geral</h1>

            <a href="<%=ctx%>/pages/projetos.html?id=<%=projetoId%>"
               id="voltarProjeto">← Voltar ao Projeto</a>
        </header>

        <main>
            <section class="cabecalho">
                <h2>Anotações Livres</h2>
                <p>Ideias, sistemas de magia, culturas, curiosidades... tudo cabe aqui.</p>
            </section>

            <section class="mural">

                <% if (notas != null && !notas.isEmpty()) { %>

                    <% for (Nota nota : notas) { %>

                        <a class="postit"
                           href="<%=ctx%>/VisualizarNotaServlet?projetoId=<%=projetoId%>&notaId=<%=nota.getId()%>">

                            <h3>✨ <%=HtmlUtil.escapar(nota.getTitulo())%></h3>

                            <p><%=nota.getConteudo() == null || nota.getConteudo().isBlank()
                                    ? "Sem conteúdo."
                                    : HtmlUtil.escapar(nota.getConteudo())%></p>

                        </a>

                    <% } %>

                <% } else { %>

                    <p class="vazio">
                        Nenhuma anotação cadastrada ainda. Que tal registrar sua primeira ideia?
                    </p>

                <% } %>

                <a class="novo"
                   href="<%=ctx%>/EditarNotaServlet?projetoId=<%=projetoId%>">
                    +
                </a>

            </section>
        </main>
    </body>
</html>