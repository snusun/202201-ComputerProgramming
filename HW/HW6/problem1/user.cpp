#include "user.h"

User::User(std::string name, std::string password): name(name), password(password) {

}

void User::add_purchase_history(Product* product){
    // TODO: Problem 1.2

}

bool User::check_password(std::string password) {
    if(this->password == password) {
        return true;
    } else return false;
}