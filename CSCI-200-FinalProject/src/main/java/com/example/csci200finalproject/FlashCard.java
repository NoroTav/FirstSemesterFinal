package com.example.csci200finalproject;

import javafx.scene.control.Button;

// Flash card Class that saves the base of the Flash Card
public class FlashCard {
//Initialize the term and definition
        private String term;
        private String definition;

        public FlashCard(String term, String definition) {
            this.term = term;
            this.definition = definition;
        }
        //Get the term to put on the flash card
        public String getTerm() {
            return term;
        }
        //Gets the definition to put on the flashcard
        public String getDefinition() {
            return definition;
        }
}
