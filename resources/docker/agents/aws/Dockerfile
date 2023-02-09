FROM jenkins/slave:alpine

ARG AWS_CLI_VERSION=1.16.139
ARG S3_CMD_VERSION=2.0.2

USER root

# Install AWS cli (see https://github.com/alecharp/build-tools/blob/docker-aws/Dockerfile)
RUN apk add --update python py-pip && \
    pip install --no-cache-dir --upgrade pip awscli==$AWS_CLI_VERSION s3cmd==$S3_CMD_VERSION && \
    apk -v --purge del py-pip && \
    rm /var/cache/apk/*

USER jenkins
