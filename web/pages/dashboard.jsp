<%-- 
    Document   : dashboard
    Created on : 18 de jul. de 2026, 15:04:51
    Author     : Ryzen7RTX3050
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    </head>
    <aside class="sidebar" id="sidebar">

        <button class="fechar" onclick="fecharSidebar()">×</button>

        <div class="profile-photo-container">

            <img
                id="imagem-perfil2"
                src="${pageContext.request.contextPath}/img/anonimo 2.png"
                alt="Avatar"
                onclick="abrirOpcoes()"
                >

            <button
                id="botao-editarimagem"
                onclick="abrirOpcoes()">
                🖊
            </button>

            <div class="photo-options" id="photoOptions">

                <button
                    id="botao-editarimagem"
                    onclick="abrirCarrossel()">
                    🖊
                </button>

            </div>

        </div>

        <h2>${usuario.nomeUsuario}</h2>

        <a id="alterar-senha" href="#">
            Alterar Senha
        </a>

        <a id="excluir-conta" href="#">
            Excluir Conta
        </a>

        <a id="sair" href="${pageContext.request.contextPath}/index.html">
            Sair
        </a>

    </aside>
        <div class="avatar-overlay" id="avatarOverlay">

            <div class="avatar-carrossel">

                <button class="avatar-fechar" onclick="fecharCarrossel()">
                    ×
                </button>

                <button class="avatar-anterior" onclick="avatarAnterior()">
                    ‹
                </button>

                <div class="avatar-area">
                    <img
                        id="avatarSelecionado"
                        src="${pageContext.request.contextPath}/img/anonimo 2.png"
                        alt="Avatar selecionado"
                        >
                </div>
                        <button
                            class="avatar-confirmar"
                            onclick="selecionarAvatar()">
                            Usar este avatar
                        </button>

                <button class="avatar-proximo" onclick="proximoAvatar()">
                    ›
                </button>

                <div class="avatar-indicadores" id="avatarIndicadores">
                </div>

            </div>

        </div>
    <body>
         <header>

            <h1>📖 Entre Entrelinhas</h1>

            <nav>

                <a href="${pageContext.request.contextPath}/BibliotecaServlet">
                    Biblioteca
                </a>

                <img
                    id="imagem-perfil"
                    src="${pageContext.request.contextPath}/img/anonimo 2.png"
                    onclick="abrirSidebar()"
                    alt="Perfil"
                    >

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

                <a href="${pageContext.request.contextPath}/pages/novoProjeto.jsp">

                    ＋ Criar Novo Projeto

                </a>

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
                <script src="${pageContext.request.contextPath}/js/script.js" defer></script>
    </body>
    
</html>
