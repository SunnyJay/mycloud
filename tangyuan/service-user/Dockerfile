FROM java:9
MAINTAINER sunnnanjun

ADD build/distributions/service-user-boot.tar /usr/local/
EXPOSE 8899
CMD ["sh", "/usr/local/service-user-boot/bin/service-user"]
