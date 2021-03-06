FROM debian:jessie

MAINTAINER NGINX Docker Maintainers "docker-maint@nginx.com"

ENV NGINX_VERSION 1.11.9-1~jessie

RUN apt-key adv --keyserver hkp://pgp.mit.edu:80 --recv-keys 573BFD6B3D8FBC641079A6ABABF5BD827BD9BF62 \
	&& echo "deb http://nginx.org/packages/mainline/debian/ jessie nginx" >> /etc/apt/sources.list \
	&& apt-get update \
	&& apt-get install --no-install-recommends --no-install-suggests -y \
						ca-certificates \
						nginx=${NGINX_VERSION} \
						nginx-module-xslt \
						nginx-module-geoip \
						nginx-module-image-filter \
						nginx-module-perl \
						nginx-module-njs \
						gettext-base \
						curl \
	&& rm -rf /var/lib/apt/lists/*

RUN apt-get update \
        && apt-get install -y --no-install-recommends --no-install-suggests netcat \
	    && rm -r /var/lib/apt/lists/*

# forward request and error logs to docker log collector
RUN ln -sf /dev/stdout /var/log/nginx/access.log \
	&& ln -sf /dev/stderr /var/log/nginx/error.log

EXPOSE 80 443

## CLOUDUNIT :: BEGINNING
ENV CU_SOFTWARE /usr/share/nginx/html
ENV CU_SERVER_MANAGER_PATH ""
ENV CU_SERVER_MANAGER_PORT ""
ENV CU_SERVER_PORT 80
ENV CU_SERVER_RESTART_POST_CREDENTIALS false
ENV CU_DEFAULT_LOG_FILE system.out
ENV CU_LOGS stdout

# add custom scripts
ADD scripts /opt/cloudunit/scripts
RUN chmod +x /opt/cloudunit/scripts/*

# Enable server status app
RUN sed -i '2i		location /server-status {\n		stub_status on;\n		access_log   off;\n		allow 127.0.0.1;\n		deny all;\n	}\n' /etc/nginx/conf.d/default.conf

## CLOUDUNIT :: END

COPY docker-entrypoint.sh /usr/local/bin/
ENTRYPOINT ["docker-entrypoint.sh"]

CMD [ "nginx" ]

LABEL origin application
LABEL application-type nginx
LABEL application-version $NGINX_VERSION
