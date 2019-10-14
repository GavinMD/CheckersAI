import java.util.LinkedList;
import java.util.Random;

public class AI{

    int color;
    Checker[] board;
    LinkedList<Move> moves;

    AI(int c){
        color = c;
    }

    public Move doSomething(LinkedList<Move> m){

        Random r = new Random();
        int choose = r.nextInt(m.size());
        
        //print AI moves
        // for (int i = 0; i < m.size(); i++) {
		// 	System.out.println(m.get(i).toString());
        // }
        
        return m.get(choose);
    }




}