# spark-estudo

## Docker

 1° Roda o docker compose com Cassandra, elastic e kibana
   > docker-compose up

 #### Config Cassandra
 * Buscar o conteiner do cassandra
   > docker ps

 * Entrar no container e executar o cqlsh
  > docker exec -it <conatainer_cassandra>  /bin/bash

  > cqlsh

 * Ir na pasta src/main/resource e rodar o DML
 
 #### Config Elastic
 * Abrir o kibana 
    http://localhost:5601/app/dev_tools#/console
    
 * Executar o script do index no src/main/resource
 
