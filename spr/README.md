
notes to install SPR:
1. at dir ~/WorkSpace/  run make clean and make install
2. manually install out-of-date pcre-8.36 and comment out wget install pcre-86, move it to lighttpd-deps according to MakeFile, and comment out  in MakeFile
3. FYI:
1962  make clean
1963  make install
1964  sudo make install
1965  wget ftp://ftp.csx.cam.ac.uk/pub/software/programming/pcre/pcre-8.36.tar.gz
1966  wget pcre-8.36
1967  wget ftp://ftp.cs.stanford.edu/pub/exim/pcre/pcre-8.36.tar.gz
1968  sudo make install
1969  ls /home/fanl/Workspace/prophet/build/benchmarks/lighttpd-deps
1970  vim Makefile 
1971  ls /home/fanl/Workspace/prophet/build/benchmarks/lighttpd-deps
1972  mv ~/Workspace/prophet/build/pcre-8.36.tar.gz ./
1973  mv ~/Workspace/prophet/build/pcre-8.36.tar.gz /home/fanl/Workspace/prophet/build/benchmarks/lighttpd-deps/
1974  make
1975  vim benchmarks/lighttpd-deps/Makefile 
1976  make
