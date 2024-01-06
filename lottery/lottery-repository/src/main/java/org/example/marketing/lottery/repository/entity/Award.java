package org.example.marketing.lottery.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 奖品配置
 * @TableName award
 */
@TableName(value ="award")
@Data
public class Award implements Serializable {
    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 奖品ID
     */
    private String awardId;

    /**
     * 奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品）
     */
    private Integer awardType;

    /**
     * 奖品名称
     */
    private String awardName;

    /**
     * 奖品内容「文字描述、Key、码」
     */
    private String awardContent;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

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
        Award other = (Award) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAwardId() == null ? other.getAwardId() == null : this.getAwardId().equals(other.getAwardId()))
            && (this.getAwardType() == null ? other.getAwardType() == null : this.getAwardType().equals(other.getAwardType()))
            && (this.getAwardName() == null ? other.getAwardName() == null : this.getAwardName().equals(other.getAwardName()))
            && (this.getAwardContent() == null ? other.getAwardContent() == null : this.getAwardContent().equals(other.getAwardContent()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAwardId() == null) ? 0 : getAwardId().hashCode());
        result = prime * result + ((getAwardType() == null) ? 0 : getAwardType().hashCode());
        result = prime * result + ((getAwardName() == null) ? 0 : getAwardName().hashCode());
        result = prime * result + ((getAwardContent() == null) ? 0 : getAwardContent().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", awardId=").append(awardId);
        sb.append(", awardType=").append(awardType);
        sb.append(", awardName=").append(awardName);
        sb.append(", awardContent=").append(awardContent);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}