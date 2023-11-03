import java.util.ArrayList;
import java.util.List;

public class Cinema
{
    private List<CinemaHall> halls;

    // Конструктор класса кинотеатра
    public Cinema() {
        this.halls = new ArrayList<>();
    }

    // Метод для получения списка кинозалов
    public List<CinemaHall> getHalls()
    {
        return halls;
    }

    // Метод для добавления нового кинозала в кинотеатр с заданным количеством рядов и мест в ряду
    public void addHall(int rows, int seatsPerRow)
    {
        CinemaHall newHall = new CinemaHall(rows, seatsPerRow);
        halls.add(newHall);
    }

    public void displayHalls()
    {
        if (halls.isEmpty()) {
            System.out.println("Нет залов в данном кинотеатре.");
            return;
        }
        System.out.println("Залы в кинотеатре:");
        int index = 0;
        for (CinemaHall hall : halls) {
            System.out.println("Зал " + index + ": Рядов - " + hall.getRows() + ", Мест в ряду - " + hall.getSeats());
            index++;
        }
    }

    // Внутренний класс, представляющий кинозал
    public static class CinemaHall
    {
        private final int rows;
        private final int seats;

        // Конструктор класса кинозала
        public CinemaHall(int rows, int seatsPerRow)
        {
            this.rows = rows;
            this.seats = seatsPerRow;
        }

        // Метод для получения количества рядов в кинозале
        public int getRows()
        {
            return rows;
        }

        // Метод для получения количества мест в ряду кинозала
        public int getSeats()
        {
            return seats;
        }
    }
}
