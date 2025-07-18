# ViaCep
- Precisa da JDK compativel com a versão do Java 17
- Banco de dados PostgreSQL
- User, senha e databese, se encontra no arquivo application.properties
- teste unitario utilizando WireMock

Intuito dessa aplicação é salvar o resultado da api ViaCep Correios. Quando o usuario tentar consumir e enviar o cep, verificamos se já existe no banco de dados, caso não exista, tentamos encontrar na base da api.
