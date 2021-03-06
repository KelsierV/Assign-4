import java.util.*;
import java.io.*;
public class Payroll extends Employee
{
    private ArrayList<Employee> list;
    private int numPeople;
    private int empCount;
    private Scanner file;

    public Payroll ()
    {
        empCount = 0;
        list = new ArrayList<Employee>();
        numPeople = 0;
    }    

    /**
     *  Loads information from user text file
     */
    public boolean loadData() throws IOException
    {
        String name;
        String fileName;
        String employeeNum;
        String department;
        char type;
        final double minWage = 9.95;
        double payRate;
        double hoursWorked;
        double yearlySalary;
        int weeksWorked;
        double weeklySalary;
        double weeklySales;
        double yearlySales;
        double commission;       
        int i = 0;
        int line = 1;
        boolean valid = false;
        int exit = 0;

        Scanner input = new Scanner (System.in);

        System.out.println("Enter file name to load: ");

        while (valid == false) {
            try {
                fileName = input.nextLine();
                file = new Scanner (new File(fileName));
                valid = true;
            }
            catch (FileNotFoundException a) {
                exit++;
                System.out.println("Invalid file name. " + exit + " of 3 tries attempted.");
                if (exit == 3)
                {
                    System.out.println("System will now shut down due to repeated failures.");
                    System.exit(0);
                }
            }
        }

        while (file.hasNext()) {
            try {
                Employee emp1;
                String empRaw = file.nextLine();
                String[] empInfo = new String [9];
                empInfo = empRaw.split(" ");

                name = empInfo[0];
                employeeNum = empInfo[1];
                department = empInfo[2];
                type = empInfo[3].toUpperCase().charAt(0);

                switch (type)
                {
                    case 'H':
                    if (empInfo.length == 6)
                    {
                        payRate = Double.parseDouble(empInfo[4]);
                        hoursWorked = Double.parseDouble(empInfo[5]);
                        if (payRate < minWage)
                        {
                            System.out.print("Line " + line + ": Hourly employee " + name + " cannot work less than minimum wage. ");
                            while (payRate < minWage)
                            {
                                System.out.println("Input correct amount: ");
                                payRate = input.nextDouble();
                            }                            
                        }
                        else if (hoursWorked > 70)
                        {
                            System.out.print("Line " + line + ": Hourly employee " + name + " cannot work more than 70 hours. ");
                            while (hoursWorked > 70 || hoursWorked < 0)
                            {
                                System.out.println("Input correct amount (0 to 70): ");
                                hoursWorked = input.nextDouble();
                            }                            
                        }
                        else if (hoursWorked < 0)
                        {
                            System.out.print("Line " + line + ": Hourly employee " + name + " cannot work less than 0 hours. ");
                            hoursWorked = correctAnswer(hoursWorked);

                        }
                        emp1 = new Hourly(name, employeeNum, department, payRate, hoursWorked);
                        list.add(emp1);

                        System.out.println("Line " + line + ": is a valid transaction. " + name + " (" + type + ")" + " was added. ");
                        i++;
                    }
                    else if (empInfo.length < 6)
                    {
                        System.out.println("Line " + line + ": is an invalid transaction. Information is missing in file.");
                    }
                    else if (empInfo.length > 6)
                    {
                        System.out.println("Line " + line + ": is an invalid transaction. Extra information is in file.");
                    }
                    break;

                    case 'S':
                    if (empInfo.length == 5)
                    {
                        yearlySalary = Double.parseDouble(empInfo[4]);
                        if ((yearlySalary < 24000) || (yearlySalary > 480000))
                        {
                            System.out.print("Line " + line + ": Salary employee " + name + " cannot have a yearly salary of $" + yearlySalary + " . ");
                            while ((yearlySalary < 24000) || (yearlySalary > 480000))
                            {
                                System.out.println("Input correct amount ($24000 to $480000): ");
                                yearlySalary = input.nextDouble();
                            }
                        }
                        emp1 = new Salary(name, employeeNum, department, yearlySalary);
                        list.add(emp1);

                        System.out.println("Line " + line + ": is a valid transaction. " + name + " (" + type + ")" + " was added. ");
                        i++;
                    }
                    else if (empInfo.length < 5)
                    {
                        System.out.println("Line " + line + ": is an invalid transaction. Information is missing in file.");
                    }
                    else if (empInfo.length > 5)
                    {
                        System.out.println("Line " + line + ": is an invalid transaction. Extra information is in file.");
                    }
                    break;

                    case 'C':
                    if (empInfo.length == 9)
                    {
                        weeksWorked = Integer.parseInt(empInfo[4]);
                        if (weeksWorked < 0)
                        {
                            System.out.print("Line " + line + ": Commission employee " + name + " cannot have worked less than 0 weeks. ");
                            weeksWorked = (int)correctAnswer(weeksWorked);
                        }
                        weeklySalary = Double.parseDouble(empInfo[5]);
                        if (weeklySalary < 400)
                        {
                            System.out.print("Line " + line + ": Commission employee " + name + " cannot have a weekly salary of $" + weeklySalary + ". ");
                            while (weeklySalary < 400)
                            {
                                System.out.println("Input correct amount (greater or equal to $400): ");
                                weeklySalary = input.nextDouble();
                            }
                        }
                        weeklySales = Double.parseDouble(empInfo[6]);
                        if (weeklySales < 0)
                        {
                            System.out.print("Line " + line + ": Commission employee " + name + " cannot have negative weekly sales. ");
                            weeklySales = correctAnswer(weeklySales);
                        }
                        yearlySales = Double.parseDouble(empInfo[7]);
                        if (yearlySales < 0)
                        {
                            System.out.print("Line " + line + ": Commission employee " + name + " cannot have negative yearly sales. ");
                            yearlySales = correctAnswer(yearlySales);
                        }
                        commission = Double.parseDouble(empInfo[8]);
                        if (commission > 20)
                        {
                            System.out.print("Line " + line + ": Commission employee " + name + " cannot have a commission rate of " + commission + "%. ");
                            while ((commission > 20) || (commission < 0))
                            {
                                System.out.println("Input correct amount (less than 20%): ");
                                commission = input.nextDouble();
                            }
                        }
                        emp1 = new Commission(name, employeeNum, department, weeksWorked, weeklySalary, weeklySales, yearlySales, commission);
                        list.add(emp1);

                        System.out.println("Line " + line + ": is a valid transaction. " + name + " (" + type + ")" + " was added. ");
                        i++;
                    }
                    else if (empInfo.length < 9)
                    {
                        System.out.println("Line " + line + ": is an invalid transaction. Information is missing in file.");
                    }
                    else if (empInfo.length > 9)
                    {
                        System.out.println("Line " + line + ": is an invalid transaction. Extra information is in file.");
                    }
                    break;

                    default:
                    System.out.println("Line " + line + ": is an invalid transaction. " + name + " has an invalid employee type.");
                    file.nextLine();
                    break;
                }              
            }
            catch (NoSuchElementException a) {
                System.out.println("Invalid input. Proceeding to next line.");
            }  
            catch (NumberFormatException c) {
                System.out.println("Line " + line + ": is an invalid transaction. There is erroneous data.");
            }  
            line++;
        }
        numPeople = i;
        file.close();
        return valid;
    }

