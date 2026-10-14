/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
const campoTexto = document.getElementById("texto");
const contagem = document.getElementById("contagemPalavras");

function contarPalavras(texto) {
    const conteudo = texto.trim();

    if (conteudo === "") {
        return 0;
    }

    return conteudo.split(/\s+/).length;
}

function atualizarContagem() {
    if (!campoTexto || !contagem) {
        return;
    }

    const total = contarPalavras(campoTexto.value);
    contagem.textContent =
        `${total} ${total === 1 ? "palavra" : "palavras"}`;
}

if (campoTexto && contagem) {
    campoTexto.addEventListener("input", atualizarContagem);
    atualizarContagem();
}

