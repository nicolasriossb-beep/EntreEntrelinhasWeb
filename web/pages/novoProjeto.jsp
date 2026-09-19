<%-- 
    Document   : novoProjeto
    Created on : 3 de set. de 2026, 22:22:28
    Author     : Ryzen7RTX3050
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Projeto"%>

<%
    Projeto projeto = (Projeto) request.getAttribute("projeto");

    boolean editando = projeto != null;

    String acao = editando
            ? "AtualizarProjetoServlet"
            : "CriarProjetoServlet";

    String titulo = editando
            ? "✏️ Editar Projeto"
            : "✒️ Novo Projeto";

    String botao = editando
            ? "Salvar Alterações"
            : "Criar Projeto";
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title><%= titulo %></title>

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/css/novoProjeto.css">
    </head>

    <body>

        <header>
            <h1><%= titulo %></h1>

            <a href="${pageContext.request.contextPath}/DashboardServlet">
                ← Voltar
            </a>
        </header>

        <main>

            <form method="post"
                  action="${pageContext.request.contextPath}/<%= acao %>">

                <% if (editando) { %>
                    <input type="hidden"
                           name="id"
                           value="<%= projeto.getId() %>">
                <% } %>

                <div class="campo">

                    <label for="nome">Nome do projeto</label>

                    <input
                        type="text"
                        id="nome"
                        name="nome"
                        maxlength="100"
                        value="<%= editando ? projeto.getNome() : "" %>"
                        required
                    >

                </div>

                <div class="campo">

                    <label for="descricao">Descrição</label>

                    <textarea
                        id="descricao"
                        name="descricao"
                        maxlength="500"
                        rows="5"
                    ><%= editando ? projeto.getDescricao() : "" %></textarea>

                </div>

                <div class="campo">

                    <label for="tipo">Tipo</label>

                    <input
                        type="text"
                        id="tipo"
                        name="tipo"
                        maxlength="50"
                        placeholder="Ex.: Fantasia, Romance, Ficção..."
                        value="<%= editando ? projeto.getTipo() : "" %>"
                    >

                </div>

                <div class="acoes">

                    <a
                        href="${pageContext.request.contextPath}/DashboardServlet"
                        class="cancelar">
                        Cancelar
                    </a>

                    <button type="submit">
                        <%= botao %>
                    </button>

                </div>

            </form>

        </main>

    </body>
</html>
