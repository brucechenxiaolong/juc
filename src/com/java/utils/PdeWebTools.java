package com.java.utils;

import com.java.utils.ZIP.FileUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class PdeWebTools {

    public static void main(String[] args) {

        //原文在线查看,火狐低版本不支持
        InputStream in = null;
        HttpServletRequest req = null;
        HttpServletResponse res = null;
        PdeWebTools.showFile(req, res, in, "pdf");
    }

    /**
     * 请求参数转换为map
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Map re2m(HttpServletRequest request, Map m) {
        m.putAll(re2m(request));
        return m;
    }

    /**
     * 请求参数转换为map
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, String> re2m(HttpServletRequest request) {
        Map<String, String> m = new HashMap<String, String>();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = String.valueOf(paramNames.nextElement());

            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                m.put(paramName, paramValue);
            } else {
                StringBuffer paramValueBF = new StringBuffer();
                for (int i = 0; i < paramValues.length; i++) {
                    paramValueBF.append(paramValues[i]).append(",");
                }
                int l = paramValueBF.length();
                if (l > 0) {
                    m.put(paramName, paramValueBF.substring(0, l - 1));
                }
            }
        }
        return m;
    }

    /**
     * 文件下载
     *
     * @param file 要下载的文件
     * @return
     */
    public static boolean downFile(HttpServletRequest request, HttpServletResponse response,
                                   File file) {
        return downFile(request, response, file, file.getName());
    }


    public static void downFile(String file_path, HttpServletRequest request, HttpServletResponse response) {
        File downloadFile = new File(file_path);//要下载的文件
        if (!downloadFile.isFile()) {
            return;
        }
        long fileLength = downloadFile.length();//记录文件大小
        long pastLength = 0;//记录已下载文件大小
        int rangeSwitch = 0;//0：从头开始的全文下载；1：从某字节开始的下载（bytes=27000-）；2：从某字节开始到某字节结束的下载（bytes=27000-39000）
        long toLength = 0;//记录客户端需要下载的字节段的最后一个字节偏移量（比如bytes=27000-39000，则这个值是为39000）
        long contentLength = 0;//客户端请求的字节总量
        String rangeBytes = "";//记录客户端传来的形如“bytes=27000-”或者“bytes=27000-39000”的内容
        RandomAccessFile raf = null;//负责读取数据
        OutputStream os = null;//写出数据
        OutputStream out = null;//缓冲
        byte b[] = new byte[1024];//暂存容器

        if (request.getHeader("Range") != null) {// 客户端请求的下载的文件块的开始字节
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            rangeBytes = request.getHeader("Range").replaceAll("bytes=", "");
            if (rangeBytes.indexOf('-') == rangeBytes.length() - 1) {//bytes=969998336-
                rangeSwitch = 1;
                rangeBytes = rangeBytes.substring(0, rangeBytes.indexOf('-'));
                pastLength = Long.parseLong(rangeBytes.trim());
                contentLength = fileLength - pastLength + 1;//客户端请求的是 969998336 之后的字节
            } else {//bytes=1275856879-1275877358
                rangeSwitch = 2;
                String temp0 = rangeBytes.substring(0, rangeBytes.indexOf('-'));
                String temp2 = rangeBytes.substring(rangeBytes.indexOf('-') + 1, rangeBytes.length());
                pastLength = Long.parseLong(temp0.trim());//bytes=1275856879-1275877358，从第 1275856879 个字节开始下载
                toLength = Long.parseLong(temp2);//bytes=1275856879-1275877358，到第 1275877358 个字节结束
                contentLength = toLength - pastLength + 1;//客户端请求的是 1275856879-1275877358 之间的字节
            }
        } else {//从开始进行下载
            contentLength = fileLength;//客户端要求全文下载
        }

        /**
         * 如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。
         * 响应的格式是:
         * Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
         * ServletActionContext.getResponse().setHeader("Content-Length",
         * new Long(file.length() - p).toString());
         */
        response.reset();//告诉客户端允许断点续传多线程连接下载,响应的格式是:Accept-Ranges: bytes
        response.setHeader("Accept-Ranges", "bytes");//如果是第一次下,还没有断点续传,状态是默认的 200,无需显式设置;响应的格式是:HTTP/1.1 200 OK
        if (pastLength != 0) {
            //不是从最开始下载,
            //响应的格式是:
            //Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
            switch (rangeSwitch) {
                case 1: {//针对 bytes=27000- 的请求
                    String contentRange = new StringBuffer("bytes ").append(new Long(pastLength).toString()).append("-").append(new Long(fileLength - 1).toString()).append("/").append(new Long(fileLength).toString()).toString();
                    response.setHeader("Content-Range", contentRange);
                    break;
                }
                case 2: {//针对 bytes=27000-39000 的请求
                    String contentRange = rangeBytes + "/" + new Long(fileLength).toString();
                    response.setHeader("Content-Range", contentRange);
                    break;
                }
                default: {
                    break;
                }
            }
        } else {
            //是从开始下载
        }

        try {
            response.addHeader("Content-Disposition", "attachment; filename=\"" + downloadFile.getName() + "\"");
            //ContentType contentType = new ContentType() ;
            // String fileSuffix = "." + PdeTools.getExtension(file_path, "mp4");
            response.setContentType(ContentType.getContentType(FileUtil.getExtension(file_path, ".*")));
            response.addHeader("Content-Length", String.valueOf(contentLength));
            os = response.getOutputStream();
            out = new BufferedOutputStream(os);
            raf = new RandomAccessFile(downloadFile, "r");
            try {
                switch (rangeSwitch) {
                    case 0: {//普通下载，或者从头开始的下载
                        //同1
                    }
                    case 1: {//针对 bytes=27000- 的请求
                        raf.seek(pastLength);//形如 bytes=969998336- 的客户端请求，跳过 969998336 个字节
                        int n = 0;
                        while ((n = raf.read(b, 0, 1024)) != -1) {
                            out.write(b, 0, n);
                        }
                        break;
                    }
                    case 2: {//针对 bytes=27000-39000 的请求
                        raf.seek(pastLength - 1);//形如 bytes=1275856879-1275877358 的客户端请求，找到第 1275856879 个字节
                        int n = 0;
                        long readLength = 0;//记录已读字节数
                        while (readLength <= contentLength - 1024) {//大部分字节在这里读取
                            n = raf.read(b, 0, 1024);
                            readLength += 1024;
                            out.write(b, 0, n);
                        }
                        if (readLength <= contentLength) {//余下的不足 1024 个字节在这里读取
                            n = raf.read(b, 0, (int) (contentLength - readLength));
                            out.write(b, 0, n);
                        }
                        break;
                    }
                    default: {
                        break;
                    }
                }
                out.flush();
            } catch (IOException ie) {
            /**
             * 在写数据的时候，
             * 对于 ClientAbortException 之类的异常，
             * 是因为客户端取消了下载，而服务器端继续向浏览器写入数据时，
             * 抛出这个异常，这个是正常的。
             * 尤其是对于迅雷这种吸血的客户端软件，
             * 明明已经有一个线程在读取 bytes=1275856879-1275877358，
             * 如果短时间内没有读取完毕，迅雷会再启第二个、第三个。。。线程来读取相同的字节段，
             * 直到有一个线程读取完毕，迅雷会 KILL 掉其他正在下载同一字节段的线程，
             * 强行中止字节读出，造成服务器抛 ClientAbortException。
             * 所以，我们忽略这种异常
             */
            //ignore
            }
        } catch (Exception e) {
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 文件下载
     *
     * @param file         要下载的文件
     * @param showFileName 要显示的文件名
     * @return
     */
    public static boolean downFile(HttpServletRequest request, HttpServletResponse response,
                                   File file, String showFileName) {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            return downFile(request, response, is, showFileName, file.length());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            FileUtil.close(is);
        }
    }

    /**
     * 文件下载(新做支持断点下载)
     *
     * @param file         要下载的文件
     * @return
     */
    public static boolean downFileNew(HttpServletRequest request, HttpServletResponse response, File file) {
        try {

            downFile(file.getAbsolutePath(), request, response);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {

        }
    }

    /**
     * 文件下载
     *
     * @param is           文件流
     * @param showFileName 要显示的文件名
     * @return
     */
    public static boolean downFile(HttpServletRequest request, HttpServletResponse response,
                                   InputStream is, String showFileName, long totalByte) {
        BufferedInputStream bis = null;
        OutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            os = response.getOutputStream();
            bos = new BufferedOutputStream(os);
            // 下载的字节范围
            long startByte, endByte;
            if (request != null && request.getHeader("range") != null) { // 断点续传
                String[] range = request.getHeader("range").replaceAll("[^0-9\\-]", "").split("-");
                // 下载起始位置
                startByte = Long.parseLong(range[0]);
                // 下载结束位置
                if (range.length > 1) {
                    endByte = Long.parseLong(range[1]);
                } else {
                    endByte = totalByte;
                }
                // 返回http状态
                response.setStatus(206);
            } else { // 正常下载
                // 下载起始位置
                startByte = 0L;
                // 下载结束位置
                endByte = totalByte;
                // 返回http状态
                response.reset();
                response.setStatus(200);
            }
            // 响应头
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Range", "bytes " + startByte + "-" + endByte + "/" + totalByte);
            response.setHeader("Content-Length", Long.toString(totalByte));
            response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(showFileName.getBytes("GBK"), "iso-8859-1") + "\"");

            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setContentType(ContentType.getContentType(FileUtil.getExtension(showFileName, ".*")));
            int i = -1;
            byte[] b = new byte[1024 * 8];
            while ((i = bis.read(b, 0, b.length)) != -1) {
                bos.write(b, 0, i);
            }
            bos.flush();
            return true;
        } catch (IOException e) {
            System.out.println("下载中断!");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            FileUtil.closeQuiet(bos);
            FileUtil.closeQuiet(os);
            FileUtil.closeQuiet(bis);
            FileUtil.closeQuiet(is);
        }
    }

    public static String setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) throws Exception {
        if (request != null) {
            String userAgent = request.getHeader("user-agent");
            if (userAgent != null && !userAgent.equals("")) {
                userAgent = userAgent.toLowerCase();
                if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
                    // win10 ie edge 浏览器 和其他系统的ie
                    fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
                } else if (userAgent.contains("mozilla")) {//google,火狐浏览器
                    fileName = new String(fileName.getBytes(), "ISO8859-1");
                } else {
                    fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
                }
            } else {
                fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
            }
        } else {
            fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
        }
        return fileName;
    }

    /**
     * 文件查看(不设置返回文件名, 不做response.reset(), 其余与正常下载相同)
     *
     * @param file 要查看的文件
     * @return
     */
    public static boolean showFile(HttpServletRequest request, HttpServletResponse response,
                                   File file) {
        return showFile(request, response, file, file.getName());
    }

    /**
     * 文件查看(不设置返回文件名, 不做response.reset(), 其余与正常下载相同)
     *
     * @param file       要查看的文件
     * @param fileSuffix 要查看的文件名或扩展名, 用于识别ContentType
     * @return
     */
    public static boolean showFile(HttpServletRequest request, HttpServletResponse response,
                                   File file, String fileSuffix) {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            return showFile(request, response, is, fileSuffix);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            FileUtil.close(is);
        }
    }

    /**
     * 文件查看(不设置返回文件名, 不做response.reset(), 其余与正常下载相同)
     *
     * @param is         要查看的文件流
     * @param fileSuffix 要查看的文件名或扩展名, 用于识别ContentType
     * @return
     */
    public static boolean showFile(HttpServletRequest request, HttpServletResponse response,
                                   InputStream is, String fileSuffix) {
        BufferedInputStream bis = null;
        OutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            os = response.getOutputStream();
            bos = new BufferedOutputStream(os);
            // 下载的字节范围
            int startByte, endByte, totalByte;
            if (request != null && request.getHeader("range") != null) {
                // 断点续传
                String[] range = request.getHeader("range").replaceAll("[^0-9\\-]", "").split("-");
                // 文件总大小
                totalByte = is.available();
                // 下载起始位置
                startByte = Integer.parseInt(range[0]);
                // 下载结束位置
                if (range.length > 1) {
                    endByte = Integer.parseInt(range[1]);
                } else {
                    endByte = totalByte - 1;
                }
                // 返回http状态
                response.setStatus(206);
            } else {
                // 正常下载
                // 文件总大小
                totalByte = is.available();
                // 下载起始位置
                startByte = 0;
                // 下载结束位置
                endByte = totalByte - 1;
                // 返回http状态
                response.setStatus(200);
            }
            // 需要下载字节数
            int length = endByte - startByte + 1;
            // 响应头
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Range", "bytes " + startByte + "-" + endByte + "/" + totalByte);
            response.setContentType(ContentType.getContentType(FileUtil.getExtension("." + fileSuffix, ".*")));
            response.setContentLength(length);
            // 响应内容
            bis.skip(startByte);
            int len = 0;
            byte[] buff = new byte[1024 * 64];
            while ((len = bis.read(buff, 0, buff.length)) != -1) {
                if (length <= len) {
                    bos.write(buff, 0, length);
                    break;
                } else {
                    length -= len;
                    bos.write(buff, 0, len);
                }
            }
            return true;
        } catch (IOException e) {
            System.out.println("下载中断!");
            return false;
        } finally {
            FileUtil.closeQuiet(bos);
            FileUtil.closeQuiet(os);
            FileUtil.closeQuiet(bis);
            FileUtil.closeQuiet(is);
        }
    }
}

