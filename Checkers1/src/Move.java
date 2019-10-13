//class move creates a move object for ease of passing objects
public class Move{
    int fromRow, fromCol; //starting position of the checker
    int toRow, toCol; //end position of the checker
    
    public Move(int frow, int fcol, int trow, int tcol) {
        this.fromRow = frow;
        this.fromCol = fcol;
        this.toRow = trow;
        this.toCol = tcol;
    }
    
    public boolean isJump() { //checks if the move is a jump
        return fromRow - toRow == 2 || fromRow - toRow ==-2;
    }
    
    public boolean equals(Move other) {
        return this.fromCol == other.fromCol && this.fromRow == other.fromRow &&
        this.toCol == other.toCol && this.toRow == other.toRow;
    }
}