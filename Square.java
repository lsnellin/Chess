import javax.swing.*;
import java.awt.*;

public class Square extends JPanel {

    //Colors:
    private static Color color_light = new Color(240, 240, 240);
    private static Color color_dark = new Color(150, 150, 255);

    //Fields:
    private int column;
    private int row;
    private Piece currentPiece;
    private Color color;
    public Image scaledIcon;
    public boolean outlined;

    public Board thisBoard;

    public Square(int row, int column, Board thisBoard) { 
        
        this.column = column;
        this.row = row;
        this.thisBoard = thisBoard;

        //Set color:
        restoreColor();

        //Initialize the pieces:
        Piece newPiece;

        if (row > 1 && row < 6) { //Put no pieces in the middle 4 rows of the board
            newPiece = null;
        }
        else if (row > 0 && row < 7) { //Put pawns in rows 1 and 6, white in #1 and black in #6
            newPiece = (row == 1) ? new Pawn(column, row, true) : new Pawn(column, row, false);
        }
        else if (column == 0 || column == 7) { //Put rooks on a1, h1, a8, h8
            newPiece = (row == 0) ? new Rook(column, row, true) : new Rook(column, row, false);
        }
        else if (column == 1 || column == 6) { //Put knights on b1, g1, b8, g8
            newPiece = (row == 0) ? new Knight(column, row, true) : new Knight(column, row, false);
        }
        else if (column == 2 || column == 5) { //Put bishops on c1, f1, c8, f8
            newPiece = (row == 0) ? new Bishop(column, row, true): new Bishop(column, row, false);
        }
        else if (column == 3) { //Put a Queen on d1 and d8
            newPiece = (row == 0) ? new Queen(column, row, true): new Queen(column, row, false);
        }
        else { //Put a king on e1 and e8
            newPiece = (row == 0) ? new King(column, row, true) : new King(column, row, false);
        }

        setCurrentPiece(newPiece);
    }

    

    //Paint the panel its respective color:
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (scaledIcon != null) {
            if (currentPiece == null) { //The icon being drawn is the attack icon, so make it smaller
                scaledIcon = scaledIcon.getScaledInstance(getWidth() / 4 , getHeight() / 4, Image.SCALE_SMOOTH);
                g.drawImage(scaledIcon, 3 * getWidth() / 8, 3 * getHeight() / 8, this); 

            } else {//Drawing a piece icon, so make it large
                scaledIcon = scaledIcon.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                g.drawImage(scaledIcon, 0, 0, this); 

            }
        } else {
            //g.setColor(color);
            //g.fillRect(0, 0, getWidth(), getHeight());
        }

        if (outlined) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(10));
            g2.setColor(Color.BLACK);
            g2.drawRect(0, 0, getWidth(), getHeight());
        }
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public void setCurrentPiece(Piece piece) {
        currentPiece = piece;
        
        if (currentPiece != null) {
            scaledIcon = currentPiece.icon;
        }
        else {
            scaledIcon = null;
        }
    }

    public void highlight() {
        color = new Color(255,255,0);
    }

    public void restoreColor() {
        if ((column % 2) == (row % 2)) {
            color = color_dark;
        } else {
            color = color_light;
        }
    }

    public void hideIcon() {
        scaledIcon = null;
    }

    public void showIcon() {
        scaledIcon = currentPiece.icon;
    }

    public void addOutline() {
        this.outlined = true;
    }

    public void removeOutline() {
        this.outlined = false;
    }
}
