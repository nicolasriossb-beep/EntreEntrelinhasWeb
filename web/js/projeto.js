/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
const parametros = new URLSearchParams(window.location.search);

const projetoId = parametros.get("id");

if (!projetoId) {
    window.location.href = "../BibliotecaServlet";
} else {

    fetch(`../ProjetoServlet?id=${projetoId}`)
        .then(response => {

            if (!response.ok) {
                throw new Error("Não foi possível carregar o projeto.");
            }

            return response.json();
        })

        .then(projeto => {

            document.getElementById("nomeProjeto").textContent =
                "📚 " + projeto.nome;

        })

        .catch(erro => {

            console.error(erro);

            document.getElementById("nomeProjeto").textContent =
                "📚 Projeto";

        });
}