package wang.james.attendance;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class ViewAttendanceActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private ViewPager mViewPager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout mViewTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.tab_titles));
        mViewPager = (ViewPager) findViewById(R.id.view_attendance_pager);
        mViewPager.setAdapter(adapter);
        mViewTabs = (SlidingTabLayout) findViewById(R.id.view_attendance_tabs);
        mViewTabs.setDistributeEvenly(true);
        mViewTabs.setBackgroundResource(R.color.primary);
        mViewTabs.setSelectedIndicatorColors(getResources().getColor(android.R.color.white));

        mViewTabs.setViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_attendance, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        String[] titles;
        public ViewPagerAdapter(FragmentManager fm, String[] titles) {
            super(fm);
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment tab;
            switch (position) {
                case 0:
                    tab = new ViewTodayFragment();
                    break;
                case 1:
                    tab = new ViewDateFragment();
                    break;
                case 2:
                    tab = new ViewStudentFragment();
                    break;
                default:
                    tab = null;
                    break;
            }
            return tab;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}