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

Product* ShoppingDB::get_product(std::string name) {
    for(auto & product : products){
        if(product->name == name){
            return product;
        }
    }
    return nullptr;
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
    User *user = new User(username, password, premium);
    users.push_back(user);
}

User* ShoppingDB::check_user(std::string username, std::string password){
    for(auto & user : users){
        if(user->name == username && user->check_password(password)){
            return user;
        }
    }
    return nullptr;
}
