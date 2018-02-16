package com.vim.service;

import com.vim.dao.VimDao;
import com.vim.domain.Image;
import com.vim.domain.Resource;
import com.vim.domain.Vnf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * Created on 2018/2/13 at 16:11.  
 ***/
@Service
public class VimService {
    @Autowired
    private VimDao vimDao;

    public VimService() {
    }

    // 接收镜像
    public Image saveImage(final Image image) {
        return vimDao.storeImage(image);
    }

    // 创建资源
    public Resource createResource(final String memory, final String storage) {
        int m = Integer.parseInt(memory);
        int s = Integer.parseInt(storage);
        Resource resource = new Resource(m, s);
        return vimDao.storeResource(resource);
    }

    // 实例化VNF，返回VNF管理地址，测试时返回packgeId-vnfId作为VnfUrl
    public String instantiateVnf(final String vnfId, final String packageId) {
        Vnf vnf=new Vnf(vnfId,packageId);
        vimDao.storeVnf(vnf);
        return packageId + "-" + vnfId;
    }

    // 释放资源，删除注册信息
    public boolean deleteResource(final String resourceId) {
        return vimDao.removeResource(resourceId);
    }

    // 删除VNFM，测试时始终返回true，实际内容应是删除自身的逻辑
    public boolean deleteVnfm() {
        return true;
    }
}
