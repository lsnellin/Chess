public class King extends Piece{

    public King(int column, int row, boolean isWhite) {
        super(column, row, isWhite, "king");

        //Initialize sightTo to empty array of length 8
        sees = new Square[8];
        
    }

    //Method to update the sightTo array.
    //Will be called at the beginning of the game and after every move of this piece.
    public void updateSight(Square[][] squares) {
        sees = new Square[8];
        checkSquare(squares, 1, 0, 0);
        checkSquare(squares, 1, 1, 1);
        checkSquare(squares, 0, 1, 2);
        checkSquare(squares, -1, 1, 3);
        checkSquare(squares, -1, 0, 4);
        checkSquare(squares, -1, -1, 5);
        checkSquare(squares, 0, -1, 6);
        checkSquare(squares, 1, -1, 7);
    }

    public void checkSquare(Square[][] squares, int vert, int hori, int idx) {
        int row = vert + getRow();
        int column = hori + getColumn();

        if (((row >= 0) && (row < 8)) && ((column >= 0) && (column < 8))) {
            sees[idx] = squares[row][column];

            if (squares[row][column] != null) {
                //Update seenFrom:
            }
        }
    }
}
