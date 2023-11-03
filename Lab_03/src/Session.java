import java.time.LocalTime;
import java.time.Duration;
import java.util.Arrays;

public class Session
{
    String movieTitle;
    LocalTime startTime;
    private Duration duration;
    Cinema.CinemaHall cinemaHall;
    boolean[][] seatsAvailability; // Информация о доступности мест (занято/свободно)

    // Конструктор класса сеанса
    public Session(String movieTitle, LocalTime startTime, Duration duration, Cinema.CinemaHall cinemaHall)
    {
        this.movieTitle = movieTitle;
        this.startTime = startTime;
        this.duration = duration;
        this.cinemaHall = cinemaHall;
        this.seatsAvailability = new boolean[cinemaHall.getRows()][cinemaHall.getSeats()];
        for (boolean[] row : seatsAvailability)
        {
            Arrays.fill(row, true); // Изначально все места свободны
        }
    }

    // Метод для получения времени окончания сеанса
    public LocalTime getEndTime()
    {
        return startTime.plus(duration);
    }

    // Метод для получения информации о доступности конкретного места
    public boolean isSeatAvailable(int row, int seat)
    {
        return seatsAvailability[row][seat];
    }

    // Метод для занятия места
    public void bookSeat(int row, int seat)
    {
        if (isSeatAvailable(row, seat))
        {
            seatsAvailability[row][seat] = false; // Помечаем место как занятое
            System.out.println("Место забронировано: Ряд " + row + ", Место " + seat);
        }
        else
        {
            System.out.println("Место уже занято.");
        }
    }

    // Метод для освобождения места
    public void releaseSeat(int row, int seat)
    {
        if (!isSeatAvailable(row, seat))
        {
            seatsAvailability[row][seat] = true; // Помечаем место как свободное
            System.out.println("Место освобождено: Ряд " + row + ", Место " + seat);
        }
        else
        {
            System.out.println("Место уже свободно.");
        }
    }
}
