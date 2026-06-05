// ============================================================================
// 1. CONTROLOS DA IMAGEM DO PERSONAGEM
// ============================================================================
const panX = document.getElementById('pan-x');
const panY = document.getElementById('pan-y');
const imgPersonagem = document.getElementById('img-personagem');

function atualizarPosicaoImagem() {
    imgPersonagem.style.setProperty('--pos-x', `${panX.value}%`);
    imgPersonagem.style.setProperty('--pos-y', `${panY.value}%`);
}

panX.addEventListener('input', atualizarPosicaoImagem);
panY.addEventListener('input', atualizarPosicaoImagem);


// ============================================================================
// 2. COMUNICAÇÃO COM A API (AJUSTADO PARA A SUA URL /origin)
// ============================================================================
// AQUI ESTAVA O PROBLEMA: O nome do projeto no seu Tomcat é "origin"
const URL_BASE = "http://localhost:8080/origin";

document.addEventListener('DOMContentLoaded', () => {
    console.log("Sistema Orbe: A iniciar carregamento de dados...");
    carregarFichasDoUsuario(1);
});

async function carregarFichasDoUsuario(usuarioId) {
    try {
        const resposta = await fetch(`${URL_BASE}/api/fichas?usuarioId=${usuarioId}`);
        if (!resposta.ok) throw new Error("Falha ao comunicar com o servidor.");

        const fichas = await resposta.json();

        if (fichas && fichas.length > 0) {
            const personagemAtual = fichas[0];
            preencherDadosDaFicha(personagemAtual);
            carregarInventario(personagemAtual.id);

            carregarHabilidades(personagemAtual.id);

        } else {
            console.log("Nenhuma ficha encontrada para este utilizador.");
        }
    } catch (erro) {
        console.error("Erro ao procurar ficha:", erro);
    }
}

function preencherDadosDaFicha(personagem) {
    document.getElementById('nome-personagem').value = personagem.nomePersonagem || '';
    document.getElementById('estilo-personagem').value = personagem.estilos || '';
    document.getElementById('raca-personagem').value = personagem.raca || '';

    document.getElementById('nivel-valor').textContent = personagem.nivel || 1;
    document.getElementById('xp-valor').value = personagem.exp || 0;

    const inputsAtributos = document.querySelectorAll('.grid-atributos input');
    if (inputsAtributos.length >= 6) {
        inputsAtributos[0].value = personagem.forca || 0;
        inputsAtributos[1].value = personagem.velocidade || 0;
        inputsAtributos[2].value = personagem.destreza || 0;
        inputsAtributos[3].value = personagem.vigor || 0;
        inputsAtributos[4].value = personagem.sabedoria || 0;
        inputsAtributos[5].value = personagem.inteligencia || 0;
    }

    document.getElementById('status-vida').textContent = personagem.vida || 0;
    document.getElementById('status-sagrada').textContent = personagem.sagrada || 0;
    document.getElementById('status-amaldicoada').textContent = personagem.amaldicoada || 0;
    document.getElementById('status-pesquisa').textContent = personagem.pesquisa || 0;
    document.getElementById('status-conhecimento').textContent = personagem.conhecimento || 0;
}

// ============================================================================
// 3. INJETAR E SALVAR OS DADOS DA FICHA
// ============================================================================
let fichaAtualId = 0; // Guarda o ID da ficha para sabermos quem atualizar

function preencherDadosDaFicha(personagem) {
    fichaAtualId = personagem.id; // Guarda o ID no momento que carrega

    document.getElementById('nome-personagem').value = personagem.nomePersonagem || '';
    document.getElementById('estilo-personagem').value = personagem.estilos || '';
    document.getElementById('raca-personagem').value = personagem.raca || 'Humano';

    document.getElementById('nivel-valor').textContent = personagem.nivel || 1;
    document.getElementById('xp-valor').value = personagem.exp || 0;

    // Agora é à prova de falhas! Cada atributo tem o seu ID:
    document.getElementById('attr-forca').value = personagem.forca || 0;
    document.getElementById('attr-velocidade').value = personagem.velocidade || 0;
    document.getElementById('attr-destreza').value = personagem.destreza || 0;
    document.getElementById('attr-vigor').value = personagem.vigor || 0;
    document.getElementById('attr-sabedoria').value = personagem.sabedoria || 0;
    document.getElementById('attr-inteligencia').value = personagem.inteligencia || 0;

    calcularSubstatus();
}

