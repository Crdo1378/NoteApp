package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteapp.Alarm.AlarmReceiver;
import com.example.noteapp.Database.NoteDB;
import com.example.noteapp.Object.NoteOO;
import com.example.noteapp.databinding.ActivityDetailBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    private boolean check_new = false;
    private boolean change_color = false;
    private ActivityDetailBinding binding;
    private int listLast = -1;
    private int colorBack;
    private int colorHint;
    private int colorText;
    private ArrayList<NoteOO> noteList;
    private NoteDB noteDB;
    private Date date = new Date();
    private NoteOO note;
    private ArrayList<String> label;
    private String labelSelect;
    //private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    private ArrayAdapter arrayAdapter;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        label = new ArrayList<>();

        //receive from Main
        note = (NoteOO) getIntent().getSerializableExtra("noteAtTime");
        check_new = getIntent().getExtras().getBoolean("checkNew");
        listLast = getIntent().getExtras().getInt("listSize");
        label = getIntent().getExtras().getStringArrayList("listLabel");

        if (check_new) {//Nếu là tạo mới
            binding.tvCreatedDetail.append(" " + simpleDateFormat.format(date));
        } else {
            binding.txNote.setText(note.getContentNote());
            binding.txTitle.setText(note.getTitleNote());
            binding.tvTagDetail.setText(note.getTagNote());
            binding.tvCreatedDetail.append(" " + note.getTimeNote());
            binding.tvLastDetail.append(" " + note.getTimeLastChange());
            binding.layoutDetail.setBackgroundResource(note.getColorBack());
            binding.tvOption.setTextColor(note.getColorText());
            binding.tvCreatedDetail.setTextColor(note.getColorText());
            binding.tvLastDetail.setTextColor(note.getColorText());
            binding.txTitle.setTextColor(note.getColorText());
            binding.txNote.setTextColor(note.getColorText());
            binding.txTitle.setHintTextColor(note.getColorHint());
            binding.txNote.setHintTextColor(note.getColorHint());
        }

        binding.tvOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOption();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuPost_post:
                if (check_new) {
                    createNote();
                    Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    changeNote();
                }

        }
        return super.onOptionsItemSelected(item);
    }

    private void changeNote() {
        if (!note.getContentNote().equals(binding.txNote.toString())) {
            note.setContentNote(binding.txNote.getText().toString());
        }
        if (!note.getTagNote().equals(binding.tvTagDetail.toString()) || note.getTagNote().equals(null)) {
            note.setTagNote(binding.tvTagDetail.getText().toString());
        }
        if (note.getColorBack() != colorBack) {
            note.setColorBack(colorBack);
            note.setColorHint(colorHint);
            note.setColorText(colorText);
        }
        if (!note.getTitleNote().equals(binding.txTitle.toString())) {
            note.setTitleNote(binding.txTitle.getText().toString());
        }

        if (note.getContentNote().equals("")) {
            Toast.makeText(this, "Nội dung trống!!!", Toast.LENGTH_SHORT).show();
        } else {
            noteDB = new NoteDB(DetailActivity.this);
            noteDB.suaPhieuCap(note);
            noteDB.closeDB();
            Toast.makeText(this, "Note Success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }

    private void createNote() {
        if (!change_color) {
            colorBack = R.drawable.bg_1_note;
            colorHint = getResources().getColor(R.color.colorHint);
            colorText = Color.BLACK;
        }
        NoteOO note = new NoteOO();
        note.setContentNote(binding.txNote.getText().toString());
        note.setIdNote(listLast + 1);
        note.setTagNote(labelSelect);
        note.setTitleNote(binding.txTitle.getText().toString());
        note.setTimeNote(simpleDateFormat.format(date));
        note.setTimeLastChange(simpleDateFormat.format(date));
        note.setTimeAlarm("alarm");
        note.setColorBack(colorBack);
        note.setColorHint(colorHint);
        note.setColorText(colorText);

        if (note.getContentNote().equals("")) {
            Toast.makeText(this, "Note chưa được tạo", Toast.LENGTH_SHORT).show();
        } else {
            noteDB = new NoteDB(DetailActivity.this);
            noteDB.themNote(note);
            noteDB.closeDB();
            Toast.makeText(this, "Note Success", Toast.LENGTH_SHORT).show();
        }
    }

    private void openOption() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_option);
        dialog.setTitle("Option");
        dialog.show();

        final TextView tvLabel = dialog.findViewById(R.id.tvLabel_option);
        final TextView tvXoa = dialog.findViewById(R.id.tvXoa_option);
        final TextView tvAlarm = dialog.findViewById(R.id.tvAlarm);
        final ImageButton btRed = dialog.findViewById(R.id.btRed);
        final ImageButton btYellow = dialog.findViewById(R.id.btyellow);
        final ImageButton btPurple = dialog.findViewById(R.id.btpurple);
        final ImageButton btBlack = dialog.findViewById(R.id.btBlack);
        final ImageButton btBlue = dialog.findViewById(R.id.btBlue);
        final ImageButton btWhite = dialog.findViewById(R.id.btwhite);

        btBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorBack = R.drawable.bg_black;
                colorHint = getResources().getColor(R.color.colorHint);
                colorText = Color.WHITE;
                binding.layoutDetail.setBackgroundResource(colorBack);
                binding.tvOption.setTextColor(Color.WHITE);
                binding.tvCreatedDetail.setTextColor(Color.WHITE);
                binding.tvLastDetail.setTextColor(Color.WHITE);
                binding.txTitle.setTextColor(Color.WHITE);
                binding.txNote.setTextColor(Color.WHITE);
                binding.txTitle.setHintTextColor(getResources().getColor(R.color.colorHint));
                binding.txNote.setHintTextColor(getResources().getColor(R.color.colorHint));
                change_color = true;
            }
        });
        btBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorBack = R.drawable.bg_blue;
                colorHint = getResources().getColor(R.color.colorHint);
                colorText = Color.WHITE;
                binding.layoutDetail.setBackgroundResource(colorBack);
                binding.tvOption.setTextColor(Color.WHITE);
                binding.tvCreatedDetail.setTextColor(Color.WHITE);
                binding.tvLastDetail.setTextColor(Color.WHITE);
                binding.txTitle.setTextColor(Color.WHITE);
                binding.txNote.setTextColor(Color.WHITE);
                binding.txTitle.setHintTextColor(getResources().getColor(R.color.colorHint));
                binding.txNote.setHintTextColor(getResources().getColor(R.color.colorHint));
                change_color = true;
            }
        });
        btRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorBack = R.drawable.bg_red;
                colorHint = getResources().getColor(R.color.colorHint);
                colorText = Color.WHITE;
                binding.layoutDetail.setBackgroundResource(colorBack);
                binding.tvOption.setTextColor(Color.WHITE);
                binding.tvCreatedDetail.setTextColor(Color.WHITE);
                binding.tvLastDetail.setTextColor(Color.WHITE);
                binding.txTitle.setTextColor(Color.WHITE);
                binding.txNote.setTextColor(Color.WHITE);
                binding.txTitle.setHintTextColor(getResources().getColor(R.color.colorHint));
                binding.txNote.setHintTextColor(getResources().getColor(R.color.colorHint));
                change_color = true;
            }
        });
        btYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorBack = R.drawable.bg_yellow;
                colorHint = getResources().getColor(R.color.colorHint);
                colorText = Color.WHITE;
                binding.layoutDetail.setBackgroundResource(colorBack);
                binding.tvOption.setTextColor(Color.WHITE);
                binding.tvCreatedDetail.setTextColor(Color.WHITE);
                binding.tvLastDetail.setTextColor(Color.WHITE);
                binding.txTitle.setTextColor(Color.WHITE);
                binding.txNote.setTextColor(Color.WHITE);
                binding.txTitle.setHintTextColor(getResources().getColor(R.color.colorHint));
                binding.txNote.setHintTextColor(getResources().getColor(R.color.colorHint));
                change_color = true;
            }
        });
        btPurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorBack = R.drawable.bg_purple;
                colorHint = getResources().getColor(R.color.colorHint);
                colorText = Color.WHITE;
                binding.layoutDetail.setBackgroundResource(colorBack);
                binding.tvOption.setTextColor(Color.WHITE);
                binding.tvCreatedDetail.setTextColor(Color.WHITE);
                binding.tvLastDetail.setTextColor(Color.WHITE);
                binding.txTitle.setTextColor(Color.WHITE);
                binding.txNote.setTextColor(Color.WHITE);
                binding.txTitle.setHintTextColor(getResources().getColor(R.color.colorHint));
                binding.txNote.setHintTextColor(getResources().getColor(R.color.colorHint));
                change_color = true;
            }
        });

        btWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorBack = R.drawable.bg_1_note;
                colorHint = getResources().getColor(R.color.colorHint);
                colorText = Color.BLACK;
                binding.layoutDetail.setBackgroundResource(colorBack);
                binding.tvOption.setTextColor(Color.BLACK);
                binding.tvCreatedDetail.setTextColor(Color.BLACK);
                binding.tvLastDetail.setTextColor(Color.BLACK);
                binding.txTitle.setTextColor(Color.BLACK);
                binding.txNote.setTextColor(Color.BLACK);
                binding.txTitle.setHintTextColor(getResources().getColor(R.color.colorHint));
                binding.txNote.setHintTextColor(getResources().getColor(R.color.colorHint));
                change_color = true;
            }
        });

        tvLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogLabel();
            }
        });
        tvXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete code
                delNote();
            }
        });
        tvAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlarm();
            }
        });
    }

    private void delNote() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        builder.setTitle("Remove");
        builder.setMessage("You want to Remove?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                noteDB = new NoteDB(DetailActivity.this);
                noteDB.xoaPhieuCap(note);
                noteDB.closeDB();
                Toast.makeText(DetailActivity.this, "Remove OK", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.show();
    }

    private void openDialogLabel() {
        final Dialog dialog1 = new Dialog(this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_label_to_post);
        dialog1.setTitle("Label");
        dialog1.show();

        final ListView rvLabel = (ListView) dialog1.findViewById(R.id.lvlabelPost);
        if (label.isEmpty()) {
            Toast.makeText(this, "Label null", Toast.LENGTH_SHORT).show();
            Log.i("Crdo", "Label null");
        } else {
            arrayAdapter = new ArrayAdapter(
                    DetailActivity.this,
                    android.R.layout.simple_expandable_list_item_1,
                    label
            );
        }

        rvLabel.setAdapter(arrayAdapter);
        rvLabel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                labelSelect = label.get(position);
                binding.tvTagDetail.setText(labelSelect);
                Toast.makeText(DetailActivity.this, labelSelect, Toast.LENGTH_SHORT).show();
                dialog1.cancel();
            }
        });
    }

    private void openAlarm(){
        final Dialog dialog1 = new Dialog(this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_alarm);
        dialog1.show();

        final EditText txDay = dialog1.findViewById(R.id.txNgay);
        EditText txThang = dialog1.findViewById(R.id.txThang);
        EditText txNam = dialog1.findViewById(R.id.txNam);
        EditText txGio = dialog1.findViewById(R.id.txHour);
        EditText txPhut = dialog1.findViewById(R.id.txMi);
        final EditText txGiay = dialog1.findViewById(R.id.txSec);
        Button btOk = dialog1.findViewById(R.id.btOKAlarm);
        Button btCancel = dialog1.findViewById(R.id.btCancelAlarm);

        Calendar calendar = Calendar.getInstance();
        final Date date = new Date();
//        txDay.setText(String.valueOf(DateFormat.DAY_OF_YEAR_FIELD));
//        txThang.setText(String.valueOf(DateFormat.MONTH_FIELD));
//        txNam.setText(String.valueOf(DateFormat.YEAR_FIELD));
        txGio.setText(String.valueOf(date.getHours()));
        txPhut.setText(String.valueOf(date.getMinutes()));
        txGiay.setText(String.valueOf(date.getSeconds()));
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final Intent intent1 = new Intent(DetailActivity.this, AlarmReceiver.class);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager.cancel(pendingIntent);
                intent1.putExtra("stop", "off");
                sendBroadcast(intent1);
                dialog1.cancel();
            }
        });
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int giay = Integer.valueOf( txGiay.getText().toString()) - date.getSeconds();
                intent1.putExtra("stop", "on");
                pendingIntent = PendingIntent.getBroadcast(DetailActivity.this,
                        0, intent1, pendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + giay*1000, pendingIntent);
            }
        });
    }
}
