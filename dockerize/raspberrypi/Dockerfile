FROM resin/rpi-raspbian:jessie

MAINTAINER Oleg Kovalenko <monstrenyatko@gmail.com>

RUN set -x \
	&& sudo apt-key adv --recv-key --keyserver keyserver.ubuntu.com EEA14886 \
	&& echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" > /etc/apt/sources.list.d/java8.list \
	&& apt-get update \
	&& echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections \
	&& apt-get install -y oracle-java8-installer \
	&& apt-get install oracle-java8-set-default \
	&& rm -rf /etc/apt/sources.list.d/java8.list \
	&& apt-get autoremove -y \
	&& apt-get clean \
	&& rm -rf /var/lib/apt/lists/* \
	&& rm -rf /tmp/*

ENV APP_DEB_PKG=butler-sensor-manager-*-all.deb

COPY install/$APP_DEB_PKG /tmp/

RUN set -x \
	&& dpkg --install /tmp/$APP_DEB_PKG \
	&& apt-get update \
	&& apt-get install -y -f \
	&& apt-get clean \
	&& rm -rf /var/lib/apt/lists/* \
	&& rm -rf /tmp/*

VOLUME ["/config", "/data"]

CMD ["/usr/lib/butler-sensor-manager/butler-sensor-manager.sh", "/usr/lib/butler-sensor-manager/butler-sensor-manager.jar", "/config"]
