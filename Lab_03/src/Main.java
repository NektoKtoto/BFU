import java.time.LocalTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main
{
    private static final List<Cinema> cinemas = new ArrayList<>();;
    private static final List<Session> sessions = new ArrayList<>();;

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        while (true)
        {
            System.out.println("Выберите режим:\n1. Администратор\n2. Пользователь\n0. Выход");
            int mode = in.nextInt();
            switch (mode)
            {
                case 1:
                    adminMode(in);
                    break;
                case 2:
                    userMode(in);
                    break;
                case 0:
                    System.out.println("До свидания!");
                    return;
                default:
                    System.out.println("Некорректный выбор.");
            }
        }
    }

    public static void adminMode(Scanner in)
    {
        System.out.println("Добро пожаловать, администратор!");
        while (true)
        {
            System.out.println("\nВыберите действие:\n1. Добавить кинотеатр\n2. Добавить зал в кинотеатр\n3. Создать сеанс\n4. Вывести информацию по кинотеатрам\n5. Вывести информацию по сеансам\n0. Назад");
            int choice = in.nextInt();
            switch (choice) {
                case 1:
                    addCinema();
                    break;
                case 2:
                    addHallToCinema(in);
                    break;
                case 3:
                    createSession(in);
                    break;
                case 4:
                    displayCinemasInfo();
                    break;
                case 5:
                    displaySessionsInfo();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Некорректный выбор.");
            }
        }
    }

    public static void addCinema()
    {
        Cinema cinema = new Cinema();
        cinemas.add(cinema);
        System.out.println("Кинотеатр добавлен.");
    }

    public static void addHallToCinema(Scanner in)
    {
        displayCinemasInfo();
        System.out.println("Выберите кинотеатр (номер):");
        int cinemaIndex = in.nextInt();
        Cinema cinema = cinemas.get(cinemaIndex);
        System.out.println("Введите количество рядов:");
        int rows = in.nextInt();
        System.out.println("Введите количество мест в ряду:");
        int seatsPerRow = in.nextInt();
        cinema.addHall(rows, seatsPerRow);
        System.out.println("Зал добавлен в кинотеатр " + cinemaIndex + ".");
    }

    public static void createSession(Scanner in)
    {
        displayCinemasInfo();
        System.out.println("Выберите кинотеатр (номер):");
        int cinemaIndex = in.nextInt();
        Cinema cinema = cinemas.get(cinemaIndex);
        cinema.displayHalls();
        System.out.println("Выберите зал (номер):");
        int hallIndex = in.nextInt();
        Cinema.CinemaHall hall = cinema.getHalls().get(hallIndex);
        System.out.println("Введите название фильма:");
        String movieTitle = in.next();
        System.out.println("Введите время начала сеанса (часы минуты в формате HH:mm):");
        String time = in.next();
        LocalTime startTime = LocalTime.parse(time);
        System.out.println("Введите продолжительность сеанса (в минутах):");
        int durationMinutes = in.nextInt();
        Session session = new Session(movieTitle, startTime, Duration.ofMinutes(durationMinutes), hall);
        sessions.add(session);
        System.out.println("Сеанс создан.");
    }

    public static void displayCinemasInfo()
    {
        System.out.println("Информация по кинотеатрам:");
        int index = 0;
        for (Cinema cinema : cinemas) {
            System.out.println("Кинотеатр " + index + ": " + cinema.getHalls().size() + " зал(ов)");
            index++;
        }
    }

    public static void displaySessionsInfo()
    {
        System.out.println("Информация по сеансам:");
        int index = 0;
        for (Session session : sessions)
        {
            System.out.println("Сеанс " + index + ": " + session.movieTitle + " - " + session.startTime);
            index++;
        }
    }

    public static void userMode(Scanner scanner)
    {
        System.out.println("Добро пожаловать, пользователь!");
        while (true)
        {
            System.out.println("\nВыберите действие:\n1. Найти ближайший сеанс\n2. Выбрать сеанс\n3. Печать плана зала\n0. Назад");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    findNearestSession(scanner);
                    break;
                case 2:
                    bookSeat(scanner);
                    break;
                case 3:
                    printHallPlan(scanner);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Некорректный выбор.");
            }
        }
    }

    public static void findNearestSession(Scanner scanner) {
        System.out.println("Введите название фильма:");
        String movieName = scanner.next();
        Session nearestSession = findNearestSessionForMovie(movieName);
        if (nearestSession != null)
        {
            System.out.println("Ближайший сеанс для фильма '" + movieName + "':");
            System.out.println("Время начала: " + nearestSession.startTime);
            System.out.println("Время окончания: " + nearestSession.getEndTime());
        }
        else
        {
            System.out.println("Свободных сеансов для выбранного фильма нет.");
        }
    }

    public static Session findNearestSessionForMovie(String movieName)
    {
        Session nearestSession = null;
        LocalTime currentTime = LocalTime.now(); // Получаем текущее время
        long minTimeDifference = Long.MAX_VALUE;
        for (Session session : sessions) {
            if (session.movieTitle.equals(movieName))
            {
                // Проверяем, что сеанс начинается после текущего времени
                if (session.startTime.isAfter(currentTime))
                {
                    long timeDifference = Duration.between(currentTime, session.startTime).toMinutes();
                    if (timeDifference < minTimeDifference)
                    {
                        minTimeDifference = timeDifference;
                        nearestSession = session;
                    }
                }
            }
        }
        return nearestSession;
    }

    public static void bookSeat(Scanner scanner)
    {
        System.out.println("Выберите номер сеанса:");
        int sessionNumber = scanner.nextInt();
        Session session = sessions.get(sessionNumber);
        System.out.println("Выберите ряд:");
        int row = scanner.nextInt();
        System.out.println("Выберите место:");
        int seat = scanner.nextInt();
        if (session.isSeatAvailable(row, seat))
        {
            session.bookSeat(row, seat);
        }
        else
        {
            System.out.println("Место уже занято.");
        }
    }

    public static void printHallPlan(Scanner scanner) {
        System.out.println("Выберите сеанс (номер):");
        int sessionIndex = scanner.nextInt();

        Session session = sessions.get(sessionIndex);
        Cinema.CinemaHall hall = session.cinemaHall;
        boolean[][] seatsAvailability = session.seatsAvailability;

        System.out.println("План зала для сеанса:");
        for (int i = 0; i < hall.getRows(); i++) {
            for (int j = 0; j < hall.getSeats(); j++) {
                if (seatsAvailability[i][j]) {
                    System.out.print("[ ] "); // Свободное место
                } else {
                    System.out.print("[X] "); // Занятое место
                }
            }
            System.out.println(); // Переход на новую строку для нового ряда мест
        }
    }
}