
package aad.app.hello.camera;

import aad.app.hello.camera.R;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class AudioActivity extends Activity implements OnClickListener {

    private static final String TAG = AudioActivity.class.getSimpleName();

    private Button mIntentButton;
    private Button mPlayButton;
    private Button mStartButton;
    private Button mStopButton;
    private MediaRecorder mMediaRecorder;
    private static String mFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.setContentView(R.layout.activity_audio);

        mIntentButton = (Button) this.findViewById(R.id.recordIntent);
        mIntentButton.setOnClickListener(this);
        
        mPlayButton = (Button) this.findViewById(R.id.playAudioRecord);
        mPlayButton.setOnClickListener(this);

        mStartButton = (Button) this.findViewById(R.id.startAudioRecord);
        mStartButton.setOnClickListener(this);

        mStopButton = (Button) this.findViewById(R.id.stopAudioRecord);
        mStopButton.setOnClickListener(this);

        mMediaRecorder = new MediaRecorder();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            
            case R.id.recordIntent:
            	
            	String recordAction = "android.provider.MediaStore.RECORD_SOUND";
            	
            	final PackageManager packageManager = this.getPackageManager();
                Intent intent = new Intent(recordAction);
                List<ResolveInfo> list = packageManager.queryIntentActivities(intent, 0);
                
                if (list.size() > 0)
                	startActivityForResult(intent, 0);
                else
                	Toast.makeText(this, "No Recording Capability available", Toast.LENGTH_LONG).show();
                
                break;

            case R.id.playAudioRecord:
                playRecording();
                break;

            case R.id.startAudioRecord:
                startRecording();
                break;

            case R.id.stopAudioRecord:
                stopRecording();
                break;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        if (mMediaRecorder != null)
            mMediaRecorder.release();
    }

    private void playRecording() {

        File playFile = new File(mFileName);
        FileInputStream fis;
        if (playFile.exists()) {

            try {
                
                fis = new FileInputStream(playFile);
                
                MediaPlayer mp = new MediaPlayer();
                mp.setDataSource(fis.getFD());
                mp.prepare();
                mp.start();
                
                fis.close();

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
        else {
            Log.e(TAG, "playRecording() File does not exist: " + mFileName);
        }
    }

    private void startRecording() {

        mMediaRecorder.reset();

        String fileName = UUID.randomUUID().toString().substring(0, 6).concat(".3gp");
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName;

        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mMediaRecorder.setOutputFile(mFileName);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMediaRecorder.start();
    }

    private void stopRecording() {

        if (mMediaRecorder == null)
            return;

        mMediaRecorder.stop();
        mMediaRecorder = null;
    }

}
