FROM sgrio/java-oracle
MAINTAINER sunnnanjun

ADD build/distributions/{{project_name}}-{{app_name}}.tar /usr/local/
EXPOSE {{app_port}}
CMD ["sh", "/usr/local/{{project_name}}-{{app_name}}/bin/{{project_name}}-{{app_name}}"]
