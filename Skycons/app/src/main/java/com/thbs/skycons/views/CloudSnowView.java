package com.thbs.skycons.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by administrator on 09/09/14.
 */
public class CloudSnowView extends View {

    private static Paint paint, paint1;

    PathPoints[] pathPoints11, pathPoints12, pathPoints21, pathPoints22,
                 pointsCircle11, pointsCircle12, pointsCircle21, pointsCircle22;

    private int screenW, screenH;
    private float X, Y;

    private Path cloudPath, path11, path12, path13,
                 path21, path22, path23, //visible drawn paths

                 cubicPath11, cubicPath12,
                 cubicPath21, cubicPath22, //Invisible paths for drop movement

                 pathCircle1, pathCircle2; //Invisible paths for rotate operation

    int m=0, n=0, x1=0, y1=0, x2=0, y2=0;

    boolean drop11 = true, drop12 = false, drop21 = false, drop22 = false,
            pointsStored = false;

    private double count;

    public CloudSnowView(Context context) {
        super(context);
        init();
    }

    public CloudSnowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CloudSnowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

        count = 0;

        paint = new Paint();
        paint1 = new Paint();

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setShadowLayer(0, 0, 0, Color.BLACK);

        paint1.setColor(Color.BLACK);
        paint1.setStrokeWidth(7);
        paint1.setAntiAlias(true);
        paint1.setStrokeCap(Paint.Cap.ROUND);
        paint1.setStyle(Paint.Style.STROKE);

