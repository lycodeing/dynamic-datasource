package com.yuanxiaotu.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaotianyu
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("User")
public class User {
    @TableId
    private String id;

    private String name;
}
