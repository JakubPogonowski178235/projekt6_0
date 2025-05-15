import java.io.IOException;
import java.util.Scanner;
import java.util.InputMismatchException;

class WrongStudentName extends Exception { }
class WrongAge extends Exception { }
class WrongDateOfBirth extends Exception { }

class Main {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        while(true) {
            try {
                int ex = menu();
                switch(ex) {
                    case 1: exercise1(); break;
                    case 2: exercise2(); break;
                    case 3: exercise3(); break;
                    default: return;
                }
            } catch(IOException e) {

            } catch(WrongStudentName e) {
                System.out.println("Błędne imię studenta!");
            } catch(WrongAge e) {
                System.out.println("Błędny wiek! Wiek musi być z zakresu 1-99.");
            } catch(WrongDateOfBirth e) {
                System.out.println("Błędny format daty! Wymagany format: DD-MM-YYYY");
            } catch(InputMismatchException e) {
                System.out.println("Błąd wyboru: " + e.getMessage());
            }
        }
    }

    public static int menu() throws InputMismatchException {
        System.out.println("Wciśnij:");
        System.out.println("1 - aby dodać studenta");
        System.out.println("2 - aby wypisać wszystkich studentów");
        System.out.println("3 - aby wyszukać studenta po imieniu");
        System.out.println("0 - aby wyjść z programu");

        if (!scan.hasNextInt()) {
            scan.next();
            throw new InputMismatchException("Wprowadzono literę zamiast cyfry!");
        }

        int choice = scan.nextInt();
        scan.nextLine();

        if (choice < 0 || choice > 3) {
            throw new InputMismatchException("Wybór poza zakresem (0-3)!");
        }

        return choice;
    }

    public static String ReadName() throws WrongStudentName {
        System.out.println("Podaj imię: ");
        String name = scan.nextLine();
        if(name.contains(" "))
            throw new WrongStudentName();

        return name;
    }

    public static int ReadAge() throws WrongAge {
        System.out.println("Podaj wiek: ");
        int age = scan.nextInt();
        if(age < 1 || age > 99)
            throw new WrongAge();
        return age;
    }

    public static String ReadDate() throws WrongDateOfBirth {
        System.out.println("Podaj datę urodzenia DD-MM-YYYY");
        String date = scan.nextLine();
        String[] parts = date.split("-");

        if (parts.length != 3) throw new WrongDateOfBirth();

        if (parts[0].length() != 2 || parts[1].length() != 2 || parts[2].length() != 4) {
            throw new WrongDateOfBirth();
        }

        try {
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            throw new WrongDateOfBirth();
        }

        return date;
    }

    public static void exercise1() throws IOException, WrongStudentName, WrongAge, WrongDateOfBirth {
        var name = ReadName();
        var age = ReadAge();
        scan.nextLine();
        var date = ReadDate();
        (new Service()).addStudent(new Student(name, age, date));
    }

    public static void exercise2() throws IOException {
        var students = (new Service()).getStudents();
        for(Student current : students) {
            System.out.println(current.ToString());
        }
    }

    public static void exercise3() throws IOException {
        scan.nextLine();
        System.out.println("Podaj imię: ");
        var name = scan.nextLine();
        var wanted = (new Service()).findStudentByName(name);
        if(wanted == null)
            System.out.println("Nie znaleziono...");
        else {
            System.out.println("Znaleziono: ");
            System.out.println(wanted.ToString());
        }
    }
}