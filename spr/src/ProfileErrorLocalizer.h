#pragma once
#include "Utils.h"
#include "ErrorLocalizer.h"
#include <string>
#include <vector>
#include <set>
#include <map>

#define INDEX_FILE "/tmp/__index.loc"

class BenchProgram;

struct ProfileInfoTy {
    long long execution_cnt;
    long long beforeend_cnt;
    std::string pid;
};

class ProfileErrorLocalizer : public ErrorLocalizer {
    typedef std::set<unsigned long> TestCaseSetTy;

    BenchProgram &P;

    TestCaseSetTy negative_cases, positive_cases;

    class ResRecordTy {
    public:
        long long primeScore;
        long long secondScore;
        SourcePositionTy loc;
        std::string pid;
    };

    std::vector<ResRecordTy> candidateResults;

    LocationIndex *LI;

    void clearProfileResult();

    std::map<SourcePositionTy, ProfileInfoTy> parseProfileResult();

public:
    ProfileErrorLocalizer(BenchProgram &P, const std::string &res_file);

    ProfileErrorLocalizer(BenchProgram &P, const std::set<std::string> &bugged_file,
            bool skip_build);

    virtual std::vector<SourcePositionTy> getCandidateLocations();

    virtual void printResult(const std::string &outfile);

    virtual ~ProfileErrorLocalizer() { }
};
