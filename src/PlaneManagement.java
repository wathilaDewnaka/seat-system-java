import java.util.InputMismatchException; //Importing the Java InputMismatchException to handle Exceptions
import java.util.Scanner; //Importing the Java Scanner class
import java.io.File;  //Importing the Java File to handle text files

//Creating the class
public class PlaneManagement {
    //Creating the scanner object
    private static final Scanner input = new Scanner(System.in);
    //Defining how much maximum seats row can have (1st row max 14 seats, 2nd row max 12 seats, 3rd row max 12 seats,4th row max 14 seats
    private static final int[] seats_per_row = {14,12,12,14};
    //Creating the 2D Array with rows
    private static final int[][] seats_row = new int[4][14]; //Initializing the created 2D array with 0's (Default it's with 0)
    //Creating object array for Tickets
    private static final Ticket[] tickets = new Ticket[52];

    //Creating the main method
    //-----------------------------------------MAIN METHOD------------------------------------------
    public static void main(String[] args){
        delete_text_files(); //Calling the method to delete all existing text files (Additional feature)

        //Displaying the welcome message for the user
        System.out.println("------------------------------------------------------");
        System.out.println("|     Welcome to the Plane Management application    |");
        System.out.println("------------------------------------------------------\n");

        //Starting a loop
        while(true) {
            displayMenu(); //Calling method to display menu

            int choice;  //Creating and declaring choice variable

            //Starting a loop to validate the choice
            while(true) {
                try {
                    //Asking for user option and storing in a variable
                    System.out.print("Please select an option: ");
                    choice = input.nextInt();
                    input.nextLine(); //Consume next line / Resetting the scanner as after int it cant take string
                    break; //Breaking out of the loop
                }

                //In case of input mismatch exception (String was entered) asking for choice again
                catch (InputMismatchException e){
                    System.out.println("\nEnter invalid input !");
                    System.out.println("Please enter numbers only");
                    System.out.println("------------------------------------------------------");
                    input.nextLine(); //Resetting the scanner as after int it cant take string
                }
            }

            //Using the switch cases to handle the user choice
            switch (choice) {
                case 1: //If user entered 1
                    buy_seat(); //Calling for buy_seat() method
                    break;
                case 2: //If user entered 2
                    cancel_seat(); //Calling for cancel_seat() method
                    break;
                case 3: //If user entered 3
                    find_first_available(); //Calling for find_first_available() method
                    break;
                case 4: //If user entered 4
                    show_seating_plan(); //Calling for show_seating_plan() method
                    break;
                case 5: //If user entered 5
                    print_tickets_info(); //Calling for print_tickets_info() method
                    break;
                case 6: //If user entered 6
                    search_ticket(); //Calling for search_ticket() method
                    break;
                case 0: //If user entered 0
                    System.out.println("\n------------------------------------------------------");
                    System.out.println("Exiting the program"); //Showing thank you message
                    System.out.println("Thank you for using this application ! ");
                    System.out.println("------------------------------------------------------\n");
                    System.exit(0); //Returning out of the main method

                default: //If the above cases are not met showing to enter proper number / choice
                    System.out.println("\nPlease enter number properly !");
                    System.out.println("Showing menu again...");
                    System.out.println("------------------------------------------------------");
            }
        }
    }

