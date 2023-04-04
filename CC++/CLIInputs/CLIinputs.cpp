/*
    @copyright Nick Raffel
    I made this mostly to just show 
*/

#include <unistd.h>
#include <iostream>
#include <cstdlib>
#include <string.h>
#include <getopt.h>

using namespace std;

string ProgramHelp()
{
    return "Usage:      :   ./CLIinputs [-c <count>] [-t <text>]\n\n" 
           "Options:\n"
           "-h or --help:         :   Optional: Shows options and how to use them!\n\n"

           "-c or --count:         :   Optional: Amount of times to print\n"
           "                ./CLIinput -c 5\n\n"

           "-t:         :   Optional: The text to print\n"
           "                ./CLIinput -t \"Hello World!\"\n"
           "Combined    :   ./CLIinput -t \"Hello World!\" -c 5";
}

void getArguments(int argc, char** argv)
{
    int c;
    int count = 1; // default value
    string text = "Hello"; // default value

    const char* const short_opts = "hc:t:";
    const option long_opts[] = {
        {"help", no_argument, nullptr, 'h'},
        {"count", required_argument, nullptr, 'c'},
        {"text", required_argument, nullptr, 't'},
        {0,0,0,0}
    };

    // parse command line arguments using getopt
    while ((c = getopt_long(argc, argv, short_opts, long_opts, nullptr)) != -1) {
        switch (c) {
        case 'c':
            count = atoi(optarg);
            break;
        case 't':
            text = optarg;
            break;
        case 'h':
        default:
            cout << ProgramHelp();
            break;
        }
    }

    // print the given string count times
    for (int i = 0; i < count; i++) {
        cout << text << endl;
    }

}

int main(int argc, char* argv[]) {

    getArguments(argc, argv);

    return 0;
}
