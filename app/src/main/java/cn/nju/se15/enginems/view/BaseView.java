package cn.nju.se15.enginems.view;

/**
 * Created by 果宝 on 2018/1/10.
 */

public interface BaseView<T> {

    /**
     * 设置view的presenter
     *
     * @param presenter
     */
    void setPresenter(T presenter);

}
