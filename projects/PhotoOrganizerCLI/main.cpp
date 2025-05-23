#include <iostream>
#include <filesystem>
#include <string>
#include <vector>
#include <sstream>
#include <iomanip>  // for padding numbers

using namespace std;
namespace fs = std::filesystem;

class PhotoRenamer {
public:
    PhotoRenamer(const string& folder, const string& name) {
        folderPath = folder;
        baseName = name;
    }

    void run() {
        getImages();
        renameImages();
    }

private:
    string folderPath;
    string baseName;
    vector<fs::directory_entry> imageFiles;

    bool isImageFile(const fs::path& filePath) {
        string ext = filePath.extension().string();
        for (char& c : ext) {
            c = tolower(c);
        }
        return ext == ".jpg" || ext == ".jpeg" || ext == ".png";
    }

    void getImages() {
        for (const fs::directory_entry& entry : fs::directory_iterator(folderPath)) {
            if (fs::is_regular_file(entry) && isImageFile(entry.path())) {
                imageFiles.push_back(entry);
            }
        }
    }

    void renameImages() {
        int total = imageFiles.size();
        int digits = 2;
        if (total >= 100) digits = 3;
        if (total >= 1000) digits = 4;
        if (total >= 10000) digits = 5;

        int count = 1;
        for (const fs::directory_entry& entry : imageFiles) {
            string ext = entry.path().extension().string();
            stringstream newName;
            newName << baseName << setfill('0') << setw(digits) << count << ext;
            fs::path newPath = entry.path().parent_path() / newName.str();

            try {
                fs::rename(entry.path(), newPath);
                cout << "Renamed: " << entry.path().filename() << " → " << newPath.filename() << endl;
            } catch (...) {
                cout << "Error renaming: " << entry.path().filename() << endl;
            }

            count++;
        }
    }
};

int main(int argc, char* argv[]) {
    if (argc < 2) {
        cout << "Usage: " << argv[0] << " <folder path>" << endl;
        return 1;
    }

    string folderPath = argv[1];
    if (!fs::exists(folderPath) || !fs::is_directory(folderPath)) {
        cout << "Invalid folder path." << endl;
        return 1;
    }

    string baseName;
    cout << "Enter Filename: ";
    cin >> baseName;

    PhotoRenamer renamer(folderPath, baseName);
    renamer.run();

    return 0;
}
