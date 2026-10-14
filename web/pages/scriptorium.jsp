<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Capitulo"%>
<%@page import="util.HtmlUtil"%>
<%
    List<Capitulo> capitulos =
        (List<Capitulo>) request.getAttribute("capitulos");
    Integer projetoId =
        (Integer) request.getAttribute("projetoId");

    if (capitulos == null || projetoId == null) {
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
    <title>Scriptorium | EntreEntrelinhas</title>
    <link rel="stylesheet" href="<%=ctx%>/css/scriptorium.css">
</head>
<body>
<header>
    <h1>🖋️ Scriptorium</h1>
    <a href="<%=ctx%>/pages/projetos.html?id=<%=projetoId%>">
        ← Voltar ao Projeto
    </a>
</header>

<main>
    <section class="livro">
        <h2>📖 Continue escrevendo sua história</h2>
        <p>Cada capítulo é uma nova página do seu universo.</p>
    </section>

    <section class="cabecalho-capitulos">
        <h2>Seus capítulos</h2>
        <a class="botao-novo"
           href="<%=ctx%>/EditarCapituloServlet?projetoId=<%=projetoId%>">
            + Novo capítulo
        </a>
    </section>

    <section class="capitulos">
        <% if (capitulos.isEmpty()) { %>
            <div class="vazio">
                <h3>Sua história começa aqui ✨</h3>
                <p>Você ainda não criou capítulos neste projeto.</p>
            </div>
        <% } else {
            for (Capitulo c : capitulos) { %>
                <a class="capitulo"
                   href="<%=ctx%>/VisualizarCapituloServlet?projetoId=<%=projetoId%>&capituloId=<%=c.getId()%>">
                    <div>
                        <h3>Capítulo <%=c.getNumero()%></h3>
                        <p><%=HtmlUtil.escapar(c.getTitulo())%></p>
                        <small>
                            <%=c.getQuantidadePalavras() == null
                                ? 0 : c.getQuantidadePalavras()%> palavras
                            · <%=c.isPublico() ? "Público" : "Privado"%>
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
