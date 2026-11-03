<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Capitulo"%>
<%@page import="model.Projeto"%>
<%@page import="util.HtmlUtil"%>
<%
    List<Capitulo> capitulos =
        (List<Capitulo>) request.getAttribute("capitulos");
    Projeto projeto = (Projeto) request.getAttribute("projeto");

    if (capitulos == null || projeto == null) {
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
    <title>Leitura - <%= projeto.getNome() %>| EntreEntrelinhas</title>
    <link rel="stylesheet" href="<%=ctx%>/css/scriptorium.css">
</head>
<body>
<header>
    <h1>📖  Leitura - <%= projeto.getNome() %> </h1>
    <a href="<%=ctx%>/DashboardServlet">
        ← Voltar ao Dashboard
    </a>
</header>

<main>
    <section class="capitulos">
        <% if (capitulos.isEmpty()) { %>
            <div class="vazio">
                <h3>Esse projeto não tem capítulos </h3>
            </div>
        <% } else {
            for (Capitulo c : capitulos) { %>
                <a class="capitulo"
                   href="<%=ctx%>/LerCapituloServlet?projetoId=<%=projeto.getId()%>&capituloId=<%=c.getId()%>">
                    <div>
                        <h3>Capítulo <%=c.getNumero()%></h3>
                        <p><%=HtmlUtil.escapar(c.getTitulo())%></p>
                        <small>
                            <%=c.getQuantidadePalavras() == null
                                ? 0 : c.getQuantidadePalavras()%> palavras
                        </small>
                    </div>
                    <span class="seta">›</span>
                </a>
        <%  }
           }
        %>
    </section>
</main>
</body>
</html>
