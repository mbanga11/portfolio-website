#include "Assistant.h"
#include <iostream>
#include <fstream>
#include <ctime>
#include <thread>
#include <chrono>
#include <unordered_set>
#include <vector>
#include <cstdlib>

using namespace std;

unordered_set<string> triggeredReminders;

string getCurrentTime() {
    time_t now = time(0);
    tm* ltm = localtime(&now);
    char timeString[10];
    strftime(timeString, sizeof(timeString), "%I:%M %p", ltm);
    return string(timeString);
}

void sendNotification(const string& line) {
    string safeLine = line;
    size_t quotePos;
    while ((quotePos = safeLine.find('"')) != string::npos) {
        safeLine.replace(quotePos, 1, "'");
    }

    string message = "display notification \"" + safeLine + "\" with title \"🔔 Reminder\"";
    string command = "osascript -e '" + message + "'";
    system(command.c_str());

    system("afplay /System/Library/Sounds/Pop.aiff");
}

void checkRemindersLoop(bool& running) {
    while (running) {
        string currentTime = getCurrentTime();

        ifstream in("reminders.txt");
        vector<string> remainingReminders;
        string line;

        if (in) {
            while (getline(in, line)) {
                bool isDaily = line.find("daily") != string::npos;
                bool alreadyTriggered = triggeredReminders.find(line + currentTime) != triggeredReminders.end();

                if (line.find(currentTime) != string::npos && !alreadyTriggered) {
                    cout << "\n\033[32m🔔 Reminder:\033[0m " << line << endl;
                    sendNotification(line);
                    triggeredReminders.insert(line + currentTime);

                    if (isDaily) {
                        remainingReminders.push_back(line);
                    }
                } else {
                    if (!line.empty()) remainingReminders.push_back(line);
                }
            }
            in.close();

            ofstream out("reminders.txt", ios::trunc);
            for (const string& reminder : remainingReminders) {
                out << reminder << endl;
            }
            out.close();
        }

        this_thread::sleep_for(chrono::seconds(30));
    }
}

void handleReminder(const string& input) {
    cout << "\033[32m[Detected] Reminder Command\033[0m\n";
    ofstream out("reminders.txt", ios::app);
    out << input << endl;
    out.close();
}

void handleJournal(const string& input) {
    cout << "\033[32m[Detected] Journal Entry\033[0m\n";
    size_t pos = input.find(":");
    if (pos != string::npos) {
        string entry = input.substr(pos + 1);
        ofstream out("journal.txt", ios::app);
        out << entry << endl;
        out.close();
    } else {
        cout << "\033[31m[Error]\033[0m Could not find ':' in journal input.\n";
    }
}

void handleAppLaunch(const string& input) {
    size_t pos = input.find("open");
    string appName = input.substr(pos + 5);
    while (!appName.empty() && appName[0] == ' ') {
        appName.erase(0, 1);
    }

    string command = "open -a \"" + appName + "\"";
    cout << "\033[32m[Launching]\033[0m: " << appName << endl;
    system(command.c_str());
}

void printHelp() {
    cout << "\n\033[34mAvailable Commands:\033[0m\n";
    cout << " - remind me to [task] at [HH:MM AM/PM]\n";
    cout << " - add to journal: [your entry here]\n";
    cout << " - open [app name]\n";
    cout << " - help (show this message)\n";
    cout << " - exit (quit assistant)\n";
}

void handleInput(const string& input) {
    if (input == "help") {
        printHelp();
    } else if (input.find("remind me") != string::npos) {
        handleReminder(input);
    } else if (input.find("journal") != string::npos) {
        handleJournal(input);
    } else if (input.find("open") != string::npos) {
        handleAppLaunch(input);
    } else {
        cout << "\033[34m[Info]\033[0m Unrecognized command.\n";
    }

    cout << "\033[34m[Current time is]:\033[0m " << getCurrentTime() << endl;
    cout << "You said: " << input << endl;
}
