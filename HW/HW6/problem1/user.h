#ifndef PROBLEM1_USER_H
#define PROBLEM1_USER_H

#include <string>
#include <vector>
#include "product.h"
#define DISCOUNT_RATE 0.1

class User {
public:
    User(std::string name, std::string password);
    const std::string name;
    void add_purchase_history(Product* product);
private:
    std::string password;
};

class NormalUser : public User {

};

class PremiumUser : public User {

};

#endif //PROBLEM1_USER_H