    //Creating the method for buying a seat
    private static void buy_seat(){
        boolean buy_seat = true; //Assigning a variable for true
        while(buy_seat) {
            //Creating and declaring the necessary variables
            int seat_number;
            int row_letter_number;
            String buyAgain;
            String email;

            //Displaying a menu showing Buying a seat
            System.out.println("\n------------------------------------------------------");
            System.out.println("|                Buying a seat                       |");
            System.out.println("------------------------------------------------------\n");

            //Validating and getting the row letter and seat number inputs
            row_letter_number = row_letter_valid(); //Calling method to get row letter (Converted to number 0 = A, 1 = B , 2 = C, 3 = D)
            seat_number = seat_number_valid(row_letter_number); //Calling method to get the seat number

            //Check whether the entered row letter (in number format)  and seat number is booked
            if (seats_row[row_letter_number][seat_number - 1] == 1) {
                //Displaying that seat is booked by another person
                System.out.println("------------------------------------------------------");
                System.out.println("Seat Already Booked By Another Person !!!");
            }

            //If seat is available asking for person details
            else {
                System.out.println("------------------------------------------------------");
                System.out.println("Enter person details:"); //Display enter person details

                //Asking for the users name and store in variable
                System.out.print("Name: ");
                String name = input.next().toLowerCase();
                name = name.substring(0, 1).toUpperCase() + name.substring(1); //Converting to capitalize

                //Asking for the surname and store in variable
                System.out.print("Surname: ");
                String surname = input.next().toLowerCase();
                surname = surname.substring(0, 1).toUpperCase() + surname.substring(1); //Converting to capitalize

                input.nextLine(); //Consume next line

                //Asking for the surname and store in variable
                while (true) {
                    System.out.print("Email (gmail only): ");
                    email = input.nextLine().toLowerCase(); //Converting to capitalize

                    if (email.endsWith("@gmail.com")){ //Checking whether end with gmail.com
                        break; //if true breaking
                    }
                    //Displaying the message invalid and asking input again
                    System.out.println("------------------------------------------------------");
                    System.out.println("Invalid Email Address");
                    System.out.println("Enter Personal Email Again (should end with @gmail.com)");
                    System.out.println("------------------------------------------------------\n");
                }

                //Calculating the seat price based on letter and seat number
                int price = calculate_price(seat_number); //Calling for calculate_price method in order to calculate the seat price
                char row_letter = (char) (row_letter_number + 'A'); //Converting the row letter number to Character (Using an ASCII technique)

                //Creating the details of person (Creating the object)
                Person person = new Person(name, surname, email);

                for (int i = 0; i < tickets.length; i++) { //Iterating through array of tickets
                    if (tickets[i] == null){ //Finding first null and creating object
                        tickets[i] = new Ticket(row_letter, seat_number, price, person);
                        tickets[i].save(); //Calling method of relevant index to save text files
                        break; //Breaking loop else all the null will be selected
                    }
                }
                //Displaying the price
                System.out.println("Price : £" + price);

                //Updating the array to 1 (Relevant location /index with row letter and seat number)
                //1 means the seat is booked and 0 means not booked
                seats_row[row_letter_number][seat_number - 1] = 1;

                System.out.println("Seat Booked Successfully ! "); //Displaying the user that seat booked successfully
            }

            do {
                System.out.println("------------------------------------------------------");
                System.out.print("Do you want to buy a seat again (Y/N) ? : "); //Asking whether to buy a seat again
                buyAgain = input.nextLine();
                buyAgain = buyAgain.toUpperCase();
                if(buyAgain.equals("Y") || buyAgain.equals("N") || buyAgain.equals("YES") || buyAgain.equals("NO")){
                    break; //Breaking the loop
                }
                System.out.println("Invalid input ! (Enter Y or N )"); //Displaying invalid input
            } while (true);

            System.out.println();

            if (buyAgain.equals("N") || buyAgain.equals("NO")) {
                buy_seat = false; //Exiting the loop
            }
        }
    }

