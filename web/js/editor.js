import { Editor, Node } from 'https://esm.sh/@tiptap/core@2.2.4';
import StarterKit from 'https://esm.sh/@tiptap/starter-kit@2.2.4';
import Placeholder from 'https://esm.sh/@tiptap/extension-placeholder@2.2.4';

// 1. Definição do Nó de Diálogo Customizado
const Dialogue = Node.create({
  name: 'dialogue',
  group: 'block',
  content: 'inline*',
  defining: true,

  parseHTML() {
    return [{ tag: 'p.dialogue' }];
  },

  renderHTML({ HTMLAttributes }) {
    return ['p', { ...HTMLAttributes, class: 'dialogue' }, 0];
  },

  addCommands() {
    return {
      toggleDialogue:
        () =>
        ({ commands, state }) => {
          const { selection } = state;
          const parentNode = selection.$head.parent;
          const isDialogue = parentNode.type.name === 'dialogue';

          // Alterna entre o tipo 'dialogue' e 'paragraph'
          const result = commands.toggleNode(this.name, 'paragraph');

          // Se acabou de virar um diálogo e não começa com travessão, adiciona o travessão automaticamente
          if (!isDialogue && !parentNode.textContent.trim().startsWith('—')) {
            commands.insertContentAt(selection.$head.start(), '— ');
          }

          return result;
        },
    };
  },
});

// 2. Inicializamos o Editor incluindo a nova extensão
const conteudoSalvo = {
  "type": "doc",
  "content": [
    {
      "type": "heading",
      "attrs": {
        "level": 1
      },
      "content": [
        {
          "type": "text",
          "text": "Capítulo 1: O Encontro"
        }
      ]
    },
    {
      "type": "paragraph",
      "content": [
        {
          "type": "text",
          "text": "O silêncio da sala foi interrompido por passos rápidos no corredor."
        }
      ]
    },
    {
      "type": "dialogue",
      "content": [
        {
          "type": "text",
          "text": "— Precisamos conversar sobre o que aconteceu ontem —, disse ele."
        }
      ]
    },
    {
      "type": "dialogue",
      "content": [
        {
          "type": "text",
          "text": "— Não há nada para conversar —, respondeu ela sem olhar para trás."
        }
      ]
    },
    {
      "type": "horizontalRule"
    },
    {
      "type": "paragraph",
      "content": [
        {
          "type": "text",
          "text": "Uma nova cena se inicia após a discussão..."
        }
      ]
    },
    {
      "type": "dialogue",
      "content": [
        {
          "type": "text",
          "text": "— Bom dia"
        }
      ]
    }
  ]
}

const editor = new Editor({
  element: document.querySelector('#editor'),
  extensions: [
    StarterKit.configure({
      heading: {
        levels: [1, 2, 3],
      },
    }),
    Dialogue,
    Placeholder.configure({
      placeholder: 'Comece a escrever sua história...',
    }),
  ],
  content: window.CONTEUDO_INICIAL,
  onUpdate: ({ editor }) => {
    // Disparado sempre que o usuário digita ou apaga algo
    atualizarContadorPalavras(editor);
  },
});

function atualizarContadorPalavras(editorInstance) {
  const text = editorInstance.getText();

  // Divide o texto por espaços/quebras de linha e remove entradas vazias
  const palavras = text.trim() ? text.trim().split(/\s+/) : [];

  const contadores = document.querySelectorAll('.word-count');
  contadores.forEach(contador => {
        contador.textContent = palavras.length;
    });
}

// Executa uma vez logo ao carregar a página para contar o texto inicial
atualizarContadorPalavras(editor);


// 3. Eventos dos Botões
document.getElementById('btn-separator').addEventListener('click', () => {
  editor.chain().focus().setHorizontalRule().run();
});

document.getElementById('btn-dialogue').addEventListener('click', () => {
  editor.chain().focus().toggleDialogue().run();
});


// 4. Envio dos dados
document.getElementById('formCapitulo').addEventListener('submit', function(event) {
    // Interrompe o envio temporariamente para injetar os dados
    event.preventDefault();

    const form = event.target;

    const textoPuro = editor.getText();
    const conteudoHtml = editor.getHTML();
    const conteudoJson = JSON.stringify(editor.getJSON());

    // Função auxiliar para criar ou atualizar um input hidden existente
    function definirCampoHidden(nome, valor) {
        let input = form.querySelector(`input[name="${nome}"]`);

        if (!input) {
            // Se o campo não existe, cria dinamicamente
            input = document.createElement('input');
            input.type = 'hidden';
            input.name = nome;
            form.appendChild(input);
        }

        input.value = valor;
    }

    // Injeta os três campos no formulário
    definirCampoHidden('texto', textoPuro);
    definirCampoHidden('html', conteudoHtml);
    definirCampoHidden('json', conteudoJson);

    // Permite que o formulário prossiga com o envio normal
    form.submit();
});
