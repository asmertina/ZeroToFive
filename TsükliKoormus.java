/**
 *Created by Anna Smerina and Sander Suik on 14/03/2017.
 */
public class TsükliKoormus { //antud klass on vajalik, et saaks kõik trenni elemendid koos ühte isendisse koondada ja nad listi panna

    int jooks;
    int kõnd;
    int kord;
    int kogupikkus;

    public TsükliKoormus(int jooks, int kõnd, int kord, int kogupikkus) {
        this.jooks = jooks;
        this.kõnd = kõnd;
        this.kord = kord;
        this.kogupikkus = kogupikkus;
    }

    //lisan getterid selleks, et tulemuse väljastamisel saaksin eraldi välja võtta kõik elemendid

    public int getJooks() {
        return jooks;
    }

    public int getKõnd() {
        return kõnd;
    }

    public int getKord() {
        return kord;
    }

    public int getKogupikkus() {
        return kogupikkus;
    }

}
