package com.java.utils.ZIP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @Title  ZipFileIterator.java
 * @Package com.pde.app.util
 * @Description zip文件专用枚举类
 * @author zhangxiaozhe
 * @date 2014-9-15 下午2:59:05
 * @version 1.0
 */
public abstract class ZipFileIterator {

	/**
	 * 移动到下一个文件, 如果已经到尽头, 返回false
	 * @return
	 */
	public abstract boolean next();

	/**
	 * 获取当前文件名
	 * @return
	 */
	public abstract String getFileName();

	/**
	 * 获取当前文件流
	 * @return
	 */
	public abstract InputStream getInputStream();

	/**
	 * 当前节点是否为文件
	 * @return
	 */
	public boolean isFile() {
		return true;
	}

	/**
	 * 文件系统枚举方法
	 * @param path 文件夹路径
	 * @param subFolder 是否包含子文件夹
	 * @return
	 */
	public static ZipFileIterator forFolder(String path, final boolean subFolder) {
		final File root = new File(path);
		final int rootLength = root.getAbsolutePath().length();
		final ArrayList<File> list = new ArrayList<File>();
		if (root.exists() && root.isDirectory()) {
			for (File file : root.listFiles()) {
				list.add(file);
			}
		}
		return new ZipFileIterator() {
			File file = null;

			@Override
			public boolean next() {
				// 如果列表为空则枚举结束
				if (list.isEmpty()) {
					return false;
				}
				file = list.remove(0);
				// 如果是文件, 则为当前目标
				if (file.isFile()) {
					return true;
				}
				// 如果枚举子目录, 把子目录下的文件压入列表
				if (subFolder) {
					File[] files = file.listFiles();
					for (int i = files.length - 1; i >= 0; i--) {
						list.add(0, files[i]);
					}
					return true;
				}
				// 如果不枚举子目录, 继续找下一个标准文件
				return next();
			}
			@Override
			public String getFileName() {
				if (file == null) {
					return null;
				}
				// 相对路径为当前路径 - 根文件路径
				return file.getAbsolutePath().substring(rootLength + 1);
			}
			@Override
			public InputStream getInputStream() {
				if (!isFile()) {
					return null;
				}
				// 打开文件读取流
				InputStream is = null;
				try {
					is = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				return is;
			}
			@Override
			public boolean isFile() {
				return file != null && file.isFile();
			}
		};
	}

	/**
	 * 文件列表枚举方法
	 * @param files 文件列表
	 * @return
	 */
	public static ZipFileIterator forFiles(File[] files) {
		final ArrayList<File> list = new ArrayList<File>();
		if (files != null) {
			for (File file : files) {
				if (file != null) {
					list.add(file);
				}
			}
		}
		return new ZipFileIterator() {
			File file = null;

			Set<String> names = new HashSet<String>();

			@Override
			public boolean next() {
				// 如果列表为空则枚举结束
				if (list.isEmpty()) {
					return false;
				}
				file = list.remove(0);
				// 如果是文件, 则为当前目标
				if (file.isFile()) {
					return true;
				}
				// 如果不是文件, 继续找下一个标准文件
				return next();
			}
			@Override
			public String getFileName() {
				if (file == null) {
					return null;
				}
				// 文件名为原文件名, 如果原文件名被使用, 自动添加序号, 返回2_${原文件名}, 3_${原文件名}...
				String fileName = file.getName();
				if (!names.contains(fileName)) {
					names.add(fileName);
					return fileName;
				}
				for (int i = 2; true; i++) {
					String _fileName = i + "_" + fileName;
					if (!names.contains(_fileName)) {
						names.add(_fileName);
						return _fileName;
					}
				}
			}
			@Override
			public InputStream getInputStream() {
				if (file == null) {
					return null;
				}
				// 打开文件读取流
				InputStream is = null;
				try {
					is = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				return is;
			}
		};
	}

	public static void main(String[] args) {
		ZipFileIterator iterator = ZipFileIterator.forFolder("C:\\Users\\ZHE\\Desktop\\DOC", true);
		while (iterator.next()) {
			System.out.println(iterator.getFileName());
		}
	}
}
