package com.example.patnash; //change this to the name of your package

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;

public class MainActivity
        extends Activity {
    int H_S;
    int STEP_COUNT = 0;
    Integer[] arr;
    ArrayList<Integer> coords = new ArrayList();
    SharedPreferences highScore;
    Tile[] tiles;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.requestWindowFeature(1);
        this.getWindow().setFlags(1024, 1024);
        this.setContentView((View) new myView((Context) this));
    }

    public class Tile {
        protected int num;
        protected int x;
        protected int y;

        public Tile(int n, int n2) {
            this.x = n;
            this.y = n2;
        }

        public Rect getRect(int n) {
            return new Rect(this.x, this.y, n + this.x, n + this.y);
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public void moveBack(int n) {
            this.x -= n;
        }

        public void moveDown(int n) {
            this.y = n + this.y;
        }

        public void moveForw(int n) {
            this.x = n + this.x;
        }

        public void moveUp(int n) {
            this.y -= n;
        }
    }

    private class myView
            extends View {
        int bw;
        Display disp;
        int fx;
        Paint myPaint;
        ArrayList<Rect> rct;
        Rect tl;
        int x;
        int y;

        public myView(Context context) {
            super(context);
            disp = ((WindowManager) this.getContext().getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
            fx = this.x = 0;
            y = this.disp.getWidth() / 4;
            bw = this.disp.getWidth() / 4;
            rct = new ArrayList();
            MainActivity.this.highScore = getSharedPreferences("MyPref", 0);
            MainActivity.this.H_S = highScore.getInt("load", H_S);
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Random random = new Random();
            while (linkedHashSet.size() < 15) {
                linkedHashSet.add((Object) (1 + random.nextInt(15)));
            }
            arr = (Integer[]) linkedHashSet.toArray((Object[]) new Integer[linkedHashSet.size()]);
            myPaint = new Paint();
            tiles = new Tile[15];
            for (int i = 0; i < tiles.length; ++i) {
                tiles[i] = new Tile(x, y);
                coords.add(x);
                coords.add(y);
                this.x += 1 + bw;
                if (i != 3 && i != 7 && i != 11) continue;
                y += 1 + bw;
                x = fx;
            }
        }

        public String Win() {
            String string2 = "";
            for (int i = 0; i < 15; ++i) {
                string2 = string2 + Integer.toString((int) tiles[i].num);
            }
            return string2;
        }

        public boolean canMoveBack(Tile tile) {
            //20 + this.bw;
            Rect rect = new Rect(-20 + -bw, 0, -20 + -bw + bw, disp.getHeight());
            int n = -1 + (tile.getX() - bw);
            int n2 = tile.getY();
            Rect rect2 = new Rect(n, n2, n + bw, n2 + bw);
            for (int i = 0; i < 15; ++i) {
                if (!rect2.intersect(tiles[i].getRect(bw)) && !rect2.intersect(rect))
                    continue;
                return false;
            }
            return true;
        }

        public boolean canMoveDown(Tile tile) {
            int n = 20 + (disp.getWidth() / 2 + (1 + 3 * bw));
            Rect rect = new Rect(0, n, disp.getHeight(), n + 2 * bw);
            int n2 = tile.getX();
            int n3 = 1 + (tile.getY() + bw);
            Rect rect2 = new Rect(n2, n3, n2 + bw, n3 + bw);
            for (int i = 0; i < 15; ++i) {
                if (!rect2.intersect(tiles[i].getRect(bw)) && !rect2.intersect(rect))
                    continue;
                return false;
            }
            return true;
        }

        public boolean canMoveForw(Tile tile) {
            int n = 2 * bw + x;
            Rect rect = new Rect(n, 0, n + 2 * bw, disp.getHeight());
            int n2 = 1 + (tile.getX() + bw);
            int n3 = tile.getY();
            Rect rect2 = new Rect(n2, n3, n2 + bw, n3 + bw);
            for (int i = 0; i < 15; ++i) {
                if (!rect2.intersect(tiles[i].getRect(bw)) && !rect2.intersect(rect))
                    continue;
                return false;
            }
            return true;
        }

        public boolean canMoveUp(Tile tile) {
            int n = -20 + (disp.getWidth() / 4 - bw);
            Rect rect = new Rect(0, n, disp.getHeight(), n + bw);
            int n2 = tile.getX();
            int n3 = -1 + (tile.getY() - bw);
            Rect rect2 = new Rect(n2, n3, n2 + bw, n3 + bw);
            for (int i = 0; i < 15; ++i) {
                if (!rect2.intersect(tiles[i].getRect(bw)) && !rect2.intersect(rect))
                    continue;
                return false;
            }
            return true;
        }

        protected void onDraw(Canvas canvas) {
            int n = disp.getWidth() / 10;
            canvas.drawColor(Color.BLACK);
            myPaint.setColor(-1);
            myPaint.setTextSize((float) n);
            myPaint.setAntiAlias(true);
            for (int i = 0; i < 15; ++i) {
                int n2 = tiles[i].getX();
                int n3 = tiles[i].getY();
               tl = new Rect(n2, n3, n2 + bw, n3 + bw);
                canvas.drawRect(tl, myPaint);
                myPaint.setColor(Color.BLACK);
                canvas.drawText(Integer.toString((int)arr[i]), (float) (tl.centerX() - disp.getWidth() / 10 / 3), (float) (tl.centerY() + disp.getWidth() / 10 / 3), myPaint);
                myPaint.setTextSize(20.0f);
                MainActivity.this.tiles[i].num = arr[i];
                myPaint.setColor(-1);
                int n4 = this.disp.getWidth() / 15;
                this.myPaint.setTextSize((float) n4);
                canvas.drawText("Количество ходов: " + STEP_COUNT, 0.0f, (float) n4, myPaint);
                int n5 = disp.getWidth() / 10;
                myPaint.setTextSize((float) n5);
            }
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            motionEvent.getX();
            motionEvent.getY();
            switch (motionEvent.getAction()) {
                default: {
                    return true;
                }
                case 0:
            }
            int n = 0;
            do {
                if (n >= 15) {
                    invalidate();
                    return true;
                }
                if (tiles[n].getRect(bw).contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                    if (this.canMoveForw(tiles[n])) {
                        tiles[n].moveForw(1 + bw);
                        STEP_COUNT = 1 + STEP_COUNT;
                    } else if (canMoveBack(tiles[n])) {
                        tiles[n].moveBack(1 + bw);
                        STEP_COUNT = 1 + STEP_COUNT;
                    } else if (canMoveDown(tiles[n])) {
                        tiles[n].moveDown(1 + bw);
                        STEP_COUNT = 1 + STEP_COUNT;
                    } else if (canMoveUp(tiles[n])) {
                        tiles[n].moveUp(1 + bw);
                        STEP_COUNT = 1 + STEP_COUNT;
                    }
                }
                ++n;
            } while (true);
        }
    }

}

