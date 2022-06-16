#ifndef PROBLEM1_USER_H
#define PROBLEM1_USER_H

#include <string>
#include <vector>
#include "product.h"
#define DISCOUNT_RATE 0.1

class User {
public:
    User(std::string name, std::string password);
    User(std::string name, std::string password, bool is_premium);
    const std::string name;
    bool is_premium;
    void add_purchase_history(Product* product);
    bool check_password(std::string password);
    int discount(bool is_premium, int price);
    void add_cart(Product *product);
    std::vector<Product*> get_cart();
    void clear_cart();
private:
    std::string password;
    std::vector<Product*> purchase_history;
    std::vector<Product*> cart;
};

class NormalUser : public User {
};

class PremiumUser : public User {
};

#endif //PROBLEM1_USER_H
