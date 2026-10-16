/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function abrirSidebar() {
    document.getElementById("sidebar").classList.add("aberta");
}

function fecharSidebar() {
    document.getElementById("sidebar").classList.remove("aberta");
}


/* OPÇÕES DE PERFIL */
function abrirOpcoes() {
    // O seletor visual existente contém o acesso para escolher outro avatar.
    abrirCarrossel();
}


/*CARROSSEL DE AVATARES */
const contextoApp = document.body?.dataset.contextoApp || window.contextoApp || "";
if (document.body?.dataset.avatarSalvo !== undefined) {
    window.avatarSalvo = document.body.dataset.avatarSalvo;
}

const avatares = [
    "avatar01.jpeg",
    "avatar02.jpeg",
    "avatar03.jpeg",
    "avatar04.jpeg",
    "avatar05.jpeg",
    "avatar06.jpeg"
];

const avatarPadrao = "anonimo 2.png";

let avatarAtual = Math.max(
    0,
    avatares.indexOf(window.avatarSalvo)
);

const avatarOverlay = document.getElementById("avatarOverlay");
const avatarSelecionado = document.getElementById("avatarSelecionado");
const avatarIndicadores = document.getElementById("avatarIndicadores");


function abrirCarrossel() {
    if (!avatarOverlay || !avatarSelecionado) return;
    avatarOverlay.classList.add("aberto");
    mostrarAvatar(avatarAtual);
}


function fecharCarrossel() {
    avatarOverlay?.classList.remove("aberto");
}


function mostrarAvatar(indice) {
    if (!avatarSelecionado) return;
    avatarAtual = indice;
    avatarSelecionado.src = caminhoAvatar(avatares[avatarAtual]);
    atualizarIndicadores();
}


function proximoAvatar() {

    avatarAtual++;

    if (avatarAtual >= avatares.length) {
        avatarAtual = 0;
    }

    mostrarAvatar(avatarAtual);
}


function avatarAnterior() {

    avatarAtual--;

    if (avatarAtual < 0) {
        avatarAtual = avatares.length - 1;
    }

    mostrarAvatar(avatarAtual);
}


function atualizarIndicadores() {
    if (!avatarIndicadores) return;
    avatarIndicadores.innerHTML = "";

    avatares.forEach(function(avatar, indice) {

        const indicador =
            document.createElement("span");

        indicador.classList.add("avatar-indicador");

        if (indice === avatarAtual) {
            indicador.classList.add("ativo");
        }

        indicador.addEventListener("click", function() {
            mostrarAvatar(indice);
        });

        avatarIndicadores.appendChild(indicador);
    });
}

function caminhoAvatar(nomeArquivo) {
    return contextoApp + "/img/" + nomeArquivo;
}

function carregarAvatarSalvo() {
    const imagemPerfil = document.getElementById("imagem-perfil");
    const imagemPerfil2 = document.getElementById("imagem-perfil2");
    if (!imagemPerfil || !imagemPerfil2 || !avatarSelecionado) return;

    const avatar = window.avatarSalvo;

    const caminho = avatar
        ? caminhoAvatar(avatar)
        : caminhoAvatar(avatarPadrao);

    document.getElementById("imagem-perfil").src = caminho;
    document.getElementById("imagem-perfil2").src = caminho;

    if (avatar) {
        avatarSelecionado.src = caminhoAvatar(avatar);
    } else {
        avatarSelecionado.src = caminhoAvatar(avatares[0]);
    }

    atualizarIndicadores();
}

