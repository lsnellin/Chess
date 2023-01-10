public class Rook extends Piece{

    public Rook(int column, int row, boolean isWhite) {
        super(column, row, isWhite, "rook");
        
        //Initialize sightTo to empty array of length 14.
        sees = new Square[14];
    }

    //Method to update the sightTo array.
    //Will be called at the beginning of the game and after every move of this piece.
    public void updateSight(Square[][] squares) {
        sees = new Square[14];
        int idx;

        //Check North, East, South, West sides
        idx = updateSightN(squares);
        idx = updateSightE(squares, idx);
        idx = updateSightS(squares, idx);
        updateSightW(squares, idx);
        
    }

    public int updateSightN(Square[][] squares) {
        //Set current square to the square directly north of rook
        int row = getRow() + 1;
        int col = getColumn();
        int i = 0;
        boolean hit = false;

        while(row < 8 && !hit) {
            //Update array with square and increase index
            sees[i++] = squares[row][col];

            if (squares[row][col].getCurrentPiece() != null) { //There is a piece on the square
                hit = true;

                if (squares[row][col].getCurrentPiece() instanceof King && squares[row][col].getCurrentPiece().isWhite() != isWhite()) {
                    squares[row][col].thisBoard.inCheck(!isWhite());
                }
            }
            else { //No piece on square
                row++;
            }
        }

        return 7 - getRow();
    }

    public int updateSightE(Square[][] squares, int idx) {
        //Set current square to the square directly east of rook
        int i = idx;
        int row = getRow();
        int col = getColumn() + 1;
        boolean hit = false;

        while(col < 8 && !hit) {
            //Update array with square and increase index
            sees[i++] = squares[row][col];

            if (squares[row][col].getCurrentPiece() != null) { //There is a piece on the square
                hit = true;

                if (squares[row][col].getCurrentPiece() instanceof King && squares[row][col].getCurrentPiece().isWhite() != isWhite()) {
                    squares[row][col].thisBoard.inCheck(!isWhite());
                }
            }
            else { //No piece on square
                col++;
            }
        }
        return idx + 7 - getColumn();
    }

    public int updateSightS(Square[][] squares, int idx) {
        //Set current square to the square directly south of rook
        int i = idx;
        int row = getRow() - 1;
        int col = getColumn();
        boolean hit = false;

        while(row >= 0 && !hit) {
            //Update array with square and increase index
            sees[i++] = squares[row][col];

            if (squares[row][col].getCurrentPiece() != null) { //There is a piece on the square
                hit = true;
                
                if (squares[row][col].getCurrentPiece() instanceof King && squares[row][col].getCurrentPiece().isWhite() != isWhite()) {
                    squares[row][col].thisBoard.inCheck(!isWhite());
                }
            }
            else { //No piece on square
                row--;
            }
        }

        return idx + getRow();
    }

    public void updateSightW(Square[][] squares, int idx) {
        //Set current square to the square directly west of rook
        int row = getRow();
        int col = getColumn() - 1;
        boolean hit = false;

        while(col >= 0 && !hit) {
            //Update array with square and increase index
            sees[idx++] = squares[row][col];

            if (squares[row][col].getCurrentPiece() != null) { //There is a piece on the square
                hit = true;
                
                if (squares[row][col].getCurrentPiece() instanceof King && squares[row][col].getCurrentPiece().isWhite() != isWhite()) {
                    squares[row][col].thisBoard.inCheck(!isWhite());
                }
            }
            else { //No piece on square
                col--;
            }
        }
    }

}
