#include <iostream>
#include "shopping_db.h"
#include "product.h"

using namespace std;

ShoppingDB::ShoppingDB() {
}

void ShoppingDB::add_product(std::string name, int price) {
    // TODO: Problem 1.1
    Product *p = new Product(name, price);
    products.push_back(p);
}

bool ShoppingDB::check_product(std::string name){
    for(auto & product : products){
        if(product->name == name){
            return true;
        }
    }
    return false;
}

bool ShoppingDB::edit_product(std::string name, int price) {
    // TODO: Problem 1.1
    for(auto & product : products){
        if(product->name == name){
            product->price = price;
            return true;
        }
    }
    return false;
}

std::vector<Product*> ShoppingDB::get_products(){
    return products;
}

void ShoppingDB::add_user(std::string username, std::string password, bool premium) {
    // TODO: Problem 1.2
    if(premium){
        PremiumUser *premiumUser = static_cast<PremiumUser *>(new User(username, password));
        users.push_back(premiumUser);
    } else {
        NormalUser *normalUser = static_cast<NormalUser *>(new User(username, password));
        users.push_back(normalUser);
    }
}

User* ShoppingDB::check_user(std::string username, std::string password){
    for(auto & user : users){
        if(user->name == username && user->check_password(password)){
            return user;
        }
    }
    return nullptr;
}
