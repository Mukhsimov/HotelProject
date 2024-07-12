package uz.pdp.backend.entity.user.cl;

public class Wallet {
    private Double USD;
    private Double EUR;
    private Double UZS;

    public Wallet() {
        this.EUR = 0.0;
        this.USD = 0.0;
        this.UZS = 0.0;
    }

    public void depositUSD(Double usd) {
        USD += usd;
    }

    public void depositEUR(Double eur) {
        EUR += eur;
    }

    public void depositUZS(Double uzs) {
        USD += uzs;
    }

    public boolean withdrawUSD(Double usd) {
        if (USD >= usd) {
            USD -= usd;
            return true;
        } else {
            return false;
        }
    }

    public boolean withdrawEUR(Double eur) {
        if (EUR >= eur) {
            EUR -= eur;
            return true;
        } else {
            return false;
        }
    }

    public boolean withdrawUZS(Double uzs) {
        if (UZS >= uzs) {
            UZS -= uzs;
            return true;
        } else {
            return false;
        }
    }

    public boolean payUSD(Double amount) {
        return withdrawUSD(amount);
    }

    public boolean payEUR(Double amount) {
        return withdrawEUR(amount);
    }

    public boolean payUZS(Double amount) {
        return withdrawUZS(amount);
    }

}
