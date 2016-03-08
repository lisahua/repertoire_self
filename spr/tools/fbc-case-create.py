#!/usr/bin/env python

from os import system, getcwd, chdir
from sys import argv

assert( len(argv) > 3 );
top_build_dir = argv[1];
top_src_dir = argv[2];
case_str = argv[3];
idx = case_str.find("-");
if idx == -1:
    new_rev = case_str;
    old_rev = str(int(new_rev) - 1);
else:
    new_rev = case_str[0:idx];
    old_rev = case_str[idx+1:];

out_dir = getcwd() + "/fbc-case-" + case_str;
system("mkdir " + out_dir);

print "Creating revision log for " + case_str + "...";
cmd = top_src_dir + "/tools/fbc-rev-test.py " + top_src_dir + "/tools/fbc-build.py " + \
    top_src_dir + "/tools/fbc-test.py " + top_build_dir + "/benchmarks/fbc-src " + \
    top_build_dir + "/benchmarks/fbc-tests " + new_rev + " " + old_rev;

system(cmd);
system("cp -f fbc-rev-" + old_rev + "-" + new_rev + ".txt " + \
       out_dir + "/fbc-" + case_str + ".revlog");
system("rm -f fbc-rev-" + old_rev + "-" + new_rev + ".txt");

f = open(top_src_dir + "/benchmarks/fbc-bug-file.log");
lines = f.readlines();
f.close();
d = {};
for line in lines:
    token = line.strip().split();
    if (len(token) != 2):
        continue;
    d[token[0]] = token[1];

print "Creating conf file for " + case_str + "...";
f = open(out_dir + "/fbc-" + case_str + ".conf", "w");
print >> f, "revision_file=" + out_dir + "/fbc-" + case_str + ".revlog";
system("cp -rf " + top_build_dir + "/benchmarks/fbc-src " + out_dir + "/fbc-src");
ori_dir = getcwd();
chdir(out_dir + "/fbc-src/fbc-src");
system("svn revert -R .");
system("svn-clean");
system("svn up -r " + old_rev);
system("svn-clean");
chdir(ori_dir);
print >> f, "src_dir=" + out_dir + "/fbc-src";
print >> f, "test_dir=" + top_build_dir + "/benchmarks/fbc-tests";
print >> f, "build_cmd=" + top_src_dir + "/tools/fbc-build.py";
print >> f, "test_cmd=" + top_src_dir + "/tools/fbc-test.py";
print >> f, "localizer=profile";
print >> f, "bugged_file=" + d[case_str];
print >> f, "fixed_out_file=fbc-fix-" + case_str + ".c";
print >> f, "single_case_timeout=12";
print >> f, "wrap_ld=yes";
f.close();

print "Creating workdir for " + case_str + "...";
system("time " + top_build_dir + "/src/prophet " + out_dir + "/fbc-" + case_str + ".conf -r " + \
       out_dir + "/fbc-" + case_str +"-workdir -init-only");