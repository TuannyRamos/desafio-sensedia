Desafio Sensedia
==================

# O problema

O nosso cliente solicita que autenticação de uma determinada API seja realizada com segundo fator de autenticação usando Google Authenticator.

# Solução
- Geração de chave secreta utilizando algoritmo HOTP;
- 

# Dependências

Para rodar este projeto você vai precisar de
- Java 1.8+
- Maven

Após isto basta seguir os passos abaixo:

# Para executar o projeto

```
java -jar target/desafio-sensedia-0.1.0.jar
```

# Para utilizar o projeto

Basta abrir o navegador e acessar os seguintes endereços:
- Para gerar a key: http://localhost:8080/v1/generateKey
- Para autenticar, copiar a key gerada acima e passar como parâmetro: http://localhost:8080/
- OBS.: será necessário utilizar o Google Authenticator