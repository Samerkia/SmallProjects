#ifndef MY_FUNCTIONS_H
#define MY_FUNCTIONS_H

using namespace std;

#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <cstdlib>
#include <ctime>
#include <limits>

// global static array for the random code Generator
static const char alphanum[] =
"0123456789"
"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
"abcdefghijklmnopqrstuvwxyz";

string Encrypt(string s, string& code);
string Decrypt(string s, string& code);

#endif