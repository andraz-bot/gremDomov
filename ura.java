import java.time.LocalTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
public static void main(String[] args){
    try {
        new ProcessBuilder("cmd", "/c", "chcp 65001").inheritIO().start().waitFor();
    } catch (Exception e) {
        //ignorira če je OS Windows
    }

    Scanner scanner = new Scanner(System.in);

    DateTimeFormatter format = DateTimeFormatter.ofPattern("H:mm");

    LocalTime uraKonca = null;

    //preverba podanega formata
    while (uraKonca == null) {
        System.out.print("Predviden čas konca delovnika (format 'H:mm'): ");
        String vnos = scanner.nextLine();
        try {
            uraKonca = LocalTime.parse(vnos, format);
        } catch (DateTimeParseException e) {
            System.out.println("Napačen format! Uporabi format 'H:mm'!");
        }
    }

    scanner.close();

    LocalTime trenutnaUra = LocalTime.now();
    Duration preostanekDelovnika = Duration.between(trenutnaUra, uraKonca);

    if (preostanekDelovnika.isNegative()) {
        System.out.println("Delavnik se je že zaključil!");
        return;  
    }

    long ure = preostanekDelovnika.toHours();
    long minute = preostanekDelovnika.toMinutes()%60;
    long sekunde = preostanekDelovnika.toSeconds()%60;

    System.out.println("Do konca delovnika je se: "+ ure + ":" + minute + ":" + sekunde);
}