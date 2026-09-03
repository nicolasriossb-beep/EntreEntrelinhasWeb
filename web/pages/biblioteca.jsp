<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    pageContext.setAttribute("formatter", DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm"));
%>

<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/biblioteca.css">
    </head>
    <body>

        <header>

            <h1>📚 Minha Biblioteca</h1>

            <a href="${pageContext.request.contextPath}/DashboardServlet">

                ← Dashboard

            </a>

        </header>

        <main>

            <div class="novoProjeto">

                <button>

                    ＋ Novo Projeto

                </button>

            </div>

            <div class="projetos">
                <c:forEach var="projeto" items="${usuario.projetos}">
                    <div class = "card">
                        <h2>${projeto.nome}</h2>
                        <p>${projeto.descricao}</p>
                        <p>${projeto.tipo}</p>
                        <p>Criado em ${projeto.dataCriacao.format(formatter)}</p>
                        <c:if test="${projeto.dataUltimaEdicao != null}">
                            <p>Última edição em ${projeto.dataUltimaEdicao}</p>
                        </c:if>
                        <button>Abrir Projeto</button>
                    </div>
                </c:forEach>
            </div>

            <br />

            <div class="projetos">

                <div class="card">

                    <h2>Elion</h2>
                 
                    <p>

                        Fantasia • Última edição hoje

                    </p>

                    <button>

                        Abrir Projeto

                    </button>

                </div>

                <div class="card">

                    <h2>Entre Mundos</h2>

                    <p>

                        Ficção Científica

                    </p>

                    <button>

                        Abrir Projeto

                    </button>

                </div>

                <div class="card">

                    <h2>Projeto Teste</h2>

                    <p>

                        Rascunhos

                    </p>


                </div>

            </div>

        </main>
    </body>
</html>
