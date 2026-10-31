<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="model.Nota"%>
<%@page import="util.HtmlUtil"%>
<%@page import="java.time.format.DateTimeFormatter"%>

<%
    String ctx = request.getContextPath();
    int projetoId = (Integer) request.getAttribute("projetoId");
    Nota nota = (Nota) request.getAttribute("nota");

    String dataFormatada = "Data não disponível";

    if (nota.getDataUltimaEdicao() != null) {
        dataFormatada = nota.getDataUltimaEdicao().format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm")
        );
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <title><%=HtmlUtil.escapar(nota.getTitulo())%></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="<%=ctx%>/css/nota.css">
    </head>

    <body>
        <header>
            <h1>📝 <%=HtmlUtil.escapar(nota.getTitulo())%></h1>

            <a href="<%=ctx%>/NotaServlet?id=<%=projetoId%>">
                ← Geral
            </a>
        </header>

        <main>
            <section class="nota">

                <label>Conteúdo</label>

                <p class="conteudo-nota"><%=nota.getConteudo() == null
                        || nota.getConteudo().isBlank()
                        ? "Esta nota ainda não possui conteúdo."
                        : HtmlUtil.escapar(nota.getConteudo())%></p>

                <p class="data-edicao">
                    Última edição: <%=dataFormatada%>
                </p>

                <div class="botoes">

                    <a href="<%=ctx%>/EditarNotaServlet?projetoId=<%=projetoId%>&notaId=<%=nota.getId()%>">
                        ✏️ Editar Nota
                    </a>

                    <a href="<%=ctx%>/NotaServlet?id=<%=projetoId%>">
                        Voltar
                    </a>

                </div>

            </section>
        </main>
    </body>
</html>