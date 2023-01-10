public class Bishop extends Piece{
    public Bishop(int column, int row, boolean isWhite) {
        super(column, row, isWhite, "bishop");

        //Initialize sightTo to empty array of length 13
        sees = new Square[13];
    }

    //Method to update the sightTo array.
    //Will be called at the beginning of the game and after every move of this piece.
    public void updateSight(Square[][] squares) {
        sees = new Square[13];
        int idx;

        //Check North, East, South, West sides
        idx = updateSightNE(squares);
        idx = updateSightSE(squares, idx);
        idx = updateSightSW(squares, idx);
        updateSightNW(squares, idx);
        
    }

    public int updateSightNE(Square[][] squares) {

        int col = getColumn() + 1;
        int row = getRow() + 1;
        int i = 0;
        boolean hit = false;

        while(row < 8 && col < 8 && !hit) {
            sees[i++] = squares[row][col];

            if (squares[row][col].getCurrentPiece() != null) {
                hit = true;

                if (squares[row][col].getCurrentPiece() instanceof King && squares[row][col].getCurrentPiece().isWhite() != isWhite()) {
                    squares[row][col].thisBoard.inCheck(!isWhite());
                }
            }
            else {
                col++;
                row++;
            }
        }

        return Math.min(7 - getRow(), 7 - getColumn());
    }

    public int updateSightSE(Square[][] squares, int idx) {
        int col = getColumn() + 1;
        int row = getRow() - 1;
        int i = idx;
        boolean hit = false;

        while(row >= 0 && col < 8 && !hit) {
            sees[i++] = squares[row][col];

            if (squares[row][col].getCurrentPiece() != null) {
                hit = true;

                if (squares[row][col].getCurrentPiece() instanceof King && squares[row][col].getCurrentPiece().isWhite() != isWhite()) {
                    squares[row][col].thisBoard.inCheck(!isWhite());
                }
            }
            else {
                col++;
                row--;
            }
        }

        return idx + Math.min(getRow(), 7 - getColumn());
    }

    public int updateSightSW(Square[][] squares, int idx) {
        int col = getColumn() - 1;
        int row = getRow() - 1;
        int i = idx;
        boolean hit = false;

        while(row >= 0 && col >= 0 && !hit) {
            sees[i++] = squares[row][col];

            if (squares[row][col].getCurrentPiece() != null) {
                hit = true;

                if (squares[row][col].getCurrentPiece() instanceof King && squares[row][col].getCurrentPiece().isWhite() != isWhite()) {
                    squares[row][col].thisBoard.inCheck(!isWhite());
                }
            }
            else {
                col--;
                row--;
            }
        }

        return idx + Math.min(getRow(), getColumn());
    }

    public void updateSightNW(Square[][] squares, int idx) {
        int col = getColumn() - 1;
        int row = getRow() + 1;
        int i = idx;
        boolean hit = false;

        while(row < 8 && col >= 0 && !hit) {
            sees[i++] = squares[row][col];

            if (squares[row][col].getCurrentPiece() != null) {
                hit = true;

                if (squares[row][col].getCurrentPiece() instanceof King && squares[row][col].getCurrentPiece().isWhite() != isWhite()) {
                    squares[row][col].thisBoard.inCheck(!isWhite());
                }
            }
            else {
                col--;
                row++;
            }
        }
    }
}
