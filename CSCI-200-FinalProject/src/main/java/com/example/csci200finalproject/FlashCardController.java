package com.example.csci200finalproject;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.List;
import java.util.ArrayList;



/*
*************TO DO LIST********************
--Add instructions for how to use
--Get testers & sugestions
--Go over requirements again
--Connect to github & version control
 */


public class FlashCardController extends Application {
    // Creation of the actual software with some initialization
    private List<FlashCard> flashcards = new ArrayList<>();
    private int currentIndex = 0;
    Button goToScene2, prevBtn, nextBtn, newCard, infoBtn;
    Stage window;
    Scene scene1, scene2;
    Label cardLabel, welcomeLabel;


    // Declare Timeline for flashing effect
    private Timeline flashTimeline;


    public static void main(String[] args){
        launch(args);
    }

//Customizing Scene


    //What the program runs on start
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Primary Stage Setup
        window = primaryStage;
        primaryStage.setTitle("Tavlet");

        //Layout 1

        welcomeLabel = new Label("Welcome to Tavlet!");
        goToScene2 = new Button("Create New Flash Card");
        infoBtn = new Button("HOW TO USE");
        //Button Action: this button switches the scene
        goToScene2.setOnAction(e -> window.setScene(scene2));


        //Creates the object that sets the positions of the UI objects and customizes them
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(new Background(new BackgroundFill(Color.DARKSALMON, CornerRadii.EMPTY, Insets.EMPTY)));

        //Customizes the welcome label and set its position on the scene
        welcomeLabel.setStyle("-fx-font-size: 45px;");
        welcomeLabel.setAlignment(Pos.CENTER);
        GridPane.setConstraints(welcomeLabel, 0,0);

        //Customizes the button to go to the next scene
        goToScene2.setStyle("-fx-background-color: #20B2AA; -fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold; -fx-background-radius: 55px;");
        GridPane.setConstraints(goToScene2, 0,3);
        goToScene2.setAlignment(Pos.CENTER);

        //Customizes the Info Button
        infoBtn.setStyle("-fx-background-color: #08302e; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 55px;");
        GridPane.setConstraints(infoBtn, 0,4);
        infoBtn.setAlignment(Pos.CENTER);
        //Sets up the information page
        infoBtn.setOnAction(e->{
            GridPane grid  = new GridPane();
            Button goBack = new Button("Go back ");
            Scene infoScene = new Scene(grid, 900,400);
            grid.setAlignment(Pos.CENTER);
            grid.setBackground(new Background(new BackgroundFill(Color.DARKSALMON, CornerRadii.EMPTY, Insets.EMPTY)));
            goBack.setOnAction(event->window.setScene(scene1));

            Label info = new Label("To use the program there you have to follow these steps: "+"\n"+
                    "-First, once you input the term or definition you must tap enter."+"\n"+
                    "-Next, once you are content with the contexts of both Term and Definition you must click save card."+"\n"+
                    "-Lastly, you will be able to see your first card by clicking the to flip the card."+"\n"+
                    "-The label above the card with show you the term and by click next or previous you will be able to"+"\n" + " cycle through the cards you have saved");

            info.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;-fx-background-color: #20B2AA;-fx-background-radius: 15px;");
            goBack.setStyle("-fx-background-color: #20B2AA; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 55px;");
            GridPane.setConstraints(goBack, 0, 0);
            GridPane.setConstraints(info,1, 1);

            grid.getChildren().addAll(info, goBack);
            window.setScene(infoScene);
        });

        //Adding the UI elements to the layout
        layout.getChildren().addAll(welcomeLabel, goToScene2, infoBtn);


        //Creating the Scene
        scene1 = new Scene(layout, 900, 600);


        //***********************************************Layout 2********************************************************************
        //Creating and customizing a text field for the input of Term and Definition
        TextField termField = new TextField();
        termField.setPromptText("Term");
        GridPane.setConstraints(termField, 0, 0);


        TextField defField = new TextField();
        defField.setPromptText("Definition");
        GridPane.setConstraints(defField, 0,1);

        termField.setAlignment(Pos.CENTER);
        defField.setAlignment(Pos.CENTER);

        // Make text bold
        termField.setStyle("-fx-font-weight: bold;");
        defField.setStyle("-fx-font-weight: bold;");

        //A descriptor for the flashcard
        cardLabel = new Label("Click Next to view flashcards");
        GridPane.setConstraints(cardLabel, 0, 2, 2, 1);


