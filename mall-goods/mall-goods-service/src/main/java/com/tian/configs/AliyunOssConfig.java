package com.tian.configs;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.*;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;
import com.tian.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.Date;


@Component
public class AliyunOssConfig {

    private Logger log= LoggerFactory.getLogger(this.getClass());
    /**
     * 斜杠
     */
    private final static String FLAG_SLANTING_ROD = "/";
    /**
     * http://
     */
    private final static String FLAG_HTTP = "http://";
    /**
     * https://
     */
    private final static String FLAG_HTTPS = "https://";
    /**
     * 空字符串
     */
    private final static String FLAG_EMPTY_STRING = "";
    /**
     * 点号
     */
    private final static String FLAG_DOT = ".";
    /**
     * 横杠
     */
    private final static String FLAG_CROSSBAR = "-";

    /**
     * 缺省的最大上传文件大小：20M
     */
    private final static int DEFAULT_MAXIMUM_FILE_SIZE = 20;

    /**
     * endpoint
     */
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    /**
     * access key id
     */
    @Value("${aliyun.oss.keyid}")
    private String accessKeyId;

    /**
     * access key secret
     */
    @Value("${aliyun.oss.keysecret}")
    private String accessKeySecret;

    /**
     * bucket name (namespace)
     */
    @Value("${aliyun.oss.bucketname}")
    private String bucketName;

    /**
     * file host (dev/test/prod)
     */
    @Value("${aliyun.oss.filehost}")
    private String fileHost;

    /**
     * 以文件流的方式上传文件
     * @param fileName 文件名称
     * @param filePath 文件路径
     * @param inputStream 文件输入流
     * @return
     */
    public String uploadFile(String fileName, String filePath, InputStream inputStream) {
        return coreUpload(fileName, filePath, inputStream);
    }

    /**
     * 核心上传功能
     * @param fileName 文件名
     * @param filePath 文件路径
     * @param inputStream 文件输入流
     * @return
     */
    private String coreUpload(String fileName, String filePath, InputStream inputStream) {
        log.info("Start to upload file....");
        if(StringUtils.isEmpty(fileName) || inputStream == null) {
            log.error("Filename Or inputStream is lack when upload file.");
            return null;
        }
        if(StringUtils.isEmpty(filePath)) {
            log.warn("File path is lack when upload file but we automatically generated");
            String dateCategory = DateUtils.format(new Date(), "yyyyMMdd");
            filePath = FLAG_SLANTING_ROD.concat(dateCategory).concat(FLAG_SLANTING_ROD);
        }
        String fileUrl;
        OSSClient ossClient = null;
        try{

            // If the upload file size exceeds the limit
           /* long maxSizeAllowed = getMaximumFileSizeAllowed();
            if(Long.valueOf(inputStream.available()) > maxSizeAllowed) {
                log.error("Uploaded file is too big.");
                return null;
            }*/

            // Create OSS instance
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

            // Create bucket if not exists
            if (!ossClient.doesBucketExist(bucketName)) {
                log.info("Bucket '{}' is not exists and create it now.", bucketName);
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }

            /*********************************/
            // List the bucket in my account
            //listBuckets(ossClient);
            /*********************************/

            // File path format
            if(!filePath.startsWith(FLAG_SLANTING_ROD)) {
                filePath = FLAG_SLANTING_ROD.concat(filePath);
            }
            if(!filePath.endsWith(FLAG_SLANTING_ROD)) {
                filePath = filePath.concat(FLAG_SLANTING_ROD);
            }

            // File url
            StringBuilder buffer = new StringBuilder();
            buffer.append(fileHost).append(filePath).append(fileName);
            fileUrl = buffer.toString();
            log.info("After format the file url is {}", fileUrl);

            // Upload file and set ACL
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, inputStream));
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            if(result != null) {
                log.info("Upload result:{}", result.getETag());
                log.info("Upload file {} successfully.", fileName);
            }
            fileUrl = getHostUrl().concat(fileUrl);
            log.info("Call path is {}", fileUrl);

