#pragma once
#include "ExprSynthesizer.h"
#include <string>

class SourceContextManager;

typedef std::map<size_t, clang::Expr*> ExprFillInfo;
typedef std::map<std::string, std::vector<std::string> > CodeSegTy;
typedef std::map<std::string, std::string> NewCodeMapTy;

NewCodeMapTy mergeCode(const CodeSegTy &codeSegs, const std::vector<CodeSegTy> &patches);

bool canMerge(const CodeSegTy &codeSegs, const CodeSegTy &patches);

NewCodeMapTy combineCode(const CodeSegTy &codeSegs, const CodeSegTy &patch);

class CodeRewriter {
    CodeSegTy resCodeSegs, resPatches;
public:
    CodeRewriter(SourceContextManager &M, const RepairCandidate &rc, ExprFillInfo *pefi);

    CodeSegTy getCodeSegments() {
        return resCodeSegs;
    }

    CodeSegTy getPatches() {
        return resPatches;
    }

    NewCodeMapTy getCodes() {
        return combineCode(resCodeSegs, resPatches);
    }
};

