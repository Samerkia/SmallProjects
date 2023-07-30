#include "header.h"

string Encrypt(string s, string& code) {
    for (int i = 0; i < s.size(); i ++) {
        s[i]++;
    }
    return s;
}

string Decrypt(string s, string& code) {
    for (int i = 0; i < s.size(); i ++) {
        s[i]--;
    }
    return s;
}
