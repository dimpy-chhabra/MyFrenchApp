package com.example.dimpychhabra.myfrenchapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FragmentColors extends Fragment {
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
            } else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    public FragmentColors() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.words_list, container, false);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> wordArrayList = new ArrayList<>();
        wordArrayList.add(new Word(R.string.color_red, R.string.french_color_red,
                R.drawable.color_red, R.raw.colors));
        wordArrayList.add(new Word(R.string.color_orange, R.string.french_color_orange,
                R.drawable.color_orange, R.raw.colors));
        wordArrayList.add(new Word(R.string.color_yellow, R.string.french_color_yellow,
                R.drawable.color_yellow, R.raw.colors));
        wordArrayList.add(new Word(R.string.color_green, R.string.french_color_green,
                R.drawable.color_green, R.raw.colors));
        wordArrayList.add(new Word(R.string.color_blue, R.string.french_color_blue,
                R.drawable.color_blue, R.raw.colors));
        wordArrayList.add(new Word(R.string.color_purple, R.string.french_color_purple,
                R.drawable.color_purple, R.raw.colors));
        wordArrayList.add(new Word(R.string.color_white, R.string.french_color_white,
                R.drawable.color_white, R.raw.colors));
        wordArrayList.add(new Word(R.string.color_brown, R.string.french_color_brown,
                R.drawable.color_brown, R.raw.colors));
        wordArrayList.add(new Word(R.string.color_black, R.string.french_color_black,
                R.drawable.color_black, R.raw.colors));
        wordArrayList.add(new Word(R.string.color_pink, R.string.french_color_pink,
                R.drawable.color_pink, R.raw.colors));
        wordArrayList.add(new Word(R.string.color_gold, R.string.french_color_gold,
                R.drawable.color_gold, R.raw.colors));
        wordArrayList.add(new Word(R.string.color_silver, R.string.french_color_silver,
                R.drawable.color_silver, R.raw.colors));
        wordArrayList.add(new Word(R.string.color_gray, R.string.french_color_gray,
                R.drawable.color_gray, R.raw.colors));


        WordAdapter wordAdapter = new WordAdapter(getActivity(), wordArrayList, R.color.category_phrases);
        ListView listView = (ListView)rootView.findViewById(R.id.list);

        //word adapter takes each word and gives it a list item view.
        //it returns the list items which will now be concated and presented
        //in the list view.

        listView.setAdapter(wordAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                Word word = wordArrayList.get(position);
                int result = audioManager.requestAudioFocus(onAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId());
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(completionListener);

            }
        });


        return rootView;
    }
    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
}
