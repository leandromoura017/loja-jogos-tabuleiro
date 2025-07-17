-- Script de inicialização do banco de dados
-- Este script será executado automaticamente quando o container PostgreSQL for criado

-- Inserir categorias padrão
INSERT INTO categorias (nome, descricao, ativa) VALUES 
('Estratégia', 'Jogos que requerem planejamento e táticas avançadas', true),
('Família', 'Jogos adequados para toda a família', true),
('Cooperativo', 'Jogos onde os jogadores trabalham juntos', true),
('Party Games', 'Jogos para grupos grandes e diversão', true),
('Clássico', 'Jogos tradicionais e atemporais', true),
('Aventura', 'Jogos com temática de aventura e exploração', true),
('Educativo', 'Jogos com propósito educacional', true)
ON CONFLICT (nome) DO NOTHING;

-- Inserir tags padrão
INSERT INTO tags (nome, cor) VALUES 
('Medieval', '#8B4513'),
('Ficção Científica', '#4169E1'),
('Fantasia', '#9932CC'),
('Guerra', '#DC143C'),
('Economia', '#228B22'),
('Construção', '#FF8C00'),
('Cartas', '#FF1493'),
('Dados', '#32CD32'),
('Tabuleiro', '#4682B4'),
('Rápido', '#FFD700')
ON CONFLICT (nome) DO NOTHING;

-- Inserir usuário administrador padrão
-- Senha: admin123 (criptografada com BCrypt)
INSERT INTO usuarios (username, password, nome_completo, email, is_admin, enabled, data_cadastro) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXiGiNa1j6W6.L4W7Zt6.Hnm.Eq', 'Administrador do Sistema', 'admin@jogostabuleiro.com', true, true, NOW())
ON CONFLICT (username) DO NOTHING;

-- Inserir usuário comum padrão
-- Senha: user123 (criptografada com BCrypt)
INSERT INTO usuarios (username, password, nome_completo, email, is_admin, enabled, data_cadastro) VALUES 
('user', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXiGiNa1j6W6.L4W7Zt6.Hnm.Eq', 'Usuário Comum', 'user@jogostabuleiro.com', false, true, NOW())
ON CONFLICT (username) DO NOTHING;

