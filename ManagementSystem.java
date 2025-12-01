import java.util.ArrayList;
import java.util.Scanner;

/*
 * Main class for the University Management System project.
 * It handles:
 *  - Displaying a menu
 *  - Reading user input
 *  - Creating Student / FullTimeEmployee / PartTimeEmployee objects
 *  - Storing all objects in a single ArrayList<Person> using polymorphism
 */
public class ManagementSystem
{

    public static void main(String[] args)
    {
        // Scanner object to read input from the keyboard
        Scanner input = new Scanner(System.in);

        /*
         * ArrayList is used instead of a normal array because:
         *  - The number of records is not fixed
         *  - We can add objects dynamically at runtime
         * It stores references of type Person (parent class),
         * so we can keep both Students and Employees in one list.
         */
        ArrayList<Person> records = new ArrayList<>();

        // requirement 3) Sample data: predefined list of courses added during initialization
        String[] sampleCourses = {"Math", "Programming", "Physics", "English"};


        // Infinite loop to keep showing the menu until the user chooses Exit
        while (true) {
            System.out.println("\n===== University Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Add Full-time Employee");
            System.out.println("3. Add Part-time Employee");
            System.out.println("4. Display All Records");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            // Read the user choice
            int choice = input.nextInt();
            // Consume the remaining newline character after nextInt()
            input.nextLine();

            switch (choice) {

                // ----------- OPTION 1: ADD STUDENT -----------
                case 1:
                    System.out.print("Enter student name: ");
                    String sName = input.nextLine();

                    int sId;
                    while (true) 
                    {
                        System.out.print("Enter student ID: ");
                        sId = input.nextInt();

                        // check duplicate ID
                        if (idExists(records, sId)) {
                            System.out.println("This ID already exists! Please enter a different ID.");
                        } else {
                            break;  // ID is unique, continue
                        }
                    }


                    // Show available courses
                    System.out.println("Choose a course:");
                    for (int i = 0; i < sampleCourses.length; i++) {
                        System.out.println((i + 1) + ". " + sampleCourses[i]);
                    }
                    System.out.print("Course number: ");
                    int cIndex = input.nextInt() - 1;  // -1 to convert from (1..n) to (0..n-1)

                    // Simple validation to make sure course index is inside the array range
                    if (cIndex < 0 || cIndex >= sampleCourses.length) {
                        System.out.println("Invalid course! Student not added.");
                        break;
                    }

                    // Read grade and validate it between 0 and 100
                    System.out.print("Enter grade (0-100): ");
                    double grade = input.nextDouble();
                    while (grade < 0 || grade > 100) {
                        System.out.print("Invalid! Enter grade (0-100): ");
                        grade = input.nextDouble();
                    }

                    /**
                     * Create a new Student object and add it to the ArrayList.
                     * Upcasting happens automatically here:
                     *  - records is ArrayList<Person>
                     *  - Student "is a" Person (inheritance)
                     * So the Student reference is stored as type Person.
                     */
                    records.add(new Student(sName, sId, sampleCourses[cIndex], grade));
                    System.out.println("Student added successfully!");
                    break;

                // ----------- OPTION 2: ADD FULL-TIME EMPLOYEE -----------
                case 2:
                    System.out.print("Enter employee name: ");
                    String fName = input.nextLine();

                    int fId;
                    while (true) 
                    {
                        System.out.print("Enter employee ID: ");
                        fId = input.nextInt();
                        if (idExists(records, fId)) {
                            System.out.println("This ID already exists! Please enter a different ID.");
                        } else {
                            break;
                        }
                    }


                    // Read salary and validate to be non-negative
                    System.out.print("Enter monthly salary: ");
                    double salary = input.nextDouble();
                    while (salary < 0) {
                        System.out.print("Invalid! Enter salary (>=0): ");
                        salary = input.nextDouble();
                    }

                    // Create a FullTimeEmployee object (subclass of Employee -> Person)
                    records.add(new FullTimeEmployee(fName, fId, salary));
                    System.out.println("Full-time employee added!");
                    break;

                // ----------- OPTION 3: ADD PART-TIME EMPLOYEE -----------
                case 3:
                    System.out.print("Enter employee name: ");
                    String pName = input.nextLine();

                    int pId;
                    while (true) 
                    {
                        System.out.print("Enter employee ID: ");
                        pId = input.nextInt();
                        if (idExists(records, pId)) {
                            System.out.println("This ID already exists! Please enter a different ID.");
                        } else {
                            break;
                        }
                    }

                    // Read hourly rate and validate
                    System.out.print("Enter hourly rate: ");
                    double rate = input.nextDouble();
                    while (rate < 0) {
                        System.out.print("Invalid! Enter hourly rate (>=0): ");
                        rate = input.nextDouble();
                    }

                    // Read hours worked and validate
                    System.out.print("Enter hours worked: ");
                    int hours = input.nextInt();
                    while (hours < 0) {
                        System.out.print("Invalid! Enter hours (>=0): ");
                        hours = input.nextInt();
                    }

                    // Create a PartTimeEmployee object
                    records.add(new PartTimeEmployee(pName, pId, rate, hours));
                    System.out.println("Part-time employee added!");
                    break;

                // ----------- OPTION 4: DISPLAY ALL RECORDS -----------
                case 4:
                                    
                    System.out.println("DEBUG: records size = " + records.size());
                    if (records.isEmpty()) {
                        System.out.println("No records found.");
                    } else {
                        System.out.println("\n===== All Records =====");
                        for (Person p : records) {
                            p.displayDetails();
                            System.out.println("----------------------");
                        }
                    }
                    break;

                    // if (records.isEmpty())
                    // {
                    //     System.out.println("No records found.");
                    // } else {
                    //     System.out.println("\n===== All Records =====");
                    //     /*
                    //      * Enhanced for-loop to go through all Person references in the ArrayList.
                    //      * Polymorphism:
                    //      *   - p may refer to a Student, FullTimeEmployee, or PartTimeEmployee.
                    //      *   - The correct version of displayDetails() is chosen at runtime
                    //      *     (dynamic binding / late binding).
                    //      */
                    //     for (Person p : records) {
                    //         p.displayDetails();      // Polymorphic method call
                    //         System.out.println("----------------------");
                    //     }
                    // }
                    // break;

                // ----------- OPTION 5: EXIT PROGRAM -----------
                case 5:
                    System.out.println("Exiting...");
                    return;  // Breaks out of main and ends the program

                // ----------- INVALID OPTION -----------
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    /*
     * Abstract parent class Person.
     * It contains common attributes (name, id) that are shared by
     * all types of persons in the system (students and employees).
     *
     * Being abstract means:
     *  - We cannot create objects of type Person directly.
     *  - It can contain abstract methods that must be overridden
     *    by subclasses.
     */
    public static abstract class Person
    {
        // Protected so subclasses can access them directly
        protected String name;
        protected int id;

        public Person(String name, int id)
        {
            this.name = name;
            this.id = id;
        }

        public String getName()
        {
            return name;
        }

        public int getId()
        {
            return id;
        }

        /*
         * Abstract method:
         *  - No body here
         *  - Every subclass (Student, FullTimeEmployee, PartTimeEmployee)
         *    must provide its own implementation.
         * This is an example of polymorphism (same method name, different behavior).
         */
        public abstract void displayDetails();
    }

    /*
     * Student class (child of Person).
     * Inherits name and id from Person and adds:
     *  - courseName
     *  - grade
     */
    public static class Student extends Person
    {
        private String courseName;
        private double grade;

        public Student(String name, int id, String courseName, double grade)
        {
            // Call the constructor of the parent class Person
            super(name, id);
            this.courseName = courseName;
            this.grade = grade;
        }

        public String getCourseName()
        {
            return courseName;
        }

        public double getGrade()
        {
            return grade;
        }

        /*
         * Simple method that returns true if the student passed.
         * Here we assume passing grade is 50 or above.
         */
        public boolean isPassed()
        {
            return grade >= 50;
        }

        /*
         * Overridden method from Person.
         * This version is specific for students and shows student-related data.
         */
        @Override
        public void displayDetails()
        {
            System.out.println("Type       : Student");
            System.out.println("Name       : " + name);
            System.out.println("ID         : " + id);
            System.out.println("Course     : " + courseName);
            System.out.println("Grade      : " + grade);
            System.out.println("Status     : " + (isPassed() ? "Passed" : "Failed"));
        }
    }

    /*
     * Abstract Employee class that extends Person.
     * It is also abstract because different employee types
     * will calculate salary in different ways.
     */
    public static abstract class Employee extends Person
    {
        public Employee(String name, int id)
        {
            super(name, id);
        }

        /*
         * Abstract method for salary calculation.
         * Each subclass must implement its own formula.
         */
        public abstract double calculateSalary();
    }

    /*
     * FullTimeEmployee:
     *  - Inherits from Employee
     *  - Salary is a fixed monthlySalary
     */
    public static class FullTimeEmployee extends Employee
    {
        private double monthlySalary;

        public FullTimeEmployee(String name, int id, double monthlySalary)
        {
            super(name, id);
            this.monthlySalary = monthlySalary;
        }

        /*
         * Implementation of abstract method calculateSalary().
         * For full-time employees, the salary is simply the monthly amount.
         */
        @Override
        public double calculateSalary()
        {
            return monthlySalary;
        }

        /*
         * Overridden displayDetails() to show information about a full-time employee.
         */
        @Override
        public void displayDetails()
        {
            System.out.println("Type       : Full-time Employee");
            System.out.println("Name       : " + name);
            System.out.println("ID         : " + id);
            System.out.println("Monthly Salary : " + monthlySalary);
        }
    }

    /*
     * PartTimeEmployee:
     *  - Inherits from Employee
     *  - Salary is based on hourlyRate * hoursWorked
     */
    public static class PartTimeEmployee extends Employee
    {
        private double hourlyRate;
        private int hoursWorked;

        public PartTimeEmployee(String name, int id, double hourlyRate, int hoursWorked)
        {
            super(name, id);
            this.hourlyRate = hourlyRate;
            this.hoursWorked = hoursWorked;
        }

        /*
         * Implementation of calculateSalary() for part-time employees.
         * Salary = hourly rate × number of hours worked.
         */
        @Override
        public double calculateSalary()
        {
            return hourlyRate * hoursWorked;
        }

        /*
         * Overridden displayDetails() to show information about a part-time employee.
         */
        @Override
        public void displayDetails() {
            System.out.println("Type       : Part-time Employee");
            System.out.println("Name       : " + name);
            System.out.println("ID         : " + id);
            System.out.println("Hourly Rate: " + hourlyRate);
            System.out.println("Hours Worked: " + hoursWorked);
            System.out.println("Total Salary: " + calculateSalary());
        }
    }
    
    /*
     * Helper method to check if a given ID already exists
     * in the records ArrayList.
     */
    private static boolean idExists(ArrayList<Person> records, int id) 
    {
        for (Person p : records) {
            if (p.getId() == id) {
                return true;   // duplicate found
            }
        }
        return false;          // no duplicate
    }
}