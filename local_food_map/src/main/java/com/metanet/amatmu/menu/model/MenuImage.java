package com.metanet.amatmu.menu.model;

public class MenuImage {
    private Long menuId; // Menu 객체 대신 Menu의 ID
    private Long id;
    private String fileName;
    private String url;
    private String originName;
    private String fileCate;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getId() {
        return id; // 수정된 부분
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getFileCate() {
        return fileCate;
    }

    public void setFileCate(String fileCate) {
        this.fileCate = fileCate;
    }

    // 기본 생성자 및 전체 인자 생성자
}
