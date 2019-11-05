# Email Notifications for Pivnet

## Overview
This application is a REST based webapp that uses your PivNet token to extract the latest PivNet product details and email them as notifications for intended recipients. Make sure that your platform allows egress through your SMTP port (usually 587).

For purposes of simplicity, this app will use an in-memory H2 database to store a list of email recipients. If you need to redeploy the application, follow the setup steps again.

## Variables
Take note of a few variables that we will reference with this application. Values of the variables will be recommeneded but feel free to use your own naming conventions.

1. CREDENTIALS_INSTANCE_NAME = email-credentials-service

## Run the Application in Pivotal Cloud Foundry (PAS)
Inject your `spring.mail` properties. If you are using Pivotal Cloud Foundry, you can do so with the CredHub Broker or CUPS (custom user provided service).

1. Edit `src/resources/application.yml` with the CREDENTIALS_INSTANCE_NAME value defined in the previous section.
```
# Spring mail
spring.mail.host=${vcap.services.CREDENTIALS_INSTANCE_NAME.credentials.host}
spring.mail.port=${vcap.services.CREDENTIALS_INSTANCE_NAME.credentials.port}
spring.mail.username=${vcap.services.CREDENTIALS_INSTANCE_NAME.credentials.username}
spring.mail.password=${vcap.services.CREDENTIALS_INSTANCE_NAME.credentials.password}
...
```
1. Create a Crehub broker or CUPS instance.
```
cf create-service credhub default $CREDENTIALS_INSTANCE_NAME -c "{ \"host\": \"MAIL_HOST\", \"port\": \"MAIL_PORT\", \"username\": \"MAIL_USERNAME\", \"password\": \"MAIL_PASSWORD\" }"
```
OR
```
cf cups $CREDENTIALS_INSTANCE_NAME -p "{ \"host\": \"MAIL_HOST\", \"port\": \"MAIL_PORT\", \"username\": \"MAIL_USERNAME\", \"password\": \"MAIL_PASSWORD\" }"
```
1. Deploy the application after editing the manifest: `cf push`
1. Bind your service to your application: `cf bind-service APP_NAME CREDENTIALS_INSTANCE_NAME`

## API

### Add a recipient
1. Endpoint: APP_ROUTE/add-user
1. Method: GET
1. Request Parameters: (String) email
1. Example REST call: `curl -k https://APP_ROUTE/add-user?email=USER_EMAIL`

### Check an added recipient
1. Endpoint: APP_ROUTE/check-user
1. Method: GET
1. Request Parameters: (String) email
1. Example REST call: `curl -k https://APP_ROUTE/check-user?email=USER_EMAIL`

### Check all recipients
1. Endpoint: APP_ROUTE/list-user
1. Method: GET
1. Example REST call: `curl -k https://APP_ROUTE/check-user`

### Add a product to subscribe for a recipient
1. Endpoint: APP_ROUTE/add-product
1. Method: GET
1. Request Parameters: (String) email, (String) product
1. Example REST call: `curl -k https://APP_ROUTE/add-product?email=USER_EMAIL&product=elastic-runtime`
1. Example REST call: `curl -k https://APP_ROUTE/add-product?email=USER_EMAIL&product=ops-manager`

### Check a product subscribed for a recipient
1. Endpoint: APP_ROUTE/check-product
1. Method: GET
1. Request Parameters: (String) email, (String) product
1. Example REST call: `curl -k https://APP_ROUTE/check-product?email=USER_EMAIL&product=elastic-runtime`
1. Example REST call: `curl -k https://APP_ROUTE/check-product?email=USER_EMAIL&product=ops-manager`

### Add PivNet Refresh Token
1. Endpoint: APP_ROUTE/add-refreshtoken
1. Method: GET
1. Request Parameters: (String) token
1. Example REST call: `curl -k https://APP_ROUTE/add-refreshtoken?token=TOKEN`

### Get PivNet Access Token
1. Endpoint: APP_ROUTE/get-accesstoken
1. Method: GET
1. Example REST call: `curl -k https://APP_ROUTE/get-accesstoken`

### Send emails to all recipients for all product versions (not recommended)
1. Endpoint: APP_ROUTE/send-emails
1. Method: GET
1. Example REST call: `curl -k https://APP_ROUTE/send-emails`

### Send emails to all recipients for latest product version
1. Endpoint: APP_ROUTE/send-new-emails
1. Method: GET
1. Example REST call: `curl -k https://APP_ROUTE/send-new-emails`

## Operate
Follow the following instructions to start sending email notifications.

Add recipients and products to subsrcibe to. For each user:
```
curl -k https://APP_ROUTE/add-user?email=USER_EMAIL
curl -k https://APP_ROUTE/add-product?email=USER_EMAIL&product=elastic-runtime
curl -k https://APP_ROUTE/add-product?email=USER_EMAIL&product=ops-manager
```

Create a cron job to ping this app to send emails:
```
curl -k https://APP_ROUTE/send-new-emails
```

