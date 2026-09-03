<%-- 
    Document   : dashboard
    Created on : 18 de jul. de 2026, 15:04:51
    Author     : Ryzen7RTX3050
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>JSP Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    </head>
    <body>
         <header>

            <h1>📖 Entre Entrelinhas</h1>

            <nav>

                <a href="${pageContext.request.contextPath}/BibliotecaServlet">Biblioteca</a>

                <a href="#">Perfil</a>

                <a href="index.html">Sair</a>

            </nav>

        </header>

        <main>

            <section class="boasVindas">

                <h2>

                    ${saudacao}, ${usuario.nomeUsuario}.

                </h2>

                <p>

                    Em qual história vamos mergulhar hoje?

                </p>

            </section>

            <section class="novoProjeto">

                <form action="${pageContext.request.contextPath}/CriarProjetoServlet" method="post">
                    <div>
                        <label for="nome">Nome</label>
                        <input 
                            id="nome"
                            type="text"
                            name="nome"
                            placeholder="Nome do projeto"
                            required>
                    </div>

                    <div>
                        <label for="descricao">Descrição</label>
                        <input 
                            id="descricao"
                            type="text"
                            name="descricao"
                            placeholder="Descrição do projeto"
                            required>
                    </div>

                    <div>
                        <label for="tipo">Tipo</label>
                        <input 
                            id="tipo"
                            type="text"
                            name="tipo"
                            placeholder="Tipo do projeto"
                            required>
                    </div>

                    <button type="submit">
                        + Criar novo projeto
                    </button>
                </form>

            </section>

            <section class="carrossel">

                <h2>

                    ✨ Inspiração

                </h2>

                <div class="card">

                    <p>

                        "Toda grande história começou com uma página em branco."

                    </p>

                </div>

            </section>

            <section class="avaliacao">

                <h2>

                    💬 Avalie o Entre Entrelinhas

                </h2>

                <p>

                    Sua opinião é muito importante para nós.

                </p>

                <a href="#">

                    Avaliar Projeto

                </a>

            </section>

            <section class="sobre">

                <h2>

                    📚 Sobre o Entre Entrelinhas

                </h2>

                <p>

                    O Entre Entrelinhas foi desenvolvido para auxiliar escritores na organização de personagens, capítulos, cronologias, locais, conflitos e anotações gerais em um único ambiente.

                </p>

            </section>

        </main>
    </body>
</html>