// Ouve o clique do botão Salvar
document.getElementById('btn-salvar-ficha').addEventListener('click', salvarFicha);

async function salvarFicha() {
    // Recolhe os dados editados da tela
    const fichaEditada = {
        id: fichaAtualId,
        raca: document.getElementById('raca-personagem').value, // O Java precisa da raça para o Strategy
        exp: parseInt(document.getElementById('xp-valor').value) || 0,
        forca: parseInt(document.getElementById('attr-forca').value) || 0,
        velocidade: parseInt(document.getElementById('attr-velocidade').value) || 0,
        destreza: parseInt(document.getElementById('attr-destreza').value) || 0,
        vigor: parseInt(document.getElementById('attr-vigor').value) || 0,
        sabedoria: parseInt(document.getElementById('attr-sabedoria').value) || 0,
        inteligencia: parseInt(document.getElementById('attr-inteligencia').value) || 0
    };

    try {
        // PUT é o padrão REST da internet para "Atualizar/Modificar"
        const resposta = await fetch(`${URL_BASE}/api/fichas`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(fichaEditada)
        });

        if (resposta.ok) {
            alert("✨ Ficha atualizada com sucesso!");
            // Recarrega a ficha para ver o nível novo calculado pelo Strategy no Java
            carregarFichasDoUsuario(1);
        } else {
            alert("Erro ao salvar a ficha.");
        }
    } catch (erro) {
        console.error("Erro na requisição:", erro);
    }
}

// ============================================================================
// 4. CARREGAR INVENTÁRIO (NOVO LAYOUT)
// ============================================================================
async function carregarInventario(fichaId) {
    try {
        const resposta = await fetch(`${URL_BASE}/api/inventario?fichaId=${fichaId}`);
        const itens = await resposta.json();

        const containerInventario = document.getElementById('pack-load');
        containerInventario.innerHTML = '';

        if (itens.length === 0) {
            containerInventario.innerHTML = '<p style="color: var(--text-secondary); padding: 15px;">A mochila está vazia.</p>';
            return;
        }

        itens.forEach(item => {
            let divImagem = '';
            let classeGrid = 'sem-imagem'; // Por padrão, assume que não tem imagem

            // Se o item tiver uma URL salva no banco de dados
            if (item.imagem && item.imagem.trim() !== "") {
                classeGrid = 'com-imagem';
                divImagem = `
                    <div class="item-img-mini">
                        <img src="${item.imagem}" alt="Ícone">
                    </div>
                `;
            }

            // O HTML agora é dinâmico e adapta a classe do CSS
            const linhaHTML = `
                <div class="linha-item ${classeGrid}">
                    ${divImagem}
                    <div class="item-detalhes">
                        <h4>${item.tituloItem}</h4>
                        <div class="item-descricao-scroll">
                            ${item.descricao || 'Sem descrição.'}
                        </div>
                    </div>
                    <div class="item-acoes">
                        <button class="btn-acao">Editar</button>
                        <button class="btn-acao btn-perigo">Excluir</button>
                    </div>
                </div>
            `;
            containerInventario.innerHTML += linhaHTML;
        });
    } catch (erro) {
        console.error("Erro ao buscar inventário:", erro);
    }
}

// ============================================================================
// 5. CÁLCULO DE SUBSTATUS EM TEMPO REAL
// ============================================================================
function calcularSubstatus() {
    const forca = parseInt(document.getElementById('attr-forca').value) || 0;
    const vigor = parseInt(document.getElementById('attr-vigor').value) || 0;
    const sabedoria = parseInt(document.getElementById('attr-sabedoria').value) || 0;
    const inteligencia = parseInt(document.getElementById('attr-inteligencia').value) || 0;

    // As suas fórmulas!
    const vida = (vigor * 5) + (forca * 0.5);
    const sagrada = vigor + (inteligencia * 2);
    const amaldicoada = vigor + (sabedoria * 2);
    const pesquisa = inteligencia;
    const conhecimento = sabedoria;

    // Atualiza a tela (arredondando a vida para baixo caso dê quebrado)
    document.getElementById('status-vida').textContent = Math.floor(vida);
    document.getElementById('status-sagrada').textContent = sagrada;
    document.getElementById('status-amaldicoada').textContent = amaldicoada;
    document.getElementById('status-pesquisa').textContent = pesquisa;
    document.getElementById('status-conhecimento').textContent = conhecimento;
}

