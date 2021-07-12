# API-Java

API simples desenvolvida em Java utilizando Spring Tools 4, H2 Database (banco de dados em memória), Eclipse, Maven e Java versão 11

# Getting started

* JDK 11: Necessário para executar o projeto Java
* Plataforma de colaboração para desenvolvimento de API (Postman ou Insomnia por exemplo): Necessário para realizar os testes

# Desenvolvimento

Para iniciar o desenvolvimento, é necessário clonar o projeto do GitHub num diretório de sua preferência:

```shell
cd "diretorio de sua preferencia"
git clone https://github.com/beatrizmenezes406/API-Java
```

# Testes

Para realizar os testes, basta executar o arquivo target/api-java-0.0.1-SNAPSHOT.jar. Após a execução, o projeto estará rodando na porta 8080 do seu navegador.

As rotas necessárias para os testes são:
  
  * POST para inserir os registros: localhost:8080/clientes, enviando como JSON a seguinte estrutura:
  
    ```code
      {
        "nome" : "Seu nome aqui"
      }
      ```
  * GET para listar os registros inseridos: localhost:8080/clientes
  * DELETE para deletar algum registro inserido anteriormente: localhost:8080/clientes/{id}, passando como parâmetro na url o id que deseja excluir
   * PUT Para alterar algum registro inserido anteriormente: localhost:8080/clientes, enviando como JSON a seguinte estrutura:
   
      ```code
      {
        "id" : "Id a ser alterado",
        "nome" : "Novo nome aqui"
      }
      ```

