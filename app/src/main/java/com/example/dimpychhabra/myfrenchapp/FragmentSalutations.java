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

public class FragmentSalutations extends Fragment {
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

    public FragmentSalutations() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.words_list, container, false);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> wordArrayList = new ArrayList<>();
        wordArrayList.add(new Word(R.string.salutation_one, R.string.french_salutation_one,
                R.raw.bonjour));
        wordArrayList.add(new Word(R.string.salutation_two, R.string.french_salutation_two,
                R.raw.bonsoir ));
        wordArrayList.add(new Word(R.string.salutation_three, R.string.french_salutation_three,
                R.raw.bonnenuit ));
        wordArrayList.add(new Word(R.string.salutation_four, R.string.french_salutation_four,
                R.raw.salut ));
        wordArrayList.add(new Word(R.string.salutation_five, R.string.french_salutation_five,
                R.raw.aurevoir ));
        wordArrayList.add(new Word(R.string.salutation_six, R.string.french_salutation_six,
                R.raw.silvousplait ));
        wordArrayList.add(new Word(R.string.salutation_seven, R.string.french_salutation_seven,
                R.raw.mercibcp ));
        wordArrayList.add(new Word(R.string.salutation_eight, R.string.french_salutation_eight,
                R.raw.jevousenprie ));
        wordArrayList.add(new Word(R.string.salutation_nine, R.string.french_salutation_nine,
                R.raw.atoutalheure ));
        wordArrayList.add(new Word(R.string.salutation_ten, R.string.french_salutation_ten,
                R.raw.aplustard ));
        wordArrayList.add(new Word(R.string.salutation_eleven, R.string.french_salutation_eleven,
                R.raw.jesuisdesole ));


        WordAdapter wordAdapter = new WordAdapter(getActivity(), wordArrayList, R.color.category_family);
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
