/*
    Nick Raffel
*/

#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <string>
#include <vector>
#include <sstream>
#include <fstream>
using namespace std;

vector<string> getTokenVector(string userLine)
{
    vector<string> tmpVec;

    stringstream streamObj = stringstream(userLine);
    string currentToken;
    while (streamObj >> currentToken)
        tmpVec.push_back(currentToken);
    return tmpVec;
}

int isPipe(string l)
{
    vector<string> tokens = getTokenVector(l);
    for (int i = 0; i < tokens.size(); ++i)
    {
        if (tokens[i] == "|")
        {
            return i;
        }
    }
    return 0;
}

void handlePipe(int loc, string l)
{
    vector<string> tokens = getTokenVector(l);
    vector<string> second(tokens.begin() + loc + 1, tokens.end());
    tokens.erase(tokens.begin() + loc, tokens.end());
    char *cmd[tokens.size() + 1];
    for (int i = 0; i < tokens.size(); ++i)
        cmd[i] = (char *)tokens[i].c_str();

    cmd[tokens.size()] = NULL;

    char *cmd2[second.size() + 1];
    for (int i = 0; i < second.size(); ++i)
        cmd2[i] = (char *)second[i].c_str();

    cmd2[second.size()] = NULL;

    int fds[2];

    if (pipe(fds) < 0)
    {
        cout << "error piping\n";
    }

    int originalSTDIN = dup(0);
    int originalSTDOUT = dup(1);

    pid_t pid = fork();
    if (pid < 0) // child creation faild
    {
        cout << "Child Creation Failed\n";
    }
    else if (pid == 0) // inside child
    {
        /* dup2 is redirecting the standard OUTPUT to be the fd[1] of the pipe */
        dup2(fds[1], 1);
        /* close the standard output device */
        close(fds[1]);
        /*closing the reading end*/
        close(fds[0]);

        if (execvp(cmd[0], cmd) < 0)
        {
            cout << "Command Failed\n";
        }
    }
    else if (pid > 0) // inside parent
    {
        wait(0);

        /*We change the stdin for the second child*/
        dup2(fds[0], 0);
        close(fds[0]); // closing the duplicated end
        /*closing the wrting end*/
        dup2(originalSTDOUT, 1);
        close(fds[1]);

        pid_t pid2 = fork();
        if (pid2 < 0) // child creation faild
        {
            cout << "Child Creation Failed\n";
        }
        else if (pid2 == 0) // inside child2
        {
            if (execvp(cmd2[0], cmd2) < 0)
            {
                cout << "Command Failed\n";
            }
        }
        else if (pid2 > 0)
        {
            wait(0); // wait for the second child
                     // we get things stdin and out back to normal

            dup2(originalSTDIN, 0);
            dup2(originalSTDOUT, 1);
            close(originalSTDIN);
            close(originalSTDOUT);
            fflush(stdout);
        }
    }
}

int isOutDir(string l)
{
    vector<string> tokens = getTokenVector(l);
    for (int i = 0; i < tokens.size(); ++i)
    {
        if (tokens[i] == ">")
        {
            return i;
        }
    }
    return 0;
}

void handleOutDir(int loc, string l)
{
    vector<string> tokens = getTokenVector(l);
    vector<string> second(tokens.begin() + loc + 1, tokens.end());
    tokens.erase(tokens.begin() + loc, tokens.end());
    char *cmd[tokens.size() + 1];
    for (int i = 0; i < tokens.size(); ++i)
        cmd[i] = (char *)tokens[i].c_str();

    cmd[tokens.size()] = NULL;

    char *cmd2[second.size() + 1];
    for (int i = 0; i < second.size(); ++i)
        cmd2[i] = (char *)second[i].c_str();

    cmd2[second.size()] = NULL;

    int fds[2];

    int originalSTDIN = dup(0);
    int originalSTDOUT = dup(1);

    pid_t pid;
    pid = fork();

    if (pid < 0)
    {
        cout << "Error: Cannot create a process" << endl;
        exit(2);
    }
    else if (pid == 0)
    {
        int outFd = open(cmd2[0], O_WRONLY | O_CREAT, 0666);
        dup2(outFd, 1);
        if (execvp(cmd[0], cmd) < 0)
        {
            cout << "Error: Cannot change the process exe image a process" << endl;
            exit(3);
        }
    }
    else if (pid > 0)
    {
        wait(0);

        fflush(stdout);
        dup2(fds[0], 0);
        close(fds[0]);
        dup2(originalSTDOUT, 1);
        close(fds[1]);
    }
}

