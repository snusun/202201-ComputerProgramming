#include <iostream>
#include "player.h"

void round(Player* a, Player* b);

int Monster::num_monsters = 0;

int main() {

    Player a, b;
    a.add_monster(fireMon);
    a.add_monster(fireMon);
    a.add_monster(waterMon);
    b.add_monster(waterMon);
    b.add_monster(grassMon);
    b.add_monster(grassMon);

    std::cout << "Game start!" << std::endl;

    std::cout << "A's HP is "<<a.get_total_hp() << " (";
    for(int j=0; j<a.get_num_monsters(); j++){std::cout<<*(a.get_monster()[j])<<"<"<<a.get_monster()[j]->get_hp()<<"> ";}
    std::cout << ") / B's HP is " << b.get_total_hp() << " ( ";
    for(int j=0; j<b.get_num_monsters(); j++){std::cout<<*(b.get_monster()[j])<<"<"<<b.get_monster()[j]->get_hp()<<"> ";}
    std::cout<<")"<<std::endl;

    for (int i = 1;; i++) {
        round(&a, &b);
        std::cout << "Round " << i << ": " << "A's HP is "<<a.get_total_hp() << " (";
        for(int j=0; j<a.get_num_monsters(); j++){std::cout<<*(a.get_monster()[j])<<"<"<<a.get_monster()[j]->get_hp()<<"> ";}
        std::cout << ") / B's HP is " << b.get_total_hp() << " ( ";
        for(int j=0; j<b.get_num_monsters(); j++){std::cout<<*(b.get_monster()[j])<<"<"<<b.get_monster()[j]->get_hp()<<"> ";}
        std::cout<<")"<<std::endl;

        if (b.get_num_monsters() == 0) {
            std::cout << "Player A won the game!" << std::endl;
            break;
        } else if (a.get_num_monsters() == 0) {
            std::cout << "Player B won the game!" << std::endl;
            break;
        }
    }
    return 0;
}

void round(Player* a, Player* b) {
    Monster* a_monster = a->select_monster();
    Monster* b_monster = b->select_monster();
    std::cout<<"A select "<<*a_monster<<"("<<a_monster->get_speed()<<"), B select "<<*b_monster<<"("<<b_monster->get_speed()<<")"<<std::endl;
    if(a_monster->get_speed()>b_monster->get_speed()){
        a_monster->attack(b_monster);
        if(b_monster->get_hp()<=0){
            std::cout<<"B's "<<*b_monster<<" is fainted!"<<std::endl;
            b->delete_monster(b_monster);
            return;
        }
        b_monster->attack(a_monster);
        if(a_monster->get_hp()<=0){
            std::cout<<"A's "<<*a_monster<<" is fainted!"<<std::endl;
            a->delete_monster(a_monster);
            return;
        }
    }else{
        b_monster->attack(a_monster);
        if(a_monster->get_hp()<=0){
            std::cout<<"A's "<<*a_monster<<" is fainted!"<<std::endl;
            a->delete_monster(a_monster);
            return;
        }
        a_monster->attack(b_monster);
        if(b_monster->get_hp()<=0){
            std::cout<<"B's "<<*b_monster<<" is fainted!"<<std::endl;
            b->delete_monster(b_monster);
            return;
        }
    }
}