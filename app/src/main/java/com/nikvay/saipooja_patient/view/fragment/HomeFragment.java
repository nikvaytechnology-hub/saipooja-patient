package com.nikvay.saipooja_patient.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nikvay.saipooja_patient.MainActivity;
import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.utils.SharedUtils;
import com.nikvay.saipooja_patient.view.activity.AppointmentListActivity;
import com.nikvay.saipooja_patient.view.activity.DocumentPhotoActivity;
import com.nikvay.saipooja_patient.view.activity.GallaryActivity;
import com.nikvay.saipooja_patient.view.activity.LoginActivity;
import com.nikvay.saipooja_patient.view.activity.PreSriptionActivity;
import com.nikvay.saipooja_patient.view.activity.SaipoojaWebActivity;
import com.nikvay.saipooja_patient.view.activity.ServiceListActivity;


public class HomeFragment extends Fragment {

    Context mContext;
    LinearLayout linearFindDoctor, linearAppoinment,linearAptDetail,linearpayment,linearPreScription,ll_enquary,ll_visit,ll_gallary;
    String is_login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mContext = getActivity();

        // viewPagerinit(view);

        find_All_IDs(view);
        events();
        return view;
    }

    private void find_All_IDs(View view) {
        linearAppoinment = view.findViewById(R.id.linearAppoinment);
        ll_visit = view.findViewById(R.id.ll_visit);
        ll_enquary = view.findViewById(R.id.ll_enquary);
        linearFindDoctor = view.findViewById(R.id.linearFindDoctor);
        linearpayment = view.findViewById(R.id.linearpayment);
        linearAptDetail = view.findViewById(R.id.linearAptDetail);
        linearPreScription = view.findViewById(R.id.linearPreScription);
        ll_gallary = view.findViewById(R.id.ll_gallary);
        is_login = SharedUtils.getSharedUtils(mContext);
    }


    private void events() {

        ll_enquary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity) mContext).loadFragment(new EnquiryListFragment());
               /* Intent intent = new Intent(mContext, EnquiryAddActivity.class);
                startActivity(intent);*/
            }
        });
        linearAppoinment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_login.equalsIgnoreCase("") || is_login.equalsIgnoreCase("false")) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, ServiceListActivity.class);
                    startActivity(intent);
                }
            }
        });
        linearAptDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (is_login.equalsIgnoreCase("") || is_login.equalsIgnoreCase("false")) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent inten = new Intent(mContext, AppointmentListActivity.class);
                    startActivity(inten);
                }
            }
        });

        ll_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(mContext, SaipoojaWebActivity.class);
                startActivity(inten);
            }
        });

        linearpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent inten = new Intent(mContext, DocumentPhotoActivity.class);
                startActivity(inten);
             //   ((MainActivity) mContext).loadFragment(new PaymentFragment());

            }
        });
        linearPreScription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (is_login.equalsIgnoreCase("") || is_login.equalsIgnoreCase("false")) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent inten = new Intent(mContext, PreSriptionActivity.class);
                    startActivity(inten);
                }
            }
        });
        linearFindDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mContext).loadFragment(new FindDoctorFragment());
            }
        });

        ll_gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  ((MainActivity) mContext).loadFragment(new VideoGallaryFragment());
                Intent inten = new Intent(mContext, GallaryActivity.class);
                startActivity(inten);
            }
        });
    }



    /// =============================this code is used for View Pager Initilized===========================//////////////////////


/*
    private void viewPagerinit(View view) {
        {for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

            mPager = (ViewPager) view.findViewById(R.id.pager);


            mPager.setAdapter(new PageAdapter(mContext,ImagesArray));


            CirclePageIndicator indicator = (CirclePageIndicator)
                    view.findViewById(R.id.indicator);

            indicator.setViewPager(mPager);

            final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
            indicator.setRadius(5 * density);

            NUM_PAGES =IMAGES.length;

            // Auto start of viewpager
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == NUM_PAGES) {
                        currentPage = 0;
                    }
                    mPager.setCurrentItem(currentPage++, true);
                }
            };
            Timer swipeTimer = new Timer();
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 3000, 3000);

            // Pager listener over indicator
            indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;

                }

                @Override
                public void onPageScrolled(int pos, float arg1, int arg2) {

                }

                @Override
                public void onPageScrollStateChanged(int pos) {

                }
            });

        }

    }
*/


}
