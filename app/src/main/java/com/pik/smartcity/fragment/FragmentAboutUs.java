package com.pik.smartcity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.pik.smartcity.MainActivity;
import com.pik.smartcity.adapter.AboutUsAdapter;
import com.pik.smartcity.constanst.IWhereMyLocationConstants;
import com.pik.smartcity.object.AboutUsObject;
import com.ypyproductions.utils.ApplicationUtils;
import com.ypyproductions.utils.DBLog;
import com.ypyproductions.utils.ShareActionUtils;
import com.ypyproductions.utils.StringUtils;

import java.util.ArrayList;

/**
 * FragmentHome.java
 *
 * @author :DOBAO
 * @Email :dotrungbao@gmail.com
 * @Skype :baopfiev_k50
 * @Phone :+84983028786
 * @Date :Nov 26, 2013
 * @project :WhereMyLocation
 * @Package :com.ypyproductions.wheremylocation.fragment
 */
public class FragmentAboutUs extends Fragment implements IWhereMyLocationConstants {

    public static final String TAG = FragmentAboutUs.class.getSimpleName();

    private View mRootView;

    private MainActivity mContext;

    private boolean isFindView;
    private ListView mListViewAbout;

    private ArrayList<AboutUsObject> mListAboutObjects = new ArrayList<AboutUsObject>();
    private AboutUsAdapter mAboutUsAdapter;

    private TextView mTvVersion;

    private TextView mTvAboutUs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(com.pik.smartcity.R.layout.fragment_about, container, false);
        return mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isFindView) {
            isFindView = true;
            this.findView();
        }
    }

    private void findView() {
        this.mContext = (MainActivity) getActivity();
        this.mListViewAbout = (ListView) mRootView.findViewById(com.pik.smartcity.R.id.list_abouts);
        this.mTvVersion = (TextView) mRootView.findViewById(com.pik.smartcity.R.id.tv_version);
        this.mTvAboutUs = (TextView) mRootView.findViewById(com.pik.smartcity.R.id.tv_content_about);

        String versionFormat = String.format(mContext.getString(com.pik.smartcity.R.string.info_version_format), ApplicationUtils.getVersionName(mContext));
        mTvVersion.setText(versionFormat);

        mTvVersion.setTypeface(mContext.mTypeFaceRobotoBold);
        mTvAboutUs.setTypeface(mContext.mTypeFaceRobotoLight);
        mTvAboutUs.setText(String.format(getString(com.pik.smartcity.R.string.info_about_us), getString(com.pik.smartcity.R.string.app_name)));

        int size = LIST_CONTENT_ABOUTS.length;
        for (int i = 0; i < size; i++) {
            String link = LIST_LINK_ABOUTS[i];
            String title = mContext.getString(LIST_TITLE_ABOUTS[i]);
            String content = mContext.getString(LIST_CONTENT_ABOUTS[i]);
            AboutUsObject mAboutUsObject = new AboutUsObject(LIST_ICON_ABOUTS[i], link, title, content);
            mListAboutObjects.add(mAboutUsObject);
        }

        this.mAboutUsAdapter = new AboutUsAdapter(mContext, mListAboutObjects, mContext.mTypeFaceRobotoBold, mContext.mTypeFaceRobotoLight);
        this.mListViewAbout.setAdapter(mAboutUsAdapter);
        mListViewAbout.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                AboutUsObject mAboutUsObject = mListAboutObjects.get(position);
                String link = mAboutUsObject.getLink();
                String title = mAboutUsObject.getTitle();

                if (!StringUtils.isStringEmpty(title) && title.equals(getString(com.pik.smartcity.R.string.title_rate_us))) {
                    String url = String.format(URL_RATE_APP, mContext.getPackageName());
                    ShareActionUtils.goToUrl(mContext, url);
                    return;
                }
                if (!StringUtils.isStringEmpty(link)) {
                    if (link.equals(mContext.getString(com.pik.smartcity.R.string.link_contact_us))) {
                        ShareActionUtils.shareViaEmail(mContext, EMAIL_CONTACT, "", "");
                    } else {
                        ShareActionUtils.goToUrl(mContext, link);
                    }
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DBLog.d(TAG, "==============>Destroy About Us");
        if (mListAboutObjects != null) {
            mListAboutObjects.clear();
            mListAboutObjects = null;
        }
    }
}
