#pragma once
#include <vector>
#include <string>
#include <map>
#include <set>

namespace clang {
    class Expr;
    class Stmt;
}

class SourceContextManager;
class RepairCandidate;

class FeatureVector: public std::vector<unsigned int> {
    bool mark;
public:
    const static unsigned int MAX_FEATURE;

    FeatureVector(): std::vector<unsigned int>(), mark(false) { }

    static FeatureVector extractFeature(SourceContextManager &M,
        const std::string &srcfile,
        const RepairCandidate &rc, clang::Expr* insVar);

    static std::string fidToString(unsigned int);

    void setMark() {
        mark = true;
    }

    bool getMark() const {
        return mark;
    }

    template<typename T>
    void dump(T& out) {
        out << "Total vec size: " << size() << "\n";
        for (size_t i = 0; i < size(); i++)
            out << fidToString(at(i)) << "\n";
    }

    template<typename T>
    friend T& operator<<(T& out, const FeatureVector &fv);

    template<typename T>
    friend T& operator>>(T& in, FeatureVector &fv);
};

template<typename T>
T& operator<<(T& out, const FeatureVector &fv) {
    out << fv.size() << " ";
    for (size_t i = 0; i < fv.size(); i++)
        out << fv[i] << " ";
    out << fv.mark << "\n";
    return out;
}

template<typename T>
T& operator>>(T& in, FeatureVector &fv) {
    fv.clear();
    size_t n;
    in >> n;
    for (size_t i = 0; i < n; i++) {
        unsigned int v;
        in >> v;
        fv.push_back(v);
    }
    in >> fv.mark;
    return in;
}

typedef std::set<unsigned int> FeatureSetTy;
typedef std::map<std::string, FeatureSetTy> ValueToFeatureMapTy;
typedef std::set<std::pair<unsigned int, unsigned int> > FeatureProductSetTy;

class FeatureExtractor {
    // This is a hack for the value resolve
    std::map<std::string, clang::Expr*> valueExprInfo;
    std::map<clang::Stmt*, ValueToFeatureMapTy> cache;
public:
    FeatureExtractor() : valueExprInfo(), cache() { }

    FeatureVector extractFeature(SourceContextManager &M,
        const RepairCandidate &rc, clang::Expr* insVar);
};