    private double correctAnswer(double num)
    {
        Scanner input = new Scanner (System.in);
        while (num < 0)
        {
            System.out.println("Input correct amount: ");
            num = input.nextDouble();
        }
        return num;
    }

    /**
     * Validates employee number and returns employee if found
     */
    private boolean validateID(String num)
    {
        empCount = 0;
        int i = 0;
        boolean found = false;
        Employee emp = new Employee();

        emp.setEmployeeNumber(num);

        while(i < numPeople)
        {
            empCount++;
            if (list.get(i).getEmployeeNumber().equals(num) == true)
            {
                found = true;
                empCount = i;
                return found;
            }
            i++;
        }

        return found;
    }

    public void addEmployee(String num) throws IOException
    {
        if(validateID(num) != false)
        {
            System.out.println("Employee cannot be created. Employee already exists."); 
        }
        else    
        {
            System.out.println("Employee was not found. The employee will be created.");
            try {
                newEmployeeInfo(num);
            }
            catch (Exception a)
            {
                System.out.println("Unexpected exception: " + a + ". Returning to main menu.");
            }
        }
    }

    /**
     * Removes employee once user confirms prompt
     */
    public void removeEmployee(String num)
    {
        char verify = '\0';      

        Scanner scan = new Scanner (System.in);        
        if (validateID(num) == true)
        {
            System.out.println("You are about to delete " + list.get(empCount).getName() + " ID: " + list.get(empCount).getEmployeeNumber() + ".\n" +
                "Enter 'Y' to delete or press any key to cancel");
            verify = scan.nextLine().toUpperCase().charAt(0);
            if (verify == 'Y')
            {
                list.remove(empCount);
                numPeople--;
                System.out.println("Employee: " + num + " was removed.");
            }
            else
            {
                System.out.println(getName() + " will not be removed.");
            }
        }
        else 
        {
            System.out.println("Employee could not be found.");  
        }
        System.out.println("Returning to Main Menu.");  
    }

