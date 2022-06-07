#include "header.h"
#define STB_IMAGE_IMPLEMENTATION
#define STB_IMAGE_WRITE_IMPLEMENTATION
#include "stb_image.h"
#include "stb_image_write.h"

template <typename T>
void CheckArray(const T* a, const std::vector<T> b, int N) {
    if (!a || b.size() != N) {
        std::cout << "Failed" << std::endl;
        return;
    }
    for (std::size_t i = 0; i < N; ++i) {
        if (a[i] != b.at(i)) {
            std::cout << "Failed" << std::endl;
            return;
        }
    }
    std::cout << "Passed" << std::endl;
}

template <typename T>
void CheckValue(const T a, const T b) {
    if (a == b) {
        std::cout << "Passed" << std::endl;
    } else {
        std::cout << "Failed" << std::endl;
    }
}

int main() {
    // Test more with your own test cases

    // 1.1
    std::cout << "1-1" << std::endl;
    CheckValue(IsPalindrome(""), true);
    CheckValue(IsPalindrome("ab"), false);
    CheckValue(IsPalindrome("aabb"), false);
    CheckValue(IsPalindrome("12321a12321"), true);

    // 1.2
    std::cout << "1-2" << std::endl;
    CheckValue(HammingDistance(1,4), 2);
    CheckValue(HammingDistance(3,1), 1);
    CheckValue(HammingDistance(33,15), 4);

    // 1.3
    std::cout << "1-3" << std::endl;
    //int arr1[] ={1,3,5,0,0};
    //int arr2[] = {3,5};
    int t1_arr1[] ={5,3,1,0,0};
    int t1_arr2[] = {5,3};
    int t2_arr1[] ={3,2,1,0,0,0};
    int t2_arr2[] = {6,5, 4};
    int t3_arr1[] ={16,15,10,-3,0,0,0};
    int t3_arr2[] = {50,-5,-99};
    MergeArrays(t1_arr1, 3, t1_arr2, 2);
    CheckArray(t1_arr1, {5,5,3,3,1}, sizeof(t1_arr1)/sizeof(int));
    MergeArrays(t2_arr1, 3, t2_arr2, 3);
    CheckArray(t2_arr1, {6,5,4,3,2,1}, sizeof(t2_arr1)/sizeof(int));
    MergeArrays(t3_arr1, 4, t3_arr2, 3);
    CheckArray(t3_arr1, {50,16,15,10,-3,-5,-99}, sizeof(t3_arr1)/sizeof(int));

    // 1.4
    std::cout << "1-4" << std::endl;
    int* res = PascalTriangle(4);
    CheckArray(res, {1,3,3,1}, 4);
    res = PascalTriangle(5);
    CheckArray(res, {1,4,6,4,1}, 5);
    res = PascalTriangle(11);
    CheckArray(res, {1,10,45,120,210,252,210,120,45,10,1}, 11);
    delete[] res;

    // 1.5
    std::cout << "1-5" << std::endl;
    int width, height, channels;
    uint8_t* input_image = stbi_load("test/input7.png", &width, &height, &channels, 0);
    if(input_image == NULL) {
        printf("Error in loading the image\n");
        exit(1);
    }
    unsigned char *output_image = (unsigned char*)malloc(width*height*1);
    Labeling(input_image, output_image, width, height);
    stbi_write_png("test/output.png", width, height, 1, output_image, width);
    stbi_image_free(input_image);
    stbi_image_free(output_image);

    return 0;
}

