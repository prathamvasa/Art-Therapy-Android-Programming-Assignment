package com.example.i851409.arttherapy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * This Class represents the Custom View that will enable the users to draw on the canvas
 */


public class DrawCustomView extends View{
    //Instantiating the paint brush and the path objects
    private Paint paint_brush = new Paint();
    private Path path_draw = new Path();

    //Implementing the constructors
    public DrawCustomView(Context context) {
        super(context);

        //Invoking initialSetting() to set up the paint brush and other properties
        initialSetting(null, 0);
    }

    //Will be using this constructor to set the various properties for the paint brush
    public DrawCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Invoking initialSetting() to set up the paint brush and other properties
        initialSetting(attrs, 0);

    }

    public DrawCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //Invoking initialSetting() to set up the paint brush and other properties
        initialSetting(attrs, defStyleAttr);
    }

    public DrawCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);

        //Invoking initialSetting() to set up the paint brush and other properties
        initialSetting(attrs, defStyleAttr);
    }


    public void initialSetting(AttributeSet attributeSet, int defStyleAttr){

        //Setting this property for a smoother brush strokes
        paint_brush.setAntiAlias(true);

        //Setting the color for the Paint Brush
        paint_brush.setColor(Color.BLACK);

        //Setting the corner display for the drawings done by the User
        //The corners will be seen circular in display
        paint_brush.setStrokeJoin(Paint.Join.ROUND);

        //Setting the style of the stroke of the paint brush
        paint_brush.setStyle(Paint.Style.STROKE);

        //Setting the width of the stroke of the paint brush
        paint_brush.setStrokeWidth(8f);
    }

    //Method that will enable the users to draw on the canvas
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawPath(path_draw, paint_brush);
    }

    //Method to define the path to be drawn when the paint brush touches the canvas screen when the user wants to draw something
    @Override
    public boolean onTouchEvent(MotionEvent event){

        //Defining the X and Y co-ordinates for the paint brush position
        float x_dir_point = event.getX();
        float y_dir_point = event.getY();

        //Adding a swith case to detect the direction in which the paint brush will move
        switch(event.getAction()){
            //When the user touches the screen with the brush, fetch those co-ordinates
            case MotionEvent.ACTION_DOWN:
                path_draw.moveTo(x_dir_point, y_dir_point);
                return true;
            //When user starts moving the paint brush, show a line to connect to those points
            case MotionEvent.ACTION_MOVE:
                path_draw.lineTo(x_dir_point, y_dir_point);
                break;
            //When the user lifts the hand from the paint brush, do nothing
            case MotionEvent.ACTION_UP:
                break;
            default:
                return false;
        }

        //Perform a repaint()
        invalidate();
        return true;

    }

    //Method to clear the drawings and the contents from the canvas
    public void clearCanvas() {

        path_draw.reset();

        invalidate();

    }
}
