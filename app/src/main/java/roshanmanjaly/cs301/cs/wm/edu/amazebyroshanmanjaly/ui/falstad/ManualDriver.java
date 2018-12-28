package roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.falstad;

import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.generation.Distance;

public class ManualDriver implements RobotDriver {

	private BasicRobot robot;
	private int w,h;
	private Distance dist;
	
	public ManualDriver(MazeController maze) {
		robot=new BasicRobot();
		robot.setMaze(maze);
		robot.getMaze().setDriver(this);
		//System.out.println(maze);
		//System.out.println(maze.getMazeConfiguration());
		//System.out.print("Distance address ");
		//System.out.println(maze.getMazeConfiguration().getMazedists());
		setDistance(maze.getMazeConfiguration().getMazedists());
		setDimensions(maze.getMazeConfiguration().getHeight(),maze.getMazeConfiguration().getWidth());
	}
	
	@Override
	public void setRobot(Robot r) {
		robot=(BasicRobot)r;

	}
	
	public BasicRobot getRobot() {
		return robot;
	}
	
	public Distance getDistance() {
		return dist;
	}

	@Override
	public void setDimensions(int width, int height) {
		w=width;
		h=height;
	}
	
	public int getWidth() {
		return w;
	}
	public int getHeight() {
		return h;
	}

	@Override
	public void setDistance(Distance distance) {
		//System.out.println("Setting distance");
		dist=distance;
		//System.out.println("Distance set");
		
	}

	@Override
	public boolean drive2Exit() throws Exception {
		return false;
	}

	public boolean drive2ExitOneStep() throws Exception {
		return false;
	}

	@Override
	public float getEnergyConsumption() {
		return 3000-robot.getBatteryLevel();
	}


	@Override
	public int getPathLength() {
		return robot.getOdometerReading();
	}
	public void move(MazeController.UserInput key) {
		switch(key) {
		case Down:
			robot.rotate(Robot.Turn.AROUND);
			break;
		case Left:
			robot.rotate(Robot.Turn.LEFT);
			break;
		case Right:
			robot.rotate(Robot.Turn.RIGHT);
			break;
		case Up:
			robot.move(1, true);
			break;
		default:
			break;
		
		}
	}
	/*
	public void reset() {
		MazeController maze=robot.getMaze();
		robot=new BasicRobot();
		robot.setMaze(maze);
		robot.getMaze().setDriver(this);
		setDistance(maze.getMazeConfiguration().getMazedists());
	}
	*/
}
