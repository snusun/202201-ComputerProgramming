//
// Created by myMacbook on 2022/06/16.
//

#include <iostream>
#include "ProductInfo.h"

ProductInfo::ProductInfo(Product *product, int count, int idx) : product(product), count(count), idx(idx) {}

bool ProductInfo::operator> (const ProductInfo& other) const {
    if(count == other.count){
        return idx > other.idx;
    }
    return count > other.count;
}
