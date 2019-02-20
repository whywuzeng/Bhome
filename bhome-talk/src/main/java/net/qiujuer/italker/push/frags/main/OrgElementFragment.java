package net.qiujuer.italker.push.frags.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.common.widget.EmptyView;
import net.qiujuer.italker.common.widget.recycler.RecyclerAdapter;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.presenter.contact.ContactContract;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.R2;
import net.qiujuer.italker.push.adapter.TreeItemAdapter;

import butterknife.BindView;

/**
 * Created by Administrator on 2019-02-20.
 * <p>
 * by author wz
 * <p>
 * net.qiujuer.italker.push.frags.main
 */
public class OrgElementFragment extends PresenterFragment<ContactContract.Presenter>
        implements ContactContract.View{


    @BindView(R2.id.empty)
    EmptyView mEmptyView;

    @BindView(R2.id.recycler)
    RecyclerView mRecycler;

    // 适配器，User，可以直接从数据库查询数据
    private TreeItemAdapter<User> mAdapter;

    public OrgElementFragment(){

    }

    @Override
    protected ContactContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_orgelement;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter();
    }

    @Override
    public RecyclerAdapter<User> getRecyclerAdapter() {
        return null;
    }

    @Override
    public void onAdapterDataChanged() {

    }
}
