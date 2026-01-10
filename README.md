# SPRING SKELETON

Kafka, DB 사용가능한 뼈대 프로젝트

## FEATURE
* Avro (format)
* Thread
* DB (jdbi + hikariCP)
* Kafka (with avro)

<img src="./assets/structure_v01.png" title="feature" alt=""/>

## Structure
* avro-lib
* common
  * Thread
  * Log
  * Utils
* core
  * Kafka
  * JDBC
  * Web
* sample code
  * zombie-listener
