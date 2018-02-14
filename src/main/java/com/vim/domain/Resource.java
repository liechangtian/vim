package com.vim.domain;

import javax.persistence.*;
import java.io.Serializable;

/***
 * Created on 2018/1/27 at 18:21.
 * resource信息由vim返回给nfvo，id作为主键只在vim内唯一，如何保证nfvo上仍然唯一？
 *      可考虑id前附加vimid。
 *      resource Id并不重要，重要的是参数
 ***/
@Entity
@Table(name = "resource")
public class Resource implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int ID_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // id中不能含有字符'-'
    private String id;
    private int memory;
    private int storage;

    public Resource() {
    }

    // 以字符串初始化Resource，格式为"memory:storage"
    public Resource(String s) {
        String[] resource=s.split(":");
        this.memory = Integer.parseInt(resource[0]);
        this.storage = Integer.parseInt(resource[1]);
    }
    public Resource(int memory, int storage) {
        this.memory = memory;
        this.storage = storage;
    }
    public Resource(String id, int memory, int storage) {
        this.id = id;
        this.memory = memory;
        this.storage = storage;
    }

    @Column(unique = true, nullable = false, length = Resource.ID_LENGTH, name = "ID")
    public String getId() {
        return id;
    }

    @Column(name = "MEMORY")
    public int getMemory() {
        return memory;
    }

    @Column(name = "STORAGE")
    public int getStorage() {
        return storage;
    }

    public void setId(String i) {
        id = i;
    }

    public void setMemory(int n) {
        memory = n;
    }

    public void setStorage(int n) {
        storage = n;
    }


    @Override
    public String toString() {
        return id + ":" + memory + ":" + storage;
    }
}
