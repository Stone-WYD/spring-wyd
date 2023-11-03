package com.test.demowyd.louzai.transaction.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (TmspSysUser)表实体类
 *
 * @author Stone
 * @since 2023-11-02 14:18:04
 */
@SuppressWarnings("serial")
public class TmspSysUser extends Model<TmspSysUser> {
    //主键
    private Long id;
    //用户名
    private String userName;
    //密码
    private String password;
    //所属法院
    private String courtCode;
    //角色标识
    private String roleId;
    //状态（0-冻结，1-可用）
    private Integer status;
    //创建时间
    private LocalDateTime createTime;
    //更新时间
    private LocalDateTime updateTime;
    //删除标记 0-删除 1-未删除
    private Integer delMark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCourtCode() {
        return courtCode;
    }

    public void setCourtCode(String courtCode) {
        this.courtCode = courtCode;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelMark() {
        return delMark;
    }

    public void setDelMark(Integer delMark) {
        this.delMark = delMark;
    }

    @Override
    public String toString() {
        return "TmspSysUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", courtCode='" + courtCode + '\'' +
                ", roleId='" + roleId + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delMark=" + delMark +
                '}';
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }
    }

