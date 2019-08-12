package com.nikvay.saipooja_patient;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.model.DrawerItem;
import com.nikvay.saipooja_patient.model.PatientModel;
import com.nikvay.saipooja_patient.utils.LogoutApplicationDialog;
import com.nikvay.saipooja_patient.utils.RecyclerItemClickListener;
import com.nikvay.saipooja_patient.utils.SharedUtils;
import com.nikvay.saipooja_patient.utils.StaticContent;
import com.nikvay.saipooja_patient.view.activity.AppointmentListActivity;
import com.nikvay.saipooja_patient.view.activity.LoginActivity;
import com.nikvay.saipooja_patient.view.activity.ServiceListActivity;
import com.nikvay.saipooja_patient.view.adapter.DrawerItemAdapter;
import com.nikvay.saipooja_patient.view.fragment.ClassListFragment;
import com.nikvay.saipooja_patient.view.fragment.ChangePasswordFragment;
import com.nikvay.saipooja_patient.view.fragment.FindDoctorFragment;
import com.nikvay.saipooja_patient.view.fragment.HomeFragment;
import com.nikvay.saipooja_patient.view.fragment.ProfileFragment;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
{
    private DrawerLayout drawerLayout;
    private ImageView iv_menu_toolbar, iv_app_share,img_profle;
    private TextView textName,textEmail,textTitleName;
    String is_login,patient_id;
    private String fragmentName = null;
    private Fragment fragmentInstance;
    private FragmentManager fragmentManager;
    private boolean doubleBackToExitPressedOnce = false;
    String redirectId;

    ArrayList<DrawerItem> drawerItemArrayList;
    public static RecyclerView.Adapter drawerItemAdapter;
    private RecyclerView recyclerViewDrawer;

    private ArrayList<PatientModel> patientModelArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        is_login = SharedUtils.getSharedUtils(getApplicationContext());


        find_All_IDs();

        loadFragment(new HomeFragment());
        events();

    }



    private void events()
    {
        iv_menu_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        recyclerViewDrawer.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        displayActivity(drawerItemArrayList.get(position).getCategoryType());
                        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                    }
                })
        );

        iv_app_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();

               // rateApp();
            //    loadFragment(new NotificationFragment());
            }
        });

    }

    private void find_All_IDs()
    {

        drawerLayout = findViewById(R.id.drawer_layout);
        iv_menu_toolbar = findViewById(R.id.iv_menu_toolbar);
        recyclerViewDrawer = findViewById(R.id.recyclerViewDrawer);
        iv_app_share = findViewById(R.id.iv_app_share);
        textTitleName = findViewById(R.id.textTitleName);
        textName = findViewById(R.id.textName);
        textEmail = findViewById(R.id.textEmail);


        drawerItemArrayList = new ArrayList<>();
        drawerItemArrayList.add(new DrawerItem(R.drawable.home, StaticContent.DrawerItem.DASHBOARD));
        drawerItemArrayList.add(new DrawerItem(R.drawable.ic_vector_profil_girl, StaticContent.DrawerItem.MY_ACCOUNT));
        drawerItemArrayList.add(new DrawerItem(R.drawable.appointment, StaticContent.DrawerItem.APPOINTMENT));
        drawerItemArrayList.add(new DrawerItem(R.drawable.my_customer, StaticContent.DrawerItem.MY_APPOINMENT));
        drawerItemArrayList.add(new DrawerItem(R.drawable.service, StaticContent.DrawerItem.SERVICELIST));
        drawerItemArrayList.add(new DrawerItem(R.drawable.ic_vector_doctor_logo, StaticContent.DrawerItem.DOCTORlIST));
        drawerItemArrayList.add(new DrawerItem(R.drawable.my_patient, StaticContent.DrawerItem.CLASS));
        drawerItemArrayList.add(new DrawerItem(R.drawable.ic_vector_change_password, StaticContent.DrawerItem.CHANGEPASSWORD));
        drawerItemArrayList.add(new DrawerItem(R.drawable.ic_vector_rating, StaticContent.DrawerItem.RATEUS));
        drawerItemArrayList.add(new DrawerItem(R.drawable.logout, StaticContent.DrawerItem.LOGOUT));

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String title = intent.getStringExtra("TITLE");
            String description = intent.getStringExtra("DESCRIPTION");
            redirectId  = intent.getStringExtra("REDIRECT_ID");

            notificationRedirect(redirectId);
        }



        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerViewDrawer.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewDrawer.setHasFixedSize(true);
        recyclerViewDrawer.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));

        drawerItemAdapter = new DrawerItemAdapter(MainActivity.this, drawerItemArrayList);
        recyclerViewDrawer.setAdapter(drawerItemAdapter);
        drawerItemAdapter.notifyDataSetChanged();


        patientModelArrayList= SharedUtils.getUserDetails(MainActivity.this);
        patient_id=patientModelArrayList.get(0).getPatient_id();
        textName.setText(patientModelArrayList.get(0).getName());
        textEmail.setText(patientModelArrayList.get(0).getEmail());


    }

    private void notificationRedirect(String redirectId)
    {
        if (redirectId != null) {
            switch (redirectId) {
                case "1":
                        Intent intent =new Intent(this, AppointmentListActivity.class);
                        startActivity(intent);
                    break;
            }
        }
    }

    public   void loadFragment(Fragment fragment)
    {
        fragmentInstance = fragment;
        fragmentName = fragment.getClass().getSimpleName();

        switch (fragmentName) {

            case "NotificationFragment":
                textTitleName.setText(StaticContent.DrawerItem.NOTIFICATION);
                break;

            case "HomeFragment":
                textTitleName.setText(StaticContent.DrawerItem.DASHBOARD);
                break;

            case "AppoinmentFragment":
                textTitleName.setText(StaticContent.DrawerItem.MY_APPOINMENT);
                break;


            case "ProfileFragment":
                textTitleName.setText(StaticContent.DrawerItem.MY_ACCOUNT);
                break;

            case "AppointmentFragment":
                textTitleName.setText(StaticContent.DrawerItem.APPOINTMENT);
                break;

            case "ActivityFragment":
                textTitleName.setText(StaticContent.DrawerItem.ACTIVITY);
                break;

            case "ClassListFragment":
                textTitleName.setText(StaticContent.DrawerItem.CLASS);
                break;

            case "PaymentFragment":
                textTitleName.setText(StaticContent.DrawerItem.PAYMENT);
                break;
            case "ChangePasswordFragment":
                textTitleName.setText(StaticContent.DrawerItem.CHANGEPASSWORD);
                break;



            case "FindDoctorFragment":
                textTitleName.setText(StaticContent.DrawerItem.DOCTORlIST);
                break;

            case "EnquiryListFragment":
                textTitleName.setText(StaticContent.DrawerItem.ENQUIRYLIST);
                break;

            default:
                textTitleName.setText(" ");

        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (fragmentName.equals(fragmentInstance.getClass().getSimpleName())) {
                if (fragmentName.equals("HomeFragment")) {
                    doubleBackPressLogic();
                } else
                    loadFragment(new HomeFragment());
            }
        }
    }

    private void displayActivity(String name) {


        switch (name) {
            case StaticContent.DrawerItem.DASHBOARD:
                loadFragment(new HomeFragment());
                break;

            case StaticContent.DrawerItem.CHANGEPASSWORD:
                loadFragment(new ChangePasswordFragment());
                break;


            case StaticContent.DrawerItem.MY_ACCOUNT:
                loadFragment(new ProfileFragment());
                break;

            case StaticContent.DrawerItem.MY_APPOINMENT:
                Intent intentAppointment = new Intent(this, AppointmentListActivity.class);
                startActivity(intentAppointment);
                break;

            case StaticContent.DrawerItem.APPOINTMENT:

                if (is_login.equalsIgnoreCase("") || is_login.equalsIgnoreCase("false")) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, ServiceListActivity.class);
                    startActivity(intent);
                }
                break;

            case StaticContent.DrawerItem.DOCTORlIST:
                loadFragment(new FindDoctorFragment());
                break;

            case StaticContent.DrawerItem.CLASS:
                 loadFragment(new ClassListFragment());
                break;
            case StaticContent.DrawerItem.RATEUS:
                  rateApp();
                break;


            case StaticContent.DrawerItem.SERVICELIST:
                Intent intentService = new Intent(MainActivity.this, ServiceListActivity.class);
                startActivity(intentService);
                break;

            case StaticContent.DrawerItem.LOGOUT:
                logoutApplication();
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share_item:
                // do your code
                shareApp();
                return true;
            case R.id.review_item:
                // do your code
                rateApp();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // ============ End Double tab back press logic =================
    private void doubleBackPressLogic() {
        if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(MainActivity.this, "Please click back again to exit !!", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 1000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void logoutApplication() {
        LogoutApplicationDialog logout_application = new LogoutApplicationDialog(MainActivity.this);
        logout_application.showDialog();
    }


    private void shareApp() {
        String shareBody = "http://play.google.com/store/apps/details?id=com.nikvay.cnp_master";

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Google Pay (Open it in Google Play Store to Download the Application)");

        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    public void rateApp()
    {
        try
        {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nikvay.cnp_master")));

        } catch (ActivityNotFoundException e)
        {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=com.nikvay.cnp_master" + this.getPackageName())));
        }
    }

}