// Faz o cálculo disparar sempre que o utilizador digitar nos atributos
document.querySelectorAll('.grid-atributos input').forEach(input => {
    input.addEventListener('input', calcularSubstatus);
});


// ============================================================================
// 6. CRUD DE HABILIDADES
// ============================================================================
document.getElementById('btn-add-hab').addEventListener('click', async () => {
    const titulo = document.getElementById('hab-titulo').value;
    const tipo = document.getElementById('hab-tipo').value;
    const descricao = document.getElementById('hab-desc').value;

    if (!titulo) return alert("Dê um título à habilidade!");

    const novaHab = {
        fichaId: fichaAtualId, // A variável que guardámos no passo anterior!
        titulo: titulo,
        tipo: tipo,
        descricao: descricao
    };

    await fetch(`${URL_BASE}/api/habilidades`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(novaHab)
    });

    // Limpa os campos e recarrega a lista
    document.getElementById('hab-titulo').value = '';
    document.getElementById('hab-desc').value = '';
    carregarHabilidades(fichaAtualId);
});

async function carregarHabilidades(fichaId) {
    const resposta = await fetch(`${URL_BASE}/api/habilidades?fichaId=${fichaId}`);
    const habilidades = await resposta.json();

    const container = document.getElementById('hab-load');
    container.innerHTML = '';

    habilidades.forEach(hab => {
        container.innerHTML += `
            <div class="card-mini">
                <div class="card-mini-header">
                    <h4>${hab.titulo}</h4>
                    <span class="badge-tipo">${hab.tipo}</span>
                </div>
                <p>${hab.descricao}</p>
                <div class="card-acoes">
                    <button class="btn-acao btn-perigo" onclick="excluirHabilidade(${hab.id})">Excluir</button>
                </div>
            </div>
        `;
    });
}


// ============================================================================
// 7. UPLOAD DE IMAGEM GENÉRICO
// ============================================================================
// Função que envia a foto física para o servidor e devolve o caminho salvo
async function fazerUploadImagem(arquivo) {
    const formData = new FormData();
    formData.append('foto', arquivo);

    try {
        const resposta = await fetch(`${URL_BASE}/api/upload`, {
            method: 'POST',
            body: formData
        });
        if (resposta.ok) {
            const dados = await resposta.json();
            return dados.url; // ex: "../uploads/123_foto.png"
        }
    } catch (e) {
        console.error("Erro no upload da imagem:", e);
    }
    return "";
}


// ============================================================================
// 8. CRUD DE INVENTÁRIO (ADICIONAR, EDITAR E EXCLUIR)
// ============================================================================

// Variáveis para controlar o Modo de Edição
let itensInventarioCache = []; // Guarda a lista de itens atual
let idItemEmEdicao = null;
let urlImagemAtualDoItemEditado = "";

async function carregarInventario(fichaId) {
    try {
        const resposta = await fetch(`${URL_BASE}/api/inventario?fichaId=${fichaId}`);
        const itens = await resposta.json();

        itensInventarioCache = itens; // Guarda em cache para a edição rápida
        const containerInventario = document.getElementById('pack-load');
        containerInventario.innerHTML = '';

        if (itens.length === 0) {
            containerInventario.innerHTML = '<p style="color: var(--text-secondary); padding: 15px;">A mochila está vazia.</p>';
            return;
        }

        itens.forEach(item => {
            let divImagem = '';
            let classeGrid = 'sem-imagem';

            if (item.imagem && item.imagem.trim() !== "") {
                classeGrid = 'com-imagem';
                divImagem = `
                    <div class="item-img-mini">
                        <img src="${item.imagem}" alt="Ícone">
                    </div>
                `;
            }

            const linhaHTML = `
                <div class="linha-item ${classeGrid}">
                    ${divImagem}
                    <div class="item-detalhes">
                        <h4>${item.tituloItem}</h4>
                        <div class="item-descricao-scroll">
                            ${item.descricao || 'Sem descrição.'}
                        </div>
                    </div>
                    <div class="item-acoes">
                        <button class="btn-acao" onclick="prepararEdicaoItem(${item.id})">Editar</button>
                        <button class="btn-acao btn-perigo" onclick="excluirItem(${item.id})">Excluir</button>
                    </div>
                </div>
            `;
            containerInventario.innerHTML += linhaHTML;
        });
    } catch (erro) {
        console.error("Erro ao buscar inventário:", erro);
    }
}

