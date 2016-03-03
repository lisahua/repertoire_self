#!/usr/bin/env python
from sys import argv
from os import system, path, chdir, getcwd, environ
import subprocess
import getopt

def num2testcase( case ):
    if case=="1":
        return "11 12 13"
    elif case=="2":
        return "14 15 16"
    elif case=="3":
        return "17 18 19"
    elif case=="4":
        return "20 21 22 24"
    elif case=="5":
        return "1 2 3"
    elif case=="6":
        return "3 2"
    elif case=="7":
        return "7 8"
    elif case=="8":
        return "100 17 23"
    elif case=="9":
        return "22 33 44 55 66"
    elif case=="10":
        return "88 99"
    elif case=="11":
        return "1"
    elif case=="12":
        return "2"
        return '';

if __name__ == "__main__":
    opts, args = getopt.getopt(argv[1:], "p:");
    profile_dir = "";
    for o, a in opts:
        if o == "-p":
            profile_dir = a;

    src_dir = args[0];
    test_dir = args[1];
    work_dir = args[2];

    if (len(args) > 3):
        ids = args[3:];
        cur_dir = src_dir;
        if (profile_dir != ""):
            cur_dir = profile_dir;

        for i in ids:
            testcase = num2testcase(i);
            #print "Testing "+testcase;

            #ret = subprocess.call(["sh "+test_dir+"/test.sh  "+src_dir+"/DoublyLinkedList "+testcase], shell=True);
            ret = subprocess.call([src_dir+"/DoublyLinkedList "+testcase], shell=True);
            if ret==0:
                print i
                exit(0)
            else: exit(1)
        print;

