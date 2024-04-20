//Creating the class
public class Person {
    //Creating the variables
    private String name;
    private String surname;
    private String email;

    //Creating a constructor
    public Person(String name, String surname, String email) {
        this.name = name; //Assigning the parameters to the variables
        this.surname = surname;
        this.email = email;
    }

    //Creating a getter for get the name
    public String getName(){
        return this.name;
    }

    //Creating a getter for get the surname
    public String getSurname(){
        return this.surname;
    }

    //Creating a getter for get the email
    public String getEmail(){
        return this.email;
    }

    //Creating a setter for set the name
    public void setName(String name){
        this.name = name;
    }

    //Creating a setter for set the surname
    public void setSurname(String surname){
        this.surname = surname;
    }

    //Creating a setter for set the email
    public void setEmail(String email){
        this.email = email;
    }

    //Creating a method for printing person information
    public void printPersonInfo() {
        //Displaying persons information
        System.out.println("Name: " + this.name);
        System.out.println("Surname: " + this.surname);
        System.out.println("Email: " + this.email);
    }
}
