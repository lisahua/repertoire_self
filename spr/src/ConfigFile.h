#pragma once
#include "Utils.h"
#include <fstream>
#include <map>

class ConfigFile {
    std::map<std::string, std::string> conf_map;
public:
    ConfigFile(const std::string &filename) {
        std::ifstream fin(filename.c_str(), std::ifstream::in);
        if (fin.is_open()) {
            conf_map.clear();
            while (!fin.eof()) {
                std::string line;
                std::getline(fin, line);
                line = stripLine(line);
                size_t idx = line.find('=');
                if (idx == std::string::npos)
                    break;
                std::string s1 = line.substr(0, idx);
                std::string s2 = line.substr(idx + 1);
                conf_map[s1] = s2;
            }
            fin.close();
        }
        else
            fprintf(stderr, "Unable to open configure file %s\n", filename.c_str());
    }

    ~ConfigFile() {}

    std::string getStr(const std::string &key) {
        std::map<std::string, std::string>::iterator it = conf_map.find(key);
        if (it != conf_map.end())
            return it->second;
        else
            return "";
    }

    bool hasValue(const std::string &key) {
        return conf_map.count(key);
    }
};
