import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;

public class Program extends Application {

    Image ico = new Image("icon.png");

    public static int TRACK_LENGTH = 5000;
    public static int MAX_VELOCITY = 10;
    private final static int windowWidth = 700;
    private final static int windowHeight = 340;
    private final static int startPostitionInPixels = 30;
    private final static int finishPostitionInPixels = 525;

    private static Car car1thread;
    private static Car car2thread;
    private static Car car3thread;

    private static boolean RACE_IS_STARTED = false;
    private static Pane appRoot = new Pane();
    private static Pane gameRoot = new Pane();

    private static int money = 100;
    private Label moneyLabel = new Label("Money: " + money + "$");
    private Label noMoneyLabel = new Label("Look like you play all your money! \nHere's a present for you, but be vigilant ;) \n" +
            "+50$ for you! (click me to hide)");

    private static int rate = 10;
    private Label rateLabel = new Label("Rate: " + rate + "$");

    private static int choise = 0;
    private Label choiseLabel = new Label("Please, select a car!");

    private Label winLabel = new Label("");
    private Label loseLabel = new Label("");

    private Button ratePlus = new Button("+");
    private Button rateMinus = new Button("-");

    private Button startButton = new Button("Start!");

    private Image background = new Image("bg.png");
    private Image car1 = new Image("car1.png");
    private Image car2 = new Image("car2.png");
    private Image car3 = new Image("car3.png");
    private ImageView car1Image = new ImageView();
    private ImageView car2Image = new ImageView();
    private ImageView car3Image = new ImageView();

    private DropShadow shadow = new DropShadow();

    public static void main(String[] args) throws IOException {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(createContent());

        animationTimer.start();
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Racer Totalizator");
        primaryStage.getIcons().add(ico);
        primaryStage.show();
    }

