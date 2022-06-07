#include "header.h"

void MergeArrays(int* arr1, int len1, int* arr2, int len2) {
    // TODO: problem 1.3

    int* res = new int[len1+len2];

    int i = 0, j = 0, k = 0;
    while (i < len1) {
        res[k] = arr1[i];
        i += 1;
        k += 1;
    }
    while (j < len2) {
        res[k] = arr2[j];
        j += 1;
        k += 1;
    }

    std::sort(res, res + len1 + len2, std::greater<>());

    for(int j=0; j<len1+len2; j++){
        arr1[j] = res[j];
    }
}

