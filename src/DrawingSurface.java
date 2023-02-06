import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class DrawingSurface extends PApplet {
    private String square_selected;
    private final HashMap<String, PImage> images;
    private final Board board;

    public DrawingSurface() {
        board = new Board();
        images = new HashMap<>();
        square_selected = "";
    }

    public void settings() {
        size(800, 800);


    }

    public void setup() {
        File folder = new File("resources");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                String name = file.getName().substring(0, file.getName().length() - 4);
                String file_name = file.getName();
                images.put(name, loadImage("resources/" + file_name));
            }
        }

    }

    @Override
    public void draw() {
        background(0);
        for (int i = 0; i < 64; i++) {
            int row = i / 8;
            int column = i % 8;
            if ((row + column) % 2 == 0) {
                fill(255);
            } else {
                fill(166, 94, 46);
            }
            if (square_selected != null && !square_selected.equals("")) {
                int selected_column = square_selected.charAt(0) - 'A';
                int selected_row = square_selected.charAt(1) - '1';
                selected_row = 7 - selected_row;
                if (selected_row == row && selected_column == column) {
                    fill(0, 255, 0);
                }
            }
            int x = 100 * column, y = 100 * row;
            rect(x, y, 100, 100);
            Piece cur = board.getPiece(Square.fromValue(getSquare(row, column)));
            String name = get_image_name(cur.name());

            if (name == null) {
                continue;
            }
            image(images.get(name), column * 100, row * 100, 100, 100);

        }
    }

    @Override
    public void mousePressed() {
        String position = getPosition(mouseX, mouseY);
        if (square_selected.equals("")) {
            square_selected = position;
        } else if (square_selected.equals(position)) {
            square_selected = "";
        } else {
            List<Move> legal_moves = board.legalMoves();
            for (Move move : legal_moves) {
                if (move.getFrom().toString().equals(square_selected) && move.getTo().toString().equals(position)) {
                    board.doMove(move);
                    square_selected = "";
                    return;
                }
            }
            square_selected = position;

        }
    }

    private String get_image_name(String piece) {
        String name = "";
        switch (piece) {
            case "WHITE_PAWN":
                name = "wpawn";
                break;
            case "WHITE_ROOK":
                name = "wrook";
                break;
            case "WHITE_KNIGHT":
                name = "wknight";
                break;
            case "WHITE_BISHOP":
                name = "wbishop";
                break;
            case "WHITE_QUEEN":
                name = "wqueen";
                break;
            case "WHITE_KING":
                name = "wking";
                break;
            case "BLACK_PAWN":
                name = "bpawn";
                break;
            case "BLACK_ROOK":
                name = "brook";
                break;
            case "BLACK_KNIGHT":
                name = "bknight";
                break;
            case "BLACK_BISHOP":
                name = "bbishop";
                break;
            case "BLACK_QUEEN":
                name = "bqueen";

                break;
            case "BLACK_KING":
                name = "bking";
                break;
            case "NONE":
                name = null;
                break;

        }
        return name;
    }

    private String getPosition(int mouseX, int mouseY) {
        int row = mouseY / 100;
        int column = mouseX / 100;
        return getSquare(row, column);
    }

    private String getSquare(int row, int column) {
        row = 8 - row;
        char column_char = (char) (column + 'A');
        char row_char = (char) (row + '0');
        return "" + column_char + row_char;
    }


}










