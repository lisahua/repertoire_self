#pragma once
#include <algorithm>
#include <vector>
#include <string>
#include <set>
#include <time.h>

// This is the set of wrappers for output
void outlog_open(const char* filename, size_t v_level, size_t l_level);
int outlog_printf(size_t level, const char* format, ...);
void outlog_close();

namespace clang {
    class ASTContext;
    class CompoundStmt;
    class Stmt;
}

bool readCodeToString(const std::string &file, std::string &code);

bool readCodeInLines(const std::string &file, std::vector<std::string> &lines);

void parseArgFile(const std::string &arg_file, std::string &build_dir, std::vector<std::string> &build_args);

std::string stripLine(const std::string &);

std::string get_ext(const std::string &s);

std::string replace_ext(const std::string &str, const std::string &ext);

bool existFile(const std::string &file);

bool exist_directory(const std::string &dir);

bool is_header(const std::string &str);

std::string getFullPath(const std::string &path);

std::set<std::string> splitStringWithWhite(const std::string &str);

int execute_with_timeout(const std::string &cmd, unsigned long timeout);

static inline bool isSystemHeader(const std::string &str) {
    if (str.size() < 4) return false;
    return str.substr(0,4) == "/usr";
}

class ExecutionTimer {
    struct timespec start_time;
public:
    ExecutionTimer() {
        clock_gettime(CLOCK_MONOTONIC, &start_time);
    }

    time_t getSeconds() {
        struct timespec now_time;
        clock_gettime(CLOCK_MONOTONIC, &now_time);
        return now_time.tv_sec - start_time.tv_sec;
    }
};

class DirectorySwitcher {
    char ori_dir[1000];
public:
    DirectorySwitcher(const std::string &target_dir);

    ~DirectorySwitcher();
};