//#include "header.h"
//#define STB_IMAGE_IMPLEMENTATION
//#define STB_IMAGE_WRITE_IMPLEMENTATION
//#include "stb_image.h"
//#include "stb_image_write.h"
//
//template <typename T>
//void CheckArray(const T* a, const std::vector<T> b, int N) {
//    if (!a || b.size() != N) {
//        std::cout << "Failed" << std::endl;
//        return;
//    }
//    for (std::size_t i = 0; i < N; ++i) {
//        if (a[i] != b.at(i)) {
//            std::cout << "Failed" << std::endl;
//            return;
//        }
//    }
//    std::cout << "Passed" << std::endl;
//}
//
//template <typename T>
//void CheckValue(const T a, const T b) {
//    if (a == b) {
//        std::cout << "Passed" << std::endl;
//    } else {
//        std::cout << "Failed" << std::endl;
//    }
//}
//
//bool simple_test(unsigned char *image, int width, int height) {
//    int x_delta[8] = {-1, 0, 1, -1, 1, -1, 0, 1};
//    int y_delta[8] = {-1, -1, -1, 0, 0, 1, 1, 1};
//    bool check[5] = {false, false, false ,false, false};
//    for (int i = 0; i < height; i++) {
//        for (int j = 0; j < width; j++) {
//            int v = image[i * width + j];
//            if (v % 50 != 0 && v != 255) return false;
//            if (v != 255) {
//                check[v / 50] = true;
//                for (int t = 0; t < 8; t++) {
//                    int xx = i + x_delta[t], yy = j + y_delta[t];
//                    if (xx < 0 || yy < 0 || xx >= height || yy >= width) continue;
//                    if (image[xx * width + yy] != v && image[xx * width + yy] != 255) return false;
//                }
//            }
//        }
//    }
//    int cnt = 0; for (int i = 0; i < 5; i++) if (check[i]) cnt++;
//    printf("%d objects detected! ", cnt);
//    return true;
//}
//
//int main() {
//    // Test more with your own test cases
//
//    // 1.1
//    std::cout << "1-1" << std::endl;
//    CheckValue(IsPalindrome(""), true);
//    CheckValue(IsPalindrome(" "), true);
//    CheckValue(IsPalindrome("   "), true);
//    CheckValue(IsPalindrome("ab"), false);
//    CheckValue(IsPalindrome("aabb"), false);
//    CheckValue(IsPalindrome("Z"), true);
//    CheckValue(IsPalindrome("abba"), true);
//    CheckValue(IsPalindrome("abbA"), false);
//    CheckValue(IsPalindrome("12321a12321"), true);
//    CheckValue(IsPalindrome("12321a 12321"), false);
//    CheckValue(IsPalindrome("12345 a 54321"), true);
//    CheckValue(IsPalindrome("12345 a 54421"), false);
//    CheckValue(IsPalindrome("12345 a  54321"), false);
//
//    // 1.2
//    std::cout << "1-2" << std::endl;
//    CheckValue(HammingDistance(1,4), 2);
//    CheckValue(HammingDistance(3,1), 1);
//    CheckValue(HammingDistance(33,15), 4);
//    CheckValue(HammingDistance(2147483647,1070595901), 6);
//    CheckValue(HammingDistance(2147483647,19), 28);
//    CheckValue(HammingDistance(2147483647,16), 30);
//    CheckValue(HammingDistance(2147483647,0), 31);
//
//    // 1.3
//    std::cout << "1-3" << std::endl;
//    int t1_arr1[] ={5,3,1,0,0};
//    int t1_arr2[] = {5,3};
//    int t2_arr1[] ={3,2,1,0,0,0};
//    int t2_arr2[] = {6,5, 4};
//    int t3_arr1[] ={16,15,10,-3,0,0,0};
//    int t3_arr2[] = {50,-5,-99};
//    int t4_arr1[] ={2147483647, 0, -99999,-2147483648,0,0,0};
//    int t4_arr2[] = {987654321, 136, -2147483648};
//    MergeArrays(t1_arr1, 3, t1_arr2, 2);
//    CheckArray(t1_arr1, {5,5,3,3,1}, sizeof(t1_arr1)/sizeof(int));
//    MergeArrays(t2_arr1, 3, t2_arr2, 3);
//    CheckArray(t2_arr1, {6,5,4,3,2,1}, sizeof(t2_arr1)/sizeof(int));
//    MergeArrays(t3_arr1, 4, t3_arr2, 3);
//    CheckArray(t3_arr1, {50,16,15,10,-3,-5,-99}, sizeof(t3_arr1)/sizeof(int));
//    MergeArrays(t4_arr1, 4, t4_arr2, 3);
//    CheckArray(t4_arr1, {2147483647,987654321,136,0,-99999,-2147483648,-2147483648}, sizeof(t4_arr1)/sizeof(int));
//
//    // 1.4
//    std::cout << "1-4" << std::endl;
//    int* res = PascalTriangle(4);
//    CheckArray(res, {1,3,3,1}, 4);
//    res = PascalTriangle(5);
//    CheckArray(res, {1,4,6,4,1}, 5);
//    res = PascalTriangle(1);
//    CheckArray(res, {1}, 1);
//    res = PascalTriangle(2);
//    CheckArray(res, {1,1}, 2);
//    res = PascalTriangle(3);
//    CheckArray(res, {1,2,1}, 3);
//    res = PascalTriangle(19);
//    CheckArray(res, {1,18,153,816,3060,8568,18564,31824,43758,48620,43758,31824,18564,8568,3060,816,153,18,1},19);
//    res = PascalTriangle(20);
//    CheckArray(res, {1,19,171,969,3876,11628,27132,50388,75582,92378,92378,75582,50388,27132,11628,3876,969,171,19,1},20);
//    delete[] res;
//
//    // 1.5
//    std::cout << "1-5" << std::endl;
//    char* inputFileName[] = {"test/input1.png", "test/input3.png","test/input4.png",
//                             "test/input5.png","test/input6.png","test/input7.png"};
//    char* outputFileName[] = {"test/output1.png", "test/output3.png","test/output4.png",
//                              "test/output5.png","test/output6.png","test/output7.png"};
//    for (int i = 0; i < 6; i++) {
//        if (i == 4) continue; // input6 pass
//        int width, height, channels;
//        uint8_t* input_image = stbi_load(inputFileName[i], &width, &height, &channels, 0);
//        if(input_image == NULL) {
//            printf("Error in loading the image\n");
//            exit(1);
//        }
//        unsigned char *output_image = (unsigned char*)malloc(width*height*1);
//        Labeling(input_image, output_image, width, height);
//
//        printf("%s : ", outputFileName[i]);
//        if (simple_test(output_image, width, height)) printf("check... O\n");
//        else printf("check... X\n");
//
//        stbi_write_png(outputFileName[i], width, height, 1, output_image, width);
//        stbi_image_free(input_image);
//        stbi_image_free(output_image);
//    }
//
//    return 0;
//}