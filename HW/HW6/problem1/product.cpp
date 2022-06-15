#include "product.h"

Product::Product(std::string name, int price): name(name), price(price) { }

//std::ostream& operator<<(std::ostream& os, const Product& product){
//    return os << "(" << product.name << ", " << product.price << ")";
//}
