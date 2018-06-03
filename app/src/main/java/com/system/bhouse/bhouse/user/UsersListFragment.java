package com.system.bhouse.bhouse.user;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.system.bhouse.Common.message.MessageListActivity_;
import com.system.bhouse.base.AccountInfo;
import com.system.bhouse.base.App;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBaseFragment;
import com.system.bhouse.bhouse.task.bean.UserObject;
import com.system.bhouse.bhouse.task.view.WZFloadtionAction.WZFloationActionButton;
import com.system.bhouse.bhouse.user.sidebar.IndexableListView;
import com.system.bhouse.bhouse.user.sidebar.StringMatcher;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;


/**
 * Created by Administrator on 2017-12-14.
 */
@EFragment(R.layout.activity_users_list)
public class UsersListFragment extends WWBaseFragment {

    @ViewById
    IndexableListView listView;

    @ViewById
    TextView maxUserCount;

    @ViewById
    WZFloationActionButton floatButton;

    UserAdapter adapter = new UserAdapter();

    ArrayList<UserObject> mData = new ArrayList<>();
    ArrayList<UserObject> mSearchData = new ArrayList<>();


    @AfterViews
    protected final void initUsersListActivity() {

        saveData();
        mData = AccountInfo.loadFriends(getActivity(), Friend.Follow);
        mSearchData = new ArrayList<>(mData);
//        setActionBarMidlleTitle("我关注的人");

        adapter.initSection();
        listView.setAdapter(adapter);
        listView.setFastScrollEnabled(true);
        listView.setFastScrollAlwaysVisible(true);
        floatButton.attachToListView(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserObject userObject = mData.get((int) id);
                if (userObject!=null)
                {
                    MessageListActivity_.intent(getActivity()).mUserObject(userObject).start();
                }
            }
        });

    }

    private void saveData() {
        String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String[] sections = new String[mSections.length()];
        for (int i = 0; i < mSections.length(); i++)
            sections[i] = String.valueOf(mSections.charAt(i));

        for (int i = 0; i <sections.length; i++) {
            UserObject userObject = new UserObject();
            userObject.avatar = "https://coding.net/static/fruit_avatar/Fruit-1.png";
            userObject.created_at = 1408690749000L;
            userObject.follow = false;
            userObject.followed = true;
            userObject.follows_count = 0;
            userObject.global_key = sections[i] + "AAAAAA";
            userObject.id = 17724;
            userObject.job = 0;
            userObject.last_activity_at = 1506678237000l;
            userObject.last_logined_at = 1506678237000l;
            userObject.name = sections[i] + "AAAAAA";
            userObject.path = "/u/" + sections[i] + "AAAAAA";
            ;
            userObject.phone_country_code = "+86";
            userObject.pingYin = sections[i] + "AAAAAA";
            mData.add(userObject);
        }

        AccountInfo.saveFriends(getActivity(), mData, Friend.Follow);
    }

    class UserAdapter extends BaseAdapter implements SectionIndexer, StickyListHeadersAdapter {
        private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        private ArrayList<String> mSectionTitle = new ArrayList<>();
        private ArrayList<Integer> mSectionId = new ArrayList<>();

        public void initSection() {

            mSectionTitle.clear();
            mSectionId.clear();

            if (mData.size() > 0) {

                String lastLetter = "";

                for (int i = 0; i < mData.size(); ++i) {
                    UserObject item = mData.get(i);
                    if (!item.getFirstLetter().equals(lastLetter)) {
                        lastLetter = item.getFirstLetter();
                        mSectionTitle.add(item.getFirstLetter());
                        mSectionId.add(i);
                    }
                }
            }
        }

        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {
            HeaderViewHolder holder;
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_project_dynamic_list_head, parent, false);
                holder = new HeaderViewHolder(convertView);
                convertView.setTag(holder);
            }
            else {
                holder = (HeaderViewHolder) convertView.getTag();
            }
            holder.head.setText(mSectionTitle.get(getSectionForPosition(position)));

            return convertView;
        }

        @Override
        public long getHeaderId(int position) {

            return getSectionForPosition(position);
        }

        @Override
        public int getCount() {
            return mSearchData.size();
        }

        @Override
        public Object getItem(int position) {

            return mSearchData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.activity_users_list_item, parent, false);
                holder = new ItemViewHolder(convertView);
                convertView.setTag(holder);
            }
            else {
                holder = (ItemViewHolder) convertView.getTag();
            }
            holder.followMutual.setVisibility(View.INVISIBLE);

            final UserObject data = mSearchData.get(position);

            if (isSection(position)) {
                holder.divideTitle.setVisibility(View.VISIBLE);
                holder.divideTitle.setText(data.getFirstLetter());
            }
            else {
                holder.divideTitle.setVisibility(View.GONE);
            }

            holder.name.setText(data.name);
            Glide.with(App.getContextApp()).load(data.avatar).asBitmap().into(holder.icon);


            return convertView;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            initSection();
        }

        private boolean isSection(int pos) {
            if (getCount() == 0) {
                return true;
            }

            if (pos == 0) {
                return true;
            }

            String currentItem = mData.get(pos).getFirstLetter();
            String preItem = mData.get(pos - 1).getFirstLetter();
            return !currentItem.equals(preItem);
        }

        @Override
        public Object[] getSections() {

            String[] sections = new String[mSections.length()];
            for (int i = 0; i < mSections.length(); i++)
                sections[i] = String.valueOf(mSections.charAt(i));
            return sections;
        }

        @Override
        public int getPositionForSection(int sectionIndex) {
            // If there is no item for current section, previous section will be selected
            for (int i = sectionIndex; i >= 0; i--) {
                for (int j = 0; j < getCount(); j++) {
                    if (i == 0) {
                        // For numeric section
                        for (int k = 0; k <= 9; k++) {
                            if (StringMatcher.match(((UserObject) getItem(j)).getFirstLetter().toUpperCase(), String.valueOf(k)))
                                return j;
                        }
                    }
                    else {
                        if (StringMatcher.match(((UserObject) getItem(j)).getFirstLetter().toUpperCase(), String.valueOf(mSections.charAt(i))))
                            return j;
                    }
                }
            }
            return 0;
        }

        @Override
        public int getSectionForPosition(int position) {
            return 0;
        }

        class HeaderViewHolder {
            @Bind(R.id.head)
            TextView head;

            HeaderViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

        class ItemViewHolder {
            @Bind(R.id.divideTitle)
            TextView divideTitle;
            @Bind(R.id.icon)
            CircleImageView icon;
            @Bind(R.id.name)
            TextView name;
            @Bind(R.id.followMutual)
            CheckBox followMutual;
            @Bind(R.id.divide_line)
            View divideLine;

            ItemViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    public enum Friend {
        Follow, Fans
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.users_follow, menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        item.setIcon(R.drawable.ic_menu_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
//
//        try { // 更改搜索按钮的icon
//            int searchImgId = getResources().getIdentifier("android:id/search_button", null, null);
//            ImageView iv = (ImageView) searchView.findViewById(searchImgId);
//            iv.setImageResource(R.drawable.ic_menu_search);
//        } catch (Exception e) {
//
//        }
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                searchItem(newText);
//                return true;
//            }
//        });
//        return true;
//    }

    private void searchItem(String newText) {
        String s = newText.toLowerCase();
        mSearchData.clear();
        for (UserObject item: mData)
        {
            if (item.global_key.toLowerCase().contains(s)||item.name.toLowerCase().contains(s))
            {
                mSearchData.add(item);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
