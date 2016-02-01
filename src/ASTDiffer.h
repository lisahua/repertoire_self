#pragma once
#include <string>
#include <vector>
#include <memory>

namespace clang {
    class Decl;
    class Stmt;
    class ASTContext;
}

class ASTDiffer {
public:

    typedef enum {
        DeclKind = 0,
        StmtKind
    } NodeKindTy;

    typedef union {
        clang::Decl* decl;
        clang::Stmt* stmt;
    } ASTNodeUnionTy;

    enum DiffActionKindTy {
        ReplaceAction = 0,
        InsertAction,
        DeleteAction
    };

    struct DiffResultInternalEntry {
        unsigned int idx1, idx2;
        DiffActionKindTy kind;
        DiffResultInternalEntry(unsigned int idx1, unsigned int idx2, DiffActionKindTy kind):
            idx1(idx1), idx2(idx2), kind(kind) { }
    };

    struct DiffResultEntry {
        NodeKindTy NodeKind1, NodeKind2;
        ASTNodeUnionTy Node1, Node2;
        DiffActionKindTy DiffActionKind;
    };

    struct ASTDifferRecord {
        NodeKindTy NodeKind;
        ASTNodeUnionTy U;
        size_t end_index;
    };

    ASTDiffer(clang::ASTContext *ast1, clang::ASTContext *ast2);
    bool Diff();

    clang::ASTContext *getAST1() {
        return ast1;
    }

    clang::ASTContext *getAST2() {
        return ast2;
    }

    unsigned int GetDiffResult(std::vector<DiffResultEntry> &res);
    unsigned int GetDiffResultForStmt(std::vector<DiffResultEntry> &res);
    bool InSearchSpaceHelper();
    unsigned int GetDiffResultForCond(std::vector<DiffResultEntry> &res);
private:
    clang::ASTContext *ast1, *ast2;
    unsigned int diffDistance;
    std::vector<DiffResultInternalEntry> diffRes;
    std::vector<ASTDifferRecord> r1, r2;
    std::vector<unsigned int> parent_id1, parent_id2;
};

typedef ASTDiffer::DiffResultEntry DiffResultEntry;
