import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Anna Smerina on 22/03/2017.
 */
public class Jooksja2 {

    public static boolean onNumber(String str){ //lahendus - https://coderanch.com/t/405258/java/String-IsNumeric
        try
        {
            int number = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public static ArrayList<String> tagastaKoormuselist(String Päevad, int Jõudlusesisend){

        ArrayList<String> KoormuseListTäielik = new ArrayList<>();


        String nädalaPäevad = Päevad;
        //int Jõudlus = Integer.parseInt(Jõudlusesisend);
        int Jõudlus = Jõudlusesisend;

        Koormus jooksja = new Koormus();
        jooksja.arvutakormus(); //koormused tuleks välja arvutada
        ArrayList<TsükliKoormus> KoormuseList = jooksja.SorteeriList(Jõudlus); //loon listi koormuse elementidest, mis on juba sorteeritud vastavalt sellele, kui palju suudab jooksja joosta

        //Nädalapäevadest tehakse nimekiri massiivina
        String ilmaTyhikuteta = nädalaPäevad.replaceAll("\\s", "");
        String[] nädalapäevadMassiiv = ilmaTyhikuteta.split(",");
        int vajalikudPäevad = KoormuseList.size(); //vaja on nii mitu nädalapäeva kui on trennikordi

        //Nädalapäevade õige arvu (nii palju, kui on tsükleid, nii palju ka nädalapäevi listis) lisamine listi
        ArrayList<String> Nädalapäevad = new ArrayList<>();

        for(int i = 0; i < vajalikudPäevad; i = i + (nädalapäevadMassiiv.length)){ //tsükkel iga trennikorra kohta, liigub edasi vabade nädalapäevade võrra
            for(int d = 0; d < nädalapäevadMassiiv.length; d++){ //lisan vabad nädalapäevad ükshaaval ja väline tsükkel liigub seejärel nädalapäevade võrra edasi oma indeksiga
                Nädalapäevad.add(nädalapäevadMassiiv[d]);
            }
        }

        //TULEMUSTE VÄLJASTAMINE
        //paralleelselt võetakse elementi trennikordade listist ja nädalapäevade listist, nii saab nad omavahel klappima

        for (int i = 0; i < KoormuseList.size(); i++) { //läbin trennikordade listi


            TsükliKoormus jooksuTsykkel = KoormuseList.get(i);

            if(jooksuTsykkel.getJooks() == 1) {
                KoormuseListTäielik.add(Nädalapäevad.get(i) + ": jookse " + jooksuTsykkel.getJooks() + " minut ja kõnni " + jooksuTsykkel.getKõnd() + " minut vaheldumisi " + jooksuTsykkel.getKord() + " korda. Trenn kestab " + jooksuTsykkel.getKogupikkus() + " minutit.");
            }
            else if(jooksuTsykkel.getKõnd() == 1){
                KoormuseListTäielik.add(Nädalapäevad.get(i) + ": jookse " + jooksuTsykkel.getJooks() + " minutit ja kõnni " + jooksuTsykkel.getKõnd() + " minut vaheldumisi " + jooksuTsykkel.getKord() + " korda. Trenn kestab " + jooksuTsykkel.getKogupikkus() + " minutit.");
            }

            else if (jooksuTsykkel.getJooks() == 30) {
                KoormuseListTäielik.add(Nädalapäevad.get(i) + ": jookse " + jooksuTsykkel.getJooks() + " järjest. Lõpp juba paistab.");

            }
            else{
                KoormuseListTäielik.add(Nädalapäevad.get(i) + ": jookse " + jooksuTsykkel.getJooks() + " minutit ja kõnni " + jooksuTsykkel.getKõnd() + " minutit vaheldumisi " + jooksuTsykkel.getKord() + " korda. Trenn kestab " + jooksuTsykkel.getKogupikkus() + " minutit.");

            }

        }

        return KoormuseListTäielik;
    }}








//http://stackoverflow.com/questions/31238492/writing-ics-ical-file-using-java
