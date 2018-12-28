package roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.falstad;

import java.util.Arrays;

import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.falstad.MazeController.UserInput;
import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.falstad.Robot.Direction;
import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.falstad.Robot.Turn;
import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.generation.CardinalDirection;

public class Wizard extends ManualDriver {
	
	public Wizard(MazeController maze) {
		super(maze);
		System.out.println(getDistance());
	}
	
	@Override
	public void move(MazeController.UserInput key) {
	}
	
	@Override
	public boolean drive2Exit() throws Exception{
		getRobot().getMaze().keyDown(UserInput.ToggleLocalMap,0);
		getRobot().getMaze().keyDown(UserInput.ToggleFullMap,0);
		getRobot().getMaze().keyDown(UserInput.ToggleSolution,0);


		while(!getRobot().hasStopped() && !getRobot().isAtExit()) {
			drive2ExitOneStep();
		}
		if(getRobot().isAtExit()) {
			getRobot().exitMaze();
			return true;
		}
		return false;
	}

	public boolean drive2ExitOneStep() throws Exception{
		CardinalDirection robotDirection;
		int[]nextNeighbor;
		robotDirection=getRobot().getCurrentDirection();
		nextNeighbor=getRobot().getMaze().getMazeConfiguration().getNeighborCloserToExit(getRobot().getCurrentPosition()[0], getRobot().getCurrentPosition()[1]);
		CardinalDirection nearestNeighbor=CardinalDirection.getDirection(nextNeighbor[0]-getRobot().getCurrentPosition()[0],getRobot().getCurrentPosition()[1]-nextNeighbor[1]);
		if(nearestNeighbor==robotDirection) {
			getRobot().move(1, false);
		}else if(nearestNeighbor==robotDirection.oppositeDirection()) {
			getRobot().rotate(Turn.AROUND);
		}else if(nearestNeighbor==robotDirection.rotateClockwise()) {
			getRobot().rotate(Turn.RIGHT);
		}else if(nearestNeighbor==robotDirection.oppositeDirection().rotateClockwise()) {
			getRobot().rotate(Turn.LEFT);
		}
		return getRobot().isAtExit();
	}
}
