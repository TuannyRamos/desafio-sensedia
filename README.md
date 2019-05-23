Desafio Sensedia
==================

# O problema

O nosso cliente solicita que autenticação de uma determinada API seja realizada com segundo fator de autenticação usando Google Authenticator.

# Dependências

Para rodar este projeto você vai precisar de
- Java 1.8+
- Maven

Após isto basta seguir os passos abaixo:

# Para executar o projeto

```
java -jar target/desafio-sensedia-0.1.0.jar
```

ou

```
mvn clean install spring-boot:run
```

# Swagger

http://localhost:8080/swagger-ui.html

# Para executar o projeto, importar o curl a seguir:

- Para gerar a key:
```
curl -X GET \
  http://localhost:8080/v1/token \
  -H 'Cache-Control: no-cache' \
  -H 'Postman-Token: 2bb5065f-cf8e-4752-81da-24b8fedd1e8c'
```

- Para autenticar:
```
curl -X POST \
  http://localhost:8080/v1/token \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 0e6c25b8-334c-44e5-bd7e-4f50bba94f0d' \
  -d '{
	"generatedKey": "chave gerada no passo anterior",
	"authCode": "codigo gerado no Google Authenticator"
}'
```
