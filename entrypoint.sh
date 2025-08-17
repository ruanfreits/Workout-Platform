#!/bin/bash
java -jar /app/app.jar  --aws.s3.endpoint=http://storage:4566 --aws.s3.path-style-access=true
