package com.tangyuan.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * 作者：sunna
 * 时间: 2018/6/4 11:05
 */
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Instance
{
    @Id
    //@GeneratedValue(generator = "jpa-uuid")
    private String id;

    private String instanceName;

    /**
     * 一定要用引用类型，否则RequestBody中没有的字段会被转换为0
     */
    private Integer type;

    private String ip;

    /**
     * Timestamp以毫秒为单位
     */
    private Timestamp createTime;

    private String userId;

    private Integer status;

    private Integer baseOS;

    private Integer cpuSize;

    private Integer memorySize;

    private Integer diskSize;

    private Timestamp expireTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBaseOS() {
        return baseOS;
    }

    public void setBaseOS(Integer baseOS) {
        this.baseOS = baseOS;
    }

    public Integer getCpuSize() {
        return cpuSize;
    }

    public void setCpuSize(Integer cpuSize) {
        this.cpuSize = cpuSize;
    }

    public Integer getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(Integer memorySize) {
        this.memorySize = memorySize;
    }

    public Integer getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(Integer diskSize) {
        this.diskSize = diskSize;
    }

    public Timestamp getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString()
    {
        return "Instance{" +
                "id='" + id + '\'' +
                ", instanceName='" + instanceName + '\'' +
                ", type=" + type +
                ", ip='" + ip + '\'' +
                ", createTime=" + createTime +
                ", userId='" + userId + '\'' +
                ", status=" + status +
                ", baseOS='" + baseOS + '\'' +
                ", cpuSize=" + cpuSize +
                ", memorySize=" + memorySize +
                ", diskSize=" + diskSize +
                ", expireTime=" + expireTime +
                '}';
    }

    public enum InstanceStatus
    {
        RUNING(1),
        STOP(2),
        PAUSE(3);

        private int status;

        InstanceStatus(int status){
            this.status = status;
        }

        public int getStatus()
        {
            return status;
        }
    }
}
