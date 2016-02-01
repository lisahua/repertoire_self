#!/usr/bin/env python
from sys import argv

def is_config_line(s):
    return s.find("CONFIGFILE") != -1;

def is_start_proc_line(s):
    return s.find("start_proc") != -1;

def is_stop_proc_line(s):
    return s.find("stop_proc") != -1;

def is_request_line(s):
    return s.find("REQUEST") != -1 and s.find("$t") != -1;

assert(len(argv) == 2);
filename = argv[1];

idx = filename.rfind(".");
filebody = filename[0 : idx];
ext = filename[idx+1 :];

headers = [];
f = open(filename, "r");
lines = f.readlines();
f.close();
res = [];

in_header = True;
cur_config = [];
last_request = [];
start_idx = -1;

for line in lines:
    s = line.strip();

    if (is_config_line(s) or is_start_proc_line(s)):
        cur_config.append(line);
        in_header = False;
        start_idx = len(res);
    elif (is_stop_proc_line(s)):
        for i in range(start_idx, len(res)):
            res[i][2] = line;
        if (len(last_request) != 0):
            res.append([last_request, cur_config, line]);
            last_request = [];
        cur_config = [];
    elif ((not in_header) and is_request_line(s)):
        if (len(last_request) != 0):
            res.append([last_request, cur_config, ""]);
        last_request = [line];
    elif (not in_header):
        if ((s != "") and (line[0] != '#')) or (len(last_request) != 0):
            last_request.append(line);

    if (in_header):
        if (line.find("Test::More") == -1):
            headers.append(line);
        else:
            headers.append("use Test::More tests => 3;\n");


for i in range(0, len(res)):
    f = open(filebody + "-" + str(i) + ".t", "w");
    for line in headers:
        f.write(line);
    for line in res[i][1]:
        f.write(line);
    for line in res[i][0]:
        f.write(line);
    for line in res[i][2]:
        f.write(line);
    f.close();

