package net.qiujuer.italker.push.frags.main;

import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.common.widget.recycler.RecyclerAdapter;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.presenter.contact.ContactContract;
import net.qiujuer.italker.push.R;

/**
 * Created by Administrator on 2019-02-20.
 * <p>
 * by author wz
 * <p>
 * net.qiujuer.italker.push.frags.main
 */
public class OrgElementFragment extends PresenterFragment<ContactContract.Presenter>
        implements ContactContract.View{

    @Override
    protected ContactContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_orgelement;
    }

    @Override
    public RecyclerAdapter<User> getRecyclerAdapter() {
        return null;
    }

    @Override
    public void onAdapterDataChanged() {

    }
}
