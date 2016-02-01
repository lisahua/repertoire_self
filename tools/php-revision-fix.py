#!/usr/bin/env python
from sys import argv

for filename in argv[1:]:
    print "Processing: ", filename;
    f = open(filename, "r");
    lines = f.readlines();
    tokens1 = lines[4].strip().split();
    f.close();
    tot = int(tokens1[len(tokens1) - 1]);
    tokens2 = lines[5].strip().split();
    new_tokens = [];
    for token in tokens2:
        if (token == "9436") or (token == "11646") or (token == "10854") \
            or (token == "10916") or (token == "11345") or (token == "11368") \
            or (token == "11659") or (token == "11844") or (token == "1819") or (token == "9408"):
            tot = tot - 1;
            continue;
        new_tokens.append(token);
    f = open(filename, "w");
    f.write(lines[0]);
    f.write(lines[1]);
    f.write(lines[2]);
    f.write(lines[3]);
    f.write(" ".join(tokens1[0:len(tokens1) - 1]) + " " + str(tot) + "\n");
    f.write(" ".join(new_tokens) + "\n");
    f.close();