    /**
     * Requests user for input on new employee 
     */
    public Employee newEmployeeInfo(String num) throws IOException
    {
        String name = "";
        String empNum = "";
        String dept = "";
        char type = '\0';
        //Hourly
        double pay = 0;
        double hoursW = 0;
        final double minWage = 9.95;
        //Salary
        double yearSal = 0;
        //Commission
        int numWeeks = 0;
        double weekSal = 0;
        double wSales = 0;
        double ySales = 0;
        double comm = 0;
        boolean check = false;

        Employee emp = null;
        Scanner scan = new Scanner(System.in);

        empNum = num;
        System.out.println("Enter Employee Name: ");
        name = scan.nextLine();
        System.out.println("Enter Department: ");
        dept = scan.nextLine();        

        while (emp == null)
        {
            while (type == '\0')
            {
                try{
                    System.out.println("Enter employee type (H - Hourly, S - Salary, C - Commission): ");
                    type = scan.nextLine().toUpperCase().charAt(0);
                }
                catch (StringIndexOutOfBoundsException a)
                {
                    System.out.println("Please enter an employee type");
                }
            }
            if (type == 'H')
            {
                while (pay < minWage)
                {
                    try {
                        scan = new Scanner (System.in);
                        System.out.println("Enter pay rate (min $9.95): ");
                        pay = scan.nextDouble();
                        if (pay < minWage)
                        {
                            System.out.println("Invalid wage amount.");
                        }
                    }
                    catch (InputMismatchException a)
                    {
                        System.out.println("Invalid input.");                    
                    }
                }
                //System.out.println("Enter hours worked this week (max 70): ");
                while (check == false)
                {
                    try {
                        scan = new Scanner (System.in);
                        System.out.println("Enter hours worked this week (max 70): ");
                        hoursW = scan.nextDouble();
                        if (hoursW > 70 || hoursW < 0)
                        {
                            System.out.println("Invalid amount of hours worked.");
                            hoursW = 0;
                        }
                        else
                        {
                            check = true;
                        }
                    }
                    catch (InputMismatchException a)
                    {
                        System.out.println("Invalid input.");                    
                    }
                }
                emp = new Hourly(name, empNum, dept, pay, hoursW);

                System.out.println("Employee created successfully!"); 
            }
            else if (type == 'S')
            {
                //System.out.println("Enter yearly salary amount (between $24,000 to $480,000): ");
                while (check == false)
                {
                    try {
                        scan = new Scanner (System.in);
                        System.out.println("Enter yearly salary amount (between $24,000 to $480,000): ");
                        yearSal = scan.nextDouble();
                        if ((yearSal >= 24000) && (yearSal <= 480000))
                        {
                            check = true;
                        }
                        else
                        {
                            System.out.println("Invalid salary amount");
                            yearSal = 0;
                        }
                    }
                    catch (InputMismatchException a)
                    {
                        System.out.println("Invalid input.");                    
                    }
                }
                emp = new Salary(name, empNum, dept, yearSal);

                System.out.println("Employee created successfully!"); 
            }
            else if (type == 'C')
            {
                while (check == false)
                {
                    try {
                        scan = new Scanner (System.in);
                        System.out.println("Enter number of weeks worked since start of year or employment: ");
                        numWeeks = scan.nextInt();
                        System.out.println("Enter base weekly salary amount (min $400): ");
                        while (weekSal < 400)
                        {
                            weekSal = scan.nextDouble();
                            if (weekSal < 400)
                            {
                                System.out.println("Invalid wage amount.");
                            }
                        }
                        System.out.println("Enter weekly sales: ");
                        wSales = scan.nextDouble();
                        System.out.println("Enter yearly sales: ");
                        ySales = scan.nextDouble();
                        System.out.println("Enter commission rate (max 20%): ");
                        while (check == false)
                        {
                            comm = scan.nextDouble();
                            if ((comm > 20) || (comm < 0))
                            {
                                System.out.println("Invalid commission rate.");
                            }
                            else
                            {
                                check = true;
                            }
                        }
                        emp = new Commission(name, empNum, dept, numWeeks, weekSal, wSales, ySales, comm);

                        System.out.println("Employee created successfully!"); 
                    }
                    catch (InputMismatchException a)
                    {
                        System.out.println("Invalid input. Returning to beginning prompt.");                    
                    }
                }
            }
            else
            {
                System.out.println("Invalid employee type. Please try again");
                type = '\0';
            }
        }
        if (emp != null)
        {
            list.add(emp);
            numPeople++;
        }
        return emp;
    }

