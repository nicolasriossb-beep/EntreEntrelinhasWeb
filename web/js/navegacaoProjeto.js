/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
const parametros = new URLSearchParams(window.location.search);

const projetoId = parametros.get("id");

if (!projetoId) {

    window.location.href = "../BibliotecaServlet";

} else {

    const voltar = document.getElementById("voltarProjeto");

    if (voltar) {
        voltar.href = `projetos.html?id=${projetoId}`;
    }

}

