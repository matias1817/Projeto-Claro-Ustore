# Projeto Claro Ustore

##  Como executar o projeto

### 1. Configuração do ambiente

Crie um arquivo `.env` na raiz do projeto com as seguintes variáveis:

```bash
PASS_DB=senha_do_banco
DB_NAME=Machines_DB 
HOST_DB=localhost 
PORT_DB=5435 
SECRET_KEY=sua_secret_key 
```

Você pode gerar uma `SECRET_KEY` segura para os Tokens JWT usando o comando do openssl comando (uma chave também será enviada pelo email):

```bash
openssl rand -base64 32
```
---

### 2. Subir os containers com Docker

Certifique-se de que o Docker esteja instalado e em execução. Em seguida, execute o comando abaixo para iniciar os serviços necessários (como o banco de dados):
```bash
docker compose up -d
```

---

### 3. Rodar a aplicação

Com os containers já em execução, inicie a aplicação com o Maven Wrapper:
```bash
./mvnw spring-boot:run
```
### 4. Rodar testes

Para rodar os testes unitários execute
```bash
./mvnw test
```
---

## Requisitos

- Docker e Docker Compose instalados
- Java 17 ou superior instalado (ou a versão compatível com o projeto)