int isInDir(string l)
{
    vector<string> tokens = getTokenVector(l);
    for (int i = 0; i < tokens.size(); ++i)
    {
        if (tokens[i] == "<")
        {
            return i;
        }
    }
    return 0;
}

void handleInDir(int loc, string l)
{
    vector<string> tokens = getTokenVector(l);
    vector<string> second(tokens.begin() + loc + 1, tokens.end());
    tokens.erase(tokens.begin() + loc, tokens.end());
    char *cmd[tokens.size() + 1];
    for (int i = 0; i < tokens.size(); ++i)
        cmd[i] = (char *)tokens[i].c_str();

    cmd[tokens.size()] = NULL;

    char *cmd2[second.size() + 1];
    for (int i = 0; i < second.size(); ++i)
        cmd2[i] = (char *)second[i].c_str();

    cmd2[second.size()] = NULL;

    int fds[2];

    if (pipe(fds) < 0)
    {
        cout << "Error: Cannot create a pipe" << endl;
        exit(2);
    }

    int originalSTDIN = dup(0);
    int originalSTDOUT = dup(1);

    pid_t pid;
    pid = fork();

    if (pid < 0)
    {
        cout << "Error: Cannot create a process" << endl;
        exit(3);
    }
    else if (pid == 0)
    {
        int inFd = open(cmd2[0], O_RDONLY);
        if (inFd < 0)
        {
            cout << "error opening file" << cmd2[0] << endl;
            exit(4);
        }
        if (dup2(inFd, 0) < 0)
        {
            cout << "redirection error\n";
            exit(5);
        }
        close(fds[0]);
        if (dup2(fds[1], 1) < 0)
        {
            cout << "redirection error\n";
            exit(6);
        }
        close(fds[1]);
        if (execvp(cmd[0], cmd) < 0)
        {
            cout << "Error: Cannot change the process exe image a process" << endl;
            exit(7);
        }
    }
    else if (pid > 0)
    {
        wait(0);

        // Restore original stdin
        dup2(originalSTDIN, 0);
        close(originalSTDIN);

        // Close pipe
        close(fds[1]);

        // Read from pipe and output to screen
        char buffer[4096];
        int count = read(fds[0], buffer, sizeof(buffer));
        write(STDOUT_FILENO, buffer, count);

        // Restore original stdout
        dup2(originalSTDOUT, 1);
        close(originalSTDOUT);
    }
}

void getHistory()
{
    ifstream histFile("history");
    string line;
    while(getline(histFile, line))
    {
        cout << line << endl;
    }
}

void processNormalCommand(string l)
{
    vector<string> tokens = getTokenVector(l);
    char *cmd[tokens.size() + 1];
    for (int i = 0; i < tokens.size(); ++i)
        cmd[i] = (char *)tokens[i].c_str();
    cmd[tokens.size()] = NULL;

    pid_t pid = fork();
    if (pid < 0)
    {
        cout << "Error: Cannot create a process" << endl;
        exit(2);
    }
    else if (pid == 0)
    {
        if (execvp(cmd[0], cmd) < 0)
        {
            cout << "Error: Cannot chnage the process exe image a process" << endl;
            exit(3);
        }
    }
    else if (pid > 0)
    {
        wait(0);
    }
}

int main()
{
    // variable to hold the command entered
    string line;

    // while true
    while (1)
    {
        // Prints out the working directory, and accepts in a command
        // whilst also creating a history
        cout << get_current_dir_name() << " ~:$ ";
        getline(cin, line);
        ofstream histFile("history", ios::app);
        histFile << line << endl;

        if (line == "pwd")
        {
            cout << endl << get_current_dir_name() << endl << endl;
        }
        else if (line == "history")
        {
            getHistory();
        }
        else if (line == "exit" || line == "quit")
        {
            break;
        }
        else if (isPipe(line) <= 0 && isOutDir(line) <= 0 && isInDir(line) <= 0)
        {
            processNormalCommand(line);
        }
        else if (isPipe(line) >= 1)
        {
            int loc = isPipe(line);
            handlePipe(loc, line);
        }
        else if (isOutDir(line))
        {
            int loc = isOutDir(line);
            handleOutDir(loc, line);
        }
        else if (isInDir(line))
        {
            int loc = isInDir(line);
            handleInDir(loc, line);
        }
    }
    return 0;
}