    private AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            update();
        }
    };


    private Parent createContent() {
        gameRoot.setPrefSize(windowWidth, windowHeight);

        ImageView bgImage = new ImageView();
        bgImage.setImage(background);
        gameRoot.getChildren().addAll(bgImage);

        addCars();
        addNav();

        appRoot.getChildren().addAll(gameRoot);
        return appRoot;
    }

    private void addCars() {
        car1Image.setImage(car1);
        car1Image.setTranslateX(startPostitionInPixels);
        car1Image.setTranslateY(72);
        car1Image.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!RACE_IS_STARTED) {
                    startButton.setDisable(false);
                    hideLoseWinLayers();
                    choise = 1;
                }
            }
        });

        car2Image.setImage(car2);
        car2Image.setTranslateX(startPostitionInPixels);
        car2Image.setTranslateY(122);
        car2Image.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!RACE_IS_STARTED) {
                    startButton.setDisable(false);
                    hideLoseWinLayers();
                    choise = 2;
                }
            }
        });

        car3Image.setImage(car3);
        car3Image.setTranslateX(startPostitionInPixels);
        car3Image.setTranslateY(172);
        car3Image.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!RACE_IS_STARTED) {
                    startButton.setDisable(false);
                    hideLoseWinLayers();
                    choise = 3;
                }
            }
        });

        gameRoot.getChildren().addAll(car1Image, car2Image, car3Image);
    }

    private void addNav() {

        shadow.setOffsetX(1.5f);
        shadow.setOffsetY(1.0f);
        shadow.setColor(Color.color(0, 0, 0, 0.5));

        ratePlus.setPrefSize(30, 30);
        rateMinus.setPrefSize(30, 30);
        rateMinus.setDisable(true);

        ratePlus.setTranslateX(350);
        ratePlus.setTranslateY(10);
        ratePlus.setFont(new Font("Arial", 15));
        ratePlus.setEffect(shadow);

        rateMinus.setTranslateX(390);
        rateMinus.setTranslateY(10);
        rateMinus.setFont(new Font("Arial", 15));
        rateMinus.setEffect(shadow);

        startButton.setPrefSize(150, 30);
        startButton.setTranslateX(430);
        startButton.setTranslateY(10);
        startButton.setFont(new Font("Arial", 15));
        startButton.setEffect(shadow);
        startButton.setDisable(true);


        moneyLabel.setTranslateX(15);
        moneyLabel.setTranslateY(15);
        moneyLabel.setFont(new Font("Arial", 20));
        moneyLabel.setTextFill(Color.WHITE);
        moneyLabel.setEffect(shadow);

        noMoneyLabel.setTranslateX(200);
        noMoneyLabel.setTranslateY(100);
        noMoneyLabel.setFont(new Font("Arial", 20));
        noMoneyLabel.setTextAlignment(TextAlignment.CENTER);
        noMoneyLabel.setTextFill(Color.color(0.3, 1, 0.3, 0.5));
        noMoneyLabel.setEffect(shadow);
        noMoneyLabel.setVisible(false);
        noMoneyLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                noMoneyLabel.setVisible(false);
            }
        });

        rateLabel.setTranslateX(175);
        rateLabel.setTranslateY(15);
        rateLabel.setFont(new Font("Arial", 20));
        rateLabel.setTextFill(Color.WHITE);
        rateLabel.setEffect(shadow);

        choiseLabel.setTranslateX(100);
        choiseLabel.setTranslateY(250);
        choiseLabel.setFont(new Font("Arial", 25));
        choiseLabel.setTextFill(Color.WHITE);
        choiseLabel.setEffect(shadow);

        winLabel.setTranslateX(100);
        winLabel.setTranslateY(280);
        winLabel.setFont(new Font("Arial", 25));
        winLabel.setTextFill(Color.WHITE);
        winLabel.setEffect(shadow);

        loseLabel.setTranslateX(100);
        loseLabel.setTranslateY(280);
        loseLabel.setFont(new Font("Arial", 25));
        loseLabel.setTextFill(Color.WHITE);
        loseLabel.setEffect(shadow);


        gameRoot.getChildren().addAll(moneyLabel, rateLabel, ratePlus, rateMinus, startButton, choiseLabel, winLabel, loseLabel, noMoneyLabel);

        ratePlus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                rate += 10;
                if (rate >= money) {
                    rate = money;
                }
                update();
            }
        });

        rateMinus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (rate > 10) {
                    rate -= 10;
                } else {
                    rate = 10;
                }
                update();
            }
        });

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                startRace();
            }
        });

    }

    private void startRace() {
        if (!RACE_IS_STARTED & choise != 0) {
            startButton.setDisable(true);
            ratePlus.setDisable(true);
            rateMinus.setDisable(true);
            RACE_IS_STARTED = true;
            money -= rate;
            startCarsThreads();
            update();
        }
    }

    private void startCarsThreads() {
        car1thread = new Car();
        car2thread = new Car();
        car3thread = new Car();
        car1thread.start();
        car2thread.start();
        car3thread.start();
    }

    private void finishCarsThreads() {
        car1thread.finish();
        car2thread.finish();
        car3thread.finish();
    }

    private void update() {
        updateTopLabels();
        updateCarImagesPosition();
        updateChoiseLayer();

        if (RACE_IS_STARTED) {
            int winner = whoIsWinner();
            if (winner != 0) {
                RACE_IS_STARTED = false;
                finishRace(winner);
                resetAllToStart();
            }
        }
    }

    private void updateTopLabels() {
        moneyLabel.setText("Money: " + money + "$");
        rateLabel.setText("Your bet: " + rate + "$");

        if (!RACE_IS_STARTED) {
            startButton.setDisable(false);
        }
        if (choise == 0) {
            startButton.setDisable(true);
        }

        rateMinus.setDisable(true);
        ratePlus.setDisable(false);
        if (rate <= 10) {
            rateMinus.setDisable(true);
        } else {
            rateMinus.setDisable(false);
        }

        if (rate < money) {
            ratePlus.setDisable(false);
        } else {
            ratePlus.setDisable(true);
        }

        if (rate > money) {
            startButton.setDisable(true);
        }

        if (money == 0 && choise == 0) {
            noMoneyLabel.setVisible(true);
            money += 50;
        }
    }

    private void updateCarImagesPosition() {
        if (RACE_IS_STARTED) {

            double pos1 = startPostitionInPixels + car1thread.getDistance() * finishPostitionInPixels / TRACK_LENGTH;
            double pos2 = startPostitionInPixels + car2thread.getDistance() * finishPostitionInPixels / TRACK_LENGTH;
            double pos3 = startPostitionInPixels + car3thread.getDistance() * finishPostitionInPixels / TRACK_LENGTH;

            car1Image.setTranslateX(pos1);
            car2Image.setTranslateX(pos2);
            car3Image.setTranslateX(pos3);
        }
    }

    private void updateChoiseLayer() {
        if (choise != 0) {
            choiseLabel.setText("So.. Your bet on car #" + choise + " is " + rate + "! Good Luck!");
            noMoneyLabel.setVisible(false);
        }
    }

    private int whoIsWinner() {
        if (RACE_IS_STARTED) {
            // if first racer finished, and he is FARTHER than the second and third
            if (car1thread.isFinish() && (car1thread.getDistance() > car2thread.getDistance()) && (car1thread.getDistance() > car3thread.getDistance()))
                return 1;

            // if second racer finished, and he is FARTHER than the first and third
            if (car2thread.isFinish() && (car2thread.getDistance() > car1thread.getDistance()) && (car2thread.getDistance() > car3thread.getDistance()))
                return 2;

            // if third racer finished, and he is FARTHER than the first and second
            if (car3thread.isFinish() && (car3thread.getDistance() > car1thread.getDistance()) && (car3thread.getDistance() > car2thread.getDistance()))
                return 3;

            // return randomly number of one of racers,
            //  if during our MAINTICK, more than 1 racer finished and...

            // ...the position of the first and second racer are EQUAL
            if (car1thread.getDistance() >= TRACK_LENGTH && car1thread.getDistance() == car2thread.getDistance()) {
                return (int) (Math.random() * 2) + 1;  // [1, 2]
            }

            // ...the position of the first and third racer are EQUAL
            if (car1thread.getDistance() >= TRACK_LENGTH && car1thread.getDistance() == car3thread.getDistance()) {
                int a = (int) (Math.random() * 2) + 1;  // [1, 2]
                if (a != 1) {
                    a = 3;                              // 2 to 3
                }
                return a;
            }

            // ...the position of the second and third racer are EQUAL
            if (car2thread.getDistance() >= TRACK_LENGTH && car2thread.getDistance() == car3thread.getDistance()) {
                return (int) (Math.random() * 2) + 2;  // [2, 3]
            }

            // ...the position of all the racers EQUAL
            if (car1thread.getDistance() >= TRACK_LENGTH &&
                    car1thread.getDistance() == car2thread.getDistance() &&
                    car1thread.getDistance() == car3thread.getDistance()) {
                return (int) (Math.random() * 3) + 1;  // [1, 3]
            }

            return 0;
        } else {
            return 0;
        }
    }

    private void finishRace(int winner) {
        if (choise != 0) {
            finishCarsThreads();
            if (choise == winner) {
                winAction();
            } else {
                loseAction();
            }
            choise = 0;
        }
    }

    private void winAction() {
        money += rate * 2;
        choiseLabel.setText("Please, select a car for new race!");
        winLabel.setText("Yeah! You win " + rate + "$ this time! Congratulations!");
    }

    private void loseAction() {
        choiseLabel.setText("Please, select a car for new race!");
        loseLabel.setText("You lose your " + rate + "$, dude! But try again ;)");
    }

    private void resetAllToStart() {

        car1Image.setTranslateX(startPostitionInPixels);
        car2Image.setTranslateX(startPostitionInPixels);
        car3Image.setTranslateX(startPostitionInPixels);
        startButton.setDisable(true);
        ratePlus.setDisable(false);
        rateMinus.setDisable(false);

    }

    private void hideLoseWinLayers() {
        noMoneyLabel.setVisible(false);
        winLabel.setText("");
        loseLabel.setText("");
    }
}