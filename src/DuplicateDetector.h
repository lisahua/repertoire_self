#pragma once
#include <string>
#include <vector>
#include <map>

typedef std::map<std::string, std::vector<std::string> > CodeSegTy;

class SourceContextManager;

class DuplicateDetector {
    CodeSegTy resSegs;
    CodeSegTy resPatches;
    std::map<size_t, std::string> lineToPatches;
    bool found;
public:
    // FIXME: This should get const
    DuplicateDetector(SourceContextManager &M, CodeSegTy &codeSegs,
            CodeSegTy &patches);

    CodeSegTy getNewCodeSegments() {
        return resSegs;
    }

    CodeSegTy getNewPatches() {
        return resPatches;
    }

    bool hasDuplicateCodeToPatch() {
        return found;
    }
};
