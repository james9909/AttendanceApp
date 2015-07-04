package wang.james.attendance;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment; // Use this instead of android.app.Fragment
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawer extends Fragment {

    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View container_view;
    private DrawerAdapter adapter;
    private RecyclerView mRecyclerDrawer;

    private boolean mUserLearnedDrawer; // Determines if the user is aware of the navigation drawer
    private boolean mFromSavedInstanceState; // Determines if the fragment is coming back or starting for first time

    public NavigationDrawer() {
        // Required empty public constructor
    }

    public List<String> getData() {
        List<String> data = new ArrayList<>();
        String[] items = getResources().getStringArray(R.array.drawer_items);
        for (int i = 0; i < items.length; i++) {
            data.add(items[i]);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) { // If savedInstanceState is null, then it is starting for the first time
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRecyclerDrawer = (RecyclerView) view.findViewById(R.id.drawer_fragment);
        adapter = new DrawerAdapter(getActivity(), getData());
        mRecyclerDrawer.setAdapter(adapter);
        mRecyclerDrawer.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }

    public void setUp(int fragment_id, DrawerLayout drawerLayout, Toolbar toolbar) {
        container_view = getActivity().findViewById(fragment_id);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) { // If the user had previously not learned about the drawer, well now they did
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer  + "");
                }

                getActivity().invalidateOptionsMenu(); // Redraw the action bar
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();

            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState(); // Sync the hamburger icon to match open/closed
            }
        });
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE); // Private so only our app can edit the shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply(); // Faster than commit()
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }
}