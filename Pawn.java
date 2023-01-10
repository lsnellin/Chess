public class Pawn extends Piece{
    //Extra field
    private boolean hasMoved;

    public Pawn(int column, int row, boolean isWhite) {
        super(column, row, isWhite, "pawn");
        

        //Initialize sightTo to empty array of length 4
        sees = new Square[4];
        hasMoved = false;
    }

    //Method to update the sightTo array.
    //Will be called at the beginning of the game and after every move of this piece.
    public void updateSight(Square[][] squares) {
        sees = new Square[4];

        if (isWhite()) {
            updateSightWP(squares);
        }
        else {
            updateSightBP(squares);
        }
    }

    public void updateSightWP(Square[][] squares) {
        //North check:
        if (!hasMoved) { //Check 1 & 2 squares north of pawn:
            if (squares[getRow() + 1][getColumn()].getCurrentPiece() == null) { //If no piece directly north
                sees[0] = squares[getRow() + 1][getColumn()];

                if (squares[getRow() + 2][getColumn()].getCurrentPiece() == null) { //If no piece two to north
                    sees[1] = squares[getRow() + 2][getColumn()];
                }
            }
        }
        else {
            if (squares[getRow() + 1][getColumn()].getCurrentPiece() == null) { //If no piece directly north
                sees[0] = squares[getRow() + 1][getColumn()];
            }
        }

        //NE Diagonal Check:
        if (getColumn() + 1 < 8 && squares[getRow() + 1][getColumn() + 1].getCurrentPiece() != null) {
            sees[2] = squares[getRow() + 1][getColumn() + 1];
        }

        //NW Diagonal Check:
        if (getColumn() - 1 >= 0 && squares[getRow() + 1][getColumn() - 1].getCurrentPiece() != null) {
            sees[3] = squares[getRow() + 1][getColumn() - 1];
        }
    }

    public void updateSightBP(Square[][] squares) {
        //South check:
        if (!hasMoved) { //Check 1 & 2 squares north of pawn:
            if (squares[getRow() - 1][getColumn()].getCurrentPiece() == null) { //If no piece directly north
                sees[0] = squares[getRow() - 1][getColumn()];

                if (squares[getRow() - 2][getColumn()].getCurrentPiece() == null) { //If no piece two to north
                    sees[1] = squares[getRow() - 2][getColumn()];
                }
            }
        }
        else {
            if (squares[getRow() - 1][getColumn()].getCurrentPiece() == null) { //If no piece directly north
                sees[0] = squares[getRow() - 1][getColumn()];
            }
        }

        //SE Diagonal Check:
        if (getColumn() + 1 < 8 && squares[getRow() - 1][getColumn() + 1].getCurrentPiece() != null) {
            sees[2] = squares[getRow() - 1][getColumn() + 1];
        }

        //SW Diagonal Check:
        if (getColumn() - 1 >= 0 && squares[getRow() - 1][getColumn() - 1].getCurrentPiece() != null) {
            sees[3] = squares[getRow() - 1][getColumn() - 1];
        }
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
