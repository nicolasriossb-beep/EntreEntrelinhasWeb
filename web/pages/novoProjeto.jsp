<%-- 
    Document   : novoProjeto
    Created on : 3 de set. de 2026, 22:22:28
    Author     : Ryzen7RTX3050
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"></head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/novoProjeto.css">
    </head>
    <body>
         <header>
        <h1>✒️ Novo Projeto</h1>

        <a href="${pageContext.request.contextPath}/DashboardServlet">
            ← Voltar
        </a>
    </header>

    <main>

        <form method="post"
              action="${pageContext.request.contextPath}/CriarProjetoServlet">

            <div class="campo">

                <label for="nome">Nome do projeto</label>

                <input
                    type="text"
                    id="nome"
                    name="nome"
                    maxlength="100"
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
                ></textarea>

            </div>

            <div class="campo">

                <label for="tipo">Tipo</label>

                <input
                    type="text"
                    id="tipo"
                    name="tipo"
                    maxlength="50"
                    placeholder="Ex.: Fantasia, Romance, Ficção..."
                >

            </div>

            <div class="acoes">

                <a
                    href="${pageContext.request.contextPath}/DashboardServlet"
                    class="cancelar">
                    Cancelar
                </a>

                <button type="submit">
                    Criar Projeto
                </button>

            </div>

        </form>

    </main>
    </body>
</html>
