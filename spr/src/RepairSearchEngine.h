#pragma once
#include "Utils.h"
#include "ASTUtils.h"
#include "BenchProgram.h"
#include <set>
#include <string>

namespace clang {
    class ASTContext;
    class Stmt;
    class Expr;
}

class FeatureParameter;

typedef std::vector<clang::Stmt*> StmtListTy;
typedef std::vector<clang::Expr*> ExprListTy;

class RepairSearchEngine {
    typedef std::set<unsigned long> TestCaseSetTy;
    BenchProgram &P;
    ErrorLocalizer *L;
    TestCaseSetTy negative_cases, positive_cases;
    std::set<std::string> bugged_files;
    bool use_bugged_files;
    bool naive;
    bool learning;
    FeatureParameter *FP;
    // The number of localizaiton result we consider
    unsigned long loc_limit;
    double GeoP;
    // We will just nuke the search space scores and use random search if this is set
    bool random;
    std::string summaryFile;

public:
    RepairSearchEngine(BenchProgram& P, ErrorLocalizer *L, bool naive, bool learning, FeatureParameter *FP)
        : P(P), negative_cases(P.getNegativeCaseSet()), positive_cases(P.getPositiveCaseSet()), naive(naive),
        learning(learning && !naive), FP(FP), GeoP(0.08), random(false), summaryFile("") {
        this->L = L;
        bugged_files.clear();
        use_bugged_files = false;
        loc_limit = 5000;
    }
    virtual ~RepairSearchEngine() { }
    void setBuggedFile(const std::set<std::string> &bugged_files) {
        use_bugged_files = true;
        this->bugged_files = bugged_files;
    }
    void setLocLimit(unsigned int loc_limit) {
        this->loc_limit = loc_limit;
    }
    void setGeoP(double GeoP) {
        this->GeoP = GeoP;
    }
    void setRandom(bool random) {
        this->random = random;
    }
    void setSummaryFile(const std::string &summaryFile) {
        this->summaryFile = summaryFile;
    }
    int run(const std::string &out_prefix, size_t try_at_least,
            bool print_fix_only, bool full_synthesis);
};
