package release.gameoflife;

public class GameOfLife {

    private boolean[][] field;

    public GameOfLife(int width, int height) {
        field = new boolean[width][height];
    }

    public void step() {
        boolean[][] newField = new boolean[field.length][field[0].length];

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                int aliveNeighbours = countAliveNeighbours(i, j);

                if (field[i][j]) {
                    newField[i][j] = aliveNeighbours == 2 || aliveNeighbours == 3;
                } else {
                    newField[i][j] = aliveNeighbours == 3;
                }
            }
        }

        field = newField;
    }

    public void setCellAlive(int x, int y, boolean isAlive) {
        field[x][y] = isAlive;
    }

    public boolean isCellAlive(int x, int y) {
        return field[x][y];
    }

    private int countAliveNeighbours(int x, int y) {
        int count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;

                int newX = x + i;
                int newY = y + j;

                if (newX >= 0 && newY >= 0 && newX < field.length && newY < field[newX].length && field[newX][newY]) {
                    count++;
                }
            }
        }

        return count;
    }
}