// --- FUNÇÃO PARA EXCLUIR ---
window.excluirItem = async function(itemId) {
    if (confirm("Tem a certeza que deseja apagar este item? Esta ação é irreversível.")) {
        await fetch(`${URL_BASE}/api/inventario?id=${itemId}`, { method: 'DELETE' });
        carregarInventario(fichaAtualId); // Recarrega a tela
    }
};

// --- FUNÇÃO PARA PREPARAR O FORMULÁRIO PARA EDIÇÃO ---
window.prepararEdicaoItem = function(itemId) {
    // Procura no cache o item que clicámos
    const item = itensInventarioCache.find(i => i.id === itemId);
    if (!item) return;

    // Preenche os campos
    document.getElementById('item-titulo').value = item.tituloItem;
    document.getElementById('item-desc').value = item.descricao;

    // O input 'file' não pode ser preenchido por questões de segurança,
    // por isso guardamos a foto velha na memória caso ele não envie uma nova
    urlImagemAtualDoItemEditado = item.imagem || "";
    idItemEmEdicao = item.id;

    // Muda o aspeto do botão
    const btnAdd = document.getElementById('btn-add-item');
    btnAdd.textContent = "Salvar Edição";
    btnAdd.style.backgroundColor = "var(--cor-detalhe-vermelho)"; // Dá um destaque
};

// --- AÇÃO DO BOTÃO (AGORA FAZ CREATE OU UPDATE) ---
document.getElementById('btn-add-item').addEventListener('click', async () => {
    const titulo = document.getElementById('item-titulo').value;
    const desc = document.getElementById('item-desc').value;
    const inputFoto = document.getElementById('item-foto');
    const arquivoFoto = inputFoto.files[0];

    if (!titulo && !arquivoFoto && !urlImagemAtualDoItemEditado) {
        return alert("O item precisa de ter pelo menos um Nome ou uma Imagem!");
    }

    // Se estiver a editar, assume a foto velha por defeito.
    // Mas se enviou um arquivoFoto novo, faz o upload e substitui a variável!
    let urlImagemSalva = urlImagemAtualDoItemEditado;
    if (arquivoFoto) {
        urlImagemSalva = await fazerUploadImagem(arquivoFoto);
    }

    const itemData = {
        fichaId: fichaAtualId,
        tituloItem: titulo || "Item Sem Nome",
        descricao: desc,
        imagem: urlImagemSalva
    };

    if (idItemEmEdicao) {
        // == MODO EDIÇÃO (PUT) ==
        itemData.id = idItemEmEdicao;
        await fetch(`${URL_BASE}/api/inventario`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(itemData)
        });

        // Reseta o formulário e tira do modo edição
        idItemEmEdicao = null;
        urlImagemAtualDoItemEditado = "";
        const btnAdd = document.getElementById('btn-add-item');
        btnAdd.textContent = "Adicionar";
        btnAdd.style.backgroundColor = "transparent";

    } else {
        // == MODO CRIAÇÃO (POST) ==
        await fetch(`${URL_BASE}/api/inventario`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(itemData)
        });
    }

    // Limpa campos
    document.getElementById('item-titulo').value = '';
    document.getElementById('item-desc').value = '';
    inputFoto.value = '';

    carregarInventario(fichaAtualId);
});



// Para deletar chamando a API
window.excluirHabilidade = async function(habId) {
    if (confirm("Tem a certeza que quer apagar esta habilidade?")) {
        await fetch(`${URL_BASE}/api/habilidades?id=${habId}`, { method: 'DELETE' });
        carregarHabilidades(fichaAtualId); // Atualiza a tela
    }
};