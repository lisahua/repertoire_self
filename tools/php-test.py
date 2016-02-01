#!/usr/bin/env python
from php_tester import php_tester
from sys import argv
import getopt
from os import system
import os

if __name__ == "__main__":
    if len(argv) < 4:
        print "Usage: php-tester.py <src_dir> <test_dir> <work_dir> [cases]";
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
        a = php_tester(work_dir, src_dir, test_dir);
        s = [];
        for i in ids:
            s.append(int(i));
        ret = a.test(s, profile_dir);
        for i in ret:
            print i,
        print;
        if len(ids) == 1 and len(ret) == 0:
            if "OUTIFFAIL" in os.environ:
                outf = work_dir + "/__cleantests/" + ids[0] + ".out";
                if os.path.exists(outf):
                    system("cp -rf " + outf + " " + os.environ["OUTIFFAIL"]);
            if "EXPIFFAIL" in os.environ:
                expf = work_dir + "/__cleantests/" + ids[0] + ".exp";
                if os.path.exists(expf):
                    system("cp -rf " + expf + " " + os.environ["EXPIFFAIL"]);
