/**
 * @author 60417
 * @date 2022/2/18
 * @time 21:53
 * @todo
 */
package com.yuyefanhua.blogsystem.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 博客所属的类别  归档
 * 映射表 t_type
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type {
    private Long id;
    private String name;//该类别的名字

    private List<Blog> blogs = new ArrayList<>();
}