            /***********************************/
            // List objects in your bucket
            //listObjects(ossClient);
            /***********************************/

        }catch (Exception e){
            log.error("Upload file failed.", e);
            fileUrl = null;
        }finally {
            if(ossClient != null) {
                ossClient.shutdown();
            }
        }
        return fileUrl;
    }

    /**
     * 列出buckets下的所有文件
     * @Author: Captain&D
     * @cnblogs: https://www.cnblogs.com/captainad
     * @param ossClient
     */
    private void listObjects(OSSClient ossClient) {
        System.out.println("Listing objects");
        ObjectListing objectListing = ossClient.listObjects(bucketName);
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(" - " + objectSummary.getKey() + "  " +
                    "(size = " + objectSummary.getSize() + ")");
        }
        System.out.println();
    }

    /**
     * 列出当前用户下的所有bucket
     * @Author: Captain&D
     * @cnblogs: https://www.cnblogs.com/captainad
     * @param ossClient
     */
    private void listBuckets(OSSClient ossClient) {
        System.out.println("Listing buckets");
        ListBucketsRequest listBucketsRequest = new ListBucketsRequest();
        listBucketsRequest.setMaxKeys(500);
        for (Bucket bucket : ossClient.listBuckets()) {
            System.out.println(" - " + bucket.getName());
        }
        System.out.println();
    }

    /**
     * 以文件的形式上传文件
     * @Author: Captain&D
     * @cnblogs: https://www.cnblogs.com/captainad
     * @param fileName
     * @return
     */
    public String uploadFile(String fileName,  InputStream inputStream) {

        String fileUrl = null;
        try{
            fileUrl = uploadFile(fileName, null, inputStream);
        }catch (Exception e){
            log.error("Upload file error.", e);
        }finally {
            IOUtils.safeClose(inputStream);
        }
        return fileUrl;
    }

    /**
     * 获取访问的base地址
     * @Author: Captain&D
     * @cnblogs: https://www.cnblogs.com/captainad
     * @return
     */
    private String getHostUrl() {
        String hostUrl = null;
        if(this.endpoint.startsWith(FLAG_HTTP)) {
            hostUrl = FLAG_HTTP.concat(this.bucketName).concat(FLAG_DOT)
                    .concat(this.endpoint.replace(FLAG_HTTP, FLAG_EMPTY_STRING)).concat(FLAG_SLANTING_ROD);
        } else if (this.endpoint.startsWith(FLAG_HTTPS)) {
            return FLAG_HTTPS.concat(this.bucketName).concat(FLAG_DOT)
                    .concat(this.endpoint.replace(FLAG_HTTPS, FLAG_EMPTY_STRING)).concat(FLAG_SLANTING_ROD);
        }
        return hostUrl;
    }

    /**
     * 删除文件
     * @param fileUrl 文件访问的全路径
     */
    public void deleteFile(String fileUrl) {
        log.info("Start to delete file from OSS.{}", fileUrl);
        if(StringUtils.isEmpty(fileUrl)
                || (!fileUrl.startsWith(FLAG_HTTP)
                && !fileUrl.startsWith(FLAG_HTTPS))) {
            log.error("Delete file failed because the invalid file address. -> {}", fileUrl);
            return;
        }
        OSSClient ossClient = null;
        try{
            /**
             * http:// bucketname                                dev/test/pic/abc.jpg = key
             * http:// captainad.oss-ap-southeast-1.aliyuncs.com/dev/test/pic/abc.jpg
             */
            String key = fileUrl.replace(getHostUrl(), FLAG_EMPTY_STRING);
            if(log.isDebugEnabled()) {
                log.debug("Delete file key is {}", key);
            }
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            ossClient.deleteObject(bucketName, key);
        }catch (Exception e){
            log.error("Delete file error.", e);
        } finally {
            if(ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
    /**
     * 刷新视频上传凭证
     * @param client 发送请求客户端
     * @return RefreshUploadVideoResponse 刷新视频上传凭证响应数据
     * @throws Exception
     */
    public static RefreshUploadVideoResponse refreshUploadVideo(DefaultAcsClient client) throws Exception {
        RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
        request.setVideoId("VideoId");
        return client.getAcsResponse(request);
    }
    // 请求示例
    public static void main(String[] argv) {
        DefaultAcsClient client = initVodClient("LTAI4GD1PzuyF1tp2dZPjx3y", "yeSAx8dIzZ1RQvtfeZLfX0JcRVfihd");
        RefreshUploadVideoResponse response = new RefreshUploadVideoResponse();
        try {
            response = refreshUploadVideo(client);
            System.out.print("UploadAddress = " + response.getUploadAddress() + "\n");
            System.out.print("UploadAuth = " + response.getUploadAuth() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
