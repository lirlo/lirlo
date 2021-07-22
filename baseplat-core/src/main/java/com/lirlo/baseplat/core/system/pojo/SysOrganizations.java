package com.lirlo.baseplat.core.system.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author o-king
 * @since 2019-04-02
 */
@Data
@TableName("sys_organizations")
public class SysOrganizations {

    private static final long serialVersionUID = 1L;

    @TableField(value = "bu_id")
    private Long buId;
    @TableField(value = "organization_id")
    private String organizationId;//部门代码
    @TableField(value = "organization_name")
    private String organizationName;//部门名称
    private String level;//部门级别
    @TableField(value = "parent_org_id")
    private String parentOrgId;//上级部门代码
    /**
     * 组织是否有效的标识（Y/N）
     */
    private String status;

    private String language;

    @TableField(exist = false)
    private String label;

    @TableField(exist = false)
    private String value;

    @TableField(exist=false)
    private String userId;

    @TableField(exist=false)
    private String username;

    @TableField(exist=false)
    private List<SysOrganizations> children;

    @TableField(exist=false)
    private String userJobName;

    @TableField(exist=false)
    private String userJobId;

}
