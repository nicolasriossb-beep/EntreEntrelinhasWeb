/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
const parametros = new URLSearchParams(window.location.search);

const projetoId = parametros.get("id");

if (!projetoId) {

    window.location.href = "../BibliotecaServlet";

} else {

    const links = document.querySelectorAll(".scriptorium, .personagens, .cronologia, .locais, .conflitos, .geral");

    links.forEach(link => {

        const pagina = link.getAttribute("href");

        link.href = `${pagina}?id=${projetoId}`;
        
        const linkEditar = document.getElementById("editarProjeto");

        linkEditar.href = `../EditarProjetoServlet?id=${projetoId}`;
        
        const idProjetoExcluir =
                document.getElementById("idProjetoExcluir");

        idProjetoExcluir.value = projetoId;

    });

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
       function confirmarExclusao() {

        return confirm(
                "Tem certeza que deseja excluir este projeto? " +
                "Todos os capítulos, personagens, locais, notas, conflitos " +
                "e eventos da cronologia também serão excluídos."
                );
    }
        
        

}