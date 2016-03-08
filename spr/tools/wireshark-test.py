#!/usr/bin/env python
from sys import argv
import getopt
from os import chdir, getcwd, system, path, environ
import subprocess

if __name__ == "__main__":
    opts, args = getopt.getopt(argv[1 :], "p:");
    profile_dir = "";
    for o, a in opts:
        if o == "-p":
            profile_dir = a;

    src_dir = args[0];
    test_dir = args[1];
    work_dir = args[2];

    if (len(args) > 3):
        ids = args[3 :];
        cur_dir = src_dir;
        if (profile_dir != ""):
            cur_dir = profile_dir;
        if (not path.exists(cur_dir + "/oldtest")):
            system("mv " + cur_dir + "/test " + cur_dir + "/oldtest");
            system("cp -rf " + test_dir + " " + cur_dir + "/test");
            system("cp -rf " + test_dir + "/mytest.sh " + cur_dir + "/mytest.sh");
            system("cp -rf " + test_dir + "/services " + cur_dir + "/services");

        ori_dir = getcwd();
        chdir(cur_dir);
        my_env = environ;
        for i in ids:
            case_str = i;
            ret = subprocess.call(["./mytest.sh " + case_str + " 1>/dev/null 2>/dev/null"], shell = True);
            if (ret == 0):
                print i,
        chdir(ori_dir);
