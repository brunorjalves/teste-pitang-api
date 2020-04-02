# Teste Pitang API

API para cadastro de carros e usuários.

## Pré requisitos
* Git  
* Java 8  
* Maven 3 ou superior


## Instalação
Após clonar o projeto acessar a raiz e executar.

```bash
mvn clean install
```

## Rotas da aplicação
Método Http  | Recurso| Ação |Protegida| URL
-------------|--------|------|---------|-------
POST | Login | Login na aplicação | Não | http://localhost:8080/api/signin
GET | Usuário | Listar todos os usuários | Não | http://localhost:8080/api/users
GET | Usuário | Lista um usuário pelo id | Não | http://localhost:8080/api/users/{id}
POST | Usuário |Cadastrar novo usuário| Não | http://localhost:8080/api/users
PUT | Usuário |Alterar um usuário já existente | Não | http://localhost:8080/api/users/{id}
DELETE | Usuário | Exclui um usuário | Não | http://localhost:8080/api/users/{id}
GET | Info | Exibe informações do usuário logado | Sim | http://localhost:8080/api/me
GET | Carro | Listar todos os carros do usuário logado | Sim | http://localhost:8080/api/cars
GET | Carro | Lista um carro do usuário logado pelo id | Sim | http://localhost:8080/api/cars/{id}
POST | Carro| Cadastrar um novo carro para o usuário logado | Sim | http://localhost:8080/api/cars
PUT | Carro| Alterar um carro do usuário logado | Sim | http://localhost:8080/api/cars/{id}
DELETE | Carro | Exclui o carro do usuário logado | Sim | http://localhost:8080/api/cars/{id}

## Acesso a rotas protegidas
Após cadastrar um usuário será necessário acessar a o recurso de login (1º item da tabela acima). Esse recurso irá retornar um token JWT que deverá ser inserido nas requisições a recursos protegidos, esse token deverá ir no header das requisições.    

Exemplo de header
```
Authorization: Bearer <seu.token.aqui>
```

## Execução dos testes unitários
Para executar os testes unitários, basta executá-los usando o mvn.

```bash
mvn test
```

## Ambiente de produção
A API encontra-se hospedada no heroku e pode ser acessada através do seguinte link <https://teste-pitang-api.herokuapp.com>. 

## Processo de deploy
O Heroku está configurado para ouvir a branch master do git, caso seja feito algum push é acionado o processo de deploy. Primeiro será feito o build da aplicação, depois serão executados os testes automáticos, caso todo o processo seja concluído com sucesso o deploy é finalizado. Caso ocorra algum erro nos testes automáticos, recebo um e-mail na minha conta particular informando que aconteceu um erro no processo de deploy.

## Front end
Foi criado um client Angular para consumir nossa API, o repositório pode ser acessado [clicando aqui](https://github.com/brunorjalves/teste-pitang-ui).

## Estorias de usuário
1. Criação do recurso de cadastro de usuário.
2. Criação do recurso de listar todos os usuários.
3. Criação do recurso de listar um usuário pelo ID.
4. Criação do recurso de atualização do usuário.
5. Criação do recurso de exclusão do usuário.
6. Criação das regras de validação do usuário.
7. Criação do recurso de login.
8. Criação do recurso de informação do usuário (exibir dados do usuário logado).
9. Criação do recurso de cadastro de carro para o usuário logado.
10. Criação do recurso de listar todos os carros do usuário logado.
11. Criação do recurso de listar um carro do usuário logado pelo ID.
12. Criação do recurso de alterar os dados de um carro do usuário logado.
13. Criação do recurso de excluir um carro do usuário logado.

## Solução
A aplicação foi construída utilizando o springboot, spring data jpa, spring security e no frontend, Angular 8, primeng e bootstrap. A utilização de tais tecnologia se deram pelos seguintes motivos:

1. Facilidade de configuração.
2. Confiabilidade e estabilidade dos frameworks.
3. Aplicações que utilizam serviços rest tendem a não guardar estado no servidor, o que pode diminuir o consumo de recursos.
4. O uso de microserviços permite que diversos tipos de clients possam consumir da API, dessa forma conseguimos ter uma mesma lógica de negocio funcionando em diversas plataformas.
5. Comunidade ativa.
6. Maturidade da tecnologia.