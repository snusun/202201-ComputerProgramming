#include <string>

bool IsPalindrome(std::string s) {
    // TODO: problem 1.1

    size_t strLen = s.length();

    for(int i=0; i<strLen/2; i++){
        if(s[i] != s[strLen-1-i]){
            return false;
        }
    }
    return true;
}