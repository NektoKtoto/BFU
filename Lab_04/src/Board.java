import Figures.Bishop;
import Figures.Figure;
import Figures.King;
import Figures.Knight;
import Figures.Pawn;
import Figures.Queen;
import Figures.Rook;

import java.util.ArrayList;

public class Board {
    //TODO: Список фигур и начальное положение всех фигур
    private final Figure[][] fields = new Figure[8][8];
    private final ArrayList<String> takeWhite = new ArrayList<>(16);
    private final ArrayList<String> takeBlack = new ArrayList<>(16);

    public char getColorGaming() {
        return colorGaming;
    }

    public void setColorGaming(char colorGaming) {
        this.colorGaming = colorGaming;
    }

    private char colorGaming;

    public void init(){
        this.fields[0] = new Figure[]{
                new Rook("R", 'w'), new Knight("N", 'w'),
                new Bishop("B", 'w'), new Queen("Q", 'w'),
                new King("K", 'w'), new Bishop("B", 'w'),
                new Knight("N", 'w'),new Rook("R", 'w')
        };
        this.fields[1] = new Figure[]{
                new Pawn("P", 'w'),    new Pawn("P", 'w'),
                new Pawn("P", 'w'),    new Pawn("P", 'w'),
                new Pawn("P", 'w'),    new Pawn("P", 'w'),
                new Pawn("P", 'w'),    new Pawn("P", 'w'),
        };

        this.fields[7] = new Figure[]{
                new Rook("R", 'b'), new Knight("N", 'b'),
                new Bishop("B", 'b'), new Queen("Q", 'b'),
                new King("K", 'b'), new Bishop("B", 'b'),
                new Knight("N", 'b'),new Rook("R", 'b')
        };
        this.fields[6] = new Figure[]{
                new Pawn("P", 'b'),    new Pawn("P", 'b'),
                new Pawn("P", 'b'),    new Pawn("P", 'b'),
                new Pawn("P", 'b'),    new Pawn("P", 'b'),
                new Pawn("P", 'b'),    new Pawn("P", 'b'),
        };
    }

    public String getCell(int row, int col){
        Figure figure = this.fields[row][col];
        if (figure == null){
            return "    ";
        }
        return " "+figure.getColor()+figure.getName()+" ";
    }

    public ArrayList<String> getTakeWhite() {
        return takeWhite;
    }

    public ArrayList<String> getTakeBlack() {
        return takeBlack;
    }


    public boolean move_figure(int row1, int col1, int row2, int col2) {
        Figure attacker = this.fields[row1][col1];
        Figure target = this.fields[row2][col2];

        if ((attacker.canMove(row1, col1, row2, col2) || attacker.canAttack(row1, col1, row2, col2)) && attacker.isPathClear(row1, col1, row2, col2, this.fields)) {
            if (target != null && target.getColor() == attacker.getColor()) {
                // Атака на фигуру своего цвета - недопустимый ход
                return false;
            }

            if (target != null) {
                // Атака на фигуру противоположного цвета
                switch (target.getColor()) {
                    case 'w':
                        this.takeWhite.add(target.getColor() + target.getName());
                        break;
                    case 'b':
                        this.takeBlack.add(target.getColor() + target.getName());
                        break;
                }
            }

            this.fields[row2][col2] = attacker;
            this.fields[row1][col1] = null;
            return true;
        }

        return false;
    }

    public void print_board(){
        System.out.println(" +----+----+----+----+----+----+----+----+");
        for(int row = 7; row > -1; row--){
            System.out.print(row);
            for(int col = 0; col< 8; col++){
                System.out.print("|"+getCell(row, col));
            }
            System.out.println("|");
            System.out.println(" +----+----+----+----+----+----+----+----+");
        }

        for(int col = 0; col < 8; col++){
            System.out.print("    "+col);
        }


    }


    public boolean isCheck(char color) {
        int kingRow = -1;
        int kingCol = -1;

        // Находим координаты короля цвета color
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Figure figure = this.fields[row][col];
                if (figure instanceof King && figure.getColor() == color) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }

        // Проверяем, может ли какая-либо фигура атаковать короля
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Figure figure = this.fields[row][col];
                if (figure != null && figure.getColor() != color && figure.canAttack(row, col, kingRow, kingCol) && figure.isPathClear(row, col, kingRow, kingCol, this.fields)) {
                    return true;
                }
            }
        }

        return false;
    }


    public boolean isCheckmate(char color) {
        if (!isCheck(color)) {
            return false; // Если король не в шахе, то нет мата
        }

        // Проверяем, может ли король сделать какой-либо ход, чтобы выйти из шаха
        for (int row1 = 0; row1 < 8; row1++) {
            for (int col1 = 0; col1 < 8; col1++) {
                if (this.fields[row1][col1] != null && this.fields[row1][col1].getColor() == color) {
                    for (int row2 = 0; row2 < 8; row2++) {
                        for (int col2 = 0; col2 < 8; col2++) {
                            if (move_figure(row1, col1, row2, col2)) {
                                // Попробовали ход, теперь отменяем его
                                this.fields[row1][col1] = this.fields[row2][col2];
                                this.fields[row2][col2] = null;

                                if (!isCheck(color)) {
                                    return false; // Нашли ход, который выводит короля из шаха
                                }
                            }
                        }
                    }
                }
            }
        }

        return true; // Нет доступных ходов, король в шахмате
    }

    // В классе Board

    public boolean isStalemate(char color) {
        if (isCheck(color) || !canMoveAnyPiece(color)) {
            return false; // Если король в шахе или нет доступных ходов, то не пат
        }

        // Проверяем, может ли какая-либо фигура сделать какой-либо ход
        for (int row1 = 0; row1 < 8; row1++) {
            for (int col1 = 0; col1 < 8; col1++) {
                if (this.fields[row1][col1] != null && this.fields[row1][col1].getColor() == color) {
                    for (int row2 = 0; row2 < 8; row2++) {
                        for (int col2 = 0; col2 < 8; col2++) {
                            if (move_figure(row1, col1, row2, col2)) {
                                // Попробовали ход, теперь отменяем его
                                this.fields[row1][col1] = this.fields[row2][col2];
                                this.fields[row2][col2] = null;

                                if (!isCheck(color)) {
                                    return false; // Нашли ход, который не ставит короля в шах
                                }
                            }
                        }
                    }
                }
            }
        }

        return true; // Нет доступных ходов, пат
    }

    private boolean canMoveAnyPiece(char color) {
        // Проверяем, может ли какая-либо фигура сделать какой-либо ход
        for (int row1 = 0; row1 < 8; row1++) {
            for (int col1 = 0; col1 < 8; col1++) {
                if (this.fields[row1][col1] != null && this.fields[row1][col1].getColor() == color) {
                    for (int row2 = 0; row2 < 8; row2++) {
                        for (int col2 = 0; col2 < 8; col2++) {
                            if (move_figure(row1, col1, row2, col2)) {
                                // Попробовали ход, теперь отменяем его
                                this.fields[row1][col1] = this.fields[row2][col2];
                                this.fields[row2][col2] = null;
                                return true; // Есть доступные ходы
                            }
                        }
                    }
                }
            }
        }

        return false; // Нет доступных ходов
    }

}