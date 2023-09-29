import java.util.Arrays;

public class Main
{
    public static void main(String[] args)
    {
        String string_1 = "abcaabcdadf",
               substring = problem1(string_1);              //Поиск наибольшей подстроки без повторений
        System.out.println(substring);

        int[] array1_2 = {1, 2, 4, 5, 7, 8},
              array2_2 = {-1, 0, 3, 6, 9},
              arrayResult = problem2(array1_2, array2_2);   //Объединение отсортированных массивов
        System.out.println(Arrays.toString(arrayResult));

        int[] array_3 = {1, 2, -2, 3, 4, -8, 5, -6, 7};
        int maxSum = problem3(array_3);                     //Поиск максимальной суммы подмассива
        System.out.println(maxSum);

        int[][] matrix_4 = {
                               {1, 1, 1, 1},
                               {2, 2, 2, 2},
                               {3, 3, 3, 3}
                           },
                matrixClockwise = problem4(matrix_4);       //Поворот массива на 90 градусов по часовой
        System.out.println(Arrays.deepToString(matrixClockwise));

        int target = 5;
        int[] array_5 = {1, 2, -4, 5, 9, 5},
              arrayElement = problem5(target, array_5);     //Поиск пары по заданной сумме
        if (arrayElement != null)
            System.out.println(Arrays.toString(arrayElement));
        else
            System.out.println("Нет таких");

        int[][] matrix_6 = {
                               {1, 2, 3, 4},
                               {5, 6, 7, 8},
                               {-1, -2, -3}
                           };
        int sum = problem6(matrix_6);                       //Сумма всех элементов двумерного массива
        System.out.println(sum);

        int[][] matrix_7 = {
                               {1, 2, 3, 4},
                               {4, 3, 2, 1},
                               {-3, -1, -2}
                           };
        int[] arrayMax = problem7(matrix_7);                //Массив максимальных элементов
        System.out.println(Arrays.toString(arrayMax));

        int[][] matrix_8 = {
                               {9, 9, 9, 9},
                               {8, 8, 8, 8},
                               {7, 7, 7, 7}
                           },
                matrixAntiClockwise = problem8(matrix_8);   //Поворот массива на 90 градусов против часовой
        System.out.println(Arrays.deepToString(matrixAntiClockwise));
    }

    //Поиск наибольшей подстроки без повторений
    private static String problem1(String string)
    {
        System.out.println("#1 - Поиск наибольшей подстроки без повторений");
        StringBuilder substring = new StringBuilder();
        int start = 0,
            quantity = 1,
            startMax = 0,
            quantityMax = 1;
        substring.append(string.charAt(start));
        for (int position = 1; position < string.length(); ++position)
        {
            boolean flag = true;
            for (int item = 0; item < substring.length(); ++item)
                if (string.charAt(position) == substring.charAt(item))
                {
                    flag = false;
                    break;
                }
            if (flag)
            {
                ++quantity;
                substring.append(string.charAt(position));
            }
            else
            {
                if (quantity > quantityMax)
                {
                    quantityMax = quantity;
                    startMax = start;
                }
                start = position;
                quantity = 1;
                substring.setLength(0);
                substring.append(string.charAt(start));
            }
        }
        if (quantity > quantityMax)
        {
            quantityMax = quantity;
            startMax = start;
        }
        return string.substring(startMax, startMax + quantityMax);
    }

    //Объединение отсортированных массивов
    private static int[] problem2(int[] array1, int[] array2)
    {
        System.out.println("#2 - Объединение отсортированных массивов");
        int[] arrayResult = new int[array1.length + array2.length];
        int element1 = 0,
            elementResult = 0;
        for (int element2 : array2)
        {
            while ((element1 < array1.length) && (element2 > array1[element1]))
                arrayResult[elementResult++] = array1[element1++];
            arrayResult[elementResult++] = element2;
        }
        while (element1 < array1.length)
            arrayResult[elementResult++] = array1[element1++];
        return arrayResult;
    }

    //Поиск максимальной суммы подмассива
    private static int problem3(int[] array)
    {
        System.out.println("#3 - Поиск максимальной суммы подмассива");
        int maxSum = Integer.MIN_VALUE,
            maxSumNow = 0;
        for (int element : array)
        {
            maxSumNow = Integer.max((maxSumNow + element), element);
            maxSum = Integer.max(maxSum, maxSumNow);
        }
        return maxSum;
    }

    //Поворот массива на 90 градусов по часовой
    private static int[][] problem4(int[][] matrix)
    {
        System.out.println("#4 - Поворот массива на 90 градусов по часовой");
        int[][] matrixNew = new int[matrix[0].length][matrix.length];
        int colIndex = matrix.length;
        for (int[] row : matrix)
        {
            --colIndex;
            int rowIndex = matrix[0].length;
            for (int element : row)
                matrixNew[--rowIndex][colIndex] = element;
        }
        return matrixNew;
    }

    //Поиск пары по заданной сумме
    private static int[] problem5(int target, int[] array)
    {
        System.out.println("#5 - Поиск пары по заданной сумме");
        for (int element1 = 0; element1 < array.length - 1; ++element1)
            for (int element2 = element1 + 1; element2 < array.length; ++element2)
                if (array[element1] + array[element2] == target)
                    return new int[] {array[element1], array[element2]};
        return null;
    }

    //Сумма всех элементов двумерного массива
    private static int problem6(int[][] matrix)
    {
        System.out.println("#6 - Сумма всех элементов двумерного массива");
        int sum = 0;
        for (int[] row : matrix)
            for (int element : row)
                sum += element;
        return sum;
    }

    //Массив максимальных элементов
    private static int[] problem7(int[][] matrix)
    {
        System.out.println("#7 - Массив максимальных элементов");
        int[] arrayMax = new int[matrix.length];
        for (int rowIndex = 0; rowIndex < matrix.length; ++rowIndex)
        {
            int[] row = matrix[rowIndex];
            int currentMax = Integer.MIN_VALUE;
            for (int element : row)
                currentMax = Integer.max(currentMax, element);
            arrayMax[rowIndex] = currentMax;
        }
        return arrayMax;
    }

    //Поворот массива на 90 градусов против часовой
    private static int[][] problem8(int[][] matrix)
    {
        System.out.println("#8 - Поворот массива на 90 градусов против часовой");
        int[][] matrixNew = new int[matrix[0].length][matrix.length];
        int colIndex = 0;
        for (int[] row : matrix)
        {
            int rowIndex = matrix[0].length;
            for (int element : row)
                matrixNew[--rowIndex][colIndex] = element;
            ++colIndex;
        }
        return matrixNew;
    }
}