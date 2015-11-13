package com.vasax.clothes.util;

import com.vasax.clothes.entities.ItemImage;
import com.vasax.clothes.service.ImageService;
import com.vasax.clothes.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by vasax32 on 27.03.15.
 */
@Named
@Repository
public class AppInitUtil {
//    @PersistenceContext
//    private EntityManager entityManager;

    @Inject
    private UserService userService;

    @Inject
    private ApplicationContext applicationContext;

//    @Inject
//    private FullTextEntityManager fullTextEntityManager;

    @Inject
    private ImageService imageService;

    @PostConstruct
    public void init() {
        //create super user if it not presented in the system
        userService.checkSuperuser();

        //initialize search capabilities
//        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
//        try {
//            fullTextEntityManager.createIndexer().startAndWait();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.println("Processing watermarks");
        List<Integer> ids = imageService.AllIdsWhereWaterMarkIsNull();
        for (int i = 0; i < ids.size(); i++) {
            Integer id = ids.get(i);
            System.out.println("Processed: " + i + "/" + ids.size());
            ItemImage image = imageService.getById(id);
            if(image.getWithWatermarkData() == null || image.getWithWatermarkData().length == 0 || image.isNeedToBeUpdated()){
                imageService.update(image);
            }
        }


//        List<ItemImage> images = imageService.getAll();
//        for (ItemImage image : images) {
//            if(image.getWithWatermarkData() == null){
//                imageService.update(image);
//            }
//        }

    }
}
