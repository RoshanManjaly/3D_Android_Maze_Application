package roshanmanjaly.cs301.cs.wm.edu.amazebyroshanmanjaly.ui.falstad;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;

/**
 * Handles maze graphics.
 */
public class MazePanel extends View {
    // TODO: please check http://developer.android.com/guide/topics/graphics/2d-graphics.html
    // on how to implement your own View class
    //needs to tell the UI it needs to draw, using the update() method
    //Mazepanel needs to tell the UI invalidate()
    /**
     * Constructor with one context parameter.
     * @param context
     */
    private Bitmap mBitmap;
    private Paint mPaint;
    private Canvas canvas;
    private int color;


    public MazePanel(Context context) {
        super(context);
        mPaint = new Paint();
        mBitmap = Bitmap.createBitmap(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(mBitmap);


	//add a little test method with all the drawing methods
        //draw bitmap with onDraw
        // call super class constructor as necessary
	// TODO: initialize instance variables as necessary
    }
    /**
     * Constructor with two parameters: context and attributes.
     * @param context
     * @param app
     */
    public MazePanel(Context context, AttributeSet app) {
        super(context, app);
        mPaint = new Paint();
        mBitmap = Bitmap.createBitmap(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(mBitmap);
        // call super class constructor as necessary
	// TODO: initialize instance variables as necessary
    }
    /**
     * Draws given canvas.
     *  canvas
     */
    @Override
	public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
//        Bitmap b = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
//        c = new Canvas(b);
        // TODO: draw bitmap
        //c.drawBitmap(mBitmap, 0, 0, mPaint);

        //copies the bitmap created from earlier - the canvas copies the other canvas
    }
    
    /**
     * Measures the view and its content to determine the measured width and the measured height.
     * @param width
     * @param height
     */
    @Override
    public void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        setMeasuredDimension(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
	// as described for superclass method
    }

    /**
     * Updates maze graphics.
     */
    public void update() {
        invalidate();
    }
    

    /**
     * Sets paint object color attribute to given color.
     * @param colorInt
     */
    public void setColor(int colorInt) {
        mPaint.setColor(colorInt);
        // TODO: set the current color
    }
    
    /**
     * Takes in color integer values [0-255], returns corresponding color-int value. 
     *  color values
     */
    public static int getColorEncoding(int red, int green, int blue) {
	// TODO: provide rgb color encoding
    return Color.rgb(red, green, blue);}
    
    /**
     * Returns the RGB value representing the current color. 
     * @return integer RGB value
     */
    public int getColor() {
	return getColorEncoding(Color.red(color), Color.green(color), Color.blue(color));// TODO return the current color setting
    }
    
    /**
     * Takes in rectangle params, fills rectangle in canvas based on these. 
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void fillRect(int x, int y, int width, int height) {
	// draw a filled rectangle on the canvas, requires decision on its color
        //mPaint.setColor(getColor());
        mPaint.setStyle(Paint.Style.FILL);
        Rect rectangle = new Rect(x, y, x+width, y+height);
        canvas.drawRect(rectangle, mPaint);
    }
    
    /**
     * Takes in polygon params, fills polygon in canvas based on these. 
     * Paint is always that for corn.
     * @param xPoints
     * @param yPoints
     * @param nPoints
     */
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints){
	// translate the points into a path
	// draw a path on the canvas
        //mPaint.setColor(getColor());
        mPaint.setStyle(Paint.Style.FILL);

        Path path = new Path();
        path.moveTo(xPoints[0],yPoints[0]);
        for (int i=1;i<nPoints;i++){
            path.lineTo(xPoints[i],yPoints[i]);
        }
        path.lineTo(xPoints[0], yPoints[0]);
        canvas.drawPath(path,mPaint);
    }
    
    /**
     * Takes in line params, draws line in canvas based on these. 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void drawLine(int x1, int y1, int x2, int y2) {
	// TODO: draw a line on the canvas
        //mPaint.setColor(getColor());
        //mPaint.setStrokeWidth(10);
        canvas.drawLine(x1, y1, x2,y2, mPaint);
    }
    
    /**
     * Takes in oval params, fills oval in canvas based on these. 
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void fillOval(int x, int y, int width, int height) {
	// TODO: draw an oval on the canvas
        //mPaint.setColor(getColor());
        mPaint.setStyle(Paint.Style.FILL);
        RectF oval = new RectF(x, y, x+width, y+height);
        canvas.drawOval(oval, mPaint);
    }
    public Canvas getCanvas() {
        return canvas;
    }


}
