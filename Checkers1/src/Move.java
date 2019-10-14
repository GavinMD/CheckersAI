public class Move {
    int from; // starting position of the checker
    int to; // end position of the checker
    int inter = -1;

    public Move(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public Move(int from, int to, int inter) {
        this.from = from;
        this.to = to;
        this.inter = inter;
    }

    public boolean isJump() { // checks if the move is a jump
        return to - from == 9 || to - from == 7 || from - to == 9 || from - to == 7 || inter != -1;
    }

    // checks if the moves are equal
    public boolean equals(Move other) {
        return other.from == this.from && other.to == this.to;
    }

    public String toString() {
        return "From: " + from + " To position: " + to;
    }

//		
}