import java.awt.image.*;
import java.io.*;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Move {
    private Square squareFrom;
    private Square squareTo;
    private Piece piece;

    //Fields to restore the previous board if the move needs to be undone due to check
    private Piece oldPiece;
    private Piece capturedPiece;

    public Move(Square squareFrom) {
        this.squareFrom = squareFrom;

        //Show line of sight:
        showLineOfSight();
    }

    public void showLineOfSight() {
        Square[] LOS = squareFrom.getCurrentPiece().sees;
        for (int i = 0; i < LOS.length; i++) {
            if (LOS[i] != null) {
                if (LOS[i].getCurrentPiece() == null) {
                    //Update icon to be a dot indicating that the piece can move to the given square
                    try {
                        BufferedImage iconBF = ImageIO.read(new File("Piece_Icons/dot.png")); 
                        LOS[i].scaledIcon =  iconBF.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    if (squareFrom.getCurrentPiece().isWhite() != LOS[i].getCurrentPiece().isWhite()) {
                        LOS[i].highlight();
                    }
                }
            }
        }
    }

    public void hideLineOfSight() {
        Square[] LOS = squareFrom.getCurrentPiece().sees;
        for (int i = 0; i < LOS.length; i++) {
            if (LOS[i] != null) {
                if (LOS[i].getCurrentPiece() == null) {
                    LOS[i].scaledIcon = null;

                }
                else {
                    LOS[i].restoreColor();
                }
            }
        }

    }

    public boolean moveTo(Square squareTo) {
        hideLineOfSight();

        piece = squareFrom.getCurrentPiece();
        this.squareTo = squareTo;

        //Check if move is valid:
        if (piece instanceof Pawn) {
            return checkPawnMove();
        }
        else if (squareTo.getCurrentPiece() == null || squareTo.getCurrentPiece().isWhite() != piece.isWhite()){
            return checkMove();
        }
        return false; //Is trying to move to a square with the same color piece

    }

    public boolean checkMove() {
        for (int i = 0; i < piece.sees.length; i++) {
            if (squareTo.equals(piece.sees[i])) {
                executeMove(piece);
                return true;
            }
        }
        return false; //Piece did not move
    }

    public boolean checkPawnMove() {
        boolean validMove = false;
        //Check if diagonal and then if the pawn is attacking:
        if (isDiagonal() && squareTo.getCurrentPiece() != null && squareTo.getCurrentPiece().isWhite() != piece.isWhite()) {
            if (squareTo.equals(piece.sees[2]) || squareTo.equals(piece.sees[3])) {

                //Execute move
                ((Pawn) piece).setHasMoved(true);
                //Check if promotion is an option
                if (!checkPromotion()) {
                    executeMove(piece);
                }
                validMove = true;
            }
        }
        else if (squareTo.getCurrentPiece() == null) {
            if (squareTo.equals(piece.sees[0]) || squareTo.equals(piece.sees[1])) {
                //Execute move
                ((Pawn) piece).setHasMoved(true);
                //Check if promotion is an option
                if (!checkPromotion()) {
                    executeMove(piece);
                }
                validMove = true;
            }
        }

        return validMove; //Piece did not move
    }

    public boolean checkPromotion() {
        int row = squareTo.getRow();
        int column = squareTo.getColumn();
        boolean isWhite = piece.isWhite();

        if (row == 7 || row == 0) {
            oldPiece = piece;
            piece = new Queen(column, row, isWhite);
            squareFrom.setCurrentPiece(null);
            squareTo.setCurrentPiece(piece);
            return true;
        }
        else {
            return false;
        }
    }

    public void executeMove(Piece piece) {
        squareFrom.setCurrentPiece(null);

        capturedPiece = squareTo.getCurrentPiece();

        squareTo.setCurrentPiece(piece);

        piece.setRow(squareTo.getRow());
        piece.setColumn(squareTo.getColumn());
    }

    public void moveBack() {
        //Check if there was a pawn promotion:
        if (oldPiece != null) {
            piece = oldPiece;
        }

        //Move the piece back:
        squareFrom.setCurrentPiece(piece);
        piece.setRow(squareFrom.getRow());
        piece.setColumn(squareFrom.getColumn());
        piece.updateSight(squareFrom.thisBoard.squares);

        //Update the square moved to:
        if (capturedPiece != null) {
            squareTo.setCurrentPiece(capturedPiece);
        }
        else {
            squareTo.setCurrentPiece(null);
        }

    }

    public boolean isDiagonal() {

        int colFrom = squareFrom.getColumn();
        int rowFrom = squareFrom.getRow();
        int colTo = squareTo.getColumn();
        int rowTo = squareTo.getRow();

        return (Math.abs(colFrom - colTo) == 1 && Math.abs(rowFrom - rowTo) == 1);
    }

    public Square getSquareFrom() {
        return squareFrom;
    }

    public Square getSquareTo() {
        return squareTo;
    }
}
