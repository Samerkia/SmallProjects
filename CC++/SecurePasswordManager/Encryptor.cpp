#include "header.h"

string Encrypt(const string& s, string& code) {
    std::string encryptedMessage = s;
    for (size_t i = 0; i < s.length(); ++i) {
        encryptedMessage[i] = (s[i] + code[i % code.length()]) % 128; // Using ASCII range
    }
    return encryptedMessage;
}

string Decrypt(const string& s, string& code) {
    std::string decryptedMessage = s;
    for (size_t i = 0; i < s.length(); ++i) {
        decryptedMessage[i] = (s[i] - code[i % code.length()] + 128) % 128; // Using ASCII range
    }
    return decryptedMessage;
}
