FROM gcr.io/kaniko-project/executor as docker-runtime
FROM scratch

COPY --from=docker-runtime /kaniko /kaniko

# Defining default variables and build arguments
ARG user=jenkins
ARG group=jenkins
ARG uid=1000
ARG gid=1000
ARG jenkins_user_home=/home/${user}

ENV JENKINS_USER_HOME=${jenkins_user_home} \
  LANG=C.UTF-8 \
  JAVA_HOME=/usr/lib/jvm/java-1.8-openjdk \
  PATH=${PATH}:/usr/local/bin:/usr/lib/jvm/java-1.8-openjdk/jre/bin:/usr/lib/jvm/java-1.8-openjdk/bin

# Install required packages for running a Jenkins agent
RUN apk add --no-cache \
  bash \
  curl \
  ca-certificates \
  git \
  openjdk8 \
  unzip \
  tar \
  tini

# Set up default user for jenkins
RUN addgroup -g ${gid} ${group} \
  && adduser \
    -h "${jenkins_user_home}" \
    -u "${uid}" \
    -G "${group}" \
    -D "${user}" \
  && echo "${user}:${user}" | chpasswd

# Default working directory
WORKDIR ${jenkins_user_home}
