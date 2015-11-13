package com.vasax.clothes.service;

import com.vasax.clothes.dao.component.BlobPropertyForPlainComponentDao;
import com.vasax.clothes.entities.component.BlobPropertyForPlainComponent;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Vasax on 08.05.2015.
 */
@Named
public class BlobPropertyForPlainComponentService {
    @Inject
    private BlobPropertyForPlainComponentDao blobPropertyForPlainComponentDao;

    @Transactional
    public BlobPropertyForPlainComponent getPropertyByComponentAndKey(Integer componentId, String key) {
        return blobPropertyForPlainComponentDao.selectByComponentIdAndKey(componentId, key);
    }
}
