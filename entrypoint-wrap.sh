#!/bin/bash

echo "Attempt to create topic 'my-topic' for project 'interlok-test'"
project="interlok-test"
topic="my-topic"
# "Create topic 'my-topic' for project 'interlok-test'"
CREATE_TOPIC="curl -X PUT http://localhost:8042/v1/projects/$project/topics/$topic --output /dev/null --silent --head --fail"
until $CREATE_TOPIC; do
  echo "gcloud pub-sub emulator is unavailable - retry later"
  sleep 4
done &

exec /docker-entrypoint.sh "$@"
