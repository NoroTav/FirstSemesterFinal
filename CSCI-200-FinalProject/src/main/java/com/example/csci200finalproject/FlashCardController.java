package com.example.csci200finalproject;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
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
    Button goToScene2, prevBtn, nextBtn, newCard, infoBtn, settings;
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
        primaryStage.setTitle("Tav's FlashCards");
//****************************Layout 1********************************************************************
        welcomeLabel = new Label("Tav's FlashCards!");
        goToScene2 = new Button("Create");
        infoBtn = new Button("About");
        settings = new Button("⚙");



        //Button Action: this button switches the scene
        goToScene2.setOnAction(e -> window.setScene(scene2));


        //Creates the object that sets the positions of the UI objects and customizes them
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setBackground(new Background(new BackgroundFill(Color.web("#333533"), CornerRadii.EMPTY, Insets.EMPTY)));

        String sidebarBtnStyle = "-fx-background-color: #242423; -fx-text-fill: #cfdbd5; -fx-font-size: 25px;-fx-font-weight: bold; -fx-background-radius: 20px; -fx-padding: 10;";
        //Customizes the buttons
        goToScene2.setStyle(sidebarBtnStyle);
        infoBtn.setStyle(sidebarBtnStyle);
        settings.setStyle("-fx-background-color: #333533; -fx-text-fill: #cfdbd5; -fx-font-size: 24px; -fx-background-radius: 20px;");
        //Customizes the welcome label and set its position on the scene
        welcomeLabel.setStyle("-fx-background-color: #242423; -fx-text-fill: #cfdbd5; -fx-font-size: 45px; -fx-background-radius: 55px; -fx-padding: 10;");

        //Add buttons to sidebar
        sidebar.getChildren().addAll(welcomeLabel ,goToScene2,infoBtn, settings);

        //Right side of the screen
        VBox updateSection = new VBox(10);
        updateSection.setPadding(new Insets(20));
        updateSection.setBackground(new Background(new BackgroundFill(Color.web("#242423"), CornerRadii.EMPTY, Insets.EMPTY)));


        //Update title creation
        Label updateTitle = new Label("Update 1.0");
        updateTitle.setStyle("-fx-text-fill: #cfdbd5; -fx-font-size: 24px; -fx-font-weight: bold;");

        Label updateContent = new Label("- UI update\n- Saved Sets\n- About page\n- Settings");
        updateContent.setStyle("-fx-text-fill: #f5cb5c; -fx-font-size: 24px;");

        updateSection.getChildren().addAll(updateTitle, updateContent);
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



        //Combining the Left and Right Side
        HBox mainLayout = new HBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setBackground(new Background(new BackgroundFill(Color.web("#333533"), CornerRadii.EMPTY, Insets.EMPTY)));


        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);


        mainLayout.getChildren().addAll(sidebar, spacer, updateSection);
        scene1 = new Scene(mainLayout, 800, 400);


