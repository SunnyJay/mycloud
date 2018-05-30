import os
import shutil

def copy_tar():
    dir_list = os.listdir("..")
    print dir_list
    for dir in dir_list:
        if dir.startswith("tangyuan-"):
            src = os.path.join("..", dir, "build", "distributions", dir + ".tar")
            dest = os.path.join("output", dir)
            if os.path.exists(src) and os.path.exists(dest):
                shutil.copy(src, os.path.join(dest, dir + ".tar"))

if __name__ == '__main__':
    copy_tar()
