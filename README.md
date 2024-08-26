# Projeto Final
## API de Partidas de Futebol
Apresentação:
A aplicação consistirá em criar uma API de partidas de futebol, onde o usuário poderá manusear dados de clubes, de partidas e de estádios, bem como fazer cruzamento de dados dessas entidades.

## Requisitos não-funcionais:
1.	A aplicação deverá usar linguagem de programação Java versão 17 ou superior (21 recomendada), com framework Spring Boot e Spring Data;
2.	A aplicação deverá utilizar MySQL como banco de dados.
3.	O código deverá estar hospedado no GitHub
   
## Requisitos funcionais mínimos:
1. Cadastrar um clube: A aplicação deverá permitir cadastrar um clube, contendo no mínimo o nome do clube, a sigla do estado onde possui a sua sede, a data de criação do clube e se ele está ativo.
●	Método: POST
●	Retorno esperado: 201 CREATED.
●	Cenários de exceção: 
○	Dados inválidos: no caso de tentativa de cadastro de um clube sem os dados mínimos mencionados acima, OU com nome menor do que duas letras, OU para um estado inexistente no Brasil, OU com data de criação no futuro, a aplicação deverá impedir o cadastro e retornar 400 BAD REQUEST.
○	Conflito de dados: no caso de tentativa de cadastro de um clube com nome igual à outro clube do mesmo estado, aplicação deverá impedir o cadastro e retornar 409 CONFLICT.

2. Editar um clube: A aplicação deverá permitir editar os dados de um clube.
●	Método: PUT
●	Retorno esperado: 200 OK.
●	Cenários de exceção:
○	Dados inválidos: no caso de tentativa de atualizar os dados do nome do clube para um nome menor do que duas letras, ou para um estado inexistente no Brasil, ou para uma data de criação no futuro, a aplicação deverá impedir a atualização e retornar 400 BAD REQUEST.
○	Data inválida: no caso de edição da data de criação do clube para uma data posterior à alguma partida do clube já registrada, a aplicação deverá impedir a atualização e retornar 409 CONFLICT.
○	Conflito de dados: no caso de tentativa de atualização do nome do clube para um nome igual à outro clube do mesmo estado, aplicação deverá impedir a atualização e retornar 409 CONFLICT.
○	Clube não existe: no caso de tentativa de atualização de um clube que não existe na base de dados, a aplicação deverá retornar 404 NOT FOUND.

3. Inativar um clube: A aplicação deverá permitir inativar um clube, sem apagar seus dados do banco de dados.
●	Método: DELETE
●	Retorno esperado: 204 NO CONTENT.
●	Cenários de exceção:
○	Clube não existe: no caso de tentativa de inativação de um clube que não existe na base de dados, a aplicação deverá retornar 404 NOT FOUND.

4. Buscar um clube: A aplicação deverá permitir buscar um clube pelo seu ID.
●	Método: GET
●	Retorno esperado: 200 OK.
●	Cenários de exceção:
○	Sem resultado: no caso de busca sem resultado, a aplicação deverá retornar 404 NOT FOUND.

5. Listar clubes: A aplicação deverá permitir listar todos os clubes, ou filtrar a busca pelo nome, pelo estado, ou pela situação (ativa ou inativa), permitindo a paginação dos resultados, e a ordenação dos resultados por estes mesmos campos, de forma ascendente ou descendente.
●	Método: GET
●	Retorno esperado: 200 OK.
●	Cenários de exceção:
○	Sem resultado: no caso de busca sem resultado, a aplicação deverá retornar uma lista vazia com o status 200 OK.

6. Cadastrar uma partida: A aplicação deverá permitir cadastrar uma partida, contendo no mínimo os dois clubes envolvidos, o resultado, o estádio e a data e hora da partida.
●	Método: POST
●	Retorno esperado: 201 CREATED.
●	Cenários de exceção: 
○	Dados inválidos: no caso de tentativa de cadastro de uma partida sem os dados mínimos mencionados acima, OU com dois clubes iguais, OU para um clube que não existe, OU para um estádio que não existe, OU com o resultado com número negativo de gols, OU com data e hora no futuro, a aplicação deverá impedir o cadastro e retornar 400 BAD REQUEST.
○	Data inválida: no caso de tentativa de cadastro de uma partida com data e hora anteriores à data de criação de um clube envolvido na partida, a aplicação deverá impedir o cadastro e retornar 409 CONFLICT.
○	Clube inativo: no caso de tentativa de cadastro de uma partida com um clube inativo, a aplicação deverá impedir o cadastro e retornar 409 CONFLICT.
○	Partidas com horários próximos: no caso de tentativa de cadastro de uma partida onde um dos clubes envolvidos já possua outra partida marcada com diferença menor do que 48 horas em relação a esta, a aplicação deverá impedir o cadastro e retornar 409 CONFLICT.
○	Estádio já possui jogo: no caso de tentativa de cadastro de uma partida onde o estádio já possua outra partida cadastrada para o mesmo dia, a aplicação deverá retornar 409 CONFLICT.

