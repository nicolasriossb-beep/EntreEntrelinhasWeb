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


/*CARROSSEL DE AVATARES */
const contextoApp = window.contextoApp || "";

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

const avatarOverlay =
    document.getElementById("avatarOverlay");

const avatarSelecionado =
    document.getElementById("avatarSelecionado");

const avatarIndicadores =
    document.getElementById("avatarIndicadores");


function abrirCarrossel() {

    avatarOverlay.classList.add("aberto");

    mostrarAvatar(avatarAtual);
}


function fecharCarrossel() {

    avatarOverlay.classList.remove("aberto");
}


function mostrarAvatar(indice) {
    avatarAtual = indice;

    avatarSelecionado.src =
        caminhoAvatar(avatares[avatarAtual]);

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
/*ARRASTAR AVATAR*/

let inicioArraste = 0;

avatarSelecionado.addEventListener(
    "touchstart",
    function(event) {

        inicioArraste =
            event.touches[0].clientX;

    }
);


avatarSelecionado.addEventListener(
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
document.addEventListener("DOMContentLoaded", function () {
    carregarAvatarSalvo();
});
