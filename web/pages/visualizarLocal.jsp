
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

    String ctx = request.getContextPath();

    String tipo = local.getTipo() == null || local.getTipo().isBlank()
            ? "Não informado" : local.getTipo();

    String atmosfera = local.getAtmosfera() == null || local.getAtmosfera().isBlank()
            ? "Não informada" : local.getAtmosfera();

    String acontecimentos = local.getAcontecimentos() == null || local.getAcontecimentos().isBlank()
            ? "Não informados" : local.getAcontecimentos();

    String descricao = local.getDescricao() == null || local.getDescricao().isBlank()
            ? "Não informada" : local.getDescricao();
%>
<!DOCTYPE html>
<html>
    <head>
        <title><%=HtmlUtil.escapar(local.getNome())%></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="<%=ctx%>/css/local.css">
    </head>
    <body>

        <header>
            <h1>🗺️ <%=HtmlUtil.escapar(local.getNome())%></h1>
            <a href="<%=ctx%>/LocalServlet?id=<%=projetoId%>">
                ← Locais
            </a>
        </header>

        <main>
            <section class="ficha">

                <label>Nome</label>
                <p><%=HtmlUtil.escapar(local.getNome())%></p>

                <label>Tipo</label>
                <p><%=HtmlUtil.escapar(tipo)%></p>

                <label>Atmosfera</label>
                <p><%=HtmlUtil.escapar(atmosfera)%></p>

                <label>Acontecimentos</label>
                <p><%=HtmlUtil.escapar(acontecimentos)%></p>

                <label>Descrição</label>
                <p><%=HtmlUtil.escapar(descricao)%></p>

                <div class="botoes">
                    <a href="<%=ctx%>/EditarLocalServlet?projetoId=<%=projetoId%>&localId=<%=local.getId()%>">
                        ✏️ Editar
                    </a>
                </div>

            </section>
        </main>

    </body>
</html>
