#include <iostream>
#include <cstdlib>
#include <ctime>
#include <string>
#include <fstream>


using namespace std;

// Generates a random password with specified length
string PasswordMaker(string s , int length) {
    char Karr[] = {
        // Numbers
        '0','1','2','3','4','5','6','7','8','9',
        // Uppercase
        'A','B','C','D','E','F','G','H','I','J',
        'K','L','M','N','O','P','Q','R','S','T',
        'U','V','W','X','Y','Z',
        // Lowercase
        'a','b','c','d','e','f','g','h','i','j',
        'k','l','m','n','o','p','q','r','s','t',
        'u','v','w','x','y','z',
        // Safe special characters
        '!','@','#','$','%','^','&','*','(',')',
        '_','-','+','[',']','{','}','?','/'
    };

    int size = sizeof(Karr) / sizeof(Karr[0]);
    
    string result = "";

    for (int i = 0; i < length; i++) {
        int randomIndex = rand() % size;
        result += Karr[randomIndex];
    }

    return result;
}

int main() {
    srand(time(0));  // Seed once at the start of the program
    int p2 ; 
    string reason;
    string email_user; 

    cout<<"What would you like the length of your password to be?"<<endl;
    cin >> p2; 



    string myPassword = PasswordMaker(" ", p2);
    cout << "Generated Password: " << myPassword << endl;

    cout<<"What is this password for?"<<endl;
    cin>> reason; 


    cout<<"What is the email or username for this?"<<endl;
    cin>>email_user;

    ofstream outFile("vault.txt", ios::app);
    if (outFile.is_open()) {
        outFile <<  reason << " - " << email_user << " - " << myPassword << "\n";
        outFile.close();
        cout << " Saved to vault.txt!" << endl;
    } else {
        cerr << " Failed to open file!" << endl;
    }
    return 0;

}
