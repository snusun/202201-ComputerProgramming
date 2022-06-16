#include <cmath>
#include "user.h"

User::User(std::string name, std::string password) : name(name), password(password) {

}

User::User(std::string name, std::string password, bool is_premium) : name(name), password(password),
                                                                      is_premium(is_premium) {

}

void User::add_purchase_history(Product *product) {
    // TODO: Problem 1.2
    purchase_history.push_back(product);
}

bool User::check_password(std::string password) {
    if (this->password == password) {
        return true;
    } else return false;
}

int User::discount(bool is_premium, int price){
    if(is_premium){
        int dis_price = round((1-DISCOUNT_RATE)*price);
        return dis_price;
    } else {
        return price;
    }
}

void User::add_cart(Product *product) {
    cart.push_back(product);
}

std::vector<Product*> User::get_cart(){
    return cart;
}

void User::clear_cart(){
    cart.clear();
}