//***********************************************Layout 2********************************************************************


        //Left Section
        //Left of the Screen
        VBox leftSection = new VBox(20);
        leftSection.setPadding(new Insets(20));
        leftSection.setAlignment(Pos.CENTER);
        leftSection.setBackground(new Background(new BackgroundFill(Color.web("#333533"), CornerRadii.EMPTY, Insets.EMPTY)));


        //Buttons for setting name description and saving flashcard sets

        Button setNameBtn = new Button("Set Name");
        Button setDescBtn = new Button("Set Description");
        Button saveSetBtn = new Button("Save Set");


        String buttonStyle = "-fx-background-color: #242423; -fx-text-fill: #f5cb5c; -fx-font-size: 18px; -fx-padding: 10; -fx-background-radius: 10px;";
        setNameBtn.setStyle(buttonStyle);
        setDescBtn.setStyle(buttonStyle);
        saveSetBtn.setStyle(buttonStyle);

        leftSection.getChildren().addAll(setNameBtn, setDescBtn, saveSetBtn);


        // Right section layout
        VBox rightSection = new VBox(20);
        rightSection.setPadding(new Insets(20));
        rightSection.setAlignment(Pos.TOP_CENTER);
        rightSection.setBackground(new Background(new BackgroundFill(Color.web("#242423"), CornerRadii.EMPTY, Insets.EMPTY)));

        // Button to add new flashcards
        Button addFlashcardBtn = new Button("Add Flashcard");
        addFlashcardBtn.setStyle("-fx-background-color: #242423; -fx-text-fill: #f5cb5c; -fx-font-size: 16px; -fx-background-radius: 5px; -fx-padding: 10;");
        rightSection.getChildren().add(addFlashcardBtn);

        addFlashcardBtn.setOnAction(e -> {
            VBox flashcardForm = createFlashcardForm();
            rightSection.getChildren().add(flashcardForm);
        });

        //This button is basically the flash card
        //Customizing the FlashCard
        newCard = new Button("Click me to flip");
        newCard.setStyle("-fx-background-color: #242423; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 20px; -fx-padding: 20; -fx-background-radius: 20px;");
        newCard.setMinWidth(400);
        newCard.setMinHeight(400);


        //The buttons to switch between flashcards
        nextBtn = new Button("Next");
        nextBtn.setStyle("-fx-background-color: #242423; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 55px;");



        prevBtn = new Button("Previous");
        prevBtn.setStyle("-fx-background-color: #242423; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 55px;");



        //Button to save the cards to the ArrayList
        Button saveCardBtn = new Button("Save the Flashcard");
        saveCardBtn.setStyle("-fx-background-color: #ffd60a; -fx-text-fill: #333533; -fx-font-weight: bold; -fx-font-size: 18px; -fx-background-radius: 10px;");

        nextBtn.setOnAction(e-> showNextCard());
        prevBtn.setOnAction(e-> showPreviousCard());
        newCard.setOnAction(e-> flipCard());

        // Main layout for the page
        HBox mainLayout2 = new HBox(20);
        mainLayout2.setPadding(new Insets(20));
        mainLayout2.setBackground(new Background(new BackgroundFill(Color.web("#333533"), CornerRadii.EMPTY, Insets.EMPTY)));

        mainLayout2.getChildren().addAll(leftSection, rightSection);
        scene2 = new Scene(mainLayout2, 900, 600);


        window.setScene(scene1);
        window.show();


    }
    private VBox createFlashcardForm() {
        // Create a single flashcard input form
        VBox flashcardForm = new VBox(10);
        flashcardForm.setPadding(new Insets(10));
        flashcardForm.setBackground(new Background(new BackgroundFill(Color.web("#dbe4dc"), CornerRadii.EMPTY, Insets.EMPTY)));
        flashcardForm.setStyle("-fx-background-radius: 10px;");

        TextField termField = new TextField();
        termField.setPromptText("Term");
        TextField defField = new TextField();
        defField.setPromptText("Definition");

        Button saveCardBtn = new Button("Save");
        saveCardBtn.setStyle("-fx-background-color: #242423; -fx-text-fill: #f5cb5c; -fx-font-size: 16px; -fx-background-radius: 5px;");

        termField.setStyle("-fx-background-color: #ffffff; -fx-padding: 5; -fx-font-size: 14px; -fx-background-radius: 5px;");
        defField.setStyle("-fx-background-color: #ffffff; -fx-padding: 5; -fx-font-size: 14px; -fx-background-radius: 5px;");

        flashcardForm.getChildren().addAll(termField, defField, saveCardBtn);

        //These are the action methods for the buttons
        saveCardBtn.setOnAction(e->{
            FlashCard flashCard = new FlashCard(termField.getText(), defField.getText());
            flashcards.add(flashCard);
            if(termField.getText().isEmpty()){
                termField.setStyle("-fx-background-color: #FF0000;");
            }else{
                termField.setStyle("-fx-background-color: #f5cb5c;");
            }

            if(defField.getText().isEmpty()){
                defField.setStyle("-fx-background-color: #FF0000;");
            }else{
                defField.setStyle("-fx-background-color: #f5cb5c;");
            }
        });

        return flashcardForm;
    }
    // Method that actually determines what happens when the textfield is triggered
    private void checkAndToggleFlashing(TextField termField, TextField defField, VBox layout2) {
        if (!termField.getText().isEmpty() || !defField.getText().isEmpty()) {
            //play the effect if there is not text for term or definition
            flashTimeline.play();
        } else {
            flashTimeline.stop();
            layout2.setBackground(new Background(new BackgroundFill(Color.web("#333533"), CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }
    //Shows the next card by increasing the index of the FlashCards array list
    private void showNextCard() {
        if (!flashcards.isEmpty() && currentIndex < flashcards.size() - 1) {
            currentIndex++;
            newCard.setText("Click to flip");
        }
    }
    //Shows the previous card by decreasing the index of the FlashCards array list
    private void showPreviousCard() {
        if (!flashcards.isEmpty() && currentIndex > 0) {
            currentIndex--;
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


