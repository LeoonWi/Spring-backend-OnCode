#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i ~/.ssh/id_ed25519 \
    target/oncode-0.0.1-SNAPSHOT.jar \
    root@147.45.101.15:/root/

echo 'Restart server...'

ssh -i ~/.ssh/id_ed25519 root@147.45.101.15 << EOF

pgrep java | xargs kill -9
nohup java -jar oncode-0.0.1-SNAPSHOT.jar > log.txt &

EOF

echo 'Bye'