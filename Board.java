import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Board extends JComponent implements Runnable {

    //Fields:
    public Move currentMove;
    public boolean whiteMove;
    public JLabel movingPieceIcon;

    //JComponents:
    JPanel chessBoard = new JPanel();
    JPanel statistics = new JPanel();
    JFrame frame = new JFrame("Chess");
    Square[][] squares;

    public void run() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Initialize Layouts
        chessBoard.setLayout(new GridLayout(8,8));
        statistics.setLayout(new BoxLayout(statistics, BoxLayout.Y_AXIS));
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
        
        //Initialize Chess Board Content
        for(int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                chessBoard.add(squares[i][j]);
            }
        }
        chessBoard.setSize(800,800);

        BoxListener bl = new BoxListener(this);
        chessBoard.addMouseListener(bl);
        chessBoard.addMouseMotionListener(bl);

        //Initialize Statistic Section:
        statistics.setSize(100, 800);
        statistics.add(new JButton("Text Here"));

        frame.getContentPane().add(chessBoard);
        frame.getContentPane().add(statistics);
        frame.pack();
        frame.setSize(900,800);
        frame.setVisible(true);

    }

    public static class BoxListener extends MouseAdapter
    {
        private Board board;
        private Piece movingPiece;
        private Square clickedSquare;
        private Square mouseOverSquare;

        public BoxListener(Board board) {
            this.board = board;
        }

    	public void mousePressed(MouseEvent me)
        {
            if (!SwingUtilities.isLeftMouseButton(me)){
                return;
            }
            //Obtain variables to modify:
            JPanel chessBoard = (JPanel) me.getSource();
            clickedSquare = (Square) chessBoard.getComponentAt(me.getPoint());
            //Initialize mouseOverSquare so that clicking and releasing on the same square doesn't cause errors:
            mouseOverSquare = clickedSquare;
            movingPiece = clickedSquare.getCurrentPiece();

            //Create a pending move if there is no move currently
            if (board.currentMove == null) {
                board.move(clickedSquare);

                if (movingPiece != null && board.whiteMove == movingPiece.isWhite()) {
                    board.showMovingPieceIcon(clickedSquare.scaledIcon, me);
                    clickedSquare.hideIcon();
                    board.chessBoard.repaint();
                }
            }
        }

        public void mouseReleased(MouseEvent me) {
            JPanel chessBoard = (JPanel) me.getSource();
            Square releasedSquare = (Square) chessBoard.getComponentAt(me.getPoint());

            if (clickedSquare.getCurrentPiece() != null) {
                clickedSquare.showIcon();
            }
            board.hideMovingPieceIcon();

            //If the square that the mouse was released on is the same square, then do nothing
            Move move = board.currentMove;
            if (move != null && releasedSquare != move.getSquareFrom() && releasedSquare != null) {
                board.move(releasedSquare);
            }
            mouseOverSquare.removeOutline();
            board.frame.repaint();
        }

        public void mouseDragged(MouseEvent me) {
            
            if (mouseOverSquare != null) {
                //Removes outline from old square
                mouseOverSquare.removeOutline();
                mouseOverSquare.repaint();

                
            }
            try {
                //Add outline to new square:
                mouseOverSquare = (Square) board.chessBoard.getComponentAt(me.getPoint());
                mouseOverSquare.addOutline();
                mouseOverSquare.repaint();
                //Update moving piece location:
                board.movingPieceIcon.setLocation(me.getX() - 50, me.getY() - 50);
            }
            catch(NullPointerException e) {

            }
            catch(ClassCastException e) {

            }
        }
    }

    public Board() {
        //Initialize the squares with rows and columns:
        squares = new Square[8][8];
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new Square(i, j, this);
            }
        }

        //Update lines of sight for each piece:
        updateAllPieceSight();
        whiteMove = true; //White starts the game, so set white move to true
    }

    public void move(Square square) {
        if (currentMove == null) {
            if (square.getCurrentPiece() != null && square.getCurrentPiece().isWhite() == whiteMove) {
                currentMove = new Move(square);
            }
        }
        else {

            if (currentMove.moveTo(square)) {
                whiteMove = !whiteMove; //Flip the turn to the other side
            }
            
            updateAllPieceSight();

            currentMove = null;
        }
    }

    public void showMovingPieceIcon(Image icon, MouseEvent me) {
        //Create Logo:
        movingPieceIcon = new JLabel(new ImageIcon(icon));

        //Add Logo to screen
        frame.getLayeredPane().add(movingPieceIcon, JLayeredPane.DRAG_LAYER);
        movingPieceIcon.setSize(100,100);
        

        //Update the location of the logo to follow mouse:
        Point location = new Point(me.getPoint());

        location.x -= 50;
        location.y -= 50;

        movingPieceIcon.setLocation(location);
    
    }

    public void hideMovingPieceIcon() {
        try {
            frame.getLayeredPane().remove(movingPieceIcon);
        }
        catch (NullPointerException e) {

        }
    }

    public void updateAllPieceSight() {
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (squares[i][j].getCurrentPiece() != null) {
                    squares[i][j].getCurrentPiece().updateSight(squares);
                }
            }
        }
    }

    //Method to handle checks
    //The boolean parameter is the color of the king in check
    public void inCheck(boolean isWhite) {
        //Option 1 -- A piece moved and put its own king in check:
        if (whiteMove == !isWhite) {
            currentMove.moveBack();
            whiteMove = !whiteMove; //Let that color move again, since the move was invalid:
            return;
        }
        //Option 2 -- A piece just moved and put the opposing king in check:
        //TODO see if it is checkmate:



        

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Board());
    }
}
