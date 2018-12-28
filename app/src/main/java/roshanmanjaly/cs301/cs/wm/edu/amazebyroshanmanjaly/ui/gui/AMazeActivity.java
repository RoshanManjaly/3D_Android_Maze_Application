package roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.gui;

import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.R;
import roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.falstad.Singleton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;



public class AMazeActivity extends AppCompatActivity
{

    Spinner generationSpinner,driverSpinner;
    ArrayAdapter<CharSequence> generationAdapter;
    ArrayAdapter<CharSequence> driverAdapter;
    SeekBar difficultySlider;
    TextView difficultyText;
    public static String generation_alg,robot_driver;
    MediaPlayer backgroundMusic;
    CheckBox loadMaze;
    Singleton maze;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amaze);
        backgroundMusic=MediaPlayer.create(getBaseContext(), R.raw.money);
        loadMaze = findViewById(R.id.loadFile);
    }

    void doMusic(){
        backgroundMusic.setLooping(true);
        backgroundMusic.start();
    }

    protected void createSpinners()
    {
        final String TAG="selection: ";
        generationSpinner=findViewById(R.id.generation_spinner);
        generationAdapter=ArrayAdapter.createFromResource(this, R.array.generation_algs,android.R.layout.simple_spinner_item);
        generationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        generationSpinner.setAdapter(generationAdapter);
        generationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                Log.v(TAG,"algorithm "+adapterView.getItemAtPosition(i));
                generation_alg= adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
            }
        }
        );
        driverSpinner=findViewById(R.id.driver_spinner);
        driverAdapter=ArrayAdapter.createFromResource(this, R.array.drivers,android.R.layout.simple_spinner_item);
        driverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        driverSpinner.setAdapter(driverAdapter);
        driverSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                Log.v(TAG,"driver "+adapterView.getItemAtPosition(i));
                robot_driver= adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    protected void createDifficultyBar() {
        final String TAG = "difficulty_slider";
        difficultySlider = findViewById(R.id.difficultySlider);
        difficultyText = findViewById(R.id.difficultyText);
        difficultySlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                difficultyText.setText("" + progress);
                difficultyText.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
                Log.v(TAG,"skill level changed");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                Log.v(TAG, "Skill Level Selected: " + difficultySlider.getProgress());
            }
        }
        );

    }

    protected void onStart()
    {
        super.onStart();
        maze=maze.getInstance();
        maze.killInstance();
        createSpinners();
        createDifficultyBar();
        doMusic();
    }

    protected void onStop()
    {
        super.onStop();
        backgroundMusic.stop();
    }

    public void toLoadScreen(final View view)
    {
        nextScreen();
    }

    public void nextScreen()
    {

        final String TAG = "next screen";
        Log.v(TAG, "to the loading screen!");
        Intent intent = new Intent(AMazeActivity.this, GeneratingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("driver_selected", robot_driver);
        intent.putExtra("builder_selected", generation_alg);
        intent.putExtra("skillLevel_selected", difficultySlider.getProgress());
        intent.putExtra("load_from_file", loadMaze.isChecked());
        Log.v("load from file",Boolean.toString(loadMaze.isChecked()));
        startActivity(intent);
    }



}