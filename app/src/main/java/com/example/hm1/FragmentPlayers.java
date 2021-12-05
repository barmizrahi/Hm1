package com.example.hm1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.hm1.callBacks.CallBack_list;
import com.example.hm1.dataBase.Score;

import java.util.ArrayList;

public class FragmentPlayers extends Fragment {
    private AppCompatActivity activity;
    private CallBack_list callBackList;
    private ArrayList<Score> scores;
    private LinearLayout[] listOfPlayers;
    private TextView[] list_players, list_scores;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setCallBackList(CallBack_list callBackList) {
        this.callBackList = callBackList;
    }

    public void setRecords(ArrayList<Score> scores) {
        this.scores = scores;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_of_players, container, false);
        findViews(view);
        initViews();
        updateListView();


        return view;
    }

    private void initViews() {
        for (int i = 0; i < 10; i++) {
            listOfPlayers[i].setOnClickListener(setOnMap(i));
        }
    }

    private View.OnClickListener setOnMap(int index) {
        return view -> {
            callBackList.func(index);
        };
    }


    private void updateListView() {

        int i = 0;
        for (Score score : this.scores) {
            list_players[i].setText(score.getPlayerName());
            list_scores[i].setText("  " + score.getScore());
            listOfPlayers[i].setVisibility(View.VISIBLE);
            i++;
        }
    }

    private void findViews(View view) {
        list_players = new TextView[]{
                view.findViewById(R.id.list_player_player0),
                view.findViewById(R.id.list_player_player1),
                view.findViewById(R.id.list_player_player2),
                view.findViewById(R.id.list_player_player3),
                view.findViewById(R.id.list_player_player4),
                view.findViewById(R.id.list_player_player5),
                view.findViewById(R.id.list_player_player6),
                view.findViewById(R.id.list_player_player7),
                view.findViewById(R.id.list_player_player8),
                view.findViewById(R.id.list_player_player9),
        };
        list_scores = new TextView[]{
                view.findViewById(R.id.list_score_score0),
                view.findViewById(R.id.list_score_score1),
                view.findViewById(R.id.list_score_score2),
                view.findViewById(R.id.list_score_score3),
                view.findViewById(R.id.list_score_score4),
                view.findViewById(R.id.list_score_score5),
                view.findViewById(R.id.list_score_score6),
                view.findViewById(R.id.list_score_score7),
                view.findViewById(R.id.list_score_score8),
                view.findViewById(R.id.list_score_score9),
        };
        listOfPlayers = new LinearLayout[]{
                view.findViewById(R.id.list_player_row0),
                view.findViewById(R.id.list_player_row1),
                view.findViewById(R.id.list_player_row2),
                view.findViewById(R.id.list_player_row3),
                view.findViewById(R.id.list_player_row4),
                view.findViewById(R.id.list_player_row5),
                view.findViewById(R.id.list_player_row6),
                view.findViewById(R.id.list_player_row7),
                view.findViewById(R.id.list_player_row8),
                view.findViewById(R.id.list_player_row9),
        };
    }

}
