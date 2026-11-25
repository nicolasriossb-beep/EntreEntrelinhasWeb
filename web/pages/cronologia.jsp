<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.EventoCronologia"%>
<%@page import="util.HtmlUtil"%>

<%
    int projetoId = (Integer) request.getAttribute("projetoId");

    List<EventoCronologia> eventos =
            (List<EventoCronologia>) request.getAttribute("eventos");

    String ctx = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Cronologia - EntreEntrelinhas</title>

    <link rel="stylesheet" href="<%=ctx%>/css/cronologia.css">
</head>

<body>

<header>
    <h1>⏳ Cronologia</h1>

    <a href="<%=ctx%>/pages/projetos.html?id=<%=projetoId%>">
        ← Voltar ao Projeto
    </a>
</header>

<main>

    <section class="topo">
        <h2>Linha do Tempo</h2>
        <p>Organize os acontecimentos da sua história.</p>
    </section>

    <div class="acoes-topo">
        <button type="button" id="abrirNovoEvento">
            + Novo acontecimento
        </button>
    </div>

    <section class="formulario-evento" id="formularioNovo" hidden>

        <h2>Novo acontecimento</h2>

        <form action="<%=ctx%>/SalvarEventoServlet" method="POST">

            <input type="hidden" name="projetoId"
                   value="<%=projetoId%>">

            <label for="novoTitulo">Título</label>
            <input type="text" id="novoTitulo" name="titulo"
                   maxlength="100" required>

            <label for="novaDescricao">Descrição</label>
            <textarea id="novaDescricao" name="descricao"
                      rows="5"></textarea>

            <div class="botoes">
                <button type="submit">Salvar acontecimento</button>
                <button type="button" class="secundario"
                        id="cancelarNovo">Cancelar</button>
            </div>

        </form>
    </section>

    <section class="timeline" id="timeline">

        <% if (eventos == null || eventos.isEmpty()) { %>

            <div class="vazio">
                <h3>Sua história começa aqui...</h3>
                <p>Adicione o primeiro acontecimento à cronologia.</p>
            </div>

        <% } else { %>

            <% for (EventoCronologia evento : eventos) { %>

                <article class="evento"
                         data-evento-id="<%=evento.getId()%>"
                         draggable="true">

                    <div class="marcador">
                        <button type="button" class="bolinha"
                                aria-label="Abrir acontecimento"
                                aria-expanded="false">
                        </button>
                    </div>

                    <div class="conteudo">

                        <h3>
                            <%=HtmlUtil.escapar(evento.getTitulo())%>
                        </h3>

                        <div class="detalhes" hidden>

                            <div class="descricao">
                                <%=HtmlUtil.escapar(
                                    evento.getDescricao() == null
                                    ? "" : evento.getDescricao())%>
                            </div>

                            <div class="botoes-evento">

                                <button type="button" class="editarEvento">
                                    Editar
                                </button>

                                <form action="<%=ctx%>/ExcluirEventoServlet"
                                      method="POST"
                                      onsubmit="return confirm('Deseja excluir este acontecimento?');">

                                    <input type="hidden" name="projetoId"
                                           value="<%=projetoId%>">

                                    <input type="hidden" name="eventoId"
                                           value="<%=evento.getId()%>">

                                    <button type="submit" class="excluir">
                                        Excluir
                                    </button>
                                </form>

                            </div>

                        </div>

                        <form class="formulario-edicao"
                              action="<%=ctx%>/SalvarEventoServlet"
                              method="POST" hidden>

                            <input type="hidden" name="projetoId"
                                   value="<%=projetoId%>">

                            <input type="hidden" name="eventoId"
                                   value="<%=evento.getId()%>">

                            <label>Título</label>

                            <input type="text" name="titulo"
                                   maxlength="100" required
                                   value="<%=HtmlUtil.escapar(evento.getTitulo())%>">

                            <label>Descrição</label>

                            <textarea name="descricao" rows="5"><%=HtmlUtil.escapar(
                                evento.getDescricao() == null
                                ? "" : evento.getDescricao())%></textarea>

                            <div class="botoes">
                                <button type="submit">Salvar alterações</button>
                                <button type="button"
                                        class="cancelarEdicao secundario">
                                    Cancelar
                                </button>
                            </div>

                        </form>

                    </div>

                </article>

            <% } %>

        <% } %>

        <div class="novo" id="novoEventoFinal">
            +
        </div>

    </section>

    <p class="dica-arrastar">
        Dica: arraste os acontecimentos para reorganizar sua história.
    </p>

    <input type="hidden" id="projetoId" value="<%=projetoId%>">

</main>

<script src="<%=ctx%>/js/cronologia.js"></script>

</body>
</html>