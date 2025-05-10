#include <iostream>
#include <thread>
#include "Assistant.h"

using namespace std;

bool running = true;

int main() {
    thread reminderThread(checkRemindersLoop, ref(running));

    cout << "\033[34mSmart Assistant (type 'exit' to quit)\033[0m\n";

    string input;
    while (true) {
        getline(cin, input);

        if (input == "exit") {
            cout << "\033[34mGoodbye!\033[0m\n";
            break;
        }

        handleInput(input);
    }

    running = false;
    reminderThread.join();

    return 0;
}