    //Creating the method for canceling a seat
    private static void cancel_seat(){
        boolean cancel_seat = true; //Assigning variable for true
        while (cancel_seat) {
            //Creating and declaring the variables
            int seat_number;
            int row_letter_number;
            char row_letter;
            String cancelAgain;

            //Displaying a menu showing Cancel a seat
            System.out.println("\n------------------------------------------------------");
            System.out.println("|                 Cancel a seat                       |");
            System.out.println("------------------------------------------------------\n");

            //Validating and getting the row letter and seat number inputs
            row_letter_number = row_letter_valid(); //Calling method to get row letter (Converted to number 0 = A, 1 = B , 2 = C, 3 = D)
            seat_number = seat_number_valid(row_letter_number); //Calling method to get the seat number

            //Check whether the entered row letter (in number format)  and seat number is already available
            if (seats_row[row_letter_number][seat_number - 1] == 0) {
                //Displaying user that the seat is already available
                System.out.println("------------------------------------------------------");
                System.out.println("Seat is already available");
            } else {
                row_letter = (char) (row_letter_number + 'A'); //Getting the row letter from number
                for (int i = 0; i < tickets.length; i++) { //Iterating through array of tickets
                    //Checking whether index is not null and similar with entered row and seat number
                    if (tickets[i] != null && tickets[i].getRow() == row_letter && tickets[i].getSeat() == seat_number){
                        tickets[i].delete(); //Calling the method to delete text file  (Additional feature)
                        tickets[i] = null; //Assigning the relevant index to null
                        break;
                    }
                }
                //Updating the array to 0 because seat is canceled (Relevant location /index with row letter and seat number)
                //1 means the seat is booked and 0 means not booked
                seats_row[row_letter_number][seat_number - 1] = 0;

                System.out.println("Seat Canceled Successfully ! "); //Display seat canceled
            }

            do {
                System.out.println("------------------------------------------------------");
                System.out.print("Do you want to cancel a seat again (Y/N) ? : "); //Asking whether to cancel a seat again
                cancelAgain = input.nextLine();
                cancelAgain = cancelAgain.toUpperCase();

                if(cancelAgain.equals("Y") || cancelAgain.equals("N") || cancelAgain.equals("YES") || cancelAgain.equals("NO")){
                    break; //Breaking the loop
                }
                System.out.println("Invalid input ! (Enter Y or N )"); //Displaying invalid input
            } while (true);

            System.out.println();

            if (cancelAgain.equals("N") || cancelAgain.equals("NO")) {
                cancel_seat = false; //Exiting the loop
            }
        }
    }

    //Creating a method to show the seating plan
    private static void show_seating_plan(){
        //Creating and declaring variables
        char letter = 'A'; //Assigning A for letter variable (character)
        //Displaying show seating plan for the user
        System.out.println("\n------------------------------------------------------");
        System.out.println("|            Showing the seat plan                    |");
        System.out.println("------------------------------------------------------\n");
        System.out.print("\t\t");

        //Printing the seat numbers above seating plan
        for(int i = 1; i <= 14; i++){
            if(i > 9){ //Adjusting the space based on count
                System.out.print(i + " ");
            }
            else if(i == 8){ //Adjusting the space based on number
                System.out.print(i + "  ");
                System.out.print("\t");
            }
            else{
                System.out.print(i + "  ");
            }
        }
        System.out.println("\n");
        //Accessing the nested 2D array using nested for loops
        for(int i = 0; i < seats_row.length;i++){ //Accessing the first array
            System.out.print("\t" + letter + "\t"); //Displaying the letter of row with two tab spaces
            for(int j = 0; j < seats_per_row[i]; j++){ //Accessing the second array
                //Creating result variable and assigning O and X (If array element is 1 'X' else 'O')
                String result = (seats_row[i][j] == 0) ? "O  " : "X  ";
                //Creating a tab space if count is 8
                if(j == 8){
                    System.out.print("\t");
                }
                System.out.print(result); //Displaying the result (O or X) stored inside variable
            }
            letter++; //Increasing the letter which mean A,B,C,D ...

            //Printing an empty line
            if(i == 1){
                System.out.println("\n"); //Keep another line between B and C
            }
            else{
                System.out.println();
            }
        }
        System.out.println("\n£200 - (Seat number 1 to 5)"); //Displaying prices for user
        System.out.println("£150 - (Seat number 6 to 9)");
        System.out.println("£180 - (Seat number 10 to 14)\n");
    }


