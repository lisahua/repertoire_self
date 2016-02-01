#!/usr/bin/env python
from php_tester import php_tester, php_initializer
from git_tester import git_tester, git_initializer
from sys import argv
from os import path, mkdir, remove
import time
import shutil
import subprocess

if __name__ == "__main__":
    if len(argv) < 3:
        print "Usage: revision-tester.py <php|git> <out_dir> [<# of parallel> <id of parallel>]"
        exit(1);
    app = argv[1];
    out_dir = argv[2];
    if len(argv) < 4:
        para_n = 1;
        para_id = 0;
    else:
        para_n = int(argv[3]);
        para_id = int(argv[4]);

    gap = 10;
    workdir = "__tmp" + str(para_id);
    if path.exists(workdir):
        shutil.rmtree(workdir);
    mkdir(workdir);
    ret = subprocess.call(["mount", "-t", "ramfs", "ramfs", workdir]);
    if ret != 0:
        print "Need root to start this program!";
        exit(1);
    ret = subprocess.call(["chmod", "a+w", workdir]);
    if ret != 0:
        print "Need root to change permision!";
        exit(1);

    master_repo = "__tmpmaster";
    if para_id == 0:
        if app == "php":
            init = php_initializer();
        else:
            init = git_initializer();
        if path.exists(out_dir+"/done.0"):
            remove(out_dir + "/done.0");
        if path.exists(out_dir):
            s = raw_input(out_dir+" exists, overwrite?(y/n)");
            if s[0] != 'y':
                exit(0);
            shutil.rmtree(out_dir);
        # if there is repo, skip the git clone process
        ok = False;
        if path.exists(master_repo):
            ret = init.display_info(master_repo);
            if ret:
                s = raw_input("ok to proceed?(y/n)");
                if s[0] == 'y':
                    ret = init.reset(master_repo);
                    if ret:
                        ok = True;
                    else:
                        print "Bad repository in " + master_repo + "refetch!";

        if not ok:
            ret = init.create_repo(master_repo, "__fixes.log");
            if not ret:
                print "Failed to fetch! abort!";
                exit(1);

        ret = init.extract_tests(master_repo, out_dir);
        if not ret:
            print "Failed to extract test cases";
            exit(1);
        open(out_dir+"/done.0", "w").close();
    else:
        # wait for the master to finish
        while not path.exists(out_dir+"/done.0"):
            time.sleep(1);

    tmprepo1 = workdir + "/tmp1";
    tmprepo2 = workdir + "/tmp2";
    shutil.copytree(master_repo, tmprepo1);
    shutil.copytree(master_repo, tmprepo2);
    testdir = workdir + "/tests";
    shutil.copytree(out_dir, testdir);

    if app == "php":
        tester1 = php_tester(workdir, tmprepo1, testdir);
        tester2 = php_tester(workdir, tmprepo2, testdir);
    else:
        tester1 = git_tester(workdir, tmprepo1, testdir);
        tester2 = git_tester(workdir, tmprepo2, testdir);

    cnt = 0;
    gap_cnt = 0;
    f = open("__fixes.log", "r");
    lines = f.readlines();
    f.close();
    fix_revision = "";
    result = set();
    for line in lines:
        tokens = line.strip("\n").strip(" ").split();
        if (len(tokens) < 2):
            continue;
        if (tokens[0] == "Fix") and (tokens[1] == "Revision:"):
            fix_revision = tokens[2];
        if (tokens[0] == "Previous") and (tokens[1] == "Revision:"):
            if gap_cnt % gap == 0:
                if cnt % para_n == para_id:
                    ret = tester1.set_revision(fix_revision);
                    if not ret:
                        fout = open(out_dir + "/failed-" +fix_revision + ".log", "w");
                        print "Failed to build revision" + fix_revision;
                        print >>fout, "Failed to build revision" + fix_revision;
                        fout.close();
                        cnt = cnt + 1;
                        gap_cnt = gap_cnt + 1;
                        continue;
                    s1 = tester1.test_all();
                    s2 = tester1.test_all();
                    if (s1 != s2):
                        print "Found non-deterministic-cases:"
                        diff = (s1 - s2) | (s2 - s1);
                        print diff;
                        fout = open(out_dir+"/revision-" + fix_revision + ".log", "w");
                        print >>fout, diff;
                        fout.close();
                        assert(len(diff)>0);
                        result = result | diff;
                    else:
                        fout = open(out_dir+"/nodiff-" + fix_revision + ".log", "w");
                        print >>fout, "No-diff";
                        fout.close();
                cnt = cnt + 1;
            gap_cnt = gap_cnt + 1;
    print result;
    fout = open("res" + str(para_id) + ".log", "w");
    print >>fout, result;
    fout.close();

    subprocess.call(["umount", workdir]);
    shutil.rmtree(workdir);
