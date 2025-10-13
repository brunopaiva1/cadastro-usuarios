Primeiramente clone o repositorio

```bash
git clone https://github.com/brunopaiva1/cadastro-usuarios
cd cadastro-usuarios
```

Banco de dados gerado pelo UsuaioDAO.java na execução do código

Build do projeto e da imagem Docker

```bash
docker compose up --build
docker images
```

Listar usuários

```bash
docker compose run --rm cadastro list
```

Add usuários

```bash
docker compose run --rm cadastro add "<NOME>" <EMAIL> <SENHA>
```

Deletar usuário

```bash
docker compose run --rm cadastro delete <ID_DO_USUARIO>
```