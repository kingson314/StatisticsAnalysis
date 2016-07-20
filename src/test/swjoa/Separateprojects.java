package test.swjoa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import common.util.conver.UtilConver;
import consts.Const;

/**
 * @Description: 分离项目
 * @date Aug 27, 2014
 * @author:fgq
 */
public class Separateprojects {
	private static String sourceProjectPath = "E:\\fgq\\swjoa";// 源工程路径
	private static String targetProjectPath = "E:\\fgq\\swjoaSp" + UtilConver.dateToStr(Const.fm_yyyy_MM_dd_HH_mm_ss);// 目标工程路径

	public static void main(String[] args) {
		System.out.println(UtilConver.dateToStr(Const.fm_yyyy_MM_dd_HH_mm_ss) + ":开始分离项目...");
		// 不对比包含这些字符的源文件夹,带*号的是所有模糊路径，不带*的是sourceProjectPath+相应路径
		String[] exceptSrcPath = new String[] { "*.svn", "dir", "doc","pdresources", "template", "test", "lib", "sql", "template", 
				"src\\org", 
				"src\\net",
				"src\\META-INF", 
				//"src\\cn\\sinobest",
				"src\\cn\\prpsdc\\portal\\comminfo\\branch",
				"src\\cn\\prpsdc\\portal\\comminfo\\bulletin",
				"src\\cn\\prpsdc\\portal\\comminfo\\business",
				"src\\cn\\prpsdc\\portal\\comminfo\\events",
				"src\\cn\\prpsdc\\portal\\comminfo\\rules",
				"src\\cn\\prpsdc\\portal\\comminfo\\sysnotify",
				"src\\cn\\prpsdc\\portal\\everydayWork\\branch",
				//"src\\cn\\prpsdc\\portal\\knowledgeManage",
				//"src\\cn\\prpsdc\\portal\\knowledgeManage\\index",
				"src\\cn\\prpsdc\\portal\\official\\branch",
				"src\\cn\\prpsdc\\portal\\official\\approval",
				"src\\cn\\prpsdc\\portal\\official\\circulation",
				"src\\cn\\prpsdc\\portal\\official\\counsel",
				"src\\cn\\prpsdc\\portal\\official\\dwSend",
				"src\\cn\\prpsdc\\portal\\official\\partyRecive",
				
				"src\\cn\\prpsdc\\portal\\personal\\assigned",
				"src\\cn\\prpsdc\\portal\\personal\\branch",
				"src\\cn\\prpsdc\\portal\\personal\\convey",
				//"src\\cn\\prpsdc\\portal\\personal\\docHisQuery",
				"src\\cn\\prpsdc\\portal\\utils\\workflow\\branch",
				//"src\\cn\\prpsdc\\portal\\utils\\workflow",
				"webapp\\portal\\default\\comminfo\\bulletion",
				"webapp\\portal\\default\\comminfo\\business", 
				"webapp\\portal\\default\\comminfo\\events", 
				"webapp\\portal\\default\\comminfo\\rules", 
				"webapp\\portal\\default\\comminfo\\sysnotify",
				"webapp\\portal\\default\\personal\\assigned",
				"webapp\\portal\\share\\docManage",
				"webapp\\portal\\share\\archive",
				"webapp\\portal\\share\\counter",
				"webapp\\portal\\share\\security\\branch",
				"webapp\\portal\\share\\fwexport",
				"webapp\\portal\\share\\gwstats",
				"webapp\\portal\\share\\htmleditor",
				"webapp\\portal\\share\\init",
				"webapp\\portal\\share\\knowledge",
				"webapp\\portal\\share\\personal\\docHisQuery",
				"webapp\\portal\\share\\swexport",
				"webapp\\publish\\comminfo\\branch",
				"webapp\\publish\\comminfo\\business",
				"webapp\\publish\\comminfo\\event",
				"webapp\\publish\\comminfo\\rules",
				"webapp\\publish\\comminfo\\sysnotify",
				"webapp\\upload", 
				"webapp\\tesseract", 
				"webapp\\portal\\branch", 
				"webapp\\addin",
				"webapp\\WEB-INF\\classes"
				};
		// 不对比包含这些字符的源文件，模糊
		String[] exceptSrcFilePath = new String[] { 
				"*.svn",
				"resources\\DefineFlow-FJ-conf.xml", 
				"webapp\\testTableIe6.html", 
				"webapp\\test.html",
				"webapp\\setup.exe", 
				"webapp\\PswSetup.msi", 
				"webapp\\print.jsp" ,
				"system\\cn\\prpsdc\\interfaces\\doc\\util\\CirculationPDFConvert.java",
				"system\\cn\\prpsdc\\interfaces\\doc\\util\\CounselPDFConvert.java",
				"system\\cn\\prpsdc\\interfaces\\doc\\util\\PartyRecivePDFConvert.java",
				"system\\cn\\prpsdc\\interfaces\\doc\\util\\PartySendPDFConvert.java",
				"system\\cn\\prpsdc\\interfaces\\doc\\util\\UnitnoticePDFConvert.java",
				"system\\cn\\prpsdc\\system\\task\\processtask\\AssignedTask.java",
				"system\\cn\\prpsdc\\util\\ws\\doc\\AssignedXml.java",
				"system\\cn\\prpsdc\\util\\ws\\doc\\CirculationXml.java",
				"system\\cn\\prpsdc\\util\\ws\\doc\\ConveyXml.java",
				"system\\cn\\prpsdc\\util\\ws\\doc\\CounselXml.java",
				"system\\cn\\prpsdc\\util\\ws\\doc\\DwSendXml.java",
				"system\\cn\\prpsdc\\util\\ws\\doc\\InternalXml.java",
				"system\\cn\\prpsdc\\util\\ws\\doc\\PartyReciveXml.java",
				"system\\cn\\prpsdc\\util\\ws\\doc\\UnitnoticeXml.java",
				};
		// 1 清除已存在目录
		deleteDirectory(targetProjectPath);
		// 2 拷贝更新的文件
		copyAllFile(sourceProjectPath, targetProjectPath, exceptSrcPath, exceptSrcFilePath);
		// 3 清除指定的目录
		String[] exceptDestFilePath = new String[] {};
		for (String path : exceptDestFilePath)
			deleteDirectory(path);
		// 3 清除空的目录
		deleteEmptyDir(new File(targetProjectPath));
		System.out.println(UtilConver.dateToStr(Const.fm_yyyy_MM_dd_HH_mm_ss) + ":done");
	}

