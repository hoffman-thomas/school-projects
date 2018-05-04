// CS0401
// Blackjack
// Thomas Hoffman (tgh19)


import java.util.Scanner;
import java.io.*;

public class Blackjack {

    public static void main(String[] args) throws IOException {

        //initializes scanner keyboard
        Scanner keyboard = new Scanner(System.in);

        //Asks the user to login or create a username
        System.out.println("\n\n\t WELCOME TO BLACKJACK MAIN MENU\n");
        System.out.printf("[1] CREATE LOG IN\n[2] LOG IN\n\nENTER OPTION: ");

        //checks for invalid data type
        while(!keyboard.hasNextInt()) {
            System.out.printf("\n\nINVALID INPUT\n\n[1] CREATE LOG IN\n[2] LOG IN\n\nENTER OPTION: ");
            keyboard.next();
        }

        int newGameChoice = keyboard.nextInt();

        //Checks for invalid input for MENU
        while(newGameChoice != 1 && newGameChoice != 2 ){
          System.out.printf("\n\nINVALID INPUT\n\n[1] CREATE LOG IN\n[2] LOG IN\n\nENTER OPTION: ");

          while(!keyboard.hasNextInt()) {
              System.out.printf("\n\nINVALID INPUT\n\n[1] CREATE LOG IN\n[2] LOG IN\n\nENTER OPTION: ");
              keyboard.next();
          }

          newGameChoice = keyboard.nextInt();
        }



        //When the user decides to create a username
        if (newGameChoice == 1) {

            //Prompts user for their username
            Scanner nom = new Scanner(System.in);
            System.out.printf("\nDESIRED USERNAME: ");
            String name = nom.nextLine();

            //Writes a new file that initializes information about that user
            PrintWriter writer = new PrintWriter(name + ".txt", "UTF-8");

            //Name, Password, Money, handsPlayed, handsWon, blackjacks
            writer.println(name);
            writer.println("0");
            writer.println("50");
            writer.println("0");
            writer.println("0");
            writer.println("0");
            writer.close();

            //Continues program to login
            newGameChoice = 2;

        }

        //logs user in
        if (newGameChoice == 2) {

            System.out.println("\n\t\tBLACKJACK LOG IN\n");

            //First prompt for username entry
            Scanner nam = new Scanner(System.in);
            System.out.printf("USERNAME: ");
            String name = nam.nextLine();
            String fileName = name + ".txt";

            //checks to see if file exists
            File userFile = new File(fileName);
            boolean exists = userFile.exists();

            //When it doesnt exist, it prompts user to enter a correct username
            while (exists == false) {

                //Giver user error that their file is not found
                System.out.printf("USER DOES NOT EXIST\nUSERNAME: ");
                name = nam.nextLine();
                fileName = name + ".txt";

                //checks each iteration to see if the newly entered file exists
                userFile = new File(fileName);
                exists = userFile.exists();
            }

            //after file validation, the player class is initialized to be used thruought the program
            Player player = fileReader(fileName);

            //asks user if they would like to play a hand
            Scanner again = new Scanner(System.in);
            System.out.printf("PLAY A HAND? (Y/N) :");
            String doagain = again.next();

            while(!doagain.equals("y") && !doagain.equals("Y") && !doagain.equals("n") && !doagain.equals("N") ){
              System.out.printf("PLAY A HAND? (Y/N) :");
              doagain = again.next();
            }


            //If the user wants to play, it will enter this loop
            while (doagain.equals("y") || doagain.equals("Y")) {

                //Displays users information right aligned
                System.out.printf("\n\nNAME: %20s", player.getUsername());
                System.out.printf("\nMONEY: %19.2f", player.getMoney());
                System.out.printf("\nHANDS PLAYED: %12d", player.getHandsPlayed());
                System.out.printf("\nHANDS WON: %15d", player.getHandsWon());
                System.out.printf("\nBLACKJACKS: %14d", player.getBjCount());

                //Prompts player for bet amount
                System.out.printf("\n\nBET AMOUNT: $");


                while(!keyboard.hasNextDouble()) {
                  System.out.printf("INVALID AMOUNT\nBET AMOUNT: $");
                  keyboard.next();
                }


                double bet = keyboard.nextDouble();

                //Gets the players money and sets it = to a double called cash
                double cash = player.getMoney();

                //Checker for betting negative or <= 0 againts how much cash the user has
                while (bet > cash || bet <= 0) {

                    //Tells user there is an error and asks for a new bet amount
                    System.out.printf("INVALID AMOUNT\nBET AMOUNT: $");

                    while(!keyboard.hasNextDouble()) {
                      System.out.printf("INVALID AMOUNT\nBET AMOUNT: $");
                      keyboard.next();
                    }

                    bet = keyboard.nextDouble();

                }

                //-------BLACKJACK LOGIC---------------

                //Initile the after aces scores
                int scoreAA = 0, scoreAAdealer = 0;

                //New scoring class called scoreAfterAces
                Scoring scoreAfterAces = new Scoring();

                //Notifies user that the player is being dealt
                System.out.println("\n\n\t\tPLAYER DEAL\n");

                //Gets card 1
                int randCard = (int) (Math.random() * 52);
                Card card1 = new Card(randCard);

                //Gets card 2
                randCard = (int) (Math.random() * 52);
                Card card2 = new Card(randCard);

                //initializes raw score and number of aces to be passed into the scoring class
                int score = (card1.getFaceValue() + card2.getFaceValue());
                int playerNumAces = (card1.getAce() + card2.getAce());

                //Score after aces for the player is set
                scoreAA = scoreAfterAces.getScore(score, playerNumAces);

                //displays the cards we drew and the score after put into the scoring class
                System.out.println("CARDS: " + card1.getName() + ", " + card2.getName());
                System.out.println("SCORE: " + scoreAA);

                int bjCancelDealer = 3, cancelPlayer = 3;
                String hitStay = "K";

                if (scoreAA == 21) {
                    System.out.println("BLACKJACK!");
                    player.incBjCount();
                    player.setMoney(cash + (1.5 * bet));
                    System.out.println("YOU WON: $" + (1.5 * bet) + "0");
                    hitStay = "S";
                    bjCancelDealer = 1;
                    cancelPlayer = 1;

                } else if (scoreAA != 21) {
                    bjCancelDealer = 0;
                }

                if (cancelPlayer != 1) {
                    System.out.printf("\n[H] HIT\t [S] STAY\nENTER CHOICE: ");
                    hitStay = keyboard.next();
                }

                while (hitStay.equals("H") || hitStay.equals("h") || hitStay.equals("hit") || hitStay.equals("Hit")) {

                    System.out.println("\nPLAYER HIT !!!\n");

                    //card is drawn
                    randCard = (int) (Math.random() * 52);
                    Card hitCard = new Card(randCard);

                    //increment number of aces
                    playerNumAces = (playerNumAces + hitCard.getAce());

                    //increment score and makes a new after aces score
                    score = score + hitCard.getFaceValue();
                    scoreAA = scoreAfterAces.getScore(score, playerNumAces);

                    //displays the cards we drew and the score after put into the scoring class
                    System.out.println("CARD DEALT: " + hitCard.getName());
                    System.out.println("SCORE: " + scoreAA);

                    //Stops the loop if you reach 21
                    if (scoreAA == 21) {
                        System.out.println("21! GOOD JOB!");
                        hitStay = "S";
                        break;

                    //stops the loop if you bust
                    } else if (scoreAA > 21) {
                        System.out.println("\nBUST!");
                        hitStay = "S";
                        System.out.println("YOU LOST: $" + bet + "0");
                        player.setMoney(cash - bet);
                        bjCancelDealer = 1;
                        break;

                    //if you can still hit this gives you the option to
                    } else if (scoreAA < 21) {
                        System.out.printf("\n[H] HIT\t [S] STAY\nENTER CHOICE: ");
                        hitStay = keyboard.next();
                    }

                }

                if (bjCancelDealer == 0) {
                    System.out.println("\n\n\t\tDEALER DEAL\n");

                    randCard = (int) (Math.random() * 52);
                    Card card4 = new Card(randCard);

                    randCard = (int) (Math.random() * 52);
                    Card card5 = new Card(randCard);

                    int dealerScore = (card4.getFaceValue() + card5.getFaceValue());
                    int dealerNumAces = (card4.getAce() + card5.getAce());

                    scoreAAdealer = scoreAfterAces.getScore(dealerScore, dealerNumAces);

                    System.out.println("CARDS DEALT: " + card4.getName() + ", " + card5.getName());
                    System.out.println("SCORE: " + scoreAAdealer);

                    String dealerHit = " ";

                    //dealer hits on soft 17
                    if (scoreAAdealer == 17 && dealerNumAces >= 1) {
                        dealerHit = "H";
                    }

                    //dealer stays on hard 17
                    else if (scoreAAdealer == 17 && dealerNumAces == 0) {
                        dealerHit = "S";
                    }

                    //dealer stays between 18 and 21
                    if (scoreAAdealer >= 18 || scoreAAdealer <= 21) {
                        dealerHit = "S";
                    }

                    //dealer hits below 17
                    if (scoreAAdealer < 17) {
                        dealerHit = "H";
                    }

                    //breaks if dealer busts
                    if (scoreAAdealer > 21) {
                        dealerHit = "S";
                    }


                    while (dealerHit.equals("H")) {

                        System.out.println("\nDEALER HIT !!!\n");

                        randCard = (int) (Math.random() * 52);
                        Card card6 = new Card(randCard);

                        dealerScore = (card6.getFaceValue() + dealerScore);
                        dealerNumAces = dealerNumAces + card6.getAce();

                        scoreAAdealer = scoreAfterAces.getScore(dealerScore, dealerNumAces);

                        System.out.println("CARD DEALT: " + card6.getName());
                        System.out.println("DEALER SCORE: " + scoreAAdealer);

                        //dealer hits on soft 17
                        if (scoreAAdealer == 17 && dealerNumAces >= 1) {
                            dealerHit = "H";
                        }

                        //dealer stays on hard 17
                        else if (scoreAAdealer == 17 && dealerNumAces == 0) {
                            dealerHit = "S";
                        }

                        //dealer stays between 18 and 21
                        if (scoreAAdealer >= 18 || scoreAAdealer <= 21) {
                            dealerHit = "S";
                        }

                        //dealer hits below 17
                        if (scoreAAdealer < 17) {
                            dealerHit = "H";
                        }

                        //Break if dealer busts
                        if (scoreAAdealer > 21) {
                            dealerHit = "S";
                        }
                    }

                    //prints out results
                    System.out.println("\n\t\tRESULTS\n\nPLAYER SCORE: " + scoreAA);

                    //gives final dealer score
                    System.out.println("DEALER SCORE: " + scoreAAdealer);

                    //You beat the dealer
                    if (scoreAA > scoreAAdealer && scoreAA <= 21) {

                        System.out.println("\nYOU BEAT THE DEALER!");

                        //displays money won
                        System.out.println("YOU WON: $" + bet + "0");

                        //increments hands won
                        player.incHandsWon();

                        //modifies the player class to the new value of money
                        player.setMoney(cash + bet);

                    //you bust
                    } else if (scoreAA > 21) {
                        System.out.println("\nBUSTED!");

                        //displays money transaction
                        System.out.println("YOU LOST: $" + bet + "0");

                        //modifies the player class to the new value of money
                        player.setMoney(cash - bet);
                    }

                    if (scoreAAdealer > scoreAA && scoreAAdealer <= 21) {
                        System.out.println("\nDEALER WINS!");

                        //displays money transaction
                        System.out.println("YOU LOST: $" + bet + "0");

                        //modifies the player class to the new value of money
                        player.setMoney(cash - bet);


                    } else if (scoreAAdealer > 21 && scoreAA < 21) {
                        System.out.println("\nDEALER BUSTED, YOU WIN!");

                        //displays money transaction
                        System.out.println("YOU WON: $" + bet + "0");

                        //increments hands won
                        player.incHandsWon();

                        //modifies the player class to the new value of money
                        player.setMoney(cash + bet);
                    }

                    if (scoreAA == scoreAAdealer) {
                        System.out.println("\nPUSH!");

                        //displays money transaction
                        System.out.println("YOU LOST: $0.00");

                        //modifies the player class to the new value of money
                        player.setMoney(cash);
                    }
                }

                //increments hands played after every hands
                player.incHandsPlayed();

                //Asks player if they want to play another hand or not
                System.out.printf("\nPLAY ANOTHER HAND? (Y/N) :");
                doagain = again.next();

            }

            //Writes user data from session to file
            fileWriter(player);
        }
    }

