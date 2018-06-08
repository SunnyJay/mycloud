package com.tangyuan.domain;

/**
 * 作者：sunna
 * 时间: 2018/6/7 15:42
 */
public class ResourceDefaultConstant
{
    public static final int INSTANCE_REPLICAS_NUM = 1;
    public static final int INSTANCE_CONTAINER_PORT = 22;
    public static final int INSTANCE_SERVICE_PORT = 30033;
    public static final String INSTANCE_SERVICE_TYPE = "NodePort";
    public static final String INSTANCE_LABELS_KEY = "app";
    public static final String INSTANCE_NAMESPACE = "tangyuan";


    public static final String CENTOS = "tutum/centos";
    public static final String UBUNTU = "rastasheep/ubuntu-sshd";

}
