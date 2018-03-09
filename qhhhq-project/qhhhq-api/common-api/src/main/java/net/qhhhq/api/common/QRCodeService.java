package net.qhhhq.api.common;

import java.io.File;

/**
 *
 * @author bankqh-ldr
 *	二维码处理接口
 */

public interface QRCodeService {

	/**
	 *
	 * @param text	内容
	 * @param width	宽
	 * @param height 高
	 * @param format 图片格式
	 * @param file	生成的图片文件
	 */
	public void generateQRCode(String text, int width, int height, String format, File file);
}
