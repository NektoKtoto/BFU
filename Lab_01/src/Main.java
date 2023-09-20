import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        problem1(in);     //Сиракузская последовательность
        problem2(in);     //Сумма ряда
        problem3(in);     //Ищем клад
        problem4(in);     //Логистический максимин
        problem5(in);     //Дважды четное число
        in.close();
    }

    //Сиракузская последовательность
    public static void problem1(Scanner in)
    {
        System.out.println("#1 - Сиракузская последовательность");
        int number = in.nextInt();
        int counter = 0;
        while (number != 1)
        {
            if (number % 2 == 0)
                number /= 2;
            else
                number = 3 * number + 1;
            ++counter;
        }
        System.out.println(counter);
    }

    //Сумма ряда
    public static void problem2(Scanner in)
    {
        System.out.println("#2 - Сумма ряда");
        int number,
            sum = 0,
            quantity = in.nextInt();
        boolean mark = true;
        while (quantity > 0)
        {
            number = in.nextInt();
            if (mark)
            {
                sum += number;
                mark = false;
            }
            else
            {
                sum -= number;
                mark = true;
            }
            --quantity;
        }
        System.out.println(sum);
    }

    //Ищем клад
    public static void problem3(Scanner in)
    {
        System.out.println("#3 - Ищем клад");
        int counter = 0,
            currentX = 0,
            currentY = 0,
            x = in.nextInt(),
            y = in.nextInt();
        String direction = in.next();
        while (!direction.equals("стоп"))
        {
            int distance = in.nextInt();
            if ((currentX != x) || (currentY != y))
            {
                switch (direction)
                {
                    case "восток":
                        currentX += distance;
                        break;
                    case "запад":
                        currentX -= distance;
                        break;
                    case "север":
                        currentY += distance;
                        break;
                    case "юг":
                        currentY -= distance;
                }
                ++counter;
            }
            direction = in.next();
        }
        System.out.println(counter);
    }

    //Логистический максимин
    public static void problem4(Scanner in)
    {
        System.out.println("#4 - Логистический максимин");
        int quantityRoads = in.nextInt(),
            maxHeight = 0,
            road = 0;
        for (int counter = 1; counter <= quantityRoads; ++counter)
        {
            int quantityBridge = in.nextInt(),
                minBridge = in.nextInt();
            for (--quantityBridge; quantityBridge > 0; --quantityBridge)
            {
                int bridge = in.nextInt();
                minBridge = Math.min(bridge, minBridge);
            }
            if (minBridge > maxHeight)
            {
                maxHeight = minBridge;
                road = counter;
            }
        }
        System.out.println(road + " " + maxHeight);
    }

    //Дважды четное число

    public static class DoubleEven
    {
        private int value;

        public DoubleEven() {}

        public void setValue(int newValue)
        {
            value = newValue;
        }

        public boolean check()
        {
            int sum = 0,
                mul = 1;
            do {
                sum += value % 10;
                mul *= value % 10;
                value /= 10;
            } while (value != 0);
            return (sum % 2 == 0) && (mul % 2 == 0);
        }
    }

    public static void problem5(Scanner in)
    {
        System.out.println("#5 - Дважды четное число");
        DoubleEven number = new DoubleEven();
        number.setValue(in.nextInt());
        System.out.println(number.check());
    }
}
