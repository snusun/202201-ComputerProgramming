

#include <iostream>

int HammingDistance(int x, int y) {
    // TODO: problem 1.2

    int a[32] = { 0, };
    int b[32] = { 0, };
    int i, j;

    for (i = 0; x > 0; i++) {
        a[i] = x % 2;
        x = x / 2;
    }

    for (j = 0; y > 0; j++) {
        b[j] = y % 2;
        y = y / 2;
    }

    int idx = i > j ? i : j;
    int result = 0;
    for (int k = 0; k < idx; k++) {
       if (a[k] != b[k]) result++;
    }

    return result;
}

