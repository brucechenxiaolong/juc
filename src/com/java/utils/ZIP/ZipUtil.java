package com.java.utils.ZIP;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


public class ZipUtil {

	private static final int  BUFFER_SIZE = 2 * 1024;

	private final static long VALID_PERIOD = 7*24*3600*1000;//7天的毫秒数

	//跟路径： C:-jfjdag-run
	public static final String ROOT_SRC = (System.getProperty("user.dir").length() == 1 ? "" :System.getProperty("user.dir")) + File.separator;

	public static void main(String[] args) throws Exception {

		//------------------------------------------第一部分：下载单机版zip包到本地磁盘目录，并解压--------------------------------------------

		String basePath = ROOT_SRC + "emergency" + File.separator + "download";

		//先清空download路径
		clearEmergencyFiles(basePath);
		System.out.println("*****basePath=" + basePath + "；成功被清理*****");

		//再重新生成download
		File dir = new File(basePath);
		dir.mkdirs();

		//1.从jar中下载：emergency.zip 到 本地绝对路径：C:-jfjdag-run-emergency-base.zip
		String downloadZipPath = ROOT_SRC + "emergency" + File.separator + "download" + File.separator + "base.zip";
		dir = new File(downloadZipPath);
		boolean isdown = false;
		if(dir.exists()){
			isdown = true;
		}else{
			isdown = downZipModelMethod(downloadZipPath);
		}

		boolean state = false;
		String msg = "";
		String minioZipPath = "";

		if(isdown) {
			//解压zip到当前路径
			List<File> list = ZipUtil.unZip(downloadZipPath, downloadZipPath.substring(0, downloadZipPath.length() - 4));
			System.out.println("被解压的文件一共：" + list.size());
		}

		//------------------------------------------第二部分：压缩文件到zip--------------------------------------------
		//1.重新生成zip
		String sourceDir = ROOT_SRC + "emergency" + File.separator + "download" + File.separator + "base";
		String uploadSrc = ROOT_SRC + "emergency" + File.separator + "upload" + File.separator;

		String fileName = generateZipFile(sourceDir, uploadSrc, 1);
		System.out.println("压缩成功：文件名称是：" + fileName);

	}

	/**
	 * 生成zip文件
	 * downloadType : 1=linux;2=windows
	 * @return targetAllPath=返回zip完整路径
	 * @throws Exception
	 */
	public static String generateZipFile(String sourceDir, String uploadSrc, int downloadType) throws Exception{

		File dir = new File(uploadSrc);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		long millis = System.currentTimeMillis();
		String fileName = "p10c_"+millis+".zip";

		//清理7天外的zip文件
		clearFiles(uploadSrc);

		//判断导出：linux还是windows
		//导出linux，需要删除：startup.bat
		//导出windows，需要删除：startup.sh
		if(downloadType == 2){//windows
			File file_startup = new File(sourceDir + File.separator + "emergency" + File.separator + "startup.sh");
			file_startup.delete();
		}else{//linux
			File file_startup = new File(sourceDir + File.separator + "emergency" + File.separator + "startup.bat");
			file_startup.delete();
		}

		boolean result = ZipUtil.zip(sourceDir,uploadSrc + fileName);//zip生成路径
		if (result) {
			return fileName;
		} else {
			throw new Exception("生成zip文件失败");
		}
	}

