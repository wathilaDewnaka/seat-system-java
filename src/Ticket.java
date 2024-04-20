import java.io.File; //Importing the File
import java.io.FileWriter; //Importing the FileWriter
import java.io.IOException; //Importing IOException

//Creating the class
public class Ticket {
    //Creating and declaring the variables
    private char row;
    private int seat;
    private int price;
    private Person person; //Declare variable named person of Person type, can hold object

    //Creating a constructor
    public Ticket(char row, int seat, int price, Person person) {
        this.row = row; //Assigning the parameters to the variables
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    //Creating a getter for get the price
    public int getPrice(){
        return this.price;
    }

    //Creating a getter for get the seat
    public int getSeat(){
        return this.seat;
    }

    //Creating a getter for get the row
    public char getRow(){
        return this.row;
    }

    //Creating a getter for get the person details (Object)
    public Person getPerson() {
        return this.person;
    }

    //Creating a setter for set the price
    public void setPrice(int price){
        this.price = price;
    }

    //Creating a setter for set the row
    public void setRow(char row){
        this.row = row;
    }

    //Creating a setter for set the seat
    public void setSeat(int seat) {
        this.seat = seat;
    }

    //Creating a setter for set the Person object
    public void setPerson(Person person) {
        this.person = person;
    }

    //Creating a method for printing ticket information and print person information
    public void printTicketInfo() {
        System.out.println("\n-------------------------------------------");
        System.out.println("Row: " + this.row + " | Seat: " + this.seat);
        System.out.println("Seat : " + this.row + "" + this.seat);
        System.out.println("Price: £" + this.price);
        person.printPersonInfo();
    }

    //Creating a method for save text file
    public void save() {
        File file = new File("Invoice"); //Checking folder named Invoice
        if (!file.exists()){ //If folder doesn't exist
            file.mkdir(); //Creating folder
        }

        //Creating a file name
        String filename = row + String.valueOf(seat) + ".txt";
        try {
            //Opening the File Writer
            FileWriter text_write = new FileWriter("Invoice\\" + filename); //Putting the text file inside the folder
            //Writing the details for the text file
            text_write.write("Ticket Information:\n");
            text_write.write("----------------------------------------\n");
            text_write.write("Row: " + this.row + " | Seat: " + this.seat + "\n");
            text_write.write("Price: £" + this.price + "\n");
            text_write.write("Name: " + this.person.getName() + "\n");
            text_write.write("Surname: " + this.person.getSurname() + "\n");
            text_write.write("Email: " + this.person.getEmail() + "\n");
            text_write.write("----------------------------------------");
            text_write.close();
        } catch (IOException e) {
            System.out.println("An error occurred. Please re-run the PlaneManagement"); //In case of error printing error in saving text file
        }
    }

    //Creating the method to delete text file
    public void delete(){
        String filename = row + String.valueOf(seat) + ".txt"; //Creating a file name
        File file = new File("Invoice\\" + filename);
        file.delete(); //Deleting the file
    }
}
