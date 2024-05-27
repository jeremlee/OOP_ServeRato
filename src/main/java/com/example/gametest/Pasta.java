package com.example.gametest;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Pasta {
    StackPane PastaStack;
    int key;

    private Pasta(PastaBuilder pb) {
        PastaStack = new StackPane();
        PastaStack.getChildren().add(new ImageView(pb.Plate));
        PastaStack.getChildren().add(new ImageView(pb.Base));
        PastaStack.getChildren().add(new ImageView(pb.Sauce));
        PastaStack.getChildren().add(new ImageView(pb.Topping));
        key = HashMe(pb.SauceType, pb.BaseType, pb.ToppingType);
    }

    public Pasta(){
        PastaStack = new StackPane();
        try {
            Image img = new Image(new FileInputStream("src/main/resources/com/example/gametest/food.png"), 50, 50, false, false);
            PastaStack.getChildren().add(new ImageView(img));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        key = 1;
    }


    public int HashMe(int x, int y, int z) {
        return ((x + 1) * 100) + ((y + 1) * 10) + z + 1;
    }

    public StackPane getPastaStack() {
        return PastaStack;
    }

    public int getKey(){
        return key;
    }

    public static class PastaBuilder {
        Image Plate;
        Image Base;
        Image Sauce;
        Image Topping;
        int SauceType;
        int ToppingType;
        int BaseType;
        int maxHeight;

        public PastaBuilder(int maxHeight) throws FileNotFoundException {
            this.maxHeight = maxHeight;
            Plate = new Image(String.valueOf(getClass().getResource("/com/example/gametest/foods/plate.png")), maxHeight, maxHeight, false, false);
        }

        public PastaBuilder(int maxHeight, Ingredient i){
            this.maxHeight = maxHeight;
            Plate = new Image(String.valueOf(getClass().getResource("/com/example/gametest/foods/plate.png")), maxHeight, maxHeight, false, false);

            try{
                this.setBase(i.getBase());
                this.setSauce(i.getSauce());
                this.setTopping(i.getTopping());
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
        }

        public PastaBuilder setBase(int type) throws FileNotFoundException {
            switch (type) {
                case 0:
                    this.Base = new Image(getClass().getResource("/com/example/gametest/foods/spag.png").toExternalForm(), maxHeight * 0.8, maxHeight * 0.8, false, false);
                    break;
                case 1:
                    this.Base = new Image(getClass().getResource("/com/example/gametest/foods/ravioli.png").toExternalForm(), maxHeight * 0.6, maxHeight * 0.6, false, false);
                    break;
                case 2:
                    this.Base = new Image(getClass().getResource("/com/example/gametest/foods/feta.png").toExternalForm(), maxHeight * 0.6, maxHeight * 0.6, false, false);
                    break;
            }
            BaseType = type;
            return this;
        }

        public PastaBuilder setSauce(int type) throws FileNotFoundException {
            switch (type) {
                case 0:
                    this.Sauce = new Image(getClass().getResource("/com/example/gametest/foods/banana.png").toExternalForm(), maxHeight * 0.8, maxHeight * 0.8, false, false);
                    break;
                case 1:
                    this.Sauce = new Image(getClass().getResource("/com/example/gametest/foods/carbonara.png").toExternalForm(), maxHeight * 0.8, maxHeight * 0.8, false, false);
                    break;
                case 2:
                    this.Sauce = new Image(getClass().getResource("/com/example/gametest/foods/tomato.png").toExternalForm(), maxHeight * 0.8, maxHeight * 0.8, false, false);
                    break;
            }
            SauceType = type;
            return this;
        }

        public PastaBuilder setTopping(int type) {
            switch (type) {
                case 0:
                    this.Topping = new Image(getClass().getResource("/com/example/gametest/foods/cheese.png").toExternalForm(), maxHeight * 0.75, maxHeight * 0.75, false, false);
                    break;
                case 1:
                    this.Topping = new Image(getClass().getResource("/com/example/gametest/foods/meatball.png").toExternalForm(), maxHeight * 0.4, maxHeight * 0.4, false, false);
                    break;
                case 2:
                    this.Topping = new Image(getClass().getResource("/com/example/gametest/foods/pepperoni.png").toExternalForm(), maxHeight * 0.4, maxHeight * 0.4, false, false);
                    break;
            }
            ToppingType = type;
            return this;
        }

        public Pasta build() {
            return new Pasta(this);
        }
    }
}
