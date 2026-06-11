// ============================================================================
// VARIÁVEIS GLOBAIS E CONFIGURAÇÃO
// ============================================================================
const URL_BASE = "http://localhost:8080/origin";
let fichaAtualId = 0;
let usuarioLogado = null;

/// ============================================================================
// 0. CONTROLE DE SESSÃO E INICIALIZAÇÃO
// ============================================================================
document.addEventListener('DOMContentLoaded', () => {
    // 1. Verifica a sessão no navegador
    const usuarioLocal = localStorage.getItem('orbe_usuario');
    const fichaIdLocal = localStorage.getItem('orbe_ficha_id');

    if (!usuarioLocal) {
        alert("A sua conexão com o Vazio Arcano expirou. Faça login novamente.");
        window.location.href = '../index.html';
        return;
    }

    usuarioLogado = JSON.parse(usuarioLocal);

    // Ajusta o Link da Logo (Botão Home) dependendo de quem está logado
    const linkLogo = document.querySelector('.logo-orbe');
    if (linkLogo) {
        linkLogo.href = usuarioLogado.tipo === 'MESTRE' ? 'dashboard-mestre.html' : 'dashboard.html';
    }

    if (!fichaIdLocal) {
        alert("Nenhuma ficha foi selecionada!");
        window.location.href = usuarioLogado.tipo === 'MESTRE' ? 'dashboard-mestre.html' : 'dashboard.html';
        return;
    }

    // 2. Chama a função passando os dados corretos da Sessão
    carregarFichaEspecifica(usuarioLogado.id, parseInt(fichaIdLocal));
});

// Busca a ficha! Inteligência artificial para Mestre vs Jogador
async function carregarFichaEspecifica(usuarioId, fichaIdSelecionada) {
    try {
        // Se for Jogador, busca as fichas dele. Se for Mestre, busca o ID da ficha de forma absoluta!
        let urlBusca = `${URL_BASE}/api/fichas?usuarioId=${usuarioId}`;
        if (usuarioLogado.tipo === 'MESTRE') {
            urlBusca = `${URL_BASE}/api/fichas?id=${fichaIdSelecionada}`;
        }

        const resposta = await fetch(urlBusca);
        if (!resposta.ok) throw new Error("Falha ao comunicar com o servidor.");

        const fichas = await resposta.json();
        const personagemAtual = fichas.find(f => f.id === fichaIdSelecionada);

        if (personagemAtual) {
            preencherDadosDaFicha(personagemAtual);
            carregarInventario(personagemAtual.id);
            carregarHabilidades(personagemAtual.id);
        } else {
            console.log("Ficha não encontrada ou não pertence a este utilizador.");
            window.location.href = usuarioLogado.tipo === 'MESTRE' ? 'dashboard-mestre.html' : 'dashboard.html';
        }
    } catch (erro) {
        console.error("Erro ao procurar ficha:", erro);
    }
}

// ============================================================================
// 1. PREENCHER E ATUALIZAR A FICHA PRINCIPAL
// ============================================================================
function preencherDadosDaFicha(personagem) {
    fichaAtualId = personagem.id;

    document.getElementById('nome-personagem').value = personagem.nomePersonagem || '';
    document.getElementById('estilo-personagem').value = personagem.estilos || '';
    document.getElementById('raca-personagem').value = personagem.raca || 'Humano';

    document.getElementById('nivel-valor').textContent = personagem.nivel || 1;
    document.getElementById('xp-valor').value = personagem.exp || 0;

    document.getElementById('attr-forca').value = personagem.forca || 0;
    document.getElementById('attr-velocidade').value = personagem.velocidade || 0;
    document.getElementById('attr-destreza').value = personagem.destreza || 0;
    document.getElementById('attr-vigor').value = personagem.vigor || 0;
    document.getElementById('attr-sabedoria').value = personagem.sabedoria || 0;
    document.getElementById('attr-inteligencia').value = personagem.inteligencia || 0;



    // -- Lógica do Avatar (Primeira Letra) --
    const primeiraLetra = personagem.nomePersonagem ? personagem.nomePersonagem.charAt(0).toUpperCase() : '?';
    const elLetra = document.getElementById('letra-avatar');
    if (elLetra) elLetra.textContent = primeiraLetra;

    // Atualiza os substatus matemáticos imediatamente
    calcularSubstatus();
}

// Ouve o clique do botão Salvar Ficha
document.getElementById('btn-salvar-ficha').addEventListener('click', salvarFicha);

