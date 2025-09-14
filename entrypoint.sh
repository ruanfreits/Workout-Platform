#!/bin/bash
java -jar /app/app.jar  --spring.datasource.url=jdbc:mysql://dbwp:3306/exercises --aws.s3.endpoint=http://storage:4566 --aws.s3.path-style-access=true
