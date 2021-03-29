# interlok-gcloud-pubsub-interop [![Actions Status](https://github.com/adaptris-labs/interlok-gcloud-pubsub-interop/workflows/verifyInterlokConfig/badge.svg)](https://github.com/adaptris-labs/interlok-gcloud-pubsub-interop/actions)

Test interlok configuration that has two workflows:

* One that exposes a HTTP endpoint `/api/gcp` which will produce to gcp pubsub topic `my-topic`
* Another than consumes from gcp pubsub topic `my-topic` and logs the output.

## Setup

```shell
# start pubsub emulator
docker-compose up -d
# create topic
curl -X PUT http://localhost:8042/v1/projects/interlok-test/topics/my-topic
# build interlok
gradle clean install
# start interlok
cd ./build/distribution
java -jar ./lib/interlok-boot.jar
```

## Extra

If you want to create a new topic you do it via curl:

```shell
curl -X PUT http://localhost:8042/v1/projects/interlok-test/topics/my-new-topic
```

## Testing

```shell
curl -X POST http://localhost:8080/api/gcp -d '{ "key" : "value" }'
```
