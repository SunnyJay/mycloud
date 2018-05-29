FROM sgrio/java-oracle
MAINTAINER sunnnanjun

ADD build/distributions/tangyuan-kubernetes.tar /usr/local/
EXPOSE 8888
CMD ["sh", "/usr/local/tangyuan-kubernetes/bin/tangyuan-kubernetes"]
