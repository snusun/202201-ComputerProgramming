#include "header.h"

int* PascalTriangle(int N) {
    // TODO: problem 1.4

    //int* arr = (int*) malloc(sizeof(int) * N);
    //arr = {0,};

    int** arr = new int*[N];
    for(int i = 0; i < N; ++i)
        arr[i] = new int[N];


    for(int i = 1; i < 10; i++)
    {
        for(int j = 0; j <= i; j++)
        {
            if(j == 0 || j == i)
                arr[i][j] = 1;
            else
                arr[i][j] = arr[i - 1][j - 1] + arr[i - 1][j];
        }
    }

    for(int i=0; i<N; i++){
        printf("%d ", arr[N][i]);
    }
    printf("\n");

    //return *(arr+N);
    return NULL;
}

