import java.util.*;
import java.io.*;
public class Hourly extends Employee
{
    private double payRate;
    private double hoursWorked;
    private char type = 'H';

    //Constructors
    public Hourly ()
    {
        payRate = 0.0;
        hoursWorked = 0.0;
    }

    public Hourly(String id, String empNum, String dept, double pay, double hoursW)
    {
        super(id, empNum, dept);      
        payRate = pay;
        hoursWorked = hoursW;
    }

    //Accessors
    public double getPayRate()
    {
        return payRate;
    }

    public double getHoursWorked()
    {
        return hoursWorked;
    }
    
    public char getType()
    {
        return type;
    }
    //Mutators
    public void setPayRate(double pay)
    {
        payRate = pay;
    }

    public void setHoursWorked(double hoursW)
    {
        hoursWorked = hoursW;
    }
    
    public void setName(String id)
    {
        name = id;
    }
    //Helper Methods
    protected double calcWeeklySalary()
    {
        double salary = 0.0;

        if (hoursWorked <= 40)
            salary = hoursWorked * payRate;
        else if (hoursWorked > 40)
            salary = (40 * payRate) + ((hoursWorked - 40) * (payRate * 1.5));

        return salary;
    } 

    public String toString()
    {
        String result;

        result = super.toString(); 
        result = result + " " + type + " " + payRate + " " + hoursWorked;

        return result;
    }

    public void writeData(String fileLoc) throws IOException
    {
        super.writeData(fileLoc);
    }

    public String printDetails()
    {
        String result;

        result = super.printDetails();
        result = result + "Pay Rate: \t" + payRate + "\n" +
        "Hours Worked: \t" + hoursWorked + "\n";

        return result;
    }
}
