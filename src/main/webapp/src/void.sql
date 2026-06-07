-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Host: db:3306
-- Tempo de geração: 07/06/2026 às 02:37
-- Versão do servidor: 12.2.2-MariaDB-ubu2404
-- Versão do PHP: 8.3.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `void`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `campanhas`
--

CREATE TABLE `campanhas` (
  `id` int(11) NOT NULL,
  `mestre_id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `codigo` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

--
-- Despejando dados para a tabela `campanhas`
--

INSERT INTO `campanhas` (`id`, `mestre_id`, `nome`, `codigo`) VALUES
(1, 1, 'Aventuras em Caxias', 'CAXIAS-2026'),
(2, 7, 'Celestia', '0'),
(3, 8, 'Aventura de Devil Hunter', 'THIAGO');

-- --------------------------------------------------------

--
-- Estrutura para tabela `fichas`
--

CREATE TABLE `fichas` (
  `id` int(11) NOT NULL,
  `usuario_id` int(11) DEFAULT NULL,
  `nome_personagem` varchar(255) DEFAULT NULL,
  `estilos` varchar(255) DEFAULT NULL,
  `corpo` int(11) DEFAULT NULL,
  `sentidos` int(11) DEFAULT NULL,
  `mente` int(11) DEFAULT NULL,
  `sorte` int(11) DEFAULT NULL,
  `forca` int(11) DEFAULT NULL,
  `velocidade` int(11) DEFAULT NULL,
  `destreza` int(11) DEFAULT NULL,
  `vigor` int(11) DEFAULT NULL,
  `sabedoria` int(11) DEFAULT NULL,
  `inteligencia` int(11) DEFAULT NULL,
  `vida` float DEFAULT NULL,
  `sagrada` int(11) DEFAULT NULL,
  `amaldicoada` int(11) DEFAULT NULL,
  `pesquisa` int(11) DEFAULT NULL,
  `conhecimento` int(11) DEFAULT NULL,
  `nivel` int(11) DEFAULT NULL,
  `exp` int(11) DEFAULT NULL,
  `raca` varchar(255) DEFAULT NULL,
  `campanha_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

--
-- Despejando dados para a tabela `fichas`
--

INSERT INTO `fichas` (`id`, `usuario_id`, `nome_personagem`, `estilos`, `corpo`, `sentidos`, `mente`, `sorte`, `forca`, `velocidade`, `destreza`, `vigor`, `sabedoria`, `inteligencia`, `vida`, `sagrada`, `amaldicoada`, `pesquisa`, `conhecimento`, `nivel`, `exp`, `raca`, `campanha_id`) VALUES
(1, 1, 'Elara, a Patrulheira', 'Arco e Flecha', 12, 15, 10, 0, 10, 20, 30, 40, 50, 60, 0, 0, 0, 0, 0, 5, 5200, 'Elfo', NULL),
(3, 2, 'Legolas', 'Arqueiro', 10, 20, 0, 0, 0, 0, 18, 0, 0, 0, 0, 0, 0, 0, 0, 4, 3600, 'Elfo', NULL),
(4, 6, 'Minerva Ivan', 'Mago Beserker', 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 110, 60, 60, 20, 20, 1, 0, 'Anão', 1),
(5, 6, 'Lukkan Fusk', 'Atirador mago', 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 110, 60, 60, 20, 20, 3, 1600, 'Humano', 1),
(6, 6, 'Minerva', 'Heroi derrotado', 20, 20, 20, 20, 50, 30, 20, 20, 20, 20, 110, 60, 60, 20, 20, 1250001, 1000000017, 'Anão', 2),
(7, 7, 'não posso', 'aaaaaaa', 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 110, 60, 60, 20, 20, 1, 0, 'Humano', 2),
(8, 9, 'Daniel daniels ', 'Arqueiro', 20, 20, 20, 20, 15, 40, 20, 50, 50, 0, 110, 60, 60, 20, 20, 2, 2000, 'Elfo', 3),
(9, 9, 'sullivan', 'Mago Beserker', 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 110, 60, 60, 20, 20, 2, 800, 'Humano', 3);

-- --------------------------------------------------------

--
-- Estrutura para tabela `habilidades`
--

CREATE TABLE `habilidades` (
  `id` int(11) NOT NULL,
  `ficha_id` int(11) NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `tipo` varchar(50) NOT NULL,
  `descricao` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

--
-- Despejando dados para a tabela `habilidades`
--

INSERT INTO `habilidades` (`id`, `ficha_id`, `titulo`, `tipo`, `descricao`) VALUES
(3, 1, 'Golpe forte', 'Inspiração', 'melhora seu proximo ataque para causar um golpe mais poderoso, que causa +20 de dano.'),
(4, 1, 'salto', 'Tática', 'permite se esquivar de ataques'),
(5, 1, 'Leão', 'Tática', 'Assume a  postura do rei da selva, onde seus ataques ganham mais força em troca de stamina'),
(10, 6, 'Teste', 'Tática', '1 2 3'),
(12, 8, 'Golpe forte', 'Tática', 'causa 1d20+5 e em caso criticco 1d50');

-- --------------------------------------------------------

--
-- Estrutura para tabela `inventario`
--

CREATE TABLE `inventario` (
  `id` int(11) NOT NULL,
  `ficha_id` int(11) DEFAULT NULL,
  `titulo_item` varchar(255) DEFAULT NULL,
  `descricao` text DEFAULT NULL,
  `imagem` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

--
-- Despejando dados para a tabela `inventario`
--

INSERT INTO `inventario` (`id`, `ficha_id`, `titulo_item`, `descricao`, `imagem`) VALUES
(10, 1, 'frasco de poção vazio', 'cura 10 hp e durante rodadas', '../uploads/1780624214654_pocao.png'),
(13, 5, 'frasco vazio', 'é possivel colocar outros liquidos nele', '../uploads/1780789189939_pocao.png'),
(14, 8, 'chave do porem', 'chave que abre o porem nas profudenzas ', '');

-- --------------------------------------------------------

--
-- Estrutura para tabela `loja_habilidades`
--

CREATE TABLE `loja_habilidades` (
  `id` int(11) NOT NULL,
  `campanha_id` int(11) NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `tipo` varchar(50) NOT NULL,
  `descricao` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `loja_itens`
--

CREATE TABLE `loja_itens` (
  `id` int(11) NOT NULL,
  `campanha_id` int(11) NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `descricao` text DEFAULT NULL,
  `imagem` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `tipo` varchar(20) NOT NULL DEFAULT 'JOGADOR'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

--
-- Despejando dados para a tabela `usuarios`
--

INSERT INTO `usuarios` (`id`, `username`, `email`, `senha`, `tipo`) VALUES
(1, 'mestre', 'mago@orbe.comAA', 'senhaSecreta123', 'JOGADOR'),
(2, 'MestreDosMagos', 'mestre@cavernadodragao.com', 'segredo123', 'JOGADOR'),
(3, NULL, 'admin', '1', 'MESTRE'),
(4, NULL, 'admin', '1', 'MESTRE'),
(5, NULL, 'admin', '1', 'MESTRE'),
(6, 'Souza o Grande Arauto', 'jeffemail', '1', 'JOGADOR'),
(7, 'Vazio', 'null', '0', 'MESTRE'),
(8, 'mestre2', '123', '123', 'MESTRE'),
(9, 'gih', '1234', '1234', 'JOGADOR');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `campanhas`
--
ALTER TABLE `campanhas`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `codigo` (`codigo`),
  ADD KEY `mestre_id` (`mestre_id`);

--
-- Índices de tabela `fichas`
--
ALTER TABLE `fichas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `usuario_id` (`usuario_id`),
  ADD KEY `campanha_id` (`campanha_id`);

--
-- Índices de tabela `habilidades`
--
ALTER TABLE `habilidades`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ficha_id` (`ficha_id`);

--
-- Índices de tabela `inventario`
--
ALTER TABLE `inventario`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ficha_id` (`ficha_id`);

--
-- Índices de tabela `loja_habilidades`
--
ALTER TABLE `loja_habilidades`
  ADD PRIMARY KEY (`id`),
  ADD KEY `campanha_id` (`campanha_id`);

--
-- Índices de tabela `loja_itens`
--
ALTER TABLE `loja_itens`
  ADD PRIMARY KEY (`id`),
  ADD KEY `campanha_id` (`campanha_id`);

--
-- Índices de tabela `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `campanhas`
--
ALTER TABLE `campanhas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `fichas`
--
ALTER TABLE `fichas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de tabela `habilidades`
--
ALTER TABLE `habilidades`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de tabela `inventario`
--
ALTER TABLE `inventario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de tabela `loja_habilidades`
--
ALTER TABLE `loja_habilidades`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `loja_itens`
--
ALTER TABLE `loja_itens`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `campanhas`
--
ALTER TABLE `campanhas`
  ADD CONSTRAINT `1` FOREIGN KEY (`mestre_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE;

--
-- Restrições para tabelas `fichas`
--
ALTER TABLE `fichas`
  ADD CONSTRAINT `1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `2` FOREIGN KEY (`campanha_id`) REFERENCES `campanhas` (`id`) ON DELETE SET NULL;

--
-- Restrições para tabelas `habilidades`
--
ALTER TABLE `habilidades`
  ADD CONSTRAINT `1` FOREIGN KEY (`ficha_id`) REFERENCES `fichas` (`id`) ON DELETE CASCADE;

--
-- Restrições para tabelas `inventario`
--
ALTER TABLE `inventario`
  ADD CONSTRAINT `1` FOREIGN KEY (`ficha_id`) REFERENCES `fichas` (`id`) ON DELETE CASCADE;

--
-- Restrições para tabelas `loja_habilidades`
--
ALTER TABLE `loja_habilidades`
  ADD CONSTRAINT `1` FOREIGN KEY (`campanha_id`) REFERENCES `campanhas` (`id`) ON DELETE CASCADE;

--
-- Restrições para tabelas `loja_itens`
--
ALTER TABLE `loja_itens`
  ADD CONSTRAINT `1` FOREIGN KEY (`campanha_id`) REFERENCES `campanhas` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
