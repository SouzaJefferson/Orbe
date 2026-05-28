// ============================================================================
// 1. CONTROLOS DA IMAGEM DO PERSONAGEM (PANNING)
// ============================================================================
const panX = document.getElementById('pan-x');
const panY = document.getElementById('pan-y');
const imgPersonagem = document.getElementById('img-personagem');

function atualizarPosicaoImagem() {
    // Altera as variáveis CSS que controlam a propriedade object-position
    imgPersonagem.style.setProperty('--pos-x', `${panX.value}%`);
    imgPersonagem.style.setProperty('--pos-y', `${panY.value}%`);
}

// Ouve os eventos de arrastar as barras de rolagem
panX.addEventListener('input', atualizarPosicaoImagem);
panY.addEventListener('input', atualizarPosicaoImagem);


// ============================================================================
// 2. COMUNICAÇÃO COM A API (FETCH DO JAVA)
// ============================================================================
const URL_BASE = "http://localhost:8080/Orbe"; // Ajuste se o nome do seu projeto Tomcat for diferente

// Função principal que arranca assim que a página HTML carrega
document.addEventListener('DOMContentLoaded', () => {
    console.log("Sistema Orbe: A iniciar carregamento de dados...");
    // Para o nosso teste, vamos forçar a procura pelas fichas do utilizador ID 1
    carregarFichasDoUsuario(1);
});

async function carregarFichasDoUsuario(usuarioId) {
    try {
        const resposta = await fetch(`${URL_BASE}/api/fichas?usuario_id=${usuarioId}`);

        if (!resposta.ok) throw new Error("Falha ao comunicar com o servidor.");

        const fichas = await resposta.json();

        // Se o utilizador tiver fichas, vamos desenhar a primeira que vier na lista
        if (fichas && fichas.length > 0) {
            const personagemAtual = fichas[0];
            preencherDadosDaFicha(personagemAtual);

            // Agora que sabemos o ID da ficha, pedimos o inventário dela!
            carregarInventario(personagemAtual.id);
        } else {
            console.log("Nenhuma ficha encontrada para este utilizador.");
        }

    } catch (erro) {
        console.error("Erro ao procurar ficha:", erro);
    }
}

// ============================================================================
// 3. INJETAR OS DADOS NO HTML
// ============================================================================
function preencherDadosDaFicha(personagem) {
    // Informações Básicas
    document.getElementById('nome-personagem').value = personagem.nomePersonagem || '';
    document.getElementById('estilo-personagem').value = personagem.estilos || '';
    document.getElementById('raca-personagem').value = personagem.raca || '';

    // Nível e Experiência
    document.getElementById('nivel-valor').textContent = personagem.nivel || 1;
    document.getElementById('xp-valor').value = personagem.exp || 0;

    // Atributos Principais (Procuramos os inputs pela ordem do Grid)
    const inputsAtributos = document.querySelectorAll('.grid-atributos input');
    if (inputsAtributos.length >= 6) {
        inputsAtributos[0].value = personagem.forca || 0;
        inputsAtributos[1].value = personagem.velocidade || 0;
        inputsAtributos[2].value = personagem.destreza || 0;
        inputsAtributos[3].value = personagem.vigor || 0;
        inputsAtributos[4].value = personagem.sabedoria || 0;
        inputsAtributos[5].value = personagem.inteligencia || 0;
    }

    // Energias e Status Calculados
    document.getElementById('status-vida').textContent = personagem.vida || 0;
    document.getElementById('status-sagrada').textContent = personagem.sagrada || 0;
    document.getElementById('status-amaldicoada').textContent = personagem.amaldicoada || 0;
    document.getElementById('status-pesquisa').textContent = personagem.pesquisa || 0;
    document.getElementById('status-conhecimento').textContent = personagem.conhecimento || 0;
}


// ============================================================================
// 4. CARREGAR E DESENHAR O INVENTÁRIO
// ============================================================================
async function carregarInventario(fichaId) {
    try {
        const resposta = await fetch(`${URL_BASE}/api/inventario?fichaId=${fichaId}`);
        const itens = await resposta.json();

        const containerInventario = document.getElementById('pack-load');
        containerInventario.innerHTML = ''; // Limpa a linha estática de exemplo do HTML

        if (itens.length === 0) {
            containerInventario.innerHTML = '<p style="color: var(--text-secondary); padding: 15px;">A mochila está vazia.</p>';
            return;
        }

        // Para cada item que vier do MariaDB, desenhamos uma nova linha na tabela
        itens.forEach(item => {
            // Se o item não tiver imagem na BD, usamos a imagem padrão
            const fotoItem = item.imagem ? item.imagem : '../src/fotorpg1.png';

            const linhaHTML = `
                <div class="linha-item">
                    <div class="item-img-mini">
                        <img src="${fotoItem}" alt="Ícone do Item">
                    </div>
                    <div class="item-detalhes">
                        <h4>${item.tituloItem}</h4>
                        <p>${item.descricao || 'Sem descrição.'}</p>
                    </div>
                    <div class="item-tipo">
                        <span class="badge-tipo">Equipamento</span>
                    </div>
                    <div class="item-efeito">
                        <span class="destaque">--</span>
                    </div>
                    <div class="item-acoes">
                        <button class="btn-acao">Editar</button>
                        <button class="btn-acao btn-perigo">Excluir</button>
                    </div>
                </div>
            `;
            // Injeta o HTML montado na página
            containerInventario.innerHTML += linhaHTML;
        });

    } catch (erro) {
        console.error("Erro ao buscar inventário:", erro);
    }
}