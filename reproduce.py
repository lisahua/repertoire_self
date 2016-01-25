#!/usr/bin/env python
import glob
import os
import csv
import getopt
from sys import argv, exit
from os import system

def print_defect():
    print "Available defect/change list:";
    for d in defect_list:
        if d[2].strip() != "":
            print d[0] + '-' + d[1];

(opts, args) = getopt.getopt(argv[1:], "", ["nloc=", "bug-file", "parse-space", "init", "prophet"]);
nof = True;
nloc = 200;
parse_space = False;
init_only = False;
prophet = False;
for o, a in opts:
    if o == "--bug-file":
        nof = False;
    elif o == "--nloc":
        nloc = int(a);
    elif o == "--parse-space":
        parse_space = True;
    elif o == "--init":
        init_only = True;
    elif o == "--prophet":
        prophet = True;

scenario_addr = "http://rhino.csail.mit.edu/spr-rep/scenarios/";

defect_list = [];
current_cwd = os.path.abspath(os.path.dirname(argv[0]));
with open(current_cwd + "/nameref.csv", "rU") as f:
    reader = csv.reader(f);
    for row in reader:
        defect_list.append(row);

if (len(args) < 1):
    print_defect();
    exit(0);

defect_to_run = args[0];

idx = defect_to_run.find("-");
if (idx == -1):
    print "Invalid defect id";
    print_defect();
    exit(1);

app = defect_to_run[0:idx];
case_id = defect_to_run[idx+1:];
found = False;

for row in defect_list:
    if (app == row[0]) and (case_id == row[1]):
        found = True;
        defect_token = row[2].strip();
        break;

if (not found) or (defect_token == ""):
    print "Unkown defect id";
    print_defect();
    exit(1);

if (not os.path.exists(defect_token)):
    cmd = "wget " + scenario_addr + defect_token;
    ret = system(cmd);
    if (ret != 0):
        print "Downlaod scenario failed, check network status!";
        exit(1);
else:
    print "Work with existing tarball";

cmd = "rm -rf " + app + "-case*";
system(cmd);
cmd = "tar xvzf " + defect_token;
system(cmd);
glob_res = glob.glob(app + "-case-*");
assert( len(glob_res) > 0);
case_dir = glob_res[0];
glob_res = glob.glob(case_dir + "/" + app + "*workdir");
assert( len(glob_res) > 0);
work_dir = glob_res[0];

if init_only:
    cmd = "rm -rf " + work_dir + "/profile_localization.res";
    system(cmd);
    cmd = "time ../src/prophet -r " + work_dir + " -init-only";
    system(cmd);
elif parse_space:
    cmd = "../../tools/spr-correct-compare.py";
    if (nof):
        cmd += " --nof";
    system(cmd);
else:
    cmd = "time ../src/prophet -r " + work_dir + " -skip-verify";
    if (nof):
        cmd += " -consider-all -first-n-loc " + str(nloc);
    if (prophet):
        para_file = " ../../crawler/para-";
        if (app == "php") or (app == "libtiff") or (app == "wireshark") or (app == "python"):
            para_file += app +".out";
        else:
            para_file += "all.out";
        cmd += " -feature-para " + para_file;
    print "Invoking: " + cmd;
    system(cmd);
