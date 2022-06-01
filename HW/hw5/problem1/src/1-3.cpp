#include "header.h"

void MergeArrays(int* arr1, int len1, int* arr2, int len2) {
    // TODO: problem 1.3
    // idx1, idx2, end
    // idx1보다 idx2가 크면 arr1의 모든 원소 하나씩 shift 하고 그 자리에 idx2 넣고 idx1++, idx2++, end++
    // 작거나 같으면 idx1++

    int idx1=0, idx2=0, end=len1-1;

    while(end!=len1+len2){
        if(arr1[len1+len2-1]!=0) break;
        if(arr1[idx1] < arr2[idx2] || arr1[idx1]==0){
            for(int i=end; i>=idx1; i--){
                arr1[i+1] = arr1[i];
            }
            arr1[idx1] = arr2[idx2];
//            for(int j=0; j<len1+len2; j++){
//                printf("%d ", arr1[j]);
//            }
//            printf("\n");
            idx1++; idx2++; end++;
        } else {
            idx1++;
        }
    }

//    printf("%d %d\n", idx2, len2+len1-1);
//    if(idx2!=len2+len1-1){
//        for(int j=idx1; j<end; j++){
//            arr1[j] = arr2[idx2++];
//        }
//    }

//    for(int j=0; j<len1+len2; j++){
//        printf("%d ", arr1[j]);
//    }
//    printf("\n");

}

