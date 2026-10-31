<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Capitulo"%>
<%@page import="util.HtmlUtil"%>
<%
    Capitulo c = (Capitulo) request.getAttribute("capitulo");
    Integer projetoId = (Integer) request.getAttribute("projetoId");

    if (c == null || projetoId == null) {
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
    <title><%=HtmlUtil.escapar(c.getTitulo())%> | EntreEntrelinhas</title>
    <link rel="stylesheet" href="<%=ctx%>/css/capitulo.css">
    <link rel="stylesheet" href="<%=ctx%>/css/leitor.css">
</head>
<body>
<header>
    <h1>📖 <%=HtmlUtil.escapar(c.getTitulo())%></h1>
    <a href="<%=ctx%>/CapituloServlet?id=<%=projetoId%>">
        ← Scriptorium
    </a>
</header>

<main class="leitura">
    <section class="metadados">
        <span>Capítulo <%=c.getNumero()%></span>
        <span><%=c.getQuantidadePalavras() == null
            ? 0 : c.getQuantidadePalavras()%> palavras</span>
        <span><%=c.isPublico() ? "Público" : "Privado"%></span>
    </section>

    <h2 class="titulo-leitura">
        <%=HtmlUtil.escapar(c.getTitulo())%>
    </h2>

    <!--
    <article class="conteudo-leitura"><%=HtmlUtil.escapar(c.getTexto())%></article>
    -->

    <article class="leitura-capitulo">
        <%=c.getHtml()%>
    </article>

    <div class="acoes">
        <a class="botao"
           href="<%=ctx%>/EditarCapituloServlet?projetoId=<%=projetoId%>&capituloId=<%=c.getId()%>">
            ✏️ Editar
        </a>

        <form method="post"
              action="<%=ctx%>/ExcluirCapituloServlet"
              onsubmit="return confirm('Tem certeza de que deseja excluir este capítulo?');">
            <input type="hidden" name="projetoId" value="<%=projetoId%>">
            <input type="hidden" name="capituloId" value="<%=c.getId()%>">
            <button type="submit" class="botao excluir">🗑️ Excluir</button>
        </form>
    </div>
</main>
</body>
</html>
