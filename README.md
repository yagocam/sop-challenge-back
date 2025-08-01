
# Desafio SOP CE - Backend

Esse repositório serve de exclusivamente para todo o serviço de API do desafio técnico da SOP CE.



Etapas realizadas:

 - Utilização SpringBoot
 - Utilização do jPA
 - Utilização de DTO  
 - Testes unitários
 - Utilização do OpenAPI
 - Utilização do Lombok
 - Deploy realizado no railway 
 - Relatórios gerados pelo JasperReports
 - Estrutura de autenticação JWT
 - Utilização do postgreSQL


# Observações:
 - Todo o script do banco de dados foi gerado via pelas entidades via anotação.
 - Toda a modelagem das entidades foram consideradas as regras de negócios opcionais como os status da entidade Despesa (Expense).


## Documentação Swagger

https://sop-challenge-back-production.up.railway.app/swagger-ui/index.html#

## Diagrama DER

![App Screenshot](https://i.postimg.cc/rs3mq9gP/Diagrama-DER-1.png)

## Execução local
- Para executar localmente, basta executar o BackendApplication.
- Para rodar os testes, rode na sessão de testes cada controller. 