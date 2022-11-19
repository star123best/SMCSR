import java.util.*;
public class Gambilng{
    public static void main (String args[]){
        /*NOTE: TRY TO FIND HOW TO MAKE THE TEXT SHOW UP INDIVIDUALLY AND NOT ALL AT THE SAME TIME*/
        //This project is about a gambling site I invented called Avsat.
        
        Scanner s = new Scanner(System.in);
        
        System.out.println("Welecome to Avzat, a gambling site you can surely trust.");
        //Verification process
        System.out.println("");
        System.out.println("This is a test to verify that you are not a robot.");
        System.out.println("If you select an invalid number or a number that isn't written here, you will have to re-start the website, so be careful!");
        System.out.println("");
        System.out.println("What is the largest number?");
        System.out.println("1, 15, 300, 1.00007");
        float o = s.nextFloat();
        if(o != 300){
            System.out.println("INCORRECT, YOU WILL HAVE TO RESATRT");
        } else {
            System.out.println("Access Granted :) \n");
   /* In this part, the code is tellng you to create an account,
   by asking for the password and the username.*/
            System.out.println("Please create a username");
            String u = s.next();
            System.out.println("Your password is Saved!: " +u);
            System.out.println("Please create a password");
            String p = s.next();
            System.out.println("Your username is Saved!: " +p);
            // You are entering your "bank"(because it invented) pin so money can be tranferred easily
            System.out.println("Before we start, you will have to input");
            System.out.println("your Bank card details so we can transfare and you can input the money.");
            System.out.println("");
            String b = s.next();
            System.out.println("Saved!");
            System.out.println("");
            // This is asking you if you want to gamble or log out by pressing 1 or 2.
            System.out.println("Now everything is ready, do you want to gamble(1) or just leave(2)?");
            int q = 0;
            int choice = s.nextInt();
            if(choice == 1){
                q++;
            }
            else if(choice == 2){
                System.out.println("We hope to see you again soon.");
            }
            if (q == 1){
                //Here starts the gambling code.
                System.out.println("");
                System.out.println("[---------------------------------------------------------------------------------------------]");
                System.out.println("-So this this how the gambling system works,");
                System.out.println(" your going to bet an amount of money and there will be a Random Number Generator");
                System.out.println(" that generates two numbers from 1 - 1000.");
                System.out.println(" If it's a tie (The two numbers are the same), you will be refunded the exact amount you bet");
                System.out.println(" but if your number doesnt match you will lose your money.");
                System.out.println(" If you are lucky enough and your/ number is larger that the opponents, your money will double.");
                System.out.println(" If you are in team 1 this is what you will see YOU:OPPONENT");
                System.out.println(" If you are in team 2 this is what you will see OPPONENT:YOU");
                System.out.println("[---------------------------------------------------------------------------------------------]");
                System.out.println("");
                System.out.println("How much money do you want to bet?");
                int btng = s.nextInt();;
                if (500 > btng){
     /* #1 If you bet a very high number, the code
     the code will ask you if you are sure
     you want to bet that amount so if you lose,
     you wont lose a lot of money. */
                    System.out.println("Which team would you like to be? (1 - Team #1, 2 - Team #2)");
                    int l = s.nextInt();
                    if (l == 1){
                        System.out.println("Are you sure you want to bet £" +btng+ "?");
                        System.out.println("(1:Yes)(2:No) [If you select the option No, you will have to restart]");
                        int n = s.nextInt();
                        if (n == 1){
                            System.out.println("£ " +btng);
                            System.out.println("Let's try your luck:");
                            // creating a random number generator.
                            Random rand = new Random();
                            int num1 = rand.nextInt(1001);
                            int num2 = rand.nextInt(1001);
                            System.out.println(num1+ ":" +num2);
                            if(num1 > num2){
                                System.out.println("You won! £" + (btng * 2));
                                System.out.println("");
                                System.out.println("");
                                System.out.println ("Recap");
                                System.out.println ("Team"+"\t\t"+"Bet"+"\t"+"     "+"Outcome");
                                System.out.println ("-------------------------------------");
                                System.out.println ("1"+"\t\t"+ btng +"\t\t"+"Win"+"\t");
                            }
                            else if(num1 < num2){
                                System.out.println("You lost, try again next time -£" + btng);
                                System.out.println("");
                                System.out.println("");
                                System.out.println ("Recap");
                                System.out.println ("Team"+"\t\t"+"Bet"+"\t"+"     "+"Outcome");
                                System.out.println ("-------------------------------------");
                                System.out.println ("1"+"\t\t"+ btng +"\t\t"+"Loss"+"\t");
                            }
                            else if(num1 == num2){
                                System.out.println("Tie, you were refunded " + btng);
                                System.out.println("");
                                System.out.println("");
                                System.out.println ("Recap");
                                System.out.println ("Team"+"\t\t"+"Bet"+"\t"+"     "+"Outcome");
                                System.out.println ("-------------------------------------");
                                System.out.println ("1"+"\t\t"+ btng +"\t\t"+"Tie"+"\t");
                            }
                        }
                        else if (n== 2){
                            System.out.println("Bye");
                        }
                    }
                    System.out.println("Fired");
                    if (l == 2){
                        System.out.println("Are you sure you want to bet £" +btng+ "?");
                        System.out.println("(1:Yes)(2:No) [If you select the option No, you will have to restart]");
                        int n = s.nextInt();
                        if (n == 1){
                            System.out.println("£ " +btng);
                            System.out.println("Let's try your luck:");
                            // creating a random number generator.
                            Random rand = new Random();
                            int num1 = rand.nextInt(1001);
                            int num2 = rand.nextInt(1001);
                            System.out.println(num1+ ":" +num2);
                            if(num2 > num1){
                                System.out.println("You won! £" + (btng * 2));
                                System.out.println("");
                                System.out.println("");
                                System.out.println ("Recap");
                                System.out.println ("Team"+"\t\t"+"Bet"+"\t"+"     "+"Outcome");
                                System.out.println ("-------------------------------------");
                                System.out.println ("2"+"\t\t"+ btng +"\t\t"+"Win"+"\t");
                            }
                            else if(num2 < num1){
                                System.out.println("You lost, try again next time -£" + btng);
                                System.out.println("");
                                System.out.println("");
                                System.out.println ("Recap");
                                System.out.println ("Team"+"\t\t"+"Bet"+"\t"+"     "+"Outcome");
                                System.out.println ("-------------------------------------");
                                System.out.println ("2"+"\t\t"+ btng +"\t\t"+"Loss"+"\t");
                            }
                            else if(num2 == num1){
                                System.out.println("Tie, you were refunded " + btng);
                                System.out.println("");
                                System.out.println("");
                                System.out.println ("Recap");
                                System.out.println ("Team"+"\t\t"+"Bet"+"\t"+"     "+"Outcome");
                                System.out.println ("-------------------------------------");
                                System.out.println ("2"+"\t\t"+ btng +"\t\t"+"Tie"+"\t");
                            }
                        }
                        else if (n == 1){
                            System.out.println("Bye");
                        }
                    }
                }
                if(btng > 500){
                    // #1 Same goes to this one
                    System.out.println("Which team would you like to be? (1 - Team #1, 2 - Team #2)");
                    int e = s.nextInt();
                    if (e == 1){
                        System.out.println("Are you sure you want to bet £" +btng+ "?");
                        System.out.println("(1:Yes)(2:No) [If you select the option No, you will have to restart]");
                        int l = s.nextInt();
                        if (l == 1){
                            System.out.println("£ " +btng);
                            System.out.println("Let's try your luck:");
                            // creating a random number generator.
                            Random rand = new Random();
                            int num1 = rand.nextInt(1001);
                            int num2 = rand.nextInt(1001);
                            System.out.println(num1+ ":" +num2);
                            if(num1 > num2){
                                System.out.println("You won! £" + (btng * 2));
                                System.out.println("");
                                System.out.println("");
                                System.out.println ("Recap");
                                System.out.println ("Team"+"\t\t"+"Bet"+"\t"+"     "+"Outcome");
                                System.out.println ("-------------------------------------");
                                System.out.println ("1"+"\t\t"+ btng +"\t\t"+"Win"+"\t");
                            }
                            else if(num1 < num2){
                                System.out.println("You lost, try again next time -£" + btng);
                                System.out.println("");
                                System.out.println("");
                                System.out.println ("Recap");
                                System.out.println ("Team"+"\t\t"+"Bet"+"\t"+"     "+"Outcome");
                                System.out.println ("-------------------------------------");
                                System.out.println ("1"+"\t\t"+ btng +"\t\t"+"Loss"+"\t");
                            }
                            else if(num1 == num2){
                                System.out.println("Tie, you were refunded " + btng);
                                System.out.println("");
                                System.out.println("");
                                System.out.println ("Recap");
                                System.out.println ("Team"+"\t\t"+"Bet"+"\t"+"     "+"Outcome");
                                System.out.println ("-------------------------------------");
                                System.out.println ("1"+"\t\t"+ btng +"\t\t"+"Tie"+"\t");
                            }
                        }
                        else if (1 == 2){
                            System.out.println("Bye");
                        }
                    }
                    else if (1 == 2){
                        System.out.println("Are you sure you want to bet £" +btng+ "?");
                        System.out.println("(1:Yes)(2:No) [If you select the option No, you will have to restart]");
                        int n = s.nextInt();
                        if (n == 1){
                            System.out.println("£ " +btng);
                            System.out.println("Let's try your luck:");
                            // creating a random number generator.
                            Random rand = new Random();
                            int num1 = rand.nextInt(1001);
                            int num2 = rand.nextInt(1001);
                            System.out.println(num1+ ":" +num2);
                            if(num2 > num1){
                                System.out.println("You won! £" + (btng * 2));
                                System.out.println("");
                                System.out.println("");
                                System.out.println ("Recap");
                                System.out.println ("Team"+"\t\t"+"Bet"+"\t"+"     "+"Outcome");
                                System.out.println ("-------------------------------------");
                                System.out.println ("2"+"\t\t"+ btng +"\t\t"+"Win"+"\t");
                            }
                            else if(num2 < num1){
                                System.out.println("You lost, try again next time -£" + btng);
                                System.out.println("");
                                System.out.println("");
                                System.out.println ("Recap");
                                System.out.println ("Team"+"\t\t"+"Bet"+"\t"+"     "+"Outcome");
                                System.out.println ("-------------------------------------");
                                System.out.println ("2"+"\t\t"+ btng +"\t\t"+"Loss"+"\t");
                            }
                            else if(num2 == num1){
                                System.out.println("Tie, you were refunded " + btng);
                                System.out.println("");
                                System.out.println("");
                                System.out.println ("Recap");
                                System.out.println ("Team"+"\t\t"+"Bet"+"\t"+"     "+"Outcome");
                                System.out.println ("-------------------------------------");
                                System.out.println ("2"+"\t\t"+ btng +"\t\t"+"Tie"+"\t");
                            }
                        }
                        else if (n == 1){
                            System.out.println("Bye");
                        }
                    }
                }
            }
        }
    }
}