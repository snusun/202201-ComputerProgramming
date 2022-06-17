//
// Created by myMacbook on 2022/06/16.
//

#ifndef PROBLEM1_PRODUCTINFO_H
#define PROBLEM1_PRODUCTINFO_H
#include "product.h"


class ProductInfo {
public:
    ProductInfo(Product* product, int count, int idx);
    Product* product;
    int count;
    int idx;
    bool operator> (const ProductInfo& other) const;
};


#endif //PROBLEM1_PRODUCTINFO_H
