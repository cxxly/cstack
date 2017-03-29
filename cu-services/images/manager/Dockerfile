FROM ubuntu:14.04

RUN echo "en_US.UTF-8 UTF-8" >> /etc/locale.gen
RUN locale-gen en_US.UTF-8
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8
ENV MAVEN_HOME /usr/share/maven
ENV JAVA_HOME /cloudunit/java/jdk1.8.0_121

RUN apt-get update \
    && apt-get install -y curl \
    && apt-get install -y git \
    && apt-get install -y wget \
    && apt-get install -y git-core \
    && apt-get install -y unzip \
        && rm -r /var/lib/apt/lists/*

RUN git config --global url."https://".insteadOf git://

RUN wget mirrors.tuna.tsinghua.edu.cn/apache/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz -O /tmp/apache-maven-3.3.9-bin.tar.gz
RUN tar -xvf /tmp/apache-maven-3.3.9-bin.tar.gz -C /usr/share && rm /tmp/apache-*
RUN mv /usr/share/apache-maven-3.3.9 /usr/share/maven && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

RUN mkdir -p /cloudunit/java && wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie"  download.oracle.com/otn-pub/java/jdk/8u121-b13/e9e7ea248e2c4826b92b3f075a80e441/jdk-8u121-linux-x64.tar.gz -O /tmp/jdk-8u121-linux-x64.tar.gz
RUN tar -xvf /tmp/jdk-8u121-linux-x64.tar.gz -C /cloudunit/java
RUN rm /tmp/jdk-*

RUN mkdir -p /home/cstack/app

WORKDIR /home/cstack/app

# git branch being bundled in this docker image
ARG GIT_BRANCH
ENV GIT_BRANCH ${GIT_BRANCH:-master}
RUN cd /home/cstack/app && git clone https://github.com/oncecloud/cstack.git -b ${GIT_BRANCH} --depth 1

# Add nodesource PPA for specific version of node and install
RUN curl -sL https://deb.nodesource.com/setup_5.x | bash -
RUN apt-get update && apt-get install -y nodejs

# Fix npm
RUN cd /usr/lib/node_modules/npm \
&& npm install fs-extra \
&& sed -i -e s/graceful-fs/fs-extra/ -e s/fs\.move/fs.rename/ ./lib/utils/rename.js

RUN npm install -g grunt grunt-cli bower
RUN npm config set registry https://registry.npm.taobao.org
RUN cd /home/cstack/app/cstack/cu-manager-ui && npm install
RUN cd /home/cstack/app/cstack/cu-manager-ui && bower --allow-root install
RUN cd /home/cstack/app/cstack/cu-manager-ui && grunt build -f

# Build Maven with profiles
RUN cd /home/cstack/app/cstack/ && mvn clean install -DskipTests -Pdefault -Dmaven.repo.local=/opt/maven/.m2

ENV CATALINA_HOME /opt/tomcat
ENV PATH $PATH:$JAVA_HOME/bin:$CATALINA_HOME/bin:$CATALINA_HOME/scripts

# Install Tomcat
RUN wget http://archive.apache.org/dist/tomcat/tomcat-8/v8.0.38/bin/apache-tomcat-8.0.38.tar.gz && \
        tar -xvf apache-tomcat-8.0.38.tar.gz && \
        rm apache-tomcat*.tar.gz && \
        mv apache-tomcat* ${CATALINA_HOME}

RUN chmod +x ${CATALINA_HOME}/bin/*sh
WORKDIR /opt/tomcat
RUN rm -rf /opt/tomcat/webapps/*
RUN cp -rf /home/cstack/app/cstack/cu-manager/target/ROOT.war /opt/tomcat/webapps
RUN ls -la /opt/tomcat/webapps
RUN unzip /home/cstack/app/cstack/cu-manager/target/ROOT.war -d /opt/tomcat/webapps/ROOT
RUN cp -rf /home/cstack/app/cstack/cu-manager-ui/dist/* /opt/tomcat/webapps/ROOT
ADD tomcat.sh $CATALINA_HOME/scripts/tomcat.sh
RUN chmod +x $CATALINA_HOME/scripts/*
EXPOSE 8080

CMD ["tomcat.sh"]
