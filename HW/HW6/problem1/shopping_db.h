#ifndef PROBLEM1_SHOPPING_DB_H
#define PROBLEM1_SHOPPING_DB_H

#include <string>
#include <vector>
#include "user.h"
#include "product.h"

class ShoppingDB {
public:
    ShoppingDB();
    void add_product(std::string name, int price);
    bool check_product(std::string name);
    bool edit_product(std::string name, int price);
    void print_product();
    void add_user(std::string username, std::string password, bool premium);
    User* check_user(std::string username, std::string password);
private:
    std::vector<User*> users;
    std::vector<Product*> products;
};

#endif //PROBLEM1_SHOPPING_DB_H
