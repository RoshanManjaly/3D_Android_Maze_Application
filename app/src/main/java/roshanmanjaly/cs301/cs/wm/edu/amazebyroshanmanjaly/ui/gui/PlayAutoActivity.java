package roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.gui;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.R;
import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.falstad.BasicRobot;
import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.falstad.ManualDriver;
import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.falstad.MazeController;
import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.falstad.MazePanel;
import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.falstad.Singleton;

public class PlayAutoActivity extends AppCompatActivity {

    ImageButton playpause_button;
    boolean paused=true;

    Switch mapSwitch,solutionSwitch,wallsSwitch;
    Button nextScreen;
    Singleton maze;
    MazeController controller;
    ManualDriver robot;
    TextView batteryText,distanceText;
    ProgressBar battBar;
    Runnable doOneStep;
    int oneStepMillis=10;//The time allocated for the RobotDriver to perform a single step.
    public static Handler handler;
    MediaPlayer backgroundMusic;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_auto);
        String savedOrLoaded;
        if(!GeneratingActivity.loadFromFile)
            {
                savedOrLoaded="Maze saved to file ";
            }
        else
            {
                savedOrLoaded="Maze loaded from file ";
            }
        if (!getIntent().getBooleanExtra("did_not_load_did_not_save",true)) {}
        playPause();
        makeFinishButton();
        createSwitches();
        maze = maze.getInstance();
        controller = maze.getMazeController();

        maze.getMazeController().setMazePanel((MazePanel) findViewById(R.id.maze_panel));
        robot = controller.getDriver();
        controller.switchToPlayingScreen();
        controller.notifyViewerRedraw();
        for(int z : new int[5])
        {
            controller.keyDown(MazeController.UserInput.ZoomIn, 0);
        }
        battBar=(ProgressBar)findViewById(R.id.batteryProgress);
        battBar.setMax(3000);
        battBar.setProgress(3000);
        batteryText = (TextView) findViewById(R.id.batteryLabelNumbers);
        distanceText=(TextView)findViewById(R.id.manualDistanceTraveled);
        backgroundMusic= MediaPlayer.create(getBaseContext(), R.raw.motivation);

        updateBattery();


        handler=new Handler();

        doOneStep=new Runnable()
        {
            @Override
            public void run()
            {
                if (!paused)
                {
                    Log.v("starting action", "robot animation loop runnable");
                    if (robot.getRobot().getBatteryLevel() <= 0)
                    {
                        exitMaze(false);
                    } else if (robot.getRobot().isAtExit())
                    {
                        robot.getRobot().exitMaze();
                        exitMaze(true);
                    }
                    else
                    {
                        try
                        {
                            robot.drive2ExitOneStep();
                            updateBattery();
                            handler.postDelayed(this, oneStepMillis);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        Log.v("driving to exit", "energy consumed " + robot.getEnergyConsumption());
                    }
                }
            }
        };
    }
    void startGoing()
    {
        handler.postDelayed(doOneStep, oneStepMillis);
    }

    void makeFinishButton()
    {
        nextScreen = (Button)findViewById(R.id.finish_maze);
        nextScreen.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                exitMaze(true);
            }
        }
        );
    }

    void createSwitches()
    {
        mapSwitch= (Switch) findViewById(R.id.mapSwitch);
        solutionSwitch= (Switch) findViewById(R.id.solutionSwitch);
        wallsSwitch= (Switch) findViewById(R.id.wallsSwitch);
        final String TAG="switch change:";
        mapSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                Log.v(TAG, "map state");
                if(!wallsSwitch.isChecked() && !solutionSwitch.isChecked())
                {
                    maze.getMazeController().keyDown(MazeController.UserInput.ToggleLocalMap, 0);
                }
                maze.getMazeController().keyDown(MazeController.UserInput.ToggleFullMap,0);
            }
        }
        );
        solutionSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                Log.v(TAG, "solution state");
                if(!wallsSwitch.isChecked() && !mapSwitch.isChecked())
                {
                    maze.getMazeController().keyDown(MazeController.UserInput.ToggleLocalMap, 0);
                }
                maze.getMazeController().keyDown(MazeController.UserInput.ToggleSolution,0);
            }
        }
        );
        wallsSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                Log.v(TAG, "wall state");
                if(!solutionSwitch.isChecked() && !mapSwitch.isChecked()){
                    maze.getMazeController().keyDown(MazeController.UserInput.ToggleLocalMap,0);
                }
            }
        });
    }


    void playPause()
    {
        playpause_button = (ImageButton) findViewById(R.id.playPause);
        playpause_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String TAG = "playpause";
                if (paused)
                {
                    playpause_button.setImageResource(R.drawable.ic_pause_black_24dp);
                    playpause_button.setBackgroundColor(getResources().getColor(R.color.colorPlay));
                    paused = false;
                    Log.v(TAG, "Robot started");
                    startGoing();
                }
                else {
                    playpause_button.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    playpause_button.setBackgroundColor(getResources().getColor(R.color.colorPause));
                    paused = true;
                    Log.v(TAG, "Robot paused");
                }
            }
        }
        );
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Log.v("back pressed"," so yeah");
    }

    void exitMaze(boolean wonGame)
    {
        Intent finishScreen=new Intent(PlayAutoActivity.this,FinishActivity.class);
        finishScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finishScreen.putExtra("won game?",wonGame);
        startActivity(finishScreen);
    }

    void updateBattery()
    {
        batteryText.setText(""+(3000-robot.getEnergyConsumption())+"/3000");
        distanceText.setText("Distance traveled: "+robot.getPathLength());
        battBar.setProgress((int)(3000-robot.getEnergyConsumption()));
    }
    protected void onStart()
    {
        super.onStart();
        backgroundMusic.setLooping(true);
        backgroundMusic.start();
    }
    protected void onStop()
    {
        super.onStop();
        backgroundMusic.stop();
    }

}
