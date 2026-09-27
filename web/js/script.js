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

const avatares = [
    "img/avatar01.jpeg",
    "img/avatar02.jpeg",
    "img/avatar03.jpeg",
    "img/avatar04.jpeg",
    "img/avatar05.jpeg",
    "img/avatar06.jpeg"
];

let avatarAtual = 0;

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
        avatares[avatarAtual];

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
