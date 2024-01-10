package com.metanet.amatmu.menu.dto;

import com.metanet.amatmu.menu.model.MenuImage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuImageDTO {
    private Long id;
    private String fileName;
    private String url;
    private String originName;

    public MenuImage createMenuImage() {
        MenuImage menuImage = new MenuImage();
        menuImage.setId(this.id);
        menuImage.setFileName(this.fileName);
        menuImage.setUrl(this.url);
        menuImage.setOriginName(this.originName);
        return menuImage;
    }

    public static MenuImageDTO of(MenuImage menuImage) {
        return MenuImageDTO.builder()
                .id(menuImage.getId())
                .fileName(menuImage.getFileName())
                .url(menuImage.getUrl())
                .originName(menuImage.getOriginName())
                .build();
    }
}