7. Editar uma partida: A aplicação deverá permitir editar os dados de uma partida.
●	Método: PUT
●	Retorno esperado: 200 OK.
●	Cenários de exceção:
○	Dados inválidos: no caso de tentativa de atualização de uma partida para dois clubes iguais, OU para um clube que não existe, OU para um estádio que não existe, OU para um resultado com número negativo de gols, OU para uma data e hora no futuro, a aplicação deverá impedir a atualização e retornar 400 BAD REQUEST.
○	Data inválida: no caso de tentativa de atualização de uma partida com data e hora anteriores à data de criação de um clube envolvido na partida, a aplicação deverá impedir a atualização e retornar 409 CONFLICT.
○	Clube inativo: no caso de tentativa de atualização de uma partida substituindo um clube para outro que atualmente está inativo, a aplicação deverá impedir a atualização e retornar 409 CONFLICT.
○	Partidas com horários próximos: no caso de tentativa de atualização do horário de uma partida para um horário onde um dos clubes envolvidos já possua outra partida cadastrada com diferença menor do que 48 horas, a aplicação deverá impedir a atualização e retornar 409 CONFLICT.
○	Estádio já possui jogo: no caso de tentativa de atualização da data de uma partida para uma data na qual o estádio já tenha outra partida cadastrada para o mesmo dia, a aplicação deverá retornar 409 CONFLICT.
○	Partida não existe: no caso de tentativa de atualização de uma partida que não existe na base de dados, a aplicação deverá retornar 404 NOT FOUND.

8. Remover uma partida: A aplicação deverá permitir remover uma partida.
●	Método: DELETE
●	Retorno esperado: 204 NO CONTENT.
●	Cenários de exceção:
○	Partida não existe: no caso de tentativa de remoção de uma partida que não existe na base de dados, a aplicação deverá retornar 404 NOT FOUND.

9. Buscar uma partida: A aplicação deverá permitir buscar uma partida pelo seu ID.
●	Método: GET
●	Retorno esperado: 200 OK.
●	Cenários de exceção:
○	Sem resultado: no caso de busca sem resultado, a aplicação deverá retornar 404 NOT FOUND.

10. Listar partidas: A aplicação deverá permitir listar todas as partidas, ou filtrar a busca por um clube, ou por um estádio, permitindo a paginação dos resultados, e a ordenação dos resultados por estes mesmos campos, de forma ascendente ou descendente.
●	Método: GET
●	Retorno esperado: 200 OK.
●	Cenários de exceção:
○	Sem resultado: no caso de busca sem resultado, a aplicação deverá retornar uma lista vazia com o status 200 OK.

11. Cadastrar um estádio: A aplicação deverá permitir cadastrar um estádio, contendo no mínimo o nome do estádio.
●	Método: POST
●	Retorno esperado: 201 CREATED.
●	Cenários de exceção: 
○	Dados inválidos: no caso de tentativa de cadastro de um estádio sem os dados mínimos mencionados acima, OU o nome menor do que 3 letras, a aplicação deverá impedir o cadastro e retornar 400 BAD REQUEST.
○	Estádio já existe: no caso de tentativa de cadastro de um estádio com nome idêntico a outro já cadastrado, a aplicação deverá retornar 409 CONFLICT.

12. Editar um estádio: A aplicação deverá permitir editar os dados de um estádio.
●	Método: PUT
●	Retorno esperado: 200 OK.
●	Cenários de exceção:
○	Dados inválidos: no caso de tentativa de atualização de um estádio para um nome menor do que 3 letras, a aplicação deverá impedir a atualização e retornar 400 BAD REQUEST.
○	Estádio já existe: no caso de tentativa de atualização de um estádio para nome idêntico a outro já cadastrado, a aplicação deverá retornar 409 CONFLICT.
○	Estádio não existe: no caso de tentativa de atualização de um estádio que não existe na base de dados, a aplicação deverá retornar 404 NOT FOUND.

13. Buscar um estádio: A aplicação deverá permitir buscar um estádio pelo seu ID.
●	Método: GET
●	Retorno esperado: 200 OK.
●	Cenários de exceção:
○	Sem resultado: no caso de busca sem resultado, a aplicação deverá retornar 404 NOT FOUND.

14. Listar estádios: A aplicação deverá permitir listar todos os estádios, permitindo a paginação dos resultados, e a ordenação dos resultados pelo nome, de forma ascendente ou descendente.
●	Método: GET
●	Retorno esperado: 200 OK.
●	Cenários de exceção:
○	Sem resultado: no caso de busca sem resultado, a aplicação deverá retornar uma lista vazia com o status 200 OK.

