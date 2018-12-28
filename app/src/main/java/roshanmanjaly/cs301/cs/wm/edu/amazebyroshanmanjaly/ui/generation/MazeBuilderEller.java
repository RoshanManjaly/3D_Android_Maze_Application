package roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.generation;

import java.util.ArrayList;

public class MazeBuilderEller extends MazeBuilder implements Runnable {
	
	/**
	 * This class has the responsibility to create a maze of given dimensions (width, height) 
	 * together with a solution based on a distance matrix.
	 * The MazeBuilder implements Runnable such that it can be run a separate thread.
	 * The MazeFactory has a MazeBuilder and handles the thread management. 
	 * 
	 * The maze is built with  Eller's algorithm.
	 * @author Jeremy Elkayam
	 */
	//TODO make it actually use eller's alg
	public MazeBuilderEller() {
		super();
		System.out.println("MazeBuilderPrim uses Prim's algorithm to generate maze.");
	}
	
	public MazeBuilderEller(boolean det) {
		super(det);
		System.out.println("MazeBuilderPrim uses Prim's algorithm to generate maze.");
	}
	

}
