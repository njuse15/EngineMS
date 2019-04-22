package cn.nju.se15.enginems.contract;

import cn.nju.se15.enginems.model.entity.DemoVO;
import cn.nju.se15.enginems.presenter.BasePresenter;
import cn.nju.se15.enginems.view.BaseView;

/**
 * Created by NJU.LG on 2019/3/15.
 */

public interface DemoContract {

    interface Presenter extends BasePresenter {
        void getData();
    }

    interface View extends BaseView<Presenter> {
        void showData(DemoVO question);
    }
}
