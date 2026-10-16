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

    <body data-contexto-app="${pageContext.request.contextPath}" data-avatar-salvo="${usuario.avatar != null ? usuario.avatar : ''}">

        <!-- Menu lateral(oculto) de perfil -->
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

        <!-- Tela de escolha(oculta) dos avatares -->
        <div class="avatar-overlay" id="avatarOverlay">
            <div class="avatar-carrossel">
                <button class="avatar-fechar" onclick="fecharCarrossel()" aria-label="Fechar seletor de avatar">×</button>
                <button class="avatar-anterior" onclick="avatarAnterior()" aria-label="Avatar anterior">‹</button>
                <div class="avatar-area">
                    <img id="avatarSelecionado" src="${pageContext.request.contextPath}/img/anonimo 2.png" alt="Avatar selecionado">
                </div>
                <button class="avatar-confirmar" onclick="selecionarAvatar()">Usar este avatar</button>
                <button class="avatar-proximo" onclick="proximoAvatar()" aria-label="Próximo avatar">›</button>
                <div class="avatar-indicadores" id="avatarIndicadores"></div>
            </div>
        </div>

        <!-- Cabeçario -->
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


        <main class="area-principal">

            <!-- Botões de navegação no carrossel -->
            <div class="seletor-paginas" role="tablist" aria-label="Seções principais">

                <button class="aba-pagina ativa" id="abaMenu" type="button" role="tab"
                        aria-selected="true" aria-controls="painelMenu" data-pagina="0">
                    Menu Principal
                </button>

                <button class="aba-pagina" id="abaMural" type="button" role="tab"
                        aria-selected="false" aria-controls="painelMural" data-pagina="1">
                    Mural
                </button>

                <span class="indicador-aba" aria-hidden="true"></span>

            </div>

            <!-- Contêiner englobando as duas páginas(dashboard e mural) -->
            <div class="carrossel-paginas" id="carrosselPaginas">

                <div class="trilho-paginas" id="trilhoPaginas">

                    <!-- Conteúdos do dashboard -->
                    <section class="painel-pagina" id="painelMenu" role="tabpanel" aria-labelledby="abaMenu">
                            
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

            
                    </section>

                    <!-- Conteúdo do mural -->
                    <section class="painel-pagina painel-pagina-mural" id="painelMural" role="tabpanel" aria-labelledby="abaMural" aria-hidden="true">
                            
                        <section class="painel-mural" aria-labelledby="tituloMural">

                            <h2 class="titulo-mural" id="tituloMural">
                                🖼️ Mural da comunidade
                            </h2>

                            <p class="descricao-mural">
                                Compartilhe suas histórias com a comunidade e dê vida às suas ideias.
                            </p>

                            <div class="historias">

                                <article class="historia">
                                    <h3>O Último Reino</h3>
                                    <p class="previa">Há muitos anos, o reino de Valmor enfrentava uma grande guerra. As cidades estavam sendo destruídas e ninguém sabia quanto tempo aquilo iria durar... <a href="#">LER MAIS</a></p>
                                    <span class="usuario">@Chavito</span>
                                </article>

                                <article class="historia">
                                    <h3>As Sombras da Floresta</h3>
                                    <p class="previa">A floresta sempre foi um lugar tranquilo, até que estranhas criaturas começaram a aparecer durante a noite. Os moradores decidiram investigar... <a href="#">LER MAIS</a></p>
                                    <span class="usuario">@Mr.dark_souls</span>
                                </article>

                                <article class="historia">
                                    <h3>O Viajante</h3>
                                    <p class="previa">Ninguém sabia de onde aquele homem tinha vindo. Ele apareceu na cidade carregando apenas uma velha mochila e um mapa que parecia indicar um lugar desconhecido... <a href="#">LER MAIS</a></p>
                                    <span class="usuario">@Maria</span>
                                </article>

                                <article class="historia">
                                    <h3>A Cidade Perdida</h3>
                                    <p class="previa">Depois de anos procurando pela cidade perdida, finalmente encontramos uma entrada escondida entre as montanhas. Mas havia algo estranho naquele lugar... <a href="#">LER MAIS</a></p>
                                    <span class="usuario">@ana</span>
                                </article>

                                <article class="historia">
                                    <h3>O Guardião das Estrelas</h3>
                                    <p class="previa">Desde criança, ele conseguia enxergar coisas que ninguém mais podia ver. Certa noite, uma estrela caiu do céu e mudou completamente sua vida... <a href="#">LER MAIS</a></p>
                                    <span class="usuario">@Cleitinho</span>
                                </article>

                                <article class="historia">
                                    <h3>Além do Horizonte</h3>
                                    <p class="previa">O navio partiu antes do nascer do sol, levando uma pequena tripulação em busca de terras que não apareciam em nenhum mapa conhecido... <a href="#">LER MAIS</a></p>
                                    <span class="usuario">@Sofia</span>
                                </article>

                            </div>

                            <div class="paginacao" aria-label="Paginação do mural">

                                <button class="seta" type="button" aria-label="Página anterior" disabled>‹</button>
                                <button class="pagina-atual" type="button" aria-current="page">1</button>
                                <button class="seta" type="button" aria-label="Próxima página" disabled>›</button>

                            </div>

                        </section>

                    </section>

                </div>

            </div>

        </main>

        <script src="${pageContext.request.contextPath}/js/script.js" defer></script>
    </body>
</html>