	/**
	 * 复制目录下所有文件和目录
	 * 
	 * @param oldPath
	 * @param newPath
	 */
	@SuppressWarnings("static-access")
	private static void copyAllFile(String oldPath, String newPath, String[] exceptSrcPath, String[] exceptSrcFilePath) {
		File file = new File(oldPath);
		if (!file.exists()) {
			return;
		}
		if (file.isFile()) {
			copyFile(oldPath, newPath, exceptSrcFilePath);
			return;
		}
		File[] fileList = file.listFiles();
		File mkdir = new File(newPath);

		if (!mkdir.exists()) {
			mkdir.mkdirs();
		}
		for (int i = 0; i < fileList.length; i++) {
			String old = (oldPath + fileList[i].separator + fileList[i].getName());
			String new1 = (newPath + fileList[i].separator + fileList[i].getName());
			if (fileList[i].isDirectory()) {
				boolean ingored = false;
				for (String path : exceptSrcPath) {
					if (path.indexOf("*") >= 0) {
						path = path.replace("*", "");
					} else {
						path = sourceProjectPath + "\\" + path;
					}
					if (old.indexOf(path.trim()) >= 0) {
						ingored = true;
						break;
					}
				}
				if (!ingored)
					copyAllFile(old, new1, exceptSrcPath, exceptSrcFilePath);
			} else {
				copyFile(old, new1, exceptSrcFilePath);
			}
		}
	}

	/**
	 * 复制目录下单个文件
	 * 
	 * @param oldPass
	 * @param newPath
	 * @return
	 */
	private static boolean copyFile(String oldPath, String newPath, String[] exceptSrcFilePath) {
		boolean result = false;
		try {
			for (String filePath : exceptSrcFilePath) {
				if (filePath.indexOf("*") >= 0) {
					filePath = filePath.replace("*", "");
				} else {
					filePath = sourceProjectPath + "\\" + filePath;
				}
				if (oldPath.indexOf(filePath) >= 0)
					return false;
			}
			File file = new File(oldPath);
			if (file.isDirectory()) {
				return true;
			}
			Long fileTime = file.lastModified();
			if (file.exists()) {
				File destFile = new File(newPath);
				File destFilePath = new File(destFile.getParent());
				if (!destFilePath.exists())
					destFilePath.mkdirs();
				if (!destFile.exists()) {
					destFile.createNewFile();
				}
				FileInputStream inputStream = new FileInputStream(file);
				FileOutputStream outputStream = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = inputStream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, len);
				}
				inputStream.close();
				outputStream.close();
				destFile.setLastModified(fileTime);
			}
			result = true;
			System.out.println(UtilConver.dateToStr(Const.fm_yyyy_MM_dd_HH_mm_ss) + ":" + newPath);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 循环删除空的文件夹
	 * 
	 * @param dir
	 */
	private static void deleteEmptyDir(File dir) {
		if (dir.isDirectory()) {
			File[] fs = dir.listFiles();
			if (fs != null && fs.length > 0) {
				for (int i = 0; i < fs.length; i++) {
					File tmpFile = fs[i];
					if (tmpFile.isDirectory()) {
						deleteEmptyDir(tmpFile);
					}
					if (tmpFile.isDirectory() && tmpFile.listFiles().length <= 0) {
						tmpFile.delete();
					}
				}
			}
			if (dir.isDirectory() && dir.listFiles().length == 0) {
				dir.delete();
			}
		}
	}
}
