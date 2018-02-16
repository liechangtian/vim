package com.vim.domain;


import javax.persistence.*;
import java.util.List;

/***
 * Created on 2018/2/8 at 17:12.
 ***/
@Entity
@Table(name = "vnf")
public class Vnf {
    private static final long serialVersionUID = 1L;
    //对于NFVO上的Vnf,NFVO将其id存为"vnfmId-vnfId"以确保id唯一性，所以长度为41
    private static final int ID_LENGTH = 41;
    private static final int URL_LENGTH = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // id中不能含有字符'-'
    private String id;
    private String packageId;
    private String vnfmId;
    private String vimId;
    private String vnfUrl;
    @OneToMany(targetEntity = Resource.class)
    private List<Resource> resources;

    public Vnf() {
    }

    public Vnf(String id, String packageId) {
        this.id = id;
        this.packageId = packageId;
    }
    public Vnf(String packageId, String vnfmId, String vimId) {
        this.packageId = packageId;
        this.vnfmId = vnfmId;
        this.vimId = vimId;
    }

    public Vnf(String id, String packageId, String vnfmId, String vimId) {
        this.id = id;
        this.packageId = packageId;
        this.vimId = vimId;
        this.vnfmId = vnfmId;
    }

    public Vnf(String id, String packageId, String vnfmId, String vimId,String vnfUrl) {
        this.id = id;
        this.packageId = packageId;
        this.vimId = vimId;
        this.vnfmId = vnfmId;
        this.vnfUrl = vnfUrl;
    }
    public Vnf(String id, String packageId, String vnfmId, String vimId,String vnfUrl,List<Resource> resources) {
        this.id = id;
        this.packageId = packageId;
        this.vimId = vimId;
        this.vnfmId = vnfmId;
        this.vnfUrl = vnfUrl;
        this.resources = resources;
    }

    @Column(unique = true, nullable = false, name = "ID")
    public String getId() {
        return id;
    }

    @Column(length = Vnf.ID_LENGTH, name = "PACKAGEID")
    public String getPackageId() {
        return packageId;
    }

    @Column(length = Vnf.ID_LENGTH, name = "VNFMID")
    public String getVnfmId() {
        return vnfmId;
    }

    @Column(length = Vnf.ID_LENGTH, name = "VIMID")
    public String getVimId() {
        return vimId;
    }

    @Column(length = Vnf.URL_LENGTH, name = "VNFURL")
    public String getVnfUrl() {
        return vnfUrl;
    }

    public List getResources() {
        return resources;
    }

    public void setId(String i) {
        id = i;
    }

    public void setPackageId(String i) {
        packageId = i;
    }

    public void setVimId(String i) {
        vimId = i;
    }

    public void setVnfmId(String i) {
        vnfmId = i;
    }

    public void setVnfUrl(String i) {
        vnfUrl = i;
    }

    public void setResources(List resources) {
        this.resources = resources;
    }

    public boolean addResource(Resource resource) {
        return this.resources.add(resource);
    }

    public boolean deleteResource(String resourceId) {
        for (Resource resource : this.resources) {
            if (resource.getId().equals(resourceId))
                return this.resources.remove(resource);
        }
        return false;
    }

    @Override
    public String toString() {
        return id + ":" + packageId + ":" + vimId + ":" + vnfmId;
    }
}