	/**
	 * 清理7天外的zip文件
	 * @return
	 */
	public static boolean clearEmergencyFiles(String uploadSrc) {
		File file = new File(uploadSrc);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				try {
					if (StringUtils.isNotEmpty(file2.getName())) {
						//当前有效期之外 删除文件
						file2.delete();
					}
				} catch (Exception e) {
					e.printStackTrace();
					return  false;
				}
			}
		}
		return true;
	}

	/**
	 * 下载：basedb/emergency.zip
	 * @return
	 * @throws IOException
	 */
	public static boolean downZipModelMethod(String downloadZipPath) {
		try {
			String zipFilePath = "basedb/emergency.zip";
			ZipUtil xx = new ZipUtil();
			InputStream inputStream = xx.getClass().getClassLoader().getResourceAsStream(zipFilePath);//FIXME CXL-spring boot中用：this.getClass().getClassLoader().getResourceAsStream(zipFilePath);//获取jar包内路径地址
			// 定义一个文件名字进行接收获取文件
			FileOutputStream fileOut = new FileOutputStream(new File(downloadZipPath));
			byte[] buf = new byte[1024 * 8];
			while (true) {
				int read = 0;
				if (inputStream != null) {
					read = inputStream.read(buf);
				}
				if (read == -1) {
					break;
				}
				fileOut.write(buf, 0, read);
			}
			// 查看文件获取是否成功
			if (fileOut.getFD().valid() == true) {
				System.out.println("获取文件保存成功");
			} else {
				System.out.println("获取文件失败");
			}
			fileOut.flush();
			return true;
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 清理7天外的zip文件
	 * @return
	 */
	public static boolean clearFiles(String uploadSrc) {
		File file = new File(uploadSrc);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				try {
					if (StringUtils.isNotEmpty(file2.getName())) {
						int lastIndex = file2.getName().indexOf(".");
						int firstIndex = file2.getName().indexOf("_");
						long fileTime = Long.parseLong(file2.getName().substring(firstIndex+1,lastIndex));
						//当前有效期之外 删除文件
						if ((fileTime + VALID_PERIOD) < System.currentTimeMillis()) {
							file2.delete();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					return  false;
				}

			}
		}
		return true;
	}

	/**
	 * 压缩文件
	 * @param iterator 源文件枚举
	 * @param zipFile 压缩文件路径
	 * @return
	 */
	public static boolean zip(ZipFileIterator iterator, String zipFile) {
		OutputStream os = null;
		BufferedOutputStream bos = null;
		ZipOutputStream zos = null;
		try {
			os = new FileOutputStream(zipFile);
			bos = new BufferedOutputStream(os);
			zos = new ZipOutputStream(bos);
			zos.setEncoding("GBK");
			int length;
			byte[] buf = new byte[1024 * 4];
			while (iterator.next()) {
				InputStream in = null;
				BufferedInputStream bin = null;
				try {
					String fileName = iterator.getFileName();
					if (iterator.isFile()) {
						// 写入文件内容
						zos.putNextEntry(new ZipEntry(fileName));
						in = iterator.getInputStream();
						bin = new BufferedInputStream(in);
						while ((length = bin.read(buf, 0, buf.length)) != -1) {
							zos.write(buf, 0, length);
						}
					} else {
						// 写入文件夹定义
						if (!fileName.endsWith("\\") && !fileName.endsWith("/")) {
							fileName += File.separator;
						}
						zos.putNextEntry(new ZipEntry(fileName));
					}
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				} finally {
					FileUtil.close(bin);
					FileUtil.close(in);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			FileUtil.close(zos);
			FileUtil.close(bos);
			FileUtil.close(os);
		}
	}

	/**
	 * 压缩文件/文件夹
	 * @param sourceDir 源目录路径
	 * @param zipFile 压缩文件路径
	 */
	public static boolean zip(String sourceDir, String zipFile) {
		ZipFileIterator iterator = ZipFileIterator.forFolder(sourceDir, true);
		return zip(iterator, zipFile);
	}

	/**
	 * 压缩多个文件
	 * @param sourceFiles 源文件列表
	 * @param zipFile 压缩文件路径
	 * @return
	 */
	public static boolean zip(File[] sourceFiles, String zipFile) {
		ZipFileIterator iterator = ZipFileIterator.forFiles(sourceFiles);
		return zip(iterator, zipFile);
	}

	/**
	 * 解压文件
	 * @param zipFile 待压缩文件
	 * @param destDir 解压路径
	 */
	public static List<File> unZip(String zipFile, String destDir) {
		List<File> list = new ArrayList<File>();
		// 目标路径格式标准化
		destDir = new File(destDir).getAbsolutePath() + File.separator;
		// 读取zip文件
		ZipFile zipfile = null;
		try {
			zipfile = new ZipFile(zipFile, "gbk");
			@SuppressWarnings("rawtypes")
			Enumeration enumeration = zipfile.getEntries();
			while (enumeration.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) enumeration.nextElement();
				String destPath = new File(destDir, entry.getName()).getAbsolutePath();
				// 验证文件路径合法, 只能解压到目标路径下
				if (destPath.startsWith(destDir)) {
					list.add(new File(destPath));
					if (entry.isDirectory()) {
						// 如果是文件夹, 生成此文件夹
						FileUtil.mkdirs(destPath);
					} else {
						// 如果是文件, 生成此文件
						InputStream is = null;
						try {
							is = zipfile.getInputStream(entry);
							FileUtil.copyFile(is, destPath);
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						} finally {
							FileUtil.close(is);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (zipfile != null) {
				try {
					zipfile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	/**
	 * byte数组转换成16进制字符串
	 *
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 根据文件流读取真实类型
	 *
	 * @param is
	 * @return
	 */
	public static String getTypeByStream(FileInputStream is) {
		byte[] b = new byte[4];
		try {
			is.read(b, 0, b.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String type = bytesToHexString(b).toUpperCase();
		if (type.contains("504B0304")) {
			return "zip";
		} else if (type.contains("52617221")) {
			return "rar";
		}
		return type;
	}

	public static void unZipOrRar(String sourceRar, String destDir) {
		FileInputStream is;
		try {
			is = new FileInputStream(sourceRar);
			String type = getTypeByStream(is);
			if (type.equals("rar")) {
//				unRar(sourceRar, destDir);
			} else {
				unZip(sourceRar, destDir);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
     * 压缩成ZIP 方法1
     * @param srcDir 压缩文件夹路径
     * @param zipFile    压缩文件输出流
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构;
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(String srcDir, String zipFile, boolean KeepDirStructure)throws RuntimeException{
        long start = System.currentTimeMillis();
        OutputStream os = null;
		BufferedOutputStream bos = null;
		ZipOutputStream zos = null;
		try {
			os = new FileOutputStream(zipFile);
			bos = new BufferedOutputStream(os);
			zos = new ZipOutputStream(bos);
          //zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile,zos,"",KeepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 压缩成ZIP 方法2
     * @param srcFiles 需要压缩的文件列表
     * @param out           压缩文件输出流
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(List<File> srcFiles , OutputStream out)throws RuntimeException {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null ;
        try {
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1){
                   zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 递归压缩方法
     * @param sourceFile 源文件
     * @param zos        zip输出流
     * @param name       压缩后的名称
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构;
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name,boolean KeepDirStructure) throws Exception {
        byte[] buf = new byte[BUFFER_SIZE];
        if(sourceFile.isFile()){
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1){
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if(listFiles == null || listFiles.length == 0){
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if(KeepDirStructure){
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            }else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(),KeepDirStructure);
                    } else {
                       compress(file, zos, file.getName(),KeepDirStructure);
                    }
                }
            }
        }
    }

	//删除指定文件夹下的所有文件
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);//再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	//删除文件夹
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); //删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); //删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
