package roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.gui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
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
import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.falstad.ManualDriver;
import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.falstad.MazeController;
import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.falstad.MazePanel;
import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.falstad.Singleton;

public class PlayManualActivity extends AppCompatActivity
{

    ImageButton button_up, button_right, button_left, button_down;

    Switch mapSwitch,solutionSwitch,wallsSwitch;
    Button nextScreen;
    Singleton maze;
    MazeController controller;
    ManualDriver robot;
    TextView batteryText,distanceText;
    ProgressBar battBar;
    MediaPlayer backgroundMusic;
    int x = 0;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_manual);

        String savedOrLoaded;
        if(!GeneratingActivity.loadFromFile)
        {
            savedOrLoaded="Maze saved to file ";
        }else{
            savedOrLoaded="Maze loaded from file ";
        }
        if(!getIntent().getBooleanExtra("did_not_load_did_not_save",true))
        {
            x=1;
        }
        makeFinishButton(new Intent(PlayManualActivity.this,FinishActivity.class));
        createSwitches();
        maze = maze.getInstance();
        controller = maze.getMazeController();
        batteryText = (TextView) findViewById(R.id.batteryLabelNumbers);
        distanceText=(TextView)findViewById(R.id.manualDistanceTraveled);
        backgroundMusic=MediaPlayer.create(getBaseContext(), R.raw.money);


        maze.getMazeController().setMazePanel((MazePanel) findViewById(R.id.maze_panel));
        robot = controller.getDriver();
        controller.switchToPlayingScreen();
        controller.notifyViewerRedraw();
        battBar=(ProgressBar)findViewById(R.id.batteryProgress);
        battBar.setMax(3000);
        battBar.setProgress(3000);
        buttonPress();
        updateBattery();
    }


    public void makeFinishButton(final Intent mainscreen)
    {
        nextScreen = (Button)findViewById(R.id.finish_maze);
        nextScreen.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                mainscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(mainscreen);
            }
        }
        );
    }

    public void createSwitches(){
        mapSwitch= (Switch) findViewById(R.id.mapSwitch);
        solutionSwitch= (Switch) findViewById(R.id.solutionSwitch);
        wallsSwitch= (Switch) findViewById(R.id.wallsSwitch);
        final String TAG="switch change:";
        mapSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                Log.v(TAG, "map state");
                if(!wallsSwitch.isChecked() && !solutionSwitch.isChecked())
                {
                    maze.getMazeController().keyDown(MazeController.UserInput.ToggleLocalMap, 0);
                }
                maze.getMazeController().keyDown(MazeController.UserInput.ToggleFullMap,0);
            }
        });
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
        });
        wallsSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                Log.v(TAG, "wall state");
                if(!solutionSwitch.isChecked() && !mapSwitch.isChecked())
                {
                    maze.getMazeController().keyDown(MazeController.UserInput.ToggleLocalMap,0);
                }
            }
        }
        );
    }

    public void buttonPress(){
        final String TAG="button pressed: ";
        button_down = (ImageButton) findViewById(R.id.downArrow);
        button_left = (ImageButton) findViewById(R.id.leftArrow);
        button_right = (ImageButton) findViewById(R.id.rightArrow);
        button_up = (ImageButton) findViewById(R.id.upArrow);
        button_down.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.v(TAG,"down");
                //Snackbar.make(view,"Down pressed",Snackbar.LENGTH_SHORT).show();
                robot.move(MazeController.UserInput.Down);
                updateBattery();

            }
        });
        button_up.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.v(TAG,"up");
                robot.move(MazeController.UserInput.Up);
                updateBattery();
                try
                {
                    if (robot.getRobot().isOutside())
                    {
                        Log.v("robot status","won the game");
                        exitMaze(true);
                    }
                }
                catch(Exception e)
                {

                }
            }
        });
        button_left.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.v(TAG,"left");
                robot.move(MazeController.UserInput.Left);
                updateBattery();
            }
        }
        );
        button_right.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Log.v(TAG,"right");
                robot.move(MazeController.UserInput.Right);
                updateBattery();
            }
        }
        );


    }

    void updateBattery()
    {
        if(robot.getEnergyConsumption()>=3000)
        {
            exitMaze(false);
        }
        batteryText.setText(""+(3000-robot.getEnergyConsumption())+"/3000");
        distanceText.setText("Distance traveled: "+robot.getPathLength());
        battBar.setProgress((int)(3000-robot.getEnergyConsumption()));

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Log.v("back pressed"," so yeah");
    }

    void exitMaze(boolean wonGame)
    {
        Intent finishScreen=new Intent(PlayManualActivity.this,FinishActivity.class);
        finishScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finishScreen.putExtra("won game?",wonGame);
        startActivity(finishScreen);
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
