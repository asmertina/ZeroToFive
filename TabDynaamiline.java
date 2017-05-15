import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Anna Smertina on 21/04/2017.
 */


public class TabDynaamiline extends Application implements Serializable {

    public static boolean onNumber(String str){ //erindi püüdmiseks on see abiks
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

    //mul on vaja muutujaid, mis on saadaval mitmes klassis

    String Jõudlus;
    String Nädalapäevad;
    int praeguneJõudlus = -1;
    ArrayList<String> Trennid = null;
    int jäänud = 0;
    List<Float> values = new ArrayList();

    public void start(Stage peaLava)  {

        peaLava.setTitle("ZeroToFive");


        peaLava.setOnCloseRequest(new EventHandler<WindowEvent>() {



            public void handle(WindowEvent we) { //http://stackoverflow.com/questions/16111496/java-how-can-i-write-my-arraylist-to-a-file-and-read-load-that-file-to-the
                FileOutputStream fos = null;
                FileOutputStream fos2 = null;
                // vastavalt vajadusele tekitan ajutised failid trennide ja progressbari väärtuste hoidmiseks
                // ja kui graafik täidetud, kustutan ajutised failid
                if(jäänud > 0){
                try {
                    fos = new FileOutputStream("t.tmp");
                    ObjectOutputStream oos = null;
                    oos = new ObjectOutputStream(fos);
                    oos.writeObject(Trennid);
                    oos.close();

                    fos2 = new FileOutputStream("t2.tmp");
                    ObjectOutputStream oos2 = null;
                    oos2 = new ObjectOutputStream(fos2);
                    oos2.writeObject(values);
                    oos2.close();


                } catch (IOException e) { //deprecated!
                    e.printStackTrace();
                }}

                if(jäänud == 0){
                    File kustutamiseks = new File("t.tmp");
                    File kustutamiseks2 = new File("t2.tmp");
                    kustutamiseks.delete();
                    kustutamiseks2.delete();
                }


            }
        });



        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 400,600);

        //edasi loon põhitabi, mille nupule vajutamise korral tuleb ette jooksugraafikuku tab
        GridPane UusPaan = new GridPane();
        UusPaan.setBackground(new Background(new BackgroundFill(Color.web("#fbfdfb"), new CornerRadii(0), Insets.EMPTY)));

        UusPaan.setHgap(20); // http://stackoverflow.com/questions/20454021/how-to-set-padding-between-columns-of-a-javafx-gridpane
        UusPaan.setVgap(30); //vertical gap in pixels
        UusPaan.setPadding(new Insets(30, 30, 30, 30));


        Label tutvustus = new Label();
        tutvustus.setText("Hei!" + "\n" + "\n" + "ZeroToFive aitab sul joosta 5 kilomeetrit ka siis, kui see esialgu võimatu tundub." +  "\n" +  "\n" +
                "Alustuseks mõõda ära, mitu minutit suudad joosta nii, et pulss liiga kõrgeks ei lähe ja endal mugav oleks." + "\n" +  "\n" +
                "Seejärel sisesta küsitavad andmed programmi ning vastu saad graafiku, mille täitmine aitab sul jõuda eesmärgini." + "\n");
        tutvustus.setWrapText(true); //selleks, et sobituks kasti
        tutvustus.setFont(new Font("Roboto", 14));


        UusPaan.add(tutvustus,0,0,2,1);


        final TextField päevad = new TextField();
        päevad.setPromptText("Sisesta vabad nädalapäevad, eralda komaga");
        päevad.getText();
        GridPane.setConstraints(päevad, 0, 1);
        UusPaan.setHgrow(päevad, Priority.ALWAYS);
        UusPaan.getChildren().add(päevad);

        final TextField jõudlus = new TextField();
        jõudlus.setPromptText("Mitu minutit suudad joosta?");
        GridPane.setConstraints(jõudlus, 0, 2);
        UusPaan.setHgrow(jõudlus, Priority.ALWAYS);

        UusPaan.getChildren().add(jõudlus);

        Button submit = new Button("Arvuta graafik!");
        UusPaan.setConstraints(submit, 0, 3);
        UusPaan.getChildren().add(submit);


        Tab Sisend = new Tab();
        Sisend.setText("Alustuseks!");
        Sisend.setContent(UusPaan);

        TabPane tabiPaan = new TabPane();
        tabiPaan.getTabs().add(Sisend);


        Button taasta = new Button("Taasta pooleli olev kava");
        UusPaan.setConstraints(taasta, 0, 4);
        UusPaan.getChildren().add(taasta);


        taasta.setOnAction(new EventHandler<ActionEvent>() {


            @Override
            public void handle(ActionEvent event) {
                File f = new File("t.tmp");

                if(!f.exists()){

                    Alert failiViga = new Alert(Alert.AlertType.WARNING);
                    failiViga.setTitle("Viga!");
                    failiViga.setHeaderText("Ühtegi treeningkava ei leitud!");
                    String sõnum = "Alusta trennigraafiku täitmisega uuesti";
                    failiViga.setContentText(sõnum);
                    failiViga.showAndWait();

                }
                else{
                    final Tab ülesanneteLeht = TabYlesanded();
                    tabiPaan.getTabs().add(ülesanneteLeht);
                    tabiPaan.getSelectionModel().select(ülesanneteLeht);
                }


            }
        });





        submit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                if ((!päevad.getText().isEmpty()  && !jõudlus.getText().isEmpty())) {
                    Nädalapäevad = päevad.getText();
                    Jõudlus = jõudlus.getText();

                    try{
                        boolean kasOnInt = onNumber(Jõudlus);
                        if(!kasOnInt){
                            throw new NumbriErind("Jõudlus peaks olema täisarv!");
                        }
                        if(kasOnInt){
                            praeguneJõudlus = Integer.parseInt(Jõudlus);
                        }}
                    catch(NumbriErind n){
                        Alert numbriViga = new Alert(Alert.AlertType.WARNING);
                        numbriViga.setTitle("Viga!");
                        numbriViga.setHeaderText("Jõudluse sisestuse viga!");
                        String sõnum = n.getMessage();
                        numbriViga.setContentText(sõnum);
                        numbriViga.showAndWait();
                    }


                    final Tab ülesanneteLeht = TabYlesanded();
                    if (praeguneJõudlus >= 0) {
                    tabiPaan.getTabs().add(ülesanneteLeht);
                    tabiPaan.getSelectionModel().select(ülesanneteLeht);}
                }
                else {
                    Alert tühjadVäljad = new Alert(Alert.AlertType.WARNING);
                    tühjadVäljad.setTitle("Viga!");
                    tühjadVäljad.setHeaderText("Andmete sisestuse viga!");
                    String sõnum = "Kontrolli, kas väljad on täidetud!";
                    tühjadVäljad.setContentText(sõnum);
                    tühjadVäljad.showAndWait();
                }


            }
        });

        root.setCenter(tabiPaan);
        peaLava.setScene(scene);
        peaLava.setResizable(true);
        peaLava.show();}



    private Tab TabYlesanded(){

        ScrollPane skroller = new ScrollPane();  //http://docs.oracle.com/javafx/2/ui_controls/scrollpane.htm

        VBox ylesanneteBox = new VBox();
        skroller.setContent(ylesanneteBox);
        skroller.setMinViewportHeight(10);
        skroller.setFitToHeight(true);
        skroller.setFitToWidth(true);
        skroller.setPadding(new Insets(30, 30, 30, 30));

        Tab ylesanneteTab = new Tab();
        ylesanneteTab.setText("Trennid!");
        ylesanneteTab.setContent(skroller);

        ylesanneteTab.setOnSelectionChanged(new YlesanneteLaadija(ylesanneteBox));

        return ylesanneteTab;
    }

    class YlesanneteLaadija implements EventHandler<Event> {


        VBox sisendBox;


        public YlesanneteLaadija(VBox sisendBox) {
            this.sisendBox = sisendBox;
        }


        public void handle(Event syndmus) {


            if(praeguneJõudlus >= 0){
                Trennid = Jooksja2.tagastaKoormuselist(Nädalapäevad, praeguneJõudlus);
                int trennidearv = Trennid.size();
                float jagatis = (float)1.0/(float)trennidearv;
                for(int a = 0; a < trennidearv; a++){
                    values.add((a+1)*jagatis);
                }}

            else{
                try {
                    FileInputStream fis = new FileInputStream("t.tmp");
                    ObjectInputStream ois = null;
                    ois = new ObjectInputStream(fis);
                    Trennid = (ArrayList<String>)ois.readObject();
                    ois.close();

                    FileInputStream fis2 = new FileInputStream("t2.tmp");
                    ObjectInputStream ois2 = null;
                    ois2 = new ObjectInputStream(fis2);
                    values = (List<Float>)ois2.readObject();
                    ois2.close();



                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }



                //int trennidearv = Trennid.size();
                String[] Värvid = {
                        "#EAD9D5","#E8D8D4","#E7D7D4","#E6D6D3","#E5D6D3","#E4D5D2","#E2D4D2","#E1D4D1","#E0D3D1","#DFD2D1","#DED1D0","#DCD1D0","#DBD0CF","#DACFCF","#D9CFCE","#D8CECE","#D6CDCE","#D5CDCD","#D4CCCD","#D3CBCC","#D2CACC","#D0CACB","#CFC9CB","#CEC8CB","#CDC8CA","#CCC7CA","#CAC6C9","#C9C5C9","#C8C5C8","#C7C4C8","#C6C3C8","#C4C3C7","#C3C2C7","#C2C1C6","#C1C1C6","#C0C0C5","#BEBFC5","#BDBEC5","#BCBEC4","#BBBDC4","#BABCC3","#B8BCC3","#B7BBC2","#B6BAC2","#B5B9C2","#B4B9C1","#B2B8C1","#B1B7C0","#B0B7C0","#AFB6BF","#AEB5BF","#ADB5BF"
                };
                sisendBox.setPadding(new Insets(5, 5, 5, 5));
                sisendBox.setSpacing(10);
                int i = 0;

                sisendBox.getChildren().clear();
                //float jagatis = (float)1.0/(float)trennidearv;

                //final List<Float> values = new ArrayList();

                /*if(praeguneJõudlus >=0){
                for(int a = 0; a < trennidearv; a++){
                    values.add((a+1)*jagatis);
                }}*/

                final ProgressBar[] progressBar = new ProgressBar[values.size()];
                final ProgressBar pb = progressBar[0] = new ProgressBar();
                pb.prefWidthProperty().bind(sisendBox.widthProperty());
                pb.setStyle("-fx-accent: #977C7C;");
                sisendBox.getChildren().add(pb);

                for(String trenn : Trennid) {
                    GridPane ylesandePaan = new GridPane();
                    Label ylesanne = new Label(trenn);
                    ylesanne.setWrapText(true); //selleks, et sobituks kasti
                    ylesanne.setFont(new Font("Roboto", 14));
                    ylesanne.setMinHeight(Region.USE_PREF_SIZE);
                    ylesanne.setMaxWidth(Region.USE_PREF_SIZE);
                    Button tehtud = new Button("Läbitud!");
                    tehtud.setFont(new Font("Roboto", 14));
                    ylesandePaan.setPadding(new Insets(2,5,2,5));
                    ylesandePaan.setBackground(new Background(new BackgroundFill(Color.web(Värvid[i]), new CornerRadii(5), Insets.EMPTY)));
                    i = i+1;
                    ylesandePaan.add(ylesanne, 0, 0, 1, 1);
                    ylesandePaan.add(tehtud, 0, 1, 1, 2);
                    ylesandePaan.setAlignment(Pos.CENTER_LEFT);
                    //tehtud.setOnAction(event -> sisendBox.getChildren().remove(ylesandePaan));
                    sisendBox.getChildren().add(ylesandePaan);

                    tehtud.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent e) {
                            //int jäänud = sisendBox.getChildren().size() - 2;
                            jäänud = sisendBox.getChildren().size() - 2;

                            sisendBox.getChildren().remove(ylesandePaan);
                            pb.setProgress((Float) values.get(0));
                            values.remove(0);
                            Trennid.remove(0);

                            if(jäänud == 0){
                                Label hurraa = new Label();
                                hurraa.setText("\n" + "\n" + "Palju õnne!" + "\n" + "\n" + "Oled läbinud treeningkava, jätka samas vaimus edasi.");
                                hurraa.setWrapText(true); //selleks, et sobituks kasti
                                hurraa.setFont(new Font("Roboto", 16));
                                sisendBox.getChildren().add(hurraa);



                 }

                        }
                    })



            ;}
        }




    }


    public static void main(String[] args) {
        launch(args);
    }


}
