package com.jihyun.mobilesoftwareproject;

import static com.jihyun.mobilesoftwareproject.MenuDatabase.TABLE_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Analyze extends AppCompatActivity {

    private TextView monthYear;
    private RecyclerView recyclerView;
    private MenuRecyclerAdapter recyclerAdapter;

    // 메뉴 세부 정보를 표시하는 TextView 추가
    private TextView type_text;
    private TextView time_text;
    private TextView name_text;
    private TextView num_text;
    private TextView date_text;
    private TextView price_text;

    private SQLiteDatabase database;  // 데이터베이스 변수 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);

        // 뷰 초기화
        monthYear = findViewById(R.id.monthYear2);
        recyclerView = findViewById(R.id.recyclerView);

        // RecyclerView 및 어댑터 초기화
        recyclerAdapter = new MenuRecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // 데이터베이스 초기화
        MenuDatabase menuDatabase = MenuDatabase.getInstance(this);
        database = menuDatabase.getWritableDatabase();

        // 월 뷰 설정 및 데이터베이스에서 데이터 가져오기
        setMonthView();
        getAlldata(TABLE_NAME, 1);  // '1'은 적절한 ID로 교체
    }

    private String printDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy\nMM");
        return date.format(formatter);
    }

    private void setMonthView() {
        // 선택한 날짜를 적절한 LocalDate로 대체
        LocalDate selectedDate = LocalDate.now();

        // monthYear의 텍스트 설정
        monthYear.setText(printDate(selectedDate));

        // 텍스트의 일부에 스타일 적용
        String content = monthYear.getText().toString();
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new RelativeSizeSpan(2.5f), 5, 7, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        monthYear.setText(spannableString);

        // RecyclerView에 대한 데이터 생성 (사용자의 논리로 대체)
        ArrayList<String> dayArray = dateArr(selectedDate);

        // DateDecision에 Context가 필요한 경우 'this' (Analyze)를 전달하거나 getApplicationContext() 사용
        // 필요에 따라 결정
    }

    // 실제 날짜 데이터를 생성하는 논리로 대체하는 메서드
    private ArrayList<String> dateArr(LocalDate date) {
        // 여기에 날짜 데이터를 생성하는 로직을 추가
        // 이 부분은 실제 구현으로 대체되어야 합니다.
        ArrayList<String> dummyData = new ArrayList<>();
        dummyData.add("Date 1");
        dummyData.add("Date 2");
        // 필요한 만큼 더 많은 날짜 추가
        return dummyData;
    }

    private void getAlldata(String t_name, int id) {
        Log.d("CustomTag", "GetAllData Start");
        if (database != null) {
            String sql = "SELECT type, time, name, num, kcal, review, image, price, map FROM " + t_name + " WHERE id = " + id;
            Cursor cursor = database.rawQuery(sql, null);
            Log.d("CustomTag", "For sentence Start");
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                // 커서에서 데이터를 추출하고 TextView에 업데이트
                String type = cursor.getString(0);
                String time = cursor.getString(1);
                String name = cursor.getString(2);
                String num = cursor.getString(3);
                String kcal = cursor.getString(4);
                String review = cursor.getString(5);
                String image = cursor.getString(6);
                String map = cursor.getString(8);
                String price = cursor.getString(7);


                // TextView에 데이터 설정
                type_text.setText(type);
                time_text.setText(time);
                name_text.setText(name);
                num_text.setText("음식 하나당 칼로리 : " + kcal + "kcal  /  먹은 양 : " + num + "개");
                price_text.setText(price);
            }
            Log.d("CustomTag", "Data Select End");
        } else {
            Log.d("CustomTag", "No DATABASE");
        }
    }
}
