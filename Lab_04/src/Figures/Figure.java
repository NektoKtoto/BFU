package Figures;

// 1(w) - White 2(b) - Black;

public abstract class Figure {
    public char getColor() {
        return color;
    }

    private String name;

    public String getName() {
        return name;
    }

    private char color;

    //private int[] position = new int[2];  // 0- row ; 1 - col


    public Figure(String name, char color) {
        this.name = name;
        this.color = color;
    }

    public boolean canMove(int row, int col, int row1, int col1){
        return (row >=0 && row < 8 )&&(col >=0 && col < 8)&&
                (row1 >=0 && row1 < 8 )&&(col1 >=0 && col1 < 8) &&
                (row!=row1) && (col!=col1);
    }

    public boolean canAttack(int row, int col, int row1, int col1){
        return this.canMove(row, col, row1, col1);
    }

    public boolean isPathClear(int row, int col, int row1, int col1, Figure[][] fields) {
        if (this.canMove(row, col, row1, col1)) {
            if (this instanceof Knight) {
                return true; // Для коня путь всегда свободен
            }

            // Если фигура не конь, проверяем препятствия на пути
            int rowDir = Integer.compare(row1, row);
            int colDir = Integer.compare(col1, col);

            if (rowDir != 0 && colDir != 0 && Math.abs(row - row1) == Math.abs(col - col1)) {
                // Движение по диагонали (слон, ферзь)
                for (int i = 1; i < Math.abs(row - row1); i++) {
                    if (fields[row + i * rowDir][col + i * colDir] != null) {
                        return false; // Есть препятствие
                    }
                }
                return true; // Путь свободен
            }

            if (row == row1) {
                // Движение по горизонтали (ладья, ферзь)
                for (int i = 1; i < Math.abs(col - col1); i++) {
                    int nextCol = col + i * colDir;
                    if (fields[row][nextCol] != null) {
                        return false; // Есть препятствие
                    }
                }
                return true; // Путь свободен
            }

            if (col == col1) {
                // Движение по вертикали (ладья, ферзь)
                for (int i = 1; i < Math.abs(row - row1); i++) {
                    int nextRow = row + i * rowDir;
                    if (fields[nextRow][col] != null) {
                        return false; // Есть препятствие
                    }
                }
                return true; // Путь свободен
            }

            return false; // В остальных случаях путь не является прямой линией
        }

        return false; // Фигура не может двигаться в указанном направлении
    }

}
