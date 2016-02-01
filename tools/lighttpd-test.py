#!/usr/bin/env python
from sys import argv
from os import environ, system, path, getcwd, chdir
import subprocess
import getopt

if __name__ == "__main__":
    if len(argv) < 4:
        print "Usage: lighttpd-py <src_dir> <test_dir> <work_dir> [cases]"
        exit(1);

    opts, args = getopt.getopt(argv[1:], "p:");
    profile_dir = "";
    for o, a in opts:
        if o == "-p":
            profile_dir = a;

    src_dir = args[0];
    test_dir = args[1];
    work_dir = args[2];
    if len(args) > 3:
        ids = args[3:];
        cur_dir = src_dir;
        if (profile_dir != ""):
            cur_dir = profile_dir;

        if (not path.exists(cur_dir+"/my-tests")):
            system("cp -rf " + test_dir + " " + cur_dir + "/my-tests");

        system("killall -9 lighttpd > /dev/null 2> /dev/null");

        ori_dir = getcwd();
        chdir(cur_dir + "/my-tests");

        ret = subprocess.call(["sh prepare.sh >/dev/null"], shell=True);
        if ret != 0:
            print "Error on preparing";
            assert(0);

        my_env = environ;
        for i in ids:
            testcase = str(i);
            my_env["RUNTESTS"] = testcase;
            ret = subprocess.call(["perl run-tests.pl 1> __out 2>/dev/null"], shell=True, env = my_env);
            if ret != 0:
                system("rm -rf __out");
                continue;

            with open("__out", "r") as fin:
                outs = fin.readlines();

            if ("Result: PASS\n" in outs):
                print i,
            system("rm -rf __out");
        print;

        subprocess.call(["sh cleanup.sh > /dev/null"], shell=True);

        chdir(ori_dir);
