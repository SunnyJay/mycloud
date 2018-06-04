package tangyuan.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 作者：sunna
 * 时间: 2018/6/4 11:05
 */
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Instance
{
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    //TODO 加入校验
    private String name;

    private String type;

    private String ip;

    //TODO 修改字段类型
    private String createTime;

    private String userId;

    private String status;

    private String baseOS;

    private String cpuSize;

    private String memorySize;

    private String diskSize;

    private String expireTime;

    public String getExpireTime()
    {
        return expireTime;
    }

    public void setExpireTime(String expireTime)
    {
        this.expireTime = expireTime;
    }

    public String getBaseOS()
    {
        return baseOS;
    }

    public void setBaseOS(String baseOS)
    {
        this.baseOS = baseOS;
    }

    public String getCpuSize()
    {
        return cpuSize;
    }

    public void setCpuSize(String cpuSize)
    {
        this.cpuSize = cpuSize;
    }

    public String getMemorySize()
    {
        return memorySize;
    }

    public void setMemorySize(String memorySize)
    {
        this.memorySize = memorySize;
    }

    public String getDiskSize()
    {
        return diskSize;
    }

    public void setDiskSize(String diskSize)
    {
        this.diskSize = diskSize;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
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

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
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

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
