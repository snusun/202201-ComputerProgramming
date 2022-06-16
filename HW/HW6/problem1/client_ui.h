#ifndef PROBLEM1_CLIENT_UI_H
#define PROBLEM1_CLIENT_UI_H

#include <string>
#include "ui.h"
#include "shopping_db.h"
#include "ProductInfo.h"

class ClientUI : public UI {
    public:
        ClientUI(ShoppingDB &db, std::ostream& os);
        void signup(std::string username, std::string password, bool premium);
        void login(std::string username, std::string password);
        void logout();
        bool check_current_user();
        void add_to_cart(std::string product_name);
        void list_cart_products();
        void buy(std::string product_name);
        void buy_all_in_cart();
        void recommend_products();
        //bool compare_product_info(const ProductInfo& n1, const ProductInfo& n2);
    private:
        User* current_user;

    bool compare_product_info(ProductInfo &n1, ProductInfo &n2);
};

#endif //PROBLEM1_CLIENT_UI_H
