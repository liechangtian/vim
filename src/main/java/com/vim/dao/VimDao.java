package com.vim.dao;

import com.vim.domain.Image;
import com.vim.domain.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/***
 * Created on 2018/2/13 at 18:51.  
 ***/
@Repository
public class VimDao {
    @PersistenceContext
    private EntityManager entityManager;

    public VimDao() {
    }

    public Resource findResourceById(final String id) {
        Assert.notNull(id);
        try {
            return entityManager.find(Resource.class, id);
        } catch (final Exception e) {
            return null;
        }
    }

    @Transactional
    public Image storeImage(final Image entity) {
        Image image = entityManager.merge(entity);
        return image;
    }

    @Transactional
    public Resource storeResource(final Resource entity) {
        Resource resource = entityManager.merge(entity);
        return resource;
    }

    // 删除资源注册信息
    @Transactional
    public boolean removeResource(final String resourceId) {
        final Resource resource = findResourceById(resourceId);
        if (resource != null) {
            entityManager.remove(resource);
            return true;
        } else {
            return false;
        }
    }
}
