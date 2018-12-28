package roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.falstad;

import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.falstad.Robot.Direction;
import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.falstad.Robot.Turn;
import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.generation.CardinalDirection;

public class Explorer extends ManualDriver {

	int[][]visitcounts;
	public Explorer(MazeController maze) {
		super(maze);
		visitcounts=new int[getHeight()][getWidth()];
		try {
			visitcounts[getRobot().getCurrentPosition()[0]][getRobot().getCurrentPosition()[1]]++;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean drive2Exit() {
		while(!getRobot().hasStopped() && !getRobot().isAtExit()) {
			try {
				drive2ExitOneStep();
			}catch(Exception e){

			}
		}
		return false;	
	}

	@Override
	public boolean drive2ExitOneStep() throws Exception {
		int min=Integer.MAX_VALUE;
		Direction mindir = null;
		int x=0;
		int y=0;
		try {
			x=getRobot().getCurrentPosition()[0];
			y = getRobot().getCurrentPosition()[1];
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(getRobot().distanceToObstacle(Direction.FORWARD)!=0) {//no wall ahead
			CardinalDirection card=getRobot().directionToCardinal(Direction.FORWARD);
			if(card==CardinalDirection.North || card==CardinalDirection.South) {
				card=card.oppositeDirection();
			}
			int[]dir=card.getDirection();

			if(min>visitcounts[x+dir[0]][y+dir[1]]) {
				min=visitcounts[x+dir[0]][y+dir[1]];
				mindir=Direction.FORWARD;
			}
		}
		if(getRobot().distanceToObstacle(Direction.BACKWARD)!=0) {//no wall ahead
			CardinalDirection card=getRobot().directionToCardinal(Direction.BACKWARD);
			if(card==CardinalDirection.North || card==CardinalDirection.South) {
				card=card.oppositeDirection();
			}
			int[]dir=card.getDirection();
			try {
				if(min>visitcounts[x+dir[0]][y+dir[1]]) {
					min=visitcounts[x+dir[0]][y+dir[1]];
					mindir=Direction.BACKWARD;
				}
			}catch(ArrayIndexOutOfBoundsException aioobe) {

			}
		}
		if(getRobot().distanceToObstacle(Direction.LEFT)!=0) {//no wall ahead
			CardinalDirection card=getRobot().directionToCardinal(Direction.LEFT);
			if(card==CardinalDirection.North || card==CardinalDirection.South) {
				card=card.oppositeDirection();
			}
			int[]dir=card.getDirection();

			if(min>visitcounts[x+dir[0]][y+dir[1]]) {
				min=visitcounts[x+dir[0]][y+dir[1]];
				mindir=Direction.LEFT;
			}
		}
		if(getRobot().distanceToObstacle(Direction.RIGHT)!=0) {//no wall ahead
			CardinalDirection card=getRobot().directionToCardinal(Direction.RIGHT);
			if(card==CardinalDirection.North || card==CardinalDirection.South) {
				card=card.oppositeDirection();
			}
			int[]dir=card.getDirection();

			if(min>visitcounts[x+dir[0]][y+dir[1]]) {
				min=visitcounts[x+dir[0]][y+dir[1]];
				mindir=Direction.RIGHT;
			}
		}

		switch(mindir) {
			case BACKWARD:
				getRobot().rotate(Turn.AROUND);
			case LEFT:
				getRobot().rotate(Turn.LEFT);
				break;
			case RIGHT:
				getRobot().rotate(Turn.RIGHT);
				break;
			default:
				break;
		}
		getRobot().move(1, false);
		try {
			visitcounts[getRobot().getCurrentPosition()[0]][getRobot().getCurrentPosition()[1]]++;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.getRobot().isAtExit();
	}

}
