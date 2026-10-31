
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Personagem"%>
<%@page import="util.HtmlUtil"%>

<%
    Personagem personagem =
        (Personagem) request.getAttribute("personagem");

    Integer projetoId =
        (Integer) request.getAttribute("projetoId");

    if (personagem == null || projetoId == null) {
        response.sendError(500);
        return;
    }

    String ctx = request.getContextPath();

    String idade = personagem.getIdade();
    if (idade == null || idade.isBlank()) {
        idade = "Não informada";
    }

    String aparencia = personagem.getAparencia();
    if (aparencia == null || aparencia.isBlank()) {
        aparencia = "Não informada";
    }

    String personalidade = personagem.getPersonalidade();
    if (personalidade == null || personalidade.isBlank()) {
        personalidade = "Não informada";
    }

    String historia = personagem.getHistoria();
    if (historia == null || historia.isBlank()) {
        historia = "Não informada";
    }
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Personagem | EntreEntrelinhas</title>

    <link rel="stylesheet" href="<%=ctx%>/css/personagem.css">
</head>

<body>

<header>
    <h1>👤 <%=HtmlUtil.escapar(personagem.getNome())%></h1>

    <a href="<%=ctx%>/PersonagemServlet?id=<%=projetoId%>">
        ← Personagens
    </a>
</header>

<main>

    <section class="ficha">

        <label>Nome</label>
        <p><%=HtmlUtil.escapar(personagem.getNome())%></p>

        <label>Idade</label>
        <p class="idade"><%=HtmlUtil.escapar(idade)%></p>

        <label>Aparência</label>
        <p><%=HtmlUtil.escapar(aparencia)%></p>

        <label>Personalidade</label>
        <p><%=HtmlUtil.escapar(personalidade)%></p>

        <label>História</label>
        <p><%=HtmlUtil.escapar(historia)%></p>

        <div class="botoes">

            <a href="<%=ctx%>/EditarPersonagemServlet?projetoId=<%=projetoId%>&personagemId=<%=personagem.getId()%>"
               style="text-decoration:none;">
                <button type="button">✏️ Editar</button>
            </a>

            <form action="<%=ctx%>/ExcluirPersonagemServlet"
                  method="POST"
                  onsubmit="return confirm('Tem certeza que deseja excluir este personagem?');">

                <input type="hidden"
                       name="projetoId"
                       value="<%=projetoId%>">

                <input type="hidden"
                       name="personagemId"
                       value="<%=personagem.getId()%>">

                <button type="submit">🗑️ Excluir</button>

            </form>

        </div>

    </section>

</main>

</body>
</html>