        //This button is basically the flash card
        //Customizing the FlashCard
        newCard = new Button("Click me to flip");
        newCard.setStyle("-fx-background-color: #008080; -fx-text-fill: white; -fx-font-weight: bold;-fx-font-size: 20px;");
        newCard.setMinSize(400,200);
        GridPane.setConstraints(newCard, 0, 3, 2, 1);


        //The buttons to switch between flashcards
        nextBtn = new Button("Next");
        nextBtn.setStyle("-fx-background-color: #20B2AA; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 55px;");
        GridPane.setConstraints(nextBtn, 1, 4);
        prevBtn = new Button("Previous");
        prevBtn.setStyle("-fx-background-color: #20B2AA; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 55px;");
        GridPane.setConstraints(prevBtn, 0, 4);


        //Button to save the cards to the ArrayList
        Button saveCardBtn = new Button("Save the Flashcard");
        saveCardBtn.setStyle("-fx-background-color: #20B2AA; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 55px;");
        GridPane.setConstraints(saveCardBtn, 1, 0);


        //These are the action methods for the buttons
        saveCardBtn.setOnAction(e->{
            FlashCard flashCard = new FlashCard(termField.getText(), defField.getText());
            flashcards.add(flashCard);
            termField.clear();
            defField.clear();
            cardLabel.setText("Flashcard Saved! ");
        });

        nextBtn.setOnAction(e-> showNextCard());
        prevBtn.setOnAction(e-> showPreviousCard());
        newCard.setOnAction(e-> flipCard());

        //Controls the Layout of the UI objects
        GridPane layout2 = new GridPane();
        layout2.setPadding(new Insets(10, 10, 10, 10));  // Padding around the grid
        layout2.setVgap(10);  // Vertical spacing between rows
        layout2.setHgap(10);  // Horizontal spacing between columns
        layout2.setAlignment(Pos.CENTER);  // Center the grid in the scene
        layout2.getChildren().addAll(termField,defField, newCard, nextBtn, prevBtn, cardLabel, saveCardBtn);
        layout2.setBackground(new Background(new BackgroundFill(Color.DARKSALMON, CornerRadii.EMPTY, Insets.EMPTY)));


        // Create the background color changing effect
        createFlashingBackground(layout2);

        //Flashes green when the new term or definition is entered
        termField.setOnAction(e-> checkAndToggleFlashing(termField, defField, layout2));
        defField.setOnAction(e-> checkAndToggleFlashing(termField, defField, layout2));

        scene2 = new Scene(layout2, 900, 600);
        window.setScene(scene1);
        window.show();


    }
    // Method to create the flashing background
    private void createFlashingBackground(GridPane layout2) {
        //Using Animation to control the color of the background
        flashTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), e ->
                        layout2.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)))
                ),
                new KeyFrame(Duration.seconds(1), e ->
                        layout2.setBackground(new Background(new BackgroundFill(Color.DARKSALMON, CornerRadii.EMPTY, Insets.EMPTY)))
                )
        );

        //How many times the color flashes
        flashTimeline.setCycleCount(1);
    }
    // Method that actually determines what happens when the textfield is triggered
    private void checkAndToggleFlashing(TextField termField, TextField defField, GridPane layout2) {
        if (!termField.getText().isEmpty() || !defField.getText().isEmpty()) {
            //play the effect if there is not text for term or definition
            flashTimeline.play();
        } else {
            flashTimeline.stop();
            layout2.setBackground(new Background(new BackgroundFill(Color.DARKSALMON, CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }
    //Shows the next card by increasing the index of the FlashCards array list
    private void showNextCard() {
        if (!flashcards.isEmpty() && currentIndex < flashcards.size() - 1) {
            currentIndex++;
            cardLabel.setText(flashcards.get(currentIndex).getTerm());
            newCard.setText("Click to flip");
        }
    }
    //Shows the previous card by decreasing the index of the FlashCards array list
    private void showPreviousCard() {
        if (!flashcards.isEmpty() && currentIndex > 0) {
            currentIndex--;
            cardLabel.setText(flashcards.get(currentIndex).getTerm());
            newCard.setText("Click to flip");
        }
    }

    //This is how the card "Flips"
    private void flipCard()
    {
        if(!flashcards.isEmpty())
        {
            FlashCard currentCard = flashcards.get(currentIndex);
            if (newCard.getText().equals(currentCard.getTerm())) {
                newCard.setText(currentCard.getDefinition());
            } else {
                newCard.setText(currentCard.getTerm());
            }
        }
    }


}


