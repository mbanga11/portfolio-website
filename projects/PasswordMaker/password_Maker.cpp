#include <iostream>
#include <string>
#include <fstream>
#include <random>
#include <algorithm>  // for shuffle()

using namespace std;

// Function to generate a random password with required complexity
string PasswordMaker(int length) {
    // Ensure minimum length to fit all character types
    if (length < 4) return "";

    // Character categories
    char digits[] = "0123456789";
    char uppers[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    char lowers[] = "abcdefghijklmnopqrstuvwxyz";
    char specials[] = "!@#$%^&*()_-+[]{}?/";

    string result;

    // Random number generator setup
    random_device rd;
    mt19937 gen(rd());

    // Distributions to pick random characters from each set
    uniform_int_distribution<> digit_dist(0, sizeof(digits) - 2);
    uniform_int_distribution<> upper_dist(0, sizeof(uppers) - 2);
    uniform_int_distribution<> lower_dist(0, sizeof(lowers) - 2);
    uniform_int_distribution<> special_dist(0, sizeof(specials) - 2);

    // Ensure the password includes at least one of each type
    result += digits[digit_dist(gen)];
    result += uppers[upper_dist(gen)];
    result += lowers[lower_dist(gen)];
    result += specials[special_dist(gen)];

    // Combine all characters into one set
    string allChars = string(digits) + uppers + lowers + specials;
    uniform_int_distribution<> all_dist(0, allChars.size() - 1);

    // Fill the remaining characters
    for (int i = 4; i < length; i++) {
        result += allChars[all_dist(gen)];
    }

    // Shuffle to prevent predictable ordering (e.g. digit always first)
    shuffle(result.begin(), result.end(), gen);

    return result;
}

// XOR encryption/decryption function using a key (symmetric)
string xorEncryptDecrypt(const string& data, const string& key) {
    string result = data;
    for (size_t i = 0; i < data.size(); ++i) {
        result[i] = data[i] ^ key[i % key.size()];  // Cycle through key
    }
    return result;
}

int main() {
    int p2; 
    string reason;
    string email_user; 

    // Ask user for password length
    cout << "What would you like the length of your password to be?" << endl;
    cin >> p2; 

    // Ensure the length is secure
    while (p2 < 8 || p2 > 128) {
        cout << "Please enter a password length between 8 and 128: ";
        cin >> p2;
    }

    cin.ignore(); // Clear newline from input buffer

    // Ask what the password is for
    cout << "What is this password for?" << endl;
    getline(cin, reason);

    // Ask for email or username
    cout << "What is the email or username for this?" << endl;
    getline(cin, email_user);

    // Generate secure password
    string myPassword = PasswordMaker(p2);
    cout << "Generated Password: " << myPassword << endl;

    // Format the data to be saved
    string combined = "Purpose: " + reason +
                      "\nUsername: " + email_user +
                      "\nPassword: " + myPassword +
                      "\n----------------------------\n";

    // Prompt for master password to encrypt the vault entry
    string masterPassword;
    cout << "Enter a master password to encrypt your vault entry: ";
    getline(cin, masterPassword);

    // Encrypt the combined data using XOR cipher
    string encrypted = xorEncryptDecrypt(combined, masterPassword);

    // Save encrypted data to vault.txt
    ofstream outFile("vault.txt", ios::app);
    if (outFile.is_open()) {
        outFile << encrypted << "\n";
        outFile.close();
        cout << "Encrypted and saved to vault.txt!" << endl;
    } else {
        cerr << "Failed to open file!" << endl;
    }

    return 0;
}
