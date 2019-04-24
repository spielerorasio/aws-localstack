
This project is a sample use of localstack
https://github.com/localstack/localstack
using spring https://cloud.spring.io/spring-cloud-aws/spring-cloud-aws.html

you can find a smaple using spring aws with S3 / SNS / SQS


To run the project
on windows / linux
docker-compose up

mac
TMPDIR=/private$TMPDIR docker-compose up -d

you can access localstack at  http://localhost:8888/#/infra

git clone this project
mvn clean install
mvn spring-boot:run


Playing with aws cli and localstack
aws configure --region us-east-1


Setup topics, queues and subscriptions:


aws --endpoint-url=http://localhost:4576 sqs create-queue --queue-name queue-name

aws --endpoint-url=http://localhost:4575 sns create-topic --name sns-topic

SNS supports multiple endpoint types (SQS, Email, HTTP, HTTPS),
we can route to sqs queue test-queue-sns   using

aws --endpoint-url=http://localhost:4576 sqs create-queue --queue-name test-queue-sns

in case endpoint is SQS queue
aws --endpoint-url=http://localhost:4575 sns subscribe --topic-arn arn:aws:sns:us-east-1:123456789012:sns-topic --protocol sqs --notification-endpoint arn:aws:sqs:eu-east-1:queue:test-queue-sns

in case endpoint is controller
make sure container has access to hst machine => for example use host.docker.internal

aws --endpoint-url=http://localhost:4575 sns subscribe --topic-arn arn:aws:sns:us-east-1:123456789012:sns-topic --protocol http --notification-endpoint http://host.docker.internal:8080/sns-topic-controller/


to unsubscribe : example
aws --endpoint-url=http://localhost:4575 sns unsubscribe --subscription-arn  arn:aws:sns:us-east-1:123456789012:sns-topic:795eb526-3991-4503-8578-cfcaa2d23325

list
aws --endpoint-url=http://localhost:4576 sqs list-queues
aws --endpoint-url=http://localhost:4575 sns list-topics
aws --endpoint-url=http://localhost:4575 sns list-subscriptions



aws --endpoint-url=http://localhost:4575 sns confirm-subscription --topic-arn arn:aws:sns:us-east-1:123456789012:sns-topic




aws --endpoint-url=http://localhost:4575 sns publish --topic-arn "arn:aws:sns:us-east-1:123456789012:sns-topic" --subject "my subject for notification" --message "Orasio Spieler"




create buckets
aws --endpoint-url=http://localhost:4572 s3 mb s3://orasio-bucket

add content to a bucket cd into the '/files' folder of this repo
aws --endpoint-url=http://localhost:4572 s3 cp hello.txt s3://orasio-bucket

check the change of state in the browser
http://localhost:4572/
http://localhost:4572/orasio-bucket


