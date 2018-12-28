package roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.gui;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.R;
import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.falstad.Singleton;

public class FinishActivity extends AppCompatActivity {

    Button restart;
    TextView winLoseText,debriefText;
    boolean wonGame;
    Singleton maze;
    ImageView face;

    @SuppressLint("SetTextI18n")
    @Override

    /*
    Called when the activity is created. Sets up the activity's layout

    @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        //init();
        maze = Singleton.getInstance();
        makeRestartButton();
        winLoseText = findViewById(R.id.winLoseText);
        debriefText = findViewById(R.id.debriefText);
        wonGame = getIntent().getBooleanExtra("won game?", false);
        Log.v("smiley tag", "" + R.drawable.yess);
        face = findViewById(R.id.smileyFrown);
        final MediaPlayer jingle;
        if (wonGame) {
            winLoseText.setText("You win!");
            face.setImageResource(R.drawable.yess
            );
            jingle = MediaPlayer.create(this, R.raw.win);
        } else {
            winLoseText.setText("You lose!");
            face.setImageResource(R.drawable.rip);
            jingle = MediaPlayer.create(this, R.raw.lose);
        }
        float final_batt = maze.getMazeController().getDriver().getRobot().getBatteryLevel();
        if (final_batt < 0) {
            final_batt = 0;
        }

        debriefText.setText("Your final battery level was: " + final_batt + "\n\nYour distance traveled was: " + maze.getMazeController().getDriver().getRobot().getOdometerReading() + "\n\nPress the Restart button to play again!");
        jingle.setLooping(false);
        jingle.start();
        maze.killInstance();

    }

    void makeRestartButton()
    {
        restart = findViewById(R.id.restart);
        restart.setOnClickListener(new View.OnClickListener()
                                      {
                                          public void onClick(View view)
                                          {
                                              exitMaze();
                                          }
                                      }
        );

    }

    void exitMaze()
    {
        Intent mainscreen=new Intent(FinishActivity.this, AMazeActivity.class);
        mainscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(mainscreen);
    }




    @Override
    public void onBackPressed(){
        super.onBackPressed();

    }
}
