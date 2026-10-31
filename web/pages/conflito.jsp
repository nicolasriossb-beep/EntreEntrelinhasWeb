<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="model.Conflito"%>
<%@page import="util.HtmlUtil"%>

<%
    String ctx = request.getContextPath();
    int projetoId = (Integer) request.getAttribute("projetoId");
    Conflito conflito = (Conflito) request.getAttribute("conflito");

    boolean editando = conflito.getId() > 0;
%>

<!DOCTYPE html>
<html>
    <head>
        <title><%=editando ? "Editar Conflito" : "Novo Conflito"%></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="<%=ctx%>/css/conflito.css">
    </head>

    <body>
        <header>
            <h1>⚔️ <%=editando
                    ? HtmlUtil.escapar(conflito.getNome())
                    : "Novo Conflito"%></h1>

            <a href="<%=ctx%>/ConflitoServlet?id=<%=projetoId%>">
                ← Conflitos
            </a>
        </header>

        <main>
            <section class="ficha">

                <form action="<%=ctx%>/SalvarConflitoServlet" method="post">

                    <input type="hidden" name="projetoId" value="<%=projetoId%>">

                    <% if (editando) { %>
                        <input type="hidden" name="conflitoId"
                               value="<%=conflito.getId()%>">
                    <% } %>

                    <label for="nome">Nome</label>
                    <input type="text" id="nome" name="nome"
                           placeholder="Nome do conflito"
                           maxlength="100" required
                           value="<%=conflito.getNome() == null ? "" : HtmlUtil.escapar(conflito.getNome())%>">

                    <label for="escala">Escala</label>
                    <input type="text" id="escala" name="escala"
                           placeholder="Pessoal, Nacional, Mundial..."
                           maxlength="50"
                           value="<%=conflito.getEscala() == null ? "" : HtmlUtil.escapar(conflito.getEscala())%>">

                    <label for="lados">Lados Envolvidos</label>
                    <textarea id="lados" name="lados"
                              placeholder="Quem participa desse conflito?"><%=conflito.getLados() == null ? "" : HtmlUtil.escapar(conflito.getLados())%></textarea>

                    <label for="motivacao">Motivação</label>
                    <textarea id="motivacao" name="motivacao"
                              placeholder="Por que esse conflito começou?"><%=conflito.getMotivacao() == null ? "" : HtmlUtil.escapar(conflito.getMotivacao())%></textarea>

                    <label for="descricao">Descrição</label>
                    <textarea class="grande" id="descricao" name="descricao"
                              placeholder="Descreva o conflito detalhadamente"><%=conflito.getDescricao() == null ? "" : HtmlUtil.escapar(conflito.getDescricao())%></textarea>

                    <div class="botoes">
                        <button type="submit">💾 Salvar</button>

                        <a href="<%=ctx%>/ConflitoServlet?id=<%=projetoId%>">
                            Cancelar
                        </a>
                    </div>
                </form>

                <% if (editando) { %>
                    <form action="<%=ctx%>/ExcluirConflitoServlet" method="post"
                          onsubmit="return confirm('Deseja realmente excluir este conflito?');">

                        <input type="hidden" name="projetoId"
                               value="<%=projetoId%>">

                        <input type="hidden" name="conflitoId"
                               value="<%=conflito.getId()%>">

                        <div class="botoes">
                            <button type="submit">🗑️ Excluir</button>
                        </div>
                    </form>
                <% } %>

            </section>
        </main>
    </body>
</html>