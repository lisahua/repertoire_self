#pragma once
#include <map>
#include <set>
#include <string>
#include <vector>

namespace clang {
    class ASTContext;
    class Stmt;
    class Expr;
    class VarDecl;
    class FunctionDecl;
    class EnumConstantDecl;
    class EnumDecl;
}

class GlobalAnalyzer {
    clang::ASTContext &C;
    std::string filename;
    std::set<clang::VarDecl*> GlobalVarDecls;
    std::set<clang::FunctionDecl*> FuncDecls;
    std::map<clang::EnumConstantDecl*, clang::EnumDecl*> EnumMap;
    std::set<clang::Expr*> CandidateExprs;
    std::set<clang::Stmt*> CandidateMacroExps;
    std::set<clang::Stmt*> CandidateIfStmts;

public:

    typedef std::vector<clang::Expr*> ExprListTy;

    GlobalAnalyzer(clang::ASTContext &C, const std::string &filename);

    const std::set<clang::FunctionDecl*> & getFuncDecls() {
        return FuncDecls;
    }

    const std::set<clang::VarDecl*> & getGlobalVarDecls() {
        return GlobalVarDecls;
    }

    const std::set<clang::Expr*> & getCandidateExprs() {
        return CandidateExprs;
    }

    const std::set<clang::Stmt*> & getCandidateMacroExps() {
        return CandidateMacroExps;
    }

    const std::set<clang::Stmt*> & getCandidateIfStmts() {
        return CandidateIfStmts;
    }

    ExprListTy getCandidateEnumConstant(clang::EnumConstantDecl *ECD);

    void dump(bool pretty = true);
};
