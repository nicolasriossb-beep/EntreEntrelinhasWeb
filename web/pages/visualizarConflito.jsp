<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="model.Conflito"%>
<%@page import="util.HtmlUtil"%>

<%
    String ctx = request.getContextPath();
    int projetoId = (Integer) request.getAttribute("projetoId");
    Conflito conflito = (Conflito) request.getAttribute("conflito");
%>

<!DOCTYPE html>
<html>
    <head>
        <title><%=HtmlUtil.escapar(conflito.getNome())%></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="<%=ctx%>/css/conflito.css">
    </head>

    <body>
        <header>
            <h1>⚔️ <%=HtmlUtil.escapar(conflito.getNome())%></h1>

            <a href="<%=ctx%>/ConflitoServlet?id=<%=projetoId%>">
                ← Conflitos
            </a>
        </header>

        <main>
            <section class="ficha">

                <label>Escala</label>
                <p><%=conflito.getEscala() == null || conflito.getEscala().isBlank()
                        ? "Não definida"
                        : HtmlUtil.escapar(conflito.getEscala())%></p>

                <label>Lados Envolvidos</label>
                <p><%=conflito.getLados() == null || conflito.getLados().isBlank()
                        ? "Nenhuma informação cadastrada."
                        : HtmlUtil.escapar(conflito.getLados())%></p>

                <label>Motivação</label>
                <p><%=conflito.getMotivacao() == null || conflito.getMotivacao().isBlank()
                        ? "Nenhuma informação cadastrada."
                        : HtmlUtil.escapar(conflito.getMotivacao())%></p>

                <label>Descrição</label>
                <p><%=conflito.getDescricao() == null || conflito.getDescricao().isBlank()
                        ? "Nenhuma descrição cadastrada."
                        : HtmlUtil.escapar(conflito.getDescricao())%></p>

                <div class="botoes">
                    <a href="<%=ctx%>/EditarConflitoServlet?projetoId=<%=projetoId%>&conflitoId=<%=conflito.getId()%>">
                        ✏️ Editar Conflito
                    </a>

                    <a href="<%=ctx%>/ConflitoServlet?id=<%=projetoId%>">
                        Voltar
                    </a>
                </div>

            </section>
        </main>
    </body>
</html>