    public void printEmployee(String num)
    {       
        if (validateID(num) != false)
            System.out.println (list.get(empCount).printDetails());
        else
            System.out.println ("The employee number " + num + " does not exist.");
    }

    /**
     * Calculates and prints weekly salary of employee
     */
    public void weeklySalary(String num)
    {      
        double yearlySalary = 0.0;

        if (validateID(num) != false)
            System.out.println ("Weekly Salary for " + list.get(empCount).getName() + " is $" + list.get(empCount).calcWeeklySalary());
        else
            System.out.println ("The employee number " + num + " does not exist.");
    }

    /**
     * Top Seller's Club requires member to have sales greater than $1,500 per week
     */
    public void topEmployees()
    {
        int i = 0;
        boolean check = false;

        Commission com = new Commission();
        System.out.println("Top Seller Club Members:");
        while (i < numPeople)
        {
            if (list.get(i).getType() == 'C')
            {
                if (list.get(i).topSeller() == true)
                {
                    System.out.println(list.get(i).getName());
                    check = true;
                }
            }
            i++;
        }
        if (check = false)
        {
            System.out.println("No employee is a member of the top sellers club.");
        }
    }

    public void salaryReport()
    {
        int i = 0;

        System.out.println("Salary Report");
        while (i < numPeople)
        {
            System.out.print(list.get(i).getName() + "\t" + list.get(i).getEmployeeNumber() + "\t" + list.get(i).getDepartment() + " \t");

            if (list.get(i).getType() == 'H')
                System.out.print("Hourly" + "\t\t");
            else if (list.get(i).getType() == 'C')
                System.out.print("Commission" + "\t");
            else if (list.get(i).getType() == 'S')
                System.out.print("Salary" + "\t\t");

            System.out.println(list.get(i).calcWeeklySalary());
            i++;
        }        
    }

