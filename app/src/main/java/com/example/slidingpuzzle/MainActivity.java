package com.example.slidingpuzzle;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private static final int COLUMNS = 4;
    private static final int DIMENSIONS  = COLUMNS * COLUMNS;

    private static String[] tileList;

    private static GestureDetectGridView mGridView;

    private static int mColumnWidth, mColumnHeight;

    public static final String UP = "up";
    public static final String DOWN ="down";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        scramble();

        setDimensions();

    }
    private void setDimensions() {
        ViewTreeObserver vto = mGridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = mGridView.getMeasuredWidth();
                int displayHeight = mGridView.getMeasuredHeight();

                int statusbarHeight = getStatusBarHeight(getApplicationContext());
                int requiredHeight = displayHeight - statusbarHeight;

                mColumnWidth = displayWidth / COLUMNS;
                mColumnHeight = requiredHeight / COLUMNS;

                display(getApplicationContext());
            }
        });
    }

    private int getStatusBarHeight(Context context) {
        int result =0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private static void display(Context context) {
        ArrayList<Button> buttons = new ArrayList<>();
        Button button;

        for (int i=0; i < tileList.length; i++) {
            button = new Button(context);

            if (tileList[i].equals("0"))
                button.setBackgroundResource(R.drawable.img1);
            else if (tileList[i].equals("1"))
                button.setBackgroundResource(R.drawable.img2);
            else if (tileList[i].equals("2"))
                button.setBackgroundResource(R.drawable.img3);
            else if (tileList[i].equals("3"))
                button.setBackgroundResource(R.drawable.img4);
            else if (tileList[i].equals("4"))
                button.setBackgroundResource(R.drawable.img5);
            else if (tileList[i].equals("5"))
                button.setBackgroundResource(R.drawable.img6);
            else if (tileList[i].equals("6"))
                button.setBackgroundResource(R.drawable.img7);
            else if (tileList[i].equals("7"))
                button.setBackgroundResource(R.drawable.img8);
            else if (tileList[i].equals("8"))
                button.setBackgroundResource(R.drawable.img9);
            else if (tileList[i].equals("9"))
                button.setBackgroundResource(R.drawable.img10);
            else if (tileList[i].equals("10"))
                button.setBackgroundResource(R.drawable.img11);
            else if (tileList[i].equals("11"))
                button.setBackgroundResource(R.drawable.img12);
            else if (tileList[i].equals("12"))
                button.setBackgroundResource(R.drawable.img13);
            else if (tileList[i].equals("13"))
                button.setBackgroundResource(R.drawable.img14);
            else if (tileList[i].equals("14"))
                button.setBackgroundResource(R.drawable.img15);
            else if (tileList[i].equals("15"))
                button.setBackgroundResource(R.drawable.img16);

            buttons.add(button);
        }

        mGridView.setAdapter(new CustomAdapter(buttons, mColumnWidth, mColumnHeight));


    }
    private void scramble() {
        int index;
        String temp;
        Random random = new Random();

        for (int i = tileList.length -1; i > 0; i--){
            index = random.nextInt(i+1);
            temp = tileList[index];
            tileList[index] = tileList[i];
            tileList[i] = temp;
        }
    }

    private void init() {
        mGridView = (GestureDetectGridView) findViewById(R.id.grid);
        mGridView.setNumColumns(COLUMNS);

        tileList = new String[DIMENSIONS];
        for (int i=0; i < DIMENSIONS; i++){
            tileList[i] = String.valueOf(i);
        }
    }

    private static void swap( Context context, int position, int swap){
        String newPosition = tileList[position + swap];
        tileList[position + swap] = tileList[position];
        tileList[position] = newPosition;
        display(context);

        if (isSolved()) Toast.makeText(context, "YOU SOLVED THE PUZZLE!", Toast.LENGTH_LONG).show();
    }

    private static boolean isSolved(){
        boolean solved = false;

        for (int i = 0; i < tileList.length; i++) {
            if (tileList[i].equals(String.valueOf(i))){
                solved = true;
            } else {
                solved = false;
                break;
            }
        }
        return solved;
    }
    public static void moveTiles(Context context, String direction, int position){

        // Upper left corner tile
        if (position == 0){
            if (direction.equals(RIGHT)) swap(context, position, 1);
            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);
             else Toast.makeText(context, "Cannot Swipe That Direction", Toast.LENGTH_SHORT).show();

            //Upper Center Left Tile
        } else if (position > 0 && position < COLUMNS - 2) {
            if(direction.equals(LEFT)) swap(context, position, -1);
            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);
            else if (direction.equals(RIGHT)) swap(context, position, 1);
            else Toast.makeText(context, "Cannot Swipe That Direction", Toast.LENGTH_SHORT).show();

            //Upper Center Right Tile
        } else if (position > 1 && position < COLUMNS - 1) {
            if(direction.equals(LEFT)) swap(context, position, -1);
            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);
            else if (direction.equals(RIGHT)) swap(context, position, 1);
            else Toast.makeText(context, "Cannot Swipe That Direction", Toast.LENGTH_SHORT).show();

            //Upper right corner tile
        } else if (position == COLUMNS - 1) {
            if(direction.equals(LEFT)) swap(context, position, -1);
            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Cannot Swipe That Direction", Toast.LENGTH_SHORT).show();

            //Second row left tile
        } else if (position < COLUMNS - 1 && position < COLUMNS + 1) {
            if(direction.equals(UP)) swap(context, position, - COLUMNS);
            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);
            else if (direction.equals(RIGHT)) swap(context, position, 1);
            else Toast.makeText(context, "Cannot Swipe That Direction", Toast.LENGTH_SHORT).show();

            //second row left middle tile
        } else if (position > COLUMNS && position < COLUMNS + 2) {
            if(direction.equals(LEFT)) swap(context, position, -1);
            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);
            else if (direction.equals(RIGHT)) swap(context, position, 1);
            else if (direction.equals(UP)) swap(context, position, - COLUMNS);
            else Toast.makeText(context, "Cannot Swipe That Direction", Toast.LENGTH_SHORT).show();

            //second row right middle tile
        } else if (position > COLUMNS + 1 && position < COLUMNS + 3) {
            if(direction.equals(LEFT)) swap(context, position, -1);
            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);
            else if (direction.equals(RIGHT)) swap(context, position, 1);
            else if (direction.equals(UP)) swap(context, position, -COLUMNS);
            else Toast.makeText(context, "Cannot Swipe That Direction", Toast.LENGTH_SHORT).show();

            //second row right tile
        } else if (position > COLUMNS + 2 && position < COLUMNS + 4) {
            if(direction.equals(LEFT)) swap(context, position, -1);
            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);
            else if (direction.equals(UP)) swap(context, position, -COLUMNS);
            else Toast.makeText(context, "Cannot Swipe That Direction", Toast.LENGTH_SHORT).show();

            //third row left tile
        } else if (position > COLUMNS + 3 && position < COLUMNS + 5) {
            if(direction.equals(UP)) swap(context, position, -COLUMNS);
            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);
            else if (direction.equals(RIGHT)) swap(context, position, 1);
            else Toast.makeText(context, "Cannot Swipe That Direction", Toast.LENGTH_SHORT).show();

            //third row left middle tile
        } else if (position > COLUMNS + 4 && position < COLUMNS + 6) {
            if(direction.equals(LEFT)) swap(context, position, -1);
            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);
            else if (direction.equals(RIGHT)) swap(context, position, 1);
            else if (direction.equals(UP)) swap(context, position, -COLUMNS);
            else Toast.makeText(context, "Cannot Swipe That Direction", Toast.LENGTH_SHORT).show();

            //third row right middle tile
        } else if (position > COLUMNS + 5 && position < COLUMNS + 7) {
            if(direction.equals(LEFT)) swap(context, position, -1);
            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);
            else if (direction.equals(RIGHT)) swap(context, position, 1);
            else if (direction.equals(UP)) swap(context, position, -COLUMNS);
            else Toast.makeText(context, "Cannot Swipe That Direction", Toast.LENGTH_SHORT).show();

            //third row right tile
        } else if (position > COLUMNS + 6 && position < DIMENSIONS - COLUMNS ) {
            if(direction.equals(LEFT)) swap(context, position, -1);
            else if (direction.equals(DOWN)) swap(context, position, COLUMNS);
            else if (direction.equals(UP)) swap(context, position, - COLUMNS);
            else Toast.makeText(context, "Cannot Swipe That Direction", Toast.LENGTH_SHORT).show();

            //last row left tile
        } else if (position == DIMENSIONS- COLUMNS) {
            if(direction.equals(UP)) swap(context, position, -COLUMNS);
            else if (direction.equals(RIGHT)) swap(context, position, 1);
            else Toast.makeText(context, "Cannot Swipe That Direction", Toast.LENGTH_SHORT).show();

            //last row left middle tile
        } else if (position > DIMENSIONS - COLUMNS && position < DIMENSIONS - 2) {
            if(direction.equals(LEFT)) swap(context, position, -1);
            else if (direction.equals(UP)) swap(context, position, -COLUMNS);
            else if (direction.equals(RIGHT)) swap(context, position, 1);
            else Toast.makeText(context, "Cannot Swipe That Direction", Toast.LENGTH_SHORT).show();

            //last row right middle tile
        } else if (position > DIMENSIONS - 3 && position < DIMENSIONS -1) {
            if(direction.equals(LEFT)) swap(context, position, -1);
            else if (direction.equals(UP)) swap(context, position, -COLUMNS);
            else if (direction.equals(RIGHT)) swap(context, position, 1);
            else Toast.makeText(context, "Cannot Swipe That Direction", Toast.LENGTH_SHORT).show();

            //last row right tile
        } else if (position > DIMENSIONS - 2 && position < DIMENSIONS) {
            if (direction.equals(UP)) swap(context, position, -COLUMNS);
            else if (direction.equals(LEFT)) swap(context, position, -1);
            else Toast.makeText(context, "Cannot Swipe That Direction", Toast.LENGTH_SHORT).show();
        }
    }
}