    //Creating a method to find the first available seat
    private static void find_first_available(){
        //Creating and declaring variables
        char letter = 'A'; //Assigning A for letter variable (character)

        //Displaying Showing the first available seat for the user
        System.out.println("\n------------------------------------------------------");
        System.out.println("|         Showing the first available seat            |");
        System.out.println("------------------------------------------------------\n");

        //Accessing the nested 2D array using nested for loops
        for(int i = 0; i < 4;i++){ //Accessing the first array
            for(int j = 0; j < seats_per_row[i]; j++){ //Accessing the second array
                if(seats_row[i][j] == 0){ //If available seat found show seat is available and display row letter and seat number
                    System.out.println("First available seat found at " + letter + (j + 1));
                    System.out.println("Row : " + letter + " | Seat : " + (j+1) + "\n");
                    return; //Returning out of the method in case found a seat
                }
            }
            letter++; //Increasing the letter which mean A,B,C,D ...
        }
        System.out.println("Seat not found!\n"); //Showing the user that seat is not available
    }

    //Creating a method to print all the ticket information
    private static void print_tickets_info() {
        //Creating and declaring a variable
        int totalAmount = 0; //Assigning 0 to variable

        //Displaying Printing the ticket information for the user
        System.out.println("\n------------------------------------------------------");
        System.out.println("|        Printing the ticket information              |");
        System.out.println("------------------------------------------------------\n");

        System.out.println("Tickets Information:");
        //Accessing the elements of object array
        for (Ticket ticket : tickets) {
            if (ticket != null) { //If the element of object array is not null (Null means empty)
                ticket.printTicketInfo(); //Printing ticket info by printTicketInfo() method
                totalAmount += ticket.getPrice(); //Getting the ticket price by getPrice() method and adding it to totalAmount variable to calculate total
            }
        }
        if(totalAmount == 0){ //If total is zero showing ticket information printed
            System.out.println("No ticket information's to be printed !!! ");
        }

        //After loop ends showing the user total amount
        System.out.println("\n-----------------------------------------------------");
        System.out.println("Total amount: £" + totalAmount ); //Display the total
        System.out.println("-----------------------------------------------------\n");
    }

    //Creating a method to search the tickets
    private static void search_ticket() {
        boolean search_again = true; //Assigning the variable for true
        while(search_again) {
            //Creating and declaring the variables
            int seat_number;
            int row_letter_number;
            char row_letter;
            String searchAgain;
            boolean seatAvailable; //Variable to check whether searched ticket is available or not

            //Displaying Searching the tickets for the user
            System.out.println("\n------------------------------------------------------");
            System.out.println("|            Searching the tickets                    |");
            System.out.println("------------------------------------------------------\n");

            //Validating and getting the row letter and seat number inputs
            row_letter_number = row_letter_valid(); //Calling method to get row letter (Converted to number 0 = A, 1 = B , 2 = C, 3 = D)
            seat_number = seat_number_valid(row_letter_number); //Calling method to get the seat number

            seatAvailable = true; //Assigning to true
            row_letter = (char) (row_letter_number + 'A'); //Getting the row letter from number

            for (Ticket ticket : tickets) { //Iterating through the array tickets
                //If ticket is not null and equal with relevant row and seat number printing details
                if (ticket != null && ticket.getRow() == row_letter && ticket.getSeat() == seat_number) {
                    ticket.printTicketInfo();
                    seatAvailable = false; //Assigning to true
                    break;
                }
            }

            if (seatAvailable){ //If the seat is still available show user as seat is available
                System.out.println("------------------------------------------------------");
                System.out.println("Seat is available ! ");
            }

            do {
                System.out.println("------------------------------------------------------");
                System.out.print("Do you want to search again (Y/N) ? : "); //Asking whether to search a seat again
                searchAgain = input.nextLine();
                searchAgain = searchAgain.toUpperCase();
                if(searchAgain.equals("Y") || searchAgain.equals("N") || searchAgain.equals("YES") || searchAgain.equals("NO")){
                    break; //Breaking the loop
                }
                System.out.println("Invalid input ! (Enter Y or N )"); //Displaying invalid input
            } while (true);

            System.out.println();

            if (searchAgain.equals("N") || searchAgain.equals("NO")) {
                search_again = false; //Exiting the loop
            }
        }
    }

