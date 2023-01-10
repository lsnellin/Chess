public class Queen extends Piece{
    public Queen(int column, int row, boolean isWhite) {
        super(column, row, isWhite, "queen");

        //Initialize sightTo to empty array of length 27
        sees = new Square[27];
    }

    //Method to update the sightTo array.
    //Will be called at the beginning of the game and after every move of this piece.
    public void updateSight(Square[][] squares) {
        sees = new Square[27];
        int idx;

        //Check N, NE, E, SE, S, SW, and W sides
        idx = updateSightN(squares);
        idx = updateSightNE(squares, idx);

        idx = updateSightE(squares, idx);
        idx = updateSightSE(squares, idx);

        idx = updateSightS(squares, idx);
        idx = updateSightSW(squares, idx);

        idx = updateSightW(squares, idx);
        updateSightNW(squares, idx);
        
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

    public int updateSightW(Square[][] squares, int idx) {
        //Set current square to the square directly west of rook
        int row = getRow();
        int i = idx;
        int col = getColumn() - 1;
        boolean hit = false;

        while(col >= 0 && !hit) {
            //Update array with square and increase index
            sees[i++] = squares[row][col];

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

        return idx + getColumn();
    }

    public int updateSightNE(Square[][] squares, int idx) {

        int col = getColumn() + 1;
        int row = getRow() + 1;
        int i = idx;
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

        return idx + Math.min(7 - getRow(), 7 - getColumn());
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
