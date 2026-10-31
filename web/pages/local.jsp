
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="model.Local"%>
<%@page import="util.HtmlUtil"%>
<%
    Integer projetoId = (Integer) request.getAttribute("projetoId");
    Local local = (Local) request.getAttribute("local");

    if (projetoId == null || local == null) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return;
    }

    boolean editando = local.getId() > 0;
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
    <head>
        <title><%=editando ? "Editar Local" : "Novo Local"%></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="<%=ctx%>/css/local.css">
    </head>
    <body>

        <header>
            <h1>🗺️ <%=editando ? HtmlUtil.escapar(local.getNome()) : "Novo Local"%></h1>
            <a href="<%=ctx%>/LocalServlet?id=<%=projetoId%>">← Locais</a>
        </header>

        <main>
            <section class="ficha">

                <form action="<%=ctx%>/SalvarLocalServlet" method="POST">

                    <input type="hidden" name="projetoId"
                           value="<%=projetoId%>">

                    <% if (editando) { %>
                        <input type="hidden" name="localId"
                               value="<%=local.getId()%>">
                    <% } %>

                    <label>Nome</label>
                    <input type="text" name="nome"
                           maxlength="100" required
                           value="<%=local.getNome() == null ? "" : HtmlUtil.escapar(local.getNome())%>"
                           placeholder="Nome do local">

                    <label>Tipo</label>
                    <input type="text" name="tipo"
                           maxlength="50"
                           value="<%=local.getTipo() == null ? "" : HtmlUtil.escapar(local.getTipo())%>"
                           placeholder="Cidade, Reino, Floresta...">

                    <label>Atmosfera</label>
                    <textarea name="atmosfera"
                              placeholder="Como esse lugar transmite emoções?"><%=local.getAtmosfera() == null ? "" : HtmlUtil.escapar(local.getAtmosfera())%></textarea>

                    <label>Acontecimentos</label>
                    <textarea name="acontecimentos"
                              placeholder="O que aconteceu aqui?"><%=local.getAcontecimentos() == null ? "" : HtmlUtil.escapar(local.getAcontecimentos())%></textarea>

                    <label>Descrição</label>
                    <textarea class="grande" name="descricao"
                              placeholder="Descreva esse local"><%=local.getDescricao() == null ? "" : HtmlUtil.escapar(local.getDescricao())%></textarea>

                    <div class="botoes">
                        <button type="submit">💾 Salvar</button>
                        <a href="<%=ctx%>/LocalServlet?id=<%=projetoId%>">
                            Cancelar
                        </a>
                    </div>

                </form>

                <% if (editando) { %>
                    <form action="<%=ctx%>/ExcluirLocalServlet" method="POST"
                          onsubmit="return confirm('Deseja realmente excluir este local?');">

                        <input type="hidden" name="projetoId"
                               value="<%=projetoId%>">

                        <input type="hidden" name="localId"
                               value="<%=local.getId()%>">

                        <button type="submit">🗑️ Excluir</button>
                    </form>
                <% } %>

            </section>
        </main>

    </body>
</html>
