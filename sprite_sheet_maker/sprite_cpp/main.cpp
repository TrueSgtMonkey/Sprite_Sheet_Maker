#include "lodepng.h"
#include "SpriteSheet.h"
#include <iostream>
#include <windows.h>
#include <fstream>
extern "C" {
    #include <unistd.h>
}

/*
int main () {
    const char* filename = "animation/dude0000.png";
    const char* filenameOutput = "dude0000.png";

    // these variables will be set by the decodeOneStep function
    std::vector<unsigned char> vec = decodeOneStep (filename, width, height);

    std::cout << vec.size() << " vs " << (width*height*4) << std::endl;
    
    encodeOneStep (filenameOutput, vec, width, height);
    return 0;
}
*/

unsigned constCharToUnsigned (const char* str);
int filesFromDirToText (const unsigned& numOfColumns, const std::string& path,
                        const std::string outputPath, const char* currentDirectory);

int main (int argc, const char* argv[]) {
    switch (argc) {
        case 1:
            std::cout << "need <path> <output_path> <num_of_columns>" << std::endl;
            return argc;
        case 2:
            std::cout << "need " << argv[1] << " <output_path> <num_of_columns>" << std::endl;
            return argc;
        case 3:
            std::cout << "need " << argv[1] << " " << argv[2] << " <num_of_columns>" << std::endl;
            return argc;
    }

    std::string path = argv[1];
    std::string outputPath = argv[2];
    char currentDirectory[512];
    size_t len = sizeof(currentDirectory);
    GetModuleFileName(NULL, currentDirectory, len);

    unsigned numOfColumns;
    numOfColumns = constCharToUnsigned (argv[3]);
    int filesCheck = filesFromDirToText (numOfColumns, path, outputPath, currentDirectory);
    if (filesCheck != 0) {
        return filesCheck;
    }

    SpriteSheet spriteSheet(numOfColumns);
    std::ifstream ifs;
    ifs.open((outputPath + "\\temp_dir.txt").c_str());
    std::string line;
    while (ifs.peek() != EOF) {
        // want to grab files even if they have spaces
        getline (ifs, line);
        std::cout << "\'" << line << "\'" << std::endl;
    }

    return 0;
}

int filesFromDirToText (const unsigned& numOfColumns, const std::string& path,
                        const std::string outputPath, const char* currentDirectory) {
    if (numOfColumns == 0) {
        std::cout << "<num_of_columns> argument must be greater than 0!" << std::endl;
        return 3;
    }

    chdir (path.c_str());
    system (("dir /b /a-d > " + outputPath + "\\temp_dir.txt").c_str());
    chdir (currentDirectory);

    return 0;
}

unsigned constCharToUnsigned (const char* str) {
    unsigned i   = 0;
    unsigned sum = 0;
    while (str[i] != '\0') {
        if (str[i] < '0' || str[i] > '9') {
            return 0;
        }
        sum = sum * 10 + (unsigned) str[i] - (unsigned) '0';
        i++;
    }
    std::cout << "sum=" << sum << "; str=\'" << str << "\'" << std::endl;
    return sum;
}