package com.jandali.familyaddresses;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import android.text.InputType;
import java.util.ArrayList;

public class MainActivity extends Activity {
 final ArrayList<EditText> names = new ArrayList<>(), addresses = new ArrayList<>();
 SharedPreferences prefs;
 int green=Color.rgb(17,105,67);
 int dp(float x){return (int)(x*getResources().getDisplayMetrics().density+.5f);}
 LinearLayout.LayoutParams lp(int w,int h){return new LinearLayout.LayoutParams(w<0?w:dp(w),h<0?h:dp(h));}
 TextView title(String text,int size,int color){TextView t=new TextView(this);t.setText(text);t.setTextSize(size);t.setTextColor(color);t.setGravity(Gravity.CENTER);return t;}
 Button button(String s){Button b=new Button(this); b.setText(s);b.setTextSize(15);b.setAllCaps(false);return b;}
 void save(){SharedPreferences.Editor e=prefs.edit(); for(int i=0;i<10;i++){e.putString("name"+i,names.get(i).getText().toString());e.putString("address"+i,addresses.get(i).getText().toString());}boolean ok=e.commit(); Toast.makeText(this,ok?"✅ تم حفظ العناوين":"تعذر الحفظ",Toast.LENGTH_SHORT).show();}
 @Override public void onCreate(Bundle state){super.onCreate(state);getWindow().setStatusBarColor(green);getWindow().setNavigationBarColor(Color.BLACK);prefs=getSharedPreferences("family_addresses",MODE_PRIVATE);
 LinearLayout page=new LinearLayout(this);page.setOrientation(1);page.setBackgroundColor(Color.rgb(246,249,246));page.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
 TextView h=title("📍 عناوين أولادي",29,green);h.setTypeface(null,Typeface.BOLD);h.setPadding(dp(10),dp(22),dp(10),dp(5));page.addView(h,lp(-1,70));
 TextView sub=title("اضغط حفظ بجانب كل عنوان، أو افتح خرائط Google",14,Color.DKGRAY); page.addView(sub,lp(-1,42));
 ScrollView scroll=new ScrollView(this);page.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
 LinearLayout entries=new LinearLayout(this);entries.setOrientation(1);entries.setPadding(dp(12),dp(6),dp(12),dp(12)); scroll.addView(entries);
 for(int i=0;i<10;i++){final int index=i; LinearLayout card=new LinearLayout(this);card.setOrientation(1);card.setBackgroundColor(Color.WHITE);card.setPadding(dp(10),dp(10),dp(10),dp(10));LinearLayout.LayoutParams clp=lp(-1,-2);clp.bottomMargin=dp(10);entries.addView(card,clp);
 TextView num=title("العنوان "+(i+1),18,green);num.setTypeface(null,Typeface.BOLD);card.addView(num,lp(-1,38));
 EditText name=new EditText(this);name.setSingleLine(true);name.setTextSize(16);name.setHint("اسم الابن أو الابنة");name.setText(prefs.getString("name"+i,""));card.addView(name,lp(-1,52));names.add(name);
 EditText address=new EditText(this);address.setSingleLine(true);address.setTextSize(16);address.setHint("الشارع، الرقم، المدينة، الدولة");address.setText(prefs.getString("address"+i,""));card.addView(address,lp(-1,52));addresses.add(address);
 LinearLayout row=new LinearLayout(this);row.setOrientation(LinearLayout.HORIZONTAL);card.addView(row,lp(-1,-2));
 Button save=button("💾 حفظ");row.addView(save,new LinearLayout.LayoutParams(0,dp(52),1));save.setOnClickListener(v->save());
 Button map=button("🗺️ افتح الخريطة");row.addView(map,new LinearLayout.LayoutParams(0,dp(52),1));map.setOnClickListener(v->{String a=address.getText().toString().trim();if(a.isEmpty()){Toast.makeText(this,"أدخل العنوان أولًا",Toast.LENGTH_SHORT).show();return;}save();try{Uri uri=Uri.parse("https://www.google.com/maps/search/?api=1&query="+Uri.encode(a));startActivity(new Intent(Intent.ACTION_VIEW,uri));}catch(Exception ex){Toast.makeText(this,"تعذر فتح الخريطة",Toast.LENGTH_LONG).show();}});
 }
 Button saveAll=button("💾 حفظ جميع العناوين");saveAll.setTextColor(Color.WHITE);saveAll.setBackgroundTintList(android.content.res.ColorStateList.valueOf(green));page.addView(saveAll,lp(-1,58));saveAll.setOnClickListener(v->save());
 setContentView(page);
 }
 @Override protected void onPause(){super.onPause();if(names.size()==10){SharedPreferences.Editor e=prefs.edit();for(int i=0;i<10;i++){e.putString("name"+i,names.get(i).getText().toString());e.putString("address"+i,addresses.get(i).getText().toString());}e.apply();}}
}