import java.awt.image.*;
import java.io.*;
import java.awt.Image;

import javax.imageio.ImageIO;

public class Piece {
    protected Square[] sees;
    protected Square[] seenFrom;

    private int column;
    private int row;
    private boolean isWhite;
    private String pieceName;
    public Image icon;

    public Piece(int column, int row, boolean isWhite, String pieceName) {
        this.column = column;
        this.row = row;
        this.isWhite = isWhite;
        this.pieceName = pieceName;
        
        //Initialize the piece icon:
        getIcon();

        //Initialize sightFrom to an array of 9 object references to squares.
        //In chess, a maximum of 9 pieces can attack a single piece at once
        seenFrom = new Square[9];
    }

    public void getIcon() {
        String fileName = "Piece_Icons/" + pieceName;

        fileName += (isWhite) ? "W" : "B";
        fileName += ".png";

        try {
            BufferedImage iconBF = ImageIO.read(new File(fileName)); 
            icon = iconBF.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setIcon(boolean isWhite, String pieceName) {
        String fileName = "Piece_Icons/" + pieceName;

        fileName += (isWhite) ? "W" : "B";
        fileName += ".png";

        try {
            BufferedImage iconBF = ImageIO.read(new File(fileName)); 
            icon = iconBF.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void updateSight(Square[][] squares) {
        System.out.println("Null Piece:");
    }


}
