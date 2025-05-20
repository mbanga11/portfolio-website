#include <iostream>
#include <fstream>
#include <string>
using namespace std;

// XOR encryption/decryption function (same as used for encryption)
string xorEncryptDecrypt(const string& data, const string& key) {
    string result = data;
    for (size_t i = 0; i < data.size(); ++i) {
        result[i] = data[i] ^ key[i % key.size()];
    }
    return result;
}

int main() {
    string masterPassword;
    cout << "Enter your master password to decrypt the vault: ";
    getline(cin, masterPassword);

    ifstream inFile("vault.txt");
    if (!inFile.is_open()) {
        cerr << "Failed to open vault.txt!" << endl;
        return 1;
    }

    cout << "\n--- Decrypted Vault Contents ---\n" << endl;

    string line;
    while (getline(inFile, line)) {
        string decryptedLine = xorEncryptDecrypt(line, masterPassword);
        cout << decryptedLine << endl;  // Should now print clean multi-line blocks
    }

    inFile.close();
    return 0;
}