        cloudPath = new Path();
        pathCircle1 = new Path();
    }

    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenW = w;
        screenH = h;
        X = screenW/2;
        Y = (screenH/2);

        cloudPath.moveTo(X, Y);

    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        cloudPath = new Path();
        count = count+0.5;

        int retval = Double.compare(count, 360.00);

        if(retval > 0) {

        }
        else if(retval < 0) {

        }
        else {
            count = 0;
        }

        float X1 = (float)(90 * Math.cos(Math.toRadians(0+(0.222*count))) + X);
        float Y1 = ((float)(50 * Math.sin(Math.toRadians(0+(0.222*count))) + Y));
        float P1X = (float)(90 * Math.cos(Math.toRadians(80+(0.111*count))) + X);
        float P1Y = ((float)(50 * Math.sin(Math.toRadians(80+(0.111*count))) + Y));
        float P2X = (float)(90 * Math.cos(Math.toRadians(120+(0.222*count))) + X);
        float P2Y = ((float)((50+(0.111*count)) * Math.sin(Math.toRadians(120+(0.222*count))) + Y));
        float P3X = (float)(90 * Math.cos(Math.toRadians(200+(0.222*count))) + X);
        float P3Y = ((float)(90 * Math.sin(Math.toRadians(200+(0.222*count))) + Y));
        float P4X =(float)(90 * Math.cos(Math.toRadians(280+(0.222*count))) + X);
        float P4Y = ((float)(90 * Math.sin(Math.toRadians(280+(0.222*count))) + Y));

        cloudPath.moveTo(X1,Y1);
        PointF P1c1 = calculateTriangle(X1, Y1, P1X, P1Y, true, count);
        PointF P1c2 = calculateTriangle(X1, Y1, P1X, P1Y, false, count);
        PointF P2c1 = calculateTriangle(P1X, P1Y, P2X, P2Y, true, count);
        PointF P2c2 = calculateTriangle(P1X, P1Y, P2X, P2Y, false, count);
        PointF P3c1 = calculateTriangle(P2X, P2Y, P3X, P3Y, true, count);
        PointF P3c2 = calculateTriangle(P2X, P2Y, P3X, P3Y, false, count);
        PointF P4c1 = calculateTriangle(P3X, P3Y, P4X, P4Y, true, count);
        PointF P4c2 = calculateTriangle(P3X, P3Y, P4X, P4Y, false, count);
        PointF P5c1 = calculateTriangle(P4X, P4Y, X1, Y1, true, count);
        PointF P5c2 = calculateTriangle(P4X, P4Y, X1,Y1, false, count);

        cloudPath.moveTo(X1,Y1);
        cloudPath.cubicTo(P1c1.x,P1c1.y,P1c2.x,P1c2.y,P1X,P1Y);
        cloudPath.cubicTo(P2c1.x,P2c1.y,P2c2.x,P2c2.y,P2X,P2Y);
        cloudPath.cubicTo(P3c1.x,P3c1.y,P3c2.x,P3c2.y,P3X,P3Y);
        cloudPath.cubicTo(P4c1.x,P4c1.y,P4c2.x,P4c2.y,P4X,P4Y);
        cloudPath.cubicTo(P5c1.x,P5c1.y,P5c2.x,P5c2.y,X1,Y1);

        if(x1==0) {
            x1 = (int) P1c2.x + 10;
        }
        if(y1==0) {
            float value = (int) P1c2.y-((P1c1.y+P1Y)/2);
            y1 = (int) (P1c2.y-value/2);
        }

        if(x2==0) {
            x2 = (int) P2c2.x + 10;
        }
        if(y2==0) {
            float value = (int) P2c2.y-((P2c1.y+P2Y)/2);
            y2 = (int) (P2c2.y-value/2);
        }

        if(!pointsStored) {
            cubicPath11 = new Path();
            cubicPath11.moveTo(x1, y1);
            cubicPath11.cubicTo(x1-10, y1+30, x1-20, y1+60, x1-30, y1+90);
            pathPoints11 = getPoints(cubicPath11);


            cubicPath12 = new Path();
            int x = x1-5;
            cubicPath12.moveTo(x, y1);
            cubicPath12.cubicTo(x+10, y1+30, x+15, y1+60, x-5, y1+90);
            pathPoints12 = getPoints(cubicPath12);

            cubicPath21 = new Path();
            cubicPath21.moveTo(x2, y2);
            cubicPath21.cubicTo(x2+10, y2+30, x2+20, y2+60, x2+30, y2+90);
            pathPoints21 = getPoints(cubicPath21);

            cubicPath22 = new Path();
            int xx= x2+5;
            cubicPath22.moveTo(xx, y2);
            cubicPath22.cubicTo(xx-10, y2+30, xx-15, y2+60, xx+5, y2+90);
            pathPoints22 = getPoints(cubicPath22);

            pointsStored = true;
        }


        if(drop11) {

            pathCircle1 = new Path();
            pathCircle1.addCircle(pathPoints11[m].getX(), pathPoints11[m].getY(),
                    13, Path.Direction.CW);
            pointsCircle11 = getPoints(pathCircle1);

            //1st drop
            path11 = new Path();
            path12 = new Path();
            path13 = new Path();

            int a = (25+m/5) >= 100 ? 25+m/5 - 100 : 25+m/5;
            int b = (8+m/5) >= 100 ? 8+m/5 - 100 : 8+m/5;
            int c = (40+m/5) >= 100 ? 40+m/5 - 100 : 40+m/5;

            path11.moveTo(pointsCircle11[a].getX(), pointsCircle11[a].getY());
            path12.moveTo(pointsCircle11[b].getX(), pointsCircle11[b].getY());
            path13.moveTo(pointsCircle11[c].getX(), pointsCircle11[c].getY());

            a = (75+m/5) >= 100 ? 75+m/5 - 100 : 75+m/5;
            b = (59+m/5) >= 100 ? 59+m/5 - 100 : 59+m/5;
            c = (90+m/5) >= 100 ? 90+m/5 - 100 : 90+m/5;

            path11.lineTo(pointsCircle11[a].getX(), (pointsCircle11[a].getY()));
            path12.lineTo(pointsCircle11[b].getX(), (pointsCircle11[b].getY()));
            path13.lineTo(pointsCircle11[c].getX(), (pointsCircle11[c].getY()));

            canvas.drawPath(path11, paint1);
            canvas.drawPath(path12, paint1);
            canvas.drawPath(path13, paint1);

            //fill cloud with white color
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(cloudPath, paint);

            //draw stroke with back color
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(cloudPath, paint);

            m = m+1;

            if(m > 75) {

                pathCircle2 = new Path();
                pathCircle2.addCircle(pathPoints12[n].getX(), pathPoints12[n].getY(),
                        13, Path.Direction.CW);
                pointsCircle12 = getPoints(pathCircle2);

                //2nd drop
                path21 = new Path();
                path22 = new Path();
                path23 = new Path();

                a = (25+n/5) >= 100 ? 25+n/5 - 100 : 25+n/5;
                b = (8+n/5) >= 100 ? 8+n/5 - 100 : 8+n/5;
                c = (40+n/5) >= 100 ? 40+n/5 - 100 : 40+n/5;

                path21.moveTo(pointsCircle12[a].getX(), pointsCircle12[a].getY());
                path22.moveTo(pointsCircle12[b].getX(), pointsCircle12[b].getY());
                path23.moveTo(pointsCircle12[c].getX(), pointsCircle12[c].getY());

                a = (75+n/5) >= 100 ? 75+n/5 - 100 : 75+n/5;
                b = (59+n/5) >= 100 ? 59+n/5 - 100 : 59+n/5;
                c = (90+n/5) >= 100 ? 90+n/5 - 100 : 90+n/5;

                path21.lineTo(pointsCircle12[a].getX(), (pointsCircle12[a].getY()));
                path22.lineTo(pointsCircle12[b].getX(), (pointsCircle12[b].getY()));
                path23.lineTo(pointsCircle12[c].getX(), (pointsCircle12[c].getY()));

                canvas.drawPath(path21, paint1);
                canvas.drawPath(path22, paint1);
                canvas.drawPath(path23, paint1);

                //fill cloud with white color
                paint.setColor(Color.WHITE);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawPath(cloudPath, paint);


                //draw stroke with back color
                paint.setColor(Color.BLACK);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawPath(cloudPath, paint);

                n = n+1;

            }

            if(m==100) {
                m=0;
                path11.reset();
                path11.moveTo(0, 0);
                path12.reset();
                path12.moveTo(0, 0);
                path13.reset();
                path13.moveTo(0, 0);

                canvas.clipPath(path11);
                canvas.clipPath(path12);
                canvas.clipPath(path13);

                x1=0;
                y1=0;

                x2=0;
                y2=0;

                drop12 = true;
                drop11 = false;

            }

        }

        if(drop12) {

            pathCircle2 = new Path();
            pathCircle2.addCircle(pathPoints12[n].getX(), pathPoints12[n].getY(),
                    13, Path.Direction.CW);
            pointsCircle12 = getPoints(pathCircle2);

            //2nd drop
            path21 = new Path();
            path22 = new Path();
            path23 = new Path();

            int a = (25+n/5) >= 100 ? 25+n/5 - 100 : 25+n/5;
            int b = (8+n/5) >= 100 ? 8+n/5 - 100 : 8+n/5;
            int c = (40+n/5) >= 100 ? 40+n/5 - 100 : 40+n/5;

            path21.moveTo(pointsCircle12[a].getX(), pointsCircle12[a].getY());
            path22.moveTo(pointsCircle12[b].getX(), pointsCircle12[b].getY());
            path23.moveTo(pointsCircle12[c].getX(), pointsCircle12[c].getY());

            a = (75+n/5) >= 100 ? 75+n/5 - 100 : 75+n/5;
            b = (59+n/5) >= 100 ? 59+n/5 - 100 : 59+n/5;
            c = (90+n/5) >= 100 ? 90+n/5 - 100 : 90+n/5;

            path21.lineTo(pointsCircle12[a].getX(), (pointsCircle12[a].getY()));
            path22.lineTo(pointsCircle12[b].getX(), (pointsCircle12[b].getY()));
            path23.lineTo(pointsCircle12[c].getX(), (pointsCircle12[c].getY()));

            canvas.drawPath(path21, paint1);
            canvas.drawPath(path22, paint1);
            canvas.drawPath(path23, paint1);

            //fill cloud with white color
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(cloudPath, paint);


            //draw stroke with back color
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(cloudPath, paint);

            n = n+1;

            if(n==100) {
                m=0;
                n=0;
                path21.reset();
                path21.moveTo(0, 0);
                path22.reset();
                path22.moveTo(0, 0);
                path23.reset();
                path23.moveTo(0, 0);

                canvas.clipPath(path21);
                canvas.clipPath(path22);
                canvas.clipPath(path23);

                x1=0;
                y1=0;

                drop21 = true;
                drop11 = false;
                drop12 = false;

            }

        }

        if(drop21) {

            pathCircle1 = new Path();
            pathCircle1.addCircle(pathPoints21[m].getX(), pathPoints21[m].getY(),
                    13, Path.Direction.CW);
            pointsCircle21 = getPoints(pathCircle1);

            //1st drop
            path11 = new Path();
            path12 = new Path();
            path13 = new Path();

            int a = (25+m/5) >= 100 ? 25+m/5 - 100 : 25+m/5;
            int b = (8+m/5) >= 100 ? 8+m/5 - 100 : 8+m/5;
            int c = (40+m/5) >= 100 ? 40+m/5 - 100 : 40+m/5;

            path11.moveTo(pointsCircle21[a].getX(), pointsCircle21[a].getY());
            path12.moveTo(pointsCircle21[b].getX(), pointsCircle21[b].getY());
            path13.moveTo(pointsCircle21[c].getX(), pointsCircle21[c].getY());

            a = (75+m/5) >= 100 ? 75+m/5 - 100 : 75+m/5;
            b = (59+m/5) >= 100 ? 59+m/5 - 100 : 59+m/5;
            c = (90+m/5) >= 100 ? 90+m/5 - 100 : 90+m/5;

            path11.lineTo(pointsCircle21[a].getX(), (pointsCircle21[a].getY()));
            path12.lineTo(pointsCircle21[b].getX(), (pointsCircle21[b].getY()));
            path13.lineTo(pointsCircle21[c].getX(), (pointsCircle21[c].getY()));

            canvas.drawPath(path11, paint1);
            canvas.drawPath(path12, paint1);
            canvas.drawPath(path13, paint1);

            //fill cloud with white color
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(cloudPath, paint);


            //draw stroke with back color
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(cloudPath, paint);

            m = m+1;

            if(m > 75) {

                pathCircle2 = new Path();
                pathCircle2.addCircle(pathPoints22[n].getX(), pathPoints22[n].getY(),
                        13, Path.Direction.CW);
                pointsCircle22 = getPoints(pathCircle2);

                //2nd drop
                path21 = new Path();
                path22 = new Path();
                path23 = new Path();

                a = (25+n/5) >= 100 ? 25+n/5 - 100 : 25+n/5;
                b = (8+n/5) >= 100 ? 8+n/5 - 100 : 8+n/5;
                c = (40+n/5) >= 100 ? 40+n/5 - 100 : 40+n/5;

                path21.moveTo(pointsCircle22[a].getX(), pointsCircle22[a].getY());
                path22.moveTo(pointsCircle22[b].getX(), pointsCircle22[b].getY());
                path23.moveTo(pointsCircle22[c].getX(), pointsCircle22[c].getY());

                a = (75+n/5) >= 100 ? 75+n/5 - 100 : 75+n/5;
                b = (59+n/5) >= 100 ? 59+n/5 - 100 : 59+n/5;
                c = (90+n/5) >= 100 ? 90+n/5 - 100 : 90+n/5;

                path21.lineTo(pointsCircle22[a].getX(), (pointsCircle22[a].getY()));
                path22.lineTo(pointsCircle22[b].getX(), (pointsCircle22[b].getY()));
                path23.lineTo(pointsCircle22[c].getX(), (pointsCircle22[c].getY()));

                canvas.drawPath(path21, paint1);
                canvas.drawPath(path22, paint1);
                canvas.drawPath(path23, paint1);

                //fill cloud with white color
                paint.setColor(Color.WHITE);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawPath(cloudPath, paint);


                //draw stroke with back color
                paint.setColor(Color.BLACK);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawPath(cloudPath, paint);

                n = n+1;

            }

            if(m==100) {
                m=0;
                path11.reset();
                path11.moveTo(0, 0);
                path12.reset();
                path12.moveTo(0, 0);
                path13.reset();
                path13.moveTo(0, 0);

                canvas.clipPath(path11);
                canvas.clipPath(path12);
                canvas.clipPath(path13);

                x1=0;
                y1=0;

                drop22 = true;
                drop21 = false;
            }

        }

        if(drop22) {

            pathCircle2 = new Path();
            pathCircle2.addCircle(pathPoints22[n].getX(), pathPoints22[n].getY(),
                    13, Path.Direction.CW);
            pointsCircle22 = getPoints(pathCircle2);

            //2nd drop
            path21 = new Path();
            path22 = new Path();
            path23 = new Path();

            int a = (25+n/5) >= 100 ? 25+n/5 - 100 : 25+n/5;
            int b = (8+n/5) >= 100 ? 8+n/5 - 100 : 8+n/5;
            int c = (40+n/5) >= 100 ? 40+n/5 - 100 : 40+n/5;

            path21.moveTo(pointsCircle22[a].getX(), pointsCircle22[a].getY());
            path22.moveTo(pointsCircle22[b].getX(), pointsCircle22[b].getY());
            path23.moveTo(pointsCircle22[c].getX(), pointsCircle22[c].getY());

            a = (75+n/5) >= 100 ? 75+n/5 - 100 : 75+n/5;
            b = (59+n/5) >= 100 ? 59+n/5 - 100 : 59+n/5;
            c = (90+n/5) >= 100 ? 90+n/5 - 100 : 90+n/5;

            path21.lineTo(pointsCircle22[a].getX(), (pointsCircle22[a].getY()));
            path22.lineTo(pointsCircle22[b].getX(), (pointsCircle22[b].getY()));
            path23.lineTo(pointsCircle22[c].getX(), (pointsCircle22[c].getY()));

            canvas.drawPath(path21, paint1);
            canvas.drawPath(path22, paint1);
            canvas.drawPath(path23, paint1);

            //fill cloud with white color
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(cloudPath, paint);


            //draw stroke with back color
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(cloudPath, paint);

            n = n+1;

            if(n==100) {
                m=0;
                n=0;
                path21.reset();
                path21.moveTo(0, 0);
                path22.reset();
                path22.moveTo(0, 0);
                path23.reset();
                path23.moveTo(0, 0);

                canvas.clipPath(path21);
                canvas.clipPath(path22);
                canvas.clipPath(path23);

                x1=0;
                y1=0;

                drop11 = true;
                drop12 = false;
                drop21 = false;
                drop22 = false;

            }

        }

        invalidate();

    }

    private PointF calculateTriangle(float x1, float y1, float x2, float y2,
                                     boolean left, double count) {

        PointF result = new PointF(0,0);
        float dy = y2 - y1;
        float dx = x2 - x1;
        float dangle = (float) ((Math.atan2(dy, dx) - Math.PI /2f));
        float sideDist = (float)0.5 * (float) Math.sqrt(dx * dx + dy * dy); //square
        if (left){
            result.x = (int) (Math.cos(dangle) * sideDist + x1);
            result.y = (int) (Math.sin(dangle) * sideDist + y1);
        }else{
            result.x = (int) (Math.cos(dangle) * sideDist + x2);
            result.y = (int) (Math.sin(dangle) * sideDist + y2);
        }
        return result;
    }

    private PathPoints[] getPoints(Path path) {
        PathPoints[] pointArray = new PathPoints[100];
        PathMeasure pm = new PathMeasure(path, false);
        float length = pm.getLength();
        float distance = 0f;
        float speed = length / 100;
        int counter = 0;
        float[] aCoordinates = new float[2];

        while ((distance < length) && (counter < 100)) {
            pm.getPosTan(distance, aCoordinates, null);
            pointArray[counter] = new PathPoints(aCoordinates[0], aCoordinates[1]);
            counter++;
            distance = distance + speed;
        }

        return pointArray;
    }

    class PathPoints {

        float x, y;

        public PathPoints(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

    }

}


