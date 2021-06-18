package cz.hernik.kaku.helper;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import cz.hernik.kaku.R;

public class DrawView extends View {

    private Path drawPath;
    private Paint canvasPaint;
    private Paint drawPaint;
    // color
    private int paintColor;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private float currentBrushSize, lastBrushSize;
    private String currChar;

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0 , 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldwidth, int oldheight) {
        super.onSizeChanged(width, height, oldwidth, oldheight);

        canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
        setCharacter(currChar); // is it good to leave it here?
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawPath.lineTo(touchX, touchY);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        // redraw
        invalidate();
        return true;
    }

    private void init(){
        currentBrushSize = 5;
        lastBrushSize = currentBrushSize;

        // check for dark mode
        int nightModeFlags =
                getContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        if(nightModeFlags == Configuration.UI_MODE_NIGHT_YES){
            paintColor = 0xFFFFFFFF;
        }
        else{
            paintColor = 0xFF000000;
        }

        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(currentBrushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    public void setCurrent(String s){
        currChar = s;
    }

    private void setCharacter(String s){
        currChar = s;
        Paint textPaint = new Paint();
        textPaint.setTextAlign(Paint.Align.CENTER);
        int text;
        int nightModeFlags =
                getContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        if(nightModeFlags == Configuration.UI_MODE_NIGHT_YES){
            text = Color.argb(100,255,255,255);
        }
        else{
            text = Color.argb(100,0,0,0);
        }
        textPaint.setColor(text);
        textPaint.setTypeface(ResourcesCompat.getFont(getContext(),R.font.stroke_orders));
        textPaint.setTextSize(600);

        drawCanvas.drawText(s, drawCanvas.getWidth()/2,(int) ((drawCanvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)),textPaint); // https://stackoverflow.com/a/11121873
    }

    public void eraseAll() {
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        setCharacter(currChar);
        invalidate();
    }

}
