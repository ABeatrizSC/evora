<div align="center">

![evora-logo-banner.jpg](docs%2Fimages%2Fevora.jpg)

  <img src="https://img.shields.io/badge/Status-In%20Progress-yellow?style=for-the-badge&logo=headspace&logoColor=orange&color=yellow" alt="repo-status" />

 <a href="/README.md">
  <img src="https://img.shields.io/badge/README-English-ff0000?style=for-the-badge" alt="README evora em inglês" />
 </a>
</div>


<div align="center">
  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="Spring" />
  <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" alt="Docker" />
  <img src="https://img.shields.io/badge/Rabbitmq-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white" alt="RabbitMQ" />
  <img src="https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white" alt="POSTGRESQL" />
  <img src="https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white" alt="POSTGRESQL" />
</div>

</br>

# Evora

Evora é um sistema de gerenciamento de eventos construído com arquitetura de microsserviços, possuindo um API Gateway, mensageria, integração com a API de pagamentos da Asaas e envio automático de notificações por e-mail para usuários participantes.

## Funcionalidades
- Criar contas do tipo `PARTICIPANT` ou `CREATOR`;
- Usuários do tipo `CREATOR` podem criar e gerenciar eventos;
- Usuários do tipo `PARTICIPANT` podem comprar ingressos para eventos disponíveis. Após confirmar a compra, recebem o ingresso por e-mail;
- Um usuário do tipo `CREATOR` não pode comprar ingressos de outros eventos, e vice-versa;
- Criadores podem sacar o dinheiro dos ingressos vendidos após a realização do evento.

</br>

# Tecnologias Utilizadas
* **Java 17**: Linguagem de programação de alto nível, orientada a objetos, amplamente utilizada para aplicações server-side, web services e aplicações Android.

* **Spring Boot 3.5.5**: Framework que simplifica o desenvolvimento de aplicações Java, fornecendo recursos embutidos para injeção de dependência, configuração e suporte a microsserviços.

* **Spring Security**: Framework poderoso e personalizável para autenticação e controle de acesso em aplicações Java.

* **JWT (JSON Web Token)**: Padrão aberto que permite transmissão segura de informações de autenticação.

* **Spring Cloud API Gateway**: Serve como ponto de entrada central, lidando com roteamento de requisições, autenticação e controle de tráfego entre microsserviços.

* **Spring Cloud Netflix Eureka**: Permite registro e descoberta automáticos de microsserviços, facilitando escalabilidade dinâmica e comunicação entre serviços.

* **Spring Cloud OpenFeign**: Cliente HTTP declarativo para Java, que simplifica a integração com APIs REST ao permitir a definição de clientes com mínimo boilerplate.

* **RabbitMQ**: Software open-source que atua como broker de mensagens entre produtores e consumidores.

* **Jakarta Bean Validation**: Framework padrão para declarar e validar restrições em objetos Java com anotações, usado para regras de negócio e validação de entradas.

* **JUnit**: Framework amplamente usado para testes em Java, fornecendo anotações e asserts para escrita e execução de testes unitários.

* **JPA**: API de persistência em Java que fornece mapeamento objeto-relacional (ORM) para manipulação de dados relacionais.

* **MapStruct 1.6.2**: Framework Java que simplifica o mapeamento de objetos, reduzindo código repetitivo e melhorando a manutenibilidade.

* **Flyway**: Ferramenta de migração de banco de dados que garante controle de versão e consistência em alterações de schema.

* **Lombok 1.18.28**: Biblioteca Java que reduz código repetitivo ao gerar automaticamente getters, setters, construtores e outros métodos via anotações.

* **Postman**: Ferramenta para testes de API, permitindo envio de requisições HTTP, inspeção de respostas e automação de testes.

## Bancos de Dados e Outras Tecnologias

* **MongoDB**: Banco de dados NoSQL projetado para alta performance, escalabilidade e flexibilidade, armazenando dados em documentos no estilo JSON.

* **PostgreSQL**: Banco de dados relacional open-source robusto, conhecido por confiabilidade, extensibilidade e conformidade com SQL.

* **Docker**: Plataforma que permite automatizar o deploy de aplicações em containers leves, garantindo consistência entre ambientes e simplificando o setup.

</br>

# Requisitos
Para rodar o projeto na sua máquina, as seguintes ferramentas devem estar instaladas e configuradas previamente:

- Docker
- Git

</br>

# Guia de Instalação
Siga os passos abaixo para baixar, configurar e executar o projeto no seu ambiente:

1. **Crie uma conta no Asaas**
- Para que o sistema de pagamento funcione, você precisa ter uma conta ativa no Asaas e gerar sua `API key`.
- Criar conta [aqui](https://www.asaas.com/login/auth).

2. **Clone o repositório**
```bash
git clone https://github.com/ABeatrizSC/evora.git
 ```

3. **Navegue até o diretório do projeto**

```bash
cd evora
 ```

4. **Adicione sua JWT KEY e Asaas API KEY**
- Crie um arquivo `.env` na raiz do projeto e adicione sua JWT KEY (usada para assinar o token) e sua Asaas API_KEY (passo 1). Exemplo:

```.env
JWT_KEY=SUA_CHAVE_AQUI
API_KEY=SUA_CHAVE_AQUI
```

5. **Build e inicialização do container docker**

 ```bash
docker-compose up --build
 ```

</br>

# Endpoints
## 1. USER-SERVICE
- Microsserviço responsável pela autenticação e gerenciamento de usuários.

### **POST** `/api/v1/auth/register`
- Cria um novo usuário.

#### Request Body
- `RegisterRequestDto`
```json
{
    "name": "Marcelo Almeida",
    "email": "marcelo@email.com",
    "password": "marc123",
    "role": "PARTICIPANT", // PARTICIPANT ou CREATOR
    "document": "24971563792", // se role == participant
    "mobilePhone": "4799376637" // se role == participant
}
```

#### Success Response Body
- `204 CREATED`
---

### **POST** `/api/v1/auth/login`
- Realiza login de um usuário existente.

#### Request Body
- `LoginRequestDto`

```json
{
    "email": "marcelo@email.com",
    "password": "marc123"
}
```

#### Success Response Body
- `LoginResponseDto`: token JWT.
```json
{
    "token": "ey..."
}
```
---

### **PUT** `/api/v1/users`
- Atualiza o usuário autenticado.

#### Headers
- `Authorization: Bearer <jwt_token>`

#### Request Body
- `UpdateUserRequestDto`

```json
{
    "nameUpdated": "Marcelo Almeida updated",
    "emailUpdated": "marceloupdated@email.com",
    "currentPassword": "marc123",
    "password": "marc124",
    "documentUpdated": "59654828090", // se role == participant
    "mobilePhoneUpdated": "4799376637" // se role == participant
}
```

#### Success Response Body
- `UserResponseDto`
```json
{
    "name": "Marcelo Almeida updated",
    "email": "marceloupdated@email.com",
    "role": "marc123",
    "customerId": "cus.." // se role == participant
}
```
---

### **DELETE** `/api/v1/users`
- Deleta o usuário autenticado.

#### Headers
- `Authorization: Bearer <jwt_token>`

#### Request Body
- None.

#### Success Response Body
- `200 OK`
---

### **GET** `/api/v1/users`
- Retorna detalhes da conta do usuário autenticado.

#### Headers
- `Authorization: Bearer <jwt_token>`

#### Request Body
- None.

#### Success Response Body
- `UserResponseDto`
```json
{
    "name": "Marcelo Almeida updated",
    "email": "marceloupdated@email.com",
    "role": "marc123",
    "customerId": "cus.." // se role == participant
}
```
---

</br>

## Mensagens de Erro

Todas as respostas de erro seguem o formato abaixo:

```json
{
  "status": 400,
  "error": "BAD_REQUEST",
  "message": "Mensagem explicando o erro ocorrido."
}
```

| Campo     | Tipo     | Descrição                                                               |
|-----------|----------|-------------------------------------------------------------------------|
| `status`  | Integer  | Código HTTP do erro.                                                    |
| `error`   | String   | Nome da constante correspondente em `HttpStatus`.                       |
| `message` | String   | Mensagem descritiva do erro, podendo vir de uma exceção personalizada.  |

---

</br>

# Contato
* GitHub: [ABeatrizSC](https://github.com/ABeatrizSC)
* Linkedin: [Ana Beatriz Santucci Carmoni](www.linkedin.com/in/ana-carmoni)
* Email: [anabscarmoni@gmail.com](mailto:anabscarmoni@gmail.com)  
