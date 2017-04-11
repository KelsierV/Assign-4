import java.util.Scanner;
import java.io.*;
public class Client
{
    public static void main(String args[]) throws IOException
    {
        // define necessary variables here
        char choice = '\0';
        boolean loadCheck = false;
        String input = "";
        Payroll pr = new Payroll();

        Scanner kb = new Scanner (System.in);
        // pla ce here the code for the processing requirements
        while(loadCheck == false)
        {
            loadCheck = pr.loadData();
        }
        
        pr.newWeek();
        
        while (choice != 'q')
        {
            showMenu();
            System.out.println ("Please enter your choice or 'Q' to exit");
            choice = kb.next().toLowerCase().charAt(0);
            kb.nextLine();

            switch (choice)
            {
                case 'a':
                System.out.println("Please enter a new employee number: ");
                input = kb.nextLine();
                pr.addEmployee(input);
                break;
                case 'i':
                System.out.println("Please enter employee number: ");
                input = kb.nextLine();
                pr.printEmployee(input);
                break;
                case 'd':
                System.out.println("Please enter employee number: ");
                input = kb.nextLine();
                pr.removeEmployee(input);
                break;
                case 's': 
                System.out.println("Please enter employee number: ");
                input = kb.nextLine();
                pr.weeklySalary(input);
                break;
                case 't':
                pr.topEmployees();
                break;
                case 'p':
                pr.salaryReport();
                break;
                case 'w': 
                pr.endOfWeek();
                break;
                case 'q':
                break;

                default:
                System.out.println ("Invalid valid choice. Please re-enter.");
            }        
        }
        System.out.println ("Thank you for using the Payroll Processing System");
    }

    //   The Payroll processing menu     
    public static void showMenu()
    {
        System.out.println("\nMENU:");
        System.out.println("A - Add a new employee");
        System.out.println("I - Print information of an individual employee");
        System.out.println("D - Remove an employee from payroll");
        System.out.println("S - calculate and print the weekly pay of an employee");
        System.out.println("T - This prints the list of Commission employees who are Top Sellers");
        System.out.println("P - prints salary report");
        System.out.println("W - End of week processing");
        System.out.println();
        System.out.println("Q - Quit the system");
    }
}