    //----OTHER USEFUL METHODS----
    //Creating a method to display the menu
    private static void displayMenu(){
        System.out.println("******************************************************"); //Printing the menu
        System.out.println("*                  MENU OPTIONS                      *");
        System.out.println("******************************************************\n");
        System.out.println("    1) Buy a seat");
        System.out.println("    2) Cancel a seat");
        System.out.println("    3) Find first available seat");
        System.out.println("    4) Show seating plan");
        System.out.println("    5) Print tickets information and total sales");
        System.out.println("    6) Search ticket");
        System.out.println("    0) Quit");
        System.out.println("\n******************************************************");
    }

    //Creating a method to get row letter and validate it (Converts it to number also)
    private static int row_letter_valid(){
        //Creating and declaring the variables
        int row_letter_number;
        char row_letter;

        //Using a loop
        while(true) {
            //Asking the user to enter a row letter and storing in variable
            System.out.print("Please enter the row letter (A - D) : ");
            row_letter = input.next().charAt(0); //Getting the first character

            //Converting the character to uppercase
            row_letter = Character.toUpperCase(row_letter);
            row_letter_number = row_letter - 'A'; //Converting the letter for a number to access the index of arrays
            //Converted by ASCII method by reducing the ASCII value of 'A'

            //Checking whether row letter is in range
            if (!(row_letter_number >= 0 && row_letter_number <= 3)) {
                //If not, displaying the user wrong row letter
                System.out.println("Invalid Row Letter !, Please Try Again !");
                System.out.println("------------------------------------------------------\n");
            } else {
                return row_letter_number; //Returning the row letter number out of method
            }
        }
    }

    //Creating a method to get seat number and validate it
    private static int seat_number_valid(int row_letter_number) {
        //Creating and declaring the variables
        int seat_number;

        //Using a loop
        while(true) {
            //Using try catch block in order to catch exceptions
            try {
                //Asking the user to enter a seat number and storing in variable
                System.out.print("Please enter the seat number (1 - " + seats_per_row[row_letter_number] + ") : ");
                seat_number = input.nextInt();
                input.nextLine(); //Consume next line

                //Validating the seat number
                if (seat_number <= 0 || seat_number > seats_per_row[row_letter_number]) {
                    //If the entered seat number is incorrect display it to user
                    System.out.println("Invalid Seat Number !, Please Try Again");
                    System.out.println("------------------------------------------------------\n");
                    //Restarts the loop again (no need continue because of while true loop)
                } else {
                    return seat_number; //If seat number is correct returning value out of function
                }

                //In case of wrong input (Exception raised)
            } catch (InputMismatchException e) {
                //Displaying the user that user entered wrong number
                System.out.println("Invalid Seat Number !, Please Try Again");
                System.out.println("------------------------------------------------------\n");
                input.nextLine(); //Resetting the scanner
                //Restarts the loop again (no need continue because of while true loop)
            }
        }
    }

    private static void delete_text_files(){
        try{
            String defaultDirectoryPath = System.getProperty("user.dir") + "\\Invoice"; //Accessing the folder in user directory
            // Create a File object for the directory
            File directory = new File(defaultDirectoryPath);
            // Get a list of all files in the directory
            File[] files = directory.listFiles();

            // Iterate through each file in the directory
            for (File file : files) {
                // Check if the file is a text file
                if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                    file.delete(); //Deleting all text files
                }
            }
        }catch (NullPointerException e){ //In case folder doesn't exist exit out of method
            return;
        }
    }

    //Creating a method to calculate the seat price
    private static int calculate_price(int seat_number){
        //Returning the seat price
        //Calculated like if seat number is less than 5 price is 200, if seat number is less than 9 price is 150, else 100
        return (seat_number <= 5) ? 200 : (seat_number <= 9) ? 150 : 180;
    }
}
