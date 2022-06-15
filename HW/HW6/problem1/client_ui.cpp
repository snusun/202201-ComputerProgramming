#include <vector>
#include "client_ui.h"
#include "product.h"
#include "user.h"

using namespace std;

ClientUI::ClientUI(ShoppingDB &db, std::ostream& os) : UI(db, os), current_user() { }

void ClientUI::signup(std::string username, std::string password, bool premium) {
    // TODO: Problem 1.2
    db.add_user(username, password, premium);
    cout << "CLIENT_UI: " << username << " is signed up." << endl;
}

void ClientUI::login(std::string username, std::string password) {
    // TODO: Problem 1.2
    if(current_user != nullptr){
        cout << "CLIENT_UI: Please logout first." << endl;
    } else {
        User* user = db.check_user(username, password);
        if(user != nullptr){
            current_user = user;
            cout << "CLIENT_UI: " << username << " is logged in." << endl;
        } else {
            cout << "CLIENT_UI: Invalid username or password." << endl;
        }
    }
}

void ClientUI::logout() {
    // TODO: Problem 1.2
    if(current_user != nullptr){
        cout << "CLIENT_UI: " << current_user->name << " is logged out." << endl;
        current_user = nullptr;
    } else {
        cout << "CLIENT_UI: There is no logged-in user." << endl;
    }
}

bool ClientUI::check_current_user(){
    if(current_user== nullptr) {
        cout << "CLIENT_UI: Please login first." << endl;
        return false;
    }
    else return true;
}

void ClientUI::buy(std::string product_name) {
    // TODO: Problem 1.2
    if(!check_current_user()) return;
}

void ClientUI::add_to_cart(std::string product_name) {
    // TODO: Problem 1.2
    if(!check_current_user()) return;
}

void ClientUI::list_cart_products() {
    // TODO: Problem 1.2.
    if(!check_current_user()) return;
}

void ClientUI::buy_all_in_cart() {
    // TODO: Problem 1.2
    if(!check_current_user()) return;
}

void ClientUI::recommend_products() {
    // TODO: Problem 1.3

}
