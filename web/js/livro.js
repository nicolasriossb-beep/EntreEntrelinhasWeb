
(() => {
    "use strict";

    /* ------------------------------------------------------------------
     * Ajustes de tempo (em milissegundos)
     * FADE_ENTRADA / FADE_SAIDA devem coincidir com --fade-entrada e
     * --fade-saida em css/style.css.
     * ------------------------------------------------------------------ */
    const FADE_ENTRADA = 1400;   // opções/formulário aparecendo
    const FADE_SAIDA = 700;      // conteúdo sumindo antes de virar a página
    const MS_POR_QUADRO = 50;    // mesma velocidade dos GIFs originais
    const ESPERA_MAX_CARGA = 8000;

    const PASTA = "img/";
    const QUADROS_ABRINDO = 29;  // img/livro-abrindo/f00.webp ... f28.webp
    const QUADROS_VIRANDO = 27;  // img/livro-virando/f00.webp ... f26.webp

    const reduzirMovimento = window.matchMedia("(prefers-reduced-motion: reduce)").matches;

    const livro = document.getElementById("livro");
    const canvas = document.getElementById("canvasLivro");
    const ctx = canvas.getContext("2d");
    const abrir = document.getElementById("abrirLivro");
    const fechar = document.getElementById("fecharLivro");
    const conteudo = document.getElementById("conteudoLivro");
    const painelCadastro = document.getElementById("painelCadastro");
    const voltarInicio = document.getElementById("voltarInicio");
    const frame = document.getElementById("paginaDireita");
    const ctxSobretitulo = document.getElementById("contextoSobretitulo");
    const ctxTitulo = document.getElementById("contextoTitulo");
    const ctxTexto = document.getElementById("contextoTexto");

    let estado = "fechado";      // fechado | aberto
    let ocupado = false;         // animação/transição em andamento
    let fluxoAtual = "";         // "" (escolha) | "login" | "cadastro" | ...
    let redirecionando = false;  // dashboard detectado: navegação da janela principal
    let retornoPedido = false;   // resposta do servidor foi o index: voltar à escolha
    let resolverCarga = null;

    const espera = ms => new Promise(r => window.setTimeout(r, reduzirMovimento ? Math.min(ms, 60) : ms));

    /* ------------------------------------------------------------------
     * Quadros dos GIFs (já com fundo transparente)
     * ------------------------------------------------------------------ */
    function carregarQuadros(pasta, total) {
        return Array.from({ length: total }, (_, i) => {
            const img = new Image();
            img.decoding = "async";
            img.src = `${PASTA}${pasta}/f${String(i).padStart(2, "0")}.webp`;
            return img;
        });
    }
    const quadrosAbrindo = carregarQuadros("livro-abrindo", QUADROS_ABRINDO);
    const quadrosVirando = carregarQuadros("livro-virando", QUADROS_VIRANDO);

    function desenhar(img) {
        if (!img || !img.complete || !img.naturalWidth) return;
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
    }

    function quadroPronto(img) {
        if (img.complete && img.naturalWidth) return Promise.resolve();
        return new Promise(res => {
            img.addEventListener("load", res, { once: true });
            img.addEventListener("error", res, { once: true });
        });
    }

    // Mostra o primeiro quadro (livro fechado) assim que carregar.
    quadroPronto(quadrosAbrindo[0]).then(() => desenhar(quadrosAbrindo[0]));

    // Toca uma sequência de quadros uma única vez (a favor ou contra a ordem original).
    async function tocar(quadros, reverso = false) {
        const ordem = quadros.map((_, i) => i);
        if (reverso) ordem.reverse();
        await Promise.all(quadros.map(quadroPronto));
        if (reduzirMovimento) { desenhar(quadros[ordem[ordem.length - 1]]); return; }
        await new Promise(resolve => {
            const inicio = performance.now();
            let ultimo = -1;
            function passo(agora) {
                const i = Math.min(ordem.length - 1, Math.floor((agora - inicio) / MS_POR_QUADRO));
                if (i !== ultimo) { desenhar(quadros[ordem[i]]); ultimo = i; }
                if (i >= ordem.length - 1) { resolve(); return; }
                window.requestAnimationFrame(passo);
            }
            window.requestAnimationFrame(passo);
        });
    }

    /* ------------------------------------------------------------------
     * Conteúdo das páginas (fade lento)
     * ------------------------------------------------------------------ */
    async function esconderConteudo() {
        conteudo.classList.remove("conteudo-visivel");
        conteudo.setAttribute("aria-hidden", "true");
        await espera(FADE_SAIDA + 80);
    }

    function mostrarConteudo() {
        conteudo.classList.add("conteudo-visivel");
        conteudo.setAttribute("aria-hidden", "false");
    }

    function definirOcupado(valor) {
        ocupado = valor;
        livro.classList.toggle("ocupado", valor);
    }

    /* Texto da página esquerda que acompanha cada formulário */
    const CONTEXTOS = {
        login: {
            sobretitulo: "BEM-VINDO DE VOLTA",
            titulo: "Continue exatamente de onde sua história parou.",
            texto: "Seus personagens, capítulos e ideias estão guardados esperando por você."
        },
        cadastro: {
            sobretitulo: "ABRA UM NOVO CAPÍTULO",
            titulo: "Sua próxima história começa aqui.",
            texto: "Crie sua conta e comece a organizar personagens, capítulos, locais e todas as ideias da sua próxima história."
        },
        confirmar: {
            sobretitulo: "QUASE LÁ",
            titulo: "Confirme o seu e-mail.",
            texto: "Falta só um passo para abrir o primeiro capítulo da sua jornada."
        },
        senha: {
            sobretitulo: "SEM PROBLEMAS",
            titulo: "Vamos recuperar o seu acesso.",
            texto: "Até os melhores autores esquecem uma palavra de vez em quando."
        },
        padrao: {
            sobretitulo: "ENTRE ENTRELINHAS",
            titulo: "Toda história tem seus bastidores.",
            texto: ""
        }
    };

    function tipoDaUrl(url) {
        const nome = (url || "").split("?")[0].split("/").pop().toLowerCase();
        if (nome === "logins.html") return "login";
        if (nome === "cadastros.html") return "cadastro";
        if (nome.startsWith("cadastro-conf")) return "confirmar";
        if (nome.startsWith("esqueci-minha-senha")) return "senha";
        return "padrao";
    }

    function atualizarContexto(url) {
        const c = CONTEXTOS[tipoDaUrl(url)];
        ctxSobretitulo.textContent = c.sobretitulo;
        ctxTitulo.textContent = c.titulo;
        ctxTexto.textContent = c.texto;
    }

    /* ------------------------------------------------------------------
     * Abrir / fechar o livro
     * ------------------------------------------------------------------ */
    async function abrirLivro() {
        if (estado !== "fechado" || ocupado) return;
        definirOcupado(true);
        livro.classList.remove("realce");
        livro.classList.remove("fechado");   // some o convite e (no celular) volta ao tamanho normal
        await tocar(quadrosAbrindo);
        estado = "aberto";
        livro.classList.add("aberto");
        // Só depois do livro totalmente aberto as opções aparecem, devagar.
        mostrarConteudo();
        await espera(FADE_ENTRADA);
        definirOcupado(false);
    }

    function limparFluxo() {
        livro.classList.remove("em-fluxo");
        painelCadastro.hidden = false;
        frame.src = "about:blank";
        fluxoAtual = "";
        redirecionando = false;
        retornoPedido = false;
    }

    async function fecharLivro() {
        if (estado !== "aberto" || ocupado) return;
        definirOcupado(true);
        await esconderConteudo();
        limparFluxo();
        livro.classList.remove("aberto");
        await tocar(quadrosAbrindo, true);    // o GIF de abrir, ao contrário
        estado = "fechado";
        livro.classList.add("fechado");
        definirOcupado(false);
    }

    /* ------------------------------------------------------------------
     * Virar a página (conteúdo some -> GIF -> novo conteúdo aparece)
     * ------------------------------------------------------------------ */
    function carregarNoFrame(url) {
        return new Promise(resolve => {
            let fim = false;
            const concluir = () => { if (!fim) { fim = true; resolverCarga = null; resolve(); } };
            resolverCarga = concluir;
            window.setTimeout(concluir, ESPERA_MAX_CARGA);
            if (url) frame.src = url;
        });
    }

    async function virarPagina(direcao, aoVirar) {
        const carga = aoVirar ? aoVirar() : Promise.resolve();
        await tocar(quadrosVirando, direcao === "tras");
        await carga;
    }

    // Abre um formulário (login, cadastro...) a partir da tela de escolha ou de um link interno.
    async function irPara(url, tipoFluxo) {
        if (ocupado || redirecionando) return;
        definirOcupado(true);
        await esconderConteudo();                          // 1) conteúdo atual some lentamente
        await virarPagina("frente", () => {                // 2) GIF de virar a página
            const carga = carregarNoFrame(url);            //    (o formulário carrega escondido)
            painelCadastro.hidden = true;
            livro.classList.add("em-fluxo");
            fluxoAtual = tipoFluxo || tipoDaUrl(url);
            return carga;
        });
        if (redirecionando) return;
        atualizarContexto(url);
        mostrarConteudo();                                 // 3) novo conteúdo aparece lentamente
        await espera(FADE_ENTRADA);
        definirOcupado(false);
    }

    async function voltarParaEscolha() {
        if (ocupado || redirecionando) return;
        definirOcupado(true);
        await esconderConteudo();
        await virarPagina("tras", async () => { limparFluxo(); });
        mostrarConteudo();
        await espera(FADE_ENTRADA);
        definirOcupado(false);
    }

    // Resposta de um formulário enviado (ex.: cadastro -> confirmar código).
    async function aguardarRespostaDoFormulario() {
        if (ocupado || redirecionando) return;
        definirOcupado(true);
        const carga = carregarNoFrame(null);               // aguarda a próxima página do iframe
        await esconderConteudo();                          // a página atual some antes de qualquer coisa
        await carga;
        if (redirecionando || retornoPedido) return;       // dashboard (tela inteira) ou retorno ao início
        await virarPagina("frente");
        atualizarContexto(urlDoFrame());
        mostrarConteudo();
        await espera(FADE_ENTRADA);
        definirOcupado(false);
    }

    function urlDoFrame() {
        try { return frame.contentWindow.location.href; } catch (e) { return ""; }
    }

    /* ------------------------------------------------------------------
     * Conteúdo carregado no iframe
     * ------------------------------------------------------------------ */
    function injetarEstiloInterno(doc, tipo) {
        if (!doc || doc.getElementById("estiloLivroIframe")) return;
        // Títulos já aparecem na página esquerda: evitamos repeti-los na direita.
        let ocultar = ".voltar { display: none !important; }";
        if (tipo === "login") ocultar += " .logo, h1, .subtitulo { display: none !important; }";
        if (tipo === "cadastro") ocultar += " .titulo { display: none !important; }";
        const estilo = doc.createElement("style");
        estilo.id = "estiloLivroIframe";
        estilo.textContent = `
            html { overflow-x: hidden !important; overflow-y: auto !important; scrollbar-width: thin; scrollbar-color: rgba(139, 99, 53, .48) transparent; }
            body { overflow-x: hidden !important; overflow-y: auto !important; max-width: 100% !important; background: transparent !important; }
            * { scrollbar-width: thin; scrollbar-color: rgba(139, 99, 53, .48) transparent; }
            *::-webkit-scrollbar { width: 5px; height: 0 !important; }
            *::-webkit-scrollbar:horizontal { display: none; height: 0 !important; }
            *::-webkit-scrollbar-track { background: transparent; }
            *::-webkit-scrollbar-thumb { background: rgba(139, 99, 53, .42); border-radius: 10px; border: 1px solid rgba(248, 240, 220, .7); }
            *::-webkit-scrollbar-thumb:hover { background: rgba(112, 67, 34, .7); }
            ${ocultar}
        `;
        (doc.head || doc.documentElement).appendChild(estilo);
    }

    function ehIndex(href) {
        return /index\.html(?:[?#].*)?$/i.test(href) || href === "../" || href === "./";
    }

    function prepararPagina(doc, url) {
        injetarEstiloInterno(doc, tipoDaUrl(url));
        doc.querySelectorAll("form").forEach(form => form.setAttribute("target", "_self"));

        if (doc.body.dataset.livroPronto) return;
        doc.body.dataset.livroPronto = "true";

        // Links: "voltar ao início" atualiza o livro; links para outras páginas viram a página.
        doc.body.addEventListener("click", event => {
            const link = event.target.closest && event.target.closest("a[href]");
            if (!link || event.defaultPrevented) return;
            const href = link.getAttribute("href") || "";
            if (!href || href.startsWith("#") || /^(https?:|mailto:|javascript:)/i.test(href)) return;
            if (ehIndex(href)) {
                event.preventDefault();
                voltarParaEscolha();
            } else if (/\.html(?:[?#].*)?$/i.test(href)) {
                event.preventDefault();
                irPara(link.href);
            }
        });

        // Envio de formulário: a página atual some antes de a resposta do servidor aparecer.
        doc.addEventListener("submit", event => {
            if (event.defaultPrevented) return;
            aguardarRespostaDoFormulario();
        });
    }

    frame.addEventListener("load", () => {
        let url = "";
        try { url = frame.contentWindow.location.href; } catch (e) { /* mesma origem; ignora */ }
        if (!url || url === "about:blank") return;

        // Dashboard: ocupa a tela inteira em vez de aparecer dentro do livro.
        if (url.includes("DashboardServlet")) {
            redirecionando = true;
            conteudo.classList.remove("conteudo-visivel");
            frame.src = "about:blank";
            if (resolverCarga) resolverCarga();
            window.location.href = url;
            return;
        }

        // Redirecionamentos para o index dentro do iframe: volta ao livro principal.
        if (ehIndex(url)) {
            if (resolverCarga) resolverCarga();
            retornoPedido = true;
            frame.src = "about:blank";
            ocupado = false;
            voltarParaEscolha();
            return;
        }

        try { prepararPagina(frame.contentDocument, url); }
        catch (erro) { console.warn("Não foi possível preparar o conteúdo da página do livro.", erro); }

        if (resolverCarga) resolverCarga();
    });

    /* ------------------------------------------------------------------
     * Eventos
     * ------------------------------------------------------------------ */
    abrir.addEventListener("click", abrirLivro);
    abrir.addEventListener("mouseenter", () => { if (estado === "fechado" && !ocupado) livro.classList.add("realce"); });
    abrir.addEventListener("mouseleave", () => livro.classList.remove("realce"));
    abrir.addEventListener("focus", () => { if (estado === "fechado" && !ocupado) livro.classList.add("realce"); });
    abrir.addEventListener("blur", () => livro.classList.remove("realce"));
    fechar.addEventListener("click", fecharLivro);
    voltarInicio.addEventListener("click", voltarParaEscolha);

    document.querySelectorAll("[data-destino]").forEach(botao => {
        botao.addEventListener("click", () => {
            if (fluxoAtual) return;
            const tipo = botao.dataset.destino;
            irPara(tipo === "login" ? "pages/logins.html" : "pages/cadastros.html", tipo);
        });
    });

    document.addEventListener("keydown", event => {
        if (event.key === "Escape" && estado === "aberto") fecharLivro();
    });

    // Gesto horizontal para escolher uma das opções iniciais em dispositivos móveis.
    let inicioX = 0;
    livro.addEventListener("touchstart", event => {
        if (event.touches.length) inicioX = event.touches[0].clientX;
    }, { passive: true });
    livro.addEventListener("touchend", event => {
        if (!inicioX || !event.changedTouches.length || fluxoAtual || estado !== "aberto") return;
        const delta = event.changedTouches[0].clientX - inicioX;
        inicioX = 0;
        if (Math.abs(delta) < 65) return;
        const tipo = delta < 0 ? "cadastro" : "login";
        irPara(tipo === "login" ? "pages/logins.html" : "pages/cadastros.html", tipo);
    }, { passive: true });
})();