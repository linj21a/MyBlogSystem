/**
 * @author 60417
 * @date 2022/2/18
 * @time 21:11
 * @todo
 */
package com.yuyefanhua.blobsystem.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签库  映射到t_tags
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    private Long id;
    private String name;

    private List<Blog> blogs = new ArrayList<>();
}
