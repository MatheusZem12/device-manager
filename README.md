# Device Manager

Sistema de gerenciamento e localização de dispositivos com backend Java/Spring Boot e frontend Flutter.

## Estrutura

```
device-manager/
├── backend/     # Java 21 + Spring Boot + Maven
├── frontend/    # Flutter (Android/iOS/Web/Desktop)
└── start.sh     # Script de inicialização
```

## Requisitos

- Java 21
- Maven 3.9+
- Flutter (canal stable)

## Inicialização Rápida

```bash
./start.sh
```

Isso irá:
1. Compilar e iniciar o backend na porta 8080
2. Mostrar instruções para executar o frontend Flutter

### Backend

```bash
cd backend
mvn spring-boot:run
```

- API REST: `http://localhost:8080/api`
- H2 Console: `http://localhost:8080/h2-console`

### Frontend

```bash
cd frontend
flutter run
```

## Funcionalidades

- 🔐 Login com autenticação simples
- 📊 Dashboard administrativo com dispositivos supervisionados
- 🗺️ Mapa interativo com pontos de localização (OpenStreetMap)
- 📍 Histórico de localizações por dispositivo
- 👤 Gerenciamento de perfil
- 🎨 Interface moderna e escura

## Contas de Teste

| E-mail | Senha | Perfil |
|--------|-------|--------|
| joao@email.com | senha123 | Admin |
| maria@email.com | senha123 | User |
| ana@email.com | senha123 | Admin |

## Tecnologias

**Backend:**
- Java 21
- Spring Boot 3.5+
- Spring Data JPA
- Spring Security (OAuth2 Resource Server)
- H2 Database (dev)

**Frontend:**
- Flutter 3.x
- Provider (state management)
- flutter_map (OpenStreetMap)
- geolocator (GPS)
- http (API REST)
- shared_preferences (sessão local)
