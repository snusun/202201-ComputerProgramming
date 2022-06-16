#include <vector>
#include "client_ui.h"
#include "product.h"
#include "user.h"

using namespace std;

ClientUI::ClientUI(ShoppingDB &db, std::ostream &os) : UI(db, os), current_user() {}

void ClientUI::signup(std::string username, std::string password, bool premium) {
    // TODO: Problem 1.2
    db.add_user(username, password, premium);
    os << "CLIENT_UI: " << username << " is signed up." << endl;
}

void ClientUI::login(std::string username, std::string password) {
    // TODO: Problem 1.2
    if (current_user != nullptr) {
        os << "CLIENT_UI: Please logout first." << endl;
    } else {
        User *user = db.check_user(username, password);
        if (user != nullptr) {
            current_user = user;
            os << "CLIENT_UI: " << username << " is logged in." << endl;
        } else {
            os << "CLIENT_UI: Invalid username or password." << endl;
        }
    }
}

void ClientUI::logout() {
    // TODO: Problem 1.2
    if (current_user != nullptr) {
        os << "CLIENT_UI: " << current_user->name << " is logged out." << endl;
        current_user = nullptr;
    } else {
        os << "CLIENT_UI: There is no logged-in user." << endl;
    }
}

bool ClientUI::check_current_user() {
    if (current_user == nullptr) {
        os << "CLIENT_UI: Please login first." << endl;
        return false;
    } else return true;
}

void ClientUI::buy(std::string product_name) {
    // TODO: Problem 1.2
    if (!check_current_user()) return;

    if (db.get_product(product_name) == nullptr) {
        os << "CLIENT_UI: Invalid product name." << endl;
    } else {
        //os << current_user->is_premium << endl;
        int price = current_user->discount(current_user->is_premium, db.get_product(product_name)->price);
        Product *product = new Product(product_name, price);
        current_user->add_purchase_history(product);
        // CLIENT_UI: Purchase completed. Price: <PRICE>.
        os << "CLIENT_UI: Purchase completed. Price: " << price << "." << endl;
    }

}

void ClientUI::add_to_cart(std::string product_name) {
    // TODO: Problem 1.2
    if (!check_current_user()) return;

    if(db.get_product(product_name) == nullptr){
        os << "CLIENT_UI: Invalid product name." << endl;
    } else {
        int price = current_user->discount(current_user->is_premium, db.get_product(product_name)->price);
        Product *product = new Product(product_name, price);
        current_user->add_cart(product);
        os << "CLIENT_UI: " << product_name << " is added to the cart." << endl;
    }
}

void ClientUI::list_cart_products() {
    // TODO: Problem 1.2.
    if (!check_current_user()) return;

    std::vector<Product*> cart = current_user->get_cart();

    os << "CLIENT_UI: ";

    if(cart.size()==0){
        os << "Cart: []" << endl;
        return;
    }
    os << "Cart: [";

    for(int i=0; i<cart.size()-1; i++){
        auto product = cart.at(i);
        os << "(" << product->name << ", " << product->price << "), ";
    }

    os << "(" << cart.at(cart.size()-1)->name << ", " << cart.at(cart.size()-1)->price << ")]" << endl;

}

void ClientUI::buy_all_in_cart() {
    // TODO: Problem 1.2
    if (!check_current_user()) return;
    int total_price = 0;

    for(Product *product : current_user->get_cart()){
        current_user->add_purchase_history(product);
        total_price += product->price;
    }

    os << "CLIENT_UI: Cart purchase completed. Total price: " << total_price << "." << endl;
    current_user->clear_cart();
}

void ClientUI::recommend_products() {
    // TODO: Problem 1.3

}
