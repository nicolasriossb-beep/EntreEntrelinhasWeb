<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Personagem"%>
<%@page import="util.HtmlUtil"%>

<%
    Personagem personagem = (Personagem) request.getAttribute("personagem");
    Integer projetoId = (Integer) request.getAttribute("projetoId");

    if (personagem == null || projetoId == null) {
        response.sendError(500);
        return;
    }

    boolean editando = personagem.getId() > 0;
    String ctx = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Ficha do Personagem | EntreEntrelinhas</title>

    <link rel="stylesheet" href="<%=ctx%>/css/personagem.css">
</head>

<body>

<header>
    <h1>👤 <%= editando ? "Editar Personagem" : "Novo Personagem" %></h1>

    <a href="<%=ctx%>/PersonagemServlet?id=<%=projetoId%>">
        ← Personagens
    </a>
</header>

<main>

    <form class="ficha"
          action="<%=ctx%>/SalvarPersonagemServlet"
          method="POST">

        <input type="hidden" name="projetoId" value="<%=projetoId%>">

        <% if (editando) { %>
            <input type="hidden"
                   name="personagemId"
                   value="<%=personagem.getId()%>">
        <% } %>

        <label for="nome">Nome</label>

        <input type="text"
               id="nome"
               name="nome"
               value="<%=HtmlUtil.escapar(personagem.getNome())%>"
               maxlength="150"
               required>

        <label for="idade">Idade</label>

        <input type="text"
               id="idade"
               name="idade"
               value="<%=HtmlUtil.escapar(personagem.getIdade())%>"
               maxlength="100">

        <label for="aparencia">Aparência</label>

        <textarea id="aparencia" name="aparencia"><%=HtmlUtil.escapar(personagem.getAparencia())%></textarea>

        <label for="personalidade">Personalidade</label>

        <textarea id="personalidade" name="personalidade"><%=HtmlUtil.escapar(personagem.getPersonalidade())%></textarea>

        <label for="historia">História</label>

        <textarea id="historia" name="historia" class="grande"><%=HtmlUtil.escapar(personagem.getHistoria())%></textarea>

        <div class="botoes">

            <button type="submit">💾 Salvar</button>

            <a href="<%=ctx%>/PersonagemServlet?id=<%=projetoId%>"
               style="text-decoration:none;">
                <button type="button">Cancelar</button>
            </a>

        </div>

    </form>

</main>

</body>
</html>