package com.yitongmed.multitenant.common.mybatis.model;

import java.io.Serializable;

public class RecordMainExtWithBLOBs extends RecordMainExt implements Serializable {
    private String result;

    private String simpleResult;

    private static final long serialVersionUID = 1L;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getSimpleResult() {
        return simpleResult;
    }

    public void setSimpleResult(String simpleResult) {
        this.simpleResult = simpleResult == null ? null : simpleResult.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", result=").append(result);
        sb.append(", simpleResult=").append(simpleResult);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

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
        RecordMainExtWithBLOBs other = (RecordMainExtWithBLOBs) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
            && (this.getRecordMainId() == null ? other.getRecordMainId() == null : this.getRecordMainId().equals(other.getRecordMainId()))
            && (this.getTempletMainId() == null ? other.getTempletMainId() == null : this.getTempletMainId().equals(other.getTempletMainId()))
            && (this.getFixed() == null ? other.getFixed() == null : this.getFixed().equals(other.getFixed()))
            && (this.getDelFlg() == null ? other.getDelFlg() == null : this.getDelFlg().equals(other.getDelFlg()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getResult() == null ? other.getResult() == null : this.getResult().equals(other.getResult()))
            && (this.getSimpleResult() == null ? other.getSimpleResult() == null : this.getSimpleResult().equals(other.getSimpleResult()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
        result = prime * result + ((getRecordMainId() == null) ? 0 : getRecordMainId().hashCode());
        result = prime * result + ((getTempletMainId() == null) ? 0 : getTempletMainId().hashCode());
        result = prime * result + ((getFixed() == null) ? 0 : getFixed().hashCode());
        result = prime * result + ((getDelFlg() == null) ? 0 : getDelFlg().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getResult() == null) ? 0 : getResult().hashCode());
        result = prime * result + ((getSimpleResult() == null) ? 0 : getSimpleResult().hashCode());
        return result;
    }
}