async function selecionarAvatar() {
    const nomeArquivo = avatares[avatarAtual];

    const botao = document.querySelector(".avatar-confirmar");
    if (!botao) return;
    botao.disabled = true;

    try {
        const resposta = await fetch(
            contextoApp + "/AtualizarAvatarServlet",
            {
                method: "POST",
                headers: {
                    "Content-Type":
                        "application/x-www-form-urlencoded;charset=UTF-8"
                },
                body: new URLSearchParams({
                    avatar: nomeArquivo
                })
            }
        );

        if (!resposta.ok) {
            throw new Error("Não foi possível salvar o avatar.");
        }

        window.avatarSalvo = nomeArquivo;

        document.getElementById("imagem-perfil").src =
            caminhoAvatar(nomeArquivo);

        document.getElementById("imagem-perfil2").src =
            caminhoAvatar(nomeArquivo);

        fecharCarrossel();

    } catch (erro) {
        alert("Não foi possível salvar seu avatar. Tente novamente.");
        console.error(erro);

    } finally {
        botao.disabled = false;
    }
}
/* ARRASTAR AVATAR */
let inicioArraste = 0;

avatarSelecionado?.addEventListener(
    "touchstart",
    function(event) {

        inicioArraste =
            event.touches[0].clientX;

    }
);


avatarSelecionado?.addEventListener(
    "touchend",
    function(event) {

        const fimArraste =
            event.changedTouches[0].clientX;

        const distancia =
            fimArraste - inicioArraste;

        if (Math.abs(distancia) < 50) {
            return;
        }

        if (distancia < 0) {
            proximoAvatar();
        } else {
            avatarAnterior();
        }

    }
);
/* CARROSSEL ENTRE MENU PRINCIPAL E MURAL */
function inicializarCarrosselPaginas() {
    const trilho = document.getElementById("trilhoPaginas");
    const viewport = document.getElementById("carrosselPaginas");
    const abas = Array.from(document.querySelectorAll(".aba-pagina"));
    const paineis = Array.from(document.querySelectorAll(".painel-pagina"));

    if (!trilho || !viewport || abas.length < 2 || paineis.length < 2) return;

    let paginaAtual = 0;
    let inicioX = 0;
    let inicioY = 0;
    let arrastando = false;

    function mudarPagina(indice) {
        paginaAtual = Math.max(0, Math.min(1, indice));
        trilho.style.transform = `translateX(-${paginaAtual * 50}%)`;

        abas.forEach((aba, i) => {
            const ativa = i === paginaAtual;
            aba.classList.toggle("ativa", ativa);
            aba.setAttribute("aria-selected", String(ativa));
            aba.tabIndex = ativa ? 0 : -1;
        });

        paineis.forEach((painel, i) => {
            painel.setAttribute("aria-hidden", String(i !== paginaAtual));
        });
        viewport.dataset.pagina = String(paginaAtual);
    }

    abas.forEach((aba, indice) => {
        aba.addEventListener("click", () => mudarPagina(indice));
        aba.addEventListener("keydown", (event) => {
            if (event.key !== "ArrowRight" && event.key !== "ArrowLeft") return;
            event.preventDefault();
            const destino = event.key === "ArrowRight"
                ? Math.min(1, indice + 1)
                : Math.max(0, indice - 1);
            mudarPagina(destino);
            abas[destino].focus();
        });
    });

    // Gesto horizontal em celulares; movimentos verticais continuam rolando a página.
    viewport.addEventListener("touchstart", (event) => {
        if (!event.touches.length) return;
        inicioX = event.touches[0].clientX;
        inicioY = event.touches[0].clientY;
        arrastando = true;
    }, { passive: true });

    viewport.addEventListener("touchend", (event) => {
        if (!arrastando || !event.changedTouches.length) return;
        arrastando = false;
        const deltaX = event.changedTouches[0].clientX - inicioX;
        const deltaY = event.changedTouches[0].clientY - inicioY;
        if (Math.abs(deltaX) > 55 && Math.abs(deltaX) > Math.abs(deltaY) * 1.2) {
            mudarPagina(deltaX < 0 ? paginaAtual + 1 : paginaAtual - 1);
        }
    }, { passive: true });

    mudarPagina(0);
}

document.addEventListener("DOMContentLoaded", function () {
    carregarAvatarSalvo();
    inicializarCarrosselPaginas();
});