    public void endOfWeek() throws IOException
    {
        System.out.println("Executing end of week process");
        reset();
        exportData();
    }

    /**
     * Resets sales and hours worked
     */
    public void reset()
    {
        /*int i = 0;
        double sales = 0.0;
        int hours = 0;
        int placeHold = 0;

        while (i < numPeople)
        {
        if (list.get(i).getType() == 'C')
        {
        Commission com = (Commission)list.get(i);
        com.setWeeklySales(0.0);
        placeHold = com.getWeeksWorked() + 1;
        com.setWeeksWorked(placeHold);
        }
        else if (list.get(i).getType() == 'H')
        {
        Hourly hour = (Hourly)list.get(i);
        hour.setHoursWorked(0);
        }
        i++;
        }
         */
        Employee e;
        char type;

        int i = 0;

        while (i < numPeople)
        {
            e = list.get(i);
            type = list.get(i).getType();
            switch (type)
            {
                case 'H':
                e.setHoursWorked(0.0);
                break;
                case 'C':
                int numWeeks = e.getWeeksWorked() + 1;   // adds one week to the number of weeks
                e.setWeeksWorked(numWeeks);  
                double y_sales = e.getYearlySales() + e.getWeeklySales();
                e.setYearlySales(y_sales);
                e.setWeeklySales (0.0);
                case 'S':
                break;
            }
            i++;
        }
    }

    /**
     * Requests user for current week values
     * Increases weeks worked
     */
    public void newWeek()
    {
        int i = 0;
        double sales = 0.0;
        int hours = 0;

        System.out.println("New week processessing. Please enter new values.");
        while (i < numPeople)
        { 
            if (list.get(i).getType() == 'C')
            {
                Commission com = (Commission)list.get(i);
                System.out.print("Enter weekly sales for " + list.get(i).getName() + " : ");
                try {
                    Scanner input = new Scanner (System.in);
                    sales = input.nextDouble();
                    if (sales < 0){
                        System.out.println("Invalid amount. Please input a valid amount.");
                        while (sales < 0){
                            sales = input.nextDouble();
                        }
                    }
                    com.setWeeklySales(sales);
                    i++;
                }
                catch (InputMismatchException a) {
                    System.out.println("Invalid input, please input an integer.");
                }
            }            
            else if (list.get(i).getType() == 'H')
            {
                Hourly hour = (Hourly)list.get(i);
                System.out.print("Enter hours worked for " + list.get(i).getName() + " : ");
                try {
                    Scanner input = new Scanner (System.in);
                    hours = input.nextInt();
                    if ((hours < 0) || (hours > 70)){
                        System.out.println("Invalid amount. Please input a valid amount (0 to 70).");
                        while ((hours < 0) || (hours > 70)){
                            hours = input.nextInt();
                        }
                    }
                    hour.setHoursWorked(hours);
                    i++;
                }
                catch (InputMismatchException a) {
                    System.out.println("Invalid input, please input an integer.");
                }
            }
            else if (list.get(i).getType() == 'S')
            {
                i++;
            }
        }
    }

    /**
     * Exports data to new file
     */
    private void exportData() throws IOException
    {
        String confirm = "";
        String fileName;

        int i = 0;
        Scanner input = new Scanner (System.in);

        System.out.print("Exporting payroll data. Enter new file name: ");
        fileName = input.nextLine();

        if(!(fileName.substring(fileName.length() - 4)).equals(".txt"))
        {
            fileName += ".txt";
        }

        PrintWriter out = new PrintWriter (fileName);
        while (i < numPeople)
        {
            out.print(list.get(i).toString());
            i++;
            if (i < numPeople)
            {
                out.println();
            }
        }
        System.out.println("Export successful. New file is called '" + fileName + "'");
        out.close();
    }
}