async function salvarFicha() {
    const fichaEditada = {
        id: fichaAtualId,
        raca: document.getElementById('raca-personagem').value,
        exp: parseInt(document.getElementById('xp-valor').value) || 0,
        forca: parseInt(document.getElementById('attr-forca').value) || 0,
        velocidade: parseInt(document.getElementById('attr-velocidade').value) || 0,
        destreza: parseInt(document.getElementById('attr-destreza').value) || 0,
        vigor: parseInt(document.getElementById('attr-vigor').value) || 0,
        sabedoria: parseInt(document.getElementById('attr-sabedoria').value) || 0,
        inteligencia: parseInt(document.getElementById('attr-inteligencia').value) || 0
    };

    try {
        const resposta = await fetch(`${URL_BASE}/api/fichas`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(fichaEditada)
        });

        if (resposta.ok) {
            alert("✨ Ficha atualizada com sucesso!");
            // Recarrega apenas a ficha atual para ver o novo nível calculado!
            carregarFichaEspecifica(usuarioLogado.id, fichaAtualId);
        } else {
            alert("Erro ao salvar a ficha.");
        }
    } catch (erro) {
        console.error("Erro na requisição:", erro);
    }
}

// ============================================================================
// 2. CÁLCULO DE SUBSTATUS EM TEMPO REAL
// ============================================================================
function calcularSubstatus() {
    const forca = parseInt(document.getElementById('attr-forca').value) || 0;
    const vigor = parseInt(document.getElementById('attr-vigor').value) || 0;
    const sabedoria = parseInt(document.getElementById('attr-sabedoria').value) || 0;
    const inteligencia = parseInt(document.getElementById('attr-inteligencia').value) || 0;

    const vida = (vigor * 5) + (forca * 0.5);
    const sagrada = vigor + (inteligencia * 2);
    const amaldicoada = vigor + (sabedoria * 2);
    const pesquisa = inteligencia;
    const conhecimento = sabedoria;

    document.getElementById('status-vida').textContent = Math.floor(vida);
    document.getElementById('status-sagrada').textContent = sagrada;
    document.getElementById('status-amaldicoada').textContent = amaldicoada;
    document.getElementById('status-pesquisa').textContent = pesquisa;
    document.getElementById('status-conhecimento').textContent = conhecimento;
}

document.querySelectorAll('.grid-atributos input').forEach(input => {
    input.addEventListener('input', calcularSubstatus);
});

// ============================================================================
// 3. CRUD DE HABILIDADES (Cores e Edição)
// ============================================================================
let idHabEmEdicao = null;
let habilidadesCache = []; // Guarda as habilidades na memória para edição rápida

// Função inteligente que lê o texto e devolve a cor do CSS
function obterClasseBadge(tipo) {
    if (!tipo) return '';
    const texto = tipo.toLowerCase();
    if (texto.includes('inspiração') || texto.includes('inspiracao')) return 'badge-inspiracao';
    if (texto.includes('postura')) return 'badge-postura';
    return ''; // Se for Tática (ou outra coisa qualquer), assume a cor vermelha padrão
}

// Ouve o clique do botão Adicionar/Salvar
const btnAddHab = document.getElementById('btn-add-hab');
btnAddHab.onclick = async () => {
    const titulo = document.getElementById('hab-titulo').value;
    const tipo = document.getElementById('hab-tipo').value;
    const descricao = document.getElementById('hab-desc').value;

    if (!titulo) return alert("Dê um título à habilidade!");

    const dadosHab = { fichaId: fichaAtualId, titulo: titulo, tipo: tipo, descricao: descricao };

    if (idHabEmEdicao) {
        // MODO EDIÇÃO (PUT)
        dadosHab.id = idHabEmEdicao;
        await fetch(`${URL_BASE}/api/habilidades`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dadosHab)
        });

        // Restaura a estética do botão
        idHabEmEdicao = null;
        btnAddHab.textContent = "Adicionar";
        btnAddHab.style.backgroundColor = "transparent";
    } else {
        // MODO CRIAÇÃO (POST)
        await fetch(`${URL_BASE}/api/habilidades`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dadosHab)
        });
    }

    // Limpa os campos e recarrega
    document.getElementById('hab-titulo').value = '';
    document.getElementById('hab-desc').value = '';
    carregarHabilidades(fichaAtualId);
};

// Carrega as habilidades pintando as Badges
async function carregarHabilidades(fichaId) {
    const resposta = await fetch(`${URL_BASE}/api/habilidades?fichaId=${fichaId}`);
    habilidadesCache = await resposta.json();

    const container = document.getElementById('hab-load');
    container.innerHTML = '';

    habilidadesCache.forEach(hab => {
        // Descobre a cor antes de criar o HTML
        const classeCor = obterClasseBadge(hab.tipo);

        container.innerHTML += `
            <div class="card-mini">
                <div class="card-mini-header">
                    <h4>${hab.titulo}</h4>
                    <span class="badge-tipo ${classeCor}">${hab.tipo}</span>
                </div>
                <p>${hab.descricao}</p>
                <div class="card-acoes">
                    <button class="btn-acao" onclick="prepararEdicaoHabilidade(${hab.id})">Editar</button>
                    <button class="btn-acao btn-perigo" onclick="excluirHabilidade(${hab.id})">Excluir</button>
                </div>
            </div>
        `;
    });
}

