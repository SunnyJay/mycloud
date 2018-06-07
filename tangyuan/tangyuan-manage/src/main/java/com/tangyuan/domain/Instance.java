package com.tangyuan.domain;

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

    private int type;

    private String ip;

    /**
     * Timestamp以毫秒为单位
     */
    private Timestamp createTime;

    private String userId;

    private int status;

    private int baseOS;

    private int cpuSize;

    private int memorySize;

    private int diskSize;

    private Timestamp expireTime;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getInstanceName()
    {
        return instanceName;
    }

    public void setInstanceName(String instanceName)
    {
        this.instanceName = instanceName;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public Timestamp getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime)
    {
        this.createTime = createTime;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getBaseOS()
    {
        return baseOS;
    }

    public void setBaseOS(int baseOS)
    {
        this.baseOS = baseOS;
    }

    public int getCpuSize()
    {
        return cpuSize;
    }

    public void setCpuSize(int cpuSize)
    {
        this.cpuSize = cpuSize;
    }

    public int getMemorySize()
    {
        return memorySize;
    }

    public void setMemorySize(int memorySize)
    {
        this.memorySize = memorySize;
    }

    public int getDiskSize()
    {
        return diskSize;
    }

    public void setDiskSize(int diskSize)
    {
        this.diskSize = diskSize;
    }

    public Timestamp getExpireTime()
    {
        return expireTime;
    }

    public void setExpireTime(Timestamp expireTime)
    {
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
