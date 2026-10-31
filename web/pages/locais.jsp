<%@page import="java.util.List"%>
<%@page import="model.Local"%>
<%@page import="util.HtmlUtil"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Integer projetoId = (Integer) request.getAttribute("projetoId");
    List<Local> locais = (List<Local>) request.getAttribute("locais");

    if (projetoId == null || locais == null) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return;
    }

    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Locais</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="<%=ctx%>/css/locais.css">
    </head>
    <body>

        <header>
            <h1>🗺️ Locais</h1>

            <a href="<%=ctx%>/pages/projetos.html?id=<%=projetoId%>" id="voltarProjeto">
                ← Voltar ao Projeto
            </a>
        </header>

        <main>

            <section class="cabecalho">
                <h2>Construa o mundo da sua história</h2>
                <p>Registre cidades, reinos, florestas e qualquer lugar importante.</p>
            </section>

            <section class="lista">

                <% if (locais.isEmpty()) { %>

                    <p class="vazio">
                        Nenhum local cadastrado ainda. Que tal criar o primeiro?
                    </p>

                <% } else { %>

                    <% for (Local local : locais) { %>

                        <a class="card"
                           href="<%=ctx%>/VisualizarLocalServlet?projetoId=<%=projetoId%>&localId=<%=local.getId()%>">

                            <h3>🗺️ <%=HtmlUtil.escapar(local.getNome())%></h3>

                            <p>
                                <%=local.getTipo() == null || local.getTipo().isBlank()
                                        ? "Tipo não informado"
                                        : HtmlUtil.escapar(local.getTipo())%>
                            </p>

                        </a>

                    <% } %>

                <% } %>

                <a class="novo"
                   href="<%=ctx%>/EditarLocalServlet?projetoId=<%=projetoId%>">
                    +
                </a>

            </section>

        </main>

        <script src="<%=ctx%>/js/navegacaoProjeto.js"></script>

    </body>
</html>
