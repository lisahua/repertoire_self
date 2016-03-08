from os import system
from sys import argv

dest_dir = argv[1];
log_file = argv[2];

f = open(dest_dir + "/" + log_file, "w");
cnt = 0;
v = [];
for filename in sorted(argv[3:]):
    cnt = cnt + 1;
    cmd = "cp " + filename + " " + dest_dir + "/" + str(cnt)+".t";
    system(cmd);
    v.append(filename);

print >>f, len(v);
for i in range(0, len(v)):
    print >>f, i+1;
    print >>f, v[i];
f.close();
