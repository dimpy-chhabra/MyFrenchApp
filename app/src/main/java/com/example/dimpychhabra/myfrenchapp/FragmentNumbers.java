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

public class FragmentNumbers extends Fragment {
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

    public FragmentNumbers() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.words_list, container, false);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> wordArrayList = new ArrayList<>();
        wordArrayList.add(new Word(R.string.number_one, R.string.french_number_one,
                R.drawable.number_one, R.raw.numbers));
        wordArrayList.add(new Word(R.string.number_two, R.string.french_number_two,
                R.drawable.number_two, R.raw.numbers));
        wordArrayList.add(new Word(R.string.number_three, R.string.french_number_three,
                R.drawable.number_three, R.raw.numbers));
        wordArrayList.add(new Word(R.string.number_four, R.string.french_number_four,
                R.drawable.number_four, R.raw.numbers));
        wordArrayList.add(new Word(R.string.number_five, R.string.french_number_five,
                R.drawable.number_five, R.raw.numbers));
        wordArrayList.add(new Word(R.string.number_six, R.string.french_number_six,
                R.drawable.number_six, R.raw.numbers));
        wordArrayList.add(new Word(R.string.number_seven, R.string.french_number_seven,
                R.drawable.number_seven, R.raw.numbers));
        wordArrayList.add(new Word(R.string.number_eight, R.string.french_number_eight,
                R.drawable.number_eight, R.raw.numbers));
        wordArrayList.add(new Word(R.string.number_nine, R.string.french_number_nine,
                R.drawable.number_nine, R.raw.numbers));
        wordArrayList.add(new Word(R.string.number_ten, R.string.french_number_ten,
                R.drawable.number_ten, R.raw.numbers));

        WordAdapter wordAdapter = new WordAdapter(getActivity(), wordArrayList, R.color.category_numbers);
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
