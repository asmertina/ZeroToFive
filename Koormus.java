import java.util.ArrayList;

/**
 * Created by Anna Smerina and Sander Suik on 14/03/2017.
 */
public class Koormus {

    //abiks on kaks listi - üks trennikordade jaoks ja teine trennikordade jaoks koos koormuse arvestusega
    ArrayList<TsükliKoormus> Koormus = new ArrayList<>();
    ArrayList<TsükliKoormus> sorteeritudKoormus = new ArrayList<>();

    //jooksualgoritm - arvutab koormuse välja soovituslike CouchTo5K nõuannete järgi

    public ArrayList<TsükliKoormus> arvutakormus() {
        int tsyklitearv;
        for (int i = 1; i < 7; i++) {
            if (i < 4) {
                int jooksJAkõnd = i + 1;
                tsyklitearv = (int) 20 / jooksJAkõnd;
                int kogupikkus = tsyklitearv * jooksJAkõnd;
                TsükliKoormus tsykkel = new TsükliKoormus(i, 1, tsyklitearv, kogupikkus);
                for(int d = 0; d<4; d++){ //igat trennikorda lisan listi kohe 4x - siin ja edaspidi
                    Koormus.add(tsykkel);
                }

            } else {
                int jooksJAkõnd = i + 2;
                if (i < 5) {
                    tsyklitearv = (int) 30 / jooksJAkõnd;
                } else {
                    tsyklitearv = (int) 30 / jooksJAkõnd;
                }

                int kogupikkus = tsyklitearv * jooksJAkõnd;
                TsükliKoormus tsykkel = new TsükliKoormus(i, 2, tsyklitearv, kogupikkus);
                for(int d = 0; d<4; d++){
                    Koormus.add(tsykkel);
                }
            }
        }

        for (int i = 8; i < 13; i = i + 2) {
            if (i < 11) {
                int jooksJAkõnd = i + 3;
                tsyklitearv = (int) 40 / jooksJAkõnd;
                int kogupikkus = tsyklitearv * jooksJAkõnd;
                TsükliKoormus tsykkel = new TsükliKoormus(i, 3, tsyklitearv, kogupikkus);
                for(int d = 0; d<4; d++){
                    Koormus.add(tsykkel);
                }
            } else {
                int jooksJAkõnd = i + 3;
                tsyklitearv = 3;
                int kogupikkus = tsyklitearv * jooksJAkõnd;
                TsükliKoormus tsykkel = new TsükliKoormus(i, 3, tsyklitearv, kogupikkus);
                for(int d = 0; d<4; d++){
                    Koormus.add(tsykkel);
                };
            }

        }
        //viimased trennid lisan käsitsi
        TsükliKoormus tsykkel1 = new TsükliKoormus(15, 5, 3, (15 + 5) * 3);
        for(int d = 0; d<4; d++){
            Koormus.add(tsykkel1);
        }
        TsükliKoormus tsykkel2 = new TsükliKoormus(15, 3, 2, (15 + 3) * 2);
        for(int d = 0; d<4; d++){
            Koormus.add(tsykkel2);
        }
        TsükliKoormus tsykkel3 = new TsükliKoormus(15, 1, 2, (15 + 1) * 2);
        for(int d = 0; d<4; d++){
            Koormus.add(tsykkel3);
        }
        TsükliKoormus tsykkel4 = new TsükliKoormus(30, 0, 1, 30);
        for(int d = 0; d<4; d++){
            Koormus.add(tsykkel4);
        }
        return Koormus; //see on osaliselt kontrolli jaoks programmi kirjutamise ajal


    }

    //sorteerin välja alakoormusega trennid - lisan uude listi ainult õige koormusega

    public ArrayList<TsükliKoormus> SorteeriList(int praeguneKoormus){

        for (TsükliKoormus p : Koormus) {
            if(p.getJooks() >= praeguneKoormus){
                sorteeritudKoormus.add(p);
            }
        }
        return sorteeritudKoormus;

    }


}



