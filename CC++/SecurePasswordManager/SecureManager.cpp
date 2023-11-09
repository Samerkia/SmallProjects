// Nick Raffel 
//

#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <cstdlib>
#include <ctime>
#include <limits>
#include "header.h"

using namespace std;

string getCode() {
    string l;
    fstream codeFile;
    codeFile.open("code.txt");
    getline(codeFile, l);
    return l;
}

string genCode() {
    srand(time(nullptr));
    string c;
    for (int i = 0; i < 10; i++) {
        c += alphanum[rand() % sizeof(alphanum) - 1];
    }
    return c;
}

// Function for reading the Password file
 vector<string> readPasswordsFile() {
    fstream file;

    // Opens the file, if it doesn't exist it creates it
    file.open("passwords.txt", fstream::in | fstream::out | fstream::app);

    string line;
    // string vector to hold all of the saved passwords
    vector<string> lines;

    // if the file exists, push all saved passwords to the string vector
    if (file) {
        while (getline(file, line)) {
            lines.push_back(line);
        }
        file.close();
    }
    return lines;
}

// prints out the passwords in the file (they are stored encrypted)
void printEncryptedPasswords() {
    vector<string> lines = readPasswordsFile();
    for (const auto a : lines)
        cout << a << "\n";
}

// ifthe user enters in the correct secret cod
// this function is called to decrypt all the passwords
void printPlainPasswords(string& code) {
    vector<string> lines = readPasswordsFile();
    for (const auto a: lines)
        cout << Decrypt(a, code) << "\n";
}

// encrypts and adds the password to the password file
void addPass(string& newPass, string& code) {
    fstream file;
    file.open("passwords.txt", fstream::in | fstream::out | fstream::app);
    file << Encrypt(newPass, code) << "\n";
    file.close();
}

// checks if the code entered in is correct
// if so, prints decrypted passwords
// else tells them they're wrong
void checkCode(string& code) {
    string l;
    fstream codeFile;
    codeFile.open("code.txt");
    while (getline(codeFile, l)) { 
        if (code == l)
            printPlainPasswords(code);
        else
            cout << "INVALID Code! Starting Over...\n[Enter Origingal Options 1-4]\n";
    }
}

// checks if the file that stores the code exists
void checkCodeFile(string& code) {
    fstream codeFile;
    codeFile.open("code.txt");
    // checks if the file that stores the code exists or not
    if (!codeFile) {
        // if it doesn't exsist, it creates the file and generates a secret code to store it
        codeFile.open("code.txt", fstream::in | fstream::out | fstream::app);
        code = genCode();
        
        // this also only will get printed and tells the user their secret code ONCE upon file creation
        codeFile << code;
        cout << "Code: " << code << "\nUse this code for you decryptions!DON'T LOSE IT!!\n";
        codeFile.close();
    }
    code = getCode();
    codeFile.close();
}

int main()
{   
    string pass;
    string code;    
    int option;

    checkCodeFile(code);
    cout << endl <<
        "| What would you like to do?     |\n" <<
        "| 1: List Passwords (Encrypted)  |\n" <<
        "| 2: List Passwords (Plain Text) |\n" <<
        "| 3: Add Password                |\n" <<
        "| 4: Quit                        |\n";
    
    while (true) {
        cout << "-> ";
        if (!(cin >> option)) {
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            cout << "INVALID INPUT! Try Again!\n\n";
            continue;
        }

        switch (option)
        {
        case 1:
            printEncryptedPasswords();
            break;
        case 2:
            cout << "[!] Enter Code -> ";
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            getline(cin, code);
            checkCode(code);
            break;
        case 3:
            cout << "[+] Enter New Password -> ";
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            getline(cin, pass);
            addPass(pass, code);
            break;
        case 4:
            cout << "\nQUITTING!\n";
            return false;
        default:
            cout << "INVALID! Try Again!\n\n";
            break;
        }
    }
}
