package com.qiu.paper_management.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

import java.io.File;
import java.io.InputStream;

public class OssUtil {
    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
    private static final String ENDPOINT = "https://oss-cn-beijing.aliyuncs.com";

    // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
    private static final String OSS_ACCESS_KEY_ID = "LTAI5t8ak5n7WkzvtbhxNiWn";
    private static final String OSS_ACCESS_KEY_SECRET = "YdKWE1DQMKJlMk66O3DhprBwiP0it5";

    // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
    // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
//    private static final String filePath= "C:\\Users\\Qiu\\Desktop\\Differential_Testing_Framework_for_WebAssembly_Runtimes.pdf";

    // 填写Bucket名称，例如examplebucket。
    private static final String bucketName = "papermanagement";
    // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
//    private static final String objectName = "testdir/firstpdf.pdf";

    public static String uploadFile(String originalObj, String filename, InputStream in) throws Exception {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, OSS_ACCESS_KEY_ID, OSS_ACCESS_KEY_SECRET);
        String url = "";
        try {
            // 创建PutObjectRequest对象。
            // 创建文件在oss服务器上的存储路径
            String objectName = filename;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, in);

            // 上传文件,必要时删除原文件。
            if (originalObj!= null && !originalObj.isEmpty())
                ossClient.deleteObject(bucketName, originalObj);
            ossClient.putObject(putObjectRequest);
            url = "https://" + bucketName + "." + ENDPOINT.substring(ENDPOINT.lastIndexOf("/") + 1) + "/" + objectName;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }
}
