# User Service

### Requisitos
Maven
Docker

Banco de Dados: Para subir o banco de dados optei por criar ele em um container, com isso precisamos de rodar os comandos abaixo
para buscar a imagem do mysql e expor o container

docker pull mysql
docker run --name mysql-database -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=user_database -e MYSQL_USER=springuser -e MYSQL_PASSWORD=ThePassword -d mysql:8

### Como construir a aplicacao
Abrir o diretorio do projeto e rodar os seguintes comandos, 
para que possamos limpar o projeto, empacotar, criar uma imagem docker
e por fim subir a aplicacao em um container na porta 8080.

mvn clean package
docker build -t user-service .
docker run -p 8080:8080 --name user-service --link mysql-database:mysql -d user-service

Para realizar os testes basta rodar o comando abaixo:
mvn test

E apos esses comandos e possivel acessar a documentacao da API Swagger pelo link abaixo
--http://localhost:8080/swagger-ui.html

###Ferramentas utilizadas
Neste projeto foi utilizado as seguintes ferramentas:

 * Docker: Foi implementado um DockerFile com o intuito de subir a aplicacao via container e simplificar o processo
 * Swagger: A escolha do Swagger foi para que a API fosse documentada totalmente, facilitando a visualizacao.
 * Lombok: Para auxiliar na criacao automatica de Getters, Setters e Constructors
 


##Montar o ambiente de producao
Segue abaixo os passos necessarios para colocar o servico em producao:
 * Configurar uma integraçao automatizada, criando um job no Jenkins para construir o serviço e gerar a imagem docker
 * Criar um cluster em alguma plataforma para o container do servico
 * Configurar um API Gateway para expor o servico para a internet
 * Criar regras de AutoScaling para que o servico escale automaticamente caso o numero de requisicoes aumentem
 


    

