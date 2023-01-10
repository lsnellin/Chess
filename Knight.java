public class Knight extends Piece{
    public Knight(int column, int row, boolean isWhite) {
        super(column, row, isWhite, "knight");

        //Initialize sightTo to empty array of length 8
        sees = new Square[8];
    }

    //Method to update the sightTo array.
    //Will be called at the beginning of the game and after every move of this piece.
    public void updateSight(Square[][] squares) {
        sees = new Square[8];
        checkSquare(squares, 2, 1, 0);
        checkSquare(squares, 1, 2, 1);
        checkSquare(squares, -1, 2, 2);
        checkSquare(squares, -2, 1, 3);
        checkSquare(squares, -2, -1, 4);
        checkSquare(squares, -1, -2, 5);
        checkSquare(squares, 1, -2, 6);
        checkSquare(squares, 2, -1, 7);
    }

    public void checkSquare(Square[][] squares, int vert, int hori, int idx) {
        int row = vert + getRow();
        int col = hori + getColumn();

        if (((row >= 0) && (row < 8)) && ((col >= 0) && (col < 8))) {
            sees[idx] = squares[row][col];

            if (squares[row][col] != null) {

                if (squares[row][col].getCurrentPiece() instanceof King && squares[row][col].getCurrentPiece().isWhite() != isWhite()) {
                    squares[row][col].thisBoard.inCheck(!isWhite());
                }
            }
        }
    }
}
