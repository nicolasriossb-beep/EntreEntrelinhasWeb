(() => {
    const livro = document.getElementById("livro");
    const convite = document.getElementById("convite");
    const abrir = document.getElementById("abrirLivro");
    const fechar = document.getElementById("fecharLivro");
    const painelEscolha = document.getElementById("painelEscolha");
    const painelCadastro = document.getElementById("painelCadastro");
    const mensagemDireita = document.getElementById("mensagemDireita");
    const esquerda = document.getElementById("paginaEsquerda");
    const paginaEsquerda = document.querySelector(".pagina-esquerda");
    const paginaDireita = document.querySelector(".pagina-direita");
    const conteudo = document.getElementById("conteudoLivro");

    let fluxoAtual = "";
    let transicaoEmCurso = false;
    let retornoAgendado = false;

    function mostrarLivro() {
        convite.classList.add("sumindo");
        convite.style.opacity = "0";
        convite.style.visibility = "hidden";
        convite.style.transform = "scale(.88) translateY(20px)";
        window.setTimeout(() => {
            livro.classList.add("aberto");
            livro.setAttribute("aria-hidden", "false");
            conteudo.classList.add("conteudo-visivel");
        }, 520);
    }

    function limparFluxo() {
        esquerda.src = "about:blank";
        paginaEsquerda.classList.remove("com-form");
        paginaDireita.classList.remove("com-form");
        livro.classList.remove("em-fluxo");
        painelEscolha.hidden = false;
        painelCadastro.hidden = false;
        mensagemDireita.hidden = false;
        fluxoAtual = "";
        retornoAgendado = false;
    }

    function fecharLivro() {
        livro.classList.remove("aberto", "virando", "em-fluxo");
        livro.setAttribute("aria-hidden", "true");
        limparFluxo();
        convite.style.visibility = "visible";
        convite.style.opacity = "1";
        convite.style.transform = "none";
    }

    function voltarParaEscolha() {
        if (transicaoEmCurso) return;
        transicaoEmCurso = true;
        conteudo.classList.remove("conteudo-visivel");
        livro.classList.add("virando");
        window.setTimeout(() => {
            limparFluxo();
            conteudo.classList.add("conteudo-visivel");
        }, 460);
        window.setTimeout(() => {
            livro.classList.remove("virando");
            transicaoEmCurso = false;
        }, 900);
    }

    function virarPagina(callback) {
        if (transicaoEmCurso) return;
        transicaoEmCurso = true;
        conteudo.classList.remove("conteudo-visivel");
        livro.classList.add("virando");
        window.setTimeout(() => {
            callback();
            conteudo.classList.add("conteudo-visivel");
        }, 480);
        window.setTimeout(() => {
            livro.classList.remove("virando");
            transicaoEmCurso = false;
        }, 900);
    }

    function iniciarFluxo(tipo) {
        if (transicaoEmCurso || fluxoAtual) return;
        fluxoAtual = tipo;
        virarPagina(() => {
            painelEscolha.hidden = true;
            painelCadastro.hidden = true;
            mensagemDireita.hidden = true;
            paginaDireita.classList.remove("com-form");
            livro.classList.add("em-fluxo");
            paginaEsquerda.classList.add("com-form");
            esquerda.src = tipo === "login" ? "pages/logins.html" : "pages/cadastros.html";
        });
    }

    function injetarEstiloInterno(doc) {
        if (!doc || doc.getElementById("estiloLivroIframe")) return;
        const estilo = doc.createElement("style");
        estilo.id = "estiloLivroIframe";
        estilo.textContent = `
            html { overflow-x: hidden !important; overflow-y: auto !important; scrollbar-width: thin; scrollbar-color: rgba(139, 99, 53, .48) transparent; }
            body { overflow-x: hidden !important; overflow-y: auto !important; max-width: 100% !important; }
            * { scrollbar-width: thin; scrollbar-color: rgba(139, 99, 53, .48) transparent; }
            *::-webkit-scrollbar { width: 5px; height: 0 !important; }
            *::-webkit-scrollbar:horizontal { display: none; height: 0 !important; }
            *::-webkit-scrollbar-track { background: transparent; }
            *::-webkit-scrollbar-thumb { background: rgba(139, 99, 53, .42); border-radius: 10px; border: 1px solid rgba(248, 240, 220, .7); }
            *::-webkit-scrollbar-thumb:hover { background: rgba(112, 67, 34, .7); }
        `;
        (doc.head || doc.documentElement).appendChild(estilo);
    }

    function prepararPagina(frame) {
        try {
            const doc = frame.contentDocument;
            if (!doc) return;
            injetarEstiloInterno(doc);

            // Os formulários e suas respostas permanecem sempre na página esquerda.
            doc.querySelectorAll("form").forEach(form => form.setAttribute("target", "_self"));

            // O retorno à página inicial deve atualizar o livro principal, nunca abrir index.html dentro do iframe.
            doc.querySelectorAll('a[href]').forEach(link => {
                if (link.dataset.livroInterceptado) return;
                const href = link.getAttribute("href") || "";
                if (/index\.html(?:[?#].*)?$/i.test(href) || href === "../" || href === "./") {
                    link.dataset.livroInterceptado = "true";
                    link.addEventListener("click", event => {
                        event.preventDefault();
                        voltarParaEscolha();
                    });
                }
            });

            // Alguns formulários podem criar links dinamicamente; a delegação cobre esses casos.
            if (!doc.body.dataset.livroLinks) {
                doc.body.dataset.livroLinks = "true";
                doc.body.addEventListener("click", event => {
                    const link = event.target.closest && event.target.closest("a[href]");
                    if (!link) return;
                    const href = link.getAttribute("href") || "";
                    if (/index\.html(?:[?#].*)?$/i.test(href) || href === "../" || href === "./") {
                        event.preventDefault();
                        voltarParaEscolha();
                    }
                });
            }
        } catch (erro) {
            console.warn("Não foi possível preparar o conteúdo da página do livro.", erro);
        }
    }

    esquerda.addEventListener("load", () => {
        prepararPagina(esquerda);
        try {
            const url = esquerda.contentWindow.location.href;
            if (url.includes("DashboardServlet")) {
                window.location.href = url;
                return;
            }
        } catch (erro) {
            // O iframe pode estar em about:blank durante a inicialização.
        }
    });

    abrir.addEventListener("click", mostrarLivro);
    fechar.addEventListener("click", fecharLivro);
    document.querySelectorAll("[data-destino]").forEach(botao => {
        botao.addEventListener("click", () => iniciarFluxo(botao.dataset.destino));
    });

    document.addEventListener("keydown", event => {
        if (event.key === "Escape" && livro.classList.contains("aberto")) fecharLivro();
    });

    // Gesto horizontal para escolher uma das opções iniciais em dispositivos móveis.
    let inicioX = 0;
    livro.addEventListener("touchstart", event => {
        if (event.touches.length) inicioX = event.touches[0].clientX;
    }, { passive: true });
    livro.addEventListener("touchend", event => {
        if (!inicioX || !event.changedTouches.length || fluxoAtual) return;
        const delta = event.changedTouches[0].clientX - inicioX;
        inicioX = 0;
        if (Math.abs(delta) < 65) return;
        iniciarFluxo(delta < 0 ? "cadastro" : "login");
    }, { passive: true });
})();
