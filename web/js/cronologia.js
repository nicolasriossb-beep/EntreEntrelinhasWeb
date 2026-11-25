document.addEventListener("DOMContentLoaded", () => {

    const timeline = document.getElementById("timeline");
    const projetoId = document.getElementById("projetoId").value;

    const formularioNovo = document.getElementById("formularioNovo");
    const abrirNovo = document.getElementById("abrirNovoEvento");
    const cancelarNovo = document.getElementById("cancelarNovo");
    const novoFinal = document.getElementById("novoEventoFinal");

    // ======================================
    // CRIAÇÃO DE EVENTOS
    // ======================================

    function abrirFormularioNovo() {
        formularioNovo.hidden = false;

        document.getElementById("novoTitulo").focus();

        formularioNovo.scrollIntoView({
            behavior: "smooth",
            block: "center"
        });
    }

    function fecharFormularioNovo() {
        formularioNovo.hidden = true;
        formularioNovo.querySelector("form").reset();
    }

    abrirNovo.addEventListener("click", abrirFormularioNovo);
    novoFinal.addEventListener("click", abrirFormularioNovo);
    cancelarNovo.addEventListener("click", fecharFormularioNovo);


    // ======================================
    // DETALHES E EDIÇÃO
    // ======================================

    document.querySelectorAll(".evento").forEach(evento => {

        const bolinha = evento.querySelector(".bolinha");
        const titulo = evento.querySelector(".conteudo h3");
        const detalhes = evento.querySelector(".detalhes");
        const formularioEdicao = evento.querySelector(".formulario-edicao");

        function alternarDetalhes() {

            if (!formularioEdicao.hidden) {
                formularioEdicao.hidden = true;
            }

            const aberto = !detalhes.hidden;

            detalhes.hidden = aberto;
            evento.classList.toggle("aberto", !aberto);

            bolinha.setAttribute("aria-expanded", String(!aberto));
        }

        bolinha.addEventListener("click", alternarDetalhes);
        titulo.addEventListener("click", alternarDetalhes);

        const editar = evento.querySelector(".editarEvento");

        editar.addEventListener("click", () => {

            detalhes.hidden = true;
            formularioEdicao.hidden = false;

            evento.classList.add("aberto");

            formularioEdicao.querySelector("input[name='titulo']").focus();
        });

        const cancelar = evento.querySelector(".cancelarEdicao");

        cancelar.addEventListener("click", () => {

            formularioEdicao.hidden = true;
            detalhes.hidden = false;

        });

    });


    // ======================================
    // FUNÇÕES DE REORGANIZAÇÃO
    // ======================================

    function obterEventos() {
        return Array.from(
            timeline.querySelectorAll(".evento")
        );
    }

    function moverEvento(evento, posY) {

        const outrosEventos = obterEventos()
            .filter(outro => outro !== evento);

        for (const outro of outrosEventos) {

            const retangulo = outro.getBoundingClientRect();
            const meio = retangulo.top + retangulo.height / 2;

            if (posY < meio) {

                if (evento.nextElementSibling !== outro) {
                    timeline.insertBefore(evento, outro);
                    ordemAlterada = true;
                }

                return;
            }
        }

        if (evento.nextElementSibling !== novoFinal) {
            timeline.insertBefore(evento, novoFinal);
            ordemAlterada = true;
        }
    }


    // ======================================
    // SALVAR ORDEM NO BANCO
    // ======================================

    async function salvarOrdem() {

        const ids = obterEventos().map(evento =>
            evento.dataset.eventoId
        );

        const dados = new URLSearchParams();

        dados.append("projetoId", projetoId);
        dados.append("ordem", ids.join(","));

        const contexto = window.location.pathname.substring(
            0,
            window.location.pathname.lastIndexOf("/")
        );

        try {

            const resposta = await fetch(
                `${contexto}/ReordenarCronologiaServlet`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body: dados
                }
            );

            if (!resposta.ok) {
                throw new Error("Não foi possível salvar a nova ordem.");
            }

        } catch (erro) {

            alert(
                "Não foi possível salvar a reorganização. A página será atualizada."
            );

            window.location.reload();
        }
    }


    // ======================================
    // ARRASTAR COM MOUSE OU PONTEIRO
    // ======================================

    let eventoArrastado = null;
    let inicioX = 0;
    let inicioY = 0;
    let arrastando = false;
    let ordemAlterada = false;
    let ponteiroId = null;

    // Desativa o mecanismo nativo do navegador
    obterEventos().forEach(evento => {
        evento.draggable = false;
    });

    timeline.addEventListener("dragstart", e => {
        e.preventDefault();
    });

    // Início do movimento
    timeline.addEventListener("pointerdown", e => {

        const evento = e.target.closest(".evento");

        if (!evento || e.button !== 0) {
            return;
        }

        // Não inicia arrasto ao clicar em botões ou formulários
        if (e.target.closest("button, input, textarea, form, a")) {
            return;
        }

        eventoArrastado = evento;
        inicioX = e.clientX;
        inicioY = e.clientY;
        ponteiroId = e.pointerId;

        arrastando = false;
        ordemAlterada = false;

    });

    // Movimento do ponteiro
    document.addEventListener("pointermove", e => {

        if (!eventoArrastado || e.pointerId !== ponteiroId) {
            return;
        }

        const distancia = Math.hypot(
            e.clientX - inicioX,
            e.clientY - inicioY
        );

        // Só começa a arrastar depois de um pequeno movimento
        if (!arrastando && distancia > 5) {

            arrastando = true;

            eventoArrastado.classList.add("arrastando");

            eventoArrastado.setPointerCapture(e.pointerId);
        }

        if (!arrastando) {
            return;
        }

        e.preventDefault();

        moverEvento(eventoArrastado, e.clientY);

    });

    // Soltura do evento
    document.addEventListener("pointerup", async e => {

        if (!eventoArrastado || e.pointerId !== ponteiroId) {
            return;
        }

        if (arrastando) {

            eventoArrastado.classList.remove("arrastando");

            if (ordemAlterada) {
                await salvarOrdem();
            }
        }

        eventoArrastado = null;
        ponteiroId = null;
        arrastando = false;
        ordemAlterada = false;

    });

    // Cancelamento do movimento
    document.addEventListener("pointercancel", () => {

        if (eventoArrastado) {
            eventoArrastado.classList.remove("arrastando");
        }

        eventoArrastado = null;
        ponteiroId = null;
        arrastando = false;
        ordemAlterada = false;

    });

});