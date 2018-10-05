package com.torgaigym.torgai.torgaigym.fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.torgaigym.torgai.torgaigym.R;
import com.torgaigym.torgai.torgaigym.classes.Music;
import com.torgaigym.torgai.torgaigym.dialogs.GymDialogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabataFragment extends Fragment implements View.OnClickListener {

    private TextView artistNameView, musicNameView, timerView, musicPositionView, tabataInfoView;
    private FloatingActionButton tabataTimerButton, chooseMusicButton, playButton, prevButton, nextButton, resetTimerButton;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer, startPlayer, stopPlayer;
    private Handler handler = new Handler();
    private Handler timerHandler = new Handler();
    private long startTime = 0;
    private boolean resetTimer = false;
    private int tT1 = 0, tT2 = 0, tRounds = 0, totalTimerSeconds = 0;
    private List<Music> _musics = new ArrayList<>();
    private int musicPosition = 0;
    private boolean isPause = false, isPlay = false;
    private Vibrator vibrator;
    private int _rounds = 1, _tT1Helper = 0;

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
        musicPositionView = view.findViewById(R.id.music_position);
        tabataInfoView = view.findViewById(R.id.tabata_info);
        playButton = view.findViewById(R.id.play_button);
        prevButton = view.findViewById(R.id.prev_button);
        nextButton = view.findViewById(R.id.next_button);
        chooseMusicButton = view.findViewById(R.id.choose_button);
        seekBar = view.findViewById(R.id.seek_bar);
        timerView = view.findViewById(R.id.timer);
        resetTimerButton = view.findViewById(R.id.reset_timer_button);
        tabataTimerButton = view.findViewById(R.id.tabata_timer_button);

        playButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        chooseMusicButton.setOnClickListener(this);
        resetTimerButton.setOnClickListener(this);
        tabataTimerButton.setOnClickListener(this);

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

        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        stopPlayer = MediaPlayer.create(getContext(), R.raw.tuturu);
        startPlayer = MediaPlayer.create(getContext(), R.raw.here_we_go_again);
        checkPermission();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_button:
                playMusic();
                break;
            case R.id.prev_button:
                onPrevButtonClicked();
                break;
            case R.id.next_button:
                onNextButtonClicked();
                break;
            case R.id.choose_button:
                startChoosing();
                break;
            case R.id.reset_timer_button:
                resetTimer();
                break;
            case R.id.tabata_timer_button:
                openTabataDialog();
                break;
        }
    }

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
                    String uriStr = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

                    if (!_musics.isEmpty()) {
                        for (Music music : _musics) {
                            if (artist.equals(music.getArtist()) && name.equals(music.getName())) {
                                Toast.makeText(getContext(), "Это музыка уже добавлена!", Toast.LENGTH_SHORT).show();
                                cursor.close();
                                return;
                            }
                        }
                    }

                    _musics.add(new Music(artist, name, uriStr));
                    updateMusicList();
                }
                cursor.close();
            }
        }
    }

    private void resetTimer() {
        _rounds = 1;
        _tT1Helper = 0;
        resetTimer = false;
        timerHandler.removeCallbacks(timerRunnable);
        timerView.setText("0:00");
        updateTabataInfo();
    }

    private void playMusic(String uri) {
        try {
            if (!resetTimer) {
                resetTimer = true;
                startTime = System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);
            }
            isPlay = true;
            isPause = false;
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
            playButton.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_media_pause));
            handler.postDelayed(runnable, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pauseMusic() {
        isPause = true;
        isPlay = false;
        playButton.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_media_play));
        mediaPlayer.pause();
        handler.removeCallbacks(runnable);
    }

    private void stopMusic() {
        if (mediaPlayer != null) {
            isPause = false;
            isPlay = false;
            playButton.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_media_play));
            handler.removeCallbacks(runnable);
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            seekBar.setProgress(0);
        }
    }

    private void updateMusicList() {
        artistNameView.setText(_musics.get(musicPosition).getArtist());
        musicNameView.setText(_musics.get(musicPosition).getName());
        musicPositionView.setText((musicPosition + 1) + " / " + _musics.size());
    }

    private void startNextMusic() {
        stopMusic();
        playMusic();
    }

    private void playMusic() {
        if (tT1 == 0 && tT2 == 0 && tRounds == 0) {
            Toast.makeText(getContext(), "Добавьте время Табата!", Toast.LENGTH_SHORT).show();
            openTabataDialog();
        } else {
            if (!_musics.isEmpty()) {
                if (!isPlay) {
                    playMusic(_musics.get(musicPosition).getUri());
                } else {
                    pauseMusic();
                }
            } else {
                Toast.makeText(getContext(), "Выберите музыку!", Toast.LENGTH_SHORT).show();
                startChoosing();
            }
        }
    }

    private void onPrevButtonClicked() {
        if (!_musics.isEmpty() && _musics.size() > 1) {
            if (musicPosition == 0) {
                musicPosition = _musics.size() - 1;
            } else {
                musicPosition--;
            }
            updateMusicList();
            startNextMusic();
        }
    }

    private void onNextButtonClicked() {
        if (!_musics.isEmpty() && _musics.size() > 1) {
            if (musicPosition == _musics.size() - 1) {
                musicPosition = 0;
            } else {
                musicPosition++;
            }
            updateMusicList();
            startNextMusic();
        }
    }

    private void openTabataDialog() {
        if (!resetTimer) {
            GymDialogs.tabataTimerExercise(getContext(), getString(R.string.nav_tabata), new GymDialogs.TimerInputListener() {
                @Override
                public void onClick(int t1, int t2, int rounds) {
                    tT1 = t1;
                    tT2 = t2;
                    tRounds = rounds;
                    totalTimerSeconds = (tT1 + tT2) * tRounds;
                    updateTabataInfo();
                }
            }).show();
        } else {
            Toast.makeText(getContext(), "Остановите время!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTabataInfo() {
        tabataInfoView.setText("Rounds: " + _rounds + " / " + tRounds + " rounds\n" + "Work: " + tT1 + " seconds\n" + "Rest: " + tT2 + " seconds");
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null) {
                if (!mediaPlayer.isPlaying() && !isPause) {
                    handler.removeCallbacks(this);
                    seekBar.setProgress(0);
                    playButton.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_media_play));
                    onNextButtonClicked();
                    return;
                }
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 100);
            }
        }
    };

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            if (seconds >= totalTimerSeconds) {
                int minutes = seconds / 60;
                seconds = seconds % 60;
                timerView.setText(String.format("%d:%02d", minutes, seconds));

                vibrator.vibrate(200);
                stopPlayer.start();
                timerHandler.removeCallbacks(this);
                _rounds = 1;
                _tT1Helper = 0;
                return;
            }

            if (seconds > 0) {
                if (((tT1 * _rounds) + _tT1Helper) % seconds == 0 && ((tT1 * _rounds) + _tT1Helper) / seconds == 1) {

                    float l = (float) 0.1;
                    float r = (float) 0.1;
                    mediaPlayer.setVolume(l, r);

                    vibrator.vibrate(200);
                    stopPlayer.start();
                } else if (((tT1 + tT2) * _rounds) % seconds == 0 && ((tT1 + tT2) * _rounds) / seconds == 1) {
                    _rounds++;
                    _tT1Helper += tT2;
                    updateTabataInfo();

                    float l = (float) 0.1;
                    float r = (float) 0.1;
                    mediaPlayer.setVolume(l, r);

                    tabataInfoView.setTypeface(tabataInfoView.getTypeface(), Typeface.BOLD);
                    tabataInfoView.setTextColor(getResources().getColor(R.color.colorAccent));

                    vibrator.vibrate(200);
                    startPlayer.start();
                }

                if (seconds % ((tT1 * _rounds) + _tT1Helper) == 1) {
                    float l = (float) 1.0;
                    float r = (float) 1.0;
                    mediaPlayer.setVolume(l, r);
                } else if (_rounds > 1 && seconds % ((tT1 + tT2) * (_rounds - 1)) == 1) {
                    float l = (float) 1.0;
                    float r = (float) 1.0;
                    mediaPlayer.setVolume(l, r);

                    tabataInfoView.setTypeface(tabataInfoView.getTypeface(), Typeface.NORMAL);
                    tabataInfoView.setTextColor(getResources().getColor(android.R.color.black));
                }
            }

            int minutes = seconds / 60;
            seconds = seconds % 60;
            timerView.setText(String.format("%d:%02d", minutes, seconds));
            timerHandler.postDelayed(this, 500);
        }
    };

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
