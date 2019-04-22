package cn.nju.se15.enginems.model;

/**
 * Created by NJU.LG on 2019/3/15.
 */
public class ModelRepository{
    private static ModelRepository modelRepository;

    private ModelRepository() {
    }

    public static synchronized ModelRepository getInstance() {
        if(modelRepository == null) {
            modelRepository = new ModelRepository();
        }

        return modelRepository;
    }

}
