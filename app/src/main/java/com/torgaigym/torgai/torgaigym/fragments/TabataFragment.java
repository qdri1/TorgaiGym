package com.torgaigym.torgai.torgaigym.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.torgaigym.torgai.torgaigym.R;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabataFragment extends Fragment {

    private TextView artistNameView, musicNameView;
    private Button playButton, chooseMusicButton;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private String uriStr;
    private Handler handler = new Handler();

    public static TabataFragment newInstance() {

        Bundle args = new Bundle();

        TabataFragment fragment = new TabataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tabata, container, false);
        initView(v);
        return v;
    }

    private void initView(View view) {
        artistNameView = view.findViewById(R.id.artist_name);
        musicNameView = view.findViewById(R.id.music_name);
        playButton = view.findViewById(R.id.play_button);
        chooseMusicButton = view.findViewById(R.id.choose_button);
        seekBar = view.findViewById(R.id.seek_bar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seekProgress;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacks(runnable);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekProgress);
                handler.postDelayed(runnable, 100);
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uriStr != null) {
                    if (playButton.getText().toString().equalsIgnoreCase("Play")) {
                        playMusic(uriStr);
                    } else {
                        pauseMusic();
                    }
                    handler.postDelayed(runnable, 100);
                }
            }
        });

        chooseMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChoosing();
            }
        });

        checkPermission();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null) {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                if (mediaPlayer.getCurrentPosition() >= mediaPlayer.getDuration()) {
                    handler.removeCallbacks(this);
                    seekBar.setProgress(0);
                    playButton.setText("Play");
                } else {
                    handler.postDelayed(this, 100);
                }
            }
        }
    };

    private void startChoosing() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 123);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 123) {
            Uri uri = data.getData();
            String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
            Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    artistNameView.setText(artist);
                    musicNameView.setText(name);
                    uriStr = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    stopMusic();
                }
                cursor.close();
            }
        }
    }

    private void playMusic(String uri) {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.start();
            } else {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(uri);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                        seekBar.setProgress(0);
                        seekBar.setMax(mp.getDuration());
                    }
                });
            }
            playButton.setText("Pause");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pauseMusic() {
        playButton.setText("Play");
        mediaPlayer.pause();
    }

    private void stopMusic() {
        if (mediaPlayer != null) {
            playButton.setText("Play");
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            seekBar.setProgress(0);
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 123:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startChoosing();
                } else {
                    Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    checkPermission();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
