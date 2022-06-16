#include <vector>
#include "client_ui.h"
#include "product.h"
#include "user.h"
#include "ProductInfo.h"

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
        int price = current_user->discount(current_user->is_premium, db.get_product(product_name)->price);
        Product *product = new Product(product_name, price);
        current_user->add_purchase_history(product);
        os << "CLIENT_UI: Purchase completed. Price: " << price << "." << endl;
    }

}

void ClientUI::add_to_cart(std::string product_name) {
    // TODO: Problem 1.2
    if (!check_current_user()) return;

    if (db.get_product(product_name) == nullptr) {
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

    std::vector<Product *> cart = current_user->get_cart();

    os << "CLIENT_UI: ";

    if (cart.size() == 0) {
        os << "Cart: []" << endl;
        return;
    }
    os << "Cart: [";

    for (int i = 0; i < cart.size() - 1; i++) {
        auto product = cart.at(i);
        os << "(" << product->name << ", " << product->price << "), ";
    }

    os << "(" << cart.at(cart.size() - 1)->name << ", " << cart.at(cart.size() - 1)->price << ")]" << endl;
}

void ClientUI::buy_all_in_cart() {
    // TODO: Problem 1.2
    if (!check_current_user()) return;
    int total_price = 0;

    for (Product *product: current_user->get_cart()) {
        current_user->add_purchase_history(product);
        total_price += product->price;
    }

    os << "CLIENT_UI: Cart purchase completed. Total price: " << total_price << "." << endl;
    current_user->clear_cart();
}

void ClientUI::recommend_products() {
    // TODO: Problem 1.3
    if (!check_current_user()) return;

    if (!current_user->is_premium) {
        vector<Product *> purchase_history = current_user->get_purchase_history();

        // recommend list에 추가
        // 없으면 생성하여 인덱스 설정해서 넣기
        // 있으면 count ++
        vector<ProductInfo *> recommend_list;
        for (int i = 0; i < purchase_history.size(); i++) {
            Product *product = purchase_history.at(i);
            int j;
            for (j = 0; j < recommend_list.size(); j++) {
                if (recommend_list.at(j)->product->name == product->name) {
                    recommend_list.at(j)->count++;
                    recommend_list.at(j)->idx = j;
                    break;
                }
            }
            if (j == recommend_list.size()) { // TODO: 벡터에 없는 건지 확인
                ProductInfo *productInfo = new ProductInfo(product, 1, i);
                recommend_list.push_back(productInfo);
            }
        }

        // sort

        sort(recommend_list.begin(), recommend_list.end());

        // 세개만 출력
        os << "CLIENT_UI: ";

        if (recommend_list.size() == 0) {
            // Recommended products: []
            os << "Recommended products: []" << endl;
        } else if (recommend_list.size() < 3) {
            os << "Recommended products: [";
            for (int i = 0; i < recommend_list.size() - 1; i++) {
                auto product = recommend_list.at(i)->product;
                os << "(" << product->name << ", " << product->price << "), ";
            }
            os << "(" << recommend_list.at(recommend_list.size() - 1)->product->name << ", "
               << recommend_list.at(recommend_list.size() - 1)->product->price << ")]" << endl;
        } else {
            os << "Recommended products: [";
            for (int i = 0; i < 2; i++) {
                auto product = recommend_list.at(i)->product;
                os << "(" << product->name << ", " << product->price << "), ";
            }
            os << "(" << recommend_list.at(2)->product->name << ", "
               << recommend_list.at(2)->product->price << ")]" << endl;
        }
    }
}
