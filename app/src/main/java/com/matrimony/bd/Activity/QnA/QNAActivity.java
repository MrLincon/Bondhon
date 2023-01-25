package com.matrimony.bd.Activity.QnA;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.matrimony.bd.R;

import java.util.ArrayList;

public class QNAActivity extends AppCompatActivity {
    // creating array list for our categories,
    // different news and category recycler view.
    private ArrayList<CategoriesModal> categoriesModalArrayList;
    private ArrayList<NewsModal> popularNewsArrayList, sportsNews, techNews;
    private RecyclerView catRV;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qnaactivity);
        back = findViewById(R.id.btn_back);
        back.setOnClickListener(view -> {
            finish();
        });
        // initializing our variables.
        catRV = findViewById(R.id.idRVNewsCategories);
        categoriesModalArrayList = new ArrayList<>();

        // popular news array list.
        popularNewsArrayList = new ArrayList<>();
        popularNewsArrayList.add(new NewsModal("বায়োডাটা জমা দিতে কত টাকা লাগে?", "আমরা এখন পর্যন্ত কারো কাছ থেকে টাকা নিচ্ছি না।"));
        popularNewsArrayList.add(new NewsModal("বায়োডাটা তৈরি করার কোনো বিশেষ শর্ত আছে?", "আমাদের বায়োডাটা তৈরি করতে হলে নূন্যতম আবশ্যকতা নিম্নরূপ-\n" +
                "\n" +
                "পুরুষ-\n" +
                "১/ ৫ ওয়াক্ত নামাযী হতে হবে।\n" +
                "\n" +
                "২/ ওয়াজিব দাড়ি সুন্নতি পদ্ধতিতে বড় থাকতে হবে।\n" +
                "\n" +
                "৩/ টাখনুর উপর কাপড় পরতে হবে।\n" +
                "\n" +
                "নারী-\n" +
                "১/ ৫ ওয়াক্ত নামাযী হতে হবে।\n" +
                "\n" +
                "২/ “নিকাব” সহ ফরজ পর্দানশীন হতে হবে।"));

        // on below line we are adding our first category.
        categoriesModalArrayList.add(new CategoriesModal("বায়োডাটা তৈরি করা", popularNewsArrayList));
        sportsNews = new ArrayList<>();
        sportsNews.add(new NewsModal("একজনের বায়োডাটা আরেকজন তৈরি করতে পারবে?", "আমাদের বায়োডাটা ফর্মে অনেক ব্যক্তিগত প্রশ্ন আছে, যেগুলোর উত্তর একমাত্র পাত্র-পাত্রী নিজেই ভাল জানেন। পরিবারের অন্য কেউ যদি ফর্মটি তৈরি করে দেন তাহলে সেই প্রশ্নগুলোর উত্তর বাহ্যিকভাবে সত্য হলেও কিছু ত্রুটি থেকে যাবে। এজন্য যার বিয়ে তাকেই লিখতে হবে এমন শর্ত আবশ্যক করা হয়েছে। তবে অনেকেই আছেন বাংলা টাইপ করতে জানেন না। এক্ষেত্রে বাংলা টাইপ করতে জানেন এমন একজনকে পাশে বসিয়ে উনি উত্তরগুলো বলে দিবেন, টাইপ জানা ব্যক্তি লিখে ফর্মটি তৈরি করবেন।"));
        sportsNews.add(new NewsModal("কিছু তথ্য সঠিকভাবে না দেয়ার কারণে আমার বায়োডাটা এপ্রুভ হয় নি, আমি কি আবার বায়োডাটা জমা দিতে পারবো?", "হ্যাঁ পারবেন। যে ঘরে ভুল তথ্য দেয়ার জন্য আপনার বায়োডাটা নট এপ্রুভ করা হয়েছে, সেই ঘরে সঠিক তথ্য দিয়ে Save Changes ক্লিক করে Publish Biodata করবেন তাহলে এপ্রুভ করা হবে ইন শা আল্লাহ। তবে উপরের প্রশ্নের উত্তরে উল্লিখিত বিশেষ শর্ত না থাকার কারণে যদি বায়োডাটা নট এপ্রুভ হয় তাহলে আর এপ্রুভ হবে না। "));

        // on below line we are creating our second category.
        categoriesModalArrayList.add(new CategoriesModal("বায়োডাটা জমা", sportsNews));
        techNews = new ArrayList<>();
        techNews.add(new NewsModal("বায়োডাটা জমা দেয়ার পর বিয়ে হয়ে গেলে বা অন্য কারণে বায়োডাটা ডিলিট করতে পারবো?", "হ্যাঁ, আপনার যখন ইচ্ছা তখন বায়োডাটা ডিলিট করতে পারবেন। বায়োডাটা ডিলিট করতে ই-মেইল এর মাধ্যমে যোগাযোগ করুন।"));
        techNews.add(new NewsModal("আমি একজন ছাত্র, আমার এখনো কোনো আয় নেই, আমি কি বায়োডাটা আপলোড করতে পারবো?", "হ্যাঁ, পারবেন। তবে অবশ্যই আপনার অভিভাবকের অনুমতি নিয়ে বায়োডাটা তৈরি করতে হবে।"));


        // on below line we are setting layout manager to our recycler view.
        catRV.setLayoutManager(new LinearLayoutManager(this));

        // on below line we  are creating and setting adapter to our recycler view.
        CategoriesRVAdapter adapter = new CategoriesRVAdapter(categoriesModalArrayList, this);
        catRV.setAdapter(adapter);
    }
}