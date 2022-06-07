#include "header.h"

int* PascalTriangle(int N) {
    // TODO: problem 1.4

    static int b = 1;
    int* arr1 = &b;
    if(N==1){
        return arr1;
    }

    int** arr = new int*[N];
    for(int i = 0; i < N; ++i){
        arr[i] = new int[N];
        memset(arr[i], 0, sizeof(int)*N);
    }

    for(int i = 1; i < N; i++)
    {
        for(int j = 0; j <= i; j++)
        {
            if(j == 0 || j == i)
                arr[i][j] = 1;
            else
                arr[i][j] = arr[i - 1][j - 1] + arr[i - 1][j];
        }
    }

    return arr[N-1];
}

