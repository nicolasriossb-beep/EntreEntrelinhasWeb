<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="model.Nota"%>
<%@page import="util.HtmlUtil"%>

<%
    String ctx = request.getContextPath();
    int projetoId = (Integer) request.getAttribute("projetoId");
    Nota nota = (Nota) request.getAttribute("nota");

    boolean editando = nota.getId() > 0;
%>

<!DOCTYPE html>
<html>
    <head>
        <title><%=editando ? "Editar Nota" : "Nova Nota"%></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="<%=ctx%>/css/nota.css">
    </head>

    <body>
        <header>
            <h1>📝 <%=editando ? "Editar Nota" : "Nova Nota"%></h1>

            <a href="<%=ctx%>/NotaServlet?id=<%=projetoId%>">
                ← Geral
            </a>
        </header>

        <main>
            <section class="nota">

                <form action="<%=ctx%>/SalvarNotaServlet" method="post">

                    <input type="hidden" name="projetoId" value="<%=projetoId%>">

                    <% if (editando) { %>
                        <input type="hidden" name="notaId"
                               value="<%=nota.getId()%>">
                    <% } %>

                    <label for="titulo">Título</label>

                    <input type="text" id="titulo" name="titulo"
                           placeholder="Título da anotação"
                           maxlength="100" required
                           value="<%=nota.getTitulo() == null ? "" : HtmlUtil.escapar(nota.getTitulo())%>">

                    <label for="conteudo">Conteúdo</label>

                    <textarea class="grande" id="conteudo" name="conteudo"
                              placeholder="Escreva qualquer ideia, anotação ou detalhe importante..."><%=nota.getConteudo() == null ? "" : HtmlUtil.escapar(nota.getConteudo())%></textarea>

                    <div class="botoes">
                        <button type="submit">💾 Salvar</button>

                        <a href="<%=ctx%>/NotaServlet?id=<%=projetoId%>">
                            Cancelar
                        </a>
                    </div>
                </form>

                <% if (editando) { %>
                    <form action="<%=ctx%>/ExcluirNotaServlet" method="post"
                          onsubmit="return confirm('Deseja realmente excluir esta nota?');">

                        <input type="hidden" name="projetoId"
                               value="<%=projetoId%>">

                        <input type="hidden" name="notaId"
                               value="<%=nota.getId()%>">

                        <div class="botoes">
                            <button type="submit">🗑️ Excluir</button>
                        </div>
                    </form>
                <% } %>

            </section>
        </main>
    </body>
</html>