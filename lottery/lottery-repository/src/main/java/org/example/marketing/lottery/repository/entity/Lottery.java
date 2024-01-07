package org.example.marketing.lottery.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName lottery
 */
@TableName(value ="lottery")
@Data
public class Lottery implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 概率类型：0-单项不变概率，1-剩余变化概率
     */
    private Integer chanceType;

    /**
     * 发放奖励类型：0:立即发放，1-结束后发放，2-人工发放
     */
    private Integer grantType;

    /**
     * 
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Lottery other = (Lottery) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getChanceType() == null ? other.getChanceType() == null : this.getChanceType().equals(other.getChanceType()))
            && (this.getGrantType() == null ? other.getGrantType() == null : this.getGrantType().equals(other.getGrantType()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getChanceType() == null) ? 0 : getChanceType().hashCode());
        result = prime * result + ((getGrantType() == null) ? 0 : getGrantType().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", chanceType=").append(chanceType);
        sb.append(", grantType=").append(grantType);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}