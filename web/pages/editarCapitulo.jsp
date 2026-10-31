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

    boolean editando = c.getId() > 0;
    String ctx = request.getContextPath();
    String acao = editando
        ? ctx + "/AtualizarCapituloServlet"
        : ctx + "/CriarCapituloServlet";
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%=editando ? "Editar capítulo" : "Novo capítulo"%> | EntreEntrelinhas</title>
    <link rel="stylesheet" href="<%=ctx%>/css/capitulo.css">
    <link rel="stylesheet" href="<%=ctx%>/css/editor.css">
</head>
<body>
<header>
    <h1>🖋️ <%=editando ? "Editar capítulo" : "Novo capítulo"%></h1>
    <a href="<%=editando
        ? ctx + "/VisualizarCapituloServlet?projetoId=" + projetoId
            + "&capituloId=" + c.getId()
        : ctx + "/CapituloServlet?id=" + projetoId%>">
        ← Voltar
    </a>
</header>

<main>
    <form id="formCapitulo" method="post" action="<%=acao%>">
        <input type="hidden" name="projetoId" value="<%=projetoId%>">

        <% if (editando) { %>
            <input type="hidden" name="capituloId" value="<%=c.getId()%>">
        <% } %>

        <label for="numero">Número do capítulo</label>
        <input id="numero" type="number" name="numero"
               min="1" required
               value="<%=c.getNumero()%>" class="campo-numero">

        <label for="titulo">Título</label>
        <input id="titulo" type="text" name="titulo"
               maxlength="100" required
               placeholder="Título do capítulo"
               value="<%=HtmlUtil.escapar(c.getTitulo())%>"
               class="titulo">

        <div class="container">
            <!-- Barra de ferramentas -->
            <div class="toolbar">
              <button id="btn-dialogue" type="button" title="Alternar/Inserir Diálogo">
                💬 Diálogo
              </button>
              <button id="btn-separator" type="button" title="Inserir Separador de Cena">
                ➖ Separador
              </button>
              <div class="word-counter">
                Palavras: <span class="word-count">0</span>
              </div>
            </div>

            <div class="divider"></div>

            <div id="editor">
            </div>
        </div>

        <div class="contador">
            <span class="word-count">0</span> palavras
        </div>

        <label class="publicacao">
            <input type="checkbox" name="publico" value="true"
                <%=c.isPublico() ? "checked" : ""%>>
            Tornar este capítulo público
        </label>

        <div class="acoes">
            <button type="submit">💾 Salvar</button>
            <a class="botao-secundario"
               href="<%=editando
                   ? ctx + "/VisualizarCapituloServlet?projetoId=" + projetoId
                       + "&capituloId=" + c.getId()
                   : ctx + "/CapituloServlet?id=" + projetoId%>">
                Cancelar
            </a>
        </div>
    </form>
</main>

<script src="<%=ctx%>/js/scriptorium.js"></script>

<script>
        window.CONTEUDO_INICIAL = <%=c.getJson()%>;
</script>
<script src="<%=ctx%>/js/editor.js" type="module"></script>
</body>
</html>
