package com.mycompany.a1;

import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

public class Game extends Form {
    private GameWorld gw;

    public Game() {
        gw = new GameWorld();
        gw.init();
        play();
    }

    @SuppressWarnings("rawtypes")
	private void play() {
        Label myLabel = new Label("Enter a Command:");
        this.addComponent(myLabel);
        final TextField myTextField = new TextField();
        this.addComponent(myTextField);
        this.show();

        myTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String sCommand = myTextField.getText().trim();
                myTextField.clear();
                if(sCommand.length() != 0) {
                    // If exit confirmation is pending, accept only y/n.
                    if(gw.isExitPending()) {
                        char confirm = sCommand.charAt(0);
                        if(confirm == 'y' || confirm == 'Y') {
                            System.out.println("Exiting the game.");
                            System.exit(0);
                        } else if(confirm == 'n' || confirm == 'N') {
                            System.out.println("Exit cancelled.");
                            gw.setExitPending(false);
                        } else {
                            System.out.println("Please enter 'y' or 'n' to confirm exit.");
                        }
                        return;
                    }
                    char command = sCommand.charAt(0);
                    // If the command is a digit, treat it as a flag collision.
                    if(Character.isDigit(command)) {
                        int flagNumber = command - '0';
                        if(flagNumber >= 1 && flagNumber <= 9) {
                            gw.flagCollision(flagNumber);
                        } else {
                            System.out.println("Invalid flag number.");
                        }
                        return;
                    }
                    // When you click these keyboard commands, it will call that corresponding function, they're all in gameworld.java
                    switch (command) {
                        case 'a':
                            gw.accelerate();
                            break;
                        case 'b':
                            gw.brake();
                            break;
                        case 'l':
                            gw.turnLeft();
                            break;
                        case 'r':
                            gw.turnRight();
                            break;
                        case 'c':
                            gw.setFoodConsumptionRate();
                            break;
                        case 'f':
                            gw.collideWithFoodStation();
                            break;
                        case 'g':
                            gw.collideWithSpider();
                            break;
                        case 't':
                            gw.clockTick();
                            break;
                        case 'd':
                            gw.display();
                            break;
                        case 'm':
                            gw.map();
                            break;
                        case 'x':
                            gw.exit();
                            break;
                        default:
                            System.out.println("Invalid command.");
                            break;
                    }
                }
            }
        });
    }
}