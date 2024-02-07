package com.vince.linedrawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class VulkanView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private Paint paint;

    public VulkanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        holder.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.BLUE); // Set line color to blue
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        drawAxis(canvas); // Draw X and Y axes
        drawDDALine(canvas, -18, 24, 30, -12); // Draw line using DDA Alg orithm
        holder.unlockCanvasAndPost(canvas);
    }

    private void drawAxis(Canvas canvas) {
        // Draw X axis
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, paint);
        // Draw Y axis
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), paint);
        // Label axes
        paint.setTextSize(30);
        paint.setColor(Color.RED);
        canvas.drawText("X", getWidth() - 20, getHeight() / 2 - 10, paint);
        canvas.drawText("Y", getWidth() / 2 + 10, 30, paint);
    }

    private void drawDDALine(Canvas canvas, int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int steps = Math.max(Math.abs(dx), Math.abs(dy));
        float xIncrement = (float) dx / steps;
        float yIncrement = (float) dy / steps;

        float x = x1;
        float y = y1;
        for (int i = 0; i <= steps; i++) {
            canvas.drawPoint(x + getWidth() / 2, getHeight() / 2 - y, paint); // Adjust for center of the canvas
            x += xIncrement;
            y += yIncrement;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}
}