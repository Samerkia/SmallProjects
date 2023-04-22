// Nick Raffel 
//

#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <cstdlib>
#include <ctime>

using namespace std;

// global static array for the random code Generator
static const char alphanum[] =
"0123456789"
"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
"abcdefghijklmnopqrstuvwxyz";

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

 // super basic encryption 
 // could made the code the encryption key
string encrypt(string s) {

    for (int i = 0; i < s.size(); i++)
    {
        s[i]++;
    }

    return s;
}

// super basic decryption
// could made the code the decryption key
string decrypt(string s) {

    for (int i = 0; i < s.size(); i++)
    {
        s[i]--;
    }

    return s;
}

// prints out the passwords in the file (they are stored encrypted)
void encryptedPasswords() {
    vector<string> lines = readPasswordsFile();
    for (const auto a : lines)
        cout << a << "\n";
}

// ifthe user enters in the correct secret cod
// this function is called to decrypt all the passwords
void plainPasswords() {
    vector<string> lines = readPasswordsFile();
    for (const auto a: lines)
        cout << decrypt(a) << "\n";
}

// encrypts and adds the password to the password file
void addPass(string& newPass) {
    fstream file;
    file.open("passwords.txt", fstream::in | fstream::out | fstream::app);
    file << encrypt(newPass) << "\n";
    file.close();
}

// checks if the code entered in is correct
// if so, prints decrypted passwords
// else tells them they're wrong
void checkCode(string& code) {
    string l;
    fstream codeFile;
    codeFile.open("code.txt");
    while (getline(codeFile, l))
        if (code == l)
            plainPasswords();
        else
            cout << "INVALID Code! Starting Over...\n[Enter Origingal Options 1-4]\n";
}

// checks if the file that stores the code exists
void checkCodeFile() {
    fstream codeFile;
    codeFile.open("code.txt");
    // checks if the file that stores the code exists or not
    if (!codeFile) {
        // if it doesn't exsist, it creates the file and generates a secret code to store it
        codeFile.open("code.txt", fstream::in | fstream::out | fstream::app);
        string code;
        srand(time(nullptr));
        for (int i = 0; i < 10; i++) {
            code += alphanum[rand() % sizeof(alphanum) - 1];
        }

        // this also only will get printed and tells the user their secret code ONCE upon file creation
        codeFile << code;
        cout << "Code: " << code << "\nUse this code for you decryptions!DON'T LOSE IT!!\n";
        codeFile.close();
    }
    codeFile.close();
}

int main()
{   
    checkCodeFile();
    cout << endl <<
        "| What would you like to do?     |\n" <<
        "| 1: List Passwords (Encrypted)  |\n" <<
        "| 2: List Passwords (Plain Text) |\n" <<
        "| 3: Add Password                |\n" <<
        "| 4: Quit                        |\n";

    string pass;
    string code;
    int option;
    while (true) {
        cout << "-> ";
        if (!(cin >> option)) {
            cin.clear();
            cin.ignore( numeric_limits< streamsize>::max(), '\n');
            cout << "INVALID INPUT! Try Again!\n\n";
            continue;
        }
        switch (option)
        {
        case 1:
            encryptedPasswords();
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
            addPass(pass);
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
