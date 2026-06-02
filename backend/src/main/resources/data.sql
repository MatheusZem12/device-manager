-- ======================================
-- DADOS DE TESTE - DEVELOPMENT
-- ======================================
-- Senha padrão para todos os usuários: senha123
-- Hash BCrypt: $2a$10$Jz8pCCNruK745DxfIcAFjuiZ5l2Xgw1BoB/sKNTHvkpiisWuNK3Y6
-- ======================================

-- ============ USUÁRIOS ============
-- Senha de todos: senha123
INSERT INTO tb_users (username, email, password, device_name) VALUES
('joao', 'joao@email.com', '$2a$10$Jz8pCCNruK745DxfIcAFjuiZ5l2Xgw1BoB/sKNTHvkpiisWuNK3Y6', 'iPhone 12'),
('maria', 'maria@email.com', '$2a$10$Jz8pCCNruK745DxfIcAFjuiZ5l2Xgw1BoB/sKNTHvkpiisWuNK3Y6', 'Samsung Galaxy S21'),
('pedro', 'pedro@email.com', '$2a$10$Jz8pCCNruK745DxfIcAFjuiZ5l2Xgw1BoB/sKNTHvkpiisWuNK3Y6', 'Xiaomi Redmi Note 10'),
('ana', 'ana@email.com', '$2a$10$Jz8pCCNruK745DxfIcAFjuiZ5l2Xgw1BoB/sKNTHvkpiisWuNK3Y6', 'iPhone 13 Pro'),
('carlos', 'carlos@email.com', '$2a$10$Jz8pCCNruK745DxfIcAFjuiZ5l2Xgw1BoB/sKNTHvkpiisWuNK3Y6', 'Motorola Edge 30');

-- ============ ROLES ============
-- Apenas 2 roles: ADMIN (pode gerenciar) e USER (pode ser supervisionado)
INSERT INTO tb_roles (name) VALUES
('ROLE_ADMIN'),
('ROLE_USER');

-- ============ USER_ROLES ============
-- joao: ADMIN (manager)
INSERT INTO tb_usuarios_roles (user_id, role_id) VALUES (1, 1);
-- maria: USER (supervised)
INSERT INTO tb_usuarios_roles (user_id, role_id) VALUES (2, 2);
-- pedro: USER (supervised)
INSERT INTO tb_usuarios_roles (user_id, role_id) VALUES (3, 2);
-- ana: ADMIN (manager)
INSERT INTO tb_usuarios_roles (user_id, role_id) VALUES (4, 1);
-- carlos: USER (supervised)
INSERT INTO tb_usuarios_roles (user_id, role_id) VALUES (5, 2);

-- ============ LOCALIZAÇÕES ============
-- Localizações para João (São Paulo)
INSERT INTO tb_localizations (latitude, longitude, altitude, timestamp, cep, city, state, country, user_id) VALUES
(-23.550520, -46.633308, 760.0, '2025-10-18 08:30:00', '01310-100', 'São Paulo', 'SP', 'Brasil', 1),
(-23.561684, -46.655981, 735.0, '2025-10-18 12:15:00', '01310-200', 'São Paulo', 'SP', 'Brasil', 1),
(-23.563242, -46.654296, 740.0, '2025-10-18 18:45:00', '01310-300', 'São Paulo', 'SP', 'Brasil', 1);

-- Localizações para Maria (Rio de Janeiro)
INSERT INTO tb_localizations (latitude, longitude, altitude, timestamp, cep, city, state, country, user_id) VALUES
(-22.906847, -43.172896, 10.0, '2025-10-18 09:00:00', '20040-020', 'Rio de Janeiro', 'RJ', 'Brasil', 2),
(-22.970722, -43.182365, 5.0, '2025-10-18 14:30:00', '22640-100', 'Rio de Janeiro', 'RJ', 'Brasil', 2);

-- Localizações para Pedro (Belo Horizonte)
INSERT INTO tb_localizations (latitude, longitude, altitude, timestamp, cep, city, state, country, user_id) VALUES
(-19.916681, -43.934493, 858.0, '2025-10-18 10:20:00', '30130-100', 'Belo Horizonte', 'MG', 'Brasil', 3);

-- Localizações para Ana (Curitiba)
INSERT INTO tb_localizations (latitude, longitude, altitude, timestamp, cep, city, state, country, user_id) VALUES
(-25.428954, -49.267137, 934.0, '2025-10-18 11:00:00', '80010-130', 'Curitiba', 'PR', 'Brasil', 4),
(-25.437650, -49.269750, 920.0, '2025-10-18 16:00:00', '80020-300', 'Curitiba', 'PR', 'Brasil', 4);

-- Localizações para Carlos (Porto Alegre)
INSERT INTO tb_localizations (latitude, longitude, altitude, timestamp, cep, city, state, country, user_id) VALUES
(-30.034647, -51.217658, 10.0, '2025-10-18 13:45:00', '90010-270', 'Porto Alegre', 'RS', 'Brasil', 5);

-- ============ MANAGERS ============
-- João (ADMIN) gerencia Maria, Pedro e Carlos
INSERT INTO tb_managers (user_manager_id, user_supervised_id) VALUES (1, 2);
INSERT INTO tb_managers (user_manager_id, user_supervised_id) VALUES (1, 3);
INSERT INTO tb_managers (user_manager_id, user_supervised_id) VALUES (1, 5);

-- Ana (ADMIN) gerencia Maria e Pedro
INSERT INTO tb_managers (user_manager_id, user_supervised_id) VALUES (4, 2);
INSERT INTO tb_managers (user_manager_id, user_supervised_id) VALUES (4, 3);

-- ======================================
-- RESUMO DOS DADOS INSERIDOS:
-- ======================================
-- 5 Usuários (todos com senha: senha123)
--   1. joao (joao@email.com) - ADMIN (Manager) - iPhone 12
--   2. maria (maria@email.com) - USER (Supervised) - Samsung Galaxy S21
--   3. pedro (pedro@email.com) - USER (Supervised) - Xiaomi Redmi Note 10
--   4. ana (ana@email.com) - ADMIN (Manager) - iPhone 13 Pro
--   5. carlos (carlos@email.com) - USER (Supervised) - Motorola Edge 30
--
-- 2 Roles:
--   1. ROLE_ADMIN (pode gerenciar outros usuários)
--   2. ROLE_USER (pode ser supervisionado)
--
-- 10 Localizações distribuídas entre os usuários
--
-- 5 Relacionamentos de gerenciamento:
--   - João (ADMIN) gerencia: Maria, Pedro, Carlos
--   - Ana (ADMIN) gerencia: Maria, Pedro
-- ======================================