    //reads a users file and returns player object
    public static Player fileReader(String fileName) throws IOException {

        //reads aspects of file
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        // username
        String username = bufferedReader.readLine();

        //etc
        String pass = bufferedReader.readLine();
        int password = Integer.parseInt(pass);

        String chips = bufferedReader.readLine();
        double money = Double.parseDouble(chips);

        String hands = bufferedReader.readLine();
        int handsPlayed = Integer.parseInt(hands);

        String handsW = bufferedReader.readLine();
        int handsWon = Integer.parseInt(handsW);

        String bj = bufferedReader.readLine();
        int bjCount = Integer.parseInt(bj);

        //ends sequential file reading
        bufferedReader.close();

        //sets the return object attributes
        Player obj = new Player(username, password, money, handsPlayed, handsWon, bjCount);

        //returns player object to main
        return obj;

    }

    //File saving aka file writing
    public static void fileWriter(Player player) throws IOException {

        //over writes user file with the same username
        PrintWriter writer = new PrintWriter((player.getUsername() + ".txt"), "UTF-8");

        // uses the same attributes of the player object to sequentially write to the file
        writer.println(player.getUsername());

        writer.println("1234");

        writer.println(player.getMoney());

        writer.println(player.getHandsPlayed());

        writer.println(player.getHandsWon());

        writer.println(player.getBjCount());

        //closes file writing
        writer.close();
    }
}