// Prepara o formulário quando o jogador clica em Editar
window.prepararEdicaoHabilidade = function(habId) {
    const hab = habilidadesCache.find(h => h.id === habId);
    if (!hab) return;

    document.getElementById('hab-titulo').value = hab.titulo;
    document.getElementById('hab-tipo').value = hab.tipo;
    document.getElementById('hab-desc').value = hab.descricao;

    idHabEmEdicao = hab.id;

    // Destaca o botão para o jogador não se esquecer que está a editar
    btnAddHab.textContent = "Salvar Edição";
    btnAddHab.style.backgroundColor = "var(--cor-detalhe-vermelho)";
};

// Excluir
window.excluirHabilidade = async function(habId) {
    if (confirm("Tem a certeza que quer apagar esta habilidade?")) {
        await fetch(`${URL_BASE}/api/habilidades?id=${habId}`, { method: 'DELETE' });
        carregarHabilidades(fichaAtualId);
    }
};

// ============================================================================
// 4. UPLOAD DE IMAGEM GENÉRICO
// ============================================================================
async function fazerUploadImagem(arquivo) {
    const formData = new FormData();
    formData.append('foto', arquivo);

    try {
        const resposta = await fetch(`${URL_BASE}/api/upload`, { method: 'POST', body: formData });
        if (resposta.ok) {
            const dados = await resposta.json();
            return dados.url;
        }
    } catch (e) {
        console.error("Erro no upload da imagem:", e);
    }
    return "";
}

// ============================================================================
// 5. CRUD DE INVENTÁRIO
// ============================================================================
let itensInventarioCache = [];
let idItemEmEdicao = null;
let urlImagemAtualDoItemEditado = "";

async function carregarInventario(fichaId) {
    try {
        const resposta = await fetch(`${URL_BASE}/api/inventario?fichaId=${fichaId}`);
        const itens = await resposta.json();

        itensInventarioCache = itens;
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

            containerInventario.innerHTML += `
                <div class="linha-item ${classeGrid}">
                    ${divImagem}
                    <div class="item-detalhes">
                        <h4>${item.tituloItem}</h4>
                        <div class="item-descricao-scroll">${item.descricao || 'Sem descrição.'}</div>
                    </div>
                    <div class="item-acoes">
                        <button class="btn-acao" onclick="prepararEdicaoItem(${item.id})">Editar</button>
                        <button class="btn-acao btn-perigo" onclick="excluirItem(${item.id})">Excluir</button>
                    </div>
                </div>
            `;
        });
    } catch (erro) {
        console.error("Erro ao buscar inventário:", erro);
    }
}

window.excluirItem = async function(itemId) {
    if (confirm("Tem a certeza que deseja apagar este item?")) {
        await fetch(`${URL_BASE}/api/inventario?id=${itemId}`, { method: 'DELETE' });
        carregarInventario(fichaAtualId);
    }
};

window.prepararEdicaoItem = function(itemId) {
    const item = itensInventarioCache.find(i => i.id === itemId);
    if (!item) return;

    document.getElementById('item-titulo').value = item.tituloItem;
    document.getElementById('item-desc').value = item.descricao;
    urlImagemAtualDoItemEditado = item.imagem || "";
    idItemEmEdicao = item.id;

    const btnAdd = document.getElementById('btn-add-item');
    btnAdd.textContent = "Salvar Edição";
    btnAdd.style.backgroundColor = "var(--cor-detalhe-vermelho)";
};

document.getElementById('btn-add-item').addEventListener('click', async () => {
    const titulo = document.getElementById('item-titulo').value;
    const desc = document.getElementById('item-desc').value;
    const inputFoto = document.getElementById('item-foto');
    const arquivoFoto = inputFoto.files[0];

    if (!titulo && !arquivoFoto && !urlImagemAtualDoItemEditado) {
        return alert("O item precisa de ter pelo menos um Nome ou uma Imagem!");
    }

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
        itemData.id = idItemEmEdicao;
        await fetch(`${URL_BASE}/api/inventario`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(itemData)
        });

        idItemEmEdicao = null;
        urlImagemAtualDoItemEditado = "";
        const btnAdd = document.getElementById('btn-add-item');
        btnAdd.textContent = "Adicionar";
        btnAdd.style.backgroundColor = "transparent";
    } else {
        await fetch(`${URL_BASE}/api/inventario`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(itemData)
        });
    }

    document.getElementById('item-titulo').value = '';
    document.getElementById('item-desc').value = '';
    inputFoto.value = '';

    carregarInventario(fichaAtualId);
});

