package com.example.covidsldash;

import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import at.markushi.ui.CircleButton;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GlobeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GlobeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final String url = "https://api.covid19api.com/summary";
    TextView tvdate;
    TextView tvtotal_cases;
    TextView tvtotal_deaths;
    TextView tvrecovered;
    TextView tvsuspects;
    TextView tvnew_deaths;
    TextView tvnew_cases;
    TextView tvactive;
    private CircleButton btnRequest2;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GlobeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GlobeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GlobeFragment newInstance(String param1, String param2) {
        GlobeFragment fragment = new GlobeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        sendAndRequestResponse2();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_globe, container, false);

        btnRequest2 = v.findViewById(R.id.refresh2);

        tvdate = v.findViewById(R.id.date2);
        tvnew_cases = v.findViewById(R.id.txt_new_cases2);
        tvnew_deaths = v.findViewById(R.id.txt_new_deaths2);
        tvrecovered = v.findViewById(R.id.txt_recovered2);
        tvsuspects = v.findViewById(R.id.txt_suspects2);
        tvtotal_cases = v.findViewById(R.id.txt_total2);
        tvtotal_deaths = v.findViewById(R.id.txt_total_death2);
        tvactive = v.findViewById(R.id.txt_active2);

        btnRequest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make your toast here
                // Toast.makeText(getActivity(),"Text!",Toast.LENGTH_SHORT).show();
                Animation animation = new RotateAnimation(0.0f, 360.0f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                        0.5f);
                animation.setRepeatCount(-1);
                animation.setDuration(200);
                // btnRequest.setAnimation(animation);
                sendAndRequestResponse2();

            }
        });
        return v;
    }

    private void sendAndRequestResponse2() {


        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(getContext());

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                //    Toast.makeText(getContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(response, JsonObject.class);

                String total_cases = jsonObject.getAsJsonObject("Global").get("TotalConfirmed").getAsString();
                String total_deaths = jsonObject.getAsJsonObject("Global").get("TotalDeaths").getAsString();
                String total_recovered = jsonObject.getAsJsonObject("Global").get("TotalRecovered").getAsString();
              String total_new_recovered = jsonObject.getAsJsonObject("Global").get("NewRecovered").getAsString();
                String new_deaths = jsonObject.getAsJsonObject("Global").get("NewDeaths").getAsString();
                String new_cases = jsonObject.getAsJsonObject("Global").get("NewConfirmed").getAsString();
          //      String date = jsonObject.getAsJsonObject("Global").get("update_date_time").getAsString();
          //      String activecase = jsonObject.getAsJsonObject("Global").get("NewRecovered").getAsString();

                DecimalFormat decim = new DecimalFormat("#,###");

                try {

                    //Total case animate
                    ValueAnimator animator = new ValueAnimator();
                    animator.setObjectValues(0, Integer.parseInt(total_cases));
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            tvtotal_cases.setText(String.valueOf(animation.getAnimatedValue()));
                        }
                    });
                    animator.setDuration(500);
                    animator.start();

                    //Recovered2
                    ValueAnimator animator2 = new ValueAnimator();
                    animator2.setObjectValues(0, Integer.parseInt(total_recovered));
                    animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            tvrecovered.setText(String.valueOf(animation.getAnimatedValue()));
                        }
                    });
                    animator2.setDuration(500);
                    animator2.start();

                    //Deaths3
                    ValueAnimator animator3 = new ValueAnimator();
                    animator3.setObjectValues(0, Integer.parseInt(total_deaths));
                    animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            tvtotal_deaths.setText(String.valueOf(animation.getAnimatedValue()));
                        }
                    });
                    animator3.setDuration(500);
                    animator3.start();

                    //new recovered4
                    ValueAnimator animator4 = new ValueAnimator();
                    animator4.setObjectValues(0, Integer.parseInt(total_new_recovered));
                    animator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            tvsuspects.setText(String.valueOf(animation.getAnimatedValue()));
                        }
                    });
                    animator4.setDuration(500);
                    animator4.start();

                    //newcase5
                    ValueAnimator animator5 = new ValueAnimator();
                    animator5.setObjectValues(0, Integer.parseInt(new_cases));
                    animator5.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            tvnew_cases.setText(String.valueOf(animation.getAnimatedValue()));
                            //  Toast.makeText(getContext(),"Response :" + new_cases.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                        }
                    });
                    animator5.setDuration(500);
                    animator5.start();

                    //NewDeaths6
                    ValueAnimator animator6 = new ValueAnimator();
                    animator6.setObjectValues(0, Integer.parseInt(new_deaths));
                    animator6.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            tvnew_deaths.setText(String.valueOf(animation.getAnimatedValue()));
                        }
                    });
                    animator6.setDuration(500);
                    animator6.start();


//                    //active7
//                    ValueAnimator animator7 = new ValueAnimator();
//                    animator7.setObjectValues(0, Integer.parseInt(activecase));
//                    animator7.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            tvactive.setText(String.valueOf(animation.getAnimatedValue()));
//                        }
//                    });
//                    animator7.setDuration(500);
//                    animator7.start();

//date7
                    long date = System.currentTimeMillis();

                    SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
                    String dateString = sdf.format(date);
                    tvdate.setText("Last updated on : " + dateString );


//                    tvnew_cases.setText(decim.format(Integer.parseInt(new_cases)));
//                    tvnew_deaths.setText(decim.format(Integer.parseInt(new_deaths)));
//                    tvrecovered.setText(decim.format(Integer.parseInt(total_recovered)));
//                    tvsuspects.setText(decim.format(Integer.parseInt(total_suspects)));
//                    tvtotal_cases.setText(decim.format(Integer.parseInt(total_cases)));
//                    tvtotal_deaths.setText(decim.format(Integer.parseInt(total_deaths)));

                } catch (Exception e) {
                    Toast.makeText(getContext(), "Response :" + e.toString(), Toast.LENGTH_LONG).show();//display the response on screen
//
                }


                // Toast.makeText(getContext(),"Response :" + pageName.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG, "Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }
}