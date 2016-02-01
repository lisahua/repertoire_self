#pragma once
#include "RepairCandidateGenerator.h"
#include <queue>
#include <map>
#include <algorithm>

namespace clang {
    class Expr;
}

class FeatureParameter;

typedef std::map<size_t, clang::Expr*> ExprFillInfo;
typedef std::map<std::string, std::string> NewCodeMapTy;
typedef std::vector<std::pair<NewCodeMapTy, double> > ExprSynthesizerResultTy;

class ExprSynthesizer {
    BenchProgram &P;
    SourceContextManager &M;
    size_t tested_cnt;
    RepairCandidateQueue &q;
    bool naive;
    bool learning;
    FeatureParameter *FP;
public:
    ExprSynthesizer(BenchProgram &P, SourceContextManager &M,
            RepairCandidateQueue &q,
            bool naive, bool learning, FeatureParameter *FP):
        P(P), M(M), tested_cnt(0), q(q), naive(naive), learning(learning && !naive), FP(FP) { }

    bool workUntil(size_t candidate_limit,
            size_t time_limit, ExprSynthesizerResultTy &res,
            bool full_synthesis, bool quit_with_any);

    size_t getTestedCandidateNumber() {
        return tested_cnt;
    }
};
