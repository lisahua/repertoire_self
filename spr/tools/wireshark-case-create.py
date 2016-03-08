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
    old_rev = new_rev + "^1";
else:
    new_rev = case_str[0:idx];
    old_rev = case_str[idx+1:];

out_dir = getcwd() + "/wireshark-case-" + case_str;
system("mkdir " + out_dir);

print "Creating revision log for " + case_str + "...";
cmd = top_src_dir + "/tools/wireshark-rev-test.py " + top_src_dir + "/tools/wireshark-build.py " + \
    top_src_dir + "/tools/wireshark-test.py " + top_build_dir + "/benchmarks/wireshark-src " + \
    top_build_dir + "/benchmarks/wireshark-tests " + new_rev + " " + old_rev;

system(cmd);
system("cp -f wireshark-rev-" + old_rev + "-" + new_rev + ".txt " + \
       out_dir + "/wireshark-" + case_str + ".revlog");
system("rm -f wireshark-rev-" + old_rev + "-" + new_rev + ".txt");

f = open(top_src_dir + "/benchmarks/wireshark-bug-file.log");
lines = f.readlines();
f.close();
d = {};
for line in lines:
    token = line.strip().split();
    if (len(token) != 2):
        continue;
    d[token[0]] = token[1];

print "Creating conf file for " + case_str + "...";
f = open(out_dir + "/wireshark-" + case_str + ".conf", "w");
print >> f, "revision_file=" + out_dir + "/wireshark-" + case_str + ".revlog";
system("cp -rf " + top_build_dir + "/benchmarks/wireshark-src " + out_dir + "/wireshark-src");
ori_dir = getcwd();
chdir(out_dir + "/wireshark-src");
system("git checkout -f " + old_rev);
chdir(ori_dir);
print >> f, "src_dir=" + out_dir + "/wireshark-src";
print >> f, "test_dir=" + top_build_dir + "/benchmarks/wireshark-tests";
print >> f, "build_cmd=" + top_src_dir + "/tools/wireshark-build.py";
print >> f, "test_cmd=" + top_src_dir + "/tools/wireshark-test.py";
print >> f, "localizer=profile";
print >> f, "bugged_file=" + d[case_str];
print >> f, "fixed_out_file=wireshark-fix-" + case_str + ".c";
f.close();

print "Creating workdir for " + case_str + "...";
system("time " + top_build_dir + "/src/prophet " + out_dir + "/wireshark-" + case_str + ".conf -r " + \
       out_dir + "/wireshark-" + case_str +"-workdir -init-only");