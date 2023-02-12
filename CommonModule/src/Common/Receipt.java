package Common;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Receipt {
    private String userName;
    private String hour;
    private LocalDate date;

    public Receipt(String userName) {
        this.date=LocalDate.now();
        this.userName = userName;
        LocalTime time= LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.GERMAN);
        this.hour= formatter.format(time);
    }
    public String toString() {
        return ("Client: " + userName + "\n" + "Date and time: " +
                date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))+ " " + hour);
    }
}







