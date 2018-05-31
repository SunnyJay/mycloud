import jinja2
import sys
import codecs
import random
import os

project_name = "tangyuan"


def get_template(searchpath, template_name):
    templateLoader = jinja2.FileSystemLoader(searchpath=searchpath)
    templateEnv = jinja2.Environment(loader=templateLoader)
    template = templateEnv.get_template(template_name)
    return template


def render_template(app_list):
    for app_name in app_list:
        if app_name.startswith("tangyuan-"):
            app_name = app_name.split("-")[1]
            app_port = random.randint(8800, 9000)
            render_dockerfile(app_name, app_port)
            render_k8s_yaml(app_name, app_port)
            render_ingress_yaml(app_name, app_port)


def render_dockerfile(app_name, app_port):
    template = get_template("templates", "Dockerfile")
    render = template.render(project_name=project_name,
                             app_name=app_name,
                             app_port=app_port)
    output_file(render, "Dockerfile", app_name)


def output_file(render, dest, app_name):
    if not os.path.exists(os.path.join("output", project_name + "-" + app_name)):
        os.makedirs(os.path.join("output", project_name + "-"+app_name))
    dest = os.path.join("output", project_name + "-" + app_name, dest)
    with codecs.open(dest, 'wb', encoding='utf-8') as fh:
        fh.write(render)


def render_k8s_yaml(app_name, app_port):
    template = get_template("templates", "k8s.yaml")
    render = template.render(project_name=project_name,
                             app_name=app_name,
                             app_port=app_port)
    output_file(render, "k8s.yaml", app_name)


def render_ingress_yaml(app_name, app_port):
    template = get_template("templates", "ingress.yaml")
    render = template.render(project_name=project_name,
                             app_name=app_name,
                             app_port=app_port)
    output_file(render, "ingress.yaml", app_name)


if __name__ == '__main__':
    render_template(["tangyuan-monitor", "tangyuan-kubernetes"])
    # render_template(sys.argv[1:])