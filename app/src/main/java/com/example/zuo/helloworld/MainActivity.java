package com.example.zuo.helloworld;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zuo.sudoku_solver.R;

public class MainActivity extends ActionBarActivity {
    private EditText[][] boardInput;
    private Button solveButton;
    private Button cleanButton;
    private int color = Color.rgb(168,203,255);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardInput = new EditText[9][9];
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                boardInput[i][j] = (EditText) findViewById(R.id.editText1 + i * 9 + j);

            }
        }
        solveButton = (Button) findViewById(R.id.button);
        cleanButton = (Button) findViewById(R.id.button2);
        solveButton.setOnClickListener(new SovleOnClickListener());
        cleanButton.setOnClickListener(new CleanOnClickListener());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "版本:1.0\n作者:csu 安云", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public class SovleOnClickListener implements View.OnClickListener
    {

        public String[][] getAllInput()
        {
            String[][] board = new String[9][9];
            for (int i = 0; i < 9; i++)
            {
                for (int j = 0; j < 9; j++)
                {
                    board[i][j] = boardInput[i][j].getText().toString();
                    if(board[i][j].length() > 0 && board[i][j].charAt(0) <= '9' &&  board[i][j].charAt(0) > '0') boardInput[i][j].setBackgroundColor(color);
                }
            }
            return board;
        }
        public void outputAnswer(String [][] board)
        {
            for (int i = 0; i < 9; i++)
            {
                for (int j = 0; j < 9; j++)
                {
                    boardInput[i][j].setText(board[i][j]);
                }
            }
        }

        @Override
        public void onClick(View v) {
            String[][] board = getAllInput();
            if(SudokuSolver.solve(board))outputAnswer(board);
            else Toast.makeText(MainActivity.this,"该数独无解",Toast.LENGTH_SHORT).show();

        }
    }
    public class CleanOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            for (int i = 0; i < 9; i++)
            {
                for (int j = 0; j < 9; j++)
                {
                    boardInput[i][j].setText("");
                    boardInput[i][j].setBackgroundResource(R.drawable.selector);
                }
            }
        }
    }
}
