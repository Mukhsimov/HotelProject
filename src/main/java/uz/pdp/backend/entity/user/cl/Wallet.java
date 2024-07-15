package uz.pdp.backend.entity.user.cl;

import lombok.Getter;

@Getter
public class Wallet {

    private Double UZS;

    public Wallet() {

        this.UZS = 0.0;
    }

    public void depositUZS(Double uzs) {
        if (uzs < 50000){
           throw new RuntimeException("you cannot top up your balance with less than 50,000 UZS");
        }
        UZS += uzs;

    }

    public void withdrawUZS(Double uzs) {
        if (uzs < 0) throw  new RuntimeException("Incorrect amount entered");
        if (UZS >= uzs) {
            UZS -= uzs;
        } else {
            throw new RuntimeException("Incorrect amount entered");
        }
    }


    public void payUZS(Double uzs) {
         withdrawUZS(uzs);
    }

}
